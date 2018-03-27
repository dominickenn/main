package seedu.organizer.logic.commands;

//@@author dominickenn

import static java.util.Objects.requireNonNull;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.user.User;
import seedu.organizer.model.user.exceptions.UserNotFoundException;

/**
 * Login a user into the organizer
 */
public class LoginUserCommand extends Command {

    public static final String COMMAND_WORD = "login";
    public static final String COMMAND_ALIAS = "log";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Login a user into PrioriTask, if the user account exists. "
            + "Parameters: "
            + PREFIX_USERNAME + "USERNAME "
            + PREFIX_PASSWORD + "PASSWORD \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_USERNAME + "david "
            + PREFIX_PASSWORD + "david19583 ";

    public static final String MESSAGE_SUCCESS = "User logged in: %1$s";
    public static final String MESSAGE_USER_DOES_NOT_EXIST = "No such user exists";

    private final User toLogin;

    public LoginUserCommand(User toLogin) {
        requireNonNull(toLogin);
        this.toLogin = toLogin;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.loginUser(toLogin);
        } catch (UserNotFoundException e) {
            throw new CommandException(MESSAGE_USER_DOES_NOT_EXIST);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toLogin.username));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoginUserCommand // instanceof handles nulls
                && this.toLogin.equals(((LoginUserCommand) other).toLogin)); // state check
    }

}
