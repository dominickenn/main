package seedu.organizer.ui.testutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.TaskCardHandle;
import guitests.guihandles.TaskListPanelHandle;
import seedu.organizer.model.task.Task;
import seedu.organizer.ui.TaskCard;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    private static final String LABEL_DEFAULT_STYLE = "label";

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(TaskCardHandle expectedCard, TaskCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getDescription(), actualCard.getDescription());
        assertEquals(expectedCard.getDeadline(), actualCard.getDeadline());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPriority(), actualCard.getPriority());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
        expectedCard.getTags().forEach(tag -> assertEquals(expectedCard.getTagStyleClasses(tag),
            actualCard.getTagStyleClasses(tag)));
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedTask}.
     */
    public static void assertCardDisplaysTask(Task expectedTask, TaskCardHandle actualCard) {
        assertEquals(expectedTask.getName().fullName, actualCard.getName());
        assertEquals("[" + expectedTask.getStatus().toString() + "]", actualCard.getStatus());
        assertEquals("Priority : " + expectedTask.getPriority().value, actualCard.getPriority());
        assertEquals("Deadline : " + expectedTask.getDeadline().toString(), actualCard.getDeadline());
        assertEquals("Date Added : " + expectedTask.getDateAdded().toString(), actualCard.getDateAdded());
        assertEquals("Description : " + expectedTask.getDescription().value, actualCard.getDescription());
        assertTagsEqual(expectedTask, actualCard);
    }

    /**
     * Asserts that the list in {@code taskListPanelHandle} displays the details of {@code tasks} correctly and
     * in the correct order.
     */
    public static void assertListMatching(TaskListPanelHandle taskListPanelHandle, Task... tasks) {
        for (int i = 0; i < tasks.length; i++) {
            assertCardDisplaysTask(tasks[i], taskListPanelHandle.getTaskCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code taskListPanelHandle} displays the details of {@code tasks} correctly and
     * in the correct order.
     */
    public static void assertListMatching(TaskListPanelHandle taskListPanelHandle, List<Task> tasks) {
        assertListMatching(taskListPanelHandle, tasks.toArray(new Task[0]));
    }

    /**
     * Asserts the size of the list in {@code taskListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(TaskListPanelHandle taskListPanelHandle, int size) {
        int numberOfPeople = taskListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }

    // @@author guekling-reused
    // Reused from https://github.com/se-edu/organizer-level4/pull/798/files with minor modifications
    /**
     * Returns the color style for {@code tagName}'s label. The tag's color is determined by looking up the color
     * in {@code TaskCard#TAG_COLOR_STYLES}, using an index generated by the hash code of the tag's content.
     *
     * @see TaskCard#getTagColorStyleFor(String)
     */
    private static String getTagColorStyleFor(String tagName) {
        switch (tagName) {
        case "classmates":
        case "owesMoney":
            return "blue";

        case "colleagues":
        case "neighbours":
            return "gray";

        case "family":
        case "friend":
            return "maroon";

        case "friends":
            return "orange";

        case "husband":
            return "yellow";

        default:
            fail(tagName + " does not have a color assigned.");
            return "";
        }
    }

    /**
     * Asserts that the tags in {@code actualCard} matches all the tags in {@code expectedTask} with the correct
     * color.
     */
    private static void assertTagsEqual(Task expectedTask, TaskCardHandle actualCard) {
        List<String> expectedTags = expectedTask.getTags().stream().map(tag -> tag.tagName)
            .collect(Collectors.toList());
        assertEquals(expectedTags, actualCard.getTags());
        expectedTags.forEach(tag -> assertEquals(Arrays.asList(LABEL_DEFAULT_STYLE, getTagColorStyleFor(tag)),
            actualCard.getTagStyleClasses(tag)));
    }
}
