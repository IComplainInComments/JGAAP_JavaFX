import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.MenuItem;

public class GUI_MenuItemsBatch{

    private List<MenuItem> items;
    public GUI_MenuItemsBatch(){
        this.items = new ArrayList<MenuItem>();
        genItems();
    }
    private void genItems(){
        MenuItem save = new MenuItem("Save Documents");
        MenuItem load = new MenuItem("Load Documents");
        /*
         * Insert code for the menu items to do something here at a later date.
         */
        this.items.add(save);
        this.items.add(load);
    }
    public List<MenuItem> getItems(){
        return this.items;
    }
}