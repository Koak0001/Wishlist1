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
                ItemList newItemList = new ItemList(listName);
                newItemList.setIdItemList(idItemList);
                wishlists.add(newItemList);
            }
        } catch (SQLException e) {
            System.out.println("Database not connected");
            e.printStackTrace();
        }
        return wishlists;
    }

    public List<Item> getItemsInList(int idItemList) {
        List<Item> items = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            String sql = "SELECT i.idItem, i.ItemName, i.ItemDescription, i.ItemPrice " +
                    "FROM Item i " +
                    "JOIN ListJunction lj ON i.idItem = lj.idItem " +
                    "WHERE lj.idItemList = ?";
            PreparedStatement psts = con.prepareStatement(sql);
            psts.setInt(1, idItemList);
            ResultSet resultSet = psts.executeQuery();
            while (resultSet.next()) {
                int idItem = resultSet.getInt("idItem");
                String itemName = resultSet.getString("ItemName");
                String itemDescription = resultSet.getString("ItemDescription");
                int itemPrice = resultSet.getInt("ItemPrice");
                //setting the items in the list
                Item item = new Item(itemName);
                item.setIdItem(idItem);
                item.setItemDescription(itemDescription);
                item.setItemPrice(itemPrice);

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
            String itemSql = "INSERT INTO Item (ItemName, ItemDescription, ItemPrice) VALUES (?, ?, ?)";
            PreparedStatement itemPstmt = con.prepareStatement(itemSql, Statement.RETURN_GENERATED_KEYS);
            itemPstmt.setString(1, item.getItemName());
            itemPstmt.setString(2, item.getItemDescription());
            itemPstmt.setInt(3, item.getItemPrice());
            itemPstmt.executeUpdate();

            // Retrieving keys
            ResultSet itemRs = itemPstmt.getGeneratedKeys();
            int idItem = -1;
            if (itemRs.next()) {
                idItem = itemRs.getInt(1);
                item.setIdItem(idItem);
                //Debug line - System.out.println("Item ID: " + idItem);
            }
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

    public void deleteItemFromList(int idItemList, Item itemToDelete) {
        try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            // Delete from ListJunction table
            String junctionSql = "DELETE FROM ListJunction WHERE idItemList = ? AND idItem = ?";
            PreparedStatement junctionPstmt = con.prepareStatement(junctionSql);
            junctionPstmt.setInt(1, idItemList);
            junctionPstmt.setInt(2, itemToDelete.getIdItem());
            junctionPstmt.executeUpdate();

            String itemSql = "DELETE FROM Item WHERE idItem = ?";
            PreparedStatement itemPstmt = con.prepareStatement(itemSql);
            itemPstmt.setInt(1, itemToDelete.getIdItem());
            itemPstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting item from the wishlist");
            e.printStackTrace();
        }
    }

    public Item getItemById(int idItem, int idItemList) {
        Item item = null;
        try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            String sql = "SELECT i.ItemName, i.ItemDescription, i.ItemPrice " +
                    "FROM Item i " +
                    "JOIN ListJunction lj ON i.idItem = lj.idItem " +
                    "WHERE lj.idItemList = ? AND i.idItem = ?";
            PreparedStatement psts = con.prepareStatement(sql);
            psts.setInt(1, idItemList);
            psts.setInt(2, idItem);
            ResultSet resultSet = psts.executeQuery();
            if (resultSet.next()) {
                String itemName = resultSet.getString("ItemName");
                String itemDescription = resultSet.getString("ItemDescription");
                int itemPrice = resultSet.getInt("ItemPrice");
                // Create the Item object with the retrieved data
                item = new Item(itemName);
                item.setIdItem(idItem);
                item.setItemDescription(itemDescription);
                item.setItemPrice(itemPrice);
            }
        } catch (SQLException e) {
            System.out.println("Error locating item");
            e.printStackTrace();
        }
        return item;
    }

    public void deleteItemList(int idItemList) {
        try (Connection con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            // Delete from ListJunction table
            String junctionSql = "DELETE FROM ListJunction WHERE idItemList = ?";
            PreparedStatement junctionPstmt = con.prepareStatement(junctionSql);
            junctionPstmt.setInt(1, idItemList);
            junctionPstmt.executeUpdate();
            // Delete from Item table
            String itemSql = "DELETE FROM Item WHERE idItem IN (SELECT idItem FROM ListJunction WHERE idItemList = ?)";
            PreparedStatement itemPstmt = con.prepareStatement(itemSql);
            itemPstmt.setInt(1, idItemList);
            itemPstmt.executeUpdate();
            // Delete from ItemList table
            String itemListSql = "DELETE FROM ItemList WHERE idItemList = ?";
            PreparedStatement itemListPstmt = con.prepareStatement(itemListSql);
            itemListPstmt.setInt(1, idItemList);
            itemListPstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting item list");
            e.printStackTrace();
        }
    }



    //TODO Delete list
    //TODO Edit item
}


