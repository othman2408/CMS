package cms.views.loginRegister;

import cms.DB.DBConnector;
import cms.Entity.Author;
import cms.Entity.Organizer;
import cms.Entity.Reviewer;
import cms.Entity.User;
import cms.views.shardCom.Notify;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.sql.SQLException;

@Route("register")
@PageTitle("Register")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

    public RegisterView() {

        // Center the content of the page both horizontally and vertically and horizontally
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setHeightFull();


        // Create the register form
        Div registerForm = createRegisterForm();
        add(registerForm);

    }

    private Div createRegisterForm() {

        Div div = new Div();

        // Apply styles directly using getStyle().set() method
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

        // Header
        Div header = new Div();
        H1 title = new H1("Welcome to CMS");
        title.getStyle()
                .set("font-size", "32px")
                .set("font-weight", "bold")
                .set("margin-bottom", "5px")
                .set("color", "white");
        H4 subtitle = new H4("Register to continue");
        subtitle.getStyle()
                .set("font-size", "18px")
                .set("font-weight", "normal")
                .set("margin-top", "0px")
                .set("color", "white");
        header.getStyle()
                .set("font-size", "24px")
                .set("font-weight", "bold")
                .set("background-color", "var(--lumo-primary-color)")
                .set("color", "white")
                .set("padding", "4rem 2rem")
                .set("border-radius", "5px 5px 0 0")
                .set("width", "-webkit-fill-available")
                .set("text-align", "center")
                .set("user-select", "none");
        header.add(title, subtitle);


        // fields Container
        Div fieldsContainer = new Div();
        fieldsContainer.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("justify-content", "center")
                .set("align-items", "center")
                .set("width", "85%")
                .set("padding", "2rem 0");


        // Create form elements
        TextField username = new TextField("Username");
        username.setPlaceholder("Please enter your username");
        username.setRequired(true);
        username.getStyle()
                .set("margin-bottom", "15px")
                .set("padding", "10px")
                .set("border-radius", "4px")
                .set("width", "100%");

        PasswordField password = new PasswordField("Password");
        password.setPlaceholder("Please enter your password");
        password.setRequired(true);
        password.getStyle()
                .set("margin-bottom", "15px")
                .set("padding", "10px")
                .set("border-radius", "4px")
                .set("width", "100%");

        TextField name = new TextField("Name");
        name.setPlaceholder("Please enter your name");
        name.setRequired(true);
        name.getStyle()
                .set("margin-bottom", "15px")
                .set("padding", "10px")
                .set("border-radius", "4px")
                .set("width", "100%");

        EmailField email = new EmailField("Email");
        email.setPlaceholder("Please enter your email");
        email.setRequired(true);
        email.getStyle()
                .set("margin-bottom", "15px")
                .set("padding", "10px")
                .set("border-radius", "4px")
                .set("width", "100%");

        Select<String> role = new Select<>();
        role.setPlaceholder("Role");
        role.setLabel("Role");
        role.setItems("Organizer", "Author", "Reviewer");
        role.setValue("Organizer");
        role.getStyle()
                .set("margin-bottom", "15px")
                .set("padding", "10px")
                .set("border-radius", "4px")
                    .set("width", "100%");

        // Create register button
        Button registerButton = new Button("Register");
        registerButton.getStyle()
                .set("margin-bottom", "15px")
                .set("padding", "10px")
                .set("border-radius", "4px")
                .set("width", "100%")
                .set("background-color", "var(--lumo-primary-color)")
                .set("color", "white")
                .setCursor("pointer");

        registerButton.addClickListener(e -> {
            // Validate the fields
            if (username.isEmpty() || password.isEmpty() || name.isEmpty() || email.isEmpty()) {
                Notify.notify("Please fill all the fields", 3000, "warning");
            } else {
                // Create a user object
                User user = switch (role.getValue()) {
                    case "Organizer" -> new Organizer(username.getValue(), password.getValue(), name.getValue(), email.getValue());
                    case "Author" -> new Author(username.getValue(), password.getValue(), name.getValue(), email.getValue());
                    case "Reviewer" -> new Reviewer(username.getValue(), password.getValue(), name.getValue(), email.getValue());
                    default -> throw new IllegalStateException("Unexpected value: " + role.getValue());
                };
                System.out.println(user.getName() + " "
                        + user.getUsername() +  " "
                        + user.getPassword() + " "
                        + user.getEmail() + " "
                        + user.getClass().getSimpleName());

                // Register the user
                try {
                    registerUser(user);
                    Notify.notify("User registered successfully", 3000, "success");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    Notify.notify("Error: " + throwables.getMessage(), 3000, "error");
                }

            }
        });



        fieldsContainer.add(name, username, password , email, role, registerButton);
        div.add(header, fieldsContainer);

        return div;
    }

    private void registerUser(User user) throws SQLException {
            DBConnector db = new DBConnector();
            try {
                db.registerUser(user);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throw throwables; // Re-throw the exception for handling in the calling code
            }
    }



}
