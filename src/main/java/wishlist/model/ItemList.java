package wishlist.model;

import java.util.ArrayList;

public class ItemList {
    private String listName;
    private ArrayList<Item> localList;

    public ItemList(String listName) {
        this.listName = listName;
        this.localList = new ArrayList<>();
    }
    public void addItem(Item item) {
        localList.add(item);
    }
    public void removeItem(Item item) {
        localList.remove(item);
    }

    public ArrayList<Item> getLocalList() {
        return localList;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListName() {
        return listName;
    }
}
