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

package de.Lathanael.TheLivingForce.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import de.Lathanael.TheLivingForce.Utils.AbstractPermission;
import de.Lathanael.TheLivingForce.Utils.BukkitPermOrOP;
import de.Lathanael.TheLivingForce.Utils.PermissionEx;
import de.Lathanael.TheLivingForce.Utils.YetiPermission;
import de.Lathanael.TheLivingForce.bukkit.ForcePlugin;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 * Some code-ideas in here are from AdminCmd!
 */
public class PermissionsHandler {

	private static PermissionsHandler permHandler = null;
	public static boolean yetiPerm = false;
	public static boolean permEx = false;
	public static boolean bPerm = false;
	public static boolean permBukkit = false;
	public static AbstractPermission perm;

	private PermissionsHandler() {
		perm = new BukkitPermOrOP();
	}

	public static void setInstance() {
		if (permHandler == null)
			permHandler = new PermissionsHandler();
	}

	public PermissionsHandler getInstance() {
		return permHandler;
	}

	public boolean hasPerm(CommandSender player, String permNode) {
		return perm.hasPerm(player, permNode);
	}

	public static void setYetiPerm(Plugin yetiPermPlugin) {
		yetiPerm = true;
		perm = new YetiPermission(yetiPermPlugin);
	}

	public static boolean setbPerm() {
		if (!yetiPerm) {
			bPerm = true;
			perm = new BukkitPermOrOP();
			return true;
		}
		return false;
	}

	public static boolean setPermEx(Plugin permExPlugin) {
		if (!bPerm && !yetiPerm) {
			permEx = true;
			perm = new PermissionEx(permExPlugin);
			return true;
		}
		return false;
	}

	public static boolean setPermBukkit() {
		if (!bPerm && !permEx && !yetiPerm) {
			permBukkit = true;
			perm = new BukkitPermOrOP();
			return true;
		}
		return false;
	}

	public static void ressetPerm(String name) {
		if (name.equalsIgnoreCase("Permissions")) {
			yetiPerm = false;
			ForcePlugin.log.info("[TheLivingForce] Permissions 2.x/3.x disabled, using OP system!");
		} else if (name.equalsIgnoreCase("bPermissions")) {
			bPerm = false;
			ForcePlugin.log.info("[TheLivingForce] bPermissions disabled, using OP system!");
		} else if (name.equalsIgnoreCase("PermissionsEx")) {
			permEx = false;
			ForcePlugin.log.info("[TheLivingForce] PermissionsEx disabled, using OP system!");
		} else if (name.equalsIgnoreCase("PermissionsBukkit")) {
			permBukkit = false;
			ForcePlugin.log.info("[TheLivingForce] PermissionsBukkit disabled, using OP system!");
		}
		perm = new BukkitPermOrOP();
	}
}
