package com.github.x3rmination.skillrequirementgenerator;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("skillrequirementgenerator")
public class Skillrequirementgenerator {

    public static final Logger LOGGER = LogManager.getLogger();

    public Skillrequirementgenerator() {
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SkillReqConfig.SPEC, "skillrequirementgenerator-common.toml");
    }
}
