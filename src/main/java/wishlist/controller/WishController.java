package wishlist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wishlist.model.Item;
import wishlist.model.ItemList;
import wishlist.repository.WishRepository;
import wishlist.service.WishService;

import java.util.List;


@Controller
@RequestMapping("wishlist")
public class WishController {

    private final WishService wishService;
    private final WishRepository wishRepository;

    @Autowired
    public WishController(WishService wishService, WishRepository wishRepository) {this.wishService = wishService;
        this.wishRepository = wishRepository;
    }


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
    @PostMapping("/{listName}/delete/{itemName}")
    public String deleteItem(@PathVariable String listName, @PathVariable String itemName, Model model) {
        ItemList itemlist = wishService.getItemListByName(listName);
        Item item = wishService.getItemByName(itemName, itemlist);
        wishService.deleteItem(itemlist, item);
        model.addAttribute("itemlist", itemlist);
        return "redirect:/wishlist/{listName}";
    }

    @PostMapping("wishlists/delete/{listName}")
    public String delete(@PathVariable String listName, Model model) {
        wishService.deleteItemList(listName);
        return "redirect:/wishlist/wishlists";
    }
}

