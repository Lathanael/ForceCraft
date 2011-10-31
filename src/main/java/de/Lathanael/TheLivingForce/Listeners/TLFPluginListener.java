/*************************************************************************
 * Copyright (C) 2011  Philippe Leipold
 *
 * This file is part of TheLivingForce.
 *
 * TheLivingForce is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TheLivingForce is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TheLivingForce. If not, see <http://www.gnu.org/licenses/>.
 *
 **************************************************************************/

package de.Lathanael.TheLivingForce.Listeners;

import org.bukkit.event.server.ServerListener;

import de.Lathanael.TheLivingForce.Commands.PermissionsHandler;
import de.Lathanael.TheLivingForce.bukkit.ForcePlugin;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class TLFPluginListener extends ServerListener {

	public void hook() {
		ForcePlugin instance = ForcePlugin.getInstance();
		if (!instance.config.getBoolean("IgnorePermissionBridge")) {
			if (instance.getServer().getPluginManager().getPlugin("Permissions").isEnabled()) {
				ForcePlugin.log.info("[TheLivingForce] Found Permission 2.x/3.x, hooking in!");
				PermissionsHandler.setYetiPerm(instance.getServer().getPluginManager().getPlugin("Permissions"));
			}
		}
		if (instance.getServer().getPluginManager().getPlugin("Permissions").isEnabled()) {
				return;
		}
	}
}
