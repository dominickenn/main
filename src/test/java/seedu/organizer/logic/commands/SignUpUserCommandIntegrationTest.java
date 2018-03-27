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

//@@author dominickenn
/**
 * Contains integration tests (interaction with the Model) for {@code SignUpUserCommand}.
 */
public class SignUpUserCommandIntegrationTest {

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalOrganizer(), new UserPrefs());
    }

    @Test
    public void execute_newUser_success() throws Exception {
        User validUser = new User("bob", "bobby97");

        Model expectedModel = new ModelManager(model.getOrganizer(), new UserPrefs());
        expectedModel.addUser(validUser);

        assertCommandSuccess(prepareCommand(validUser, model), model,
                String.format(SignUpUserCommand.MESSAGE_SUCCESS, validUser), expectedModel);
    }

    @Test
    public void execute_duplicateUser_throwsCommandException() {
        User userInList = model.getOrganizer().getUserList().get(0);
        assertCommandFailure(prepareCommand(userInList, model), model, SignUpUserCommand.MESSAGE_DUPLICATE_USER);
    }

    /**
     * Generates a new {@code SignUpUserCommand} which upon execution, adds {@code user} into the {@code model}.
     */
    private SignUpUserCommand prepareCommand(User user, Model model) {
        SignUpUserCommand command = new SignUpUserCommand(user);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
