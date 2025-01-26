/**
 * ----------------------------------------------------------------------------- *
 *                     Copyright (c) by FeatherToolkit 2025                      *
 * ----------------------------------------------------------------------------- *
 * @license https://github.com/TheAncientOwl/feather-toolkit/blob/main/LICENSE
 *
 * @file FeatherToolkitDependencyFactory.java
 * @author Alexandru Delegeanu
 * @version 0.2
 * @description Dependency factory for feather toolkit base modules
 */

package dev.defaultybuf.feather.toolkit.testing;

import dev.defaultybuf.feather.toolkit.core.modules.language.components.LanguageManager;
import dev.defaultybuf.feather.toolkit.core.modules.language.interfaces.ILanguage;
import dev.defaultybuf.feather.toolkit.core.modules.reload.components.ReloadModule;
import dev.defaultybuf.feather.toolkit.core.modules.reload.interfaces.IReloadModule;
import dev.defaultybuf.feather.toolkit.testing.annotations.DependencyFactory;

public class FeatherToolkitDependencyFactory {

    @DependencyFactory(of = ILanguage.class)
    public static final DependencyHelper<LanguageManager> getLanguageFactory() {
        return new DependencyHelper<LanguageManager>(
                LanguageManager.class,
                ILanguage.class,
                "LanguageManager",
                "language");
    }

    @DependencyFactory(of = IReloadModule.class)
    public static final DependencyHelper<ReloadModule> getReloadFactory() {
        return new DependencyHelper<ReloadModule>(
                ReloadModule.class,
                IReloadModule.class,
                "ReloadModule",
                "reload");
    }

}
