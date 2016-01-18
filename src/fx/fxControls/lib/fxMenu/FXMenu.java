package fx.fxControls.lib.fxMenu;

/**
 * Created by kayler on 12/7/15.
 */
public abstract class FXMenu {

    public static void addItems(javafx.scene.control.Menu menu, IFXMenuEventHandle handle, FXMenuItem... items){
        int insertionIndex = menu.getItems().size();
        for(int i = 0; i < items.length; i++){
            addEvent(insertionIndex, handle, items[i]);
            insertionIndex++;
            menu.getItems().add(items[i]);
        }
    }

    public static void addItems(javafx.scene.control.ContextMenu menu, IFXMenuEventHandle handle, FXMenuItem... items){
        int insertionIndex = menu.getItems().size();
        for(int i = 0; i < items.length; i++){
            addEvent(insertionIndex, handle, items[i]);
            insertionIndex++;
            menu.getItems().add(items[i]);
        }
    }


    private static void addEvent(int insertionIndex, IFXMenuEventHandle handle, FXMenuItem item){
        item.setOnAction(new FXMenuItemEvent(handle, insertionIndex));
        item.setInsertionIndex(insertionIndex);
    }


}