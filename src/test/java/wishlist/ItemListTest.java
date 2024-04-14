package wishlist;
import org.junit.jupiter.api.Test;
import wishlist.model.Item;
import wishlist.model.ItemList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemListTest {
    @Test
    public void testAddItem() {
        ItemList itemList = new ItemList("TestList");
        Item item = new Item("TestItem");
        itemList.addItem(item);

        assertEquals(1, itemList.getLocalList().size(), "Item should be added to the list");
        assertEquals("TestItem", itemList.getLocalList().get(0).getItemName(), "Item name should match");
    }
}