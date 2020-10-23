package cc.flogi.dev.smoothchunks.mixin;

import cc.flogi.dev.smoothchunks.client.SmoothChunksClient;
import net.minecraft.client.render.chunk.ChunkBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 10/07/2020
 */
@Mixin(ChunkBuilder.BuiltChunk.class)
public abstract class ChunkBuilderMixin {
    @Inject(
            method = "setOrigin",
            at = @At(
                    value = "INVOKE",
//                    args = "log=true",
                    target = "Lnet/minecraft/client/render/chunk/ChunkBuilder$BuiltChunk;clear()V"
            )
    )
    public void onSetOrigin(int x, int y, int z, CallbackInfo info) {
        ChunkBuilder.BuiltChunk chunk = (ChunkBuilder.BuiltChunk) (Object) this;

//        System.out.println("Running animation on chunk @ (" + x + ", " + y + ", " + z + ").");
//        String xBehind = chunk.getOrigin().getX() < Objects.requireNonNull(MinecraftClient.getInstance().getCameraEntity()).getBlockPos().getX() ? "behind" : "in-front of";
//        String zBehind = chunk.getOrigin().getZ() < Objects.requireNonNull(MinecraftClient.getInstance().getCameraEntity()).getBlockPos().getZ() ? "behind" : "in-front of";

//        System.out.printf("Animating chunk %s you on the x-axis and %s you on the z-axis.\n", xBehind, zBehind);
        SmoothChunksClient.get().getChunkAnimationHandler().addChunk(chunk);
    }
}
