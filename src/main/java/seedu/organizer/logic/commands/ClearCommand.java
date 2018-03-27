package seedu.organizer.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.Organizer;
import seedu.organizer.model.task.exceptions.TaskNotFoundException;

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
        model.deleteAllCurrentUserTasks();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
