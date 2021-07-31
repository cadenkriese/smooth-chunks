package cc.flogi.dev.smoothchunks.mixin;

import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(WorldRenderer.class)
public class MixinWoldRenderer {
    @Inject(at = @At(
            value = "INVOKE",
            shift = At.Shift.AFTER,
            target = "Lnet/minecraft/client/render/chunk/ChunkBuilder$BuiltChunk;getOrigin()Lnet/minecraft/util/math/BlockPos;"

    ), method = "renderLayer", locals = LocalCapture.CAPTURE_FAILHARD)
    public void mixinRenderLayer(RenderLayer renderLayer, MatrixStack matrices, double d, double e, double f, Matrix4f matrix4f, CallbackInfo ci, boolean bl, ObjectListIterator objectListIterator, VertexFormat vertexFormat, Shader shader, GlUniform glUniform, boolean bl2, WorldRenderer.ChunkInfo chunkInfo2, ChunkBuilder.BuiltChunk builtChunk, VertexBuffer vertexBuffer) {
        //TODO render system holds one shader at a time as it works through the render process
        // at this point that is something to do with world render layers
        // we should probably add the uniform here, but where to put our own custom shader is still unknown.
    }


}
