package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.TuitionTimingContainsKeywordsPredicate;

/**
 * Sorts the students in TutorHelper based on their tuition timings.
 */
public class GroupCommand extends Command {
    public static final String COMMAND_WORD = "group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Groups all students based on the specified DAY "
            + "or TIME and displays them as a list.\n"
            + "The input can also be null and the students will be grouped according to the current day and time.\n"
            + "Parameters: KEYWORD\n"
            + "Examples: " + COMMAND_WORD + "Monday\n"
            + COMMAND_WORD+ "12:00pm";

    public static final String MESSAGE_SUCCESS = "Grouped all students";

    private final TuitionTimingContainsKeywordsPredicate predicate;

    public GroupCommand(TuitionTimingContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList(). size())
        );
    }
}