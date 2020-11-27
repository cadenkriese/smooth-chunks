package cc.flogi.dev.smoothchunks.mixin;

import cc.flogi.dev.smoothchunks.access.BuiltChunkTaskAccess;
import cc.flogi.dev.smoothchunks.handler.ChunkAnimationHandler;
import net.minecraft.client.render.chunk.BlockBufferBuilderStorage;
import net.minecraft.client.render.chunk.ChunkBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 11/03/2020
 */
@Mixin(ChunkBuilder.class)
public abstract class ChunkBuilderMixin {
    @Inject(
            method = "scheduleRunTasks",
            at = @At(value = "INVOKE",
                     args = "log=true",
                     target = "Ljava/util/concurrent/CompletableFuture;runAsync(Ljava/lang/Runnable;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void onRunTasks(CallbackInfo ci, ChunkBuilder.BuiltChunk.Task task, BlockBufferBuilderStorage blockBufferBuilderStorage) {
        ChunkAnimationHandler.get().addChunk(((BuiltChunkTaskAccess) task).getParentClass());
    }
}