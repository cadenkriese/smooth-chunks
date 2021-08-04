package cc.flogi.dev.smoothchunks.mixin;

import cc.flogi.dev.smoothchunks.ext.IMixinChunkInfo;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.RenderChunkInfo.class)
public class MixinChunkInfo implements IMixinChunkInfo {
    private float creationTime;

    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/client/renderer/chunk/ChunkRenderDispatcher$RenderChunk;Lnet/minecraft/core/Direction;I)V")
    public void init(ChunkRenderDispatcher.RenderChunk chunk, @Nullable Direction direction, int propagationLevel, CallbackInfo info) {
        creationTime = RenderSystem.getShaderGameTime();
    }

    public float getCreationTime() {
        return creationTime;
    }
}
