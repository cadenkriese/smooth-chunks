package cc.flogi.dev.smoothchunks.mixin;

import cc.flogi.dev.smoothchunks.ext.IMixinChunkInfo;
import cc.flogi.dev.smoothchunks.ext.IMixinShader;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@SuppressWarnings("rawtypes")
@Mixin(LevelRenderer.class)
public class MixinWoldRenderer {
    @Inject(at = @At(
            value = "INVOKE",
            shift = At.Shift.AFTER,
            target = "Lnet/minecraft/client/renderer/chunk/ChunkRenderDispatcher$RenderChunk;getOrigin()Lnet/minecraft/core/BlockPos;"

    ), method = "renderChunkLayer", locals = LocalCapture.CAPTURE_FAILHARD)
    public void mixinRenderLayer(RenderType renderLayer, PoseStack matrices, double d, double e, double f, Matrix4f matrix4f, CallbackInfo ci, boolean bl, ObjectListIterator objectListIterator, VertexFormat vertexFormat, ShaderInstance shader, Uniform glUniform, boolean bl2, LevelRenderer.RenderChunkInfo chunkInfo, ChunkRenderDispatcher.RenderChunk builtChunk, VertexBuffer vertexBuffer) {
        //TODO render system holds one shader at a time as it works through the render process
        // at this point that is something to do with world render layers
        // we should probably add the uniform here, but where to put our own custom shader is still unknown.

        IMixinShader mixinShader = (IMixinShader) shader;
        mixinShader.getSmoothChunkLifetimeUniform().set(RenderSystem.getShaderGameTime() - ((IMixinChunkInfo) chunkInfo).getCreationTime());
    }
}
