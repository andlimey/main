package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.GroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TuitionTimingContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new GroupCommand object
 */
public class GroupCommandParser implements Parser<GroupCommand> {

    private static final String DAY_REGEX = "^(Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday)";
    private static final String TIME_REGEX = "\\d{1,2}[:.]\\d{2}(am|pm|AM|PM)$";

    /**
     * Parses the given {@code String} of arguments in the context of the GroupCommand
     * and returns an GroupCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GroupCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            //use date()
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        String keyword = nameKeywords[0];

        if (!(keyword.matches(DAY_REGEX) || keyword.matches(TIME_REGEX)) || nameKeywords.length > 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE));
        }

        return new GroupCommand(new TuitionTimingContainsKeywordsPredicate(keyword));
    }
}

/*
TODO: Also need to group by Date() if no input entered
 */