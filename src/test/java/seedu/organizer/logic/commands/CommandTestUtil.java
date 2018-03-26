package seedu.organizer.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.CommandHistory;
import seedu.organizer.logic.UndoRedoStack;
import seedu.organizer.logic.commands.exceptions.CommandException;
import seedu.organizer.logic.commands.util.EditTaskDescriptor;
import seedu.organizer.model.Model;
import seedu.organizer.model.Organizer;
import seedu.organizer.model.task.NameContainsKeywordsPredicate;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.task.exceptions.TaskNotFoundException;
import seedu.organizer.testutil.EditTaskDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_USERNAME_ADMIN = "admin";
    public static final String VALID_PASSWORD_ADMIN = "admin";
    public static final String VALID_NAME_EXAM = "CS2103T Exam";
    public static final String VALID_NAME_STUDY = "Study MA1101R";
    public static final String VALID_NAME_REVISION = "Revision";
    public static final String VALID_PRIORITY_EXAM = "9";
    public static final String VALID_PRIORITY_STUDY = "0";
    public static final String VALID_PRIORITY_REVISION = "5";
    public static final String VALID_DEADLINE_EXAM = "2019-04-05";
    public static final String VALID_DEADLINE_STUDY = "2019-09-11";
    public static final String VALID_DEADLINE_REVISION = "2019-04-05";
    public static final String VALID_DESCRIPTION_EXAM = "CS2103T Exam";
    public static final String VALID_DESCRIPTION_STUDY = "Study for CS2103T Exam";
    public static final String VALID_DESCRIPTION_REVISION = " Revise for CS2106 midterms";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_FRIENDS = "friends";
    public static final String VALID_TAG_UNUSED = "unused"; // do not use this tag when creating a person

    public static final String USERNAME_DESC_ADMIN = " " + PREFIX_USERNAME + VALID_USERNAME_ADMIN;
    public static final String PASSWORD_DESC_ADMIN = " " + PREFIX_PASSWORD + VALID_PASSWORD_ADMIN;
    public static final String NAME_DESC_EXAM = " " + PREFIX_NAME + VALID_NAME_EXAM;
    public static final String NAME_DESC_STUDY = " " + PREFIX_NAME + VALID_NAME_STUDY;
    public static final String NAME_DESC_REVISION = " " + PREFIX_NAME + VALID_NAME_REVISION;
    public static final String PRIORITY_DESC_EXAM = " " + PREFIX_PRIORITY + VALID_PRIORITY_EXAM;
    public static final String PRIORITY_DESC_STUDY = " " + PREFIX_PRIORITY + VALID_PRIORITY_STUDY;
    public static final String PRIORITY_DESC_REVISION = " " + PREFIX_PRIORITY + VALID_PRIORITY_REVISION;
    public static final String DEADLINE_DESC_EXAM = " " + PREFIX_DEADLINE + VALID_DEADLINE_EXAM;
    public static final String DEADLINE_DESC_STUDY = " " + PREFIX_DEADLINE + VALID_DEADLINE_STUDY;
    public static final String DEADLINE_DESC_REVISION = " " + PREFIX_DEADLINE + VALID_DEADLINE_REVISION;
    public static final String DESCRIPTION_DESC_EXAM = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_EXAM;
    public static final String DESCRIPTION_DESC_STUDY = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_STUDY;
    public static final String DESCRIPTION_DESC_REVISION = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_REVISION;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_FRIENDS = " " + PREFIX_TAG + VALID_TAG_FRIENDS;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PRIORITY_DESC = " " + PREFIX_PRIORITY + "911a"; // 'a' not allowed in prioritys
    public static final String INVALID_DEADLINE_DESC = " " + PREFIX_DEADLINE + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_USERNAME_DESC = " " + PREFIX_USERNAME + "b@by"; // '@' not allowed in usernames
    public static final String INVALID_PASSWORD_DESC = " " + PREFIX_PASSWORD + "baby$$"; // '$' is not allowed in passwords

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditTaskDescriptor DESC_EXAM;
    public static final EditTaskDescriptor DESC_STUDY;

    static {
        DESC_EXAM = new EditTaskDescriptorBuilder().withName(VALID_NAME_EXAM)
                .withPriority(VALID_PRIORITY_EXAM).withDeadline(VALID_DEADLINE_EXAM).withDescription
                        (VALID_DESCRIPTION_EXAM)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_STUDY = new EditTaskDescriptorBuilder().withName(VALID_NAME_STUDY)
                .withPriority(VALID_PRIORITY_STUDY).withDeadline(VALID_DEADLINE_STUDY).withDescription
                        (VALID_DESCRIPTION_STUDY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the organizer book and the filtered task list in the {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Organizer expectedOrganizer = new Organizer(actualModel.getOrganizer());
        List<Task> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTaskList());

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedOrganizer, actualModel.getOrganizer());
            assertEquals(expectedFilteredList, actualModel.getFilteredTaskList());
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the task at the given {@code targetIndex} in the
     * {@code model}'s organizer book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTaskList().size());

        Task task = model.getFilteredTaskList().get(targetIndex.getZeroBased());
        final String[] splitName = task.getName().fullName.split("\\s+");
        model.updateFilteredTaskList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredTaskList().size());
    }

    /**
     * Deletes the first task in {@code model}'s filtered list from {@code model}'s organizer book.
     */
    public static void deleteFirstPerson(Model model) {
        Task firstTask = model.getFilteredTaskList().get(0);
        try {
            model.deleteTask(firstTask);
        } catch (TaskNotFoundException pnfe) {
            throw new AssertionError("Task in filtered list must exist in model.", pnfe);
        }
    }

    /**
     * Returns an {@code UndoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static UndoCommand prepareUndoCommand(Model model, UndoRedoStack undoRedoStack) {
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return undoCommand;
    }

    /**
     * Returns a {@code RedoCommand} with the given {@code model} and {@code undoRedoStack} set.
     */
    public static RedoCommand prepareRedoCommand(Model model, UndoRedoStack undoRedoStack) {
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.setData(model, new CommandHistory(), undoRedoStack);
        return redoCommand;
    }
}
