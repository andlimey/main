package seedu.address.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Payment;
import seedu.address.model.person.Person;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final String DEFAULT_PAGE = "default.html";
    public static final String SEARCH_PAGE_URL =
            "PersonPage.html";

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Loads the page with the person's information parsed into the URL.
     * Also escapes pound sign.
     *
     * @param person Person to read information from.
     */
    private void loadPersonPage(Person person) {
        final StringBuilder subjectsBuilder = new StringBuilder();
        person.getSubjects().forEach(subjectsBuilder::append);

        final StringBuilder subjectNamesBuilder = new StringBuilder();

        List<Subject> subjectNames = new ArrayList<>(person.getSubjects());
        for (int i = 0; i < subjectNames.size(); i++) {
            subjectNamesBuilder.append(subjectNames.get(i).getSubjectName());
            if (i != subjectNames.size() - 1) {
                subjectNamesBuilder.append(" | ");
            }
        }

        final StringBuilder paymentsBuilder = new StringBuilder();

        List<Payment> payments = new ArrayList<>(person.getPayments());
        for (int i = 0; i < payments.size(); i++) {
            Payment selected = payments.get(i);
            paymentsBuilder.append(String.format("Month: %5d     Year: %10d     Amount: %10d         ",
                    selected.getMonth(), selected.getYear(), selected.getAmount()));
        }

        final StringBuilder tagsBuilder = new StringBuilder();
        List<Tag> tags = new ArrayList<>(person.getTags());
        for (int i = 0; i < tags.size(); i++) {
            tagsBuilder.append(tags.get(i).toString());
            if (i != tags.size() - 1) {
                tagsBuilder.append(" | ");
            }
        }

        URL personPage = MainApp.class.getResource(FXML_FILE_FOLDER + SEARCH_PAGE_URL);

        String url = personPage.toExternalForm()
                + "?name=" + person.getName().fullName
                + "&phone=" + person.getPhone().value
                + "&email=" + person.getEmail().value
                + "&address=" + person.getAddress().value
                + "&tuitionTimingDay=" + person.getTuitionTiming().day.toString().substring(0, 3)
                + "&tuitionTimingTime=" + person.getTuitionTiming().time
                + "&subjectNames=" + subjectNamesBuilder.toString()
                + "&subjects=" + subjectsBuilder.toString()
                + "&payments=" + paymentsBuilder.toString()
                + "&tags=" + tagsBuilder.toString();

        logger.info(url.replace("#", "%23"));
        loadPage(url.replace("#", "%23"));
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection());
    }
}
