package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Payment;
import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_YEAR;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Add payment details of an existing person in the TutorHelper.
 */
public class PayCommand extends Command {
    public static final String COMMAND_WORD = "paid";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates if a person has paid.\n"
            + "Parameters: "
            + "INDEX AMOUNT MONTH YEAR \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PAYMENT + "1 "
            + PREFIX_PAYMENT_AMOUNT + "200 "
            + PREFIX_PAYMENT_MONTH + "08 "
            + PREFIX_PAYMENT_YEAR + "2018";
    public static final String MESSAGE_PAYMENT_SUCCESS = "Payment for this person is added: %1$s";

    private final Logger logger = LogsCenter.getLogger(PayCommand.class);
    private Index targetIndex;
    private Payment newPayment;

    public PayCommand(Payment payment) {
        this.targetIndex = payment.getIndex();
        this.newPayment = payment;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personTarget = lastShownList.get(targetIndex.getZeroBased());

        List<Payment> pay = personTarget.getPayments();

        for (int i = 0; i < pay.size(); i++) {
            if (pay.get(i).toString().equals(newPayment.toString())) {
                throw new CommandException(Messages.MESSAGE_DUPLICATE_ENTRY);
            }
        }

        List<Payment> updatedPayments = updatePayment(personTarget.getPayments(), newPayment);

        Person personToPay = new Person(personTarget.getName(), personTarget.getPhone(),
                personTarget.getEmail(), personTarget.getAddress(), personTarget.getSubjects(),
                personTarget.getTuitionTiming(), personTarget.getTags(), updatedPayments);

        model.updatePerson(personTarget, personToPay);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_PAYMENT_SUCCESS, personToPay));
    }



    /**
     * Update payment for this person and returns a new instance of this person.
     * @return the same person but updated with payment.
     */
    public List<Payment> updatePayment(List<Payment> oldPayments, Payment newPayment) {
        List<Payment> updatedPayment = new ArrayList<>(oldPayments);
        updatedPayment.add(newPayment);
        return updatedPayment;
    }
}
