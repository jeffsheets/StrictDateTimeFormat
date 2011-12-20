package jeffsheets.util.format;

import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Pattern;

import org.springframework.format.Parser;

/**
 * The parse method first strictly checks the string for a regex Wraps a Spring
 * Parser
 * 
 * @see org.springframework.format.Parser
 */
public class RegexParserDecorator<T> implements Parser<T> {

	private final Parser<T> parser;

	private final Pattern regexPattern;

	/**
	 * Create a new StrictDateTimeParser.
	 * 
	 * @param dateTimeFormatter
	 */
	public RegexParserDecorator(Parser<T> parser, String regex) {
		this.parser = parser;
		this.regexPattern = Pattern.compile(regex);
	}

	/**
	 * Checks the regex and then parses the date
	 */
	public T parse(String text, Locale locale) throws ParseException {
		if (!regexPattern.matcher(text).matches()) {
			throw new IllegalArgumentException("Text does not match regex: " + text);
		}
		return parser.parse(text, locale);
	}

}
