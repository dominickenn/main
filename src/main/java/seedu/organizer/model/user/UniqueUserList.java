package seedu.organizer.model.user;

import static java.util.Objects.requireNonNull;
import static seedu.organizer.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.organizer.commons.exceptions.DuplicateDataException;
import seedu.organizer.commons.util.CollectionUtil;

//@@author dominickenn
/**
 * A list of users that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see User#equals(Object)
 */
public class UniqueUserList implements Iterable<User> {

    private final ObservableList<User> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty UserList.
     */
    public UniqueUserList() {
    }

    /**
     * Creates a UniqueUserList using given tags.
     * Enforces no nulls.
     */
    public UniqueUserList(Set<User> users) {
        requireAllNonNull(users);
        internalList.addAll(users);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all users in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<User> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the users in this list with those in the argument user list.
     */
    public void setUsers(Set<User> users) {
        requireAllNonNull(users);
        internalList.setAll(users);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every user in the argument list exists in this object.
     */
    public void mergeFrom(UniqueUserList from) {
        final Set<User> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(user -> !alreadyInside.contains(user))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent User as the given argument.
     */
    public boolean contains(User toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a User to the list.
     *
     * @throws DuplicateUserException if the User to add is a duplicate of an existing User in the list.
     */
    public void add(User toAdd) throws DuplicateUserException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateUserException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<User> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<User> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueUserList // instanceof handles nulls
                && this.internalList.equals(((UniqueUserList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueUserList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateUserException extends DuplicateDataException {
        protected DuplicateUserException() {
            super("Operation would result in duplicate tags");
        }
    }

}
