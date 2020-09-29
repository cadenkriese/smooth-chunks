package cc.flogi.dev.smoothchunks.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 09/27/2020
 */
public class ChunkAnimationHandler {
    private static final long DURATION = 1000;

    private static final HashMap<ChunkBuilder.BuiltChunk, AnimationController> animations = new HashMap<>();

    public static void update(ChunkBuilder.BuiltChunk chunk, MatrixStack stack) {
        AnimationController controller = animations.get(chunk);

        if (controller == null) {
            controller = new AnimationController(chunk.getOrigin(), System.currentTimeMillis());
            animations.put(chunk, controller);
        }

        double completion = (double) (System.currentTimeMillis() - controller.getStartTime()) / DURATION;
        completion = Math.min(completion, 1.0);

        stack.translate(0, (1 - completion) * controller.getFinalPos().getY(), 0);

        if (completion >= 1.0) animations.remove(chunk);
    }

    @AllArgsConstructor @Data
    private static class AnimationController {
        private BlockPos finalPos;
        private long startTime;
    }
}
