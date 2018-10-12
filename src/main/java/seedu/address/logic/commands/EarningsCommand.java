package seedu.address.logic.commands;

import java.util.List;
import java.util.logging.Logger;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Payment;
import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_YEAR;

/*
 * Displays earning of the month, year.
 */
public class EarningsCommand extends Command {
    public static final String COMMAND_WORD = "earnings";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows earnings of the month\n"
            + "Parameters: "
            + "MONTH YEAR \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PAYMENT_MONTH + "08 "
            + PREFIX_PAYMENT_YEAR + "2018";

    private final Logger logger = LogsCenter.getLogger(PayCommand.class);
    private int month;
    private int year;
    private int earnings=0;

    public EarningsCommand(int month, int year) {
        this.month = month;
        this.year = year;
    }

    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        for(int i=0; i<lastShownList.size(); i++){
            List<Payment> personPaymentsList = lastShownList.get(i).getPayments();
            for(int j=0; j<personPaymentsList.size(); j++){
                Payment thisPayment = personPaymentsList.get(j);
                if(thisPayment.getMonth()==this.month && thisPayment.getYear()==this.year){
                    this.earnings+=(thisPayment.getAmount());
                }
            }
        }
        return new CommandResult("Earnings: $"+String.valueOf(earnings));
    }
}
