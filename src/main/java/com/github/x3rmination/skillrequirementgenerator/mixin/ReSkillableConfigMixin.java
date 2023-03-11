package com.github.x3rmination.skillrequirementgenerator.mixin;

import com.github.x3rmination.skillrequirementgenerator.SkillReqConfig;
import floris0106.rereskillablerereforked.common.Config;
import floris0106.rereskillablerereforked.common.skills.Requirement;
import floris0106.rereskillablerereforked.common.skills.Skill;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(Config.class)
public abstract class ReSkillableConfigMixin {


    @Shadow(remap = false) private static Config config;

    @Inject(method = "load", at = @At(value = "TAIL"), remap = false)
    private static void loadMixin(CallbackInfo ci) {
        ForgeRegistries.ITEMS.getEntries().forEach(entry -> {
            if(!inConfig(entry.getValue().getRegistryName())) {
                ItemStack stack = entry.getValue().getDefaultInstance();

                float damage = getDamage(stack);
                float armor = getArmor(stack);

                if (damage > 0) {
                    int level = getAttackLevel(stack, damage);
                    if (level > 0) {
                        Requirement[] requirements = {new Requirement(Skill.ATTACK, level)};
                        addRequirement(entry.getValue().getRegistryName(), requirements);
                    }
                }

                if (armor > 0) {
                    int level = getDefenseLevel(stack, armor);
                    if (level > 0) {
                        Requirement[] requirements = {new Requirement(Skill.DEFENCE, level)};
                        addRequirement(entry.getValue().getRegistryName(), requirements);
                    }
                }
            }
        });
    }


    private static boolean inConfig(ResourceLocation entry) {
        try {
            return (((Map<ResourceLocation, Requirement[]>) config.getClass().getDeclaredField("skillLocks").get(config)).get(entry) != null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static float getDamage(ItemStack stack) {
        return Math.round(stack.getAttributeModifiers(EquipmentSlotType.MAINHAND).get(Attributes.ATTACK_DAMAGE).stream().mapToDouble(AttributeModifier::getAmount).sum());
    }

    private static float getArmor(ItemStack stack) {
        float armor = 0;
        for (EquipmentSlotType type : EquipmentSlotType.values()) {
            if (type.getType().equals(EquipmentSlotType.Group.ARMOR)) {
                armor = (float) stack.getAttributeModifiers(type).get(Attributes.ARMOR).stream().mapToDouble(AttributeModifier::getAmount).sum();
                if (armor > 0) {
                    break;
                }
            }
        }
        return armor;
    }

    private static void addRequirement(ResourceLocation name, Requirement[] requirements) {
        try {
            ((Map<ResourceLocation, Requirement[]>) config.getClass().getDeclaredField("skillLocks").get(config)).put(name, requirements);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private static int getAttackLevel(ItemStack stack, float damage) {
        int level;
        if(stack.getItem() instanceof TieredItem) {
            TieredItem tieredItem = (TieredItem) stack.getItem();
            level = (int) Math.round(SkillReqConfig.tier_damage_multiplier.get() * (tieredItem.getTier().getAttackDamageBonus() + SkillReqConfig.tier_damage_addend.get()));
        } else {
            level = (int) Math.round(SkillReqConfig.damage_multiplier.get() * Math.log(Math.max(0, damage + SkillReqConfig.damage_addend.get())) + SkillReqConfig.damage_shift.get());
        }
        return Math.min(Config.getMaxLevel(), level);
    }

    private static int getDefenseLevel(ItemStack stack, float armor) {
        int level;
        if(stack.getItem() instanceof ArmorItem ) {
            ArmorItem armorItem = (ArmorItem) stack.getItem();
            level = (int) Math.round(SkillReqConfig.tier_armor_multiplier.get() * (armorItem.getMaterial().getDefenseForSlot(EquipmentSlotType.CHEST) + SkillReqConfig.tier_armor_addend.get()));
        } else {
            level = (int) Math.round(SkillReqConfig.armor_multiplier.get() * Math.log(Math.max(0, armor + SkillReqConfig.armor_addend.get())) + SkillReqConfig.armor_shift.get());
        }
        return Math.min(Config.getMaxLevel(), level);
    }
}
