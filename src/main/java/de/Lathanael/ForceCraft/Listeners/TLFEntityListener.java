/*************************************************************************
 * Copyright (C) 2011  Philippe Leipold
 *
 * This file is part of ForceCraft.
 *
 * ForceCraft is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ForceCraft is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ForceCraft. If not, see <http://www.gnu.org/licenses/>.
 *
 **************************************************************************/

package de.Lathanael.ForceCraft.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.ProjectileHitEvent;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.Utils.PlayerPowerStates;
import de.Lathanael.ForceCraft.bukkit.ForcePlugin;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class TLFEntityListener extends EntityListener{

	public void onEntityDamageEvent(EntityDamageEvent event) {
		if (event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent newEvent = (EntityDamageByEntityEvent)event;
			Entity attacker = newEvent.getDamager();
			if (!(attacker instanceof Player))
				return;
			ForcePlayer fPlayer = PlayerHandler.getInstance().getPlayer(((Player) attacker).getName());
			if (fPlayer == null)
				return;
			if (fPlayer.hasPowerState(PlayerPowerStates.RAGE)) {
				newEvent.setDamage(newEvent.getDamage()*2);
				return;
			}
		}
		if (event.getCause().equals(DamageCause.LIGHTNING)) {
			ForcePlayer fPlayer = null;
			if (event.getEntity() instanceof Player)
				fPlayer = PlayerHandler.getInstance().getPlayer(((Player) event.getEntity()).getName());
			if (fPlayer != null && fPlayer.hasPowerState(PlayerPowerStates.SHOCKED)) {
				event.setDamage(0);
				return;
			}
			if ((event.getEntity() instanceof LivingEntity)
					&& ForcePlugin.containsStrokedEntity(event.getEntity().getUniqueId()))
				event.setDamage(0);

		}
	}

	public void onProjectileHit(ProjectileHitEvent event) {
		Entity attacked = event.getEntity();
		if (!(attacked instanceof Player))
			return;
		Player damaged = (Player) attacked;
		ForcePlayer fPlayer = PlayerHandler.getInstance().getPlayer(damaged.getName());
		if (fPlayer == null)
			return;
		if (fPlayer.hasPowerState(PlayerPowerStates.SHIELD)) {
			double shieldDmgRed = ForcePlugin.getInstance().ranksInfo.
								getDouble("Shield." + String.valueOf(fPlayer.getRank()), 1);
			damaged.setLastDamage((int) (damaged.getLastDamage()*shieldDmgRed));
		}
	}
}
