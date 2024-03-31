package com.github.argon.sos.planttree;


import com.github.argon.sos.planttree.log.Level;
import com.github.argon.sos.planttree.log.Logger;
import com.github.argon.sos.planttree.log.Loggers;
import com.github.argon.sos.planttree.util.UIUtil;
import init.resources.RESOURCES;
import init.sprite.SPRITES;
import lombok.NoArgsConstructor;
import script.SCRIPT;
import settlement.job.PlantTreeJob;
import util.info.INFO;

/**
 * Entry point
 */
@NoArgsConstructor
public final class PlantTreeModScript implements SCRIPT {
	private final static Logger log = Loggers.getLogger(PlantTreeModScript.class);

	private final INFO info = new INFO("Plant trees", "Plant trees on the map.");

	@Override
	public CharSequence name() {
		return info.name;
	}

	@Override
	public CharSequence desc() {
		return info.desc;
	}

	@Override
	public void initBeforeGameCreated() {
		Loggers.setLevels(Level.INFO);
	}

	@Override
	public SCRIPT_INSTANCE createInstance() {
		log.info("initialized");

		PlantTreeJob plantTreeJob = new PlantTreeJob(
				SPRITES.icons().m.agriculture,
				50,
				33,
				RESOURCES.WOOD(),
				3);

		UIUtil.addJob(plantTreeJob);

		return new Instance();
	}
}
