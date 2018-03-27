package seedu.organizer.logic.commands;

//@@author dominickenn

import static java.util.Objects.requireNonNull;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.user.exceptions.DuplicateUserException;
import seedu.organizer.model.user.User;

/**
 * Adds a user to the organizer
 */
public class SignUpUserCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "signup";
    public static final String COMMAND_ALIAS = "su";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a user account in Prioritask. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "david "
            + PREFIX_PASSWORD + "david19583 ";

    public static final String MESSAGE_SUCCESS = "New user created: %1$s";
    public static final String MESSAGE_DUPLICATE_USER = "This user already exists";

    private final User toAdd;

    /**
     * Creates an SignUpUserCommand to add the specified {@code User}
     */
    public SignUpUserCommand(User user) {
        requireNonNull(user);
        toAdd = user;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addUser(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.username));
        } catch (DuplicateUserException e) {
            throw new CommandException(MESSAGE_DUPLICATE_USER);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SignUpUserCommand // instanceof handles nulls
                && toAdd.equals(((SignUpUserCommand) other).toAdd));
    }
}
