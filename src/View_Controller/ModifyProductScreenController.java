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
import java.util.ResourceBundle;


public class ModifyProductScreenController implements Initializable {

    Product product;
    private int index;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateUnassociatedPartsTable();

    }

    public ModifyProductScreenController() {

    }

    @FXML
    private TextField modifyProductIdField;

    @FXML
    private TextField modifyProductNameField;

    @FXML
    private TextField modifyProductInvField;

    @FXML
    private TextField modifyProductPriceField;

    @FXML
    private TextField modifyProductMaxField;

    @FXML
    private TextField modifyProductMinField;

    @FXML
    private Button saveModifyProductButton;

    @FXML
    private Button cancelModifyProductButton;

    @FXML
    private TableView<Part> addPartsToProductTable;

    @FXML
    private TableColumn<Part, Integer> partIDCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> inventoryLevelCol;

    @FXML
    private TableColumn<Part, Double> priceCol;

    @FXML
    private Button searchProductButton;

    @FXML
    private TextField searchProductTextField;

    @FXML
    private Button addPartToProductButton;

    @FXML
    private TableView<Part> deletePartFromProductTable;

    @FXML
    private TableColumn<Part, Integer> partIDColDelete;

    @FXML
    private TableColumn<Part, String> partNameColDelete;

    @FXML
    private TableColumn<Part, Integer> inventoryLevelColDelete;

    @FXML
    private TableColumn<Part, Double> priceColDelete;

    @FXML
    private Button deletePartFromProductButton;

    private void generateAssociatedPartsTable() {
        if (!product.getAllAssociatedParts().isEmpty()) {
            deletePartFromProductTable.setItems(product.getAllAssociatedParts());
            partIDColDelete.setCellValueFactory(new PropertyValueFactory<>("ID"));
            partNameColDelete.setCellValueFactory(new PropertyValueFactory<>("name"));
            inventoryLevelColDelete.setCellValueFactory(new PropertyValueFactory<>("stock"));
            priceColDelete.setCellValueFactory(new PropertyValueFactory<>("price"));
            deletePartFromProductTable.refresh();
        }
    }

    private void generateUnassociatedPartsTable() {
        addPartsToProductTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        addPartsToProductTable.refresh();
    }

    @FXML
    void addPartToProductHandler(ActionEvent event) {
        Part selectedPart = addPartsToProductTable.getSelectionModel().getSelectedItem();

        product.addAssociatedPart(selectedPart);
        generateAssociatedPartsTable();
    }

    @FXML
    void cancelModifyProductHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Cancel Modify Product");
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

    @FXML
    void deletePartFromProductHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete Part from Product");
        alert.setContentText("Are you sure you want to delete this part from this product?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Part partToDelete = deletePartFromProductTable.getSelectionModel().getSelectedItem();
            product.deleteAssociatedPart(partToDelete);
            generateAssociatedPartsTable();
        }
    }

    public void setProduct(Product product) {
        this.product = product;

        modifyProductIdField.setText(new Integer(product.getID()).toString());
        modifyProductNameField.setText(product.getName());
        modifyProductInvField.setText(new Integer(product.getStock()).toString());
        modifyProductPriceField.setText(new Double(product.getPrice()).toString());
        modifyProductMaxField.setText(new Integer(product.getMax()).toString());
        modifyProductMinField.setText(new Integer(product.getMax()).toString());

        product.getAllAssociatedParts();
        generateAssociatedPartsTable();
    }

    @FXML
    void saveModifyProductHandler(ActionEvent event) throws IOException {
        System.out.println("Save button clicked in Modify Product Screen");

        index = Inventory.getAllProducts().indexOf(product);

        Product modifiedProduct = product;

        if (inventoryIsValid(modifyProductInvField.getText(), modifyProductMinField.getText(), modifyProductMaxField.getText())) {
            modifiedProduct.setID(Integer.parseInt(modifyProductIdField.getText()));
            if (!modifyProductNameField.getText().isEmpty()) {
                modifiedProduct.setName(modifyProductNameField.getText());
            }
            if (!modifyProductPriceField.getText().isEmpty()) {
                modifiedProduct.setPrice(Double.parseDouble(modifyProductPriceField.getText()));
            }
            if (!modifyProductInvField.getText().isEmpty()) {
                modifiedProduct.setStock(Integer.parseInt(modifyProductInvField.getText()));
            }
            if (!modifyProductMinField.getText().isEmpty()) {
                modifiedProduct.setMin(Integer.parseInt(modifyProductMinField.getText()));
            }
            if (!modifyProductMaxField.getText().isEmpty()) {
                modifiedProduct.setMax(Integer.parseInt(modifyProductMaxField.getText()));
            }

            modifiedProduct.getAllAssociatedParts();
            Inventory.updateProduct(index, modifiedProduct);
            System.out.println("Product was modified");

            Stage stage;
            Parent root;
            stage=(Stage) saveModifyProductButton.getScene().getWindow();
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
        addPartsToProductTable.setItems(foundParts);
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


