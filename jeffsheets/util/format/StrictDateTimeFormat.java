package jeffsheets.util.format;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Strictly check the string when parsing before conversion to a date Based
 * heavily on spring DateTimeFormat
 * 
 * @see org.springframework.format.annotation.DateTimeFormat
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface StrictDateTimeFormat {

	public static final String REGEX_DEFAULT = "^(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/\\d\\d\\d\\d$";

	public static final String PATTERN_DEFAULT = "MM/dd/yyyy";

	/**
	 * regex to strictly check the date string when parsing
	 * <p>
	 * Default enforces four digit year but allows single digit months
	 * 
	 * @return
	 */
	String regex() default REGEX_DEFAULT;

	/**
	 * Default is MM/dd/yyyy
	 * 
	 * @see org.springframework.format.annotation.DateTimeFormat
	 */
	String pattern() default PATTERN_DEFAULT;

}
