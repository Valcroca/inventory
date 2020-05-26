package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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
import java.util.Random;
import java.util.ResourceBundle;

public class AddProductScreenController implements Initializable {
    Product newProduct = new Product(0,"",0,0,0,0);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateUnassociatedPartsTable();
        generateAssociatedPartsTable();
    }

    public AddProductScreenController() { }

    @FXML
    private Button saveAddProductButton;

    @FXML
    private Button cancelAddProductButton;

    @FXML
    private TextField addProductNameField;

    @FXML
    private TextField addProductInvField;

    @FXML
    private TextField addProductPriceField;

    @FXML
    private TextField addProductMaxField;

    @FXML
    private TextField addProductMinField;

    @FXML
    private TableView<Part> addPartToProductTable;

    @FXML
    private TableColumn<Part, Integer> partID;

    @FXML
    private TableColumn<Part, String> partName;

    @FXML
    private TableColumn<Part, Integer> partInventory;

    @FXML
    private TableColumn<Part, Double> partPrice;

    @FXML
    private Button searchProductButton;

    @FXML
    private TextField searchProductTextField;

    @FXML
    private Button addPartToProductButton;

    @FXML
    private TableView<Part> deletePartFromProductTable;

    @FXML
    private TableColumn<Part, Integer> assocPartId;

    @FXML
    private TableColumn<Part, String> assocPartName;

    @FXML
    private TableColumn<Part, Integer> assocPartInv;

    @FXML
    private TableColumn<Part, Double> assocPartPrice;

    @FXML
    private Button deletePartFromProductButton;



    private void generateAssociatedPartsTable() {
        if (!newProduct.getAllAssociatedParts().isEmpty()) {
            deletePartFromProductTable.setItems(newProduct.getAllAssociatedParts());
            assocPartId.setCellValueFactory(new PropertyValueFactory<>("ID"));
            assocPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
            assocPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
            assocPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            deletePartFromProductTable.refresh();
        }
    }

    private void generateUnassociatedPartsTable() {
        addPartToProductTable.setItems(Inventory.getAllParts());
        partID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        addPartToProductTable.refresh();
    }

    @FXML
    void addPartToProductHandler(ActionEvent event) {
        Part selectedPart = addPartToProductTable.getSelectionModel().getSelectedItem();

        newProduct.addAssociatedPart(selectedPart);
        generateAssociatedPartsTable();
        addPartToProductTable.refresh();
    }

    @FXML void cancelAddProductHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Cancel Add Product");
        alert.setContentText("Are you sure you want to go back?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Parent mainScreenParent = FXMLLoader.load(getClass().getResource("/View_Controller/MainScreen.fxml"));
            Scene mainScene = new Scene(mainScreenParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(mainScene);
            window.show();
        }
    }

    @FXML void deletePartFromProductHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Part from Product");
        alert.setContentText("Are you sure you want to delete this part from this product?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Part selectedPart = deletePartFromProductTable.getSelectionModel().getSelectedItem();
            newProduct.deleteAssociatedPart(selectedPart);
            addPartToProductTable.refresh();
        }
    }

    @FXML void saveAddProductHandler(ActionEvent event) throws IOException {
        Random rand = new Random();
        int id = rand.nextInt(30);

        if (inventoryIsValid(addProductInvField.getText(), addProductMinField.getText(), addProductMaxField.getText())) {
            newProduct.setID(id);
            if (!addProductNameField.getText().isEmpty()) {
                newProduct.setName(addProductNameField.getText());
            }
            if (!addProductPriceField.getText().isEmpty()) {
                newProduct.setPrice(Double.parseDouble(addProductPriceField.getText()));
            }
            if (!addProductInvField.getText().isEmpty()) {
                newProduct.setStock(Integer.parseInt(addProductInvField.getText()));
            }
            if (!addProductMinField.getText().isEmpty()) {
                newProduct.setMin(Integer.parseInt(addProductMinField.getText()));
            }
            if (!addProductMaxField.getText().isEmpty()) {
                newProduct.setMax(Integer.parseInt(addProductMaxField.getText()));
            }

            Inventory.addProduct(newProduct);
            System.out.println("Product was added");

            Stage stage;
            Parent root;
            stage=(Stage) saveAddProductButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
            root =loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    void searchProductsHandler(ActionEvent event) {
        String searchedPart = searchProductTextField.getText();
        ObservableList foundParts = Inventory.lookupPart(searchedPart);
        addPartToProductTable.setItems(foundParts);
    }

    public boolean inventoryIsValid(String productStock, String productMin, String productMax) {
        String errorMessage = "";
        Integer intMin = null, intMax = null;
        boolean inventoryIsValid;

        try {
            intMin =  Integer.parseInt(productMin);
        } catch (NumberFormatException e) {
            errorMessage += ("Min must be a number\n");
        }

        try {
            intMax = Integer.parseInt(productMax);
        } catch (NumberFormatException e) {
            errorMessage += ("Maximum must be a number\n");
        }

        try {
            if(intMin > intMax) {
                errorMessage += ("Minimum must be less than maximum\n");
            }
            if (intMin < 0 || intMax <0) {
                errorMessage += ("Quantity cannot be less than zero\n");
            }
        } catch (NullPointerException e) {
            errorMessage += ("Min and Max cannot be empty\n");
        }

        try {
            int intInv = Integer.parseInt(productStock);

            if (intMax != null && intMin != null) {
                if(intInv < intMin || intInv > intMax) {
                    errorMessage += ("Inventory must be between minimum and maximum\n");
                }
            } else {
                errorMessage += ("Inventory cannot be blank\n");
            }
        } catch (NumberFormatException e) {
            errorMessage += ("Inventory cannot be blank and must be a number\n");
        }

        if (errorMessage.isEmpty() == true) {
            inventoryIsValid = true;
        } else {
            inventoryIsValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Validation Error");
            alert.setHeaderText("Error");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }
        return inventoryIsValid;
    }
}
