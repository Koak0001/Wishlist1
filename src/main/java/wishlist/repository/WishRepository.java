package wishlist.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import wishlist.model.Item;
import wishlist.model.ItemList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WishRepository {
    @Value("${spring.datasource.url}")
    String dbUrl;
    @Value("${spring.datasource.username}")
    String dbUsername;
    @Value("${spring.datasource.password}")
    String dbPassword;

    public List<ItemList> getAllWishlists() {
        List<ItemList> wishlists = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            String sql = "SELECT idItemList, ListName FROM ItemList";
            PreparedStatement psts = con.prepareStatement(sql);
            ResultSet resultSet = psts.executeQuery();
            while (resultSet.next()) {

                int idItemList = resultSet.getInt("idItemList");
                String listName = resultSet.getString("ListName");
                ItemList newItemList = new ItemList(listName); // Create ItemList without idItemList

                newItemList.setIdItemList(idItemList); // Set idItemList separately
                wishlists.add(newItemList);

            }
        } catch (SQLException e) {
            System.out.println("Database not connected");
            e.printStackTrace();
        }
        return wishlists;
    }

    public List<Item> getItemList(int idItemList) {
        List<Item> items = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            String sql = "SELECT i.idItem, i.ItemName FROM Item i " +
                    "JOIN ListJunction lj ON i.idItem = lj.idItem " +
                    "WHERE lj.idItemList = ?";
            PreparedStatement psts = con.prepareStatement(sql);
            psts.setInt(1, idItemList);
            ResultSet resultSet = psts.executeQuery();
            while (resultSet.next()) {
                int idItem = resultSet.getInt("idItem");
                String itemName = resultSet.getString("ItemName");
//                String itemDescription = resultSet.getString("ItemDescription");
//                int itemPrice = resultSet.getInt("ItemPrice");

                Item item = new Item(itemName);

                items.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Items not located");
            e.printStackTrace();
        }
        return items;
    }
    public ItemList getItemListDetails(int idItemList) {
        ItemList itemList = null;
        try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            String sql = "SELECT * FROM ItemList WHERE idItemList = ?";
            PreparedStatement psts = con.prepareStatement(sql);
            psts.setInt(1, idItemList);
            ResultSet resultSet = psts.executeQuery();
            if (resultSet.next()) {
                String listName = resultSet.getString("ListName");
                itemList = new ItemList(listName);
                itemList.setIdItemList(idItemList);
            }
        } catch (SQLException e) {
            System.out.println("ItemList not located");
            e.printStackTrace();
        }
        return itemList;
    }
    public void addNewItemList(ItemList newItemList) {
        try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            String sql = "INSERT INTO ItemList (ListName) VALUES (?)";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, newItemList.getListName());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int idItemList = rs.getInt(1);
                newItemList.setIdItemList(idItemList);
            }
        } catch (SQLException e) {
            System.out.println("Error adding new ItemList");
            e.printStackTrace();
        }
    }
    public void addItem(Item item, int idItemList) {
        try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            // Insert the item into the Item table
            String itemSql = "INSERT INTO Item (ItemName, ItemDescription, ItemPrice) VALUES (?, ?, ?)";
            PreparedStatement itemPstmt = con.prepareStatement(itemSql, Statement.RETURN_GENERATED_KEYS);
            itemPstmt.setString(1, item.getItemName());
            itemPstmt.setString(2, item.getItemDescription());
            itemPstmt.setInt(3, item.getItemPrice());
            itemPstmt.executeUpdate();

            // Retrieve keys
            ResultSet itemRs = itemPstmt.getGeneratedKeys();
            int idItem = -1;
            if (itemRs.next()) {
                idItem = itemRs.getInt(1);

                item.setIdItem(idItem);
                System.out.println("Item ID: " + idItem);
            }

            // Insert into ListJunction
            String junctionSql = "INSERT INTO ListJunction (idItemList, idItem) VALUES (?, ?)";
            PreparedStatement junctionPstmt = con.prepareStatement(junctionSql);
            junctionPstmt.setInt(1, idItemList);
            junctionPstmt.setInt(2, item.getIdItem()); // Use the retrieved idItem
            junctionPstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error adding new item to the wishlist");
            e.printStackTrace();
        }
    }






    //TODO Add Item to list
    //TODO Delete list
    //TODO View item
    //TODO Delete item
    //TODO Edit item
}


