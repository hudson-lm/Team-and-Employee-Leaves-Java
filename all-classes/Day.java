public class Day implements Cloneable, Comparable<Day> {
	
	private int year;
	private int month;
	private int day;
	
	//Constructor
	public Day(int y, int m, int d) {
		this.year=y;
		this.month=m;
		this.day=d;		
	}

	@Override
	public int compareTo(Day another){
		if(this.year != another.year){
			return Integer.compare(this.year, another.year);
		}else if(this.month != another.month){
			return Integer.compare(this.month, another.month);
		}else{
			return Integer.compare(this.day, another.day);
		}
	}

	private static int getDaysPerMonth(int month, int year){
		switch(month){
			case 1: case 3: case 5: case 7: case 8: case 10: case 12:
				return 31;
			case 4: case 6: case 9: case 11:
				return 30;
			case 2:
				if(isLeapYear(year)){
					return 29;
				} else return 28;
			default:
				return 0;
		}
	}

	public static int calcDuration(Day start, Day end){
		if(start.year == end.year){
			int dayUntilStart = 0;
			for(int month = 1; month<start.month;month++){
				dayUntilStart +=getDaysPerMonth(month, start.year);
			}
			dayUntilStart+=start.day;

			int dayUntilEnd = 0;
			for(int month = 1;month<end.month;month++){
				dayUntilEnd+=getDaysPerMonth(month, end.year);
			}
			dayUntilEnd+=end.day;
			return dayUntilEnd - dayUntilStart + 1;
		}else{
			int dayUntilStart = 0;
			dayUntilStart += getDaysPerMonth(start.month, start.year) - start.day + 1;
			for (int month = start.month + 1; month <= 12; month++) {
				dayUntilStart += getDaysPerMonth(month, start.year);
			}
			int dayUntilEnd = 0;
			for(int month = 1;month<end.month;month++){
				dayUntilEnd += getDaysPerMonth(month, end.year);
			}

			int dayInBetween = 0;
				for (int year = start.year + 1; year < end.year; year++) {
					if(isLeapYear(year)){
						dayInBetween += 366;
					}else{
						dayInBetween += 365;
					}
			}
        	return dayUntilStart + dayUntilEnd + dayInBetween;
		}
	}
	
	// check if a given year is a leap year
	static public boolean isLeapYear(int y)
	{
		if (y%400==0)
			return true;
		else if (y%100==0)
			return false;
		else if (y%4==0)
			return true;
		else
			return false;
	}
	 
	// check if y,m,d valid
	static public boolean valid(int y, int m, int d)
	{
		if (m<1 || m>12 || d<1) return false;
		switch(m){
			case 1: case 3: case 5: case 7:
			case 8: case 10: case 12:
					 return d<=31; 
			case 4: case 6: case 9: case 11:
					 return d<=30; 
			case 2:
					 if (isLeapYear(y))
						 return d<=29; 
					 else
						 return d<=28; 
		}
		return false;
	}

	// Return a string for the day like dd MMM yyyy
    @Override
	public String toString() {
		return day+"-"+MonthNames.substring((month-1)*3,month*3)+"-"+year;
	}

    private static final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";

    public void set(String sDay){
        String[] sDayParts = sDay.split("-");
        this.day = Integer.parseInt(sDayParts[0]);
        this.month = MonthNames.indexOf(sDayParts[1])/3+1;
        this.year = Integer.parseInt(sDayParts[2]);
    }

    public Day(String sDay){
        set(sDay);
    }

    @Override
    public Day clone(){
        Day copy = null;
        try {
            copy = (Day) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return copy;
    }
}