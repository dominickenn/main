package seedu.organizer.model;

import javafx.collections.ObservableList;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.user.User;

/**
 * Unmodifiable view of an organizer book
 */
public interface ReadOnlyOrganizer {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Task> getTaskList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

    /**
     * Returns an unmodifiable view of the users list.
     * This list will not contain any duplicate users.
     */
    ObservableList<User> getUserList();

}
