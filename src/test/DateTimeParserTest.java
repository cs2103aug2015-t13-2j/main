package test;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import parser.DateTimeParser;

public class DateTimeParserTest {
	@Test
	// 1 param time formats
	public void parseGood1Pt() {
		DateTimeParser parser = new DateTimeParser();
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yy hh:mm a");

		String res2 = formatter.print(parser.parse("12 November 2015 1:00am"));
		String res3 = formatter.print(parser.parse("12 November 2015 100am"));
		String res4 = formatter.print(parser.parse("12 November 2015 1am"));
		String res5 = formatter.print(parser.parse("12 November 2015 1.00am"));
		String res6 = formatter.print(parser.parse("12 November 2015 100"));
		String res7 = formatter.print(parser.parse("12 November 2015 01:00am"));
		String res8 = formatter.print(parser.parse("12 November 2015 0100am"));
		String res9 = formatter.print(parser.parse("12 November 2015 01am"));
		String res10 = formatter.print(parser.parse("12 November 2015 01:00am"));
		String res11 = formatter.print(parser.parse("12 November 2015 0100am"));
		String res12 = formatter.print(parser.parse("12 November 2015 01:00"));
		String res13 = formatter.print(parser.parse("12 November 2015 1"));

		DateTime expectedDts = formatter.parseDateTime("12/11/15 1:00 am");
		String expected = formatter.print(expectedDts);

		assertEquals(true, expected.equals(res2));
		assertEquals(true, expected.equals(res3));
		assertEquals(true, expected.equals(res4));
		assertEquals(true, expected.equals(res5));
		assertEquals(true, expected.equals(res6));
		assertEquals(true, expected.equals(res7));
		assertEquals(true, expected.equals(res8));
		assertEquals(true, expected.equals(res9));
		assertEquals(true, expected.equals(res10));
		assertEquals(true, expected.equals(res11));
		assertEquals(true, expected.equals(res12));
		assertEquals(true, expected.equals(res13));
	}

	@Test
	// 2 param time formats
	public void parseGood2Pt() {
		DateTimeParser parser = new DateTimeParser();
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yy hh:mm a");

		String res2 = formatter.print(parser.parse("12 November 2015 1:00 am"));
		String res3 = formatter.print(parser.parse("12 November 2015 100 am"));
		String res4 = formatter.print(parser.parse("12 November 2015 1 am"));
		String res5 = formatter.print(parser.parse("12 November 2015 1.00 am"));

		DateTime expectedDts = formatter.parseDateTime("12/11/15 1:00 am");
		String expected = formatter.print(expectedDts);

		assertEquals(true, expected.equals(res2));
		assertEquals(true, expected.equals(res3));
		assertEquals(true, expected.equals(res4));
		assertEquals(true, expected.equals(res5));

	}

	@Test
	public void parseGoodDateWithYear() {
		DateTimeParser parser = new DateTimeParser();
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yy hh:mm a");

		String res1 = formatter.print(parser.parse("12 Nov 2015 1:00 am"));
		String res2 = formatter.print(parser.parse("12112015 1:00 am"));
		String res3 = formatter.print(parser.parse("12/11/15 1:00 am"));
		String res4 = formatter.print(parser.parse("121115 1:00 am"));

		DateTime expectedDts = formatter.parseDateTime("12/11/15 1:00 am");
		String expected = formatter.print(expectedDts);

		assertEquals(true, expected.equals(res1));
		assertEquals(true, expected.equals(res2));
		assertEquals(true, expected.equals(res3));
		assertEquals(true, expected.equals(res4));
	}

	@Test
	public void parseGoodDateWithoutYear() {
		DateTimeParser parser = new DateTimeParser();
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM");

		String res1 = formatter.print(parser.parse("12/11"));
		String res2 = formatter.print(parser.parse("1211"));
		String res3 = formatter.print(parser.parse("12 Nov"));
		String res4 = formatter.print(parser.parse("12 November"));

		DateTime expectedDts = formatter.parseDateTime("12/11");
		String expected = formatter.print(expectedDts);

		assertEquals(true, expected.equals(res1));
		assertEquals(true, expected.equals(res2));
		assertEquals(true, expected.equals(res3));
		assertEquals(true, expected.equals(res4));
	}

}
