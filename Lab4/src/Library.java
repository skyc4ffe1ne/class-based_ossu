interface IBook{}

abstract class ABook implements IBook{
  String title;
  int dayTaken;
  
  ABook(String title, int dayTaken){
    this.title = title;
    this.dayTaken = dayTaken;
  }

}

class Book extends ABook {
  String author;
  
  Book(String title, String author, int dayTaken){
   super(title,dayTaken);
   this.dayTaken = dayTaken; 
  }
}

class RefBook extends ABook {
 
  RefBook(String title, int dayTaken){
    super(title,dayTaken);
 }

}

class AudioBook extends ABook {
  String author;
  
  AudioBook(String title, String author, int dayTaken){
    super(title,dayTaken);
    this.dayTaken = dayTaken; 
  }
}