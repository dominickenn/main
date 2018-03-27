package seedu.organizer.logic.commands;

import static java.util.Objects.requireNonNull;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertFalse;
import static seedu.organizer.testutil.TypicalTasks.ADMIN;

import java.util.ArrayList;
import java.util.Arrays;
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
import seedu.organizer.model.user.exceptions.DuplicateUserException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;

//@@author dominickenn
public class LoginUserCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullUser_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SignUpUserCommand(null);
    }

    @Test
    public void execute_userAcceptedByModel_addSuccessful() throws Exception {
        ModelStubLoginUserAdded modelStub = new ModelStubLoginUserAdded();
        User validUser = ADMIN;

        CommandResult commandResult = getLoginUserCommandForUser(validUser, modelStub).execute();

        assertEquals(String.format(SignUpUserCommand.MESSAGE_SUCCESS, validUser), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validUser), modelStub.usersAdded);
    }

    @Test
    public void execute_duplicateUser_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateUserException();
        User validUser = ADMIN;

        thrown.expect(CommandException.class);
        thrown.expectMessage(SignUpUserCommand.MESSAGE_DUPLICATE_USER);

        getLoginUserCommandForUser(validUser, modelStub).execute();
    }

    @Test
    public void equals() {
        User alice = new User("alice", "al1ce");
        User bob = new User("bob", "b0b");
        LoginUserCommand loginAliceCommand = new LoginUserCommand(alice);
        LoginUserCommand loginBobCommand = new LoginUserCommand(bob);

        // same object -> returns true
        assertTrue(loginAliceCommand.equals(loginAliceCommand));

        // same values -> returns true
        LoginUserCommand addAliceCommandCopy = new LoginUserCommand(alice);
        assertTrue(loginAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(loginAliceCommand.equals(1));

        // null -> returns false
        assertFalse(loginAliceCommand.equals(null));

        // different task -> returns false
        assertFalse(loginAliceCommand.equals(loginBobCommand));
    }

    /**
     * Generates a new SignUpUserCommand with the details of the given user.
     */
    private SignUpUserCommand getLoginUserCommandForUser(User user, Model model) {
        SignUpUserCommand command = new SignUpUserCommand(user);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addTask(Task task) throws DuplicateTaskException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyOrganizer newData) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteAllCurrentUserTasks() {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyOrganizer getOrganizer() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void addUser(User user) throws DuplicateUserException {
            fail("This method should not be called.");
        }

        @Override
        public void loginUser(User user) throws UserNotFoundException {
            fail("This method should not be called");
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
        public void updateFilteredTaskListWithCurrentUser(Predicate<Task> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateUserException when trying to add a user.
     */
    private class ModelStubThrowingDuplicateUserException extends ModelStub {
        @Override
        public void addUser(User user) throws DuplicateUserException {
            throw new DuplicateUserException();
        }

        @Override
        public ReadOnlyOrganizer getOrganizer() {
            return new Organizer();
        }
    }

    /**
     * A Model stub that always accept the user being added.
     */
    private class ModelStubLoginUserAdded extends ModelStub {
        final ArrayList<User> usersAdded = new ArrayList<>();

        @Override
        public void addUser(User user) throws DuplicateUserException {
            requireNonNull(user);
            usersAdded.add(user);
        }

        @Override
        public ReadOnlyOrganizer getOrganizer() {
            return new Organizer();
        }
    }

}
