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

package de.Lathanael.TheLivingForce.Powers;

import de.Lathanael.TheLivingForce.Utils.ForceAlignment;
import de.Lathanael.TheLivingForce.bukkit.ForcePlugin;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public abstract class BasePower {

	public String name;
	public String cmdName;
	public String perm;
	public ForceAlignment alignment;
	public int rank = 1;
	public final ForcePlugin instance;
	public Long delay = 0L;

	public BasePower() {
		instance = ForcePlugin.getInstance();
	}

	public abstract void execute();
}
