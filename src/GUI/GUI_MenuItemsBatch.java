package GUI;
/**
 * Menu Item Batch Class File
 */
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.MenuItem;

public class GUI_MenuItemsBatch{

    private List<MenuItem> items;
    private List<MenuItem> problems;

    /**
     * Constructor of the Class
     */
    public GUI_MenuItemsBatch(){
        this.items = new ArrayList<MenuItem>();
        this.problems = new ArrayList<MenuItem>();
        genItems();
        genProblems();
    }
    /**
     * Generates the Menu Items for the "File" Menu
     */
    private void genItems(){
        MenuItem save = new MenuItem("Save Documents");
        MenuItem load = new MenuItem("Load Documents");
        /*
         * Insert code for the menu items to do something here at a later date.
         */
        this.items.add(save);
        this.items.add(load);
    }
    /**
     * Generates the Menu Items for the "AAC Problems" Menu
     */
    private void genProblems(){
        MenuItem pa = new MenuItem("Problem A");
        MenuItem pb = new MenuItem("Problem B");
        MenuItem pc = new MenuItem("Problem C");
        MenuItem pd = new MenuItem("Problem D");
        MenuItem pe = new MenuItem("Problem E");
        MenuItem pf = new MenuItem("Problem F");
        MenuItem pg = new MenuItem("Problem G");
        MenuItem ph = new MenuItem("Problem H");
        MenuItem pi = new MenuItem("Problem I");
        MenuItem pj = new MenuItem("Problem J");
        MenuItem pk = new MenuItem("Problem K");
        MenuItem pl = new MenuItem("Problem L");
        MenuItem pm = new MenuItem("Problem M");
        this.problems.add(pa);
        this.problems.add(pb);
        this.problems.add(pc);
        this.problems.add(pd);
        this.problems.add(pe);
        this.problems.add(pf);
        this.problems.add(pg);
        this.problems.add(ph);
        this.problems.add(pi);
        this.problems.add(pj);
        this.problems.add(pk);
        this.problems.add(pl);
        this.problems.add(pm);

    }
    /**
     * Getter Function for the "File" Menu Items
     * @return List
     */
    public List<MenuItem> getItems(){
        return this.items;
    }
    /**
     * Getter Function for the "AAAC Problem" Menu Items
     * @return List
     */
    public List<MenuItem> getProblems(){
        return this.problems;
    }
}