package seedu.organizer.logic.commands;

import static java.util.Objects.requireNonNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertFalse;

import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.organizer.logic.CommandHistory;
import seedu.organizer.logic.UndoRedoStack;
import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.model.Model;
import seedu.organizer.model.Organizer;
import seedu.organizer.model.ReadOnlyOrganizer;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.exceptions.DuplicateTaskException;
import seedu.organizer.model.task.exceptions.TaskNotFoundException;
import seedu.organizer.model.user.User;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.DuplicateUserException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;

//@@author dominickenn
public class LoginCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand(null);
    }

    @Test
    public void execute_userAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingUserLogin modelStub = new ModelStubAcceptingUserLogin();
        User validUser = new User("david", "david123");
        modelStub.addUser(validUser);

        CommandResult commandResult = getLoginCommandForUser(validUser, modelStub).execute();

        assertEquals(String.format(LoginCommand.MESSAGE_SUCCESS, validUser), commandResult.feedbackToUser);
    }

    @Test
    public void execute_noSuchUser_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingUserNotFoundException();
        User noSuchUser = new User("no", "no");

        thrown.expect(CommandException.class);
        thrown.expectMessage(LoginCommand.MESSAGE_USER_NOT_FOUND);

        getLoginCommandForUser(noSuchUser, modelStub).execute();
    }

    @Test
    public void execute_UserCurrentlyLoggedIn_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingCurrentlyLoggedInException();
        User validUser = new User("admin", "admin");

        thrown.expect(CommandException.class);
        thrown.expectMessage(LoginCommand.MESSAGE_CURRENTLY_LOGGED_IN);

        getLoginCommandForUser(validUser, modelStub).execute();
    }

    @Test
    public void equals() {
        User alice = new User("alice", "alice123");
        User bob = new User("bob", "bob123");
        LoginCommand loginAliceCommand = new LoginCommand(alice);
        LoginCommand loginBobCommand = new LoginCommand(bob);

        // same object -> returns true
        assertTrue(loginAliceCommand.equals(loginAliceCommand));

        // same values -> returns true
        LoginCommand loginAliceCommandCopy = new LoginCommand(alice);
        assertTrue(loginAliceCommand.equals(loginAliceCommandCopy));

        // different types -> returns false
        assertFalse(loginAliceCommand.equals(1));

        // null -> returns false
        assertFalse(loginAliceCommand.equals(null));

        // different task -> returns false
        assertFalse(loginAliceCommand.equals(loginBobCommand));
    }

    /**
     * Generates a new LoginCommand with the given user.
     */
    private LoginCommand getLoginCommandForUser(User user, Model model) {
        LoginCommand command = new LoginCommand(user);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        Organizer organizer = new Organizer();

        @Override
        public void addUser(User user) throws DuplicateUserException {
            fail("This method should not be called");
        }

        @Override
        public void loginUser(User user) throws UserNotFoundException, CurrentlyLoggedInException {
            fail("This method should not be called");
        }

        @Override
        public void addTask(Task task) throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyOrganizer newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyOrganizer getOrganizer() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deleteCurrentUserTasks() {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTask(Task target) throws TaskNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updateTask(Task target, Task editedTask)
                throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<Task> getFilteredTaskList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredTaskList(Predicate<Task> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a NoSuchUserException when trying to login a user.
     */
    private class ModelStubThrowingUserNotFoundException extends ModelStub {
        @Override
        public void loginUser(User user) throws UserNotFoundException, CurrentlyLoggedInException {
            throw new UserNotFoundException();
        }

        @Override
        public ReadOnlyOrganizer getOrganizer() {
            return new Organizer();
        }
    }

    /**
     * A Model stub that always throw a CurrentlyLoggedInException when trying to login a user.
     */
    private class ModelStubThrowingCurrentlyLoggedInException extends ModelStub {
        @Override
        public void loginUser(User user) throws UserNotFoundException, CurrentlyLoggedInException {
            throw new CurrentlyLoggedInException();
        }

        @Override
        public ReadOnlyOrganizer getOrganizer() {
            return new Organizer();
        }
    }

    /**
     * A Model stub that always accept the user login attempt.
     */
    private class ModelStubAcceptingUserLogin extends ModelStub {
        Organizer organizer = new Organizer();

        @Override
        public void addUser(User user) throws DuplicateUserException {
            requireNonNull(user);
            organizer.addUser(user);
        }

        @Override
        public void loginUser(User user) throws UserNotFoundException, CurrentlyLoggedInException {
            requireNonNull(user);
            organizer.loginUser(user);
        }

        @Override
        public ReadOnlyOrganizer getOrganizer() {
            return new Organizer();
        }
    }

}

