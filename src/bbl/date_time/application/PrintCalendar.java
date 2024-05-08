package bbl.date_time.application;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.Locale;

import bbl.util.Arrays;


record MonthYearDay(int month, int year, int dayNumber)
{
	
}
public class PrintCalendar
{

	private static final int TITLE_OFFSET = 5;
	private static final int COLUMN_WIDTH = 4;
	private static DayOfWeek[] weekDays = DayOfWeek.values();
	public static void main(String[] args)
	{
		try {
			MonthYearDay monthYearD = getMonthYear(args);
			printCalendar(monthYearD);
			} 
		catch (RuntimeException e)
			{
			e.printStackTrace();
			} 
		catch (Exception e)
			{
			System.out.println(e.getMessage());
			}
	}

	private static  MonthYearDay getMonthYear(String[] args) throws Exception
	{
		int monthNumber = getMonth(args);
		int year = getYear(args);
		int dayNumber=getDayNumber(args);
		return new MonthYearDay(monthNumber, year,dayNumber);
	}

	private static int getDayNumber(String[] args) throws Exception 
	{
		int dayNumber=args.length<3 ?  0 : getFirstDayNumber(args[2]);
		return dayNumber;
	}

	private static int getFirstDayNumber(String dayString) throws Exception
	{
		int index=0;
		while(index < weekDays.length && 
			  weekDays[index].getDisplayName(TextStyle.FULL,Locale.forLanguageTag("en")).compareTo(dayString)!=0)
		{
			index++;
		}
		if(index==weekDays.length)
		{
			String exc=String.format("Parametr %s is not day of week", dayString);
			throw new Exception(exc);		
		}
		return index;		
	}

	private static int getYear(String[] args) throws Exception {
		int year = args.length < 2 ? getCurrentYear() : getYear(args[1]);
		return year;
	}

	private static int getYear(String yearStr) throws Exception {
		try
		{
			int res = Integer.parseInt(yearStr);
			return res;
		} catch (NumberFormatException e) {
			throw new Exception("year must be an integer number");
		}
		
	}

	private static int getCurrentYear() {
		
		return LocalDate.now().getYear();
	}

	private static int getMonth(String[] args) throws Exception{
		int month = args.length == 0 ? getCurrentMonth() : getMonthNumber(args[0]);
		return month;
	}

	private static int getMonthNumber(String monthStr)throws Exception {
		try {
			int result = Integer.parseInt(monthStr);
			if (result < 1) {
				throw new Exception("Month cannot be less than 1");
			}
			if(result > 12) {
				throw new Exception("Month cannot be greater than 12");
			}
			return result;
		} catch (NumberFormatException e) {
			throw new Exception("Month must be a number");
		}
	}

	private static int getCurrentMonth() {
		
		return LocalDate.now().get(ChronoField.MONTH_OF_YEAR);
	}

	private static void printCalendar(MonthYearDay monthYearD)
	{
		printTitle(monthYearD);
		printWeekDays(monthYearD);
		printDays(monthYearD);	
	}

	private static void printDays(MonthYearDay monthYear) 
	{
		int nDays = getDaysInMonth(monthYear);
		int currentWeekDay = getFirstDayOfMonth(monthYear);
		int firstOffset = getFirstOffset(currentWeekDay);
		System.out.printf("%s", " ".repeat(firstOffset));
		for(int day = 1; day <= nDays; day++) {
			System.out.printf("%" + COLUMN_WIDTH +"d", day);
			if(currentWeekDay == weekDays.length) {
				currentWeekDay = 0;
				System.out.println();
			}
			currentWeekDay++;
		}
		
	}

	private static int getFirstOffset(int currentWeekDay) {
		
		return COLUMN_WIDTH * (currentWeekDay-1) ;
	}

	private static int getFirstDayOfMonth(MonthYearDay monthYearD) 
	{
		LocalDate ld = LocalDate.of(monthYearD.year(), monthYearD.month(),
				1);
		int day=ld.get(ChronoField.DAY_OF_WEEK);
		int shift=(weekDays.length-monthYearD.dayNumber()-1+day)%weekDays.length;
		return shift+1;
	}

	private static int getDaysInMonth(MonthYearDay monthYear) {
		YearMonth ym = YearMonth.of(monthYear.year(), monthYear.month());
		return ym.lengthOfMonth();
	}

	private static void printWeekDays(MonthYearDay monthYearD) 
	{
		System.out.printf("%s", " ".repeat(1));
		for(int i=0;i< weekDays.length;i++) 
		{
			System.out.printf("%" + COLUMN_WIDTH +"s",weekDays[(i+monthYearD.dayNumber())%weekDays.length].getDisplayName(TextStyle.SHORT,
					Locale.forLanguageTag("en")));
			
		}
		System.out.println();
		
	}

	private static void printTitle(MonthYearDay monthYear) {
		String monthName = Month.of(monthYear.month())
				.getDisplayName(TextStyle.FULL, Locale.getDefault());
		System.out.printf("%s%s %d\n"," ".repeat(TITLE_OFFSET), monthName, monthYear.year());		
	}

}