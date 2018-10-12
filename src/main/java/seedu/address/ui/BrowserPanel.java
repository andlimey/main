package seedu.address.ui;

import java.net.URL;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

import com.google.common.eventbus.Subscribe;

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
        final StringBuilder builder = new StringBuilder();
        person.getTags().forEach(builder::append);

        final StringBuilder builder1 = new StringBuilder();
        person.getPayments().forEach(builder1::append);

        URL personPage = MainApp.class.getResource(FXML_FILE_FOLDER + SEARCH_PAGE_URL);

        String url = personPage.toExternalForm()
                + "?name=" + person.getName().fullName
                + "&phone=" + person.getPhone().value
                + "&email=" + person.getEmail().value
                + "&address=" + person.getAddress().value
                + "&payments=" + builder1.toString()
                + "&tags=" + builder.toString();

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
