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
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.ProjectileHitEvent;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.Utils.PlayerPowerStates;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class TLFEntityListener extends EntityListener{

	public void onEntityDamageEvent(EntityDamageEvent event) {
		if (!(event instanceof EntityDamageByEntityEvent))
			return;

		EntityDamageByEntityEvent newEvent = (EntityDamageByEntityEvent)event;
		Entity attacker = newEvent.getDamager();
		if (!(attacker instanceof Player))
			return;
		ForcePlayer fPlayer = PlayerHandler.getInstance().getPlayer(((Player) attacker).getName());
		if (fPlayer == null)
			return;
		// TODO: Rage Code
		if (fPlayer.containsPowerState(PlayerPowerStates.RAGE)) {
			newEvent.setDamage(newEvent.getDamage()*2);
		}
	}

	public void onProjectileHit(ProjectileHitEvent event) {
		Entity attacked = event.getEntity();
		if (!(attacked instanceof Player))
			return;
		Player damaged = (Player) attacked;
		ForcePlayer fPlayer = PlayerHandler.getInstance().getPlayer(damaged.getName());
		// TODO: Shield code
		if (fPlayer.containsPowerState(PlayerPowerStates.SHIELD))
			return;

	}
}
