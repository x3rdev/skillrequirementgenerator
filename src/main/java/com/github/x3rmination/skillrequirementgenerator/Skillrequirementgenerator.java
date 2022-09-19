package com.github.x3rmination.skillrequirementgenerator;

import floris0106.rereskillablerereforked.common.Config;
import floris0106.rereskillablerereforked.common.skills.Requirement;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;
import java.util.Map;

@Mod("skillrequirementgenerator")
public class Skillrequirementgenerator {

    public Skillrequirementgenerator() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void setRequirement(Config config, ResourceLocation resourceLocation, Requirement[] requirements) {
        try {
            Field field = config.getClass().getDeclaredField("skillLocks");
            field.setAccessible(true);
            Map<ResourceLocation, Requirement[]> map = (Map<ResourceLocation, Requirement[]>) field.get(config);
            if(map.isEmpty()) {
                map.put(resourceLocation, requirements);
            }
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
