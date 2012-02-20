/*************************************************************************
 * Copyright (C) 2011-2012 Philippe Leipold
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

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.Lathanael.ForceCraft.Events.PowerUsedEvent;
import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.bukkit.ForcePlugin;
import de.Lathanael.ForceCraft.bukkit.PromoteValues;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class FCEventListener implements Listener{

	@EventHandler
	public void onPowerUsed(PowerUsedEvent event) {
		if (!ForcePlugin.autoPromote) {
			event.setCancelled(true);
			return;
		}
		ForcePlayer p = event.getPlayer();
		int rank = p.getRank();
		boolean promote = false;
		HashMap<String, Integer> amounts = p.getPowerAmounts();
		for (Map.Entry<String, Integer> entries : amounts.entrySet()) {
			int value = PromoteValues.getValue(entries.getKey() + "." + String.valueOf(rank));
			if (value <= 0)
				continue;
			else if (value <= entries.getValue())
				promote = true;
			else if (value > entries.getValue())
				promote = false;
		}
		if (promote) {
			p.incRank();
		}
	}
}
