package seedu.organizer.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.organizer.model.user.exceptions.NoUserLoggedInException;

/**
 * Clears the organizer book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String COMMAND_ALIAS = "c";
    public static final String MESSAGE_SUCCESS = "Organizer has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        try {
            model.deleteCurrentUserTasks();
        } catch (NoUserLoggedInException e) {
            throw new AssertionError("No user is logged in");
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
