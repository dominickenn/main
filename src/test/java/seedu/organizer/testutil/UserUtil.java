package seedu.organizer.testutil;

import static seedu.organizer.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.organizer.logic.commands.AddCommand;
import seedu.organizer.logic.commands.CreateUserCommand;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.user.User;

//@@author dominickenn
/**
 * A utility class for User.
 */
public class UserUtil {

    /**
     * Returns an createuser command string for adding the {@code user}.
     */
    public static String getCreateUserCommand(User user) {
        return CreateUserCommand.COMMAND_WORD + " " + getUserDetails(user);
    }

    /**
     * Returns an createuser command string for adding the {@code user} using alias.
     */
    public static String getCreateUserCommandAlias(User user) {
        return CreateUserCommand.COMMAND_ALIAS + " " + getUserDetails(user);
    }

    /**
     * Returns the part of command string for the given {@code user}'s details.
     */
    public static String getUserDetails(User user) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_USERNAME + user.username + " ");
        sb.append(PREFIX_PASSWORD + user.password + " ");
        return sb.toString();
    }
}
