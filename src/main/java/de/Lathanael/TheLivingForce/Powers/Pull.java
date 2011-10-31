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

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class Pull extends BasePower {

	public Pull() {
		name = "Pull";
		cmdName = "tlf_pull";
		perm = "force.pull";
		alignment = ForceAlignment.valueOf(instance.config.getString("Power." + name + ".alignment"));
		rank = instance.config.getInt("Power." + name + ".rank");
		delay = instance.config.getLong("Power." + name + "delay");
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
