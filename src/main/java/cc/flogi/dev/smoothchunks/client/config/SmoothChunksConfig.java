package cc.flogi.dev.smoothchunks.client.config;

import lombok.Getter;
import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 09/28/2020
 */
@Config(name = "smooth-chunks") @Config.Gui.Background("minecraft:textures/block/stone.png") @Getter
public class SmoothChunksConfig implements ConfigData {
    @Comment("Duration of the animation in seconds.")
    @ConfigEntry.BoundedDiscrete(min = 0, max=10)
    int duration = 1;

    @Comment("Type of animation for loading chunks.")
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    LoadAnimation loadAnimation = LoadAnimation.DOWNWARD;

    @Comment("Disable animating chunks close to you")
    boolean disableNearby = false;
}