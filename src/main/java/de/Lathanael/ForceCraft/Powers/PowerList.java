/*************************************************************************
 * Copyright (C) 2012 Philippe Leipold
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

package de.Lathanael.ForceCraft.Powers;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public enum PowerList {
	PUSH("Push"),
	PULL("Pull"),
	CHOKE("Choke"),
	FLASH("Flash"),
	HEAL("Heal"),
	JUMP("Jump"),
	LIFT("Lift"),
	LIGHTNING("Lightning"),
	MEDITATION("Meditation"),
	RAGE("Rage"),
	RUN("Run"),
	SHIELD("Shield");

	private String power;

	private PowerList(String power) {
		this.power = power;
	}

	public String getString() {
		return power;
	}
}
