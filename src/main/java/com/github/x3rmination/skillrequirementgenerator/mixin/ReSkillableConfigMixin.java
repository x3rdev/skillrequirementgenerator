package com.github.x3rmination.skillrequirementgenerator.mixin;

import com.github.x3rmination.skillrequirementgenerator.SkillReqConfig;
import floris0106.rereskillablerereforked.common.Config;
import floris0106.rereskillablerereforked.common.skills.Requirement;
import floris0106.rereskillablerereforked.common.skills.Skill;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Final;
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
                int damage = (int) Math.round(entry.getValue().getAttributeModifiers(EquipmentSlot.MAINHAND, stack).get(Attributes.ATTACK_DAMAGE).stream().mapToDouble(AttributeModifier::getAmount).sum());
                float speed = 4 + (float) entry.getValue().getAttributeModifiers(EquipmentSlot.MAINHAND, stack).get(Attributes.ATTACK_SPEED).stream().mapToDouble(AttributeModifier::getAmount).sum();
                if (damage > 0) {
                    int level = getAttackLevel(damage, speed);
                    if (level > 0) {
                        Requirement[] requirements = new Requirement[1];
                        requirements[0] = new Requirement(Skill.ATTACK, level);
                        try {
                            ((Map<ResourceLocation, Requirement[]>) config.getClass().getDeclaredField("skillLocks").get(config)).put(entry.getValue().getRegistryName(), requirements);
                        } catch (IllegalAccessException | NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                    }
                }
                int armor = 0;
                for (EquipmentSlot type : EquipmentSlot.values()) {
                    if (type.getType().equals(EquipmentSlot.Type.ARMOR)) {
                        armor = (int) Math.round(stack.getAttributeModifiers(type).get(Attributes.ARMOR).stream().mapToDouble(AttributeModifier::getAmount).sum());
                        if (armor > 0) {
                            break;
                        }
                    }
                }
                if (armor > 0) {
                    int level = getDefenseLevel(armor);
                    if (level > 0) {
                        Requirement[] requirements = new Requirement[1];
                        requirements[0] = new Requirement(Skill.DEFENCE, level);
                        try {
                            ((Map<ResourceLocation, Requirement[]>) config.getClass().getDeclaredField("skillLocks").get(config)).put(entry.getValue().getRegistryName(), requirements);
                        } catch (IllegalAccessException | NoSuchFieldException e) {
                            e.printStackTrace();
                        }
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

    private static int getAttackLevel(int damage, float attackSpeed) {
        int level = (int) Math.round(SkillReqConfig.damage_multiplier.get() * Math.log(Math.max(0, damage * (attackSpeed - 0.6) + SkillReqConfig.damage_addend.get())) + SkillReqConfig.damage_shift.get());
        return Math.min(Config.getMaxLevel(), level);
    }

    private static int getDefenseLevel(int armor) {
        int level = (int) Math.round(SkillReqConfig.armor_multiplier.get() * Math.log(Math.max(0, armor + SkillReqConfig.armor_addend.get())) + SkillReqConfig.armor_shift.get());
        return Math.min(Config.getMaxLevel(), level);
    }
}
