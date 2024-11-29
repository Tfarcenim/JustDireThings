package com.direwolf20.justdirethings.util;

import com.direwolf20.justdirethings.setup.Config;
import java.util.List;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

public class ConfigValueComponentProcessor implements IComponentProcessor {
    private String rawText;
    private List<String> options;

    @Override
    public void setup(final Level level, final IVariableProvider iVariableProvider) {
        this.rawText = iVariableProvider.get("text").asString();
        this.options = iVariableProvider.get("config_options")
            .asList()
            .stream()
            .map(IVariable::asString)
            .toList();
    }

    @Override
    public IVariable process(final Level level, final String key) {
        if (!key.equals("text")) {
            return null;
        }

        String result = this.rawText;

        for (final String option : this.options) {
            final Object value = ((ForgeConfigSpec.ConfigValue<?>) Config.COMMON_CONFIG.getValues().get(option)).get();
            result = result.replace("#" + option + "#", String.valueOf(value));
        }

        return IVariable.wrap(result);
    }
}
