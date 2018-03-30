package systemtests;

import static org.junit.Assert.assertFalse;
import static seedu.organizer.commons.core.Messages.MESSAGE_TASKS_LISTED_OVERVIEW;
import static seedu.organizer.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.organizer.testutil.TypicalTasks.ADMIN_USER;
import static seedu.organizer.testutil.TypicalTasks.HOMEWORK;
import static seedu.organizer.testutil.TypicalTasks.KEYWORD_MATCHING_DO;
import static seedu.organizer.testutil.TypicalTasks.KEYWORD_MATCHING_SPRING;
import static seedu.organizer.testutil.TypicalTasks.PREPAREBREAKFAST;
import static seedu.organizer.testutil.TypicalTasks.PROJECT;
import static seedu.organizer.testutil.TypicalTasks.SPRINGCLEAN;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.organizer.commons.core.index.Index;
import seedu.organizer.logic.commands.DeleteCommand;
import seedu.organizer.logic.commands.FindNameCommand;
import seedu.organizer.logic.commands.RedoCommand;
import seedu.organizer.logic.commands.UndoCommand;
import seedu.organizer.model.Model;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;

public class FindNameCommandSystemTest extends OrganizerSystemTest {

    @Test
    public void find() {
        /* Case: find multiple tasks in organizer, command with leading spaces and trailing spaces
         * -> 2 tasks found
         */
        String command = "   " + FindNameCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_DO + "   ";
        Model expectedModel = getModel();
        try {
            expectedModel.loginUser(ADMIN_USER);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (CurrentlyLoggedInException e) {
            e.printStackTrace();
        }
        ModelHelper.setFilteredList(expectedModel, HOMEWORK, PROJECT); // first names of HOMEWORK and PROJECT are "Do"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where task list is displaying the tasks we are finding
         * -> 2 tasks found
         */
        command = FindNameCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_DO;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find task where task list is not displaying the task we are finding -> 1 task found */
        command = FindNameCommand.COMMAND_WORD + " Spring";
        ModelHelper.setFilteredList(expectedModel, SPRINGCLEAN);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple tasks in organizer, 2 keywords -> 2 tasks found */
        command = FindNameCommand.COMMAND_WORD + " Spring Prepare";
        ModelHelper.setFilteredList(expectedModel, SPRINGCLEAN, PREPAREBREAKFAST);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple tasks in organizer, 2 keywords in reversed order -> 2 tasks found */
        command = FindNameCommand.COMMAND_WORD + " Prepare Spring";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple tasks in organizer, 2 keywords with 1 repeat -> 2 tasks found */
        command = FindNameCommand.COMMAND_WORD + " Prepare Spring Prepare";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple tasks in organizer, 2 matching keywords and 1 non-matching keyword
         * -> 2 tasks found
         */
        command = FindNameCommand.COMMAND_WORD + " Prepare Spring NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same tasks in organizer after deleting 1 of them -> 1 task found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assertFalse(getModel().getOrganizer().getTaskList().contains(PREPAREBREAKFAST));
        command = FindNameCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SPRING;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, SPRINGCLEAN);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find task in organizer, keyword is same as name but of different case -> 1 task found */
        command = FindNameCommand.COMMAND_WORD + " Spring";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find task in organizer, keyword is substring of name -> 0 tasks found */
        command = FindNameCommand.COMMAND_WORD + " Mei";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find task in organizer, name is substring of keyword -> 0 tasks found */
        command = FindNameCommand.COMMAND_WORD + " Springs";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find task not in organizer -> 0 tasks found */
        command = FindNameCommand.COMMAND_WORD + " Swim";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find priority number of task in organizer -> 0 tasks found */
        command = FindNameCommand.COMMAND_WORD + " " + HOMEWORK.getPriority().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find organizer of task in organizer -> 0 tasks found */
        command = FindNameCommand.COMMAND_WORD + " " + HOMEWORK.getDescription().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of task in organizer book -> 0 persons found */
        command = FindNameCommand.COMMAND_WORD + " " + HOMEWORK.getDeadline().toString();
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of task in organizer -> 0 tasks found */
        List<Tag> tags = new ArrayList<>(HOMEWORK.getTags());
        command = FindNameCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a task is selected -> selected card deselected */
        showAllTasks();
        selectTask(Index.fromOneBased(1));
        assertFalse(getTaskListPanel().getHandleToSelectedCard().getName().equals(HOMEWORK.getName().fullName));
        command = FindNameCommand.COMMAND_WORD + " Homework";
        ModelHelper.setFilteredList(expectedModel, HOMEWORK);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find task in empty organizer -> 0 tasks found */
        deleteAllTasks();
        command = FindNameCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_SPRING;
        expectedModel = getModel();
        try {
            expectedModel.loginUser(ADMIN_USER);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (CurrentlyLoggedInException e) {
            e.printStackTrace();
        }
        ModelHelper.setFilteredList(expectedModel, HOMEWORK);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNd Meier";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_TASKS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     *
     * @see OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_TASKS_LISTED_OVERVIEW, expectedModel.getFilteredTaskList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code OrganizerSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
