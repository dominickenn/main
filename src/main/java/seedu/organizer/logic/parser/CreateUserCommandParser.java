package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.Set;
import java.util.stream.Stream;

import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.logic.commands.AddCommand;
import seedu.organizer.logic.commands.CreateUserCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;
import seedu.organizer.model.ModelManager;
import seedu.organizer.model.tag.Tag;
import seedu.organizer.model.task.Deadline;
import seedu.organizer.model.task.Description;
import seedu.organizer.model.task.Name;
import seedu.organizer.model.task.Priority;
import seedu.organizer.model.task.Task;
import seedu.organizer.model.user.User;

//@@author dominickenn
/**
 * Parses input arguments and creates a new CreateUserCommand object
 */
public class CreateUserCommandParser implements Parser<CreateUserCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateUserCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateUserCommand.MESSAGE_USAGE));
        }

        try {
            User user = ParserUtil.parseUser(argMultimap.getValue(PREFIX_USERNAME),
                    argMultimap.getValue(PREFIX_PASSWORD)).get();
            return new CreateUserCommand(user);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
