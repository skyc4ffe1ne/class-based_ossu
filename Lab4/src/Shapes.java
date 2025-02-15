import tester.*;

interface IShape
{
  double distanceToOrigin();
  double area();
  IShape grow(int inc);
  boolean isBiggerThan(IShape that);
  boolean contains(CartPT points);
}

// to represent 2-d point by Cartesian coordinate  
class CartPT{
    int x;
    int y;
    
    CartPT(int x, int y){
     this.x = x; 
     this.y = y; 
    }
    
    double distanceToOrigin() {
      return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    CartPT grow(int inc) {
      return new CartPT((this.x +inc), (this.y + inc));
    }
    
    boolean contains(CartPT that){
     return this.x < that.x && this.y < that.y;
    }
}



class Circle implements IShape
{
  CartPT points;
  int radius;
  String color;
 
  Circle(CartPT points,int radius,String color)
  {
   this.points = points; 
   this.radius = radius; 
   this.color = color; 
  }
  
  public double distanceToOrigin() {
   return this.points.distanceToOrigin() - this.radius;
  }
  
  public double area() {
   return Math.PI * this.radius * this.radius;
  }
  
  public IShape grow(int inc) {
   return new Circle(this.points.grow(inc),this.radius,this.color);
  }
 

  public boolean isBiggerThan(IShape that) {
    return this.area() > that.area();
  }
  
  public boolean contains(CartPT point) 
  {
    return this.points.contains(point);
  }
  
}

class Square implements IShape
{

  CartPT points;
  int size;
  String color;
 
  Square(CartPT points, int size, String color)
  {
   this.points = points;
   this.size = size;
   this.color = color; 
  }
  

  public double distanceToOrigin() {
    return this.points.distanceToOrigin();
  }

  public double area() {
   return this.size * this.size;
  }
  


  public IShape grow(int inc) {
   return new Square(new CartPT(this.points.x,this.points.y ),(this.size + inc),this.color);
  }

  
  public boolean isBiggerThan(IShape that) {
    return this.area() > that.area();
  }
  
  public boolean contains(CartPT points)
    {
      return this.contains(points);
    }
}


class ExamplesShape{
  ExamplesShape(){}
  CartPT point = new CartPT(50,50);
  // Methods ?
  
  IShape c1 = new Circle(point,10,"red");
  IShape c2 = new Circle(point,30,"red");
  IShape c3 = new Circle(new CartPT(30,100),30,"red");

  IShape s1 = new Square(point,30,"red");
  IShape s2 = new Square(point,50,"red");
  IShape s3 = new Square(new CartPT(20,40),10,"red");
  
  boolean testIShapeArea(Tester t) {
    return t.checkInexact(this.c1.area(), 314.15,0.01) 
        && t.checkInexact(this.s1.area(), 900.0, 0.01);
  }
  
  boolean testSquareIsBiggerThan(Tester t) {
    return
    t.checkExpect(this.s1.isBiggerThan(this.s2), false) &&
    t.checkExpect(this.s2.isBiggerThan(this.s1), true) &&
    t.checkExpect(this.s1.isBiggerThan(this.c1), true) &&
    t.checkExpect(this.s3.isBiggerThan(this.c1), false);
  }

  boolean testCircleIsBiggerThan(Tester t) {
    return
    t.checkExpect(this.c1.isBiggerThan(this.c2), false) &&
    t.checkExpect(this.c2.isBiggerThan(this.c1), true) &&
    t.checkExpect(this.c1.isBiggerThan(this.s1), false) &&
    t.checkExpect(this.c1.isBiggerThan(this.s3), true);
  }
  boolean testSquareGrow(Tester t) {
    return
    t.checkExpect(this.s1.grow(20), this.s2);
  } 
   
}


