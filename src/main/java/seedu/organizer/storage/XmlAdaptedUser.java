package seedu.organizer.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.model.user.User;

//@@author dominickenn
/**
 * JAXB-friendly adapted version of the Tag.
 */
public class XmlAdaptedUser {

    @XmlElement
    private String username;
    @XmlElement
    private String password;

    /**
     * Constructs an XmlAdaptedUser.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedUser() {}

    /**
     * Constructs a {@code XmlAdaptedUser} with the given {@code username} and {@code password}.
     */
    public XmlAdaptedUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Converts a given User into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedUser(User source) {
        username = source.username;
        password = source.password;
    }

    /**
     * Converts this jaxb-friendly adapted user object into the model's User object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted task
     */
    public User toModelType() throws IllegalValueException {
        if (!User.isValidUsername(username)) {
            throw new IllegalValueException(User.MESSAGE_USERNAME_CONSTRAINTS);
        }
        if (!User.isValidPassword(password)) {
            throw new IllegalValueException(User.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        return new User(username, password);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedUser)) {
            return false;
        }

        return username.equals(((XmlAdaptedUser) other).username)
               && password.equals(((XmlAdaptedUser) other).password);
    }
}
