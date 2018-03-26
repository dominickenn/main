package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.commands.CommandTestUtil.DEADLINE_DESC_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.DEADLINE_DESC_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.DESCRIPTION_DESC_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.DESCRIPTION_DESC_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_PASSWORD_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.INVALID_USERNAME_DESC;
import static seedu.organizer.logic.commands.CommandTestUtil.NAME_DESC_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.NAME_DESC_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.PASSWORD_DESC_ADMIN;
import static seedu.organizer.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.organizer.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.organizer.logic.commands.CommandTestUtil.PRIORITY_DESC_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.PRIORITY_DESC_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.organizer.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.organizer.logic.commands.CommandTestUtil.USERNAME_DESC_ADMIN;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DEADLINE_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DEADLINE_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DESCRIPTION_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_DESCRIPTION_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_NAME_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_NAME_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_PASSWORD_ADMIN;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_PRIORITY_EXAM;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_PRIORITY_STUDY;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.organizer.logic.commands.CommandTestUtil.VALID_USERNAME_ADMIN;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.organizer.logic.commands.AddCommand;
import seedu.organizer.logic.commands.CreateUserCommand;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Deadline;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.user.User;
import seedu.organizer.testutil.TaskBuilder;

//@@author dominickenn
public class CreateUserCommandParserTest {
    private CreateUserCommandParser parser = new CreateUserCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        User expectedUser = new User(VALID_USERNAME_ADMIN, VALID_PASSWORD_ADMIN);
        assertParseSuccess(parser, USERNAME_DESC_ADMIN + PASSWORD_DESC_ADMIN,
                new CreateUserCommand(expectedUser));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateUserCommand.MESSAGE_USAGE);

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
