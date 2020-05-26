package sample;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void init() {
        System.out.println("Starting!");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Inventory inventory = new Inventory();
        addTestData(inventory);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1200, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void addTestData(Inventory inventory) {
        //in-house parts
        InhousePart part1 = new InhousePart(1, "part1", 1.99, 5, 5, 300, 101);
        InhousePart part2 = new InhousePart(2, "part2", 2.99, 6, 6, 300, 102);
        InhousePart part3 = new InhousePart(3, "part3", 3.99, 7, 7, 300, 103);
        inventory.addPart(part1);
        inventory.addPart(part2);
        inventory.addPart(part3);
        //outsourced parts
        OutsourcedPart part4 = new OutsourcedPart(4, "part4", 4.99, 8, 8, 300, "Acme");
        OutsourcedPart part5 = new OutsourcedPart(5, "part5", 5.99, 9, 8, 300, "Acme");
        OutsourcedPart part6 = new OutsourcedPart(6, "part6", 6.99, 10, 8, 300, "Acme");
        inventory.addPart(part4);
        inventory.addPart(part5);
        inventory.addPart(part6);
        //products
        Product product1 = new Product(1, "product1", 1.5, 1, 1, 30);
        Product product2 = new Product(2, "product2", 2.5, 1, 1, 30);
        Product product3 = new Product(3, "product3", 3.5, 1, 1, 30);
        inventory.addProduct(product1);
        inventory.addProduct(product2);
        inventory.addProduct(product3);
    }

    @Override
    public void stop() {
        System.out.println("Terminating!");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
