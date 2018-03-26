package seedu.organizer.storage;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Test;

import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.model.user.User;
import seedu.organizer.testutil.Assert;

//@@author dominickenn
public class XmlAdaptedUserTest {
    public static final String USERNAME = "billy";
    public static final String PASSWORD = "b1lly";

    public static final String OTHER_USERNAME = "billy";
    public static final String OTHER_PASSWORD = "m1lly";

    @Test
    public void equal_defaultconstructor() {
        XmlAdaptedUser user = new XmlAdaptedUser(USERNAME, PASSWORD);
        XmlAdaptedUser otherUser = new XmlAdaptedUser(USERNAME, PASSWORD);
        assertEquals(user, otherUser);

        XmlAdaptedUser diffUser = new XmlAdaptedUser(OTHER_USERNAME, OTHER_PASSWORD);
        assertNotEquals(user, diffUser);
    }

    @Test
    public void toModel_invalidUserName() {
        Assert.assertThrows(
                IllegalValueException.class, () -> new XmlAdaptedUser("@", "a").toModelType()
        );
    }

    @Test
    public void toModel_invalidPassword() {
        Assert.assertThrows(
                IllegalValueException.class, () -> new XmlAdaptedUser("a", "@").toModelType()
        );
    }
}
