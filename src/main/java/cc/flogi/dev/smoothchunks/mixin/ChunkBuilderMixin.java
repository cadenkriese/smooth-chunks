package cc.flogi.dev.smoothchunks.mixin;

import cc.flogi.dev.smoothchunks.client.SmoothChunksClient;
import net.minecraft.client.render.chunk.BlockBufferBuilderStorage;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 10/07/2020
 */
@SuppressWarnings("rawtypes") @Mixin(ChunkBuilder.BuiltChunk.RebuildTask.class)
public abstract class ChunkBuilderMixin {
    @SuppressWarnings("ShadowTarget") @Shadow private ChunkBuilder.BuiltChunk field_20839;

    @Inject(
            method = "run",
            at = @At(
                    value = "INVOKE",
                    args = "log=true",
                    target = "Ljava/util/Set;forEach(Ljava/util/function/Consumer;)V"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void onSetOrigin(BlockBufferBuilderStorage buffers, CallbackInfoReturnable<CompletableFuture> cir, Vec3d vec3d,
                            float f, float g, float h, ChunkBuilder.ChunkData chunkData, List list) {
        SmoothChunksClient.get().getChunkAnimationHandler().addChunk(field_20839);
    }
}
