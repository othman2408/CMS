package cms.views.organizer;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.PermitAll;

import java.sql.SQLException;

@PageTitle("Register Conference")
@Route("organizer")
@PermitAll
public class OrganizerView extends HorizontalLayout implements BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!isLoggedIn()) {
            event.rerouteTo("login");
        }
    }

    public OrganizerView() throws SQLException {
        setSizeFull();

        // Assuming 'organizerDashboard' is a valid Vaadin component
        organizerDashboard dashboard = new organizerDashboard();
        add(dashboard);
    }

    private boolean isLoggedIn() {
        VaadinSession session = VaadinSession.getCurrent();
        String username = (String) session.getAttribute("username");
        return username != null;
    }
}
