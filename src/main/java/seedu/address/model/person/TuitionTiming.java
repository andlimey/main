package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents tuition timing in TutorHelper.
 * Guarantees: immutable; is valid as declared in {@link #isValidTiming(String)}
 */
public class TuitionTiming {

    private static final String DAY_REGEX = "^(Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday)";
    private static final String TIME_REGEX = "\\d{1,2}[:.]\\d{2}(am|pm|AM|PM)$";
    public static final String MESSAGE_TUITION_TIMING_CONSTRAINTS =
            "Tuition Day and Time should not be blank and should be in the format:\n"
            + "1) Day, Time\n"
            + "2) Day Time.\n"
            + "Examples of Valid Input:\n"
            + "Monday, 1.00pm\n"
            + "Tuesday, 1:15AM\n"
            + "Wednesday 12:30pm\n";

    /*
     * The first character of the tuition time and day must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TUITION_TIMING_VALIDATION_REGEX = DAY_REGEX + ".{1,2}" + TIME_REGEX;

    public final String value;

    /**
     * Constructs an {@code TuitionTiming}.
     *
     * @param tuitionTiming A valid tuition time and day.
     */
    public TuitionTiming(String tuitionTiming) {
        requireNonNull(tuitionTiming);
        checkArgument(isValidTiming(tuitionTiming), MESSAGE_TUITION_TIMING_CONSTRAINTS);
        value = tuitionTiming;
    }

    /**
     * Returns true if a given string is a valid time and day
     */
    public static boolean isValidTiming(String test) {
        return test.matches(TUITION_TIMING_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Address // instanceof handles nulls
                && value.equals(((TuitionTiming) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
