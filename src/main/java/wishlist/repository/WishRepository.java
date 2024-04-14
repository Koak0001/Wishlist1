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

        ItemList list2 = new ItemList("Wishlist2");
        list2.addItem(new Item("Playstation 5"));
        list2.addItem(new Item("Skateboard"));
        list2.addItem(new Item("Koncertbilleter"));
        allWishlists.add(list2);

        ItemList list3 = new ItemList("Wishlist3");
        list3.addItem(new Item("Wellness Gavekort"));
        list3.addItem(new Item("Gavekort Bog&Ide"));
        list3.addItem(new Item("Sommerhus Ophold"));
        allWishlists.add(list3);


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
   public Item getItemByName(String name, ItemList itemList)
   {return itemList.getItemByName(name);}


    public boolean deleteItemListByName(String name) {
        for (ItemList itemList : allWishlists) {
            if (itemList.getListName().equalsIgnoreCase(name)) {
                allWishlists.remove(itemList);
                return true;
            }
        }
        return false;
    }

}


