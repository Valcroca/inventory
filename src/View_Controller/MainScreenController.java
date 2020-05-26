package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class MainScreenController implements Initializable {
    Inventory inventory;
    Stage stage;
    Parent scene;

    @FXML
    private Button searchPartButton;

    @FXML
    private TextField searchPartTextField;

    @FXML
    private TableView<Part> partsTable;

    @FXML
    private TableColumn<Part, Integer> partID;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Integer> partInventoryLevel;

    @FXML
    private TableColumn<Part, Double> partCostCol;

    @FXML
    private Button searchProductButton;

    @FXML
    private TextField searchProductTextField;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, Integer> productID;

    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, Integer> productInventoryLevel;

    @FXML
    private TableColumn<Product, Double> productCostCol;

    private ObservableList<Part> partsInventory = FXCollections.observableArrayList();
    private ObservableList<Product> productsInventory = FXCollections.observableArrayList();

    public MainScreenController(Inventory inventory) { this.inventory = inventory; }

    public MainScreenController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generatePartsTable();
        generateProductsTable();
    }

    private void generatePartsTable() {
        partsTable.setItems(Inventory.getAllParts());
        partID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTable.refresh();
    }

    private void generateProductsTable() {
        productsTable.setItems(Inventory.getAllProducts());
        productID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsTable.refresh();
    }

    @FXML
    void addPartHandler(ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("/View_Controller/AddPartScreen.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }

    @FXML
    void addProductHandler(ActionEvent event) throws IOException {
        Parent addProductParent = FXMLLoader.load(getClass().getResource("/View_Controller/AddProductScreen.fxml"));
        Scene addProductScene = new Scene(addProductParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addProductScene);
        window.show();
    }

    @FXML
    void deletePartHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Part");
        alert.setContentText("Are you sure you want to delete this part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Part partToDelete = partsTable.getSelectionModel().getSelectedItem();
            Inventory.deletePart(partToDelete);
        }

    }

    @FXML
    void deleteProductHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Product");
        alert.setContentText("Are you sure you want to delete this product?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Product productToDelete = productsTable.getSelectionModel().getSelectedItem();
            Inventory.deleteProduct(productToDelete);
        }
    }

    @FXML
    void exitHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Exit Inventory Program");
        alert.setContentText("Are you sure you want to exit this program?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    void modifyPartHandler(ActionEvent event) throws IOException {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyPartScreen.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        ModifyPartScreenController controller = loader.getController();
        controller.setPart(selectedPart);
        stage.setScene( new Scene(root));
        stage.show();
    }

    @FXML
    void modifyProductHandler(ActionEvent event) throws IOException {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyProductScreen.fxml"));
        Parent root = loader.load();

        ModifyProductScreenController controller = loader.getController();
        controller.setProduct(selectedProduct);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void searchPartHandler(ActionEvent event) {
        String searchedPart = searchPartTextField.getText();
        for (Part part : Inventory.getAllParts()) {
            if (part.getName().equals(searchedPart) || Integer.toString(part.getID()).equals(searchedPart)) {
                partsTable.getSelectionModel().select(part);
            }
        }
    }

    @FXML
    void searchProductHandler(ActionEvent event) {
        String searchedProduct = searchProductTextField.getText();
        for (Product product : Inventory.getAllProducts()) {
            if (product.getName().equals(searchedProduct) || Integer.toString(product.getID()).equals(searchedProduct)) {
                productsTable.getSelectionModel().select(product);
            }
        }
    }


}
