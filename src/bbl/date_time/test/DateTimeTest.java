package bbl.date_time.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import bbl.date_time.NextFriday13;
import bbl.date_time.BarMizvaAdjuster;
import bbl.date_time.Friday13Range;

class DateTimeTest {

	@Test
	void barMizvaAdjusterTest() {
		LocalDate birthDate = LocalDate.parse("1799-06-06");
		LocalDate expected = LocalDate.of(1812, 6, 6);
		assertEquals(expected, birthDate.with(new BarMizvaAdjuster()));
	}
	@Test
	void nextFriday13Test()
	{
		NextFriday13 nextF13=new NextFriday13();
		Temporal temporal=LocalDate.of(2024, 5, 1).with(nextF13);
		Temporal expected=LocalDate.of(2024, 9, 13);
		assertEquals(expected,temporal);
	}
	@Test
	void friday13RangeTest()
	{
		Friday13Range range1 = new Friday13Range(LocalDate.of(2024, 5, 1),LocalDate.of(2024, 8, 10));		
		Iterator<Temporal> it1 = range1.iterator();			
		assertThrowsExactly(NoSuchElementException.class, () -> it1.next());
		
		Friday13Range range2 = new Friday13Range(LocalDate.of(2024, 9, 13),LocalDate.of(2026, 12, 31));
		Iterator<Temporal> it2 = range2.iterator();
		Temporal temporal=null;
		int count=0;
		while(it2.hasNext())
		{
			temporal=it2.next();
			count++;
			assertEquals(13,temporal.get(ChronoField.DAY_OF_MONTH));
			assertEquals(5,temporal.get(ChronoField.DAY_OF_WEEK));
		}
		// 13.12.24, 13.06.25, 13.02,26, 13.03.26, 13.11.26
		assertEquals(5,count);
	}
	@Test
	void displayCurrentDateTimeTest()
	{
		//The following test implies printing out
		//all current date/time in Time Zones containing string Canada
		
		displayCurrentDateTime("Canada");
		
	}
	private void displayCurrentDateTime(String zonePart) {
		
		// prints out current date/time in all zones containing zonePart
		//format <year>-<month number>-<day> <hh:mm> <zone name>
		//ZonedDateTime class
		
		ZonedDateTime time = null;
		for (String s: ZoneId.getAvailableZoneIds())
		{
			if(s.indexOf(zonePart)>=0)
			{
				ZoneId zone = ZoneId.of(s);
				time = ZonedDateTime.now(zone);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm VV");
				String formattedString = time.format(formatter);
				System.out.println(formattedString);
			}
		}
		
	}
	

}