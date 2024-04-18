package wishlist.service;

import org.springframework.stereotype.Service;
import wishlist.model.Item;
import wishlist.model.ItemList;
import wishlist.repository.WishRepository;

import java.util.List;

@Service
public class WishService {

    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {this.wishRepository = wishRepository;}

    public void addItemList(ItemList itemList) {
        wishRepository.addNewItemList(itemList);
    }

    public void addItem(Item item, int idItemList) {wishRepository.addItem(item, idItemList);}

    public List<ItemList> getWishLists() {return wishRepository.getAllWishlists();}

    public List<Item> getItemListById(int idItemList) {return wishRepository.getItemsInList(idItemList);}

    public ItemList getItemList(int idItemList) {return wishRepository.getItemListDetails(idItemList);}

    public Item getItemById(int idItem, int idItemList) {return wishRepository.getItemById(idItem, idItemList);}

    public void deleteItemFromList(int idItemList, Item item) {wishRepository.deleteItemFromList(idItemList, item);}

}