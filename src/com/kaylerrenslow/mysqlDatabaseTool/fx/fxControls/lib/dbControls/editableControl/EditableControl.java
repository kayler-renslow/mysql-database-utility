package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.dbControls.editableControl;

/**
 * @author Kayler
 *         Created on 01/21/2016.
 */
public abstract class EditableControl<E extends javafx.scene.layout.Region>{

	protected E control;
	private String editorName;

	public EditableControl(E control, String editorName) {
		this.control = control;
		this.editorName = editorName;
	}

	public E getControl() {
		return this.control;
	}

	public String getEditorName(){
		return editorName;
	}

	public abstract void updateData(String data);

	public abstract String getData();
}
