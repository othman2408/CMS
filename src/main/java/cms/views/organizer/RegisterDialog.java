package cms.views.organizer;

import cms.DB.DBConnector;
import cms.Entities.Reviewer;
import cms.views.shardCom.Notify;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import java.sql.SQLException;

public class RegisterDialog extends Div {
    static DBConnector dbConnector;

    static {
        try {
            dbConnector = new DBConnector();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static TextField userNameField;
    private static TextField name;
    private static PasswordField password;
    private static EmailField email;

    public RegisterDialog(String title, String buttonName) throws SQLException {
        // tag::snippet[]
        Dialog dialog = new Dialog();
        dialog.addClassName("register-dialog");

        dialog.setHeaderTitle(title);

        VerticalLayout dialogLayout = createDialogLayout();
        dialog.add(dialogLayout);

        Button saveButton = createSaveButton(dialog);
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(saveButton);

        Button button = new Button(buttonName, e -> dialog.open());
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(dialog, button);

    }

    private static VerticalLayout createDialogLayout() {

        name = new TextField("Name");
        name.setRequired(true);
        name.setRequiredIndicatorVisible(true);
        name.setPlaceholder("Enter reviewer's full name");

        userNameField = new TextField("Username");
        userNameField.setRequired(true);
        userNameField.setRequiredIndicatorVisible(true);
        userNameField.setPlaceholder("Enter reviewer's username");
        userNameField.setMinLength(4);
        userNameField.setErrorMessage("Username must be at least 4 characters long");

        password = new PasswordField("Password");
        password.setRequired(true);
        password.setRequiredIndicatorVisible(true);
        password.setPlaceholder("Enter reviewer's password");
        password.setMinLength(8);
        password.setErrorMessage("Password must be at least 8 characters long");

        email = new EmailField("Email");
        email.setRequired(true);
        email.setRequiredIndicatorVisible(true);
        email.setPlaceholder("Enter reviewer's email");
        email.setErrorMessage("Please enter a valid email address");

        VerticalLayout dialogLayout = new VerticalLayout(name, userNameField, password, email);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "18rem").set("max-width", "100%");

        return dialogLayout;
    }

    private static Button createSaveButton(Dialog dialog) throws SQLException {

        Button saveButton = new Button("Add", e -> {
            try {
                boolean check = dbConnector.checkForUser(userNameField.getValue());
                if (check) {
                    Notify.notify("User already exists, please try again", 3000, "error");
                } else {
                    boolean register = dbConnector.registerUser(new Reviewer(userNameField.getValue(), password.getValue(), name.getValue(), email.getValue()));
                    if (!register) {
                        Notify.notify("Something went wrong, please try again", 3000, "error");
                        return;
                    }else {
                        Notify.notify("User added successfully", 3000, "success");
                        dialog.close();

                        // Refresh the page to show the new user
                        UI.getCurrent().getPage().executeJs("setTimeout(function() {location.reload();}, 2000);");


                        // Clear fields after closing dialog
                        userNameField.clear();
                        password.clear();
                        name.clear();
                        email.clear();
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        return saveButton;
    }

    private void addOpenedChangeListener(Dialog dialog) {
        dialog.addOpenedChangeListener(e -> {
            if (!e.isOpened()) {
                // Clear fields
                userNameField.clear();
                password.clear();
                name.clear();
                email.clear();
            }
        });
    }



}
