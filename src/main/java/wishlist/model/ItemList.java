package wishlist.model;

public class ItemList {
    private String listName;
    private int idItemList;

    public ItemList(String listName) {
        this.listName = listName;
    }
    public int getIdItemList() {
        return idItemList;
    }
    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getListName() {
        return listName;
    }

    public void setIdItemList(int idItemList) {
        this.idItemList = idItemList;
    }


}
