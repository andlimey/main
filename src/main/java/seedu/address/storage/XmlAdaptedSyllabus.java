package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.subject.Syllabus;

/**
 * JAXB-friendly adapted version of the Syllabus.
 */
public class XmlAdaptedSyllabus {

    @XmlValue
    private String value;

    /**
     * Constructs an XmlAdaptedSyllabus.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSyllabus() {}

    /**
     * Constructs a {@code XmlAdaptedSyllabus} with the given {@code syllabus}.
     */
    public XmlAdaptedSyllabus(String syllabus) {
        this.value = syllabus;
    }

    /**
     * Converts a given Syllabus into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedSyllabus(Syllabus source) {
        value = source.syllabus;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Tag object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Syllabus toModelType() throws IllegalValueException {
        if (!Syllabus.isValidSyllabus(value)) {
            throw new IllegalValueException(Syllabus.MESSAGE_SYLLABUS_CONSTRAINTS);
        }
        return new Syllabus(value, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof XmlAdaptedSyllabus // instanceof handles nulls
                && value.equals(((XmlAdaptedSyllabus) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
