package wishlist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wishlist.model.Item;
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
    @PostMapping("/{listName}/addItem")
    public String addItemToList(@PathVariable String listName, @RequestParam String itemName, @RequestParam String itemDescription, @RequestParam int itemPrice, RedirectAttributes redirectAttributes) {
        try {
            ItemList itemList = wishService.getItemListByName(listName);
            Item item = new Item(itemName);
            item.setItemDescription(itemDescription);
            item.setItemPrice(itemPrice);
            itemList.addItem(item);
            redirectAttributes.addFlashAttribute("successMessage", "Item added successfully.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding item: " + e.getMessage());
        }
        return "redirect:/wishlist/" + listName;
    }

}