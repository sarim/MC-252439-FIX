package me.sarim;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

import static net.minecraft.command.argument.EntityArgumentType.entity;
import static net.minecraft.command.argument.EntityArgumentType.getEntity;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FixMod implements DedicatedServerModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("mc-252439-fix");

	@Override
	public void onInitializeServer() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			var brain = literal("villagerBrain")
					.then(argument("entity", entity())
							.then(literal("fix").executes(context -> {
								Entity target = EntityArgumentType.getEntity(context, "entity");
								if (target instanceof VillagerEntity) {
									((VillagerEntity) target).reinitializeBrain(context.getSource().getWorld());
									context.getSource().sendFeedback(
											target.getDisplayName().copy().append("'s Brain Fixed"), true);
								} else {
									context.getSource()
											.sendError(target.getDisplayName().copy().append("not a Villager"));
								}

								return 0;
							})));

			dispatcher.register(brain);
		});

		LOGGER.info("======================== MC-252439 Fix villager brain initialized ========================");
	}
}
