package de.Lathanael.TheLivingForce.Powers;

import de.Lathanael.TheLivingForce.Enums.ForceAlignment;

public abstract class BasePower {

	protected final String name;
	protected ForceAlignment alignment;

	private BasePower (String powerName, ForceAlignment alignment) {
		this.name = powerName;
		this.alignment = alignment;
	}

	public abstract void execute();
}
