package cms.views.organizer;

import cms.Entities.*;
import cms.views.shardCom.Notify;
import com.flowingcode.vaadin.addons.verticalmenu.VerticalMenu;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class organizerDashboard extends VerticalMenu {
    public static String loggedInUser = VaadinSession.getCurrent().getAttribute("username").toString();
    private static final CMS cms = new CMS();
    private static final cms.DB.DBConnector dbConnector;

    static {
        try {
            dbConnector = new cms.DB.DBConnector();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public organizerDashboard() throws SQLException {
        super(
                createAccountSection(),
                createConferencesSection(),
                createMyConferencesSection(),
                createReviewersSection(),
                createVenuesSection()
        );

        // Set the Colors of the menu
        customizeSections();
    }


    private static Section createAccountSection() throws SQLException {
        return new Section(new H1("My Account"), userCard(dbConnector.getUser(loggedInUser)));
    }

    private static Section createConferencesSection() {
        try {
            return new Section(new H1("Register Conferences"), createConferences());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Section createMyConferencesSection() {
        try {
            return new Section(new H1("My Conferences"), organizerConferences(loggedInUser));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Section createReviewersSection() {
        return new Section(new H1("Reviewers"), reviewersList(dbConnector.getReviewers()));
    }

    private static Section createVenuesSection() {
        return new Section(new H1("Venues"), venuesList());
    }

    private static VerticalLayout createConferences() throws SQLException {
        VerticalLayout container = new VerticalLayout();
        container.getStyle()
                .set("margin", "auto")
                .set("padding", "5rem")
                .set("width", "50%");

        H3 title = new H3("Please enter the conference details");
        title.getStyle().set("text-align", "center")
                .set("margin", "auto");

        TextField name = createTextField();
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

        Div reviewerDiv = new Div();
        reviewerDiv.getStyle()
                .set("display", "flex")
                .set("align-items", "baseline")
                .set("gap", "10px")
                .set("margin-bottom", "15px")
                .set("width", "100%");

        MultiSelectComboBox<String> reviewerSelect = createReviewerSelect(cms.getReviewers() );
        RegisterDialog registerDialog = new RegisterDialog("Add new", "Add");
        registerDialog.addClassName("reviewer-dialog");
        reviewerDiv.add(reviewerSelect, registerDialog);

        Select<String> venueSelect = createVenueSelect(dbConnector.getAvailableVenues());

        Button Create = new Button("Register");
        Create.getStyle().set("margin-top", "15px")
                .set("border-radius", "4px")
                .set("width", "auto")
                .set("padding", "0 3rem")
                .set("margin", "auto")
                .set("background-image",
                        "linear-gradient(to top, rgb(6 185 106 / 35%) 0%, rgb(60 186 146 / 68%) 100%)  ")
                .set("color", "white")
                .set("font-weight", "bold")
                .set("font-size", "16px")
                .set("cursor", "pointer");

        Create.addClickListener(e -> {
            int organizerId;
            int venueId;
            try {
                organizerId = dbConnector.getUser(loggedInUser).getId();
                venueId = dbConnector.getVenue(venueSelect.getValue()).getVenueId();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            handleConferenceRegister(Create, name, startDate, endDate, deadline, reviewerSelect, venueSelect, organizerId, venueId);
        });

        container.add(title, name, startDate, endDate, deadline,reviewerDiv, venueSelect, Create);
        return container;
    }

    private static void handleConferenceRegister(Button create, TextField name, DatePicker startDate, DatePicker endDate, DatePicker deadline, MultiSelectComboBox<String> reviewerSelect, Select<String> venueSelect, int organizerID, int venueID) {
        if (name.getValue().isEmpty() || startDate.getValue() == null || endDate.getValue() == null || deadline.getValue() == null) {
            Notify.notify("Please fill all the fields", 3000, "warning");
            return;
        }

        // Validate dates
        if (startDate.getValue().isAfter(endDate.getValue()) || startDate.getValue().isAfter(deadline.getValue()) || endDate.getValue().isAfter(deadline.getValue())) {
            Notify.notify("Please enter valid dates", 3000, "warning");
            return;
        }

        Conference conference = new Conference(name.getValue(), startDate.getValue(), endDate.getValue(), deadline.getValue(), organizerID, venueID);

        if (dbConnector.checkForConference(conference.getName())) {
            Notify.notify("Conference already exists", 3000, "warning");
            return;
        }

        boolean register = dbConnector.registerConference(conference);

        if (register) {
            Notify.notify("Conference registered successfully", 3000, "success");
            // Clear fields
            name.clear();
            startDate.clear();
            endDate.clear();
            deadline.clear();
            reviewerSelect.clear();
            venueSelect.clear();
        } else {
            Notify.notify("Conference registration failed", 3000, "error");
        }
    }

    private static TextField createTextField() {
        com.vaadin.flow.component.textfield.TextField textField = new TextField("Name");
        textField.setPlaceholder("Enter conference name");
        textField.setRequired(true);
        textField.getStyle()
                .set("margin-bottom", "15px")
                .set("border-radius", "4px")
                .set("width", "100%");
        return textField;
    }

    private static MultiSelectComboBox<String> createReviewerSelect(List<Reviewer> reviewers) {
        MultiSelectComboBox<String> reviewerSelect = new MultiSelectComboBox<>();
        reviewerSelect.setWidth("100%");

        reviewerSelect.setLabel("Select Reviewers or Add New Ones");
        reviewerSelect.setPlaceholder("Select reviewers");

        Set<String> reviewerNames = reviewers.stream().map(Reviewer::getUsername).collect(Collectors.toSet());

        reviewerSelect.setItems(reviewerNames);

        // Listener to limit selections to 3
        reviewerSelect.addValueChangeListener(event -> {
            if (event.getValue().size() > 3) {
                Set<String> selectedItems = new HashSet<>(event.getValue());
                selectedItems.removeAll(event.getOldValue());
                reviewerSelect.deselect(selectedItems);
                Notify.notify("You can only select 3 reviewers only", 3000, "warning");
            }
        });

        return reviewerSelect;
    }

    private static Select<String> createVenueSelect(List<Venue> venues) throws SQLException {
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

    private static Div userCard(User user) throws SQLException {
        Div card = new Div();
        card.addClassName("user-card");

        Div firstRow = new Div();
        firstRow.addClassName("first-row");


        Avatar avatar = new Avatar();
        avatar.setName(user.getName());
        avatar.setImage("https://em-content.zobj.net/source/microsoft-teams/363/grinning-face_1f600.png");
        avatar.setClassName("avatar");

        Span name = new Span(user.getName());
        name.addClassName("user-name");



        Span role = new Span("Organizer");
        role.getElement().getThemeList().add("badge pill");
        role.addClassName("role");

        firstRow.add(avatar,name, role);

        Div secondRow = new Div();
        secondRow.addClassName("second-row");


        Paragraph ID = new Paragraph("ID: " + user.getId());
        ID.setClassName("user-id");

        Paragraph confCount = new Paragraph("Conferences: " + dbConnector.getOrganizerConfNo(user.getUsername()));
        confCount.setClassName("conf-count");

        Paragraph email = new Paragraph("Email: " + user.getEmail());
        email.setClassName("email");

        secondRow.add(ID, confCount, email);

        Div thirdRow = new Div();
        thirdRow.addClassName("third-row");


        Button edit = new Button("Edit");
        edit.addClassName("edit-button");

        // Edit button handler
        handleEdit(edit);

        Button logout = new Button("Logout");
        logout.setIcon(new Icon(VaadinIcon.SIGN_OUT));
        logout.addClassName("logout-button");


        // Logout button handler
        handleLogout(logout);

        thirdRow.add(edit, logout);

        card.add(firstRow, secondRow, thirdRow);

        return card;
    }

    private static Div reviewersList(List<Reviewer> reviewers) {
        // Main div
        Div container = new Div();
        container.getStyle()
                .set("margin", "auto")
                .set("margin-top", "3rem")
                .set("width", "90%")
                .set("display", "flex")
                .set("flex-wrap", "wrap")
                .set("justify-content", "center")
                .set("gap", "2rem");

        // Create a card for each reviewer
        for (Reviewer r : reviewers) {
            container.add(reviewerCard(r));
        }

        return container;
    }

    private static Div reviewerCard(Reviewer reviewer) {
        // Main div
        Div card = new Div();
        card.getStyle()
                .set("background-image", "linear-gradient(to top, rgb(162 122 233 / 74%) 0%, rgb(185 157 166) 100%)")
                .set("border-radius", "8px")
                .set("padding", "3rem 1rem 1rem 1rem")
                .set("width", "90%")
                .set("max-width", "360px")
                .set("margin", "5rem auto")
                .set("font-family", "Open Sans, sans-serif !important")
                .set("box-shadow", "rgb(0 0 0 / 7%) 0px 4px 20px 20px");

        // First row
        Div firstRow = new Div();
        firstRow.addClassName("first-row");

        // Avatar
        Avatar avatar = new Avatar();
        avatar.setName(reviewer.getName());
        avatar.setImage("https://em-content.zobj.net/source/microsoft-teams/363/grinning-face_1f600.png");
        avatar.addClassName("avatar");

        // Name
        Span name = new Span(reviewer.getName());
        name.addClassName("user-name");

        // Role
        Span role = new Span("Reviewer");
        role.getElement().getThemeList().add("badge pill");
        role.addClassName("role");

        firstRow.add(avatar, name, role);

        // Second row
        Div secondRow = new Div();
        secondRow.addClassName("second-row");

        // ID
        Paragraph ID = new Paragraph("ID: " + reviewer.getId());
        ID.addClassName("user-id");

        // Paper count
        Paragraph paperCount = new Paragraph("Papers: Will be added later");
        paperCount.addClassName("paper-count");

        // Email
        Paragraph email = new Paragraph("Email: " + reviewer.getEmail());
        email.addClassName("email");

        secondRow.add(ID, paperCount, email);

        // Third row
        Div thirdRow = new Div();
        thirdRow.getStyle()
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("width", "100%")
                .set("margin-top", "20px")
                .set("gap", "2rem");

        // Edit button
        Button edit = new Button(new Icon(VaadinIcon.EDIT));
        edit.getStyle()
                .set("border-radius", "4px")
                .set("background-image", "linear-gradient(15deg, rgb(26 26 27 / 21%) 0%, rgb(124 57 201 / 64%) 100%)")
                .set("color", "white")
                .set("font-weight", "bold")
                .set("font-size", "16px")
                .set("cursor", "pointer")
                .set("border", "1px solid rgb(0 0 0 / 10%)")
                .set("border-radius", "100%")
                .set("width", "4rem")
                .set("height", "4rem");

        Button delete = new Button(new Icon(VaadinIcon.TRASH));
        delete.addClassName("delete-button");
        delete.getStyle()
                .set("border-radius", "4px")
                .set("background-image", "linear-gradient(15deg, rgb(26 26 27 / 21%) 0%, rgb(124 57 201 / 64%) 100%)")
                .set("color", "white")
                .set("font-weight", "bold")
                .set("font-size", "16px")
                .set("cursor", "pointer")
                .set("border", "1px solid rgb(0 0 0 / 10%)")
                .set("border-radius", "100%")
                .set("width", "4rem")
                .set("height", "4rem");


        thirdRow.add(edit, delete);

        card.add(firstRow, secondRow, thirdRow);

        return card;

    }


    private static VerticalLayout organizerConferences(String organizerUser) throws SQLException {

        VerticalLayout container = new VerticalLayout();
        container.getStyle()
                .set("margin", "auto")
                .set("margin-top", "5rem")
                .set("width", "90%")
                .set("height", "auto");

        H2 title = new H2("My Conferences List ");
        title.getStyle().set("text-align", "center")
                .set("margin", "auto");

        Grid<Conference> grid = new Grid<>(Conference.class, false);
        grid.getStyle()
                .set("margin", "auto")
                .set("margin-top", "3rem")
                .set("width", "90%")
                .set("background-image", "linear-gradient(to top, rgb(122 183 233 / 74%) 0%, rgb(160 185 157) 100%)");

        // Set the Height of the grid to be dynamic
        grid.setAllRowsVisible(true);

        List<Conference> conferences = dbConnector.getOrganizerConferences(organizerUser);

        // Add columns to the grid
        grid.addColumn(Conference::getName).setHeader("Name");
        grid.addColumn(Conference::getStartDate).setHeader("Start Date");
        grid.addColumn(Conference::getEndDate).setHeader("End Date");
        grid.addColumn(Conference::getDeadline).setHeader("Deadline");
        grid.addColumn(Conference::getConferenceCode).setHeader("Code");
        grid.addColumn(Conference::getOrganizerName).setHeader("Organizer");
        grid.addColumn(Conference::getVenueName).setHeader("Location");

        grid.setItems(conferences);

        container.add(title, grid);

        return container;
    }

    private static VerticalLayout venuesList() {
        VerticalLayout container = new VerticalLayout();
        container.getStyle()
                .set("margin", "auto")
                .set("margin-top", "5rem")
                .set("width", "90%")
                .set("height", "auto");

        H2 title = new H2("Venues List, NOT IMPLEMENTED YET ");
        title.getStyle().set("text-align", "center")
                .set("margin", "auto");

        Grid<Venue> grid = new Grid<>(Venue.class, false);
        grid.getStyle()
                .set("margin", "auto")
                .set("margin-top", "3rem")
                .set("width", "90%")
                .set("background-image", "linear-gradient(to top, rgb(122 183 233 / 74%) 0%, rgb(160 185 157) 100%)");

        // Set the Height of the grid to be dynamic
        grid.setAllRowsVisible(true);

        // Add columns to the grid
        grid.addColumn(Venue::getVenueId).setHeader("ID");
        grid.addColumn(Venue::getLocation).setHeader("Location");
//        grid.addColumn(Venue::getCapacity).setHeader("Availability");

//        grid.setItems(venues);

        container.add(title);

        return container;

    }

    private void customizeSections() {
        getSections().get(0).getStyle().set("background-image", "linear-gradient(60deg, rgb(41 51 60) 0%, rgb(72 99 85) 100%)");
        getSections().get(1).getStyle().set("background-image", "linear-gradient(-20deg, #616161 0%, #9bc5c3 100%)");
        getSections().get(2).getStyle().set("background-image", "linear-gradient(to top, #e6b980 0%, #eacda3 100%)");
        getSections().get(3).getStyle().set("background-image", "linear-gradient(15deg, rgba(81, 19, 122, 0.66) 0%, rgb(153 208 128 / 75%) 100%)");
        getSections().get(4).getStyle().set("background-image", "linear-gradient(135deg, #d299c2 0%, #fef9d7 100%)");

        // change the height of the section set it to scroll
        getSections().forEach(section -> section.getStyle().set("overflow", "scroll"));
    }

    private static void handleEdit(Button edit) {
        edit.addClickListener(e -> {
            Notify.notify("Not implemented yet!", 3000, "warning");
        });
    }

    private static void handleLogout(Button logout) {
        logout.addClickListener(e -> {
            // Check if there is a data in the session, if so, remove it. And redirect to login page
            if (VaadinSession.getCurrent().getAttribute("username") != null) {
                VaadinSession.getCurrent().setAttribute("username", null);
                UI.getCurrent().getPage().setLocation("/login");
                VaadinSession.getCurrent().close();

                //Prevent going back to the dashboard
                UI.getCurrent().getPage().getHistory().replaceState(null, "");
            }
        });
    }

}