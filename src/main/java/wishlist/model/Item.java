package wishlist.model;
//TODO Cleanup redundant attributes.
public class Item {
    private String itemName;
    private int idItem;
    private String itemDescription;
    private int itemPrice;
    private boolean reserved;
//    private ItemType itemType;

    public Item(String itemName) {
        this.itemName = itemName;
    }
    public String getItemName() {
        return itemName;
    }
    public String getItemDescription() {
        return itemDescription;
    }
    public int getIdItem() {return idItem;}
    public int getItemPrice() {
        return itemPrice;
    }
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }
    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    //    public enum ItemType{
//        HOME("Home"),
//        FOOD("Food and Beverages"),
//        GARDEN("Garden"),
//        LIFE("Life");
//
//        private final String type;
//        ItemType(String type){
//            this.type = type;
//        }
//    }

}