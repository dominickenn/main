package seedu.organizer.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
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
import seedu.organizer.model.task.exceptions.DuplicateUserException;
import seedu.organizer.model.task.exceptions.TaskNotFoundException;
import seedu.organizer.model.user.User;
import seedu.organizer.testutil.TaskBuilder;

public class CreateUserCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullUser_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CreateUserCommand(null);
    }

    @Test
    public void execute_userAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingUserAdded modelStub = new ModelStubAcceptingUserAdded();
        User validUser = ADMIN;

        CommandResult commandResult = getCreateUserCommandForUser(validUser, modelStub).execute();

        assertEquals(String.format(CreateUserCommand.MESSAGE_SUCCESS, validUser), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validUser), modelStub.usersAdded);
    }

    @Test
    public void execute_duplicateUser_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateUserException();
        User validUser = ADMIN;

        thrown.expect(CommandException.class);
        thrown.expectMessage(CreateUserCommand.MESSAGE_DUPLICATE_USER);

        getCreateUserCommandForUser(validUser, modelStub).execute();
    }

    @Test
    public void equals() {
        User alice = new User("alice", "al1ce");
        User bob = new User("bob", "b0b");
        CreateUserCommand createAliceCommand = new CreateUserCommand(alice);
        CreateUserCommand createBobCommand = new CreateUserCommand(bob);

        // same object -> returns true
        assertTrue(createAliceCommand.equals(createAliceCommand));

        // same values -> returns true
        CreateUserCommand addAliceCommandCopy = new CreateUserCommand(alice);
        assertTrue(createAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(createAliceCommand.equals(1));

        // null -> returns false
        assertFalse(createAliceCommand.equals(null));

        // different task -> returns false
        assertFalse(createAliceCommand.equals(createBobCommand));
    }

    /**
     * Generates a new CreateUserCommand with the details of the given user.
     */
    private CreateUserCommand getCreateUserCommandForUser(User user, Model model) {
        CreateUserCommand command = new CreateUserCommand(user);
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
        public ReadOnlyOrganizer getOrganizer() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void addUser(User user) throws DuplicateUserException {
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
    private class ModelStubAcceptingUserAdded extends ModelStub {
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
