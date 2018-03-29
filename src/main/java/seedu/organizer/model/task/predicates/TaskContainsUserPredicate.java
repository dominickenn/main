package seedu.organizer.model.task.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.organizer.commons.util.StringUtil;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.user.User;

//@@author dominickenn
/**
 * Tests that a {@code Task}'s {@code User} matches the user
 */
public class TaskContainsUserPredicate implements Predicate<Task> {
    private final User user;

    public TaskContainsUserPredicate(User user) {
        this.user = user;
    }

    @Override
    public boolean test(Task task) {
        return user.equals(task.getUser());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskContainsUserPredicate // instanceof handles nulls
                && this.user.equals(((TaskContainsUserPredicate) other).user)); // state check
    }
}
