package cc.flogi.dev.smoothchunks.mixin;

import cc.flogi.dev.smoothchunks.client.ChunkAnimationHandler;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.BuiltChunkStorage;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
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
 * Created on 09/25/2020
 */
@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @Inject(
            method = "renderLayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gl/VertexBuffer;bind()V"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void rotateRender(RenderLayer renderLayer, MatrixStack matrixStack, double d, double e, double f,
                              CallbackInfo ci, boolean bl, ObjectListIterator objectListIterator,
                              WorldRenderer.ChunkInfo chunkInfo2, ChunkBuilder.BuiltChunk builtChunk, VertexBuffer vertexBuffer) {
        int chunkX = builtChunk.getOrigin().getX() / 16;
        int chunkZ = builtChunk.getOrigin().getZ() / 16;

        ChunkAnimationHandler.update(builtChunk, matrixStack);
    }
}
