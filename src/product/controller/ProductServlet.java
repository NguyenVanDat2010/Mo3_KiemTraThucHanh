package product.controller;

import product.model.Product;
import product.service.IProductService;
import product.service.ProductService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ProductServlet",urlPatterns = "/products")
public class ProductServlet extends HttpServlet {
    IProductService productService = new ProductService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        try {
            switch (action){
                case "sort":
                    sortProduct(request, response);
                    break;
                case "search":
                    searchProduct(request, response);
                    break;
                case "delete":
                    deleteProduct(request,response);
                    break;
                case "create":
                    insertProduct(request, response);
                    break;
                case "edit":
                    updateProduct(request, response);
                    break;
                default:
                    break;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void sortProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sortBy = request.getParameter("sortBy");
        String styleSort = request.getParameter("styleSort");

        List<Product> productList = this.productService.sortProduct(sortBy, styleSort);

        request.setAttribute("products", productList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/list.jsp");
        dispatcher.forward(request, response);
    }

    private void searchProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String value = request.getParameter("searchValue");
        List<Product> productList = this.productService.searchProduct(value);

        RequestDispatcher dispatcher = null;
        if (productList == null) {
            request.setAttribute("message","There are no record match with your case!");
//            dispatcher = request.getRequestDispatcher("error-404.jsp");
        } else {
            request.setAttribute("products", productList);
        }
        dispatcher = request.getRequestDispatcher("product/list.jsp");
        dispatcher.forward(request, response);
    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String color = request.getParameter("color");
        String description = request.getParameter("description");
        String category = request.getParameter("category");

        Product product = new Product(name,price,quantity,color,description,category);
        this.productService.insertProduct(product);

        RequestDispatcher dispatcher = request.getRequestDispatcher("product/createAndEdit.jsp");
        request.setAttribute("message", "New product was create");

        dispatcher.forward(request, response);
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String color = request.getParameter("color");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        int id = Integer.parseInt(request.getParameter("id"));

        Product product = new Product(id, name,price,quantity,color,description,category);

       this.productService.updateProduct(product);

        request.getRequestDispatcher("product/createAndEdit.jsp");
        response.sendRedirect("/products");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action){
            case "create":
                showInsertProduct(request, response);
                break;
            case "edit":
                showUpdateProduct(request, response);
                break;
            case "delete":
                deleteProduct(request, response);
                break;
            default:
                showAllProduct(request,response);
                break;
        }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        this.productService.deleteProduct(id);

        List<Product> productList = this.productService.selectAllProduct();
        request.setAttribute("products", productList);

        RequestDispatcher dispatcher = request.getRequestDispatcher("product/list.jsp");
        dispatcher.forward(request, response);
    }

    private void showUpdateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = this.productService.selectProductById(id);
        List<Product> listProductCategory = this.productService.selectAllProduct();

        request.setAttribute("product", product);

        request.setAttribute("listCategory",listProductCategory);

        RequestDispatcher dispatcher = request.getRequestDispatcher("product/createAndEdit.jsp");
        dispatcher.forward(request, response);
    }

    private void showInsertProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/createAndEdit.jsp");
        dispatcher.forward(request, response);
    }

    private void showAllProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> productList = this.productService.selectAllProduct();

        request.setAttribute("products", productList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product/list.jsp");
        dispatcher.forward(request, response);
    }

}
