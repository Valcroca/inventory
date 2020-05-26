package View_Controller;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class AddPartScreenController implements Initializable {
    Inventory inventory;
    private boolean isOutsourced = true;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public AddPartScreenController() {

    }

    public AddPartScreenController(Inventory inventory) { this.inventory = inventory; }

    @FXML
    private TextField addPartName;

    @FXML
    private TextField addPartInv;

    @FXML
    private TextField addPartPrice;

    @FXML
    private TextField addPartDynamicField;

    @FXML
    private TextField addPartMax;

    @FXML
    private TextField addPartMin;

    @FXML
    private RadioButton inHouseRadioButton;

    @FXML
    private RadioButton outsourcedRadioButton;

    @FXML
    private ToggleGroup partOrigin;

    @FXML
    private Label companyNameMachineID;

    @FXML
    private Button saveAddPartButton;

    @FXML
    private Button cancelAddPartButton;

    @FXML
    void inhouseRadioButtonSelected(ActionEvent event) {
        isOutsourced = false;
        companyNameMachineID.setText("Machine ID");
    }

    @FXML
    void outsourcedRadioButtonSelected(ActionEvent event) {
        isOutsourced = true;
        companyNameMachineID.setText("Company Name");
    }

    @FXML
    void saveAddPartHandler(ActionEvent event) throws IOException {
        System.out.println("Save button clicked in Add Part Screen");

        Random rand = new Random();
        int id = rand.nextInt(30);

        if (isOutsourced == false) {

            InhousePart newInhousePart = new InhousePart(0, "", 0, 0, 0, 0, 0);

            if (isValid(addPartName.getText(), addPartInv.getText(), addPartPrice.getText(), addPartMax.getText(), addPartMin.getText(), addPartDynamicField.getText())) {
                newInhousePart.setID(id);

                if (!addPartName.getText().isEmpty()) {
                    newInhousePart.setName(addPartName.getText());
                }
                if (!addPartPrice.getText().isEmpty()) {
                    newInhousePart.setPrice(Double.parseDouble(addPartPrice.getText()));
                }
                if (!addPartInv.getText().isEmpty()) {
                    newInhousePart.setStock(Integer.parseInt(addPartInv.getText()));
                }
                if (!addPartMin.getText().isEmpty()) {
                    newInhousePart.setMin(Integer.parseInt(addPartMin.getText()));
                }
                if (!addPartMax.getText().isEmpty()) {
                    newInhousePart.setMax(Integer.parseInt(addPartMax.getText()));
                }
                if (!addPartDynamicField.getText().isEmpty()) {
                    newInhousePart.setMachineID(Integer.parseInt(addPartDynamicField.getText()));
                }

                inventory.addPart(newInhousePart);
                System.out.println("Part Added");

                Stage stage;
                Parent root;
                stage=(Stage) saveAddPartButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
                root =loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

        } else {

            OutsourcedPart newOutsourcedPart = new OutsourcedPart(0, "", 0, 0, 0, 0, "");

            if (isValid(addPartName.getText(), addPartInv.getText(), addPartPrice.getText(), addPartMax.getText(), addPartMin.getText(), addPartDynamicField.getText())) {

                newOutsourcedPart.setID(id);
                if (!addPartName.getText().isEmpty()) {
                    newOutsourcedPart.setName(addPartName.getText());
                }
                if (!addPartPrice.getText().isEmpty()) {
                    newOutsourcedPart.setPrice(Double.parseDouble(addPartPrice.getText()));
                }
                if (!addPartInv.getText().isEmpty()) {
                    newOutsourcedPart.setStock(Integer.parseInt(addPartInv.getText()));
                }
                if (!addPartMin.getText().isEmpty()) {
                    newOutsourcedPart.setMin(Integer.parseInt(addPartMin.getText()));
                }
                if (!addPartMax.getText().isEmpty()) {
                    newOutsourcedPart.setMax(Integer.parseInt(addPartMax.getText()));
                }
                if (!addPartDynamicField.getText().isEmpty()) {
                    newOutsourcedPart.setCompanyName(addPartDynamicField.getText());
                }

                inventory.addPart(newOutsourcedPart);
                System.out.println("Part Added");

                Stage stage;
                Parent root;
                stage=(Stage) saveAddPartButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
                root =loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

        }

    }

    @FXML
    void cancelAddPartHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Cancel Add Part");
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


    public boolean isValid( String partName, String partStock, String partPrice, String partMax, String partMin, String partOrigin  ) {
        String errorMessage = "";
        Integer intMin = null, intMax = null;
        boolean isValid;

        if (partName == null || partName.isEmpty()) {
            errorMessage += ("Part Name cannot be blank\n");
        }

        try {
            intMin =  Integer.parseInt(partMin);
        } catch (NumberFormatException e) {
            errorMessage += ("Min must be a number\n");
        }

        try {
            intMax = Integer.parseInt(partMax);
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
            int intInv = Integer.parseInt(partStock);

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

        try {
            double price = Double.parseDouble(partPrice);
            if (price < 0) {
                errorMessage += ("Price cannot be less than zero");
            }
        } catch (NumberFormatException e) {
            errorMessage += ("Price cannot be blank and must be a number\n");
        }

        if (!isOutsourced) {
            if (!partOrigin.isEmpty()) {
                try {
                    Integer.parseInt(partOrigin);
                } catch (NumberFormatException e) {
                    errorMessage += ("MachineId must be a number");
                }
            } else {
                errorMessage += ("MachineID cannot be blank\n");
            }
        } else {
            if (partOrigin.isEmpty()) {
                errorMessage += ("Company name cannot be blank\n");
            }
        }

        if (errorMessage.isEmpty() == true) {
            isValid = true;
        } else {
            isValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Part Validation Error");
            alert.setHeaderText("Error");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }

        return isValid;

    }

}
