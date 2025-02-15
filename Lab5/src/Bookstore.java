import tester.*;
interface ILoBook{
 int count();
 double salePrice(int discount);
 ILoBook allBefore(int y);

 ILoBook sortByPrice();
 ILoBook insert(Book book);

 ILoBook sortByTitle();
 ILoBook insertTitle(Book book);

}


class Book {
  String title;
  String author;
  int year;
  double price;
  
  Book(String title, String author, int year, double price){
    this.title = title;
    this.author = author;
    this.year = year;
    this.price = price;
  }
  
  //is the price of this book cheaper than the price of the given book?
  boolean cheaperThan(Book that) {
    return this.price < that.price;
  }
  
  
  int cheaperTitle(Book that) {
    return this.title.compareTo(that.title);
  }
}

class ConsLoBook implements ILoBook{
  Book first;
  ILoBook rest;
  
  
  ConsLoBook(Book first, ILoBook rest){
   this.first = first; 
   this.rest = rest; 
  }
  

  public int count() 
  {
    return 1 + this.rest.count(); 
  }
  
  

  // salePrice(int) -> double
  // Calculate the new price of all the book in the list and return the new list of book with updated price.
  public double salePrice(int discount) 
  {
    return this.helpSalePrice(this.first.price,discount) + this.rest.salePrice(discount); 
  }

  double helpSalePrice(double price,int discount)
  {
   return price - ((price * discount) / 100); 
  }
  
   // produce a list of all books published before the given date
  // from this list of books 
  public ILoBook allBefore(int y) {
    if(this.helperAllBefore(y)) {
     return new ConsLoBook(this.first, this.rest.allBefore(y)); 
    }else {
      return this.rest.allBefore(y);
    }
  }
  
  boolean helperAllBefore(int y) {
   return this.first.year < y; 
  }

  
  // ILoBook -> ILoBook 
  // return a sorted list based on their price
  public ILoBook sortByPrice() {
   return this.rest.sortByPrice().insert(this.first); //mtlist.insert(htpd)
  }
  
  public ILoBook insert(Book b) {
    if(this.first.cheaperThan(b))
    {
      return new ConsLoBook(this.first,this.rest.insert(b));
    }else {
      return new ConsLoBook(b,this); // ConsLoBook(htdp,mtlist)
    }
  }
  
  
 public ILoBook sortByTitle() {
   return this.rest.sortByTitle().insertTitle(this.first);
 }
 
 public ILoBook insertTitle(Book b) {
   if (this.first.cheaperTitle(b) < 0)
   {
     return new ConsLoBook(this.first,this.rest.insertTitle(b));
   }else {
     return new ConsLoBook(b,this.rest);
   }
 }

}



class MtLoBook implements ILoBook{
  MtLoBook(){}
  
  public int count() {
    return 0;
  }
  
  public double salePrice(int discount) {
    return 0.0;
  }
   
  public ILoBook allBefore(int y) {
   return this; 
  }

  public ILoBook sortByPrice() {
   return this; 
  }
  
  public ILoBook insert(Book b) 
  { 
    return new ConsLoBook(b,this);
  }
  
  public ILoBook sortByTitle() {
   return this; 
  }
  
  public ILoBook insertTitle(Book b) 
  { 
    return new ConsLoBook(b,this);
  }  
}


class ExamplesILoBook{
 ExamplesILoBook(){} 

//Books
Book htdp = new Book("HtDP", "MF", 2001, 60);
Book lpp = new Book("LPP", "STX", 1942, 25);
Book ll = new Book("LL", "FF", 1986, 10);

//lists of Books
ILoBook mtlist = new MtLoBook();
ILoBook lista = new ConsLoBook(this.lpp, this.mtlist);
ILoBook listb = new ConsLoBook(this.htdp, this.mtlist);
ILoBook listc = new ConsLoBook(this.lpp,
               new ConsLoBook(this.ll, this.listb));
ILoBook listd = new ConsLoBook(this.ll,
                 new ConsLoBook(this.lpp,
                   new ConsLoBook(this.htdp, this.mtlist)));
ILoBook listdUnsorted =
new ConsLoBook(this.lpp,
  new ConsLoBook(this.htdp,
    new ConsLoBook(this.ll, this.mtlist)));

 
 boolean testCont(Tester t)
 {
   return t.checkExpect(this.lista.count(), 1);
 }
 
 boolean testSalePrice(Tester t) {
   return
   // no discount -- full price
   t.checkInexact(this.mtlist.salePrice(0), 0.0, 0.001) &&
   t.checkInexact(this.lista.salePrice(0), 25.0, 0.001) &&
   t.checkInexact(this.listc.salePrice(0), 95.0, 0.001) &&
   t.checkInexact(this.listd.salePrice(0), 95.0, 0.001) &&
   // 50% off sale -- half price
   t.checkInexact(this.mtlist.salePrice(50), 0.0, 0.001) &&
   t.checkInexact(this.lista.salePrice(50), 12.5, 0.001) &&
   t.checkInexact(this.listc.salePrice(50), 47.5, 0.001) &&
   t.checkInexact(this.listd.salePrice(50), 47.5, 0.001);
 }
 
  //tests for the method allBefore
  boolean testAllBefore(Tester t) {
  return
  t.checkExpect(this.mtlist.allBefore(2001), this.mtlist) &&
  t.checkExpect(this.lista.allBefore(2001), this.lista) &&
  t.checkExpect(this.listb.allBefore(2001), this.mtlist) &&
  t.checkExpect(this.listc.allBefore(2001),
     new ConsLoBook(this.lpp, new ConsLoBook(this.ll,this.mtlist))) &&
  t.checkExpect(this.listd.allBefore(2001),
     new ConsLoBook(this.ll, new ConsLoBook(this.lpp, this.mtlist)));
  } 
  
  // test for the method sortedList 
    boolean testSortedList(Tester t) {
  return
  t.checkExpect(this.mtlist.sortByPrice(), this.mtlist) &&
  t.checkExpect(this.lista.sortByPrice(), this.lista) &&
  t.checkExpect(this.listb.sortByPrice(), this.listb) &&
  t.checkExpect(this.listc.sortByPrice(),
     new ConsLoBook(this.ll, new ConsLoBook(this.lpp, new ConsLoBook(this.htdp, this.mtlist)))) &&
  t.checkExpect(this.listd.sortByPrice(),this.listd);
  } 
    
    boolean testSort(Tester t) {
      return
      t.checkExpect(this.listc.sortByPrice(), this.listd) &&
      t.checkExpect(this.listdUnsorted.sortByPrice(), this.listd);
    }
    
    
    boolean testSortTitle(Tester t) {
      return t.checkExpect(this.listc.sortByTitle(),
          new ConsLoBook(this.htdp, new ConsLoBook(this.ll, new ConsLoBook(this.lpp, this.mtlist))));
    }
}


