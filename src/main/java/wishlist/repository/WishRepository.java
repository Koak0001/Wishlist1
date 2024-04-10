package wishlist.repository;

import org.springframework.stereotype.Repository;
import wishlist.model.Item;
import wishlist.model.ItemList;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WishRepository {
    private List<ItemList> allWishlists;

    public WishRepository() {
        allWishlists = new ArrayList<>();

        ItemList list1 = new ItemList("Wishlist1");

        allWishlists.add(list1);

        list1.addItem(new Item("Cykel"));
        list1.addItem(new Item("AirPods"));
        list1.addItem(new Item("Iphone 10"));



    }
    public void addWishlist(ItemList wishlist) {allWishlists.add(wishlist);}

    public List<ItemList> getAllWishlists() {
        return allWishlists;
    }

}
