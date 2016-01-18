package fx.fxControls.lib.fxMenu;

import javafx.scene.control.MenuItem;

/**
 * Created by kayler on 12/7/15.
 */
public class FXMenuItem extends MenuItem {
    private int insertionIndex;
    public FXMenuItem(String text) {
        super(text);
    }

    void setInsertionIndex(int index){
        this.insertionIndex = index;
    }

    public int getInsertionIndex(){
        return this.insertionIndex;
    }

}
