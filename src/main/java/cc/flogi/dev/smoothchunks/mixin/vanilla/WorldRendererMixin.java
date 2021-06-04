package cc.flogi.dev.smoothchunks.mixin.vanilla;

import cc.flogi.dev.smoothchunks.handler.ChunkAnimationHandler;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 09/25/2020
 */
@SuppressWarnings("rawtypes") @Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @Inject(
            method = "renderLayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gl/VertexBuffer;bind()V"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void renderLayerInject(RenderLayer renderLayer, MatrixStack matrixStack, double d, double e, double f,
                                   CallbackInfo ci, boolean bl, ObjectListIterator objectListIterator,
                                   WorldRenderer.ChunkInfo chunkInfo2, ChunkBuilder.BuiltChunk builtChunk, VertexBuffer vertexBuffer) {
        ChunkAnimationHandler.get().updateChunk(builtChunk.getOrigin(), matrixStack);
    }
}
