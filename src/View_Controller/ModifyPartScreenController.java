package View_Controller;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
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
import java.util.ResourceBundle;

public class ModifyPartScreenController implements Initializable {
   private boolean isOutsourced = true;
   Part part;
   private int index;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public ModifyPartScreenController() {

    }

    @FXML
    private TextField modifyPartIdField;

    @FXML
    private TextField modifyPartNameField;

    @FXML
    private TextField modifyPartInvField;

    @FXML
    private TextField modifyPartPriceField;

    @FXML
    private TextField modifyPartDynamicField;

    @FXML
    private TextField modifyPartMaxField;

    @FXML
    private TextField modifyPartMinField;

    @FXML
    private RadioButton inHouseRadioButton;

    @FXML
    private ToggleGroup partOrigin;

    @FXML
    private RadioButton outsourcedRadioButton;

    @FXML
    private Button saveModifyPartButton;

    @FXML
    private Button cancelAddPartButton;

    @FXML
    private Label companyNameMachineID;

    @FXML
    void cancelModifyPartHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Cancel Modify Part");
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
    void inhouseRadioButtonSelected(ActionEvent event) {
        isOutsourced = false;
        companyNameMachineID.setText("Machine ID");
    }

    @FXML
    void outsourcedRadioButtonSelected(ActionEvent event) {
        isOutsourced = true;
        companyNameMachineID.setText("Company Name");
    }

    public void setPart(Part part) {
        this.part = part;

        modifyPartIdField.setText(new Integer(part.getID()).toString());
        modifyPartNameField.setText(part.getName());
        modifyPartInvField.setText(new Integer(part.getStock()).toString());
        modifyPartPriceField.setText(new Double(part.getPrice()).toString());
        modifyPartMaxField.setText(new Integer(part.getMax()).toString());
        modifyPartMinField.setText(new Integer(part.getMin()).toString());

        if (part instanceof InhousePart) {
            inHouseRadioButton.fire();
            modifyPartDynamicField.setText(String.valueOf(((InhousePart) part).getMachineID()));
        } else {
            outsourcedRadioButton.fire();
            modifyPartDynamicField.setText(String.valueOf(((OutsourcedPart) part).getCompanyName()));
        }

    }

    @FXML
    void saveModifyPartHandler(ActionEvent event) throws IOException {
        System.out.println("Save button clicked in Modify Part Screen");

        InhousePart modifiedInhousePart = new InhousePart(0, "", 0, 0,0,0,0);
        index = Inventory.getAllParts().indexOf(part);

        if (isOutsourced == false) {

            if (isValid(modifyPartNameField.getText(), modifyPartInvField.getText(), modifyPartPriceField.getText(), modifyPartMaxField.getText(), modifyPartMinField.getText(), modifyPartDynamicField.getText())) {
                modifiedInhousePart.setID(Integer.parseInt(modifyPartIdField.getText()));

                if (!modifyPartNameField.getText().isEmpty()) {
                    modifiedInhousePart.setName(modifyPartNameField.getText());
                }
                if (!modifyPartPriceField.getText().isEmpty()) {
                    modifiedInhousePart.setPrice(Double.parseDouble(modifyPartPriceField.getText()));
                }
                if (!modifyPartInvField.getText().isEmpty()) {
                    modifiedInhousePart.setStock(Integer.parseInt(modifyPartInvField.getText()));
                }
                if (!modifyPartMinField.getText().isEmpty()) {
                    modifiedInhousePart.setMin(Integer.parseInt(modifyPartMinField.getText()));
                }
                if (!modifyPartMaxField.getText().isEmpty()) {
                    modifiedInhousePart.setMax(Integer.parseInt(modifyPartMaxField.getText()));
                }
                if (!modifyPartDynamicField.getText().isEmpty()) {
                    modifiedInhousePart.setMachineID(Integer.parseInt(modifyPartDynamicField.getText()));
                }

                Inventory.updatePart(index, modifiedInhousePart);
                System.out.println("Part was modified");

                Stage stage;
                Parent root;
                stage=(Stage) saveModifyPartButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
                root =loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

        } else {

            OutsourcedPart modifiedOutsourcedPart = new OutsourcedPart(0, "", 0, 0,0,0,"");

            if (isValid(modifyPartNameField.getText(), modifyPartInvField.getText(), modifyPartPriceField.getText(), modifyPartMaxField.getText(), modifyPartMinField.getText(), modifyPartDynamicField.getText())) {
                modifiedOutsourcedPart.setID(Integer.parseInt(modifyPartIdField.getText()));

                if (!modifyPartNameField.getText().isEmpty()) {
                    modifiedOutsourcedPart.setName(modifyPartNameField.getText());
                }
                if (!modifyPartPriceField.getText().isEmpty()) {
                    modifiedOutsourcedPart.setPrice(Double.parseDouble(modifyPartPriceField.getText()));
                }
                if (!modifyPartInvField.getText().isEmpty()) {
                    modifiedOutsourcedPart.setStock(Integer.parseInt(modifyPartInvField.getText()));
                }
                if (!modifyPartMinField.getText().isEmpty()) {
                    modifiedOutsourcedPart.setMin(Integer.parseInt(modifyPartMinField.getText()));
                }
                if (!modifyPartMaxField.getText().isEmpty()) {
                    modifiedOutsourcedPart.setMax(Integer.parseInt(modifyPartMaxField.getText()));
                }
                if (!modifyPartDynamicField.getText().isEmpty()) {
                    modifiedOutsourcedPart.setCompanyName(modifyPartDynamicField.getText());
                }

                Inventory.updatePart(index, modifiedOutsourcedPart);
                System.out.println("Part was modified");

                Stage stage;
                Parent root;
                stage=(Stage) saveModifyPartButton.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainScreen.fxml"));
                root =loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

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
