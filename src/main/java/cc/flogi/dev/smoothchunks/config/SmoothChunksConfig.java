package cc.flogi.dev.smoothchunks.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 09/28/2020
 */
@Config(name = "smooth-chunks") @Config.Gui.Background("minecraft:textures/block/stone.png")
public class SmoothChunksConfig implements ConfigData {
    //TODO use localization for comment strings. (Somehow, not super straightforward bc annotations need const value)
    @Comment("Duration of the animation in seconds.")
    double duration = 1;

    @Comment("The amount the chunk moves to get to its final position.")
    @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
    int translationAmount = 5;

    @Comment("Type of animation for loading chunks.")
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    LoadAnimation loadAnimation = LoadAnimation.UPWARD;

    @Comment("Disable animating chunks close to you")
    boolean disableNearby = true;

    /*
     * Custom Getters
     */

    /**
     * Gets the config value for translation amount, translating config value to usable value.
     *
     * @return The translation amount as an int 1 to 10 where higher = more translation.
     */
    public double getTranslationAmount() {
        return (double) translationAmount / 5d;
    }
}