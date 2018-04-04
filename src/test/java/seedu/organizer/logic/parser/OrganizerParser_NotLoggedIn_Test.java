package seedu.organizer.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.organizer.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.organizer.testutil.TypicalTasks.ADMIN_USER;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.organizer.logic.commands.AddCommand;
import seedu.organizer.logic.commands.AddSubtaskCommand;
import seedu.organizer.logic.commands.AnswerCommand;
import seedu.organizer.logic.commands.ClearCommand;
import seedu.organizer.logic.commands.CurrentMonthCommand;
import seedu.organizer.logic.commands.DeleteCommand;
import seedu.organizer.logic.commands.DeleteSubtaskCommand;
import seedu.organizer.logic.commands.EditCommand;
import seedu.organizer.logic.commands.ExitCommand;
import seedu.organizer.logic.commands.FindDeadlineCommand;
import seedu.organizer.logic.commands.FindDescriptionCommand;
import seedu.organizer.logic.commands.FindMultipleFieldsCommand;
import seedu.organizer.logic.commands.FindNameCommand;
import seedu.organizer.logic.commands.ForgotPasswordCommand;
import seedu.organizer.logic.commands.HelpCommand;
import seedu.organizer.logic.commands.HistoryCommand;
import seedu.organizer.logic.commands.ListCommand;
import seedu.organizer.logic.commands.LoginCommand;
import seedu.organizer.logic.commands.NextMonthCommand;
import seedu.organizer.logic.commands.PreviousMonthCommand;
import seedu.organizer.logic.commands.RedoCommand;
import seedu.organizer.logic.commands.SelectCommand;
import seedu.organizer.logic.commands.SignUpCommand;
import seedu.organizer.logic.commands.ToggleCommand;
import seedu.organizer.logic.commands.ToggleSubtaskCommand;
import seedu.organizer.logic.commands.UndoCommand;
import seedu.organizer.logic.commands.util.EditTaskDescriptor;
import seedu.organizer.logic.parser.exceptions.ParseException;
import seedu.organizer.model.Model;
import seedu.organizer.model.ModelManager;
import seedu.organizer.model.subtask.Subtask;
import seedu.organizer.model.task.DeadlineContainsKeywordsPredicate;
import seedu.organizer.model.task.DescriptionContainsKeywordsPredicate;
import seedu.organizer.model.task.MultipleFieldsContainsKeywordsPredicate;
import seedu.organizer.model.task.NameContainsKeywordsPredicate;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.user.User;
import seedu.organizer.testutil.EditTaskDescriptorBuilder;
import seedu.organizer.testutil.TaskBuilder;
import seedu.organizer.testutil.TaskUtil;

//@@author dominickenn
public class OrganizerParser_NotLoggedIn_Test {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final OrganizerParser parser = new OrganizerParser();

    private Model model = new ModelManager();
    private User user = ADMIN_USER;
    private String username = "admin";

    @Test
    public void parseCommand_signUp() throws Exception {
        SignUpCommand command = (SignUpCommand) parser.parseCommand(
                SignUpCommand.COMMAND_WORD + " "
                        + PREFIX_USERNAME + "admin "
                        + PREFIX_PASSWORD + "admin");
        assertEquals(new SignUpCommand(user), command);

        SignUpCommand commandAlias = (SignUpCommand) parser.parseCommand(
                SignUpCommand.COMMAND_ALIAS + " "
                        + PREFIX_USERNAME + "admin "
                        + PREFIX_PASSWORD + "admin");
        assertEquals(new SignUpCommand(user), commandAlias);
    }

    @Test
    public void parseCommand_login() throws Exception {
        LoginCommand command = (LoginCommand) parser.parseCommand(
                LoginCommand.COMMAND_WORD + " "
                        + PREFIX_USERNAME + "admin "
                        + PREFIX_PASSWORD + "admin");
        assertEquals(new LoginCommand(user), command);

        LoginCommand commandAlias = (LoginCommand) parser.parseCommand(
                LoginCommand.COMMAND_ALIAS + " "
                        + PREFIX_USERNAME + "admin "
                        + PREFIX_PASSWORD + "admin");
        assertEquals(new LoginCommand(user), commandAlias);
    }

    @Test
    public void parseCommand_forgotPassword() throws Exception {
        ForgotPasswordCommand command = (ForgotPasswordCommand) parser.parseCommand(
                ForgotPasswordCommand.COMMAND_WORD + " "
                        + PREFIX_USERNAME + username);
        assertEquals(new ForgotPasswordCommand(username), command);

        ForgotPasswordCommand commandAlias = (ForgotPasswordCommand) parser.parseCommand(
                ForgotPasswordCommand.COMMAND_ALIAS + " "
                        + PREFIX_USERNAME + username);
        assertEquals(new ForgotPasswordCommand(username), commandAlias);
    }

    @Test
    public void parseCommand_answer() throws Exception {
        String answer = "answer";
        AnswerCommand command = (AnswerCommand) parser.parseCommand(
                AnswerCommand.COMMAND_WORD + " "
                        + PREFIX_USERNAME + username + " "
                        + PREFIX_ANSWER + answer);
        assertEquals(new AnswerCommand(username, answer), command);

        AnswerCommand commandAlias = (AnswerCommand) parser.parseCommand(
                AnswerCommand.COMMAND_ALIAS + " "
                        + PREFIX_USERNAME + username + " "
                        + PREFIX_ANSWER + answer);
        assertEquals(new AnswerCommand(username, answer), commandAlias);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }
}
