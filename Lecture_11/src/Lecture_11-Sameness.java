import tester.*;

//class Author
//{
//  String firstName;
//  String lastName;
//  
//  Author(String firstName,String lastName)
//  {
//    this.firstName = firstName;
//    this.lastName = lastName;
//  }
//  
//  
//  boolean sameAuthor(Author that)
//  {
//    return this.firstName.equals(that.firstName) && 
//        this.lastName.equals(that.lastName);
//  }
//}
//
//
//class Book
// {
//   String name;
//   Author author;
//   
//   
//   Book(String name, Author author)
//   {
//     this.name = name;
//     this.author = author;
//   }
//   
//   boolean sameBook(Book that)
//   {
//     return this.name.equals(that.name) 
//           && this.author.sameAuthor(that.author);
//   }
//   
// }
//

//class ExampleBook
//{
//  ExampleBook(){}
//
// Author a_0 = new Author("A", "B"); 
// Book b_0 = new Book("title", a_0); 
// Book b_1 = new Book("title", a_0); 
//
//  boolean testSameBook(Tester t)
//  {
//   return
//       t.checkExpect(b_0.sameBook(b_1), true);
//  }
//}

interface IShape{
  boolean sameShape(IShape that);

  boolean isCircle();
  boolean isRect();
  boolean isSquare();

  boolean sameCircle(Circle that);
  boolean sameRect(Rect that);
  boolean sameSquare(Square that);
  boolean sameCombo(Combo that);
}


class AShape  implements IShape{
  
  int x, y;
  
  AShape(int x, int y){
    this.x = x;
    this.y = y;
  }
  
 public boolean isCircle()
 {
  return false; 
 }
 
 public boolean isRect()
 {
  return false; 
 }
  
 public boolean isSquare()
 {
  return false; 
 }

  public boolean sameShape(IShape that) {
    return false;
  }
  
  public  boolean sameCircle(Circle that) {
    return false;
  }
  
  public boolean sameRect(Rect that) {
    return false;
  }
  
  public boolean sameSquare(Square that) {
    return false;
  }
  
  public boolean sameCombo(Combo that) {
    return false;
  }

}


class Circle extends AShape{
  int radius;
  
  Circle(int x, int y, int radius)
  {
   super(x,y);
   this.radius = radius;
  }
  
 public boolean isCircle()
 {
  return true; 
 }

 public boolean sameShape(IShape that)
  {
    if(that.isCircle()) {
      return this.sameCircle((Circle)that);
    }else {
      return false;
    }
  }
  
  public boolean sameCircle(Circle that) {
    return 
        this.x == that.x &&
        this.y == that.y &&
        this.radius == that.radius;
  }

}

class Rect extends AShape{

  int w, h;

  Rect(int x, int y, int w, int h)
  {
    super(x,y);
    this.w = w;
    this.h = h;
  }
  public boolean isRect()
  {
    return true; 
  }

  public boolean sameShape(IShape that)
  {
    if(that.isRect()) {
      return this.sameRect((Rect)that);
    }else {
      return false;
    }
  }
 
  public boolean sameRect(Rect that) {
    return 
        this.x == that.x &&
        this.y == that.y &&
        this.w == that.w &&
        this.h == that.h;
  }
}

class Square extends AShape{

 int s;

 Square(int x, int y, int s)
 {
   super(x, y);
    s = this.s;
 }
  
 public boolean isSquare()
 {
  return true; 
 }

  public boolean sameShape(IShape that)
  {
    if(that.isSquare()) {
      return this.sameSquare((Square)that);
    }else {
      return false;
    }
  }
 
  public boolean sameSquare(Square that) {
    return 
        this.x == that.x &&
        this.y == that.y &&
        this.s == that.s; 
  }

}


class Combo extends AShape{
  IShape left;
  IShape right;
  
  Combo(int x, int y,IShape left,IShape right)
  {
    super(x,y);
    this.left = left;
    this.right = right;
  }
  
  public boolean sameShape(IShape that) {
    return that.sameCombo(this);
  }
  
  public boolean sameCombo(Combo that) {
    return
        this.left.sameShape(that.left) &&
        this.right.sameShape(that.right);
  }
  
  
}

class ExamplesShape{

  Circle c1 = new Circle(3, 4, 5);
  Circle c2 = new Circle(4, 5, 6);
  Circle c3 = new Circle(3, 4, 5);

  Rect r1 = new Rect(3, 4, 5, 5);
  Rect r2 = new Rect(4, 5, 6, 7);
  Rect r3 = new Rect(3, 4, 5, 5);

  Square s1 = new Square(3, 4, 5);
  Square s2 = new Square(4, 5, 6);
  Square s3 = new Square(3, 4, 5);
  
  Combo cb1 = new Combo(10, 120, c1, c2);
  Combo cb2 = new Combo(10, 120, c1, c2);
  Combo cb3 = new Combo(120, 20, r1, s1);
  Combo cb4 = new Combo(10, 120, r1, s1);

  
  boolean testSameCircle(Tester t)
  {
    return
        t.checkExpect(c1.sameCircle(c2), false)
        &&
        t.checkExpect(c2.sameCircle(c1), false)
        &&
        t.checkExpect(c1.sameCircle(c3), true)
        &&
        t.checkExpect(c3.sameCircle(c1), true);
  }
  
    boolean testSameRect(Tester t)
  {
    return
        t.checkExpect(r1.sameRect(r2), false)
        &&
        t.checkExpect(r2.sameRect(r1), false)
        &&
        t.checkExpect(r1.sameRect(r3), true)
        &&
        t.checkExpect(r3.sameRect(r1), true);
  }
    
    boolean testSameSquare(Tester t)
  {
    return
        t.checkExpect(s1.sameSquare(s2), false)
        &&
        t.checkExpect(s2.sameSquare(s1), false)
        &&
        t.checkExpect(s1.sameSquare(s3), true)
        &&
        t.checkExpect(s3.sameSquare(s1), true);
  }
    
    boolean testSameShape(Tester t)
    {
      return 
          t.checkExpect(c1.sameShape(c3), true) 
          &&
          t.checkExpect(c1.sameShape(c2), false)
          &&
          t.checkExpect(r1.sameShape(r3), true) 
          &&
          t.checkExpect(r1.sameShape(r2), false)
          &&
          t.checkExpect(c1.sameShape(r1), false)
          &&
          t.checkExpect(s1.sameShape(s2), false)
          &&
          t.checkExpect(s2.sameShape(s1), false)
          &&
          t.checkExpect(s1.sameShape(s3), true)
          &&
          t.checkExpect(s3.sameShape(s1), true)
          &&
          t.checkExpect(s1.sameShape(r2), false) 
          &&
          t.checkExpect(r2.sameShape(s1), false) 
          &&
          t.checkExpect(s1.sameShape(r1), false) 
          &&
          t.checkExpect(r1.sameShape(s1), false)  
          ;
    }
    
    
    boolean testSameCombo(Tester t)
    {
      return 
          t.checkExpect(cb1.sameCombo(cb2), true)
          &&
          t.checkExpect(cb3.sameCombo(cb4), true)
          &&
          t.checkExpect(cb2.sameCombo(cb3), false)
          &&
          t.checkExpect(cb1.sameCombo(cb4), false)
          ;
    }
}

