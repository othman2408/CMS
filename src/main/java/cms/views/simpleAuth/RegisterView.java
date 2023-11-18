package cms.views.simpleAuth;

import cms.DB.DBConnector;
import cms.Entity.Author;
import cms.Entity.Organizer;
import cms.Entity.Reviewer;
import cms.Entity.User;
import cms.views.shardCom.Notify;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.sql.SQLException;

/**
 * This class represents the registration view of the CMS application.
 * It allows users to register by filling in their personal information and
 * selecting a role.
 * Upon successful registration, the user is redirected to the login page.
 */
@Route("register")
@PageTitle("Register")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

        /**
         * Constructor for RegisterView.
         * Sets the layout and creates the registration form.
         */
        public RegisterView() {
                setLayout();
                Div registerForm = createRegisterForm();
                add(registerForm);

                getStyle().set("background-image",
                        " linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%)");
        }

        /**
         * Sets the layout of the RegisterView.
         */
        private void setLayout() {
                setAlignItems(Alignment.CENTER);
                setJustifyContentMode(JustifyContentMode.CENTER);
                setHeightFull();
        }

        /**
         * Creates the registration form.
         * 
         * @return A Div containing the registration form.
         */
        private Div createRegisterForm() {
                Div div = new Div();
                setFormStyles(div);

                Div header = createHeader();
                Div fieldsContainer = createFieldsContainer();

                div.add(header, fieldsContainer);
                return div;
        }

        /**
         * Sets the styles for the registration form.
         * 
         * @param div The Div containing the registration form.
         */
        private void setFormStyles(Div div) {
                div.getStyle()
                                .set("display", "flex")
                                .set("flex-direction", "column")
                                .set("justify-content", "center")
                                .set("align-items", "center")
                                .set("background-color", "#f5f5f5")
                                .set("border-radius", "5px")
                                .set("box-shadow", "rgb(127 127 127 / 20%) 0px 0px 20px 0px")
                                .set("border", "1px solid rgb(0 0 0 / 4%)")
                                .set("font-family", "Roboto, sans-serif")
                                .set("min-width", "400px")
                                .set("width", "25%");
        }

        /**
         * Creates the header for the registration form.
         * 
         * @return A Div containing the header.
         */
        private Div createHeader() {
                Div header = new Div();
                H1 title = new H1("Welcome to CMS");
                Span subtitle = new Span("Register to continue");

                title.getStyle()
                                .set("font-size", "32px")
                                .set("font-weight", "bold")
                                .set("margin-bottom", "2px")
                                .set("color", "white");
                subtitle.getStyle()
                                .set("font-size", "18px")
                                .set("font-weight", "normal")
                                .set("margin-top", "0px")
                                .set("color", "white");
                header.getStyle()
                                .set("font-size", "24px")
                                .set("font-weight", "bold")
                                .set("background-image", "linear-gradient(135deg, #667eea 0%, #764ba2 100%)")
                                .set("color", "white")
                                .set("padding", "6rem 2rem 2rem 2rem")
                                .set("border-radius", "5px 5px 0 0")
                                .set("width", "-webkit-fill-available")
                                .set("user-select", "none");

                header.add(title, subtitle);
                return header;
        }

        /**
         * Creates the container for the registration form fields.
         * 
         * @return A Div containing the fields container.
         */
        private Div createFieldsContainer() {
                Div fieldsContainer = new Div();
                setFieldsContainerStyles(fieldsContainer);

                TextField username = createTextField("Username", "Please enter your username", true);
                PasswordField password = createPasswordField("Password", "Please enter your password", true);
                TextField name = createTextField("Name", "Please enter your name", true);
                EmailField email = createEmailField("Email", "Please enter your email", true);
                Select<String> role = createRoleSelect();

                Button registerButton = createRegisterButton(username, password, name, email, role);
                Button login = createLoginButton();

                fieldsContainer.add(name, username, password, email, role, registerButton, login);
                return fieldsContainer;
        }

        /**
         * Sets the styles for the fields container.
         * 
         * @param fieldsContainer The Div containing the fields container.
         */
        private void setFieldsContainerStyles(Div fieldsContainer) {
                fieldsContainer.getStyle()
                                .set("display", "flex")
                                .set("flex-direction", "column")
                                .set("justify-content", "center")
                                .set("align-items", "center")
                                .set("width", "85%")
                                .set("padding", ".5rem 0px 0px 0px");
        }

        /**
         * Creates a TextField with the given label, placeholder, and required status.
         * 
         * @param label       The label for the TextField.
         * @param placeholder The placeholder for the TextField.
         * @param required    Whether the TextField is required or not.
         * @return The created TextField.
         */
        private TextField createTextField(String label, String placeholder, boolean required) {
                TextField textField = new TextField(label);
                textField.setPlaceholder(placeholder);
                textField.setRequired(required);
                textField.getStyle()
                                .set("margin-bottom", "15px")
                                .set("padding", "10px")
                                .set("border-radius", "4px")
                                .set("width", "100%");
                return textField;
        }

        /**
         * Creates a PasswordField with the given label, placeholder, and required
         * status.
         *
         * @param label       The label for the PasswordField.
         * @param placeholder The placeholder for the PasswordField.
         * @param required    Whether the PasswordField is required or not.
         * @return The created PasswordField.
         */
        private PasswordField createPasswordField(String label, String placeholder, boolean required) {
                PasswordField passwordField = new PasswordField(label);
                passwordField.setPlaceholder(placeholder);
                passwordField.setRequired(required);
                passwordField.getStyle()
                                .set("margin-bottom", "15px")
                                .set("padding", "10px")
                                .set("border-radius", "4px")
                                .set("width", "100%");
                return passwordField;
        }

        /**
         * Creates an EmailField with the given label, placeholder, and required status.
         * 
         * @param label       The label for the EmailField.
         * @param placeholder The placeholder for the EmailField.
         * @param required    Whether the EmailField is required or not.
         * @return The created EmailField.
         */
        private EmailField createEmailField(String label, String placeholder, boolean required) {
                EmailField emailField = new EmailField(label);
                emailField.setPlaceholder(placeholder);
                emailField.setRequired(required);
                emailField.getStyle()
                                .set("margin-bottom", "15px")
                                .set("padding", "10px")
                                .set("border-radius", "4px")
                                .set("width", "100%");
                return emailField;
        }

        /**
         * Creates a Select with the available roles.
         * 
         * @return The created Select.
         */
        private Select<String> createRoleSelect() {
                Select<String> role = new Select<>();
                role.setLabel("Role");
                role.setItems("Organizer", "Author", "Reviewer");
                role.setPlaceholder("Please select your role");
                role.getStyle()
                                .set("margin-bottom", "15px")
                                .set("padding", "10px")
                                .set("border-radius", "4px")
                                .set("width", "100%");
                return role;
        }

        /**
         * Creates the Register button and sets its click listener.
         * 
         * @param username The TextField for the username.
         * @param password The PasswordField for the password.
         * @param name     The TextField for the name.
         * @param email    The EmailField for the email.
         * @param role     The Select for the role.
         * @return The created Register button.
         */
        private Button createRegisterButton(TextField username, PasswordField password, TextField name,
                        EmailField email, Select<String> role) {
                Button registerButton = new Button("Register");
                registerButton.getStyle()
                                .set("margin-bottom", "15px")
                                .set("padding", "10px")
                                .set("border-radius", "4px")
                                .set("width", "100%")
                                .set("background-color", "hsl(236.29deg 47.78% 60.2%)")
                                .set("color", "white")
                                .setCursor("pointer");

                registerButton.addClickListener(e -> handleRegistration(username, password, name, email, role));

                return registerButton;
        }

        /**
         * Creates the login button and sets its click listener.
         *
         * @return The created Register button.
         */
        private Button createLoginButton() {
                Button loginBtn = new Button("Login");
                loginBtn.getStyle()
                        .set("margin-bottom", "15px")
                        .set("padding", "10px")
                        .set("border-radius", "4px")
                        .set("width", "100%")
                        .set("background-color", "transparent")
                        .setCursor("pointer");

                loginBtn.addClickListener(e -> UI.getCurrent().navigate(""));

                return loginBtn;
        }

        /**
         * Handles the registration process.
         * 
         * @param username The TextField for the username.
         * @param password The PasswordField for the password.
         * @param name     The TextField for the name.
         * @param email    The EmailField for the email.
         * @param role     The Select for the role.
         */
        private void handleRegistration(TextField username, PasswordField password, TextField name,
                        EmailField email, Select<String> role) {
                if (username.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty() || role.isEmpty()) {
                        Notify.notify("Please fill all the fields", 3000, "warning");
                } else {
                        User user = switch (role.getValue()) {
                                case "Organizer" -> new Organizer(username.getValue(), password.getValue(),
                                                name.getValue(), email.getValue());
                                case "Author" -> new Author(username.getValue(), password.getValue(), name.getValue(),
                                                email.getValue());
                                case "Reviewer" -> new Reviewer(username.getValue(), password.getValue(),
                                                name.getValue(), email.getValue());
                                default -> throw new IllegalStateException("Unexpected value: " + role.getValue());
                        };
                        System.out.println(user.getName() + " "
                                        + user.getUsername() + " "
                                        + user.getPassword() + " "
                                        + user.getEmail() + " "
                                        + user.getClass().getSimpleName());

                        try {
                                registerUser(user);
                                Notify.notify("User registered successfully", 2000, "success");

                                getUI().ifPresent(ui -> ui.getPage().executeJs(
                                                "setTimeout(function() { window.location.href = ''; }, 2000)"));

                        } catch (SQLException throwables) {
                                throwables.printStackTrace();
                                Notify.notify("Error: " + throwables.getMessage(), 3000, "error");
                        }
                }
        }

        /**
         * Registers a user in the database.
         * 
         * @param user The User to be registered.
         * @throws SQLException If there is an error with the database connection.
         */
        private void registerUser(User user) throws SQLException {
                DBConnector db = new DBConnector();
                try {
                        db.registerUser(user);
                } catch (SQLException throwable) {
                        throwable.printStackTrace();
                        throw throwable;
                }
        }
}
