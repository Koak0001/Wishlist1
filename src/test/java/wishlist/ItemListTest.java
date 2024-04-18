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

    }
}