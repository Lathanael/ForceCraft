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

package de.Lathanael.ForceCraft.Listeners;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;

import de.Lathanael.ForceCraft.Commands.PermissionsHandler;
import de.Lathanael.ForceCraft.bukkit.ForcePlugin;

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

		if (instance.getServer().getPluginManager().getPlugin("PermissionsEx").isEnabled())
			if (PermissionsHandler.setPermEx(instance.getServer().getPluginManager().getPlugin("PermissionsEx")))
				ForcePlugin.log.info("[TheLivingForce] Found PermissionsEx, hooking in!");

		if (instance.getServer().getPluginManager().getPlugin("bPermissions").isEnabled())
			if (PermissionsHandler.setPermEx(instance.getServer().getPluginManager().getPlugin("bPermissions")))
				ForcePlugin.log.info("[TheLivingForce] Found bPermissions, hooking in!");

		if (instance.getServer().getPluginManager().getPlugin("PermissionsBukkit").isEnabled())
			if (PermissionsHandler.setPermEx(instance.getServer().getPluginManager().getPlugin("PermissionsBukkit")))
				ForcePlugin.log.info("[TheLivingForce] Found PermissionsBukkit, hooking in!");
	}

	public void onPluginEnable(PluginEnableEvent event) {
		ForcePlugin instance = ForcePlugin.getInstance();
		if (!instance.config.getBoolean("IgnorePermissionBridge")) {
			if (event.getPlugin().getDescription().getName().equalsIgnoreCase("Permissions")) {
				ForcePlugin.log.info("[TheLivingForce] Found Permission 2.x/3.x, hooking in!");
				PermissionsHandler.setYetiPerm(event.getPlugin());
			}
		}

		if (event.getPlugin().getDescription().getName().equalsIgnoreCase("PermissionsEx"))
			if (PermissionsHandler.setPermEx(event.getPlugin()))
				ForcePlugin.log.info("[TheLivingForce] Found PermissionsEx, hooking in!");

		if (event.getPlugin().getDescription().getName().equalsIgnoreCase("bPermissions"))
			if (PermissionsHandler.setPermEx(event.getPlugin()))
				ForcePlugin.log.info("[TheLivingForce] Found bPermissions, hooking in!");

		if (event.getPlugin().getDescription().getName().equalsIgnoreCase("PermissionsBukkit"))
			if (PermissionsHandler.setPermEx(event.getPlugin()))
				ForcePlugin.log.info("[TheLivingForce] Found PermissionsBukkit, hooking in!");
	}

	public void onPluginDisable(PluginDisableEvent event) {
		PermissionsHandler.ressetPerm(event.getPlugin().getDescription().getName());
	}
}
