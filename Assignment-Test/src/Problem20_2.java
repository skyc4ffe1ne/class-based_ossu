import tester.Tester;

// Exercise 20.1 Refine the constructor for the Date class

class Date {
  int day;
  int month;
  int year;

  Date(int day, int month, int year) {
    int max;

    if(month == 2) {
       max = 28;
    }else if(month == 4 || month == 7 || month == 9 || month == 10) {
       max = 30;
    }else {
       max = 31;
    }
    this.day = new Util().checkError(day, 1, max, "Invalid day: " + Integer.toString(day));
    this.month = new Util().checkError(month, 1, 12, "Invalid month: " + Integer.toString(month));
    this.year  = new Util().checkError(year, 1500, 2100, "Invalid year: " + Integer.toString(year));
  }

}



class Util{

  int checkError(int value, int min, int max, String msg ) {
    if(value >= min && value <= max) {
      return value;
    }else {
     throw new IllegalArgumentException(msg);
    }
  }
  
  
class ExamplesDate{
    ExamplesDate(){}
    
   Date d0 = new Date(1987,12,03);
   Date d1 = new Date(1930,12,03);
   
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
  
  
}