package com.github.x3rmination.skillrequirementgenerator;

import net.minecraftforge.common.ForgeConfigSpec;

public class SkillReqConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.IntValue max_level;
    public static final ForgeConfigSpec.DoubleValue damage_divisor;
    public static final ForgeConfigSpec.DoubleValue damage_power;
    public static final ForgeConfigSpec.DoubleValue armor_divisor;
    public static final ForgeConfigSpec.DoubleValue armor_power;


    static {
        BUILDER.comment("Formula for skill level: s^a / b", "s: Stat (ex. attack dmg, armor) ", "a: constant ", "b: constant ", "these constants can be defined here:").push("Skill Requirement Generator Config");
        max_level = BUILDER.comment("Max level to generate (has to be less than or equal to reskillable config)").defineInRange("max_level", 32, 0, Integer.MAX_VALUE - 1);
        damage_power = BUILDER.comment("Damage power").defineInRange("damage_power", 2F, 0, Float.MAX_VALUE);
        damage_divisor = BUILDER.comment("Damage divisor").defineInRange("damage_divisor", 2F, 0, Float.MAX_VALUE);
        armor_power = BUILDER.comment("Armor power").defineInRange("armor_power", 2F, 0, Float.MAX_VALUE);
        armor_divisor = BUILDER.comment("Armor divisor").defineInRange("armor_divisor", 2F, 0, Float.MAX_VALUE);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
