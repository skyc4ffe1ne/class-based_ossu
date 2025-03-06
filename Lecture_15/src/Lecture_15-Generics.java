import tester.*;

//interface IBookPredicate {
//  boolean apply(Book b);
//}
//interface IRunnerPredicate {
//  boolean apply(Runner r);
//}
//

interface IPred<T>{
  boolean apply(T t);
}

class isPosUnder50 implements IPred<Runner> {
  public boolean apply(Runner r){return r.pos <= 50;}
}

class bookByAuthor implements IPred<Book> {
  public boolean apply(Book b){return b.name.equals("Book0");}
}

class runnerByName implements IPred<Runner> {
  public boolean apply(Runner r){return r.name.equals("Micheal");}
}





interface IFunc<A, R>{
  boolean apply(A arg, R arg2);
}

class runnerByReadedBook implements IFunc<Book, IList<Runner>>{
  public boolean apply(Book b, IList<Runner> runners){
    if(runners.getFirst().equals(b.name)) {
      return true;
    }else {
      return false;
    }
  }
}

interface IList<T>{
  boolean find(IPred<T> pred);
  T getFirst();
//  IList<T> something(IFunc f);
}

class ConsLo<T> implements IList<T>{
  T first;
  IList<T> rest;
  
  ConsLo(T first, IList<T> rest){
    this.first = first;
    this.rest = rest;
  }

  public IList<T> filter(T t) {
    return this;
  }
  
  public boolean find(IPred<T> pred){
    if(pred.apply(this.first)) {
      return true;
    }else {
      return this.rest.find(pred);
    }
  }
  
//  public IList<T> something(IFunc f){
//    if(f.apply(this.first.getBook(), this.first.getName())) {
//      return new IList<T>(this.first, this.rest.something(f));
//    }else {
//      return this.rest.something(f);
//    }
//  }

  public T getFirst() {
    return this.first;
  }

}

class MtLo<T> implements IList<T>{
  MtLo(){}
  
  public boolean apply(T t) {
      return true;
  }

  public boolean find(IPred<T> pred) {
    return false;
  }
  
  public T getFirst() {
    return this.getFirst();
  }
}

class Runner{
  String name;
  int pos;
  Book bookRead;
  
  Runner(String name, int pos, Book bookRead){
    this.name = name;
    this.pos = pos;
    this.bookRead = bookRead;
  }
  
  String getName() {
    return this.name;
  } 
  
  Book getBook() {
    return this.bookRead;
  }
}

class Book{
  String name;
  
  Book(String name){
    this.name = name;
  }

 String getName() {
    return this.name;
  } 
  
  Book getBook() {
    return this;
  }
}


class ExamplesStringAndRunner{
  ExamplesStringAndRunner(){};
  
  IPred<Runner> posUnder50 = new isPosUnder50();
  IPred<Book> bookByAuthor = new bookByAuthor();
  IPred<Runner> runnerByName = new runnerByName();

  Book b_0 = new Book("Book0");
  Book b_1 = new Book("Book1");
  Book b_2 = new Book("Book2");

  Runner r_0 = new Runner("Micheal", 32, b_0);
  Runner r_1 = new Runner("Michelle", 43, b_1);
  Runner r_2 = new Runner("Tom",58, b_2);
  
  IList<Book> emptyBook = new MtLo<Book>();
  IList<Book> loB_0 =
      new ConsLo<Book>(b_0,
          new ConsLo<Book>(b_1,
              new ConsLo<Book>(b_2, 
                  emptyBook)));


  IList<Runner> emptyRunner = new MtLo<Runner>();
  IList<Runner> loR_0 =
      new ConsLo<Runner>(r_0,
          new ConsLo<Runner>(r_1,
              new ConsLo<Runner> (r_2, 
                  emptyRunner)));
  
  
  boolean testApply(Tester t) {
    return 
        t.checkExpect(posUnder50.apply(r_0), true)
        &&
        t.checkExpect(posUnder50.apply(r_1), true)
        &&
        t.checkExpect(posUnder50.apply(r_2), false)
        &&
        t.checkExpect(bookByAuthor.apply(b_0), true)
        &&
        t.checkExpect(bookByAuthor.apply(b_1), false)
        &&
        t.checkExpect(bookByAuthor.apply(b_2), false)
        ;
  }
  
  boolean testFind(Tester t) {
    return 
        t.checkExpect(loB_0.find(bookByAuthor), true)
        &&
        t.checkExpect(loR_0.find(runnerByName), true)
        ;
  } 
}
