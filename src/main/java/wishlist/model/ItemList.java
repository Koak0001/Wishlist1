package wishlist.model;

import java.util.ArrayList;

public class ItemList {
    private String listName;
    private ArrayList<Item> localList;

    public ItemList(String listName) {
        this.listName = listName;
        this.localList = new ArrayList<>();
    }
}