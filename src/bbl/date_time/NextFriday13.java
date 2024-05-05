package bbl.date_time;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class NextFriday13 implements TemporalAdjuster
{

	@Override
	public Temporal adjustInto(Temporal temporal)
	{
		
		int day=temporal.get(ChronoField.DAY_OF_MONTH);
		int month=temporal.get(ChronoField.MONTH_OF_YEAR)-1;
		int year=temporal.get(ChronoField.YEAR);
		if(day>=13) 
		{
			if(month==11) year++;
			month=(month+1)%12;		
		}
		day=13;
		Temporal ret=LocalDate.of(year, month+1, day);
		while(ret.get(ChronoField.DAY_OF_WEEK)!=5)
		{
			ret=ret.plus(1,ChronoUnit.MONTHS);
		}		
		return ret;
	}

}
