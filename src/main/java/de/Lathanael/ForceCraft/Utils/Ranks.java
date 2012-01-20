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
	STUDENT(1, "Student", "1dark"),
	APPRENTICE(2, "Apprentice", "2dark"),
	ACOLYTE(3, "Acolyte", "3dark"),
	MAURAUDER(4, "Maurauder", "4dark"),
	LORD(5, "Lord", "5dark"),
	INITIATE(1, "Initiate", "1light"),
	PADAWAN(2, "Padawan", "2light"),
	KNIGHT(3, "Knight", "3light"),
	GUARDIAN(4, "Guardian", "4light"),
	MASTER(5, "Master", "5light"),
	FORCE_SENSITIVE(0, "Force Sensitive", "0neutral"),
	NONE(-1, "None", "-1none");

	private Ranks(int rank, String label, String side) {
		this.rank = rank;
		this.label = label;
		this.side = side;
	}

	private int rank;
	private String label;
	private String side;
	private static final Map<String, Ranks> lookupTableString = new HashMap<String, Ranks>();

	public int getRankNr() {
		return rank;
	}

	public String getLabel() {
		return label;
	}

	public static Ranks getRank(String side, int rank) {
		if (lookupTableString.containsKey(String.valueOf(rank)+side))
			return lookupTableString.get(String.valueOf(rank)+side);
		else
			return Ranks.NONE;
	}

	static {
		for (Ranks rank : values()) {
			lookupTableString.put(rank.side, rank);
		}
	}
}
