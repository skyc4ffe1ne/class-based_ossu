interface IBook{}

class Book implements IBook {
  String title, author;
  int dayTaken;
  
  Book(String title, String author, int dayTaken){
    this.title = title;
    this.author = author;
    this.dayTaken = dayTaken; 
  }
}

class RefBook implements IBook {
  String title;
  int dayTaken;
  
  RefBook(String title, int dayTaken){
    this.title = title;
    this.dayTaken = dayTaken; 
  }
}

class AudioBook implements IBook {
  String title, author;
  int dayTaken;
  
  AudioBook(String title, String author, int dayTaken){
    this.title = title;
    this.author = author;
    this.dayTaken = dayTaken; 
  }
}