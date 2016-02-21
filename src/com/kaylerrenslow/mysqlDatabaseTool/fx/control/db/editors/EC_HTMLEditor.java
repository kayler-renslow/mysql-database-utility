package com.kaylerrenslow.mysqlDatabaseTool.fx.control.db.editors;

import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;

/**
 * Created by Kayler on 02/20/2016.
 */
public class EC_HTMLEditor extends EditableControl<HTMLEditor>{
	public EC_HTMLEditor() {
		super(new HTMLEditor(), DBColumnEditors.HTML_EDITOR.editorName);
	}

	@Override
	public void updateData(String data) {
		this.control.setRawHTML(data);
	}

	@Override
	public String getData() {
		return this.control.getRawHTML();
	}

	@Override
	public boolean supportsData(String data) {
		return true;
	}
}

class HTMLEditor extends VBox implements EventHandler<ActionEvent>{
	private ToggleButton tbShowHTML = new ToggleButton(Lang.DB_EDITOR_CONTROL_HTML_EDITOR_TOGGLE_BUTTON);
	private TextArea taEditor = new TextArea();
	private String userText, rawHTML;
	private static final Insets MARGIN_BOTTOM5 = new Insets(0,0,5,0);

	private enum HTMLSpecialChar{
		DQUOTE("\"", "&quot;"), SQUOTE("'", "&#39;"), LT("<", "&lt;"), GT(">","&gt;"), AMP("&", "&amp;");

		String userChar, safeChar;
		HTMLSpecialChar(String userChar, String safeChar){
			this.userChar = userChar;
			this.safeChar = safeChar;
		}
	}

	public HTMLEditor() {
		initialize();
	}

	private void initialize() {
		tbShowHTML.setOnAction(this);
		VBox.setMargin(tbShowHTML, MARGIN_BOTTOM5);
		this.setFillWidth(true);
		this.getChildren().addAll(this.tbShowHTML, this.taEditor);
	}

	public void setRawHTML(String text){
		this.rawHTML = text;
		this.userText = convertToUserText(text);
		this.taEditor.setText(this.userText);
	}

	private String convertToRawHTML(String text){
		text = text.replaceAll(HTMLSpecialChar.AMP.userChar, HTMLSpecialChar.AMP.safeChar); //amp must be done first since all of the other rules require an & for replacement
		for(HTMLSpecialChar specialChar : HTMLSpecialChar.values()){
			if(specialChar == HTMLSpecialChar.AMP){
				continue;
			}
			text = text.replaceAll(specialChar.userChar, specialChar.safeChar);
		}
		return text;
	}

	private String convertToUserText(String text){
		for(HTMLSpecialChar specialChar : HTMLSpecialChar.values()){
			text = text.replaceAll(specialChar.safeChar, specialChar.userChar);
		}
		return text;
	}

	/**Get's the text that is being edited.*/
	public String getRawHTML(){
		return convertToRawHTML(this.userText);
	}

	@Override
	/*called when toggle button is pressed*/
	public void handle(ActionEvent event) {
		if(this.tbShowHTML.isSelected()){
			this.userText = this.taEditor.getText();
			this.rawHTML = convertToRawHTML(this.userText);
			this.taEditor.setText(this.rawHTML);
			this.taEditor.setEditable(false);
		}else{
			this.taEditor.setText(this.userText);
			this.taEditor.setEditable(true);
		}
	}
}