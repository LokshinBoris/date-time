package bbl.date_time;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Friday13Range implements Iterable<Temporal> 
{
    Temporal from;
    Temporal to;
	
    public Friday13Range(Temporal from, Temporal to)
    {
		this.from = from;
		this.to = to;
	}
	@Override
	public Iterator<Temporal> iterator()
	{
		return new FridayIterator();
	}
	private class FridayIterator implements Iterator<Temporal>
	{
		NextFriday13 NextF13=new NextFriday13();
		Temporal current=from.with(NextF13);
		@Override
		public boolean hasNext() 
		{
			ChronoUnit unit = ChronoUnit.DAYS;
			return unit.between(current, to)>0;
		}

		@Override
		public Temporal next() 
		{
			if(!hasNext()) 
			{
				throw new NoSuchElementException();
			}
			Temporal ret=current;
			current=current.with(NextF13);
			return ret;
		}
		
	}

}