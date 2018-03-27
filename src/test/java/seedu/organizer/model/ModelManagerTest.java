package seedu.organizer.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_UNUSED;
import static seedu.organizer.model.Model.PREDICATE_SHOW_ALL_TASKS;
import static seedu.organizer.testutil.TypicalTasks.ADMIN;
import static seedu.organizer.testutil.TypicalTasks.EXAM;
import static seedu.organizer.testutil.TypicalTasks.GROCERY;
import static seedu.organizer.testutil.TypicalTasks.SPRINGCLEAN;
import static seedu.organizer.testutil.TypicalTasks.STUDY;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.NameContainsKeywordsPredicate;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.user.exceptions.DuplicateUserException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;
import seedu.organizer.testutil.OrganizerBuilder;
import seedu.organizer.testutil.TaskBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getCurrentUser_intializeSignupLoginUser_currentUserChanged() throws Exception {
        ModelManager modelManager = new ModelManager();
        assertEquals(modelManager.getCurrentUser(), null);
        modelManager.addUser(ADMIN);
        modelManager.loginUser(ADMIN);
        assertEquals(modelManager.getCurrentUser(), ADMIN);
    }

    @Test
    public void getFilteredTaskList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredTaskList().remove(0);
    }

    @Test
    public void deleteTag_nonExistentTag_modelUnchanged() throws Exception {
        Organizer organizer = new OrganizerBuilder().withTask(EXAM).withTask(STUDY).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(organizer, userPrefs);
        modelManager.deleteTag(new Tag(VALID_TAG_UNUSED));

        assertEquals(new ModelManager(organizer, userPrefs), modelManager);
    }

    @Test
    public void deleteTag_tagUsedByMultipleTasks_tagRemoved() throws Exception {
        Organizer organizer = new OrganizerBuilder().withTask(EXAM).withTask(STUDY).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(organizer, userPrefs);
        modelManager.deleteTag(new Tag(VALID_TAG_FRIEND));

        Task examWithoutFriendTag = new TaskBuilder(EXAM).withTags().build();
        Task studyWithoutFriendTag = new TaskBuilder(STUDY).withTags(VALID_TAG_HUSBAND).build();
        Organizer expectedOrganizer = new OrganizerBuilder().withTask(examWithoutFriendTag)
                .withTask(studyWithoutFriendTag).build();

        assertEquals(new ModelManager(expectedOrganizer, userPrefs), modelManager);
    }

    @Test
    public void equals() throws DuplicateUserException, UserNotFoundException {
        Organizer organizer = new OrganizerBuilder().withTask(GROCERY).withTask(SPRINGCLEAN).build();
        Organizer differentOrganizer = new Organizer();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(organizer, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(organizer, userPrefs);
        modelManager.addUser(ADMIN);
        modelManagerCopy.addUser(ADMIN);
        modelManager.loginUser(ADMIN);
        modelManagerCopy.loginUser(ADMIN);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different organizer -> returns false
        ModelManager differentModelManager = getDifferentModelManager(differentOrganizer, userPrefs);
        assertFalse(modelManager.equals(differentModelManager));

        // different filteredList -> returns false
        String[] keywords = GROCERY.getName().fullName.split("\\s+");
        modelManager.updateFilteredTaskListWithCurrentUser(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        differentModelManager = getDifferentModelManager(organizer, userPrefs);
        assertFalse(modelManager.equals(differentModelManager));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredTaskListWithCurrentUser(PREDICATE_SHOW_ALL_TASKS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setOrganizerName("differentName");
        differentModelManager = getDifferentModelManager(organizer, differentUserPrefs);
        assertTrue(modelManager.equals(differentModelManager));
    }

    private ModelManager getDifferentModelManager(Organizer differentOrganizer, UserPrefs userPrefs) throws DuplicateUserException, UserNotFoundException {
        ModelManager differentModelManager = new ModelManager(differentOrganizer, userPrefs);
        differentModelManager.addUser(ADMIN);
        differentModelManager.loginUser(ADMIN);
        return differentModelManager;
    }
}
