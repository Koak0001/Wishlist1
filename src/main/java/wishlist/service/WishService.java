package wishlist.service;

import org.springframework.stereotype.Service;
import wishlist.model.Item;
import wishlist.model.ItemList;
import wishlist.model.WishlistForm;
import wishlist.repository.WishRepository;

import java.util.List;

@Service
public class WishService {

    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public ItemList getItemListByName(String name) {
        return wishRepository.getItemListByName(name);
    }

    public Item getItemByName(String name, ItemList list) {return wishRepository.getItemByName(name, list);}

    public List<ItemList> getWishLists() {
        return wishRepository.getAllWishlists();
    }

    public void deleteItem(ItemList itemList, Item item) {itemList.removeItem(item);}

    public boolean deleteItemList(String name) {
        return wishRepository.deleteItemListByName(name);
    }

    public void addItemList(ItemList itemList) {
        wishRepository.addItemlist(itemList);
    }

    public void createWishlist(String name) {
        ItemList newList = new ItemList(name);
        addItemList(newList);
    }
}