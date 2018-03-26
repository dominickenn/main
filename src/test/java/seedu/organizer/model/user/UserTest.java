package seedu.organizer.model.user;

import org.junit.Test;

import seedu.organizer.testutil.Assert;

//@@author dominickenn
public class UserTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new User(null, null));
    }

    @Test
    public void constructor_invalidUserName_throwsIllegalArgumentException() {
        String invalidUserName = "b@b";
        String validPassword = "bob";
        Assert.assertThrows(IllegalArgumentException.class, () -> new User(invalidUserName, validPassword));
    }

    @Test
    public void constructor_invalidPassword_throwsIllegalArgumentException() {
        String validUserName = "bob";
        String invalidPassword = "b@b";
        Assert.assertThrows(IllegalArgumentException.class, () -> new User(validUserName, invalidPassword));
    }

    @Test
    public void isValidUsername() {
        // null username
        Assert.assertThrows(NullPointerException.class, () -> User.isValidUsername(null));
    }

    @Test
    public void isValidPassword() {
        // null password
        Assert.assertThrows(NullPointerException.class, () -> User.isValidPassword(null));
    }
}
