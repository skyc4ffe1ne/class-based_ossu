import tester.*;                // The tester library
import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.funworld.*;      // the abstract World class and the big-bang library
import javalib.worldcanvas.WorldCanvas;

import java.awt.Color;          // general colors (as triples of red,green,blue values)
                                // and predefined colors (Color.RED, Color.GRAY, etc.)


class MyPosn extends Posn {
  
  // standard constructor
  MyPosn(int x, int y) {
    super(x, y);
  }
 
  // constructor to convert from a Posn to a MyPosn
  MyPosn(Posn p) {
    this(p.x, p.y);
  }
  
  MyPosn add(MyPosn that) {
    return new MyPosn(this.x + that.x ,this.y + that.y );
  }
  
  boolean isOffScreen(int widthScreen, int heightScreen ) {
    if(this.x < 0 || this.x > widthScreen || this.y > heightScreen || this.y < 0) {
      return true;
    }else {
      return false;
    }
  }
}

class Circle {
  MyPosn position; // in pixels
  MyPosn velocity; // in pixels/tick
  int size; 
  Color color; 
  
  Circle(MyPosn position, MyPosn velocity,int size, Color color){
    this.position = position;
    this.velocity = velocity;
    this.size = size;
    this.color = color;
  }
  
  Circle move() {
    return new Circle(this.position.add(this.velocity), this.velocity, this.size, this.color);
  }
  
  boolean isOffScreen(int widthScreen, int heightScreen) {
    return this.position.isOffScreen(widthScreen, heightScreen);
  }
  
  WorldImage draw() {
    return  new CircleImage(this.size, OutlineMode.SOLID, this.color);
  }
  
  WorldScene place(WorldScene s) {
    return s.placeImageXY(this.draw(), this.position.x, this.position.y);
  }
}

interface ILoCircle{
  ILoCircle moveAll(); 
  ILoCircle removeOffScreen(int widthScreen, int heightScreen);
  WorldScene placeAll(WorldScene s); 
}

class ConsLoCircle implements ILoCircle{
  Circle first; 
  ILoCircle rest; 
  
  ConsLoCircle(Circle first, ILoCircle rest){
   this.first = first; 
   this.rest = rest; 
  }
  
  public ILoCircle moveAll() {
    return new ConsLoCircle(this.first.move(), this.rest.moveAll());
  }
  
  public ILoCircle removeOffScreen(int widthScreen, int heightScreen){
    if(this.first.isOffScreen(widthScreen, heightScreen)) {
      return this.rest.removeOffScreen(widthScreen, heightScreen);
    }else {
      return new ConsLoCircle(this.first, this.rest.removeOffScreen(widthScreen, heightScreen));
    }
  }
  
  public WorldScene placeAll(WorldScene s) {
    return this.rest.placeAll(this.first.place(s));
  }

}

class MtLoCircle implements ILoCircle{
  MtLoCircle(){}

  public ILoCircle moveAll() {
    return this;
  }
  
  public ILoCircle removeOffScreen(int widthScreen, int heightScreen) {
    return this;
  }

  public WorldScene placeAll(WorldScene s) {
    return s;
  }
}

class MyWorld extends World {
 int width, height;
 ILoCircle circles;

  MyWorld(int width, int height, ILoCircle circles){
    this.width = width;
    this.height = height;
    this.circles = circles;
  }

  public WorldScene makeScene(){
    return circles.placeAll(new WorldScene(500,500));
  }
  
  public WorldEnd worldEnds() {
    if(false) {
      return new WorldEnd(true, this.makeScene());
    }else {
      return new WorldEnd(false, this.makeScene());
    }
  }
  
   public World onMouseClicked(Posn pos) {
     Circle anotherCircle =  new Circle(new MyPosn(pos),new MyPosn(0,5), 40, Color.YELLOW);
     ILoCircle addCircle = new ConsLoCircle(anotherCircle, this.circles); 
     return new MyWorld(this.width, this.height, addCircle);
   } 
   
   public World onTick() {
     return new MyWorld(this.width, this.height, this.circles.moveAll()); 
   }  

   public boolean bigBang() {
     return bigBang(width, height, 1.0/28.0);
   }
  
}

class ExamplesGame{
  ExamplesGame(){}

  MyPosn p_0 = new MyPosn(100,100);
  MyPosn p_1 = new MyPosn(150,250);
  MyPosn p_2 = new MyPosn(10,50);
  MyPosn p_3 = new MyPosn(48,29);

  MyPosn p_0_velocity = new MyPosn(5,0);
  MyPosn p_1_velocity = new MyPosn(0,5);
  MyPosn p_2_velocity = new MyPosn(7,0);
  MyPosn p_3_velocity = new MyPosn(0,7);

  
  Circle c_0 = new Circle(p_0,p_0_velocity, 30, Color.RED);
  Circle c_1 = new Circle(p_1,p_1_velocity, 40, Color.BLUE);
  Circle c_2 = new Circle(p_2,p_2_velocity, 50, Color.GREEN);
  Circle c_3 = new Circle(p_3,p_3_velocity, 60, Color.CYAN); 

  
  
  ILoCircle emptyList = new MtLoCircle();

