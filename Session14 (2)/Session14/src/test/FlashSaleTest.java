package Test;
import service.OrderService;
import entity.Order_Details;
import java.util.*;
import java.util.concurrent.*;

public class FlashSaleTest {

    public static void main(String[] args) throws Exception {
        OrderService service = new OrderService();
        int THREADS = 50;
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        CountDownLatch latch = new CountDownLatch(THREADS);

        System.out.println("START FLASH SALE TEST...\n");

        for (int i = 0; i < THREADS; i++) {

            int userId = i + 1;

            executor.execute(() -> {

                try {
                    List<Order_Details> list = new ArrayList<>();
                    Order_Details d = new Order_Details();
                    d.setProduct_id(1);
                    d.setQuantity(1);

                    list.add(d);
                    service.placeOrder(userId, list);

                } catch (Exception e) {
                    System.out.println("Thread lỗi: " + e.getMessage());
                }

                latch.countDown();
            });
        }

        latch.await();
        executor.shutdown();
        System.out.println("\nTEST DONE!");
        System.out.println("Kiểm tra DB bằng SQL:");
        System.out.println("SELECT COUNT(*) FROM orders;");
        System.out.println("SELECT stock FROM products WHERE id = 1;");
    }
}