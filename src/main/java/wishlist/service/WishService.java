package wishlist.service;

import org.springframework.stereotype.Service;
import wishlist.model.ItemList;
import wishlist.repository.WishRepository;

import java.util.List;

@Service
public class WishService {

    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {this.wishRepository = wishRepository;}

    //TODO Create methods here, for adding a new Itemlist to allWishlists, and for adding Items to an Itemlist.

    //Read methods here TODO Include read method for Itemlist.

    public List<ItemList> getWishLists() {return wishRepository.getAllWishlists();}

}
