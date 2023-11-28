package cms.views.organizer;

import cms.DB.DBConnector;
import cms.views.shardCom.Notify;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.data.provider.Query;
import cms.Entities.Conference;
import com.vaadin.flow.server.VaadinSession;

import java.sql.SQLException;
import java.util.List;

public class ConferencesGrid extends Div {

    private Grid<Conference> grid;
    private Div hint;
    // get the user from the session
    public static String loggedInUser = VaadinSession.getCurrent().getAttribute("username").toString();

    public ConferencesGrid() throws SQLException {
        setWidthFull();
        this.setupGrid(loggedInUser);
        this.refreshGrid();
    }

    private void setupGrid(String organizerUser) throws SQLException {
        DBConnector dataService = new DBConnector();
        List<Conference> conferences = dataService.getOrganizerConferences(organizerUser);

        grid = new Grid<>(Conference.class, false);
        grid.setAllRowsVisible(true);
        grid.addColumn(Conference::getName).setHeader("Name");
        grid.addColumn(Conference::getStartDate).setHeader("Start Date");
        grid.addColumn(Conference::getEndDate).setHeader("End Date");
        grid.addColumn(Conference::getDeadline).setHeader("Deadline");
        grid.addColumn(Conference::getConferenceCode).setHeader("Code");
        grid.addColumn(Conference::getOrganizerName).setHeader("Organizer");
        grid.addColumn(Conference::getVenueName).setHeader("Location");

        // New column with delete buttons
        grid.addComponentColumn(conference -> {
            Button deleteButton = new Button(new Icon("lumo", "cross"));
            deleteButton.addClickListener(event -> {
                try {
                    deleteConference(conference);
                     Notify.notify("Conference deleted successfully", 3000, "success");
                    refreshGrid();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            return deleteButton;
        }).setHeader("Delete");


        grid.setItems(conferences);

        hint = new Div();
        hint.setText("No conferences available");
        hint.getStyle().set("padding", "var(--lumo-size-l)")
                .set("text-align", "center").set("font-style", "italic")
                .set("color", "var(--lumo-contrast-70pct)");


        add(hint, grid);
    }

    private void refreshGrid() {
        if (!grid.getDataProvider().isInMemory()) {
            grid.getDataProvider().refreshAll();
        }
        if (grid.getDataProvider().size(new Query<>()) > 0) {
            grid.setVisible(true);
            hint.setVisible(false);
        } else {
            grid.setVisible(false);
            hint.setVisible(true);
        }
    }

    private void deleteConference(Conference conference) throws SQLException {
        DBConnector dataService = new DBConnector();
        dataService.deleteConference(conference.getConferenceId());
        grid.setItems(dataService.getOrganizerConferences("othman"));
    }

//    private void editConference(Conference conference) throws SQLException {
//
//        // Save the changes to your data source (e.g., database)
//        DBConnector dataService = new DBConnector();
//
//
//
//        Dialog editDialog = new Dialog();
//
//        // Set dialog content, create form fields to edit conference details
//        TextField nameField = new TextField("Name");
//        nameField.setValue(conference.getName());
//        nameField.getStyle().set("width", "100%");
//        DatePicker startDateField = new DatePicker("Start Date");
//        startDateField.setValue(conference.getStartDate());
//        startDateField.getStyle().set("width", "100%");
//        DatePicker endDateField = new DatePicker("End Date");
//        endDateField.setValue(conference.getEndDate());
//        endDateField.getStyle().set("width", "100%");
//        DatePicker deadlineField = new DatePicker("Deadline");
//        deadlineField.setValue(conference.getDeadline());
//        deadlineField.getStyle().set("width", "100%");
//        TextField codeField = new TextField("Code");
//        codeField.setValue(conference.getConferenceCode());
//        codeField.getStyle().set("width", "100%");
//        TextField organizerField = new TextField("Organizer");
//        organizerField.setValue(conference.getOrganizerName());
//        organizerField.getStyle().set("width", "100%");
//        Select<String> venueSelect = createVenueSelect(conference);
//
//
//
//        // Create a button to confirm the changes
//        Button confirmButton = new Button("Save", event -> {
//            // Update the conference details
//            conference.setName(nameField.getValue());
//            conference.setStartDate(startDateField.getValue());
//            conference.setEndDate(endDateField.getValue());
//            conference.setDeadline(deadlineField.getValue());
//            conference.setConferenceCode(codeField.getValue());
//            conference.setOrganizerName(organizerField.getValue());
//            conference.setVenueName(venueSelect.getValue());
//
//            // Validate the input
//            if (conference.getName().isEmpty() || conference.getStartDate() == null || conference.getEndDate() == null || conference.getDeadline() == null || conference.getConferenceCode().isEmpty() || conference.getOrganizerName().isEmpty() || conference.getVenueName().isEmpty()) {
//                Notify.notify("Please fill all the fields", 3000, "error");
//                return;
//            }
//
//            // Validate the dates
//            if (conference.getStartDate().isAfter(conference.getEndDate())) {
//                Notify.notify("Start date must be before end date", 3000, "error");
//                return;
//            }else if (conference.getStartDate().isAfter(conference.getDeadline())) {
//                Notify.notify("Start date must be before deadline", 3000, "error");
//                return;
//            }else if (conference.getDeadline().isAfter(conference.getEndDate())) {
//                Notify.notify("Deadline must be before end date", 3000, "error");
//                return;
//            }
//
//            int organizerId;
//            int venueId;
//            try {
//                organizerId= dataService.getOrganizerId(conference.getOrganizerName());
//                venueId = dataService.getVenueId(conference.getVenueName());
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//
//            Conference newConference = new Conference(conference.getName(), conference.getStartDate(), conference.getEndDate(), conference.getDeadline(), conference.getConferenceCode(), organizerId, venueId);
//
//            // Refresh the grid with updated conference details
//            dataService.updateConference(conference.getName(), newConference);
//            refreshGrid();
//            Notify.notify("Conference edited successfully", 3000, "success");
//            editDialog.close();
//        });
//        confirmButton.getStyle().set("margin-top", "1rem")
//                .set("width", "100%")
//                .set("background-color", "var(--lumo-primary-color)")
//                .set("color", "var(--lumo-base-color)");
//
//        // Add the form fields and confirm button to the dialog
//        editDialog.add(nameField, startDateField, endDateField, deadlineField, codeField, organizerField, venueSelect, confirmButton);
//
//        // Open the dialog
//        editDialog.open();
//
//        grid.setItems(dataService.getOrganizerConferences("othman"));
//    }
//
//    private static Select<String> createVenueSelect(Conference conference) throws SQLException {
//        DBConnector dataService = new DBConnector();
//
//        Select<String> venueSelect = new Select<>();
//        venueSelect.setWidth("100%");
//
//        List<Venue> venues = dataService.getAvailableVenues();
//
//        venueSelect.setLabel("Venue");
//
//        List<String> venueNames = new ArrayList<>();
//        for (Venue venue : venues) {
//            venueNames.add(venue.getLocation());
//        }
//
//        venueSelect.setItems(venueNames);
//
//        return venueSelect;
//    }

}
