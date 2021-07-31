//package cc.flogi.dev.smoothchunks.handler;
//
//import cc.flogi.dev.smoothchunks.SmoothChunks;
//import cc.flogi.dev.smoothchunks.config.LoadAnimation;
//import cc.flogi.dev.smoothchunks.config.SmoothChunksConfig;
//import cc.flogi.dev.smoothchunks.util.UtilEasing;
//import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.util.math.MatrixStack;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.Direction;
//import net.minecraft.util.math.Vec3i;
//
///**
// * @author Caden Kriese (flogic)
// *
// * Created on 09/27/2020
// */
//public final class ChunkAnimationHandler {
//    private static final ChunkAnimationHandler instance = new ChunkAnimationHandler();
//    private final Object2ObjectOpenHashMap<Vec3i, AnimationController> animations = new Object2ObjectOpenHashMap<>();
//
//    public static ChunkAnimationHandler get() {
//        return instance;
//    }
//
//    /**
//     * Adds a chunk to the animation handler, the chunk will be animated over the next few frames.
//     *
//     * @param position The position of the chunk to animate.
//     */
//    public void addChunk(BlockPos position) {
//        if (animations.containsKey(position)) return;
//
//        Direction direction = null;
//
//        if (SmoothChunks.get().getConfig().getLoadAnimation() == LoadAnimation.INWARD
//                && MinecraftClient.getInstance().getCameraEntity() != null) {
//            Vec3i delta = position.subtract(MinecraftClient.getInstance().getCameraEntity().getBlockPos());
//
//            int dX = Math.abs(delta.getX());
//            int dZ = Math.abs(delta.getZ());
//
//            if (dX > dZ) {
//                if (delta.getX() > 0) direction = Direction.WEST;
//                else direction = Direction.EAST;
//            } else {
//                if (delta.getZ() > 0) direction = Direction.NORTH;
//                else direction = Direction.SOUTH;
//            }
//        }
//
//        animations.putIfAbsent(position, new AnimationController(direction, System.currentTimeMillis()));
//    }
//
//    /**
//     * Called for each chunk every frame, updates the animation progress of the given chunk.
//     *
//     * @param chunkPos The position of the chunk to be updated.
//     * @param stack    The stack to have translations & scale calls pushed onto it.
//     */
//    public void updateChunk(Vec3i chunkPos, MatrixStack stack) {
//        SmoothChunksConfig config = SmoothChunks.get().getConfig();
//
//        AnimationController controller = animations.get(chunkPos);
//        if (controller == null || MinecraftClient.getInstance().getCameraEntity() == null) return;
//
//        if (config.isDisableNearby()) {
//            double dX = chunkPos.getX() - MinecraftClient.getInstance().getCameraEntity().getPos().getX();
//            double dZ = chunkPos.getZ() - MinecraftClient.getInstance().getCameraEntity().getPos().getZ();
//            if (dX * dX + dZ * dZ < 32 * 32) return;
//        }
//
//        double completion = (double) (System.currentTimeMillis() - controller.getStartTime()) / config.getDuration() / 1000d;
//        completion = UtilEasing.easeOutSine(Math.min(completion, 1.0));
//
//        switch (config.getLoadAnimation()) {
//            default:
//            case DOWNWARD:
//                stack.translate(0, (chunkPos.getY() - completion * chunkPos.getY()) * config.getTranslationAmount(), 0);
//                break;
//            case UPWARD:
//                stack.translate(0, (-chunkPos.getY() + completion * chunkPos.getY()) * config.getTranslationAmount(), 0);
//                break;
//            case INWARD:
//                if (controller.getDirection() == null) break;
//                Vec3i dirVec = controller.getDirection().getVector();
//                double mod = -(200 - UtilEasing.easeInOutSine(completion) * 200) * config.getTranslationAmount();
//                stack.translate(dirVec.getX() * mod, 0, dirVec.getZ() * mod);
//                break;
//            case SCALE:
//                //TODO Find a way to scale centered at the middle of the chunk rather than the origin.
//                stack.scale((float) completion, (float) completion, (float) completion);
//                break;
//        }
//
//        if (completion >= 1.0) animations.remove(chunkPos);
//    }
//
//    //TODO consider calling this on F3+A or any world reloads to quickly empty the animations map.
//    public void clearAnimations() {
//        animations.clear();
//    }
//
//    @AllArgsConstructor @Data
//    private static class AnimationController {
//        private Direction direction;
//        private long startTime;
//    }
//}
