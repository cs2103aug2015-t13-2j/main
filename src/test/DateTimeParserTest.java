package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

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

		String res2 = formatter.print(DateTimeParser.parse("12 November 2015 1:00am"));
		String res3 = formatter.print(DateTimeParser.parse("12 November 2015 100am"));
		String res4 = formatter.print(DateTimeParser.parse("12 November 2015 1am"));
		String res5 = formatter.print(DateTimeParser.parse("12 November 2015 1.00am"));
		String res6 = formatter.print(DateTimeParser.parse("12 November 2015 100"));
		String res7 = formatter.print(DateTimeParser.parse("12 November 2015 01:00am"));
		String res8 = formatter.print(DateTimeParser.parse("12 November 2015 0100am"));
		String res9 = formatter.print(DateTimeParser.parse("12 November 2015 01am"));
		String res10 = formatter.print(DateTimeParser.parse("12 November 2015 01:00am"));
		String res11 = formatter.print(DateTimeParser.parse("12 November 2015 0100am"));
		String res12 = formatter.print(DateTimeParser.parse("12 November 2015 01:00"));
		String res13 = formatter.print(DateTimeParser.parse("12 November 2015 1"));

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

		String res2 = formatter.print(DateTimeParser.parse("12 November 2015 1:00 am"));
		String res3 = formatter.print(DateTimeParser.parse("12 November 2015 100 am"));
		String res4 = formatter.print(DateTimeParser.parse("12 November 2015 1 am"));
		String res5 = formatter.print(DateTimeParser.parse("12 November 2015 1.00 am"));

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

		String res1 = formatter.print(DateTimeParser.parse("12 Nov 2015 1:00 am"));
		String res2 = formatter.print(DateTimeParser.parse("12112015 1:00 am"));
		String res3 = formatter.print(DateTimeParser.parse("12/11/15 1:00 am"));
		String res4 = formatter.print(DateTimeParser.parse("121115 1:00 am"));
		String res5 = formatter.print(DateTimeParser.parse("12/11/2015 1:00 am"));

		DateTime expectedDts = formatter.parseDateTime("12/11/15 1:00 am");
		String expected = formatter.print(expectedDts);

		assertEquals(true, expected.equals(res1));
		assertEquals(true, expected.equals(res2));
		assertEquals(true, expected.equals(res3));
		assertEquals(true, expected.equals(res4));
		assertEquals(true, expected.equals(res5));
}

	@Test
	public void parseGoodDateWithoutYear() {
		DateTimeParser parser = new DateTimeParser();
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM");

		String res1 = formatter.print(DateTimeParser.parse("12/11"));
		String res2 = formatter.print(DateTimeParser.parse("1211"));
		String res3 = formatter.print(DateTimeParser.parse("12 Nov"));
		String res4 = formatter.print(DateTimeParser.parse("12 November"));
		String res5 = formatter.print(DateTimeParser.parse("12Nov"));
		String res6 = formatter.print(DateTimeParser.parse("12November"));

		DateTime expectedDts = formatter.parseDateTime("12/11");
		String expected = formatter.print(expectedDts);

		assertEquals(true, expected.equals(res1));
		assertEquals(true, expected.equals(res2));
		assertEquals(true, expected.equals(res3));
		assertEquals(true, expected.equals(res4));
		assertEquals(true, expected.equals(res5));
		assertEquals(true, expected.equals(res6));
	}
	
	@Test
	// When dates are not of correct format or if they are out of bounds, return false
	public void parseBadDates() {
		DateTimeParser parser = new DateTimeParser();
		
		// Out of bounds days
		DateTime res1 = DateTimeParser.parse("31/11/15"); // Boundary day
		DateTime res1_1 = DateTimeParser.parse("0/11/15"); // Boundary day
		DateTime res1_2 = DateTimeParser.parse("3/13/15"); // Boundary month
		DateTime res1_3 = DateTimeParser.parse("3/0/15"); // Boundary month

		// Negative day and month
		DateTime res2 = DateTimeParser.parse("-2/11"); 
		DateTime res3 = DateTimeParser.parse("2/-11");
		
		// Out of bound times
		DateTime res5 = DateTimeParser.parse("2/11/15 25.0");
		DateTime res6 = DateTimeParser.parse("2/11/15 0:00");
		DateTime res7 = DateTimeParser.parse("2/11/15 13:00pm");
		DateTime res8 = DateTimeParser.parse("2/11/15 -13:00");
		DateTime res9 = DateTimeParser.parse("2/11/15 3:-45pm");
		
		// Invalid Month
		DateTime res10 = DateTimeParser.parse("2 Noc 3:45pm");
		DateTime res11 = DateTimeParser.parse("2 Juli 3:45pm");
		
		// Invalid am/pm
		DateTime res12 = DateTimeParser.parse("2 July 3:45ambulance");
		DateTime res13 = DateTimeParser.parse("2 July 3:45pem");
		DateTime res14 = DateTimeParser.parse("2/7 3:45hm");
		DateTime res15 = DateTimeParser.parse("2/7/15 3:45am jajaja");

		assertEquals(true, res1==null);
		assertEquals(true, res1_1==null);
		assertEquals(true, res1_2==null);
		assertEquals(true, res1_3==null);
		assertEquals(true, res2==null);
		assertEquals(true, res3==null);
		assertEquals(true, res5==null);
		assertEquals(true, res6==null);
		assertEquals(true, res7==null);
		assertEquals(true, res8==null);
		assertEquals(true, res9==null);
		assertEquals(true, res10==null);
		assertEquals(true, res11==null);
		assertEquals(true, res12==null);
		assertEquals(true, res13==null);
		assertEquals(true, res14==null);
		assertEquals(true, res15==null);

		
	}

}
