package com.github.x3rmination.skillrequirementgenerator;

import majik.rereskillable.common.skills.Requirement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("skillrequirementgenerator")
public class Skillrequirementgenerator {

    public static final Logger LOGGER = LogManager.getLogger();

    public Skillrequirementgenerator() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void printStuff(Map<String, Requirement[]> map) {
        LOGGER.info(map.keySet());
    }
}
