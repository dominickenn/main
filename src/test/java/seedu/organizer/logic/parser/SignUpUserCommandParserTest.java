package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_PASSWORD_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_USERNAME_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.PASSWORD_DESC_ADMIN;
import static seedu.organizer.logic.commands.CommandTestUtil.USERNAME_DESC_ADMIN;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_PASSWORD_ADMIN;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_USERNAME_ADMIN;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.organizer.logic.commands.SignUpUserCommand;
import seedu.organizer.model.user.User;

//@@author dominickenn
public class SignUpUserCommandParserTest {
    private SignUpUserCommandParser parser = new SignUpUserCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        User expectedUser = new User(VALID_USERNAME_ADMIN, VALID_PASSWORD_ADMIN);
        assertParseSuccess(parser, USERNAME_DESC_ADMIN + PASSWORD_DESC_ADMIN,
                new SignUpUserCommand(expectedUser));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SignUpUserCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, VALID_USERNAME_ADMIN + PASSWORD_DESC_ADMIN, expectedMessage);

        // missing password prefix
        assertParseFailure(parser, USERNAME_DESC_ADMIN + VALID_PASSWORD_ADMIN, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_USERNAME_ADMIN + VALID_PASSWORD_ADMIN, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid username
        assertParseFailure(parser, INVALID_USERNAME_DESC + PASSWORD_DESC_ADMIN, User.MESSAGE_USERNAME_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, USERNAME_DESC_ADMIN + INVALID_PASSWORD_DESC, User.MESSAGE_PASSWORD_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_USERNAME_DESC + INVALID_PASSWORD_DESC, User.MESSAGE_USERNAME_CONSTRAINTS);
    }
}
