package cc.flogi.dev.smoothchunks.client.handler;

import cc.flogi.dev.smoothchunks.client.SmoothChunksClient;
import cc.flogi.dev.smoothchunks.client.config.SmoothChunksConfig;
import cc.flogi.dev.smoothchunks.util.UtilEasing;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;

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
        animations.put(chunk, new AnimationController(chunk.getOrigin(), System.currentTimeMillis()));
    }

    public void updateChunk(ChunkBuilder.BuiltChunk chunk, MatrixStack stack) {
        SmoothChunksConfig config = SmoothChunksClient.get().getConfig();

//        System.out.println(config.getDuration() + " - " + config.getLoadAnimation().name() + " - " + config.isDisableNearby());

        AnimationController controller = animations.get(chunk);
        if (controller == null || MinecraftClient.getInstance().getCameraEntity() == null) return;

        if (config.isDisableNearby()) {
            BlockPos cameraPos = MinecraftClient.getInstance().getCameraEntity().getBlockPos();
            BlockPos chunkPos = chunk.getOrigin();

            if (chunkPos.isWithinDistance(cameraPos, 32)) return;
        }

        double completion = (double) (System.currentTimeMillis() - controller.getStartTime()) / config.getDuration() / 1000d;
        completion = UtilEasing.easeOutSine(Math.min(completion, 1.0));

        int chunkY = controller.getFinalPos().getY();

        switch (config.getLoadAnimation()) {
            default:
            case DOWNWARD:
                stack.translate(0, 256 - chunkY - (completion * chunkY), 0);
                break;
            case UPWARD:
                stack.translate(0, -chunkY + (completion * chunkY), 0);
                break;
            case INWARD:
                stack.translate(0, (1 - completion) * controller.getFinalPos().getY(), 0);
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
        private long startTime;
    }
}
