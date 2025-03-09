import tester.Tester;

// Exercise 20.1 Refine the constructor for the Date class (Wrong exercise)

class Date {
  int year;
  int month;
  int day;

  Date(int year, int month, int day) {
    int max;

    if(month == 2) {
       max = 28;
    }else if(month == 4 || month == 6 || month == 9 || month == 11) {
       max = 30;
    }else {
       max = 31;
    }

    this.year  = new Util().checkError(year, 1500, 2100, "Invalid year: " + Integer.toString(year));
    this.month = new Util().checkError(month, 1, 12, "Invalid month: " + Integer.toString(month));
    this.day = new Util().checkError(day, 1, max, "Invalid day: " + Integer.toString(day));
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
}


class Posn2{
  int y,x;
  
  Posn2(int y, int x){
    this.y = y;
    this.x = x;
  }
}


// Exercise 20.2  Determine conditions that ensure that a rectangle respects this constraint. Design a constructor that ensures that,
//  the given initial values satisfy these conditions.

class Rectangle{
  Posn2 loc;
  int w, h;
  
  Rectangle(Posn2 loc, int w, int h){
    if(
        w > 0 &&
        h > 0 &&
        loc.y - h >= 0 &&
        loc.x - w >= 0
      ) {
      this.loc = loc;
      this.w = w;
      this.h = h;
    }else {
     throw new IllegalArgumentException("Invalid rectangle!");
    }
  }
}
  
// Exercise 21.3  Design a class called Factoring. A factoring is a pair of integer.
// What matters about a factoring is the product of the two numbers;
// their ordering is irrelevant. Equip the class with one method: product, which computes the product of the two integer.


class Factoring {
  int first;
  int second;
  
  Factoring(int first, int second){
    this.first = first;
    this.second = second;
  }
  
  int prod(){
    return this.first + this.second;
  }

  // determines whether one factoring is the same as some other factoring;
  boolean sameFactoring(Factoring other) {
    if(this.first == other.first && this.second == other.second ||
        this.first == other.second && this.second == other.first) {
      return true;
    }else {
      return false;
    }
  }

  // determines whether one factoring refers to the same product as some other factoring. 
  boolean sameProduct(Factoring other) {
    if(this.prod() == other.prod()){
      return true;
    }else {
      return false;
    }
  }

}

//// Exercise 21.4 Design a complete examples class for comparing instances of Coffee and Decaf
//
////represents bulk coffee for sale
//class Coffee {
//  private String origin;
//  private int price;
//
//  public Coffee(String origin, int price) {
//    this.origin = origin;
//    this.price = price;
//  }
//
//  //is this the same Coffee as other?
//  public boolean same(Coffee other) {
//    return this.origin.equals(other.origin) && this.price==other.price;
//  }
//
//}
//
//
//class Decaf extends Coffee {
//
//  private int quality; // between 97 and 99
//
//  Decaf(String origin, int price, int quality) {
//    super(origin,price);
//    this.quality = quality;
//  }
//  
//  public boolean same(Decaf other) {
//      return super.same(other) && this.quality == other.quality;
//  }
//
//  public boolean same(Coffee other) {
//      return super.same(other);
//  }
//  
//}
//some grocery items
interface IItem {
  //is this the same IItem as other?
  boolean same(IItem x);
  //is this Coffee?
  boolean isCoffee();
  //convert this to Coffee (if feasible)
  Coffee toCoffee();
  //is this Tea?
  boolean isTea();
  //convert this to Tea (if feasible)
  Tea toTea();
  //is this Tea?
  boolean isChocolate();
  //convert this to Tea (if feasible)
  Chocolate toChocolate();
}


class AItem implements IItem {
   int price;
  
  AItem(int price){
    this.price = price;
  }

  public boolean isTea() {
    return false;
  }

  public boolean isChocolate() {
    return false;
  }

  public boolean isCoffee() {
    return false;
  }
  public Tea toTea() {
   throw new IllegalArgumentException("not a tea");
  }
  public Chocolate toChocolate() {
   throw new IllegalArgumentException("not a chocolate");
  }
  public Coffee toCoffee() {
   throw new IllegalArgumentException("not a coffee");
  }
  
