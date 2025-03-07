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
  R apply(A arg);
}

class RunnerToStringName implements IFunc<Runner,String>{
  public String apply (Runner r) {
     return r.name;
  }
}

class RunnerToAge implements IFunc<Runner, Integer>{
  public Integer apply (Runner r) {
     return r.pos;
  }
}

class RunnerToBook implements IFunc<Runner, Book>{
  public Book apply (Runner r) {
    return r.bookRead;
  }
}

interface IList<T>{
  boolean find(IPred<T> pred);
  T getFirst();
  <R> IList<R>map(IFunc <T,R> func);
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
  
  public T getFirst() {
    return this.first;
  }
  
  public <R> IList<R> map(IFunc <T,R> func){
    return new ConsLo<R>(func.apply(this.first), this.rest.map(func));
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
  
  public <R> IList<R> map(IFunc <T,R> func){
    return new MtLo<R>();
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

  IFunc<Runner, Book> runnerToBook = new RunnerToBook();
  IFunc<Runner, Integer> runnerToAge = new RunnerToAge();
  IFunc<Runner, String> runnerToStringName = new RunnerToStringName();
  
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
  
  boolean testMap(Tester t) {
    return 
        t.checkExpect(loR_0.map(runnerToBook),
            new ConsLo<Book>(b_0,
                new ConsLo<Book>(b_1,
                    new ConsLo<Book>(b_2,
                        new MtLo<Book>()))))
        &&
        t.checkExpect(loR_0.map(runnerToStringName),
            new ConsLo<String>(r_0.name,
                new ConsLo<String>(r_1.name,
                    new ConsLo<String>(r_2.name,
                        new MtLo<String>()))))
        &&
        t.checkExpect(loR_0.map(runnerToAge),
            new ConsLo<Integer>(r_0.pos,
                new ConsLo<Integer>(r_1.pos,
                    new ConsLo<Integer>(r_2.pos,
                        new MtLo<Integer>()))))
 
       ;
  }
}
