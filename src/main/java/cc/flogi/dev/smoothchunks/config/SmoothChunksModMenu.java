package cc.flogi.dev.smoothchunks.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.Screen;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 09/28/2020
 */
@Environment(EnvType.CLIENT)
public class SmoothChunksModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (ConfigScreenFactory<Screen>) parent -> AutoConfig.getConfigScreen(SmoothChunksConfig.class, parent).get();
    }
}
