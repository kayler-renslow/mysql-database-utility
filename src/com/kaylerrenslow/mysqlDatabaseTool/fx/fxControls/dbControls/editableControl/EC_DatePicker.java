package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.dbControls.editableControl;

import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.dbControls.DBColumnEditors;
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
 *         Created by on 01/21/2016.
 */
public class EC_DatePicker extends EditableControl<EpochDatePicker>{

	public EC_DatePicker() {
		super(new EpochDatePicker(), DBColumnEditors.DATE_PICKER.editorName);
	}

	@Override
	public void updateData(String data) {
		this.control.setDateFromEpoch(data);
		this.control.updateEpochFromPicker();
	}

	@Override
	public String getData() {
		return this.control.getEpochFromPicker();
	}
}

class EpochDatePicker extends HBox implements ChangeListener<LocalDate>{
	private static final Insets margin = new Insets(0, 5, 0, 0);
	private static final String EPOCH = "Epoch";

	public DatePicker datePicker = new DatePicker();
	public TextField tf_epoch = new TextField();

	public EpochDatePicker() {
		Label lbl_epoch = new Label(EPOCH);
		lbl_epoch.setOpaqueInsets(margin);
		datePicker.setOpaqueInsets(margin);
		this.getChildren().addAll(datePicker, lbl_epoch, tf_epoch);
		datePicker.valueProperty().addListener(this);
		tf_epoch.textProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try{
					Calendar.getInstance().setTimeInMillis(Long.valueOf(newValue));
					setDateFromEpoch(newValue);
					tf_epoch.setStyle("");
				}catch (Exception e){
					tf_epoch.setStyle("-fx-background-color:red");
				}
			}
		});
	}

	public void setDateFromEpoch(String epochInSeconds) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(Long.valueOf(epochInSeconds + "000"));
		datePicker.setValue(LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE)));
	}

	void updateEpochFromPicker() {
		this.tf_epoch.setText(getEpochFromPicker());
	}

	public String getEpochFromPicker() {
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
}
