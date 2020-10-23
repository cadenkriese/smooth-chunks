package cc.flogi.dev.smoothchunks.mixin;

import cc.flogi.dev.smoothchunks.client.SmoothChunksClient;
import net.minecraft.client.render.BuiltChunkStorage;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.chunk.ChunkBuilder;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 09/29/2020
 */
@Mixin(BuiltChunkStorage.class)
public abstract class BuiltChunkStorageMixin {
    @Shadow public ChunkBuilder.BuiltChunk[] chunks;
    @Final @Shadow protected WorldRenderer worldRenderer;

    @Inject(method = "updateCameraPosition", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/chunk/ChunkBuilder$BuiltChunk;setOrigin(III)V"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void updateChunkOrigin(double x, double z, CallbackInfo info, int i, int j, int k, int l, int m, int n,
                                   int o, int p, int q, int r, int s, int t, ChunkBuilder.BuiltChunk builtChunk) {
//        if (builtChunk.rebuildFrame == -1 || worldRenderer.frame - builtChunk.rebuildFrame > 2) {
//            System.out.println("Chunk became visible, adding to queue.");
//            SmoothChunksClient.get().getChunkAnimationHandler().addChunk(builtChunk);
//        }
    }
}
