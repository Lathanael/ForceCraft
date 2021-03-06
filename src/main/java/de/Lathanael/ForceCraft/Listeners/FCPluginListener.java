/*************************************************************************
 * Copyright (C) 2011-2012 Philippe Leipold
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

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import de.Lathanael.ForceCraft.Commands.PermissionsHandler;
import de.Lathanael.ForceCraft.bukkit.ForcePlugin;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class FCPluginListener implements Listener {

	public void hook() {
		ForcePlugin instance = ForcePlugin.getInstance();
		if (!instance.config.getBoolean("IgnorePermissionBridge")) {
			if (instance.getServer().getPluginManager().getPlugin("Permissions").isEnabled()) {
				ForcePlugin.log.info("Found Permission 2.x/3.x, hooking in!");
				PermissionsHandler.setYetiPerm(instance.getServer().getPluginManager().getPlugin("Permissions"));
			}
		}

		Plugin plugin = instance.getServer().getPluginManager().getPlugin("PermissionsEx");
		if (plugin != null)
			if (PermissionsHandler.setPermEx(instance.getServer().getPluginManager().getPlugin("PermissionsEx")))
				ForcePlugin.log.info("Found PermissionsEx, hooking in!");
		plugin = instance.getServer().getPluginManager().getPlugin("bPermissions");
		if (plugin != null)
			if (PermissionsHandler.setbPerm())
				ForcePlugin.log.info("Found bPermissions, hooking in!");
		plugin = instance.getServer().getPluginManager().getPlugin("PermissionsBukkit");
		if (plugin != null)
			if (PermissionsHandler.setPermBukkit())
				ForcePlugin.log.info("Found PermissionsBukkit, hooking in!");
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void onPluginEnable(PluginEnableEvent event) {
		ForcePlugin instance = ForcePlugin.getInstance();
		if (!instance.config.getBoolean("IgnorePermissionBridge")) {
			if (event.getPlugin().getDescription().getName().equalsIgnoreCase("Permissions")) {
				ForcePlugin.log.info("Found Permission 2.x/3.x, hooking in!");
				PermissionsHandler.setYetiPerm(event.getPlugin());
			}
		}

		if (event.getPlugin().getDescription().getName().equalsIgnoreCase("PermissionsEx"))
			if (PermissionsHandler.setPermEx(event.getPlugin()))
				ForcePlugin.log.info("Found PermissionsEx, hooking in!");

		if (event.getPlugin().getDescription().getName().equalsIgnoreCase("bPermissions"))
			if (PermissionsHandler.setbPerm())
				ForcePlugin.log.info("Found bPermissions, hooking in!");

		if (event.getPlugin().getDescription().getName().equalsIgnoreCase("PermissionsBukkit"))
			if (PermissionsHandler.setPermBukkit())
				ForcePlugin.log.info("Found PermissionsBukkit, hooking in!");
	}

	@EventHandler (priority = EventPriority.NORMAL)
	public void onPluginDisable(PluginDisableEvent event) {
		PermissionsHandler.ressetPerm(event.getPlugin().getDescription().getName());
	}
}
