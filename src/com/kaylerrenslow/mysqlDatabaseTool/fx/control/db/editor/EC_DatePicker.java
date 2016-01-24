package com.kaylerrenslow.mysqlDatabaseTool.fx.control.db.editor;

import com.kaylerrenslow.mysqlDatabaseTool.fx.control.db.DBColumnEditors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.util.Calendar;

/**
 * @author Kayler
 *         Created on 01/21/2016
 */
public class EC_DatePicker extends EditableControl<EpochDatePicker>{

	public EC_DatePicker() {
		super(new EpochDatePicker(), DBColumnEditors.DATE_PICKER.editorName);
	}

	@Override
	public void updateData(String data) {
		this.control.setEpochTfValue(data);
	}

	@Override
	public String getData() {
		return this.control.getEpoch();
	}

	@Override
	public boolean supportsData(String data) {
		try{
			Long.valueOf(data);
		}catch (Exception e){
			return false;
		}
		return true;
	}
}

class EpochDatePicker extends HBox implements ChangeListener<LocalDate>{
	private static final Insets margin = new Insets(0, 5, 0, 0);
	private static final String EPOCH = "Epoch";

	public DatePicker datePicker = new DatePicker();
	public TextField tf_epoch = new TextField();

	public EpochDatePicker() {
		Label lbl_epoch = new Label(EPOCH);
		HBox.setMargin(lbl_epoch, margin);
		HBox.setMargin(datePicker, margin);
		this.getChildren().addAll(datePicker, lbl_epoch, tf_epoch);
		datePicker.valueProperty().addListener(this);
		tf_epoch.textProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try{
					Calendar.getInstance().setTimeInMillis(Long.valueOf(newValue + "000"));
					tf_epoch.setStyle("");
				}catch (Exception e){
					epochFormatError();
				}
			}

		});
	}

	private void epochFormatError() {
		tf_epoch.setStyle("-fx-background-color:red;-fx-text-fill:white;");
	}

	public void setEpochTfValue(String epochInSeconds) {
		try{
			Calendar.getInstance().setTimeInMillis(Long.valueOf(epochInSeconds + "000"));
		}catch (Exception e){
			epochFormatError();
		}
		this.tf_epoch.setText(epochInSeconds);
	}


	void updateEpochFromPicker() {
		setEpochTfValue(getEpochFromPicker());
	}


	private String getEpochFromPicker() {
		if(this.datePicker.getValue() == null){
			return "";
		}
		int month = this.datePicker.getValue().getMonthValue();
		int day = this.datePicker.getValue().getDayOfMonth();
		int year = this.datePicker.getValue().getYear();
		int[] months = {Calendar.JANUARY, Calendar.FEBRUARY, Calendar.MARCH, Calendar.APRIL, Calendar.MAY, Calendar.JUNE, Calendar.JULY, Calendar.AUGUST, Calendar.SEPTEMBER, Calendar.OCTOBER, Calendar.NOVEMBER, Calendar.DECEMBER};
		Calendar c = Calendar.getInstance();
		c.set(year, months[month - 1], day);
		return c.getTimeInMillis() / 1000 + "";
	}

	@Override
	public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
		updateEpochFromPicker();
	}

	public String getEpoch(){
		return this.tf_epoch.getText();
	}
}