  public boolean same(IItem other) {return true;};
}


class Coffee extends AItem {
  private String origin;

  Coffee(String origin, int price) {
    super(price);
    this.origin = origin;
  }
  public boolean isCoffee() {
    return true;
  }
 
  public Coffee toCoffee() {
  return this;
  }
  
  public boolean same(IItem other) {
  return 
      (other.isCoffee())
      && other.toCoffee().same(this);
  }
  // is this the same Coffee as other?
  private boolean same(Coffee other) {
  return 
      this.origin.equals(other.origin)
      && this.price == other.price;
  }
}

class Tea extends AItem {
  private String kind;

  Tea(String kind, int price) {
    super(price);
    this.kind = kind;
  }

  public boolean isTea() {
    return true;
  }


  public Tea toTea() {
    return this;
  }

  public boolean same(IItem other) {
    return other.isTea()
        && other.toTea().same(this);
  }

  // is this the same Tea as other?
  private boolean same(Tea other) {
  return
      this.kind.equals(other.kind)
      && this.price == other.price;
  }

}

class Chocolate extends AItem {
  private String sweet; 

  Chocolate(String sweet, int price) {
    super(price);
    this.sweet = sweet;
  }

  public boolean isChocolate() {
    return true;
  }

  public Chocolate toChocolate() {
    return this;
  }

  public boolean same(IItem other) {
    return other.isChocolate()
        && other.toChocolate().same(this);
  }

  // is this the same Tea as other?
  private boolean same(Chocolate other) {
  return
      this.sweet.equals(other.sweet)
      && this.price == other.price;
  }

}



class ExamplesDate {
    
    ExamplesDate(){}
    
   Date d0 = new Date(1987,12,03);
   Date d1 = new Date(1930,12,03);
   Date d2 = new Date(1930,02,28);
   
   boolean testCheckConstructorException(Tester t) {
     return
         t.checkConstructorException(new IllegalArgumentException("Invalid year: 304"), "Date", 304,01,01)
         &&
         t.checkConstructorException(new IllegalArgumentException("Invalid month: 34"), "Date", 1984,34,01)
         &&
         t.checkConstructorException(new IllegalArgumentException("Invalid day: 341"), "Date", 1984,04,341)
         &&

         // February
         t.checkConstructorException(new IllegalArgumentException("Invalid day: 31"), "Date", 1984,02,31)
         &&
         t.checkConstructorException(new IllegalArgumentException("Invalid day: 30"), "Date", 1984,02,30)
         &&
         t.checkConstructorException(new IllegalArgumentException("Invalid day: 29"), "Date", 1984,02,29)
         &&

         // Month's with only 30 day 
         t.checkConstructorException(new IllegalArgumentException("Invalid day: 31"), "Date", 1984,11,31)
         &&
         t.checkConstructorException(new IllegalArgumentException("Invalid day: 31"), "Date", 1984,04,31)
         &&
         t.checkConstructorException(new IllegalArgumentException("Invalid day: 31"), "Date", 1984,06,31)
         &&
         t.checkConstructorException(new IllegalArgumentException("Invalid day: 31"), "Date", 1984,9,31)
         ;
   }

}
  

class ExamplesRect {
    
    ExamplesRect(){}
    
   Posn2 p_0 = new Posn2(150,150);
   Posn2 p_1 = new Posn2(0,0);
   Rectangle r_0 = new Rectangle(p_0, 100, 100);
   
   boolean testCheckConstructorException(Tester t) {
     return 
       t.checkConstructorException(new IllegalArgumentException("Invalid rectangle!"), "Rectangle", p_1,100,100);
   }

}

class ExamplesFactoring {
  ExamplesFactoring(){}

  Factoring f_0= new Factoring(12,4);
  Factoring f_1= new Factoring(4,12);
  Factoring f_2= new Factoring(8,8);
  
  boolean testSameFactoring(Tester t) {
    return
        t.checkExpect(f_0.sameFactoring(f_1), true)
        &&
        t.checkExpect(f_1.sameFactoring(f_2), false)
        ;
  }
  
