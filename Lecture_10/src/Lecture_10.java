import tester.*;

class Date {
  int year; 
  int month;
  int day; 
  
  
  Date(int year, int month, int day)
  {
    this.year  = new Utils().checkRange(year, 1500, 2100, "Invalid year: " + Integer.toString(year));
    this.month = new Utils().checkRange(month, 1, 12, "Invalid month: " + Integer.toString(month));
    this.day   = new Utils().checkRange(day, 1, 31, "Invalid day: " + Integer.toString(day));
  }
  
  Date(int month, int day)
  {
    this(2022, month, day);
  }

}

class Utils {
 
  int checkRange(int value, int min, int max, String msg) {
   if(value >= min && value <= max)
   {
     return value;
   }else
   {
      throw new IllegalArgumentException(msg);
   }
  }
}


class ExamplesDate{
  ExamplesDate(){}
  
   Date d0 = new Date(1987,12,03);
   Date d1 = new Date(1930,12,03);

//   Date d2 = new Date(304,01,01);
   
   boolean testCheckConstructorException(Tester t) {
     return
         t.checkConstructorException(new IllegalArgumentException("Invalid year: 304"), "Date", 304,01,01)
         &&
         t.checkConstructorException(new IllegalArgumentException("Invalid month: 34"), "Date", 1984,34,01)
         &&
         t.checkConstructorException(new IllegalArgumentException("Invalid day: 341"), "Date", 1984,04,341)
         ;
   }

}