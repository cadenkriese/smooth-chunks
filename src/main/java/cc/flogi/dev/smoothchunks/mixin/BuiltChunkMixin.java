package cc.flogi.dev.smoothchunks.mixin;

import cc.flogi.dev.smoothchunks.handler.ChunkAnimationHandler;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 11/01/2020
 */
@Mixin(ChunkBuilder.BuiltChunk.class)
public abstract class BuiltChunkMixin {
    @Shadow public abstract BlockPos getOrigin();

    @Inject(
            method = "clear",
            at = @At(value = "TAIL")
    )
    public void onDelete(CallbackInfo ci) {
        ChunkAnimationHandler.get().getLoadedChunks().remove(getOrigin());
    }

    @Inject(
            method = "rebuild",
            at = @At(value = "HEAD")
    )
    public void onRebuild(CallbackInfo ci) {
//        ChunkAnimationHandler.get().addChunk((ChunkBuilder.BuiltChunk) (Object) this);
    }
}
