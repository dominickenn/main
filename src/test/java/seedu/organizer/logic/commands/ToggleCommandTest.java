package seedu.organizer.logic.commands;

import static seedu.organizer.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.organizer.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.organizer.logic.commands.CommandTestUtil.prepareRedoCommand;
import static seedu.organizer.logic.commands.CommandTestUtil.prepareUndoCommand;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.organizer.testutil.TypicalTasks.getTypicalOrganizer;

import org.junit.Test;

import seedu.organizer.commons.core.Messages;
import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.CommandHistory;
import seedu.organizer.logic.UndoRedoStack;
import seedu.organizer.model.Model;
import seedu.organizer.model.ModelManager;
import seedu.organizer.model.Organizer;
import seedu.organizer.model.UserPrefs;
import seedu.organizer.model.task.Task;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * ToggleCommand.
 */
public class ToggleCommandTest {
    private Model model = new ModelManager(getTypicalOrganizer(), new UserPrefs());

    @Test
    public void execute_unfilteredList_success() throws Exception {
        Task originalTask = model.getFilteredTaskList().get(0);
        Task toggledTask = toggleTask(originalTask);

        ToggleCommand toggleCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(ToggleCommand.MESSAGE_EDIT_TASK_SUCCESS, toggledTask);

        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.updateTask(originalTask, toggledTask);

        assertCommandSuccess(toggleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_secondunfilteredList_success() throws Exception {
        Task originalTask = model.getFilteredTaskList().get(1);
        Task toggledTask = toggleTask(originalTask);

        ToggleCommand toggleCommand = prepareCommand(INDEX_SECOND_TASK);

        String expectedMessage = String.format(ToggleCommand.MESSAGE_EDIT_TASK_SUCCESS, toggledTask);

        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());
        expectedModel.updateTask(originalTask, toggledTask);

        assertCommandSuccess(toggleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ToggleCommand toggleCommand = prepareCommand(outOfBoundsIndex);

        assertCommandFailure(toggleCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        UndoRedoStack undoRedoStack = new UndoRedoStack();
        UndoCommand undoCommand = prepareUndoCommand(model, undoRedoStack);
        RedoCommand redoCommand = prepareRedoCommand(model, undoRedoStack);
        Task originalTask = model.getFilteredTaskList().get(0);
        Task toggledTask = toggleTask(originalTask);
        ToggleCommand toggleCommand = prepareCommand(INDEX_FIRST_TASK);
        Model expectedModel = new ModelManager(new Organizer(model.getOrganizer()), new UserPrefs());

        // edit -> first task edited
        toggleCommand.execute();
        undoRedoStack.push(toggleCommand);

        // undo -> reverts organizer back to previous state and filtered task list to show all persons
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first task edited again
        expectedModel.updateTask(originalTask, toggledTask);
        assertCommandSuccess(redoCommand, model, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Retrun an (@code Task) with status inversed
     */
    private Task toggleTask(Task task) {
        return new Task(
                task.getName(),
                task.getPriority(),
                task.getDeadline(),
                task.getDateAdded(),
                task.getDescription(),
                task.getStatus().getInverse(),
                task.getTags(),
                task.getSubtasks()
        );
    }

    /**
     * Returns an {@code ToggleCommand} with parameters {@code index} and {@code descriptor}
     */
    private ToggleCommand prepareCommand(Index index) {
        ToggleCommand toggleCommand = new ToggleCommand(index);
        toggleCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return toggleCommand;
    }
}
