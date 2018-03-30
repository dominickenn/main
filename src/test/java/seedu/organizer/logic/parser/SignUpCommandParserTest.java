package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.organizer.logic.commands.SignUpCommand;
import seedu.organizer.model.user.User;

//@@author dominickenn
public class SignUpCommandParserTest {
    private SignUpCommandParser parser = new SignUpCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        User expectedUser = new User("expectedUser", "expectedUser");

        // whitespace only preamble
        assertParseSuccess(parser,PREAMBLE_WHITESPACE
                            + " u/expectedUser "
                            + "p/expectedUser "
                            , new SignUpCommand(expectedUser));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SignUpCommand.MESSAGE_USAGE);

        // missing username prefix
        assertParseFailure(parser, SignUpCommand.COMMAND_WORD + " user p/user", expectedMessage);

        // missing password prefix
        assertParseFailure(parser, SignUpCommand.COMMAND_WORD + " u/user user", expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, SignUpCommand.COMMAND_WORD + " user user", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid username
        assertParseFailure(parser, " u/!@# p/valid", User.MESSAGE_USER_CONSTRAINTS);

        // invalid password
        assertParseFailure(parser, " u/valid p/!@#", User.MESSAGE_USER_CONSTRAINTS);
    }
}
