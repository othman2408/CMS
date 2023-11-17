package cms.views.organizer;

import cms.Entity.CMS;
import cms.Entity.Reviewer;
import cms.Entity.User;
import cms.Entity.Venue;
import cms.views.shardCom.Notify;
import com.flowingcode.vaadin.addons.verticalmenu.VerticalMenu;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;

import java.sql.SQLException;
import java.util.List;

public class organizerDashboard extends VerticalMenu {
    public static String loggedInUser = VaadinSession.getCurrent().getAttribute("username").toString();
    private static CMS cms = new CMS();
    private static cms.DB.DBConnector dbConnector;

    static {
        try {
            dbConnector = new cms.DB.DBConnector();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public organizerDashboard() throws SQLException {
        super(new Section(new H1("Account"), userCard(dbConnector.getUser(loggedInUser))),
                new Section(new H1("Register Conferences"), createConferences()),
                new Section(new H1("Papers")),
                new Section(new H1("Reviewers")),
                new Section(new H1("Venues")));

        reloadSections();
//        addMenuSelectedListener(ev->{
//            Notification.show("Section: " + ev.getSource().getElement().getChild(0).getText() + " clicked.");
//        });

        // Change colors
        getSections().get(0).getStyle().set("background-image", "linear-gradient(60deg, #29323c 0%, #485563 100%)");
        getSections().get(1).getStyle().set("background-image", "linear-gradient(-20deg, #616161 0%, #9bc5c3 100%)");
        getSections().get(2).getStyle().set("background-image", "linear-gradient(to top, #e6b980 0%, #eacda3 100%)");
        getSections().get(3).getStyle().set("background-image", "linear-gradient(135deg, #5ee7df 0%, #b490ca 100%)");
        getSections().get(4).getStyle().set("background-image", "linear-gradient(135deg, #d299c2 0%, #fef9d7 100%)");


    }


    private static VerticalLayout createConferences() throws SQLException {
        VerticalLayout container = new VerticalLayout();
        container.setWidth("50%");
        container.getStyle().set("margin", "auto");

        H3 title = new H3("Please enter the conference details");
        title.getStyle().set("text-align", "center")
                .set("margin", "auto");


        TextField name = createTextField("Name", "Enter conference name", true);
        DatePicker startDate = new DatePicker("Start Date");
        startDate.setWidth("100%");
        startDate.setRequired(true);
        startDate.setPlaceholder("Select a start date");
        DatePicker endDate = new DatePicker("End Date");
        endDate.setWidth("100%");
        endDate.setRequired(true);
        endDate.setPlaceholder("Select an end date");
        DatePicker deadline = new DatePicker("Deadline");
        deadline.setWidth("100%");
        deadline.setRequired(true);
        deadline.setPlaceholder("Select a deadline date");
        Select<String> reviewerSelect = createReviewerSelect(cms.getReviewers());
        Select<String> venueSelect = createVenueSelect(dbConnector.getVenues());

        Button Create = new Button("Register");
        Create.getStyle().set("margin-top", "15px")
                .set("border-radius", "4px")
                .set("width", "auto")
                .set("padding", "0 3rem")
                .set("margin", "auto")
                .set("background-image", "linear-gradient(to top, rgb(6 185 106 / 35%) 0%, rgb(60 186 146 / 68%) 100%)  ")
                .set("color", "white")
                .set("font-weight", "bold")
                .set("font-size", "16px")
                .set("cursor", "pointer");

        Create.addClickListener(e -> {
            Notification.show("Conference created");
        });


        container.add(title, name, startDate, endDate, deadline, reviewerSelect, venueSelect, Create );
        return container;
    }

    private static com.vaadin.flow.component.textfield.TextField createTextField(String label, String placeholder, boolean required) {
        com.vaadin.flow.component.textfield.TextField textField = new com.vaadin.flow.component.textfield.TextField(label);
        textField.setPlaceholder(placeholder);
        textField.setRequired(required);
        textField.getStyle()
                .set("margin-bottom", "15px")
                .set("border-radius", "4px")
                .set("width", "100%");
        return textField;
    }

    private static Select<String> createReviewerSelect(List<Reviewer> reviewers) {
        Select<String> reviewerSelect = new Select<>();
        reviewerSelect.setWidth("100%");

        reviewerSelect.setLabel("Reviewer");
        reviewerSelect.setPlaceholder("Select a reviewer");

        String reviewerNames = "";
        for (Reviewer reviewer : reviewers) {
            reviewerNames += reviewer.getUsername() + ", ";
        }

        reviewerSelect.setItems(reviewerNames.split(", "));

        return reviewerSelect;
    }

    private static Select<String> createVenueSelect(List<Venue> venues) throws SQLException{
        Select<String> venueSelect = new Select<>();
        venueSelect.setWidth("100%");

        venueSelect.setLabel("Venue");
        venueSelect.setPlaceholder("Select a venue");

        String venueNames = "";
        for (Venue venue : venues) {
            venueNames += venue.getLocation() + ", ";
        }

        venueSelect.setItems(venueNames.split(", "));

        return venueSelect;
    }

    private static Div userCard(User user){
        Div card = new Div();
        card.getStyle()
                .set("background-image", "linear-gradient(to top, rgb(122 183 233 / 74%) 0%, rgb(160 185 157) 100%)")
                .set("border-radius", "8px")
                .set("padding", "3rem 1rem 1rem 1rem")
                .set("width", "90%")
                .set("max-width", "360px")
                .set("margin", "5rem auto")
                .set("font-family", "ubuntu")
                .set("box-shadow", "0 4px 8px 0 rgba(0, 0, 0, 0.1)");

        Div firstRow = new Div();
        firstRow.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("align-items", "center")
                .set("margin-bottom", "20px")
                .set("padding-bottom", "20px")
                .set("border-bottom", "1px solid rgb(0 0 0 / 5%)");

        Avatar avatar = new Avatar();
        avatar.setName(user.getName());
        avatar.setImage("https://em-content.zobj.net/source/microsoft-teams/363/grinning-face_1f600.png");
        avatar.getStyle().set("width", "100px")
                .set("height", "100px")
                .set("border-radius", "50%")
                .set("background", "white")
                .set("border", "1px solid #e6e6e6")
                .set("box-shadow", "0 2px 4px 0 rgba(0, 0, 0, 0.1)")
                .set("margin-bottom", "20px");

        Span role = new Span("Organizer");
        role.getElement().getThemeList().add("badge pill");
        role.getStyle().set("user-select", "none")
                .set("background-image", "linear-gradient(135deg, #f5f7fa 0%, #b9bee39e 100%)")
                .set("font-weight", "bold");

        firstRow.add(avatar, role);

        Div secondRow = new Div();
        secondRow.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("font-family", "Noto Sans")
                .set("text-align", "left")
                .set("margin-bottom", "10px")
                .set("padding-bottom", "15px")
                .set("border-bottom", "1px solid rgb(0 0 0 / 5%)")
                .set("font-weight", "600");

        Paragraph ID = new Paragraph("ID: " + String.valueOf(user.getId()));

        Paragraph name = new Paragraph("Name: " + user.getName());

        Paragraph email = new Paragraph("Email: " + user.getEmail());

        secondRow.add(ID, name, email);

        Div thirdRow = new Div();
        thirdRow.getStyle()
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("flex-direction", "column")
                .set("gap", "6px");

        Button edit = new Button("Edit");
        edit.getStyle().set("margin-top", "20px")
                .set("border-radius", "4px")
                .set("width", "auto")
                .set("padding", "0 2.5rem")
                .set("margin", "auto")
                .set("background", "rgb(107 156 187)")
                .set("color", "white")
                .set("font-weight", "bold")
                .set("font-size", "16px")
                .set("cursor", "pointer")
                .set("border", "1px solid #00000017");

        handleEdit(edit);

        Button logout = new Button("Logout");
        logout.setIcon(new Icon(VaadinIcon.SIGN_OUT));
        logout.getStyle()
                .set("background", "transparent")
                .set("cursor", "pointer")
                .set("color", "rgb(209 46 46 / 71%)");

        handleLogout(logout);

        thirdRow.add(edit, logout);

        card.add(firstRow, secondRow, thirdRow);

        return card;
    }

    private static void handleEdit(Button edit) {
        edit.addClickListener(e -> {
            Notify.notify("Not now", 3000, "warning");
        });
    }

    private static void handleLogout(Button logout) {
        logout.addClickListener(e -> {
            VaadinSession.getCurrent().close();
            UI.getCurrent().getPage().executeJs("window.location.href = 'login'");
        });
    }
}