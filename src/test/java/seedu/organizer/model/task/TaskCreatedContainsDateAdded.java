package seedu.organizer.model.task;

import static junit.framework.TestCase.assertNotNull;

import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DEADLINE_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DESCRIPTION_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_NAME_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_PASSWORD_ADMIN;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_PRIORITY_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_USERNAME_ADMIN;

import java.util.HashSet;

import org.junit.Test;

import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.user.User;

//@@author dominickenn
/**\
 * Tests whether a DateAdded is automatically created upon Task creation
 */
public class TaskCreatedContainsDateAdded {

    @Test
    public void createTaskContainsDateAdded() {
        Task task = new Task(new Name(VALID_NAME_EXAM), new Priority(VALID_PRIORITY_EXAM),
                new Deadline(VALID_DEADLINE_EXAM), new Description(VALID_DESCRIPTION_EXAM),
                new HashSet<Tag>(), new User(VALID_USERNAME_ADMIN, VALID_PASSWORD_ADMIN));
        assertNotNull(task.getDateAdded());
    }

}