  ILoCircle loC_0 = 
      new ConsLoCircle(c_0,
          new ConsLoCircle(c_1, 
              emptyList)); 
  
  ILoCircle loC_1 = 
      new ConsLoCircle(c_0,
          new ConsLoCircle(c_1, 
              new ConsLoCircle(c_2,
                  emptyList))); 

  ILoCircle loC_2 = 
      new ConsLoCircle(c_0,
          new ConsLoCircle(c_1, 
              new ConsLoCircle(c_2,
                  new ConsLoCircle(c_3,
                      emptyList)))); 
 
  // Test library
  boolean testImages(Tester t) {
    return t.checkExpect(new RectangleImage(30, 20, OutlineMode.SOLID, Color.GRAY),
                         new RectangleImage(30, 20, OutlineMode.SOLID, Color.GRAY));
  }
  
 // Test method add 
  boolean testAdd(Tester t) {
    return
        t.checkExpect(p_0.add(p_1), new MyPosn(250, 350)) 
        &&
        t.checkExpect(p_2.add(p_3), new MyPosn(58, 79)) 
        ;
  }

 // Test method isOffScreen 
  boolean testIsOffScreen(Tester t) {
    return
        t.checkExpect(p_0.isOffScreen(50, 50), true) 
        &&
        t.checkExpect(p_1.isOffScreen(50, 50), true) 
        &&
        t.checkExpect(p_2.isOffScreen(100, 100), false) 
        &&
        t.checkExpect(p_3.isOffScreen(100, 100), false) 
        &&
        t.checkExpect(c_0.move().isOffScreen(100, 100), true) 
        &&
        t.checkExpect(c_0.isOffScreen(100, 100), false) 
        &&
        t.checkExpect(c_1.isOffScreen(300, 300), false) 
        &&
        t.checkExpect(c_2.isOffScreen(20, 20), true) 
        &&
        t.checkExpect(c_3.isOffScreen(20, 20), true) 
        ;
  }

 // Test method move 
  boolean testMove(Tester t) {
    return
        t.checkExpect(c_0.move(), new Circle(new MyPosn(105, 100), p_0_velocity, c_0.size, c_0.color))
        &&
        t.checkExpect(c_1.move(), new Circle(new MyPosn(150, 255), p_1_velocity, c_1.size, c_1.color)) 
        &&
        t.checkExpect(c_2.move(), new Circle(new MyPosn(17, 57), p_2_velocity, c_2.size, c_2.color)) 
        &&
        t.checkExpect(c_3.move(), new Circle(new MyPosn(48, 36), p_3_velocity, c_3.size, c_3.color)) 
        ;
  }
  
  boolean testMoveAll(Tester t) {
  return
      t.checkExpect(loC_0.moveAll(), 
          new ConsLoCircle(
              new Circle(new MyPosn(105, 100), p_0_velocity, c_0.size, c_0.color),
                new ConsLoCircle(new Circle(new MyPosn(150, 255), p_1_velocity, c_1.size, c_1.color),
                    emptyList)))
      &&
      t.checkExpect(loC_1.moveAll(), 
          new ConsLoCircle(
              new Circle(new MyPosn(105, 100), p_0_velocity, c_0.size, c_0.color),
                new ConsLoCircle(new Circle(new MyPosn(150, 255), p_1_velocity, c_1.size, c_1.color),
                    new ConsLoCircle(new Circle(new MyPosn(17, 57), p_2_velocity, c_2.size, c_2.color),
                        emptyList))))
      &&
      t.checkExpect(loC_2.moveAll(), 
          new ConsLoCircle(
              new Circle(new MyPosn(105, 100), p_0_velocity, c_0.size, c_0.color),
                new ConsLoCircle(new Circle(new MyPosn(150, 255), p_1_velocity, c_1.size, c_1.color),
                    new ConsLoCircle(new Circle(new MyPosn(17, 57), p_2_velocity, c_2.size, c_2.color),
                        new ConsLoCircle(new Circle(new MyPosn(48, 36), p_3_velocity, c_3.size, c_3.color),
                            emptyList)))))
      ;
  }
  
//  boolean testDrawCircle(Tester t) {
//    WorldCanvas c = new WorldCanvas(500, 500);
//    WorldScene s = new WorldScene(500, 500);
//
//    int ORIGINPOINTX = 500 / 2;
//    int ORIGINPOINTY = 500 / 2;
//
//    return 
//        c.drawScene(s.placeImageXY(c_0.draw(), ORIGINPOINTX, ORIGINPOINTY))
//        && c.show()
//       ;
//  } 
//  
//  boolean testPlace(Tester t) {
//    WorldCanvas c = new WorldCanvas(500, 500);
//    WorldScene s = new WorldScene(500, 500);
//
//    return 
//        c.drawScene(c_0.place(s))
//        && c.show()
//       ;
//  } 
//  
//  boolean testPlaceAll(Tester t) {
//    WorldCanvas c = new WorldCanvas(500, 500);
//    WorldScene s = new WorldScene(500, 500);
//
//    return 
//        c.drawScene(loC_2.placeAll(s))
//        && c.show()
//       ;
//  } 
  
    boolean testBigBang(Tester t) {
      MyWorld w = new MyWorld(500,500, loC_2);
      return w.bigBang();
    }

}
 