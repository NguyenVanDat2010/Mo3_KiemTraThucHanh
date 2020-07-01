package product.service.action;

import java.sql.SQLException;

public class ProductAction {

    public static int checkCategory(String category) {
        if (category.equals("phone")) {
            return 1;
        } else if (category.equals("television")) {
            return 2;
        } else if (category.equals("motorcycle")) {
            return 3;
        }else
            return 4;
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    public static String clearSpaceValue(String value) {
        if (value != "") {
            if (value.charAt(0) == ' ' || value.charAt(value.length() - 1) == ' ') {
                value = value.trim(); //xóa khoảng trắng ở đầu và cuối thôi
            }
            return value;
        }
        return "";
    }
}
