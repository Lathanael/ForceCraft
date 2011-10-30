package de.Lathanael.TheLivingForce.Powers;

import de.Lathanael.TheLivingForce.Utils.ForceAlignment;

public class Pull extends BasePower {

	public Pull() {
		name = "Pull";
		perm = "force.pull";
		alignment = ForceAlignment.valueOf(instance.config.getString(name + ".alignment"));
		enabled = instance.config.getBoolean(name + ".enabled");
		rank = instance.config.getInt(name + ".rank");
		delay = instance.config.getLong(name + "delay");
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
