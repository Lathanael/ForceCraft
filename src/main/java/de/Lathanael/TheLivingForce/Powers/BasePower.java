package de.Lathanael.TheLivingForce.Powers;

import de.Lathanael.TheLivingForce.Utils.ForceAlignment;

/**
 * @author Lathanael (aka Philippe Leipold)
 */
public abstract class BasePower {

	protected final String name;
	protected ForceAlignment alignment;
	protected boolean enabled = false;
	protected int rank = 1;

	private BasePower (String powerName, ForceAlignment alignment) {
		this.name = powerName;
		this.alignment = alignment;
	}

	public abstract void execute();
}
