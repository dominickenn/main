package systemtests;

import static seedu.organizer.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.organizer.testutil.TypicalTasks.ADMIN;
import static seedu.organizer.testutil.TypicalTasks.BOB;

import org.junit.Test;

import seedu.organizer.logic.commands.LoginUserCommand;
import seedu.organizer.logic.commands.SignUpUserCommand;
import seedu.organizer.model.Model;
import seedu.organizer.model.user.exceptions.DuplicateUserException;
import seedu.organizer.model.user.User;
import seedu.organizer.model.user.exceptions.UserNotFoundException;
import seedu.organizer.testutil.UserUtil;

//@@author dominickenn
public class LoginUserCommandSystemTest extends OrganizerSystemTest{

    @Test
    public void login() throws Exception {
        Model model = getModel();
        /* Case: login user to a non-empty organizer, command with leading spaces and trailing spaces
         * -> login
         */
        User toLogin = ADMIN;
        String command = LoginUserCommand.COMMAND_WORD + "  " + PREFIX_USERNAME + "admin " +
                PREFIX_PASSWORD + "admin ";
        assertCommandSuccess(command, toLogin);
    }

    /**
     * Executes the {@code SignUpUserCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code SignUpUserCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Model}, {@code Storage} and {@code TaskListPanel} equal to the corresponding components in
     * the current model added with {@code toAdd}.<br>
     * 5. Browser url and selected card remain unchanged.<br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(User toAdd) throws UserNotFoundException {
        assertCommandSuccess(UserUtil.getSignUpUserCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(User)}. Executes {@code command}
     * instead.
     *
     * @see SignUpUserCommandSystemTest#assertCommandSuccess(User)
     */
    private void assertCommandSuccess(String command, User toLogin) throws UserNotFoundException {
        Model expectedModel = getModel();
        try {
            expectedModel.loginUser(toLogin);
        } catch (UserNotFoundException unf) {
            throw new IllegalArgumentException("toLogin does not exists in the model.");
        }
        String expectedResultMessage = String.format(LoginUserCommand.MESSAGE_SUCCESS, toLogin);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, User)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code TaskListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     *
     * @see SignUpUserCommandSystemTest#assertCommandSuccess(String, User)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) throws UserNotFoundException {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarChangedExceptSaveLocation();
    }
}
