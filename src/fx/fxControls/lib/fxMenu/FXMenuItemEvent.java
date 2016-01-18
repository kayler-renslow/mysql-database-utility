package fx.fxControls.lib.fxMenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author Kayler
 * Event class used for when a FXMenuItem was clicked.
 * Created on 12/7/15.
 */
class FXMenuItemEvent implements EventHandler<ActionEvent> {
    private IFXMenuEventHandle handler;

    /**Index at which the listener corresponds in the IFXMenuEventHandle (this.handler)*/
    private final int index;

    public FXMenuItemEvent(IFXMenuEventHandle menu, int index) {
        this.handler = menu;
        this.index = index;
    }

    @Override
    public void handle(ActionEvent event) {
        handler.handle(index, event);
    }
}
