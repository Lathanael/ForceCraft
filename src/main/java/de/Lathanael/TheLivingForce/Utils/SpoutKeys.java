package de.Lathanael.TheLivingForce.Utils;

import org.getspout.spoutapi.keyboard.Keyboard;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public class SpoutKeys {

	public static Keyboard getKey(int keyNr) {
		return Keyboard.getKey(keyNr);
	}

	public static Keyboard getKey(String keyName) {
		return Keyboard.valueOf("KEY_" + keyName.toUpperCase());
	}
}
