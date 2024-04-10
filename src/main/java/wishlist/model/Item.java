package wishlist.model;
//TODO Cleanup redundant attributes.
public class Item {
    private String itemName;
    private String itemDescription;
    private int itemPrice;
    private boolean reserved;
    private ItemType itemType;

    public Item(String itemName) {
        this.itemName = itemName;
    }
    public enum ItemType{
        HOME("Home"),
        FOOD("Food and Beverages"),
        GARDEN("Garden"),
        LIFE("Life");

        private final String type;
        ItemType(String type){
            this.type = type;
        }
    }
    public String getItemName() {return itemName;}
    public String setItemName(String itemName) {return itemName;}

}