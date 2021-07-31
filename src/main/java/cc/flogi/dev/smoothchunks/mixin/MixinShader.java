package cc.flogi.dev.smoothchunks.mixin;

import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.render.Shader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Shader.class)
public class MixinShader {
    @Unique
    public GlUniform smoothChunkLifetimeUniform;

    //TODO add chunk lifetime uniform
    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/resource/ResourceFactory;Ljava/lang/String;Lnet/minecraft/client/render/VertexFormat;)V")
    public void init(CallbackInfo info) {
        smoothChunkLifetimeUniform = ((Shader) (Object) this).getUniform("SmoothChunkLifetime");
    }


}
