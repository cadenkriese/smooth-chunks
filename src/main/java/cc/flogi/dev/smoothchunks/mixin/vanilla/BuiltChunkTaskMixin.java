package cc.flogi.dev.smoothchunks.mixin.vanilla;

import cc.flogi.dev.smoothchunks.access.BuiltChunkTaskAccess;
import net.minecraft.client.render.chunk.ChunkBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 10/07/2020
 */
@Mixin(ChunkBuilder.BuiltChunk.Task.class)
public abstract class BuiltChunkTaskMixin implements BuiltChunkTaskAccess {
    @SuppressWarnings("ShadowTarget") @Shadow private ChunkBuilder.BuiltChunk field_20837;

    public ChunkBuilder.BuiltChunk getParentClass() {
        return field_20837;
    }
}
