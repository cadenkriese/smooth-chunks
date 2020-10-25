package cc.flogi.dev.smoothchunks.client.handler;

import cc.flogi.dev.smoothchunks.client.SmoothChunksClient;
import cc.flogi.dev.smoothchunks.client.config.LoadAnimation;
import cc.flogi.dev.smoothchunks.client.config.SmoothChunksConfig;
import cc.flogi.dev.smoothchunks.util.UtilEasing;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;

import java.util.WeakHashMap;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 09/27/2020
 */
public final class ChunkAnimationHandler {
    private static final ChunkAnimationHandler instance = new ChunkAnimationHandler();
    private final WeakHashMap<ChunkBuilder.BuiltChunk, AnimationController> animations = new WeakHashMap<>();

    public static ChunkAnimationHandler get() {return instance;}

    public void addChunk(ChunkBuilder.BuiltChunk chunk) {
        Direction direction = null;

        if (SmoothChunksClient.get().getConfig().getLoadAnimation() == LoadAnimation.INWARD
                && MinecraftClient.getInstance().getCameraEntity() != null) {
            BlockPos dif = chunk.getOrigin().subtract(MinecraftClient.getInstance().getCameraEntity().getBlockPos());

            int difX = Math.abs(dif.getX());
            int difZ = Math.abs(dif.getZ());

            if (difX > difZ) {
                if (dif.getX() > 0) direction = Direction.WEST;
                else direction = Direction.EAST;
            } else {
                if (dif.getZ() > 0) direction = Direction.NORTH;
                else direction = Direction.SOUTH;
            }
        }

        animations.put(chunk, new AnimationController(chunk.getOrigin(), direction, System.currentTimeMillis()));
    }

    public void updateChunk(ChunkBuilder.BuiltChunk chunk, MatrixStack stack) {
        SmoothChunksConfig config = SmoothChunksClient.get().getConfig();

        AnimationController controller = animations.get(chunk);
        if (controller == null || MinecraftClient.getInstance().getCameraEntity() == null) return;

        if (config.isDisableNearby()) {
            BlockPos cameraPos = MinecraftClient.getInstance().getCameraEntity().getBlockPos();
            BlockPos chunkPos = chunk.getOrigin();

            if (chunkPos.isWithinDistance(cameraPos, 32)) return;
        }

        double completion = (double) (System.currentTimeMillis() - controller.getStartTime()) / config.getDuration() / 1000d;
        completion = UtilEasing.easeOutSine(Math.min(completion, 1.0));

        BlockPos finalPos = controller.getFinalPos();

        switch (config.getLoadAnimation()) {
            default:
            case DOWNWARD:
                stack.translate(0, finalPos.getY() - (completion * finalPos.getY()), 0);
                break;
            case UPWARD:
                stack.translate(0, -finalPos.getY() + (completion * finalPos.getY()), 0);
                break;
            case INWARD:
                if (controller.getDirection() == null) break;
                Vec3i dirVec = controller.getDirection().getVector();
                double mod = -(200 - UtilEasing.easeInOutSine(completion) * 200);
                stack.translate(dirVec.getX() * mod, 0, dirVec.getZ() * mod);
                break;
            case SCALE:
                stack.scale((float) completion, (float) completion, (float) completion);
                break;
        }

        if (completion >= 1.0) animations.remove(chunk);
    }

    @AllArgsConstructor @Data
    private static class AnimationController {
        private BlockPos finalPos;
        private Direction direction;
        private long startTime;
    }
}
