/*************************************************************************
 * Copyright (C) 2011-2012 Philippe Leipold
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

import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.Gradient;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.Lathanael.ForceCraft.Players.ForcePlayer;
import de.Lathanael.ForceCraft.Players.PlayerHandler;
import de.Lathanael.ForceCraft.bukkit.ForcePlugin;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class ManaBarGUI extends GenericContainer {

	private Gradient manaBar;
	private GenericTexture mbTex;
	private GenericLabel label;
	private SpoutPlayer player;
	private int texWidth;
	private int texHeight;
	private int mana;
	private int maxMana;

	public ManaBarGUI(SpoutPlayer player, int mana, int maxMana) {
		this.mana = mana;
		this.maxMana = maxMana;
		this.player = player;
		manaBar = new GenericGradient();
		// TODO: Create Texture string here and make a config option!

		// Setting up Manabar background
		mbTex = new GenericTexture("");
		mbTex.setPriority(RenderPriority.High);
		mbTex.setAnchor(WidgetAnchor.BOTTOM_CENTER);
		mbTex.setWidth(182).setHeight(5);
		mbTex.setDirty(true);

		// Setting up the bar itsself
		manaBar.setTopColor(new Color(0, 0, 1.0F, 1.0F));
		manaBar.setBottomColor(new Color(0, 0, 1.0F, 1.0F));
		manaBar.setWidth(mana).setHeight(5);
		manaBar.setAnchor(WidgetAnchor.BOTTOM_CENTER);
		//manaBar.setX(40).setY(33);
		manaBar.setPriority(RenderPriority.Low);
		manaBar.setDirty(true);

		// Setting up the container
		addChildren(new Widget[] { manaBar, mbTex});
		setWidth(0).setHeight(0);
	}

	public void updateMana(int mana) {
		this.mana = mana;
		updateBar();
	}

	public void updateMaxMana(int newMaxMana) {
		maxMana = newMaxMana;
		updateBar();
	}

	private void updateBar() {
		manaBar.setWidth(mana).setDirty(true);
	}
}
