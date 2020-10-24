package cc.flogi.dev.smoothchunks.mixin;

import cc.flogi.dev.smoothchunks.client.handler.ChunkAnimationHandler;
import net.minecraft.client.render.chunk.BlockBufferBuilderStorage;
import net.minecraft.client.render.chunk.ChunkBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 10/07/2020
 */
@SuppressWarnings("rawtypes") @Mixin(ChunkBuilder.BuiltChunk.RebuildTask.class)
public abstract class ChunkBuilderMixin {
    //Parent class
    @SuppressWarnings("ShadowTarget") @Shadow private ChunkBuilder.BuiltChunk field_20839;

    @Inject(
            method = "run",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Set;forEach(Ljava/util/function/Consumer;)V"
            )
    )
    public void onSetOrigin(BlockBufferBuilderStorage buffers, CallbackInfoReturnable<CompletableFuture> cir) {
        ChunkAnimationHandler.get().addChunk(field_20839);
    }
}
