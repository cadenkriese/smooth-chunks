package cc.flogi.dev.smoothchunks.mixin;

import net.minecraft.client.render.chunk.ChunkBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 10/07/2020
 */
@Mixin(ChunkBuilder.BuiltChunk.RebuildTask.class)
public abstract class BuiltChunkTaskMixin {
    @SuppressWarnings("ShadowTarget") @Shadow private ChunkBuilder.BuiltChunk field_20839;

    public ChunkBuilder.BuiltChunk getParentClass() {
        return field_20839;
    }
}
