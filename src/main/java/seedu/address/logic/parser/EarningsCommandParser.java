package seedu.address.logic.parser;

import java.util.stream.Stream;
import seedu.address.logic.commands.EarningsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_YEAR;

public class EarningsCommandParser implements Parser<EarningsCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EarningsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_PAYMENT_MONTH, PREFIX_PAYMENT_YEAR);

        if (!arePrefixesPresent(argMultimap,
                PREFIX_PAYMENT_MONTH, PREFIX_PAYMENT_YEAR)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EarningsCommand.MESSAGE_USAGE));
        }

        int month = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_PAYMENT_MONTH).get());
        int year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_PAYMENT_YEAR).get());

        return new EarningsCommand(month, year);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
