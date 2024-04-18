package wishlist;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import wishlist.model.ItemList;
import wishlist.repository.WishRepository;
import wishlist.service.WishService;

class WishServiceTest {

    @Test
    void testAddItemList() {

        WishRepository mockRepository = Mockito.mock(WishRepository.class);
        WishService wishService = new WishService(mockRepository);
        ItemList itemList = new ItemList("Test ItemList");


        wishService.addItemList(itemList);

        Mockito.verify(mockRepository).addNewItemList(itemList);
    }
}
