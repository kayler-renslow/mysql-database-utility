package com.kaylerrenslow.mysqlDatabaseTool.dbGui;

import com.kaylerrenslow.mysqlDatabaseTool.main.WebsiteDatabaseTool;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * @author Kayler
 * Wrapper class for the JavaFX Task system. This class also contains one additional method, notifyValuePropertyListeners(),
 * which tells JavaFX's Task base implementation to notify listeners there is an update in the execution of the task.
 *
 * Created on 11/13/15.
 */
public abstract class Task extends javafx.concurrent.Task<Object>{

	private boolean debug;
	private Task self = this;

	public Task() {
		this.exceptionProperty().addListener(new ChangeListener<Throwable>(){
			@Override
			public void changed(ObservableValue<? extends Throwable> observable, Throwable oldValue, Throwable newValue) {
				System.err.println("Task exception:");
				newValue.printStackTrace();
//				self.cancel();
				self.cancel(true);
				WebsiteDatabaseTool.showErrorWindow("Error occurred", newValue.getMessage());
			}
		});

		this.stateProperty().addListener(new ChangeListener<State>(){
			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
				if(debug){
					System.out.println("Task State: " + newValue);
				}
			}
		});
	}

	@Override
	protected void updateValue(Object value) {
		if(value == null){
			return; //call() is finished, so end the task
		}
		super.updateValue(value); //call() wants to update the value listeners
	}

	public void notifyValuePropertyListeners(){
		try{
            this.updateValue(new Object()); //needs to be a new Object otherwise the update will be discarded
        }catch(Exception e){
			e.printStackTrace();
		}
    }

	public void setStateDebug(boolean debug){
		this.debug = debug;
	}

	/**Run a task on a new thread*/
	public static void runTask(Task task) {
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.setName(task.getName());
		thread.start();
	}

	public abstract String getName();

	@Override
	public void run() {
		super.runAndReset();
	}
}