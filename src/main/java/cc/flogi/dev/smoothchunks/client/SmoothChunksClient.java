package cc.flogi.dev.smoothchunks.client;

import cc.flogi.dev.smoothchunks.client.config.SmoothChunksConfig;
import lombok.Getter;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 09/21/2020
 */
@Environment(EnvType.CLIENT) public class SmoothChunksClient implements ClientModInitializer {
    private static SmoothChunksClient instance;
    @Getter private SmoothChunksConfig config;

    public static SmoothChunksClient get() {return instance;}

    @Override public void onInitializeClient() {
        instance = this;

        AutoConfig.register(SmoothChunksConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(SmoothChunksConfig.class).getConfig();
    }
}
