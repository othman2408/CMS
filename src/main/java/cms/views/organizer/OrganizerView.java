package cms.views.organizer;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.PermitAll;

@PageTitle("Register Conference")
@Route("organizer")
@PermitAll
public class OrganizerView extends AppLayout {
    private H2 viewTitle;
    public OrganizerView() {

        // If user is not logged in, redirect to login page
        if(!isLoggedIn()) {
            UI.getCurrent().getPage().executeJs("location.href = '';");
        }
        // ==================================================================================================


        H1 appTitle = new H1("MyApp");
        appTitle.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("line-height", "var(--lumo-size-l)")
                .set("margin", "0 var(--lumo-space-m)");

        Div menu = mainMenu();

        DrawerToggle toggle = new DrawerToggle();

        H2 viewTitle = new H2();
        viewTitle.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");


        HorizontalLayout wrapper = new HorizontalLayout(toggle, viewTitle);
        wrapper.setAlignItems(FlexComponent.Alignment.CENTER);
        wrapper.setSpacing(false);

        VerticalLayout viewHeader = new VerticalLayout(wrapper);
        viewHeader.setPadding(false);
        viewHeader.setSpacing(false);

        addToDrawer(appTitle, menu);
        addToNavbar(viewHeader);

        setPrimarySection(Section.DRAWER);
    }


    private Div mainMenu() {
        Div menu = new Div();
        menu.getStyle().set("display", "flex")
                .set("flex-direction", "column")
                .set("gap", ".5rem")
                .set("border-top", "1px solid var(--lumo-contrast-10pct)")
                .set("font-size", "var(--lumo-font-size-s)")
                .set("line-height", "var(--lumo-line-height-s)")
                .set("margin", "2rem 1rem")
                .set("font-family", "var(--lumo-font-family)")
                .set("color", "var(--lumo-secondary-text-color)")
                .set("text-align", "center");

        Button registerConference = new Button("Register Conference");
        Button viewConference = new Button("View Conference");

        // Change the content when clicking on the buttons
        registerConference.addClickListener(e -> {
            viewTitle.setText("Register Conference");
            setContent(new H1("Register Conference"));
        });
        viewConference.addClickListener(e -> {
            viewTitle.setText("View Conference");
            setContent(new H1("View Conference"));
        });

        menu.add(registerConference, viewConference);

        return menu;
    }



    // ==================================================================================================

    // Check if user is logged in
    private boolean isLoggedIn() {
        VaadinSession session = VaadinSession.getCurrent();
        String username = (String) session.getAttribute("username");
        return username != null;
    }

    // Method to handle logout
    private void logout() {
        //clear the session
        VaadinSession.getCurrent().close();
        //redirect to login page
        UI.getCurrent().getPage().executeJs("window.location.href = 'login'");
    }

}
