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

package de.Lathanael.ForceCraft.gui;

import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericContainer;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.Gradient;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.player.SpoutPlayer;

import de.Lathanael.ForceCraft.bukkit.TextureURLs;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class ManaBarGUI extends GenericContainer {

	private Gradient manaBar;
	private GenericTexture mbTex;
	private GenericLabel label;
	@SuppressWarnings("unused")
	private SpoutPlayer player;
	private int mana;
	private int maxMana;

	public ManaBarGUI(SpoutPlayer player, int mana, int maxMana) {
		this.mana = mana;
		this.maxMana = maxMana;
		this.player = player;
		int screenWidth = player.getMainScreen().getWidth();

		// Setting up Manabar background
		mbTex = new GenericTexture(TextureURLs.getManaBar("manaBarTexURL"));
		mbTex.setPriority(RenderPriority.High);
		mbTex.setX((screenWidth - 202)/2).setY(2);
		mbTex.setWidth(202).setHeight(5);
		mbTex.setDirty(true);

		// Setting up the bar itsself
		manaBar = new GenericGradient();
		manaBar.setTopColor(new Color(0, 0, 1.0F, 1.0F));
		manaBar.setBottomColor(new Color(0, 0, 1.0F, 1.0F));
		manaBar.setWidth(barLength()).setHeight(3);
		manaBar.setX((screenWidth - 202)/2 + 1).setY(3);
		manaBar.setPriority(RenderPriority.Low);
		manaBar.setDirty(true);

		// Setting up the label
		label = new GenericLabel();
		label.setX((screenWidth - 50)/2).setY(5);
		label.setWidth(50).setHeight(10);
		label.setPriority(RenderPriority.Lowest);
		label.setText(String.valueOf(this.mana) + "/" + String.valueOf(this.maxMana));
		label.setDirty(true);

		// Setting up the container
		addChildren(new Widget[] {manaBar, mbTex, label});
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
		manaBar.setWidth(barLength()).setDirty(true);
		label.setText(String.valueOf(this.mana) + "/" + String.valueOf(this.maxMana));
	}

	private int barLength() {
		return (200*mana/maxMana);
	}
}
