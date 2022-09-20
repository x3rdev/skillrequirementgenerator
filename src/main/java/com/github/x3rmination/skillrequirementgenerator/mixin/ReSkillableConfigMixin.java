package com.github.x3rmination.skillrequirementgenerator.mixin;

import com.github.x3rmination.skillrequirementgenerator.Skillrequirementgenerator;
import floris0106.rereskillablerereforked.common.Config;
import floris0106.rereskillablerereforked.common.skills.Requirement;
import floris0106.rereskillablerereforked.common.skills.Skill;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(Config.class)
public abstract class ReSkillableConfigMixin {


    @Shadow(remap = false) private static Config config;

    @Inject(method = "load", at = @At(value = "INVOKE", target = "Ljava/util/List;iterator()Ljava/util/Iterator;", shift = At.Shift.AFTER), remap = false)
    private static void loadMixin(CallbackInfo ci) {
        ForgeRegistries.ITEMS.getEntries().forEach(entry -> {
            ItemStack stack = entry.getValue().getDefaultInstance();
            long damage = Math.round(entry.getValue().getAttributeModifiers(EquipmentSlot.MAINHAND, stack).get(Attributes.ATTACK_DAMAGE).stream().mapToDouble(AttributeModifier::getAmount).sum());
            if(damage > 0) {
                int level = (int) Math.min(Math.round((Math.pow(damage, 1.5) / 2F)), 32);
                if(level > 0) {
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
            for(EquipmentSlot type : EquipmentSlot.values()) {
                if (type.getType().equals(EquipmentSlot.Type.ARMOR)) {
                    armor = (int) Math.round(stack.getAttributeModifiers(type).get(Attributes.ARMOR).stream().mapToDouble(AttributeModifier::getAmount).sum());
                    if (armor > 0) {
                        break;
                    }
                }
            }
            if(armor > 0) {
                int level = (int) Math.min(Math.round((Math.pow(armor, 1.5) / 2F)), 32);
                Requirement[] requirements = new Requirement[1];
                requirements[0] = new Requirement(Skill.DEFENCE, level);
                try {
                    ((Map<ResourceLocation, Requirement[]>) config.getClass().getDeclaredField("skillLocks").get(config)).put(entry.getValue().getRegistryName(), requirements);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
