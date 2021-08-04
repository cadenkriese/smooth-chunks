package cc.flogi.dev.smoothchunks.mixin;

import cc.flogi.dev.smoothchunks.IMixinShader;
import com.mojang.blaze3d.shaders.Uniform;
import net.minecraft.client.renderer.ShaderInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ShaderInstance.class)
public class MixinShader implements IMixinShader {
    @Unique public Uniform smoothChunkLifetimeUniform;

    //TODO add chunk lifetime uniform
    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/resource/ResourceFactory;Ljava/lang/String;Lnet/minecraft/client/render/VertexFormat;)V")
    public void init(CallbackInfo info) {
        smoothChunkLifetimeUniform = ((ShaderInstance) (Object) this).getUniform("SmoothChunkLifetime");
    }

    public Uniform getSmoothChunkLifetimeUniform() {
        return smoothChunkLifetimeUniform;
    }
}
