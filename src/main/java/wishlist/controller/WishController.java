package wishlist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import wishlist.model.ItemList;
import wishlist.service.WishService;

import java.util.List;

@Controller
@RequestMapping("wishlist")
public class WishController {

    private final WishService wishService;

    @Autowired
    public WishController(WishService wishService) {this.wishService = wishService;}


@GetMapping("create")
public String createList(Model model){return "create";}

@GetMapping("wishlists")
    public String getAll(Model model){
        List<ItemList> lists = wishService.getWishLists();
        model.addAttribute("lists", lists);
        return "wishlists";}

@GetMapping("/{listName}")
    public String showList(@PathVariable String listName, Model model) {
        ItemList itemlist = wishService.getItemListByName(listName);
        model.addAttribute("itemlist", itemlist);
        return "itemlist";
    }
}
