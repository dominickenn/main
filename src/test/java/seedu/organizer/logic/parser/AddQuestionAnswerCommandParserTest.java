package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.organizer.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.organizer.model.user.UserWithQuestionAnswer.MESSAGE_QUESTION_ANSWER_CONSTRAINTS;
import static seedu.organizer.testutil.TypicalTasks.ADMIN_USER;

import org.junit.Before;
import org.junit.Test;

import seedu.organizer.logic.commands.AddQuestionAnswerCommand;
import seedu.organizer.model.Model;
import seedu.organizer.model.ModelManager;
import seedu.organizer.model.user.exceptions.CurrentlyLoggedInException;
import seedu.organizer.model.user.exceptions.DuplicateUserException;
import seedu.organizer.model.user.exceptions.UserNotFoundException;

//author dominickenn
public class AddQuestionAnswerCommandParserTest {

    private Model model = new ModelManager();

    private String VALID_QUESTION = PREFIX_QUESTION + "valid question";
    private String VALID_ANSWER = PREFIX_ANSWER + "valid answer";
    private String INVALID_QUESTION = PREFIX_QUESTION + "";
    private String INVALID_ANSWER = PREFIX_ANSWER + "";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddQuestionAnswerCommand.MESSAGE_USAGE);

    private AddQuestionAnswerCommandParser parser = new AddQuestionAnswerCommandParser();

    @Before
    public void setUp() {
        try {
            model.addUser(ADMIN_USER);
            model.loginUser(ADMIN_USER);
        } catch (DuplicateUserException e) {
            e.printStackTrace();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (CurrentlyLoggedInException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void parse_missingParts_failure() {
        // no question
        assertParseFailure(parser,
                AddQuestionAnswerCommand.COMMAND_WORD
                        + " " + VALID_ANSWER,
                MESSAGE_INVALID_FORMAT);

        // no answer
        assertParseFailure(parser,
                AddQuestionAnswerCommand.COMMAND_WORD
                        + " " +VALID_QUESTION,
                MESSAGE_INVALID_FORMAT);

        // no question and answer
        assertParseFailure(parser,
                AddQuestionAnswerCommand.COMMAND_WORD + " ",
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser,
                AddQuestionAnswerCommand.COMMAND_WORD + " "
                + VALID_QUESTION + " " + INVALID_ANSWER,
                MESSAGE_QUESTION_ANSWER_CONSTRAINTS); // invalid answer
        assertParseFailure(parser,
                AddQuestionAnswerCommand.COMMAND_WORD + " "
                + INVALID_QUESTION + " " + VALID_ANSWER,
                MESSAGE_QUESTION_ANSWER_CONSTRAINTS); // invalid question
        assertParseFailure(parser,
                AddQuestionAnswerCommand.COMMAND_WORD + " "
                + INVALID_QUESTION + " " + VALID_ANSWER,
                MESSAGE_QUESTION_ANSWER_CONSTRAINTS); // invalid question and answer
    }

    @Test
    public void parse_allValuesValid_success() {
        assertParseSuccess(parser,
                AddQuestionAnswerCommand.COMMAND_WORD + " "
                + VALID_QUESTION + " " + VALID_ANSWER,
                new AddQuestionAnswerCommand("valid question", "valid answer"));
    }
}

