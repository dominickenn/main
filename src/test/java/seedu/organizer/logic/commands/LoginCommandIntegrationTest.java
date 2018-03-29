package seedu.organizer.logic.commands;

import static seedu.organizer.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.organizer.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.organizer.testutil.TypicalTasks.getTypicalOrganizer;

import org.junit.Before;
import org.junit.Test;

import seedu.organizer.logic.CommandHistory;
import seedu.organizer.logic.UndoRedoStack;
import seedu.organizer.model.Model;
import seedu.organizer.model.ModelManager;
import seedu.organizer.model.UserPrefs;
import seedu.organizer.model.user.User;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;

//@@author dominickenn
/**
 * Contains integration tests (interaction with the Model) for {@code LoginCommand}.
 */
public class LoginCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalOrganizer(), new UserPrefs());
    }

    @Test
    public void execute_userLogin_success() throws Exception {
        User validUser = new User("admin", "admin");

        Model expectedModel = new ModelManager(model.getOrganizer(), new UserPrefs());
        expectedModel.loginUser(validUser);

        assertCommandSuccess(prepareCommand(validUser, model), model,
                String.format(LoginCommand.MESSAGE_SUCCESS, validUser), expectedModel);
    }

    @Test
    public void execute_userNotFound_throwsCommandException() {
        User userNotInList = new User("userNotInList", "userNotInList");
        assertCommandFailure(prepareCommand(userNotInList, model), model, LoginCommand.MESSAGE_USER_NOT_FOUND);
    }

    @Test
    public void execute_currentlyLoggedIn_throwsCommandException() {
        User user = new User("admin", "admin");
        try {
            model.loginUser(user);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (CurrentlyLoggedInException e) {
            e.printStackTrace();
        }
        assertCommandFailure(prepareCommand(user, model), model, LoginCommand.MESSAGE_CURRENTLY_LOGGED_IN);
    }

    /**
     * Generates a new {@code LoginCommand} which upon execution, login {@code user} into the {@code model}.
     */
    private LoginCommand prepareCommand(User user, Model model) {
        LoginCommand command = new LoginCommand(user);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
