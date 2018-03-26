package systemtests;

import static seedu.organizer.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.organizer.testutil.TypicalTasks.BOB;

import org.junit.Test;

import seedu.organizer.logic.commands.CreateUserCommand;
import seedu.organizer.model.Model;
import seedu.organizer.model.task.exceptions.DuplicateUserException;
import seedu.organizer.model.user.User;
import seedu.organizer.testutil.UserUtil;

//@@author dominickenn
public class CreateUserCommandSystemTest extends OrganizerSystemTest{

    @Test
    public void add() throws Exception {
        Model model = getModel();

        /* Case: add a user to a non-empty organizer, command with leading spaces and trailing spaces
         * -> added
         */
        User toAdd = BOB;
        String command = "   " + CreateUserCommand.COMMAND_WORD + "  " + PREFIX_USERNAME + "bob " +
                PREFIX_PASSWORD + "b0b ";
        assertCommandSuccess(command, toAdd);
    }

    /**
     * Executes the {@code CreateUserCommand} that adds {@code toAdd} to the model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code CreateUserCommand} with the details of
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
    private void assertCommandSuccess(User toAdd) {
        assertCommandSuccess(UserUtil.getCreateUserCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(User)}. Executes {@code command}
     * instead.
     *
     * @see CreateUserCommandSystemTest#assertCommandSuccess(User)
     */
    private void assertCommandSuccess(String command, User toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addUser(toAdd);
        } catch (DuplicateUserException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(CreateUserCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, User)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Model}, {@code Storage} and {@code TaskListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     *
     * @see CreateUserCommandSystemTest#assertCommandSuccess(String, User)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarChangedExceptSaveLocation();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Model}, {@code Storage} and {@code TaskListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
