package com.kaylerrenslow.mysqlDatabaseTool.dbGuiFacade;

/**
 * @author Kayler
 * Wrapper class for the JavaFX Task system. This class also contains one additional method, updateValue(),
 * which tells JavaFX's Task base implementation to notify listeners there is an update in the execution of the task.
 *
 * Created on 11/13/15.
 */
public abstract class Task extends javafx.concurrent.Task<Object>{

    public void updateValue(){
        try{
            this.updateValue(new Object()); //needs to be a new Object otherwise the update will be discarded
        }catch(Exception e){
			e.printStackTrace();
		}
    }

}