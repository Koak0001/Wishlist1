package wishlist.repository;

import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Repository;
import wishlist.model.Item;
import wishlist.model.ItemList;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WishRepository {
    private final List<ItemList> allWishlists;

    public WishRepository() {
        allWishlists = new ArrayList<>();

        ItemList list1 = new ItemList("Wishlist1");

        list1.addItem(new Item("Cykel"));
        list1.addItem(new Item("AirPods"));
        list1.addItem(new Item("Iphone 10"));

        allWishlists.add(list1);


    }
    public void addItemlist(ItemList itemList) {allWishlists.add(itemList);}

    public List<ItemList> getAllWishlists() {
        return allWishlists;
    }

    public ItemList getItemListByName(String name) {
        for (ItemList itemList : allWishlists) {
            if (itemList.getListName().equalsIgnoreCase(name)) {
                return itemList;
            }
        }
        throw new IllegalArgumentException("ItemList with name " + name + " not found");
    }


}


