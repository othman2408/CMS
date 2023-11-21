package cms.views.simpleAuth;

import cms.DB.DBConnector;
import cms.views.shardCom.Notify;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.HasStyle;

import java.sql.SQLException;

/**
 * LoginView represents the login page of the CMS application.
 */
@Route("")
@RouteAlias("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout {

    /**
     * Constructs the LoginView by setting up the layout and creating the login form.
     */
    public LoginView() {
        setLayout();
        Div loginForm = createLoginForm();
        add(loginForm);
    }

    /**
     * Sets up the layout of the page.
     */
    private void setLayout() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setHeightFull();
        getStyle().set("background-image", "linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%)");
    }

    /**
     * Creates the main login form container.
     *
     * @return Div representing the login form
     */
    private Div createLoginForm() {
        Div div = new Div();
        setFormStyles(div);

        Div header = createHeader();
        Div fieldsContainer = createFieldsContainer();

        div.add(header, fieldsContainer);
        return div;
    }

    /**
     * Sets styles for the main form container.
     *
     * @param div Main form container to style
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
     * Creates the header section.
     *
     * @return Div representing the header
     */
    private Div createHeader() {
        Div header = new Div();
        H1 title = new H1("Welcome to CMS");
        Span subtitle = new Span("Login to continue");

        setHeaderStyles(header, title, subtitle);

        header.add(title, subtitle);
        return header;
    }

    /**
     * Sets styles for the header.
     *
     * @param header   Header container to style
     * @param title    Title element to style
     * @param subtitle Subtitle element to style
     */
    private void setHeaderStyles(Div header, H1 title, Span subtitle) {
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
    }

    /**
     * Creates the container for input fields and buttons.
     *
     * @return Div representing the fields container
     */
    private Div createFieldsContainer() {
        Div fieldsContainer = new Div();
        setFieldsContainerStyles(fieldsContainer);

        TextField username = createTextField();
        PasswordField password = createPasswordField();

        Button loginButton = createLoginButton(username, password);
        Button registerButton = createRegisterButton();

        fieldsContainer.add(username, password, loginButton, registerButton);
        return fieldsContainer;
    }

    /**
     * Sets styles for the fields container.
     *
     * @param fieldsContainer Fields container to style
     */
    private void setFieldsContainerStyles(Div fieldsContainer) {
        fieldsContainer.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("justify-content", "center")
                .set("align-items", "center")
                .set("width", "85%")
                .set("padding", "2rem 0");
    }

    /**
     * Creates a text field with specified label and placeholder.
     *
     * @return TextField representing the username field
     */
    private TextField createTextField() {
        TextField textField = new TextField("Username");
        setFieldStyles(textField);
        return textField;
    }

    /**
     * Creates a password field with specified label and placeholder.
     *
     * @return PasswordField representing the password field
     */
    private PasswordField createPasswordField() {
        PasswordField passwordField = new PasswordField("Password");
        setFieldStyles(passwordField);
        return passwordField;
    }

    /**
     * Sets common styles for input fields.
     *
     * @param field Input field to style
     */
    private void setFieldStyles(HasStyle field) {
        field.getStyle()
                .set("margin-bottom", "15px")
                .set("padding", "10px")
                .set("border-radius", "4px")
                .set("width", "100%");
    }

    /**
     * Creates a login button and sets its styles and click functionality.
     *
     * @param username Username input field
     * @param password Password input field
     * @return Button representing the login button
     */
    private Button createLoginButton(TextField username, PasswordField password) {
        Button loginButton = new Button("Login");
        setLoginButtonStyles(loginButton);

        loginButton.addClickListener(e -> {
            try {
                boolean login = handleLogin(username, password);

                if (login) {
                    VaadinSession.getCurrent().setAttribute("username", username.getValue());
                    String role = new DBConnector().getUserRole(username.getValue());
                    redirectBasedOnRole(role);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        return loginButton;
    }

    /**
     * Sets styles for the login button.
     *
     * @param button Login button to style
     */
    private void setLoginButtonStyles(Button button) {
        button.getStyle()
                .set("margin-bottom", "15px")
                .set("padding", "10px")
                .set("border-radius", "4px")
                .set("width", "100%")
                .set("background-color", "hsl(236.29deg 47.78% 60.2%)")
                .set("color", "white")
                .setCursor("pointer");
    }

    /**
     * Creates a button for registration and sets its styles and click functionality.
     *
     * @return Button representing the register button
     */
    private Button createRegisterButton() {
        Button registerButton = new Button("Don't have an account? Register");
        setRegisterButtonStyles(registerButton);

        registerButton.addClickListener(e -> UI.getCurrent().navigate("register"));
        return registerButton;
    }

    /**
     * Sets styles for the registration button.
     *
     * @param button Register button to style
     */
    private void setRegisterButtonStyles(Button button) {
        button.getStyle()
                .set("margin-bottom", "15px")
                .set("padding", "10px")
                .set("border-radius", "4px")
                .set("width", "100%")
                .set("background-color", "transparent")
                .set("color", "hsl(236.29deg 47.78% 60.2%)")
                .setCursor("pointer");
    }

    /**
     * Handles the login process and authentication.
     *
     * @param username Username input field
     * @param password Password input field
     * @return boolean indicating if login was successful
     * @throws SQLException if an SQL exception occurs
     */
    private boolean handleLogin(TextField username, PasswordField password) throws SQLException {
        DBConnector dbConnector = new DBConnector();
        if (username.isEmpty() || password.isEmpty()) {
            Notify.notify("Please fill all the fields", 3000, "warning");
            return false;
        } else {
            try {
                if (dbConnector.login(username.getValue(), password.getValue())) {
                    Notify.notify("Login successful", 3000, "success");
                    return true;
                } else {
                    Notify.notify("Invalid username or password", 3000, "error");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Notify.notify("An error occurred" + e.getCause(), 3000, "error");
            }
        }
        return false;
    }

    /**
     * Redirects the user based on their role after successful login.
     *
     * @param role Role of the user
     */
    private void redirectBasedOnRole(String role) {
        switch (role) {
            case "Organizer":
                getUI().ifPresent(ui -> ui.getPage().executeJs("setTimeout(function() { window.location.href = 'organizer'; }, 1000)"));
                break;
            case "reviewer":
            case "author":
                Notify.notify("Sorry, your view has not been implemented yet!", 3000, "info");
                break;
            default:
                UI.getCurrent().navigate("");
        }
    }
}
