package seedu.organizer.model.user;

import static java.util.Objects.requireNonNull;

import static seedu.organizer.commons.util.AppUtil.checkArgument;

//@@author dominickenn
/**
 * Represents a User in the organizer book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class User {

    public static final String MESSAGE_USERNAME_CONSTRAINTS =
            "Username should only contain alphanumeric characters and spaces, and it should not be blank";
    /*
     * The first character of the username must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String USERNAME_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public static final String MESSAGE_PASSWORD_CONSTRAINTS =
            "Password should only contain alphanumeric characters and spaces, and it should not be blank";
    /*
     * The first character of the password must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String PASSWORD_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String username;

    public final String password;

    /**
     * Constructs a {@code User}.
     *
     * @param username A valid username.
     * @param password A valid password.
     */
    public User(String username, String password) {
        requireNonNull(username);
        requireNonNull(password);
        checkArgument(isValidUsername(username), MESSAGE_USERNAME_CONSTRAINTS);
        checkArgument(isValidPassword(password), MESSAGE_PASSWORD_CONSTRAINTS);
        this.username = username;
        this.password = password;
    }

    /**
     * Returns true if a given string is a valid username.
     */
    public static boolean isValidUsername(String test) {
        return test.matches(USERNAME_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid password.
     */
    public static boolean isValidPassword(String test) {
        return test.matches(PASSWORD_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof User // instanceof handles nulls
                && this.username.equals(((User) other).username)  // state check
                && this.password.equals(((User) other).password)); // state check
    }

    @Override
    public int hashCode() {
        return (username + " " + password).hashCode();
    }

}
