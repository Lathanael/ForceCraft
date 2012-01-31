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

package de.Lathanael.ForceCraft.Utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.player.SpoutPlayer;

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
	 * Starts a SyncedRepeatingTask healing a ForcePlayer 2 times per second for different
	 * amounts(min. 1 which equals to half a Heart) depending on their rank.
	 * Task is stopped after 10 seconds!
	 *
	 * @param player - The ForcePlayer object to be healed
	 */
	public void scheduleHealTask(final ForcePlayer player, final ForcePlayer target) {
		final int playerRank = player.getSkillRank("Heal");
		if (playerRank == 0)
			return;
		final int taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(
				plugin,
				new Runnable() {
					public void run() {
						int health = target.getHandler().getHealth();
						int amount = plugin.ranksInfo.getInt("Heal. " + String.valueOf(playerRank), 1);
						if (health >= 20)
							return;
						else
							target.getHandler().setHealth(health + amount);
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
	 * Starts a SyncedRepeatingTask refilling the Players FoodBar 2 times
	 * a second for amounts(min. 1 which equals to half a "chickenleg")
	 * depending on their rank. Task is stopped after 10 seconds!
	 *
	 * @param player - The ForcePlayer object to be filled
	 */
	public void scheduleMediationTask(final ForcePlayer player) {
		final int playerRank = player.getSkillRank("Mediation");
		if (playerRank == 0)
			return;
		final int taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(
				plugin,
				new Runnable() {
					public void run() {
						int foodLevel = player.getHandler().getFoodLevel();
						int amount = plugin.ranksInfo.getInt("Mediation. " + String.valueOf(playerRank), 1);
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
	 * Cancels the power Force Rage a player has. It is cancelled after a
	 * given time, depending on the players Rank.
	 *
	 * @param player - The ForcePlayer object for whom the Force Rage should be cancelled
	 */
	public void scheduleCancelRageTask(final ForcePlayer player) {
		int playerRank = player.getSkillRank("Rage");
		if (playerRank == 0)
			return;
		long delay = plugin.ranksInfo.getLong("Rage." + String.valueOf(playerRank), 200);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
				new Runnable() {
					public void run() {
						player.removePowerState(PlayerPowerStates.RAGE);
					}
				}, delay);
	}

	/**
	 * Cancels the power Force Jump a player has. It is cancelled after a
	 * given time, depending on the players Rank.
	 *
	 * @param player - The ForcePlayer object for home the Force Jump should be cancelled
	 */
	public void scheduleCancelJumpTask(final ForcePlayer player) {
		int playerRank = player.getSkillRank("Jump");
		if (playerRank == 0)
			return;
		long delay = plugin.ranksInfo.getLong("Jump." + String.valueOf(playerRank), 200);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
				new Runnable() {
					public void run() {
						player.removePowerState(PlayerPowerStates.JUMP);
						((SpoutPlayer) player).setJumpingMultiplier(1);
					}
				}, delay);
	}

	/**
	 * Cancels the power Force Run a player has. It is cancelled after a
	 * given time, depending on the players Rank.
	 *
	 * @param player - The ForcePlayer object for whom the Force Run should be cancelled
	 */
	public void scheduleCancelRunTask(final ForcePlayer player) {
		int playerRank = player.getSkillRank("Run");
		if (playerRank == 0)
			return;
		long delay = plugin.ranksInfo.getLong("Run." + String.valueOf(playerRank), 200);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
				new Runnable() {
					public void run() {
						player.removePowerState(PlayerPowerStates.RUN);
						((SpoutPlayer) player).setWalkingMultiplier(1);
					}
				}, delay);
	}

	/**
	 * Cancels the power Force Shield a player has. It is cancel after a
	 * given time, depending on the players Rank.
	 *
	 * @param player - The ForcePlayer object for whom the Force Shield should be cancelled
	 */
	public void scheduleCancelShieldTask(final ForcePlayer player) {
		int playerRank = player.getSkillRank("Shield");
		if (playerRank == 0)
			return;
		long delay = plugin.ranksInfo.getLong("Shield." + String.valueOf(playerRank), 200);
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
				new Runnable() {
					public void run() {
						player.removePowerState(PlayerPowerStates.SHIELD);
					}
				}, delay);
	}

	/**
	 * Chokes a given target. Force is cancel after a xx seconds, depending on
	 * the players Skill rank.
	 *
	 * @param player - The ForcePlayer object for whom the Force Shield should be cancelled
	 */
	public void scheduleChokeTask(final ForcePlayer player, final ForcePlayer target) {
		int playerRank = player.getSkillRank("Choke");
		if (playerRank == 0)
			return;
		long delay = plugin.ranksInfo.getLong("Choke." + String.valueOf(playerRank), 200);
		final int taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin,
				new Runnable() {
					public void run() {
						SpoutPlayer pTarget = (SpoutPlayer) target.getHandler();
						if (pTarget == null)
							return;
						pTarget.setGravityMultiplier(0);
						int health = pTarget.getHealth();
						if (health - 1 < 0)
							pTarget.setHealth(0);
						else
							pTarget.setHealth(health - 1);
					}
				}, 10, 20);

		// Cancel the Choke task after xx seconds(delay)
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
				new Runnable() {
						public void run() {
							plugin.getServer().getScheduler().cancelTask(taskID);
							((SpoutPlayer) target.getHandler()).setGravityMultiplier(1);
						}
				}, delay);
	}

	/**
	 * Chokes a given target. Force is cancel after a xx seconds, depending on
	 * the players Skill rank.
	 *
	 * @param player - The ForcePlayer object for whom the Force Shield should be cancelled
	 */
	public void scheduleChokeLivingEntityTask(final ForcePlayer player, final LivingEntity target) {
		int playerRank = player.getSkillRank("Choke");
		if (playerRank == 0)
			return;
		long delay = plugin.ranksInfo.getLong("Choke." + String.valueOf(playerRank), 200);
		target.setVelocity(new Vector(0, 0, 0));
		final Location flying = target.getLocation();
		final int taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin,
				new Runnable() {
					public void run() {
						if (!target.getLocation().equals(flying))
							target.teleport(flying);
						int health = target.getHealth();
						if (health - 1 < 0)
							target.setHealth(0);
						else
							target.setHealth(health - 1);
					}
				}, 10, 20);

		// Cancel the Choke task after xx seconds(delay)
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
				new Runnable() {
						public void run() {
							plugin.getServer().getScheduler().cancelTask(taskID);
						}
				}, delay);
	}

	/**
	 * "Strikes" the provided player with lightnings and damages him every second for half a heart.
	 * Durations depend on the players Skill rank.
	 *
	 * @param player - ForcePlayer object who casted Lightnings.
	 * @param target - ForcePlayer object who should be hit.
	 */
	public void scheduleLightningTask(final ForcePlayer player, final ForcePlayer target) {
		final int playerRank = player.getSkillRank("Lightning");
		if (playerRank == 0)
			return;
		long delay = plugin.ranksInfo.getLong("Lightning." + String.valueOf(playerRank), 20);
		final int taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin,
				new Runnable() {
					public void run() {
						Player pTarget = target.getHandler();
						World targetWorld = pTarget.getWorld();
						targetWorld.strikeLightning(pTarget.getLocation());
						int health = pTarget.getHealth();
						int amount = plugin.ranksInfo.getInt("Heal. " + String.valueOf(playerRank), 1);
						if ((health - amount) < 0)
							pTarget.setHealth(0);
						else
							pTarget.setHealth(health - amount);
					}
				}, 0, 20);

		// Stops the lightning after xx seceonds
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
				new Runnable() {
					public void run() {
						ForcePlugin.getInstance().getServer().getScheduler().cancelTask(taskID);
						target.removePowerState(PlayerPowerStates.SHOCKED);
					}
				}, delay);
	}

	/**
	 * "Strikes" the provided living entity with lightnings and damages it every second for half a heart.
	 * Durations depend on the players Skill rank.
	 *
	 * @param player - ForcePlayer object who casted Lightnings.
	 * @param target - LivingEntity to be hit.
	 */
	public void scheduleLivingEntityLightningTask(final ForcePlayer player, final LivingEntity target) {
		final int playerRank = player.getSkillRank("Lightning");
		if (playerRank == 0)
			return;
		long delay = plugin.ranksInfo.getLong("Lightning." + String.valueOf(playerRank), 20);
		final int taskID = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin,
				new Runnable() {
					public void run() {
						World targetWorld = target.getWorld();
						targetWorld.strikeLightning(target.getLocation());
						int health = target.getHealth();
						int amount = plugin.ranksInfo.getInt("Heal. " + String.valueOf(playerRank), 1);
						if ((health - amount) < 0)
							target.setHealth(0);
						else
							target.setHealth(health - amount);
					}
				}, 0, 20);

		// Stops the lightning after xx seceonds
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
				new Runnable() {
					public void run() {
						ForcePlugin.getInstance().getServer().getScheduler().cancelTask(taskID);
						ForcePlugin.removeStrokedEntity(target.getUniqueId());
					}
				}, delay);
	}

	public void scheduleLiftTask(final ForcePlayer player, final ForcePlayer target) {

	}

	public void scheduleEntityLiftTask(final ForcePlayer player, final LivingEntity target) {

	}
}