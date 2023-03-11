package com.github.x3rmination.skillrequirementgenerator;

import net.minecraftforge.common.ForgeConfigSpec;

public class SkillReqConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.DoubleValue damage_multiplier;
    public static final ForgeConfigSpec.DoubleValue damage_addend;
    public static final ForgeConfigSpec.DoubleValue damage_shift;
    public static final ForgeConfigSpec.DoubleValue armor_multiplier;
    public static final ForgeConfigSpec.DoubleValue armor_addend;
    public static final ForgeConfigSpec.DoubleValue armor_shift;


    static {
        BUILDER.comment("Formula for skill level: a * ln(x * (s-0.6) + b) + c", "x: Stat (ex. attack dmg, armor) ", "s: Attack Speed (when relevant)", "a: constant ", "b: constant ", "c: constant", "these constants can be defined here:", "Note: negative values inside the natural log will be set to 0").push("Skill Requirement Generator Config");
        damage_multiplier = BUILDER.comment("Damage multiplier").defineInRange("damage_multiplier", 14F, 0, Float.MAX_VALUE);
        damage_addend = BUILDER.comment("Damage addend").defineInRange("damage_addend", -3F, -Float.MAX_VALUE, Float.MAX_VALUE);
        damage_shift = BUILDER.comment("Damage shift").defineInRange("damage_shift", -5F, -Float.MAX_VALUE, Float.MAX_VALUE);
        armor_multiplier = BUILDER.comment("Armor multiplier").defineInRange("armor_multiplier", 14F, 0, Float.MAX_VALUE);
        armor_addend = BUILDER.comment("Armor addend").defineInRange("armor_addend", -3F, -Float.MAX_VALUE, Float.MAX_VALUE);
        armor_shift = BUILDER.comment("Armor addend").defineInRange("armor_shift", -5F, -Float.MAX_VALUE, Float.MAX_VALUE);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
