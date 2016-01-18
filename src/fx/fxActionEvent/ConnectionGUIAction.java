package fx.fxActionEvent;

import fx.fxControllers.DatabaseFXController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Created by kayler on 11/9/15.
 *
 * This class is the facade layer from the GUI to the Database connection.
 * This class is used whenever the GUI connect or disconnect button is pressed.
 */
public class ConnectionGUIAction implements EventHandler<ActionEvent> {
    private DatabaseFXController dc;

    /**True if this action represents the connect button being pressed, otherwise if the disconnect button was pressed*/
    private boolean connecting;

    public ConnectionGUIAction(DatabaseFXController dc, boolean connecting) {
        this.dc = dc;
        this.connecting = connecting;
    }

    @Override
    public void handle(ActionEvent event) {
        if(connecting){
            connectButtonPress();
        }else{
            disconnectButtonPress();
        }
    }


    private void connectButtonPress() {
        dc.runTask(dc.getConnectTask());
    }

    private void disconnectButtonPress() {
        dc.runTask(this.dc.getDisconnectTask());
    }
}
