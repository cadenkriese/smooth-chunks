package cc.flogi.dev.smoothchunks.util;

import net.fabricmc.loader.api.FabricLoader;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 11/26/2020
 */
public final class UtilCompatibility {
    public static boolean isSodiumInstalled() {
        return FabricLoader.getInstance().isModLoaded("sodium");
    }

}
