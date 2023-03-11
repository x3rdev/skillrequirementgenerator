package com.github.x3rmination.skillrequirementgenerator;

import net.minecraftforge.common.ForgeConfigSpec;

public class SkillReqConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.DoubleValue tier_damage_multiplier;
    public static final ForgeConfigSpec.DoubleValue tier_damage_addend;
    public static final ForgeConfigSpec.DoubleValue tier_armor_multiplier;
    public static final ForgeConfigSpec.DoubleValue tier_armor_addend;
    public static final ForgeConfigSpec.DoubleValue damage_multiplier;
    public static final ForgeConfigSpec.DoubleValue damage_addend;
    public static final ForgeConfigSpec.DoubleValue damage_shift;
    public static final ForgeConfigSpec.DoubleValue armor_multiplier;
    public static final ForgeConfigSpec.DoubleValue armor_addend;
    public static final ForgeConfigSpec.DoubleValue armor_shift;

    static {
        BUILDER.comment("Formula for skill level from tier (preferred):  a * (x + b)", "x: Stat from tier (ex. attack dmg, armor)", "a: multiplier", "b: addend").push("Skill Requirement Generator Config");
        tier_damage_multiplier = BUILDER.comment("Tier damage multiplier (a)").defineInRange("tier_damage_multiplier", 5F, 0, Float.MAX_VALUE);
        tier_damage_addend = BUILDER.comment("Tier damage addend (b)").defineInRange("tier_damage_addend", -1F, -Float.MAX_VALUE, Float.MAX_VALUE);
        tier_armor_multiplier = BUILDER.comment("Tier armor multiplier (a)").defineInRange("tier_armor_multiplier", 5F, 0, Float.MAX_VALUE);
        tier_armor_addend = BUILDER.comment("Tier armor addend (b)").defineInRange("tier_armor_addend", -5F, -Float.MAX_VALUE, Float.MAX_VALUE);


        BUILDER.comment("Formula for skill level: a * ln(x + b) + c", "x: Stat (ex. attack dmg, armor) ", "a: multiplier ", "b: addend ", "c: shift", "these constants can be defined here:", "Note: negative values inside the natural log will be set to 0");
        damage_multiplier = BUILDER.comment("Damage multiplier (a)").defineInRange("damage_multiplier", 14F, 0, Float.MAX_VALUE);
        damage_addend = BUILDER.comment("Damage addend (b)").defineInRange("damage_addend", -3F, -Float.MAX_VALUE, Float.MAX_VALUE);
        damage_shift = BUILDER.comment("Damage shift (c)").defineInRange("damage_shift", -5F, -Float.MAX_VALUE, Float.MAX_VALUE);
        armor_multiplier = BUILDER.comment("Armor multiplier (a)").defineInRange("armor_multiplier", 14F, 0, Float.MAX_VALUE);
        armor_addend = BUILDER.comment("Armor addend (b)").defineInRange("armor_addend", -3F, -Float.MAX_VALUE, Float.MAX_VALUE);
        armor_shift = BUILDER.comment("Armor addend (c)").defineInRange("armor_shift", -5F, -Float.MAX_VALUE, Float.MAX_VALUE);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
