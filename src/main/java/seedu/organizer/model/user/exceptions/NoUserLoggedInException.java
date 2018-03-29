package seedu.organizer.model.user.exceptions;

//@@author dominickenn
/**
 * Signals that no user is logged in
 */
public class NoUserLoggedInException extends Exception {
    public NoUserLoggedInException() {
        super("No user is logged in");
    }
}
