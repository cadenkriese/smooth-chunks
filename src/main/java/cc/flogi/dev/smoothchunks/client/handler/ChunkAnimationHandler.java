package cc.flogi.dev.smoothchunks.client.handler;

import cc.flogi.dev.smoothchunks.client.config.LoadAnimation;
import cc.flogi.dev.smoothchunks.client.config.SmoothChunksConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 09/27/2020
 */
public final class ChunkAnimationHandler {
    private final long DURATION;
    private final LoadAnimation LOAD_ANIMATION;
    private final boolean DISABLE_NEARBY;

    private final WeakHashMap<ChunkBuilder.BuiltChunk, AnimationController> animations = new WeakHashMap<>();
//    private final HashMap<ChunkBuilder.BuiltChunk, AnimationController> animations = new HashMap<>();

    public ChunkAnimationHandler(SmoothChunksConfig config) {
        DURATION = config.getDuration() * 1000;
        LOAD_ANIMATION = config.getLoadAnimation();
        DISABLE_NEARBY = config.isDisableNearby();
    }

    public void addChunk(ChunkBuilder.BuiltChunk chunk) {
        animations.put(chunk, new AnimationController(chunk.getOrigin(), System.currentTimeMillis()));
    }

    public void updateChunk(WorldRenderer renderer, WorldRenderer.ChunkInfo info, ChunkBuilder.BuiltChunk chunk, MatrixStack stack) {
        AnimationController controller = animations.get(chunk);
        if (controller == null || MinecraftClient.getInstance().getCameraEntity() == null) return;

        // The chunk was not built last frame.
//        if () {
//
//        }
//        if (chunk.rebuildFrame - renderer.frame > -1) {
//            addChunk(chunk);
//            controller = animations.get(chunk);
//        }

        //TODO for disable_nearby, check if dY < radius || dX < radius. Or just check distSq for a minuscule performance hit.

        double completion = (double) (System.currentTimeMillis() - controller.getStartTime()) / DURATION;
        completion = Math.min(completion, 1.0);

        int chunkY = controller.getFinalPos().getY();

        switch (LOAD_ANIMATION) {
            default:
            case DOWNWARD:
                stack.translate(0, 256 - chunkY - (completion * chunkY), 0);
                break;
            case UPWARD:
                stack.translate(0, -chunkY + (completion * chunkY), 0);
                break;
//            case INWARD:
//                stack.translate(0, (1 - completion) * controller.getFinalPos().getY(), 0);
//                break;
        }

        if (completion >= 1.0) animations.remove(chunk);
    }

    private void cullDistantChunks(WorldRenderer renderer) {
        List<ChunkBuilder.BuiltChunk> toCull = animations.keySet().stream().filter(chunk -> chunk.rebuildFrame - renderer.frame < -1).collect(Collectors.toList());
//                 .forEach(animations::remove);

        if (toCull.size() > 0) {
            System.out.printf("Found %d chunks. -- ", toCull.size());
            System.out.printf("Frame difference is %d. -- ", toCull.get(0).rebuildFrame - renderer.frame);

            String xBehind = toCull.get(0).getOrigin().getX() < Objects.requireNonNull(MinecraftClient.getInstance().getCameraEntity()).getBlockPos().getX() ? "behind" : "in-front of";
            String zBehind = toCull.get(0).getOrigin().getZ() < Objects.requireNonNull(MinecraftClient.getInstance().getCameraEntity()).getBlockPos().getZ() ? "behind" : "in-front of";

            System.out.printf("The chunk is %s you on the x-axis and %s you on the z-axis.\n", xBehind, zBehind);
        }
//        toCull.forEach(animations::remove);
    }

    @AllArgsConstructor @Data
    private static class AnimationController {
        private BlockPos finalPos;
        private long startTime;
    }
}
