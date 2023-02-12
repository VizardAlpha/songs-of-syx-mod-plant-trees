package com.github.argon.sos.planttree;

import script.SCRIPT;
import snake2d.MButt;
import snake2d.Renderer;
import snake2d.util.datatypes.COORDINATE;
import snake2d.util.file.FileGetter;
import snake2d.util.file.FilePutter;
import util.gui.misc.GBox;
import view.keyboard.KEYS;

/**
 * Not used, but required for the script engine
 */
final class Instance implements SCRIPT.SCRIPT_INSTANCE {
	@Override
	public void save(FilePutter file) {}

	@Override
	public void load(FileGetter file) {}

	@Override
	public void update(double ds) {}
	
	@Override
	public void hoverTimer(double mouseTimer, GBox text) {}

	@Override
	public void render(Renderer r, float ds) {}

	@Override
	public void keyPush(KEYS key) {}

	@Override
	public void mouseClick(MButt button) {}

	@Override
	public void hover(COORDINATE mCoo, boolean mouseHasMoved) {}
}
