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
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 */
public class TextureURLs {
	private static Map<String, String> urls = new HashMap<String, String>();
	public static String manaBarTexURL = "";
	public static String backgroundTexURL = "";
	public static String skillPointTexURL = "";
	public static String spHighlightedTexURL = "";
	public static String symbolBack = "";

	public TextureURLs() {
	}

	public static void loadTextures(FileConfiguration textures) {
		Set<String> keys = textures.getKeys(false);
		for (String key : keys) {
			urls.put(key, textures.getString(key));
		}
	}

	public static String getSymbol(String name) {
		if (urls.containsKey(name) && urls.get(name) != null && !urls.get(name).equals(""))
			return urls.get(name);
		return "http://dl.dropbox.com/u/42731731/STPicBack.png";
	}

	public static String getManaBar(String bar) {
		if (urls.containsKey(bar) && urls.get(bar) != null && !urls.get(bar).equals(""))
			return urls.get(bar);
		return "http://dl.dropbox.com/u/42731731/BarTex.jpg";
	}

	public static String getBackground(String back) {
		if (urls.containsKey(back) && urls.get(back) != null && !urls.get(back).equals(""))
			return urls.get(back);
		return "http://dl.dropbox.com/u/42731731/Background.png";
	}

	public static String getSkillPoint(String sp) {
		if (urls.containsKey(sp) && urls.get(sp) != null && !urls.get(sp).equals(""))
			return urls.get(sp);
		return "http://dl.dropbox.com/u/42731731/SkillPoint.png";
	}

	public static String getHighlightedSkillPoint(String sp) {
		if (urls.containsKey(sp) && urls.get(sp) != null && !urls.get(sp).equals(""))
			return urls.get(sp);
		return "http://dl.dropbox.com/u/42731731/SkillPointHighlighted.png";
	}

	public static String getPowerImage(String name) {
		if (urls.containsKey(name) && urls.get(name) != null && !urls.get(name).equals(""))
			return urls.get(name);
		return "http://dl.dropbox.com/u/42731731/Power_Back.png";
	}
}
