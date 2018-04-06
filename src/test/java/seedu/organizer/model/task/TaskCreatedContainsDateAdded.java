package seedu.organizer.model.task;

import static junit.framework.TestCase.assertNotNull;

import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DEADLINE_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DESCRIPTION_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_NAME_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_PRIORITY_EXAM;

import java.util.HashSet;

import org.junit.Test;

import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Deadline;
import seedu.organizer.model.task.Description;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.model.task.Task;

//@@author dominickenn
/**\
 * Tests whether a DateAdded is automatically created upon Task creation
 */
public class TaskCreatedContainsDateAdded {

    @Test
    public void createTaskContainsDateAdded() {
        Task task = new Task(new Name(VALID_NAME_EXAM), new Priority(VALID_PRIORITY_EXAM),
                new Deadline(VALID_DEADLINE_EXAM), new Description(VALID_DESCRIPTION_EXAM),
                new HashSet<Tag>());
        assertNotNull(task.getDateAdded());
    }

}
