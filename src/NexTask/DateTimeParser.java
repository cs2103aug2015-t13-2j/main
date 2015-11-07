package NexTask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeParser {
	// 3 PARAM DATES
	private static final String DATE_FMT_3_1 = "dd MMMM yyyy"; // 14 November
																// 2015
	private static final String DATE_FMT_3_2 = "dd MMM yyyy"; // 14 Nov 2015

	// 2 PARAM DATES NO YEARS
	private static final String DATE_FMT_2_1 = "dd MMM"; // 14 Nov no year
	private static final String DATE_FMT_2_2 = "dd MMMM"; // 14 November no year

	// 1 PARAM DATES
	private static final String DATE_FMT_1_1 = "ddMMyyyy"; // 14112015
	private static final String DATE_FMT_1_2 = "dd/MM/yy"; // 14/11/15
	private static final String DATE_FMT_1_3 = "ddMMyy"; // 141115

	// 1 PARAM DATES NO YEARS
	private static final String DATE_FMT_1_4 = "dd/MM"; // 14/11 no year
	private static final String DATE_FMT_1_5 = "ddMM"; // 1411 no year

	// 1 PARAM TIMES
	private static final String TIME_FMT_1_1 = "hh:mma"; // 5:00am
	private static final String TIME_FMT_1_2 = "hhmma"; // 500am
	private static final String TIME_FMT_1_3 = "hha"; // 5am
	private static final String TIME_FMT_1_4 = "hh.mma"; // 5.00am
	private static final String TIME_FMT_1_5 = "kmm"; // 2100
	private static final String TIME_FMT_1_6 = "k:mm"; // 21:00
	private static final String TIME_FMT_1_7 = "k"; // 21
	private static final String TIME_FMT_1_8 = "k.mm"; // 21.55

	// 2 PARAM TIMES
	private static final String TIME_FMT_2_1 = "hh:mm a"; // 5:00 am
	private static final String TIME_FMT_2_2 = "hh.mm a"; // 5.00 am
	private static final String TIME_FMT_2_3 = "hhmm a"; // 500 am
	private static final String TIME_FMT_2_4 = "hh a"; // 5 am

	private static final String[] DATE_FMTS_1_PART = { DATE_FMT_1_1, DATE_FMT_1_2, DATE_FMT_1_3 };
	private static final String[] DATE_FMTS_1_PART_NO_YEAR = { DATE_FMT_1_4, DATE_FMT_1_5 };
	private static final String[] DATE_FMTS_2_PART_NO_YEAR = { DATE_FMT_2_1, DATE_FMT_2_2 };
	private static final String[] DATE_FMTS_3_PARTS = { DATE_FMT_3_1, DATE_FMT_3_2 };

	private static final String[] TIME_FMTS_1_PART = { TIME_FMT_1_1, TIME_FMT_1_2, TIME_FMT_1_3, TIME_FMT_1_4,
			TIME_FMT_1_5, TIME_FMT_1_6, TIME_FMT_1_7, TIME_FMT_1_8 };
	private static final String[] TIME_FMTS_2_PARTS = { TIME_FMT_2_1, TIME_FMT_2_2, TIME_FMT_2_3, TIME_FMT_2_4 };

	private static final String PATTERN_ORDINAL = "(\\d(rd|st|nd|th)+)";
	private static final String PATTERN_CARDINAL = "([0-9]+)";
	private static final String BAD_TIME_FMT1 = "(\\s\\d{3}\\s*(am|pm))";
	private static final String BAD_TIME_FMT2 = "(\\s\\d{3}\\b)";

	private static final String[] EMPTY_SET = {};
	private static final String EMPTY_STRING = "";

	private static DateTime current = new DateTime();

	/**
	 * Creates instance of DateTime from a string in an appropriate DateTime
	 * format.
	 * 
	 * @param input
	 * @return
	 */

	public static DateTime parse(String input) {
		DateTime dateTime;
		String reformattedInput = reformatDate(replaceBadTimeInput(input));
		switch (getNumDateTimeParam(reformatDate(replaceBadTimeInput(input)))) {
		case 1:
			dateTime = parseOneParamDateTime(reformattedInput);
			break;
		case 2:
			dateTime = parseTwoParamDateTime(reformattedInput);
			break;
		case 3:
			dateTime = parseThreeParamDateTime(reformattedInput);
			break;
		case 4:
			dateTime = parseFourParamDateTime(reformattedInput);
			break;
		case 5:
			dateTime = parseFiveParamDateTime(reformattedInput);
			break;
		default:
			dateTime = null;
		}
		return dateTime;
	}

	private static DateTime parseFiveParamDateTime(String input) {
		return parseDateTime(input, DATE_FMTS_3_PARTS, TIME_FMTS_2_PARTS, false);
	}

	private static DateTime parseFourParamDateTime(String input) {
		DateTime dateTime = parseDateTime(input, DATE_FMTS_3_PARTS, TIME_FMTS_1_PART, true);
		if (dateTime == null) {
			dateTime = parseDateTime(input, DATE_FMTS_2_PART_NO_YEAR, TIME_FMTS_2_PARTS, false);
		}
		return dateTime;
	}

	private static DateTime parseThreeParamDateTime(String input) {
		DateTime dateTime = parseDateTime(input, DATE_FMTS_3_PARTS, EMPTY_SET, true);
		if (dateTime == null) {
			dateTime = parseDateTime(input, DATE_FMTS_2_PART_NO_YEAR, TIME_FMTS_1_PART, false);
		}
		if (dateTime == null) {
			dateTime = parseDateTime(input, DATE_FMTS_1_PART, TIME_FMTS_2_PARTS, true);
		}
		if (dateTime == null) {
			dateTime = parseDateTime(input, DATE_FMTS_1_PART_NO_YEAR, TIME_FMTS_2_PARTS, false);
		}
		return dateTime;
	}

	private static DateTime parseTwoParamDateTime(String input) {
		DateTime dateTime = parseDateTime(input, DATE_FMTS_2_PART_NO_YEAR, EMPTY_SET, false);
		if (dateTime == null) {
			dateTime = parseDateTime(input, DATE_FMTS_1_PART, TIME_FMTS_1_PART, true);
		}
		if (dateTime == null) {
			dateTime = parseDateTime(input, DATE_FMTS_1_PART_NO_YEAR, TIME_FMTS_1_PART, false);
		}
		return dateTime;
	}

	private static DateTime parseOneParamDateTime(String input) {
		DateTime dateTime = parseDateTime(input, DATE_FMTS_1_PART, EMPTY_SET, true);
		if (dateTime == null) {
			dateTime = parseDateTime(input, DATE_FMTS_1_PART_NO_YEAR, EMPTY_SET, false);
		}
		return dateTime;
	}

	private static DateTime parseDateTimeWithYear(String input, String dateFmt, String timeFmt) {
		DateTime dateTime;
		try {
			DateTimeFormatter formatter = DateTimeFormat.forPattern(dateFmt + " " + timeFmt);
			dateTime = formatter.parseDateTime(input.trim());
		} catch (IllegalArgumentException e) {
			dateTime = null;
		}
		return dateTime;
	}

	private static DateTime parseDateWithNoTime(String input, String dateFmt) {
		DateTime dateTime;
		try {
			DateTimeFormatter formatter = DateTimeFormat.forPattern(dateFmt);
			dateTime = formatter.parseDateTime(input.trim());
		} catch (IllegalArgumentException e) {
			dateTime = null;
		}
		return dateTime;
	}

	private static DateTime parseDateTimeNoYear(String input, String dateFmt, String timeFmt) {
		DateTime dateTime;
		int currentYear = current.getYear();
		try {
			DateTimeFormatter formatter = DateTimeFormat.forPattern(dateFmt + " " + timeFmt);
			dateTime = formatter.parseDateTime(input.trim());
			dateTime = dateTime.plusYears(calculateYearDifference(currentYear, dateTime.getYear()));
		} catch (IllegalArgumentException e) {
			dateTime = null;
		}
		return dateTime;
	}

	private static int calculateYearDifference(int year1, int year2) {
		return year1 - year2;
	}

	private static DateTime parseDateNoTimeNoYear(String input, String dateFmt) {
		DateTime dateTime;
		int currentYear = current.getYear();
		try {
			DateTimeFormatter formatter = DateTimeFormat.forPattern(dateFmt);
			dateTime = formatter.parseDateTime(input.trim());
			dateTime = dateTime.plusYears(calculateYearDifference(currentYear, dateTime.getYear()));
		} catch (IllegalArgumentException e) {
			dateTime = null;
		}
		return dateTime;
	}

	private static DateTime parseDateTime(String input, String[] dates, String[] times, boolean hasYear) {
		DateTime dateTime = null;
		if (times.length > 0 && hasYear) {
			for (int i = 0; i < dates.length; i++) {
				for (int j = 0; j < times.length; j++) {
					if (dateTime == null) {
						dateTime = parseDateTimeWithYear(input, dates[i], times[j]);
					} else {
						break;
					}
				}
			}
		} else if (times.length > 0 && !hasYear) {
			for (int i = 0; i < dates.length; i++) {
				for (int j = 0; j < times.length; j++) {
					if (dateTime == null) {
						dateTime = parseDateTimeNoYear(input, dates[i], times[j]);
					} else {
						break;
					}
				}
			}
		} else if (times.length == 0 && hasYear) {
			for (int i = 0; i < dates.length; i++) {
				if (dateTime == null) {
					dateTime = parseDateWithNoTime(input, dates[i]);
				} else {
					break;
				}
			}
		} else if (times.length == 0 && !hasYear) {
			for (int i = 0; i < dates.length; i++) {
				if (dateTime == null) {
					dateTime = parseDateNoTimeNoYear(input, dates[i]);
				} else {
					break;
				}

			}
		}
		return dateTime;
	}

	/**
	 * Helper method to determine how many parts did the user input date in.
	 */
	private static int getNumDateTimeParam(String dateTimeString) {
		return dateTimeString.split(" ").length;
	}

	/**
	 * When receive a date has numbers represented in ordinal manner, i.e. 1st,
	 * 2nd... this method replaces that with 1, 2 ... so it can be parsed.
	 * 
	 * @param date
	 * @return
	 */
	private static String reformatDate(String date) {
		String ordinal = getOrdinal(date);
		String cardinal = getCardinal(ordinal);
		return date.replaceAll(ordinal, cardinal);
	}

	/**
	 * Retrieve the substring of user input that is in ordinal format.
	 * 
	 * @return
	 */
	private static String getOrdinal(String input) {
		Pattern dateTimePattern = Pattern.compile(PATTERN_ORDINAL);
		Matcher m = dateTimePattern.matcher(input);
		if (m.find()) {
			return m.group(1);
		} else {
			return "";
		}
	}

	/**
	 * Retrieve the cardinal version of ordinal number
	 * 
	 * @return
	 */
	private static String getCardinal(String input) {
		Pattern dateTimePattern = Pattern.compile(PATTERN_CARDINAL);
		Matcher m = dateTimePattern.matcher(input);
		if (m.find()) {
			return m.group(1);
		} else {
			return "";
		}
	}

	private static String getBadTimeInput(String input) {
		Pattern dateTimePattern1 = Pattern.compile(BAD_TIME_FMT1);
		Matcher m1 = dateTimePattern1.matcher(input);
		Pattern dateTimePattern2 = Pattern.compile(BAD_TIME_FMT2);
		Matcher m2 = dateTimePattern2.matcher(input);
		if (m1.find()) {
			return m1.group(1);
		} else if (m2.find()) {
			return m2.group(1);
		} else {
			return "";
		}
	}

	private static String replaceBadTimeInput(String input) {
		String hmma = getBadTimeInput(input);
		if (hmma.equals(EMPTY_STRING)) {
			return input;
		} else {
			return input.replaceAll(hmma, " 0" + hmma.trim());
		}
	}
}
