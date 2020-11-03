package cc.flogi.dev.smoothchunks.mixin;

import cc.flogi.dev.smoothchunks.client.handler.ChunkAnimationHandler;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.chunk.BlockBufferBuilderStorage;
import net.minecraft.client.render.chunk.ChunkBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.concurrent.CompletableFuture;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 10/07/2020
 */
@Mixin(ChunkBuilder.class)
public abstract class ChunkBuilderMixin {
//    //Parent class
//    @SuppressWarnings("ShadowTarget") @Shadow private ChunkBuilder.BuiltChunk field_20839;
    // new
    @SuppressWarnings("ShadowTarget") @Shadow private ChunkBuilder.BuiltChunk field_20837;

    @Inject(
            method = "scheduleRunTasks",
            at = @At(value = "INVOKE",
                     args = "log=true",
                     id = "Ljava/util/concurrent/CompletableFuture;runAsync(Ljava/lang/Runnable;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;"),
            locals = LocalCapture.PRINT
    )
    public void onChunkUploads(CallbackInfo ci, ChunkBuilder.BuiltChunk.Task task, BlockBufferBuilderStorage blockBufferBuilderStorage) {

    }
}
