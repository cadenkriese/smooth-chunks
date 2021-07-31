package cc.flogi.dev.smoothchunks;

import cc.flogi.dev.smoothchunks.config.SmoothChunksConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 09/21/2020
 */
@Environment(EnvType.CLIENT)
public class SmoothChunks implements ClientModInitializer {
    private static SmoothChunks instance;
    private SmoothChunksConfig config;

    public static SmoothChunks get() {return instance;}

    @Override public void onInitializeClient() {
        instance = this;

        AutoConfig.register(SmoothChunksConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(SmoothChunksConfig.class).getConfig();
    }
}
