package product.service;

import product.model.Product;
import product.service.action.ProductAction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService implements IProductService{
    private String jdbcUrl = "jdbc:mysql://localhost:3306/Product_Management";
    private String jdbcUsername = "root";
    private String jdbcPassword = "123456";

    //SQL Query
    private static final String SELECT_ALL_PRODUCT = "select p.id, p.productName,p.price,p.quantity,p.color,p.description,c.categoryName from product p inner join Category C on p.categoryId = C.categoryId ";
    private static final String INSERT_PRODUCT_ROW = "insert into product(productName, price,quantity,color,description,categoryId)" + "value(? ,? ,?,?,?,?)";
    private static final String DELETE_PRODUCT_ROW = "delete from product where id = ?";
    private static final String SELECT_PRODUCT_BY_ID = "select p.id, p.productName,p.price,p.quantity,p.color,p.description,c.categoryName from product p inner join Category C on p.categoryId = C.categoryId where p.id=?";
    private static final String UPDATE_PRODUCT_ROW = "update product set productName = ?,price = ?, quantity = ?, color = ?, description= ?,categoryId = ? where id =?;";
    private static final String SEARCH_PRODUCT_ROWS = "select id, productName, price, quantity,color,description,c.categoryName from product inner join Category C on Product.categoryId = C.categoryId where productName = ? or price = ? or quantity = ? or color = ? or c.categoryName =?;";




    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
            System.out.println("Connection success");
        } catch (ClassNotFoundException e) {
            // TODO auto-generated catch block
            e.printStackTrace();
            System.out.println("Connection failed");
        } catch (SQLException throwables) {
            // TODO auto-generated catch block
            throwables.printStackTrace();
            System.out.println("Connection failed");
        }
        return connection;
    }


    @Override
    public List<Product> selectAllProduct() {
        List<Product> productList = new ArrayList<>();
        try (
                Connection connection = getConnection();
                PreparedStatement prstmt = connection.prepareStatement(SELECT_ALL_PRODUCT);
        ) {
//            System.out.println(prstmt);
            ResultSet rs = prstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("productName");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String color = rs.getString("color");
                String description = rs.getString("description");
                String categoryName = rs.getString("categoryName");
                productList.add(new Product(id, name, price,quantity,color,description, categoryName));
            }
        } catch (SQLException throwables) {
            ProductAction.printSQLException(throwables);
        }
        return productList;
    }

    @Override
    public Product selectProductById(int id) {
        Product product = null;
        try (
                Connection connection = getConnection();
                PreparedStatement prstmt = connection.prepareStatement(SELECT_PRODUCT_BY_ID);
        ) {
            System.out.println(prstmt);
            prstmt.setInt(1, id);
            ResultSet rs = prstmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("productName");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String color = rs.getString("color");
                String description = rs.getString("description");
                String categoryName = rs.getString("categoryName");
                product = new Product(id,name, price,quantity,color,description,categoryName);
            }
        } catch (SQLException throwables) {
            ProductAction.printSQLException(throwables);
        }
        return product;
    }

    @Override
    public void insertProduct(Product product) throws SQLException {
        try (
                Connection connection = getConnection();
                PreparedStatement prstmt = connection.prepareStatement(INSERT_PRODUCT_ROW);
        ) {
            System.out.println(prstmt);
            int categoryId = ProductAction.checkCategory(product.getCategory());
            prstmt.setString(1, product.getProductName());
            prstmt.setDouble(2, product.getPrice());
            prstmt.setInt(3, product.getQuantity());
            prstmt.setString(4, product.getColor());
            prstmt.setString(5, product.getDescription());
            prstmt.setInt(6,categoryId);
            System.out.println(prstmt);

            prstmt.executeUpdate();
            System.out.println("Insert record successfully.");
        } catch (SQLException e) {
            ProductAction.printSQLException(e);
        }

    }

    @Override
    public boolean updateProduct(Product product) {
        boolean rowUpdated = false;
        try (
                Connection connection = getConnection();
                PreparedStatement prstmt = connection.prepareStatement(UPDATE_PRODUCT_ROW);
        ) {
            System.out.println(prstmt);
            int categoryId = ProductAction.checkCategory(product.getCategory());
            prstmt.setString(1, product.getProductName());
            prstmt.setDouble(2, product.getPrice());
            prstmt.setInt(3, product.getQuantity());
            prstmt.setString(4, product.getColor());
            prstmt.setString(5, product.getDescription());
            prstmt.setInt(6, categoryId);
            prstmt.setInt(7, product.getId());


            System.out.println(prstmt);

            rowUpdated = prstmt.executeUpdate() > 0;
            System.out.println("Update successful yet? " + rowUpdated);
        } catch (SQLException throwables) {
            ProductAction.printSQLException(throwables);
        }
        return rowUpdated;
    }

    @Override
    public boolean deleteProduct(int id) {
        boolean rowDeleted = false;
        try (
                Connection connection = getConnection();
                PreparedStatement prstmt = connection.prepareStatement(DELETE_PRODUCT_ROW);
        ) {
            System.out.println(prstmt);
            prstmt.setInt(1, id);
            rowDeleted = prstmt.executeUpdate() > 0;
            System.out.println("Successfully deleted? " + rowDeleted);
        } catch (SQLException throwables) {
            ProductAction.printSQLException(throwables);
        }
        return rowDeleted;
    }

    @Override
    public List<Product> searchProduct(String value) throws SQLException {
        List<Product> productList = new ArrayList<>();

        value = ProductAction.clearSpaceValue(value);
        try (
                Connection connection = getConnection();
                PreparedStatement prstmt = connection.prepareStatement(SEARCH_PRODUCT_ROWS);
        ) {
            System.out.println(prstmt);
            prstmt.setString(1, value);
            prstmt.setString(2, value);
            prstmt.setString(3, value);
            prstmt.setString(4, value);
            prstmt.setString(5, value);

            ResultSet rs = prstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("productName");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String color = rs.getString("color");
                String description = rs.getString("description");
                String categoryName = rs.getString("categoryName");
                productList.add(new Product(id,name,price,quantity,color,description,categoryName));
            }
        }
        return productList;
    }

    @Override
    public List<Product> sortProduct(String sortBy, String styleSort) {
        List<Product> productList = new ArrayList<>();
        if (!sortBy.equals("productName") && !sortBy.equals("price") && !sortBy.equals("quantity") && !sortBy.equals("color")&&!sortBy.equals("categoryName")) {
            sortBy = "productName";
        }
        if (!styleSort.equals("asc") && !styleSort.equals("desc")) {
            styleSort = "asc";
        }

        String SORT_PRODUCT_ROWS = "select id, productName, price, quantity,color,description,c.categoryName from product" +
                "    inner join Category C on Product.categoryId = C.categoryId order by " + sortBy + " " + styleSort;

        try (
                Connection connection = getConnection();
                PreparedStatement prstmts = connection.prepareStatement(SORT_PRODUCT_ROWS);
        ) {
            System.out.println(prstmts);
            ResultSet rs = prstmts.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("productName");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String color = rs.getString("color");
                String description = rs.getString("description");
                String categoryName = rs.getString("categoryName");
                productList.add(new Product(id,name,price,quantity,color,description,categoryName));
            }
        } catch (SQLException throwables) {
            ProductAction.printSQLException(throwables);
        }
        return productList;
    }

//    public static void main(String[] args) {
//        DatabaseC
//          databaseConnect = new DatabaseConnect();
//        databaseConnect.getConnection();
//    }
}
