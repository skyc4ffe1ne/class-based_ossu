import tester.Tester;

interface IBook{
  int daysOverdue(int today);
  boolean isOverdue(int day);
}

abstract class ABook implements IBook{
  String title;
  int dayTaken;
  
  ABook(String title, int dayTaken){
    this.title = title;
    this.dayTaken = dayTaken;
  }
  
  
  public int daysOverdue(int today) {
    return today - (this.dayTaken + 14);
  }
  
  public boolean isOverdue(int day) {
    // If this.daysOverdue >= 0 is Overdue (true) 
    // Else isn't (false) 
    return this.daysOverdue(day) >= 0;
  }

}

class Book extends ABook {
  String author;
  
  Book(String title, String author, int dayTaken){
   super(title,dayTaken);
   this.author = author;
  }
}

class RefBook extends ABook {
  
  RefBook(String title, int dayTaken) {
    super(title, dayTaken);
  }
  
  public int daysOverdue(int today) {
    return today - (this.dayTaken + 2);
  }
 

  public boolean isOverdue(int day) {
    return this.daysOverdue(day) >= 0; 
  }
}

class AudioBook extends ABook {
  String author;
  
  AudioBook(String title, String author, int dayTaken){
    super(title,dayTaken);
    this.author = author;
  }
}

class ExamplesLibrary{
  ExamplesLibrary(){}
  
  // Books
  IBook b_0 = new Book("title_0", "author_0", 7999);
  IBook b_1 = new Book("title_1", "author_1", 7980);
  IBook b_2 = new Book("title_2", "author_2", 7997);

  //RefBooks
  IBook rb_0 = new RefBook("title_0", 7982);
  IBook rb_1 = new RefBook("title_1", 7986);
  IBook rb_2 = new RefBook("title_2", 7975);
  
  // AudioBook
  IBook ab_0 = new AudioBook("title_0", "author_0", 7996);
  IBook ab_1 = new AudioBook("title_1", "author_1", 7875);
  IBook ab_2 = new AudioBook("title_2", "author_2", 7675);
  
  
  boolean testDaysOverdue(Tester t)
  {
    int today = 8000;
    return 
        t.checkExpect(b_0.daysOverdue(today), - 13)
        &&
        t.checkExpect(b_1.daysOverdue(today), 6)
        &&
        t.checkExpect(b_2.daysOverdue(today), - 11)
        &&        
        t.checkExpect(rb_0.daysOverdue(today), 16)
        &&
        t.checkExpect(rb_1.daysOverdue(today), 12)
        &&
        t.checkExpect(rb_2.daysOverdue(today), 23)
        &&
        t.checkExpect(ab_0.daysOverdue(today), - 10)
        &&
        t.checkExpect(ab_1.daysOverdue(today), 111)
        &&
        t.checkExpect(ab_2.daysOverdue(today), 311)
        ;

  }
  
  
  boolean testOverdue(Tester t) {
    return 
        t.checkExpect(b_0.isOverdue(8012), false)
        &&
        t.checkExpect(b_0.isOverdue(8014), true)
        &&
        t.checkExpect(rb_0.isOverdue(7983), false)
        &&
        t.checkExpect(rb_0.isOverdue(7985), true)
        &&
        t.checkExpect(ab_0.isOverdue(8005), false)
        &&
        t.checkExpect(ab_0.isOverdue(8010), true)
        ;
  }
}