package fx.fxControls.lib.fxMenu;

/**
 * @author Kayler
 * Class that adds FXMenuItems to JavaFX menus and adds event handler and sets insertionIndex
 * Created on 12/7/15.
 */
public final class FXMenuUtil{

	/**Adds FXMenuItems to a menu. Automatically sets insertionIndex and event listener*/
    public static void addItems(javafx.scene.control.Menu menu, IFXMenuEventHandle handle, FXMenuItem... items){
        int insertionIndex = menu.getItems().size();
        for(int i = 0; i < items.length; i++){
            addEvent(insertionIndex, handle, items[i]);
            insertionIndex++;
            menu.getItems().add(items[i]);
        }
    }

	/**Adds FXMenuItems to a context menu. Automatically sets insertionIndex and event listener*/
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