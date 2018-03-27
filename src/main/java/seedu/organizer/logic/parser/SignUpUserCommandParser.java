package seedu.organizer.logic.parser;

import static seedu.organizer.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.organizer.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.util.stream.Stream;

import seedu.organizer.commons.exceptions.IllegalValueException;
import seedu.organizer.logic.commands.SignUpUserCommand;
import seedu.organizer.logic.parser.exceptions.ParseException;
import seedu.organizer.model.user.User;

//@@author dominickenn
/**
 * Parses input arguments and creates a new SignUpUserCommand object
 */
public class SignUpUserCommandParser implements Parser<SignUpUserCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SignUpUserCommand
     * and returns an SignUpUserCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SignUpUserCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_USERNAME, PREFIX_PASSWORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SignUpUserCommand.MESSAGE_USAGE));
        }

        try {
            User user = ParserUtil.parseUser(argMultimap.getValue(PREFIX_USERNAME),
                    argMultimap.getValue(PREFIX_PASSWORD)).get();
            return new SignUpUserCommand(user);
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
