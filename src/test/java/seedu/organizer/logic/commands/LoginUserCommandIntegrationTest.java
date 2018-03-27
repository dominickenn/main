package seedu.organizer.logic.commands;

import static seedu.organizer.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.organizer.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.organizer.testutil.TypicalTasks.ADMIN;
import static seedu.organizer.testutil.TypicalTasks.getTypicalOrganizer;

import org.junit.Before;
import org.junit.Test;

import seedu.organizer.logic.CommandHistory;
import seedu.organizer.logic.UndoRedoStack;
import seedu.organizer.model.Model;
import seedu.organizer.model.ModelManager;
import seedu.organizer.model.UserPrefs;
import seedu.organizer.model.user.User;

//@@author dominickenn
/**
 * Contains integration tests (interaction with the Model) and unit tests for LoginCommand.
 */
public class LoginUserCommandIntegrationTest {
    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalOrganizer(), new UserPrefs());
    }

    @Test
    public void execute_loginUser_success() throws Exception {
        User validUser = new User("admin", "admin");

        Model expectedModel = new ModelManager(model.getOrganizer(), new UserPrefs());
        expectedModel.loginUser(ADMIN);

        assertCommandSuccess(prepareCommand(validUser, model), model,
                String.format(LoginUserCommand.MESSAGE_SUCCESS, validUser), expectedModel);
    }

    @Test
    public void execute_noSuchUser_throwsCommandException() {
        User fakeUser = new User("Idontexist", "Idontexist");
        assertCommandFailure(prepareCommand(fakeUser, model), model, LoginUserCommand.MESSAGE_USER_DOES_NOT_EXIST);
    }

    /**
     * Generates a new {@code LoginUserCommand} which upon execution, sets {@code user} as currentUser in {@code model}.
     */
    private LoginUserCommand prepareCommand(User user, Model model) {
        LoginUserCommand command = new LoginUserCommand(user);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
