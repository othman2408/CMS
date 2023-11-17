package cms.views.organizer;

import cms.Entity.CMS;
import cms.Entity.Reviewer;
import com.flowingcode.vaadin.addons.verticalmenu.VerticalMenu;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Section;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;

import java.sql.SQLException;
import java.util.List;

public class DemoView extends VerticalMenu {
    private static CMS cms = new CMS();

    public DemoView() throws SQLException {
        super(new Section(new H1("Dashboard")),
                new Section(new H1("Create Conferences"), createConferences()),
                new Section(new H1("Papers")),
                new Section(new H1("Reviewers")),
                new Section(new H1("Venues")));

        getSections().get(0).getStyle().set("background-color", "green");
        reloadSections();
        addMenuSelectedListener(ev->{
            Notification.show("Section: " + ev.getSource().getElement().getChild(0).getText() + " clicked.");
        });

        // Change colors
        getSections().get(0).getStyle().set("background-image", "linear-gradient(60deg, #29323c 0%, #485563 100%)");
        getSections().get(1).getStyle().set("background-image", "linear-gradient(-20deg, #616161 0%, #9bc5c3 100%)");
        getSections().get(2).getStyle().set("background-image", "linear-gradient(135deg, #f093fb 0%, #f5576c 100%)");
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
        DatePicker endDate = new DatePicker("End Date");
        endDate.setWidth("100%");
        DatePicker deadline = new DatePicker("Deadline");
        deadline.setWidth("100%");
        TextField conferenceCode = createTextField("Conference Code", "Enter conference code", true);
        Select<String> reviewerSelect = createReviewerSelect(cms.getReviewers());

        Button Create = new Button("Create");
        Create.getStyle().set("margin-top", "15px")
                .set("border-radius", "4px")
                .set("width", "auto")
                .set("padding", "0 3rem")
                .set("margin", "auto")
                .set("background-image", "linear-gradient(to top, rgb(6 185 106 / 35%) 0%, rgb(60 186 146 / 68%) 100%)")
                .set("color", "white")
                .set("font-weight", "bold")
                .set("font-size", "16px")
                .set("cursor", "pointer");

        Create.addClickListener(e -> {
            Notification.show("Conference created");
        });


        container.add(title, name, startDate, endDate, deadline, conferenceCode, reviewerSelect, Create);
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

}