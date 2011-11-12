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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public enum Ranks {
	STUDENT(1, "Student"),
	APPRENTICE(2, "Apprentice"),
	ACOLYTE(3, "Acolyte"),
	MAURAUDER(4, "Maurauder"),
	LORD(5, "Lord"),
	INITIATE(1, "Initiate"),
	PADAWAN(2, "Padawan"),
	KNIGHT(3, "Knight"),
	GUARDIAN(4, "Guardian"),
	MASTER(5, "Master"),
	FORCE_SENSITIVE(0, "Force Sensitive"),
	NONE(-1, "None");

	private Ranks(int rank, String label) {
		this.rank = rank;
		this.label = label;
	}

	private int rank;
	private String label;
	private static final Map<Integer, Ranks> lookupTableInt = new HashMap<Integer, Ranks>();
	private static final Map<String, Ranks> lookupTableString = new HashMap<String, Ranks>();

	public int getRankNr() {
		return rank;
	}

	public String getLabel() {
		return label;
	}

	public static Ranks getRank(int number) {
		if (lookupTableInt.containsKey(number))
			return lookupTableInt.get(number);
		else
			return Ranks.NONE;
	}

	public static Ranks getRank(String label) {
		if (lookupTableString.containsKey(label))
			return lookupTableString.get(label);
		else
			return Ranks.NONE;
	}

	static {
		for (Ranks rank : values()) {
			lookupTableInt.put(rank.rank, rank);
			lookupTableString.put(rank.label, rank);
		}
	}
}
