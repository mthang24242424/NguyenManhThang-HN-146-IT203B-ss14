package presentation;
import DAO.*;
import service.*;
import entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static UserDAO userDAO = new UserDAO();
    private static ProductDAO productDAO = new ProductDAO();
    private static OrderDAO orderDAO = new OrderDAO();
    private static OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
    private static Scanner sc = new Scanner(System.in);
    public static void menu() {
        System.out.println("\n========== INVENTORY & FLASH SALE SYSTEM ==========");
        System.out.println("1. Quản lý Users");
        System.out.println("2. Quản lý Products");
        System.out.println("3. Đặt hàng (Flash Sale)");
        System.out.println("4. Báo cáo & Thống kê");
        System.out.println("0. Thoát");
        System.out.print("Chọn chức năng: ");
    }
    public static void menuProducts() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n------ QUẢN LÝ PRODUCTS ------");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Sửa sản phẩm");
            System.out.println("3. Xóa sản phẩm");
            System.out.println("4. Danh sách sản phẩm");
            System.out.println("5. Cập nhật tồn kho");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");

            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    while (true) {
                        Products products=new Products();
                        System.out.print("moi ban nhap ten san pham:");
                        String name = sc.nextLine().trim();
                        products.setProduct_name(name);
                        System.out.print("moi ban nhap gia san pham:");
                        while (true){
                            try {
                                double price= (int) Double.parseDouble(sc.nextLine().trim());
                                products.setPrice(price);
                                break;
                            }catch (Exception e){
                                System.out.println("Gia san pham phai la so!");
                                System.out.print("nhap lai: ");
                            }
                        }
                        System.out.print("moi ban nhap so luong san pham:");
                        while (true){
                            try {
                                int stock=Integer.parseInt(sc.nextLine());
                                products.setStock(stock);
                                break;
                            }catch (Exception e){
                                System.out.println("So luong san pham phai la so!");
                                System.out.print("nhap lai: ");
                            }
                        }
                        productDAO.insertProduct(products);
                        System.out.print("Tiếp tục thêm sản phẩm? (y/n): ");
                        String choices = sc.nextLine().trim().toLowerCase();
                        if (!choices.equalsIgnoreCase("y")) {
                            break;
                        }
                    }
                    break;
                case 2:
                    while (true) {
                        System.out.print("Nhập product_id: ");
                        String input = sc.nextLine().trim();
                        try {
                            int id = Integer.parseInt(input);
                            Optional<Products>optionalProducts = productDAO.findProductOptionalById(id);
                            if(optionalProducts.isPresent()){
                                Products products=new Products();
                                products=optionalProducts.get();
                                String name;
                                while (true) {
                                    System.out.print("Nhập tên sản phẩm: ");
                                    name = sc.nextLine().trim();

                                    if (name.isEmpty()) {
                                        System.out.println("Tên không được để trống!");
                                    } else {
                                        break;
                                    }
                                }
                                products.setProduct_name(name);
                                int stock;
                                while (true) {
                                    System.out.print("Nhập số lượng: ");
                                    String stockInput = sc.nextLine().trim();

                                    try {
                                        stock = Integer.parseInt(stockInput);
                                        if (stock < 0) {
                                            System.out.println("Stock không được âm!");
                                        } else {
                                            break;
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Phải nhập số nguyên!");
                                    }
                                }
                                products.setStock(stock);
                                double price;
                                while (true) {
                                    System.out.print("Nhập giá: ");
                                    String priceInput = sc.nextLine().trim();

                                    try {
                                        price = Double.parseDouble(priceInput);
                                        if (price < 0) {
                                            System.out.println("Giá không được âm!");
                                        } else {
                                            break;
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Phải nhập số!");
                                    }
                                }
                                products.setPrice(price);
                                productDAO.updateProduct(products);
                                System.out.println("Cập nhật thành công!");
                            }
                            if (id <= 0) {
                                System.out.println("ID phải > 0!");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Vui lòng nhập số nguyên hợp lệ!");
                        }
                    }
                    break;
                case 3:
                    System.out.print("moi ban nhap id:");
                    while (true){
                        try {
                            int id=Integer.parseInt(sc.nextLine());

                            boolean delete=productDAO.deleteProduct(id);
                            if(delete){
                                System.out.println("Xóa thành công!");
                            }else{
                                System.out.println("Xóa thất bại!");
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Vui lòng nhập số nguyên hợp lệ!");
                            System.out.print("nhap lai:");
                        }
                        break;
                    }
                case 4:
                    List<Products> products=productDAO.getAllProducts();
                    products.forEach(System.out::println);
                    break;
                case 5:
                    while (true){
                        System.out.print("Nhập product_id: ");
                        String input = sc.nextLine().trim();
                        try {
                            int id = Integer.parseInt(input);
                            Optional<Products> optionalProducts = productDAO.findProductOptionalById(id);
                            if(optionalProducts.isPresent()){
                                Products product = optionalProducts.get();
                                int stock;
                                while (true) {
                                    System.out.print("Nhập số lượng: ");
                                    String stockInput = sc.nextLine().trim();

                                    try {
                                        stock = Integer.parseInt(stockInput);
                                        if (stock < 0) {
                                            System.out.println("Stock không được âm!");
                                        } else {
                                            break;
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Phải nhập số nguyên!");
                                    }
                                }
                                product.setStock(stock);
                                productDAO.updateProduct(product);
                                System.out.println("Cập nhật thành công!");
                                break;
                            }else{
                                System.out.println("Không tìm thấy sản phẩm!");
                            }
                            break;
                        }catch (NumberFormatException e){
                            System.out.println("nhap lai...");
                        }

                    }
                    break;

                case 0:
                    System.out.println("Quay lại...");
                    break;
                default:
                    System.out.println("Sai lựa chọn!");
            }

        } while (choice != 0);
    }
    public static void menuUsers() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n------ QUẢN LÝ USERS ------");
            System.out.println("1. Thêm user");
            System.out.println("2. Sửa user");
            System.out.println("3. Xóa user");
            System.out.println("4. Danh sách user");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");

            try {
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        System.out.println("=== Thêm user ===");

                        String username;
                        while (true) {
                            System.out.print("Nhập username: ");
                            username = sc.nextLine().trim();
                            if (username.isEmpty()) {
                                System.out.println("Không được để trống!");
                            } else break;
                        }

                        String password;
                        while (true) {
                            System.out.print("Nhập password: ");
                            password = sc.nextLine().trim();
                            if (password.isEmpty()) {
                                System.out.println("Không được để trống!");
                            } else break;
                        }

                        String role;
                        while (true) {
                            System.out.print("Nhập role (admin/user): ");
                            role = sc.nextLine().trim().toLowerCase();
                            if (!role.equals("admin") && !role.equals("user")) {
                                System.out.println("Chỉ được nhập admin hoặc user!");
                            } else break;
                        }

                        Users newUser = new Users();
                        newUser.setUsername(username);
                        newUser.setPassword(password);
                        newUser.setRole(role);

                        if (userDAO.insertUser(newUser)) {
                            System.out.println("Thêm thành công!");
                        } else {
                            System.out.println("Thêm thất bại!");
                        }
                        break;
                    case 2:
                        System.out.println("=== Sửa user ===");

                        int updateId;
                        while (true) {
                            try {
                                System.out.print("Nhập user_id: ");
                                updateId = Integer.parseInt(sc.nextLine());
                                if (updateId <= 0) {
                                    System.out.println("ID phải > 0!");
                                } else break;
                            } catch (Exception e) {
                                System.out.println("Phải nhập số!");
                            }
                        }

                        Users userUpdate = userDAO.findUserById(updateId);

                        if (userUpdate == null) {
                            System.out.println("Không tìm thấy user!");
                            break;
                        }

                        System.out.println("User cũ: " + userUpdate.getUsername());
                        System.out.print("Username mới (Enter để giữ): ");
                        String newUsername = sc.nextLine().trim();
                        if (!newUsername.isEmpty()) {
                            userUpdate.setUsername(newUsername);
                        }

                        System.out.print("Password mới (Enter để giữ): ");
                        String newPassword = sc.nextLine().trim();
                        if (!newPassword.isEmpty()) {
                            userUpdate.setPassword(newPassword);
                        }

                        System.out.print("Role mới (admin/user, Enter để giữ): ");
                        String newRole = sc.nextLine().trim().toLowerCase();
                        if (!newRole.isEmpty()) {
                            if (newRole.equals("admin") || newRole.equals("user")) {
                                userUpdate.setRole(newRole);
                            } else {
                                System.out.println("Role không hợp lệ! Giữ nguyên.");
                            }
                        }

                        if (userDAO.updateUser(userUpdate)) {
                            System.out.println("Cập nhật thành công!");
                        } else {
                            System.out.println("Cập nhật thất bại!");
                        }
                        break;

                    // ================== DELETE ==================
                    case 3:
                        System.out.println("=== Xóa user ===");

                        int deleteId;
                        while (true) {
                            try {
                                System.out.print("Nhập user_id: ");
                                deleteId = Integer.parseInt(sc.nextLine());
                                if (deleteId <= 0) {
                                    System.out.println("ID phải > 0!");
                                } else break;
                            } catch (Exception e) {
                                System.out.println("Phải nhập số!");
                            }
                        }

                        if (userDAO.deleteUser(deleteId)) {
                            System.out.println("Xóa thành công!");
                        } else {
                            System.out.println("Không tìm thấy hoặc xóa thất bại!");
                        }
                        break;
                    case 4:
                        System.out.println("=== Danh sách user ===");

                        List<Users> list = userDAO.getAllUsers();

                        if (list.isEmpty()) {
                            System.out.println("Không có user!");
                        } else {
                            for (Users u : list) {
                                System.out.println(
                                        "ID: " + u.getUser_id() +
                                                " | Username: " + u.getUsername() +
                                                " | Role: " + u.getRole() +
                                                " | Created: " + u.getCreated_at()
                                );
                            }
                        }
                        break;

                    case 0:
                        System.out.println("Quay lại...");
                        break;

                    default:
                        System.out.println("Sai lựa chọn!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!");
                choice = -1;
            }
        } while (choice != 0);
    }
    public static void menuOrder() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n------ FLASH SALE ------");
            System.out.println("1. Tạo đơn hàng");
            System.out.println("2. Xem danh sách đơn");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    int oderid=orderDAO.createOrder();
                    break;
                case 2:
                    orderDAO.getall();
                    break;
                case 0:
                    System.out.println("Quay lại...");
                    break;
                default:
                    System.out.println("Sai lựa chọn!");
            }

        } while (choice != 0);
    }
    public static void menuReport() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n------ BÁO CÁO ------");
            System.out.println("1. Top 5 khách hàng mua nhiều nhất");
            System.out.println("2. Doanh thu theo danh mục");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    orderDAO.getTop5Customers();
                    break;
                case 2:
                    System.out.println("Doanh thu theo danh mục...");
                    break;
                case 0:
                    System.out.println("Quay lại...");
                    break;
                default:
                    System.out.println("Sai lựa chọn!");
            }

        } while (choice != 0);
    }
    public static void main(String[] args) {

        int choice=0;
       do{
           menu();
        choice = sc.nextInt();

        switch (choice) {
            case 1:
                menuUsers();
                break;
            case 2:
                menuProducts();
                break;
            case 3:
                menuOrder();
                break;
            case 4:
                menuReport();
                break;
            case 0:
                System.out.println("Thoát...");
                break;
            default:
                System.out.println("Sai lựa chọn!");
        }
    } while (choice != 0);
    }
}
