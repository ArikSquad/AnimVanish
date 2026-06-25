package eu.mikart.animvanish.commands;

import eu.mikart.animvanish.IAnimVanish;
import eu.mikart.animvanish.effects.BareEffect;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

import java.util.Collection;

public class EffectSuggestions implements SuggestionProvider<BukkitCommandActor> {
		@Override
		public Collection<String> getSuggestions(revxrsal.commands.node.ExecutionContext<BukkitCommandActor> context) {
			IAnimVanish plugin = context.lamp().dependency(IAnimVanish.class);
			return plugin.getEffectManager().getEffects().stream()
					.filter(effect -> effect.canRun() && context.actor().sender().hasPermission("animvanish.invis." + effect.getName()))
					.map(BareEffect::getName)
					.toList();
		}
	}