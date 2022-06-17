package me.sarim.mixin;

import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import me.sarim.FixMod;
@Mixin(ZombieVillagerEntity.class)
public class FixMixin {
	@Inject(method = "finishConversion(Lnet/minecraft/server/world/ServerWorld;)V", at=@At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/VillagerEntity;setExperience(I)V"), locals = LocalCapture.CAPTURE_FAILHARD)
	private void fixVillagerBrainAfterConversion(ServerWorld world, CallbackInfo ci, VillagerEntity villagerEntity) {
		FixMod.LOGGER.info("======================== Fixing villager brain ========================");
		villagerEntity.reinitializeBrain(world);
	}
}
