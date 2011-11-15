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

package de.Lathanael.ForceCraft.Utils;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.bukkit.ForcePlugin;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class Scheduler {

	private static Scheduler instance = null;
	private static ForcePlugin plugin;

	public static void initInstance(ForcePlugin newPlugin) {
		if (instance == null)
			instance = new Scheduler();
		plugin = newPlugin;
	}

	public static Scheduler getInstance() {
		return instance;
	}

	/**
	 * Starts a SyncRepeatingTask healing a ForcePlayer 2 times per second for different
	 * amounts(min 1 which equals to half a Heart) depending on their rank.
	 * Task is stopped after 10 seconds!
	 *
	 * @param player - The ForcePlayer object to be healed
	 */
	public void scheduleHealTask(final ForcePlayer player) {
		final int playerRank = player.getRank();
		final int taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(
				plugin,
				new Runnable() {
					public void run() {
						int health = player.getHandler().getHealth();
						int amount = plugin.ranksInfo.getInt("Heal. " + String.valueOf(playerRank), 1);
						if (health >= 20)
							return;
						else
							player.getHandler().setHealth(health + amount);
					}
				}
				, 0 , 10);

		// Stops Force Heal after 10 seconds
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(
				plugin,
				new Runnable() {
					public void run() {
						ForcePlugin.getInstance().getServer().getScheduler().cancelTask(taskID);
					}
				}
				, 200L);
	}

	/**
	 * Starts a SyncRepeatingTask refilling the Players FoodBar 2 times
	 * a second for amounts(min 1 which equals to half a "chickenleg")
	 * depending on their rank. Task is stopped after 10 seconds!
	 *
	 * @param player - The ForcePlayer object to be filled
	 */
	public void scheduleMediationTask(final ForcePlayer player) {
		final int playerRank = player.getRank();
		final int taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(
				plugin,
				new Runnable() {
					public void run() {
						int foodLevel = player.getHandler().getFoodLevel();
						int amount = plugin.ranksInfo.getInt("Heal. " + String.valueOf(playerRank), 1);
						if (foodLevel >= 20)
							return;
						else
							player.getHandler().setFoodLevel(foodLevel + amount);
					}
				}
				, 0 , 10);

		// Stops Force Mediation after 10 seconds
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(
				plugin,
				new Runnable() {
					public void run() {
						ForcePlugin.getInstance().getServer().getScheduler().cancelTask(taskID);
					}
				}
				, 200L);
	}

	/**
	 * Cancels the power Force Rage a player has. It is canceld after a
	 * given time, depending on the players Rank.
	 *
	 * @param player - The ForcePlayer object for whom the Force Rage should be canceled
	 */
	public void scheduleCancelRageTask(final ForcePlayer player) {
		int rank = player.getRank();
		long delay = plugin.ranksInfo.getLong("Rage." + String.valueOf(rank), 200);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
				new Runnable() {
					public void run() {
						player.removePowerState(PlayerPowerStates.RAGE);
					}
				}, delay);
	}

	/**
	 * Cancels the power Force Jump a player has. It is canceld after a
	 * given time, depending on the players Rank.
	 *
	 * @param player - The ForcePlayer object for home the Force Jump should be canceled
	 */
	public void scheduleCancelJumpTask(final ForcePlayer player) {
		int rank = player.getRank();
		long delay = plugin.ranksInfo.getLong("Jump." + String.valueOf(rank), 200);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
				new Runnable() {
					public void run() {
						player.removePowerState(PlayerPowerStates.JUMP);
					}
				}, delay);
	}

	/**
	 * Cancels the power Force Run a player has. It is canceld after a
	 * given time, depending on the players Rank.
	 *
	 * @param player - The ForcePlayer object for whom the Force Run should be canceled
	 */
	public void scheduleCancelRunTask(final ForcePlayer player) {
		int rank = player.getRank();
		long delay = plugin.ranksInfo.getLong("Run." + String.valueOf(rank), 200);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
				new Runnable() {
					public void run() {
						player.removePowerState(PlayerPowerStates.RUN);
					}
				}, delay);
	}

	/**
	 * Cancels the power Force Shield a player has. It is canceld after a
	 * given time, depending on the players Rank.
	 *
	 * @param player - The ForcePlayer object for whom the Force Shield should be canceled
	 */
	public void scheduleCancelShieldTask(final ForcePlayer player) {
		int rank = player.getRank();
		long delay = plugin.ranksInfo.getLong("Shield." + String.valueOf(rank), 200);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
				new Runnable() {
					public void run() {
						player.removePowerState(PlayerPowerStates.SHIELD);
					}
				}, delay);
	}
}