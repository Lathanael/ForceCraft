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

package de.Lathanael.TheLivingForce.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public enum Ranks {
	STUDENT(1),
	APPRENTICE(2),
	ACOLYTE(3),
	MAURAUDER(4),
	LORD(5),
	INITIATE(1),
	PADAWAN(2),
	KNIGHT(3),
	GUARDIAN(4),
	MASTER(5),
	FORCE_SENSITIVE(0),
	NONE(-1);

	private Ranks(int rank) {
		this.rank = rank;
	}

	private int rank;
	private static final Map<Integer, Ranks> lookupTable = new HashMap<Integer, Ranks>();

	public int getRankNr() {
		return rank;
	}

	public static Ranks getRank(int number) {
		if (lookupTable.containsKey(number))
			return lookupTable.get(number);
		else
			return Ranks.NONE;
	}

	static {
		for (Ranks rank : values()) {
			lookupTable.put(rank.rank, rank);
		}
	}
}
