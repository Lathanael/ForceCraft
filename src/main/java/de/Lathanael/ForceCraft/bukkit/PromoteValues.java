/*************************************************************************
 * Copyright (C) 2012 Philippe Leipold
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

package de.Lathanael.ForceCraft.bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class PromoteValues {

	private static PromoteValues instance = null;
	private static HashMap<String, Integer> values = new HashMap<String, Integer>();

	public PromoteValues() {
	}

	public static void setInstance() {
		if (instance == null)
			instance = new PromoteValues();
	}

	public static void loadValues(FileConfiguration promoteValues) {
		Map<String, Object> fileValues = promoteValues.getValues(true);
		for (Map.Entry<String, Object> entries : fileValues.entrySet()) {
			try {
				values.put(entries.getKey(), (Integer) entries.getValue());
			} catch (ClassCastException e) {
				ForcePlugin.log.log(Level.WARNING, "Entry " + entries.getKey() + " in autoPromoteValues.yml is not a valid number(Integer)! Replacing with 0.");
				values.put(entries.getKey(), 0);
			}
		}
	}

	public static int getValue(String valueName) {
		if (values.containsKey(valueName))
			return values.get(valueName);
		else
			return 0;
	}
}
