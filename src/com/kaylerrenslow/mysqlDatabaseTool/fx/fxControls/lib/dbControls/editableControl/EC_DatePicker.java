package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.dbControls.editableControl;

import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.dbControls.DBColumnEditors;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Kayler
 * Created by on 01/21/2016.
 */
public class EC_DatePicker extends EditableControl<EpochDatePicker>{

	public EC_DatePicker() {
		super(new EpochDatePicker(), DBColumnEditors.DATE_PICKER.editorName);
	}

	@Override
	public void updateData(String data) {
		this.control.setDate(data);
	}

	@Override
	public String getData() {
		return this.control.getEpoch();
	}
}

class EpochDatePicker extends HBox{
	public DatePicker datePicker = new DatePicker();
	public Label lbl_epoch = new Label("Epoch");

	public EpochDatePicker() {
		this.getChildren().addAll(lbl_epoch, datePicker);
	}

	public void setDate(String epochInSeconds) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(Long.getLong(epochInSeconds + "000"));
		datePicker.getEditor().setText(c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR));
	}

	public String getEpoch(){
		String date = this.datePicker.getEditor().getText();
		int day = Integer.valueOf(date.substring(0,date.indexOf("/")));
		int month = Integer.valueOf(date.substring(date.indexOf("/"),date.lastIndexOf("/")));
		int year = Integer.valueOf(date.substring(date.lastIndexOf("/")));
		Date d = new Date(year, month, day);
		return d.getTime() / 1000 + "";
	}
}
