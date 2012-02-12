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

package de.Lathanael.ForceCraft.gui.SkillTree;

import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.Label;
import org.getspout.spoutapi.gui.Texture;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.Lathanael.ForceCraft.gui.Geometry;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class SKGUI extends GenericContainer {
	private Label label;
	@SuppressWarnings("unused")
	private SpoutPlayer player;
	@SuppressWarnings("unused")
	private Geometry edges;
	private Texture background;

	public SKGUI (Geometry edges, SpoutPlayer player, Texture tex) {
		this.player = player;
		this.edges = edges;
		background = tex;
	}
}
