package com.github.x3rmination.skillrequirementgenerator.mixin;

import majik.rereskillable.Configuration;
import majik.rereskillable.common.skills.Requirement;
import majik.rereskillable.common.skills.Skill;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Objects;

@Mixin(Configuration.class)
public class ReSkillableConfigMixin {

    @Shadow(remap = false) @Final private static Map<String, Requirement[]> skillLocks;

//    @Inject(method = "getRequirements", at = @At("TAIL"), remap = false)
//    private static void getRequirementsMixin(ResourceLocation key, CallbackInfoReturnable<Requirement[]> cir) {
//        System.out.println("REQUIREMENTS ______________________________");
//        ForgeRegistries.ITEMS.getEntries().stream().forEach(entry -> {
//            String registryName = entry.getValue().getRegistryName().toString();
//            if(registryName.equals("minecraft:golden_apple")) {
//                Requirement[] requirements = skillLocks.get(registryName);
//                Requirement requirement = new Requirement(Skill.ATTACK, 1);
//                if(!Arrays.stream(requirements).collect(Collectors.toList()).contains(requirement)) {
//                    Requirement[] modReqs = new Requirement[1];
//                    modReqs[0] = requirement;
//                    Requirement[] finalReqsStream = Stream.concat(Arrays.stream(modReqs), Arrays.stream(requirements)).toArray(Requirement[]::new);
//                    skillLocks.remove(registryName);
//                    skillLocks.put(registryName, finalReqsStream);
//                }
//            }
//        });
//    }
    @Inject(method = "load", at = @At("HEAD"), remap = false)
    private static void loadMixin(CallbackInfo ci) {
        ForgeRegistries.ITEMS.getEntries().forEach(entry -> {
            String registryName = Objects.requireNonNull(entry.getValue().getRegistryName()).toString();
            long damage = Math.round(entry.getValue().getDefaultAttributeModifiers(EquipmentSlotType.MAINHAND).get(Attributes.ATTACK_DAMAGE).stream().mapToDouble(AttributeModifier::getAmount).sum());
            if(damage > 0) {
                int level = (int) Math.min((damage - 5) * 5, 32);
                Requirement[] requirements = new Requirement[1];
                requirements[0] = new Requirement(Skill.ATTACK, level);
                skillLocks.put(registryName, requirements);
            }
        });
    }
}
