package cc.flogi.dev.smoothchunks.ext;

import com.mojang.blaze3d.shaders.Uniform;

public interface IMixinShader {
    Uniform getSmoothChunkLifetimeUniform();
}
