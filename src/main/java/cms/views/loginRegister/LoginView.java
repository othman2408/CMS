package cms.views.loginRegister;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout {
    public LoginView() {
        LoginOverlay loginForm = createLoginForm();
        loginForm.addLoginListener(e -> {
            String username = e.getUsername();
            String password = e.getPassword();
            if (authenticateUser(username, password)) {
                loginForm.setAction("organizer");

                // Navigate to main view
                UI.getCurrent().navigate("organizer");
            } else {
                // Wrong password or username
                loginForm.setError(true);
            }
        });
        add(loginForm);
    }

    private LoginOverlay createLoginForm() {
        LoginOverlay loginForm = new LoginOverlay();
        loginForm.setOpened(true);
        loginForm.setTitle("CMS");
        loginForm.setDescription("Conference Management System");
        loginForm.setForgotPasswordButtonVisible(false);
        loginForm.getElement().getThemeList().add("dark");
        return loginForm;
    }

    // Simulated authentication method (replace this with your actual authentication logic)
    private boolean authenticateUser(String username, String password) {
        // Replace this with your actual authentication logic
        // For example, check if the username and password are correct
        // Dummy check with hardcoded values
        return username.equals("user") && password.equals("pass");
    }
}
