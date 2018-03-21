package seedu.organizer.model.task;

import static seedu.organizer.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.subtask.UniqueSubtaskList;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.tag.UniqueTagList;
import seedu.organizer.model.user.User;

/**
 * Represents a Task in the organizer book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    private final Name name;
    private final Priority priority;
    private final Deadline deadline;
    private final DateAdded dateAdded;
    private final Description description;
    private final Status status;
    private final User user;

    private final UniqueTagList tags;
    private final UniqueSubtaskList subtasks;

    /**
     * Every field must be present and not null except status
     */
    public Task(Name name, Priority priority, Deadline deadline, Description description, Set<Tag> tags, User user) {
        requireAllNonNull(name, priority, deadline, description, tags, user);
        this.name = name;
        this.priority = priority;
        this.deadline = deadline;
        this.dateAdded = new DateAdded();
        this.description = description;
        this.status = null;
        this.user = user;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.subtasks = new UniqueSubtaskList();
    }

    /**
     * Every field must be present and not null except status
     */
    public Task(Name name, Priority priority, Deadline deadline, DateAdded dateAdded,
                Description description, Set<Tag> tags, User user) {
        requireAllNonNull(name, priority, deadline, description, tags, user);
        this.name = name;
        this.priority = priority;
        this.deadline = deadline;
        this.dateAdded = dateAdded;
        this.description = description;
        this.status = null;
        this.user = user;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.subtasks = new UniqueSubtaskList();
    }

    /**
     * Another constructor with custom status and subtask
     */
    public Task(Name name, Priority priority, Deadline deadline, DateAdded dateAdded,
                Description description, Status status, Set<Tag> tags, List<Subtask> subtasks, User user) {
        requireAllNonNull(name, priority, deadline, description, tags, user);
        this.name = name;
        this.priority = priority;
        this.deadline = deadline;
        this.dateAdded = dateAdded;
        this.description = description;
        this.status = status;
        this.user = user;
        // protect internal tags from changes in the arg list
        this.tags = new UniqueTagList(tags);
        this.subtasks = new UniqueSubtaskList(subtasks);
    }

    public Name getName() {
        return name;
    }

    public Priority getPriority() {
        return priority;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public DateAdded getDateAdded() {
        return dateAdded;
    }

    public Description getDescription() {
        return description;
    }

    public Status getStatus() {
        if (status == null) {
            return new Status(false);
        }
        return status;
    }

    public User getUser() {
        return user;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags.toSet());
    }

    public List<Subtask> getSubtasks() {
        return Collections.unmodifiableList(subtasks.toList());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getName().equals(this.getName())
                && otherTask.getPriority().equals(this.getPriority())
                && otherTask.getDeadline().equals(this.getDeadline())
                && otherTask.getDescription().equals(this.getDescription())
                && otherTask.getUser().equals(this.getUser());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, priority, deadline, description, tags, status, user);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Priority: ")
                .append(getPriority())
                .append(" Deadline: ")
                .append(getDeadline())
                .append(" Status: ")
                .append(getStatus())
                .append(" Description: ")
                .append(getDescription())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" User: ")
                .append(getUser());
        return builder.toString();
    }

    //@@author dominickenn
    /**
     * @return a Task comparator based on priority
     */
    public static Comparator<Task> priorityComparator() {
        return new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                return (task2.getPriority().value)
                        .compareTo(task1.getPriority().value);
            }
        };
    }
}
