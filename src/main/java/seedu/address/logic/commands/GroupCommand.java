package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.tuitiontiming.TuitionTimingContainsKeywordsPredicate;

/**
 * Groups the students in TutorHelper based on their tuition timings and sorts the list accordingly.
 */
public class GroupCommand extends Command {
    public static final String COMMAND_WORD = "group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Groups all students based on the specified DAY "
            + "or TIME and displays them as a list.\n"
            + "Parameter: KEYWORD\n"
            + "Examples:\n" + COMMAND_WORD + " Monday\n"
            + COMMAND_WORD + " 12:00pm";

    public static final String MESSAGE_SUCCESS = "Grouped all students";

    private final TuitionTimingContainsKeywordsPredicate predicate;
    private final boolean isDay;
    private final boolean isTime;

    public GroupCommand(TuitionTimingContainsKeywordsPredicate predicate, boolean isDayRegex, boolean isTimeRegex) {
        this.predicate = predicate;
        this.isDay = isDayRegex;
        this.isTime = isTimeRegex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);

        if (this.isDay) {
            assert !this.isTime;
            model.sortByTime();
            model.commitTutorHelper();
        } else if (this.isTime) {
            model.sortByDay();
            model.commitTutorHelper();
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size())
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupCommand // instanceof handles nulls
                && predicate.equals(((GroupCommand) other).predicate)
                && isDay == (((GroupCommand) other).isDay)
                && isTime == (((GroupCommand) other).isTime)); // state check
    }
}
