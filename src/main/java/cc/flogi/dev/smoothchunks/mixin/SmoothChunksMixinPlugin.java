package cc.flogi.dev.smoothchunks.mixin;

import cc.flogi.dev.smoothchunks.util.UtilCompatibility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

/**
 * @author Caden Kriese (flogic)
 *
 * Created on 11/26/2020
 */
@SuppressWarnings("unused")
public class SmoothChunksMixinPlugin implements IMixinConfigPlugin {
    private static final String MIXIN_PACKAGE_ROOT = "cc.flogi.dev.smoothchunks.mixin.";
    private final Logger logger = LogManager.getLogger("SmoothChunks");

    @Override public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!mixinClassName.startsWith(MIXIN_PACKAGE_ROOT)) {
            this.logger.error("Expected mixin '{}' to start with package root '{}', treating as foreign and " +
                    "disabling!", mixinClassName, MIXIN_PACKAGE_ROOT);

            return false;
        }

        String mixin = mixinClassName.substring(MIXIN_PACKAGE_ROOT.length());

        if (UtilCompatibility.isSodiumInstalled() && mixin.startsWith("vanilla"))
            return false;
         else return UtilCompatibility.isSodiumInstalled() || !mixin.startsWith("sodium");
    }

    /*
     * Not used but required to complete implementation
     */

    @Override public void onLoad(String mixinPackage) {}
    @Override public String getRefMapperConfig() { return null; }
    @Override public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}
    @Override public List<String> getMixins() { return null; }
    @Override public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
    @Override public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
