package cms.views.organizer;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import jakarta.annotation.security.PermitAll;

import java.sql.SQLException;

@PageTitle("Register Conference")
@Route("organizer")
@PermitAll
public class OrganizerView extends HorizontalLayout {
    public OrganizerView() throws SQLException {
        if (!isLoggedIn()) {
            logout();
        }

        setSizeFull();

        DemoView demoView = new DemoView();
        add(demoView);

    }


    private boolean isLoggedIn() {
        VaadinSession session = VaadinSession.getCurrent();
        String username = (String) session.getAttribute("username");
        return username != null;
    }

    private void logout() {
        VaadinSession.getCurrent().close();
        UI.getCurrent().getPage().executeJs("window.location.href = 'login'");
    }
}
