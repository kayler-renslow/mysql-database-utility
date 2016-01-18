package dbGuiFacade;

/**
 * Created by kayler on 11/13/15.
 */
public abstract class Task extends javafx.concurrent.Task<Object>{
    protected final ConnectionUpdate connUpdate;

    public Task(ConnectionUpdate connUpdate) {
        this.connUpdate = connUpdate;
    }

    public void updateValue(){
        try{
            this.updateValue(new Object()); //needs to be a new Object otherwise the update will be discarded
        }catch(Exception e){}
    }
}