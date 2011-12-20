package jeffsheets.util.format;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.format.datetime.joda.DateTimeParser;
import org.springframework.format.datetime.joda.MillisecondInstantPrinter;
import org.springframework.format.datetime.joda.ReadableInstantPrinter;
import org.springframework.format.datetime.joda.ReadablePartialPrinter;

/**
 * Return a wrapped parser that uses regex to strictly bound the string that is
 * parsed
 * <p>
 * Based heavily on JodaDateTimeFormatAnnotationFormatterFactory
 * 
 * @see org.springframework.format.datetime.joda.JodaDateTimeFormatAnnotationFormatterFactory
 */
public class StrictDateTimeFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<StrictDateTimeFormat> {
	private final Set<Class<?>> fieldTypes;

	public StrictDateTimeFormatAnnotationFormatterFactory() {
		Set<Class<?>> rawFieldTypes = new HashSet<Class<?>>(8);
		rawFieldTypes.add(LocalDate.class);
		rawFieldTypes.add(LocalTime.class);
		rawFieldTypes.add(LocalDateTime.class);
		rawFieldTypes.add(DateTime.class);
		rawFieldTypes.add(DateMidnight.class);
		rawFieldTypes.add(Date.class);
		rawFieldTypes.add(Calendar.class);
		rawFieldTypes.add(Long.class);
		this.fieldTypes = Collections.unmodifiableSet(rawFieldTypes);
	}

	public Set<Class<?>> getFieldTypes() {
		return this.fieldTypes;
	}

	public Parser<DateTime> getParser(StrictDateTimeFormat annotation, Class<?> fieldType) {
		DateTimeParser parser = new DateTimeParser(forPattern(annotation.pattern()));
		return new RegexParserDecorator<DateTime>(parser, annotation.regex());
	}

	public Printer<?> getPrinter(StrictDateTimeFormat annotation, Class<?> fieldType) {
		DateTimeFormatter formatter = forPattern(annotation.pattern());
		if (ReadableInstant.class.isAssignableFrom(fieldType)) {
			return new ReadableInstantPrinter(formatter);
		} else if (ReadablePartial.class.isAssignableFrom(fieldType)) {
			return new ReadablePartialPrinter(formatter);
		} else if (Calendar.class.isAssignableFrom(fieldType)) {
			// assumes Calendar->ReadableInstant converter is registered
			return new ReadableInstantPrinter(formatter);
		} else {
			// assumes Date->Long converter is registered
			return new MillisecondInstantPrinter(formatter);
		}
	}

	private DateTimeFormatter forPattern(String pattern) {
		return org.joda.time.format.DateTimeFormat.forPattern(pattern);
	}

}
