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
    //TODO use localization for comment strings.
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
    public int getTranslationAmount() {
        return translationAmount / 5;
    }
}