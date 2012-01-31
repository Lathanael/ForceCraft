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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public enum ForceAlignment {
	DARK("Dark", 2),
	LIGHT("Light", 1),
	NEUTRAL("Neutral", 0);

	private final String label;
	private final int number;
	private static final Map<Integer, ForceAlignment> lookupTableInt = new HashMap<Integer, ForceAlignment>();

	private ForceAlignment(String label, int number) {
		this.label = label;
		this.number = number;
	}

	public String toString() {
		return label;
	}

	public int getNumber() {
		return number;
	}

	public static ForceAlignment getAlignmentByNr(int number) {
		if (lookupTableInt.containsKey(number))
			return lookupTableInt.get(number);
		else
			return ForceAlignment.NEUTRAL;
	}

	static {
		for (ForceAlignment fal : values()) {
			lookupTableInt.put(fal.number, fal);
		}
	}
}
