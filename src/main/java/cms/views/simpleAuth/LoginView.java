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

import java.sql.SQLException;

@Route("")
@RouteAlias(value = "login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout {
    public LoginView() {
        setLayout();
        Div loginForm = createLoginForm();
        add(loginForm);
    }

    private void setLayout() {
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setHeightFull();
        getStyle().set("background-image",
                " linear-gradient(120deg, rgb(255 255 255 / 23%) 0%, rgb(235 237 238 / 44%) 100%)");
    }

    private Div createLoginForm() {
        Div div = new Div();
        setFormStyles(div);

        Div header = createHeader();
        Div fieldsContainer = createFieldsContainer();

        div.add(header, fieldsContainer);
        return div;
    }

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

    private Div createHeader() {
        Div header = new Div();
        H1 title = new H1("Welcome to CMS");
        Span subtitle = new Span("Login to continue");

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

    private Div createFieldsContainer() {
        Div fieldsContainer = new Div();
        fieldsContainer.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("justify-content", "center")
                .set("align-items", "center")
                .set("width", "85%")
                .set("padding", "2rem 0");

        TextField username = createTextField("Username", "Please enter your username");
        PasswordField password = createPasswordField("Password", "Please enter your password");

        Button loginButton = createLoginButton(username, password);

        Button registerButton = createRegisterButton();

        fieldsContainer.add(username, password, loginButton, registerButton);
        return fieldsContainer;
    }

    private TextField createTextField(String label, String placeholder) {
        TextField textField = new TextField(label);
        textField.setPlaceholder(placeholder);
        textField.getStyle()
                .set("margin-bottom", "15px")
                .set("padding", "10px")
                .set("border-radius", "4px")
                .set("width", "100%");
        return textField;
    }

    private PasswordField createPasswordField(String label, String placeholder) {
        PasswordField passwordField = new PasswordField(label);
        passwordField.setPlaceholder(placeholder);
        passwordField.getStyle()
                .set("margin-bottom", "15px")
                .set("padding", "10px")
                .set("border-radius", "4px")
                .set("width", "100%");
        return passwordField;
    }

    private Button createLoginButton(TextField username, PasswordField password) {
        Button loginButton = new Button("Login");
        loginButton.getStyle()
                .set("margin-bottom", "15px")
                .set("padding", "10px")
                .set("border-radius", "4px")
                .set("width", "100%")
                .set("background-color", "hsl(236.29deg 47.78% 60.2%)")
                .set("color", "white")
                .setCursor("pointer");

        loginButton.addClickListener(e -> {
            try {
                boolean login = handleLogin(username, password);

               if(login) {
                   // Save the username in the session
                   VaadinSession.getCurrent().setAttribute("username", username.getValue());


                   // Get the user role
                   String role = new DBConnector().getUserRole(username.getValue());

                   // Redirect to the correct view based on the user role
                   switch (role) {
                       case "Organizer" -> getUI().ifPresent(ui -> ui.getPage().executeJs("setTimeout(function() { window.location.href = 'organizer'; }, 500)"));
                       case "reviewer" -> getUI().ifPresent(ui -> ui.getPage().executeJs("setTimeout(function() { window.location.href = 'reviewer'; }, 500)"));
                       case "author" -> getUI().ifPresent(ui -> ui.getPage().executeJs("setTimeout(function() { window.location.href = 'author'; }, 500)"));
                       default -> UI.getCurrent().navigate("");
                   }
               }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        return loginButton;
    }

    private Button createRegisterButton() {
        Button registerButton = new Button("Don't have an account? Register");
        registerButton.getStyle()
                .set("margin-bottom", "15px")
                .set("padding", "10px")
                .set("border-radius", "4px")
                .set("width", "100%")
                .set("background-color", "transparent")
                .set("color", "hsl(236.29deg 47.78% 60.2%)")
                .setCursor("pointer");

        registerButton.addClickListener(e -> {
            UI.getCurrent().navigate("register");
        });

        return registerButton;
    }


    private boolean handleLogin(TextField username, PasswordField password) throws SQLException {
       DBConnector dbConnector = new DBConnector();
        if (username.isEmpty() || password.isEmpty()) {
            Notify.notify("Please fill all the fields", 3000, "warning");
            return false;
        }
        else {
            try {
                if (dbConnector.login(username.getValue(), password.getValue())) {
                    Notify.notify("Login successful", 3000, "success");
                    return true;
                }
                else {
                    Notify.notify("Invalid username or password", 3000, "error");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
