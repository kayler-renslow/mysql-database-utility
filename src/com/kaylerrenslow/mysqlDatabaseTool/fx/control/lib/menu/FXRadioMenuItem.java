package com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu;

import javafx.scene.control.RadioMenuItem;

/**
 * Created by Kayler on 02/13/2016.
 */
public class FXRadioMenuItem extends RadioMenuItem implements IFXMenuItem<RadioMenuItem>{

	private final FXMenuItemGuts guts = new FXMenuItemGuts();

	public FXRadioMenuItem(String text) {
		super(text);
	}

	@Override
	public void setInsertionIndex(int index) {
		guts.setInsertionIndex(index);
	}

	@Override
	public int getInsertionIndex() {
		return guts.getInsertionIndex();
	}

	@Override
	public boolean matchesIndex(int index) {
		return guts.matchesIndex(index);
	}

	@Override
	public RadioMenuItem getMenuItem() {
		return this;
	}
}
