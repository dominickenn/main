package seedu.organizer.testutil;

import static seedu.organizer.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.organizer.logic.commands.SignUpUserCommand;
import seedu.organizer.model.user.User;

//@@author dominickenn
/**
 * A utility class for User.
 */
public class UserUtil {

    /**
     * Returns an signupuser command string for adding the {@code user}.
     */
    public static String getSignUpUserCommand(User user) {
        return SignUpUserCommand.COMMAND_WORD + " " + getUserDetails(user);
    }

    /**
     * Returns an signupuser command string for adding the {@code user} using alias.
     */
    public static String getSignUpUserCommandAlias(User user) {
        return SignUpUserCommand.COMMAND_ALIAS + " " + getUserDetails(user);
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
