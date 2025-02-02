import tester.*;

interface IComposite {
  // *Constants*
  double DELTA = 0.01;

  // *Methods*
  // Calculate the area of ​​the shape
  double area();
  // Calculate the distance of the shape from the origin
  double distTo0();
  // Check if a point *p* is inside the shape  
  boolean in(CartPT p);
  // Return the "bounding box", the smallest rectangle that contains the shape
  BoundingBox bb();
  // determines whether this shape is of equal size as some other
  boolean same(IComposite that);
  // determines whether this shape is closer to the origin than some other
  boolean closerTo(IComposite that);
}

class BoundingBox
{
 int left; 
 int right; 
 int top; 
 int bottom; 
 
 BoundingBox(int left, int right, int top, int bottom){
   this.left = left;
   this.right = right;
   this.top = top;
   this.bottom = bottom;
 }

  BoundingBox combine(BoundingBox that) {
    return new BoundingBox(
        Math.min(this.left,that.left),
        Math.max(this.right,that.right),
        Math.min(this.top,that.top),
        Math.max(this.bottom,that.bottom)
        );
  }
}


class CartPT {
  int posX;
  int posY;
  
  CartPT(int posX, int posY){
    this.posX = posX;
    this.posY = posY;
  }

  // calculate the distance between this point and another point p  
  double distanceTo(CartPT p) {
      return Math.sqrt(
          Math.pow(this.posX - p.posX, 2) +
          Math.pow(this.posY - p.posY, 2)
      );
  }
  
  
  double distTo0() {
    return Math.sqrt(this.posX * this.posX + this.posY * this.posY);
  }
}

class Square implements IComposite{
  CartPT loc;
  int size;
  
  Square(CartPT loc, int size)
  {
    this.loc = loc;
    this.size = size;
  }
  
  
  public double area() 
  {
    return this.size * this.size;
  }

  public double distTo0()
  {
    return this.loc.distTo0(); 
  }
  
  public boolean in(CartPT p)
  {
   return this.loc.posX < p.posX && this.loc.posX + this.size > p.posX
       && this.loc.posY < p.posY && this.loc.posY + this.size > p.posY;
  }
  
  public BoundingBox bb() {
    return new BoundingBox(this.loc.posX,this.loc.posX + this.size, this.loc.posY, this.loc.posY + this.size);
  }
  
  public boolean same(IComposite that)
  {
    return Math.abs(this.area() - that.area()) <= DELTA;
  }
  
  public boolean closerTo(IComposite that)
  {
    return this.distTo0() <= that.distTo0();
  }
}


class Circle implements IComposite{
  CartPT loc;
  int radius;
  
  Circle(CartPT loc, int radius)
  {
    this.loc = loc;
    this.radius = radius;
  }
  
  public double area() 
  {
    return Math.PI * this.radius * this.radius;
  }

  public double distTo0()
  {
    return this.loc.distTo0() - this.radius;
  }
  
  public boolean in(CartPT p)
  {
   return this.loc.distanceTo(p) <= this.radius;
  }
    
  public BoundingBox bb() {
    return new BoundingBox(this.loc.posX - this.radius, this.loc.posX + this.radius, this.loc.posY - this.radius, this.loc.posY + this.radius);
  } 
  
  public boolean same(IComposite that)
  {
    return Math.abs(this.area() - that.area()) <= DELTA;
  }
  
  public boolean closerTo(IComposite that)
  {
    return this.distTo0() <= that.distTo0();
  }
}

class SuperImp implements IComposite{
  IComposite top;
  IComposite bottom;
  
  SuperImp(IComposite top, IComposite bottom)
  {
    this.top = top;
    this.bottom = bottom;
  }
  
  public double area()
  {
    return this.top.area() + this.bottom.area();
  }

  public double distTo0()
  {
    return Math.min(this.bottom.distTo0(), this.top.distTo0());
  }

  public boolean in(CartPT p) {
    return this.top.in(p) || this.bottom.in(p);
  }
  
  public BoundingBox bb() {
    return this.top.bb().combine(this.bottom.bb());
  } 
 
  public boolean same(IComposite that)
  {
    return Math.abs(this.area() - that.area()) <= DELTA;
  }
  
  public boolean closerTo(IComposite that)
  {
    return this.distTo0() <= that.distTo0();
  }
}


class ExamplesComposite {
  ExamplesComposite(){}

  CartPT p_0 = new CartPT(0, 0);
  CartPT p_1 = new CartPT(3, 4);
  CartPT p_2 = new CartPT(5, 5);

 
  IComposite s_0 = new Square(new CartPT(40, 30),  40);
  IComposite s_1 = new Square(new CartPT(120, 50), 50);
  IComposite c_0 = new Circle(new CartPT(50, 120), 20);
  IComposite c_1 = new Circle(new CartPT(30, 40),  20);
  
  IComposite u_0 = new SuperImp(s_0, s_1);
  IComposite u_1 = new SuperImp(s_0, c_1);
  IComposite u_2 = new SuperImp(c_0, u_0);
  IComposite u_3 = new SuperImp(u_2, u_1);
  
  
    boolean testArea(Tester t) {
    return
        t.checkInexact(s_0.area(), 1600.0, 0.01)
        &&
        t.checkInexact(s_1.area(), 2500.0, 0.01)
        &&
        t.checkInexact(c_0.area(), 1256.0, 0.01)
        &&
        t.checkInexact(c_1.area(), 1256.0, 0.01)
        ;
  }
 
  boolean testIn(Tester t) {
    return
        t.checkExpect(u_0.in(new CartPT(42, 42)), true)
        &&
        t.checkExpect(u_1.in(new CartPT(45, 40)), true)
        &&
        t.checkExpect(u_2.in(new CartPT(20, 5)),  false)
        ;
  }
 
 
  boolean testDistTo0(Tester t) {
    return
        t.checkInexact(s_0.distTo0(), 50.0, 0.01)
        &&
        t.checkInexact(c_0.distTo0(), 110.0, 0.01)
        &&
        t.checkInexact(c_1.distTo0(), 30.0, 0.01)
        ;
  }
  
  boolean testBB(Tester t) {
    return
        t.checkExpect(s_0.bb(), new BoundingBox(40, 80, 30, 70))
        &&
        t.checkExpect(s_1.bb(), new BoundingBox(120, 170, 50, 100))
        &&
        t.checkExpect(c_0.bb(), new BoundingBox(30, 70, 100, 140))
        &&
        t.checkExpect(c_1.bb(), new BoundingBox(10, 50, 20, 60))
        ;
  }
  
  boolean testSame(Tester t)
  {
    IComposite s_2 = new Square(new CartPT(10, 10), 40); 
    
    IComposite u_4 = new SuperImp(s_0, c_0);
    IComposite u_5 = new SuperImp(s_2, c_1);

    return 
        t.checkExpect(s_0.same(s_1), false)
        &&
        t.checkExpect(c_0.same(c_1), true)
        &&
        t.checkExpect(s_0.same(s_0), true) 
        &&
        t.checkExpect(u_0.same(u_1), false)
        &&
        t.checkExpect(u_4.same(u_5), true)
        ;
  }
  
  boolean testCloserTo(Tester t) {
    return
        t.checkExpect(s_0.closerTo(s_1), true)
        &&
        t.checkExpect(s_1.closerTo(c_0), false)
        &&
        t.checkExpect(c_0.closerTo(c_1), false)
        &&
        t.checkExpect(u_1.closerTo(u_2), true)
        &&
        t.checkExpect(u_2.closerTo(u_3), false)
        &&
        t.checkExpect(u_1.closerTo(u_2), true)
        ;
  }
  
}

