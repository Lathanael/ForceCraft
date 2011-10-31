package de.Lathanael.TheLivingForce.Powers;

import de.Lathanael.TheLivingForce.Utils.ForceAlignment;

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
