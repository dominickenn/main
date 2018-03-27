package seedu.organizer.model.user.exceptions;

//@@author dominickenn
/**
 * Signals that the operation is unable to find the specified user.
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User does not exist");
    }
}
