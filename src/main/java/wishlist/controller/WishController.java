package wishlist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("wishlists")
    public String getAll(Model model){
        List<ItemList> lists = wishService.getWishLists();
        model.addAttribute("lists", lists);
        return "wishlists";}

    @GetMapping("/{idItemList}")
    public String itemListPage(@PathVariable int idItemList, Model model) {
        List<Item> items = wishService.getItemListById(idItemList);
        ItemList itemList = wishService.getItemList(idItemList);
        model.addAttribute("items", items);
        model.addAttribute("itemList", itemList);
        return "itemlist";
    }
    @GetMapping("/create")
    public String showCreateWishlistForm(Model model) {
        ItemList newItemList = new ItemList("");
        model.addAttribute("itemlist", newItemList);
        return "create";
    }

    @PostMapping("/save")
    public String saveWishlist(@ModelAttribute("itemlist") ItemList itemList) {
        wishService.addItemList(itemList);
        return "redirect:/wishlist/wishlists";
    }

    @GetMapping("/{idItemList}/addCustomItem")
    public String showAddCustomItemForm(@PathVariable int idItemList, Model model) {
        Item newItem = new Item("");
        ItemList itemList = wishService.getItemList(idItemList);
        int idItem = newItem.getIdItem();
        model.addAttribute("item", newItem);
        model.addAttribute("idItem", idItem);
        model.addAttribute("itemList", itemList);
        return "addCustomItem";

    }

    @PostMapping("/{idItemList}/addItem")
    public String addItemToWishlist(@PathVariable int idItemList, @ModelAttribute("item") Item item){
        wishService.addItem(item, idItemList);
        System.out.println(" id "+ item.getIdItem());
        System.out.println(item.getItemName());
        System.out.println(item.getItemDescription());
        System.out.println(item.getItemPrice());
        return "redirect:/wishlist/" + idItemList;
    }
//    @PostMapping("/{listName}/delete/{itemName}")
//    public String deleteItem(@PathVariable String listName, @PathVariable String itemName, Model model) {
//        ItemList itemlist = wishService.getItemListByName(listName);
//        Item item = wishService.getItemByName(itemName, itemlist);
//        wishService.deleteItem(itemlist, item);
//        model.addAttribute("itemlist", itemlist);
//        return "redirect:/wishlist/{listName}";
//    }
//
//    @PostMapping("wishlists/delete/{listName}")
//    public String delete(@PathVariable String listName, Model model) {
//        wishService.deleteItemList(listName);
//        return "redirect:/wishlist/wishlists";
//    }
}


