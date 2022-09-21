package com.github.x3rmination.skillrequirementgenerator;

import floris0106.rereskillablerereforked.common.Config;
import floris0106.rereskillablerereforked.common.skills.Requirement;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.lang.reflect.Field;
import java.util.Map;

@Mod("skillrequirementgenerator")
public class Skillrequirementgenerator {

    public Skillrequirementgenerator() {
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SkillReqConfig.SPEC, "skillrequirementgenerator-common.toml");
    }
}
