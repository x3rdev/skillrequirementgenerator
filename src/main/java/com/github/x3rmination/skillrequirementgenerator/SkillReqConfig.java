package com.github.x3rmination.skillrequirementgenerator;

import net.minecraftforge.common.ForgeConfigSpec;

public class SkillReqConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> max_level;
    public static final ForgeConfigSpec.ConfigValue<Float> damage_divisor;
    public static final ForgeConfigSpec.ConfigValue<Float> damage_power;
    public static final ForgeConfigSpec.ConfigValue<Float> armor_divisor;
    public static final ForgeConfigSpec.ConfigValue<Float> armor_power;


    static {
        BUILDER.push("Skill Requirement Generator Config");

        BUILDER.comment("Formula for skill: s^p / a");
        BUILDER.comment("s: Stat (ex. attack dmg, armor) ");
        BUILDER.comment("p: Power of ... ");
        BUILDER.comment("d: divisor ");

        max_level = BUILDER.comment("Max level to generate (has to be less than or equal to reskillable config)").define("max_level", 32);
        damage_power = BUILDER.comment("Damage power").define("damage_power", 2F);
        damage_divisor = BUILDER.comment("Damage divisor").define("damage_divisor", 2F);
        armor_power = BUILDER.comment("Armor power").define("armor_power", 2F);
        armor_divisor = BUILDER.comment("Armor divisor").define("armor_divisor", 2F);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
