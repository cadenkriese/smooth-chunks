package cc.flogi.dev.smoothchunks.client.handler;

import cc.flogi.dev.smoothchunks.client.config.LoadAnimation;
import cc.flogi.dev.smoothchunks.client.config.SmoothChunksConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

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
    private final List<BlockPos> completedChunks = new ArrayList<>();

    public ChunkAnimationHandler(SmoothChunksConfig config) {
        DURATION = config.getDuration() * 1000;
        LOAD_ANIMATION = config.getLoadAnimation();
        DISABLE_NEARBY = config.isDisableNearby();
    }

    public void update(ChunkBuilder.BuiltChunk chunk, MatrixStack stack) {
        if (completedChunks.contains(chunk.getOrigin())) return;

        AnimationController controller = animations.get(chunk);

        if (controller == null) {
            controller = new AnimationController(chunk.getOrigin(), System.currentTimeMillis());
            animations.put(chunk, controller);
        }

        double completion = (double) (System.currentTimeMillis() - controller.getStartTime()) / DURATION;
        completion = Math.min(completion, 1.0);

        switch (LOAD_ANIMATION) {
            default:
            case UPWARD:
                stack.translate(0, (1 - completion) * controller.getFinalPos().getY(), 0);
                break;
            case DOWNWARD:
                stack.translate(0, -(1 - completion) * controller.getFinalPos().getY(), 0);
                break;
//            case INWARD:
//                stack.translate(0, (1 - completion) * controller.getFinalPos().getY(), 0);
//                break;
        }

        if (completion >= 1.0) {
            completedChunks.add(chunk.getOrigin());
            animations.remove(chunk);
        }
    }

    @AllArgsConstructor @Data
    private static class AnimationController {
        private BlockPos finalPos;
        private long startTime;
    }
}
