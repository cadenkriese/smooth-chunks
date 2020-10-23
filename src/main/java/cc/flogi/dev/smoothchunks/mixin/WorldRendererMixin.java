package cc.flogi.dev.smoothchunks.mixin;

import cc.flogi.dev.smoothchunks.client.SmoothChunksClient;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.chunk.ChunkBuilder;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Queue;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 09/25/2020
 */
@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @Shadow private BuiltChunkStorage chunks;

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
        SmoothChunksClient.get().getChunkAnimationHandler().updateChunk((WorldRenderer) (Object) this, chunkInfo2, builtChunk, matrixStack);
    }

    @Inject(
            method = "setupTerrain",
            at = @At(
                    value = "INVOKE",
//                    ordinal = 1,
//                    args = "log=true",
                    target = "Lit/unimi/dsi/fastutil/objects/ObjectList;add(Ljava/lang/Object;)Z"),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void chunkBecomesVisible(Camera camera, Frustum frustum, boolean hasForcedFrustum, int frame, boolean spectator,
                                     CallbackInfo ci, Vec3d vec3d, BlockPos blockPos, ChunkBuilder.BuiltChunk builtChunk, int i,
                                     BlockPos blockPos2, float g, float h, Queue queue, boolean bl, WorldRenderer.ChunkInfo chunkInfo,
                                     ChunkBuilder.BuiltChunk builtChunk3, Direction direction) {
//        if (!Arrays.asList(chunks.chunks).contains(builtChunk3)) {
//            System.out.println("Chunk became visible, adding to queue.");
//            SmoothChunksClient.get().getChunkAnimationHandler().addChunk(builtChunk3);
//        }
    }
}