  boolean testSameProduct(Tester t) {
    Factoring f_3 = new Factoring(0,0);
    return
        t.checkExpect(f_0.sameProduct(f_1), true)
        &&
        t.checkExpect(f_1.sameProduct(f_2), true)
        &&
        t.checkExpect(f_1.sameProduct(f_3), false)
        ;
  }
  
}


class ExamplesCoffee {
  ExamplesCoffee(){}

  IItem ethi = new Coffee("Ethiopian",1200);
  IItem kona = new Coffee("Kona",2095);
  IItem ethi1300 = (new Coffee("Ethiopian",1300)); 
  
  IItem blck = new Tea("Black",1200);

  IItem c_1 = new Chocolate("Very Sweet",10);
  IItem c_2 = new Chocolate("Fondent",4);
  IItem c_3 = new Chocolate("Black",12);

// Coffee ethi = new Coffee("Ethiopian",1200);
//  Coffee kona = new Coffee("Kona",2095);
//  Coffee ethi1300 = (new Coffee("Ethiopian",1300)); 
//  
//
//  Decaf decaf1 = new Decaf("Ethiopian",1200,99);
//  Decaf decaf2 = new Decaf("Ethiopian",1200,98);

  boolean testSameCoffee(Tester t) {
    return
        t.checkExpect(this.ethi.same(ethi), true)
        &&
        t.checkExpect(this.kona.same(ethi), false)
        &&
        t.checkExpect(this.ethi.same(ethi1300), false)
        &&
        t.checkExpect(this.ethi.same(blck), false)
        &&
        t.checkExpect(this.blck.same(blck), true)
//        &&
//        t.checkExpect(this.decaf2.same(decaf1), false)
//        &&
//        t.checkExpect(this.decaf1.same(decaf1), true)
        ;
  }
  
  
  boolean testIsCoffee(Tester t) {
    return 
        t.checkExpect(this.ethi.isCoffee(), true)
        &&
        t.checkExpect(this.kona.isCoffee(), true)
        &&
        t.checkExpect(this.ethi1300.isCoffee(), true)
        &&
        t.checkExpect(this.blck.isCoffee(), false)
    ;
  }
  

  boolean testIsTea(Tester t) {
    return 
        t.checkExpect(this.ethi.isTea(), false)
        &&
        t.checkExpect(this.kona.isTea(), false)
        &&
        t.checkExpect(this.ethi1300.isTea(), false)
        &&
        t.checkExpect(this.blck.isTea(), true)
    ;
  }
  
  boolean testToCoffee(Tester t) {
    return 
        t.checkExpect(this.ethi.toCoffee(), this.ethi)
        &&
        t.checkExpect(this.kona.toCoffee(), this.kona)
        &&
        t.checkExpect(this.ethi1300.toCoffee(), this.ethi1300)
        &&
        t.checkException(new IllegalArgumentException("not a coffee"), this.blck, "toCoffee")
   ;
  }
    
  boolean testSameChocolate(Tester t) {
    return
        t.checkExpect(this.c_1.same(c_1), true)
        &&
        t.checkExpect(this.c_2.same(c_1), false)
        &&
        t.checkExpect(this.c_2.same(c_3), false)
        &&
        t.checkExpect(this.c_1.same(blck), false)
        &&
        t.checkExpect(this.c_2.same(blck), false)
        ;
  }
  
  
  boolean testIsChocolate(Tester t) {
    return 
        t.checkExpect(this.c_1.isChocolate(), true)
        &&
        t.checkExpect(this.c_2.isChocolate(), true)
        &&
        t.checkExpect(this.c_3.isChocolate(), true)
        &&
        t.checkExpect(this.blck.isChocolate(), false)
    ;
  }
  
 
  boolean testToChocolate(Tester t) {
    return 
        t.checkExpect(this.c_1.toChocolate(), this.c_1)
        &&
        t.checkExpect(this.c_2.toChocolate(), this.c_2)
        &&
        t.checkExpect(this.c_3.toChocolate(), this.c_3)
        &&
        t.checkException(new IllegalArgumentException("not a chocolate"), this.blck, "toChocolate")
   ;
  }
}