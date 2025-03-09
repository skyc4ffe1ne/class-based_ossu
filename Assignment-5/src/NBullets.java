import tester.*;                // The tester library
import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.funworld.*;      // the abstract World class and the big-bang library
import javalib.worldcanvas.WorldCanvas;

import java.awt.Color;          // general colors (as triples of red,green,blue values)
                                // and predefined colors (Color.RED, Color.GRAY, etc.)
import java.util.Random;




//class Player{
//  int bulletLeft;
//  
//  Player(int bulletLeft){
//    this.bulletLeft = 10;
//  }
//  
//  int removeOneBllet() {
//    return this.bulletLeft - 1;
//  }
//  
//}


class MyPosn extends Posn{
  MyPosn(int x , int y){
    super(x,y);
  }
  
  MyPosn(Posn p){
    this(p.x, p.y);
  }
  
  MyPosn add(MyPosn that) {
    return new MyPosn(this.x + that.x ,this.y + that.y);
  }
  
  MyPosn remove(MyPosn that) {
    return new MyPosn(this.x - that.x ,this.y - that.y);
  }
  
  boolean isOffScreen(int widthScreen, int heightScreen ) {
    if(this.x <= 0 || this.x >= widthScreen || this.y >= heightScreen || this.y <= 0) {
      return true;
    }else {
      return false;
    }
  }

}


class Ship {
  MyPosn position;
  MyPosn velocity;
  int size;
  Color color;
  String direction;
  
  
  Ship(MyPosn position, MyPosn velocity, int size, String direction){
    this.position = position;
    this.velocity = velocity;
    this.size = size;
    this.color = Color.CYAN;
    this.direction = direction; 
  }
 
  WorldImage draw() {
    return new CircleImage(this.size, OutlineMode.SOLID, this.color);
  }
  
  WorldScene place(WorldScene s) {
    return s.placeImageXY(this.draw(), this.position.x, this.position.y);
  }
  
  Ship move() {
    if(this.direction.equals("left")) {
      return new Ship(this.position.remove(this.velocity), this.velocity, this.size, this.direction);
    }else {
      return new Ship(this.position.add(this.velocity), this.velocity, this.size, this.direction);
    }
  }
  
  boolean isOffScreen(int widthScreen, int heightScreen) {
    return this.position.isOffScreen(widthScreen, heightScreen);
  }
  
}

interface ILoShip {
  WorldScene placeAll(WorldScene s);
  ILoShip moveAll();
  ILoShip addShip();
  ILoShip removeAllOffScreen(int widthScreen, int heightScreen);
}

class ConsLoShip implements ILoShip {
 Ship first; 
 ILoShip rest;

 ConsLoShip(Ship first, ILoShip rest){
   this.first = first;
   this.rest = rest;
 }
 
 public WorldScene placeAll(WorldScene s) {
   if(this.first.isOffScreen(s.width, s.height)) {
     return this.rest.placeAll(s);
   }else {
     return this.rest.placeAll(this.first.place(s));
   }
 }
 
 public ILoShip moveAll() {
  return new ConsLoShip(this.first.move(), this.rest.moveAll());
 }
 
 public ILoShip addShip() {
  Random random = new Random();
  int randomPosY = random.nextInt(230 + (this.first.size * 2));
  int randomPosX = random.nextInt(480 + (this.first.size * 2));
  return new ConsLoShip(new Ship(new MyPosn(randomPosX > 250 ? 490 : 10 ,randomPosY), this.first.velocity, this.first.size, randomPosX > 250 ? "left" : "right"), this);
 }
 
 public ILoShip removeAllOffScreen(int widthScreen, int heightScreen) {
   if(this.first.isOffScreen(widthScreen, heightScreen)) {
     return this.rest.removeAllOffScreen(widthScreen, heightScreen);
   }else {
     return new ConsLoShip(this.first, this.rest.removeAllOffScreen(widthScreen, heightScreen));
   }
 }
 
}


class MtLoShip implements ILoShip{
  MtLoShip(){}

 public WorldScene placeAll(WorldScene s) {
  return s;
 }
 
 public ILoShip moveAll() {
  return this;
 }
  
 public ILoShip addShip() {
   return this;
 }
 
 public ILoShip removeAllOffScreen(int widthScreen, int heightScreen) {
   return this; 
 }

}

// Bullet designed in the world
class Bullet {
  
  MyPosn position;
  MyPosn velocity;
  int munitions; 
  int size;
  Color color;
  
  
  Bullet(MyPosn position, MyPosn velocity, int munitions, int size){
    this.position = position;
    this.velocity = velocity;
    this.munitions = munitions;
    this.size = size;
    this.color = Color.PINK;
  }

  WorldImage draw() {
     return new CircleImage(this.size, OutlineMode.SOLID, this.color); 
  }
  
  
  int getMunitions(){
    return this.munitions;
  }
  
//  WorldImage shot() {
//    this.munitions = this.munitions - 1;
//    return 
//  }

}

class MyWorld extends World {
  int width, height;
  Bullet bullets;
  //Ship ship;
  ILoShip ship;
  int tickCounter;
  
  
  MyWorld(int width, int height, Bullet bullets, ILoShip ship, int tickCounter){
    this.width = width;
    this.height = height;
    this.bullets = bullets;
    this.ship = ship;
    this.tickCounter = tickCounter;
  }
  
  
  public WorldScene makeScene(){
    return ship.placeAll(new WorldScene(width, height));
  }
  
//  public onKeyEvent(String key) {
//   if(key == " ") {
//     // Bullet .draw()
//
//
//   }
//  }
  
  public World onTick() {
    this.tickCounter = this.tickCounter + 1;
   
    // 28 ticks equal one second; Every second a new ship   
    if(this.tickCounter == 28) {
       this.tickCounter = 0;
       this.ship = ship.addShip(); 
    }
      return new MyWorld(this.width, this.height, bullets, ship.moveAll(), tickCounter); 
  }  

  public boolean bigBang() {
    return bigBang(width, height, 1.0/28.0);
  }
}


class ExamplesNBullets{
  ExamplesNBullets(){}
  int widthCanvas = 500;
  int heightCanvas = 300;

  int sizeBullet = 2;
  int munitions = 10;

  int sizeShip = heightCanvas * 1 / 30;
  
  int tickCounter = 0;

  MyPosn initialPosBullets = new MyPosn(widthCanvas, heightCanvas); 
  MyPosn velocityPosBullets = new MyPosn(0, 8); 
  
  int getVelocityShip = this.velocityPosBullets.y / 2;

  MyPosn velocityPosShip = new MyPosn(getVelocityShip, 0);
  MyPosn initialPosShip = new MyPosn(10, 150);

  Bullet bullets_0 = new Bullet(initialPosBullets , velocityPosBullets, munitions, sizeBullet); 

  Ship ship_0  = new Ship(initialPosShip, velocityPosShip, sizeShip, "right"); 
  Ship ship_1  = new Ship(new MyPosn(10,170), velocityPosShip, sizeShip, "right"); 
  Ship ship_2  = new Ship(new MyPosn(10,190), velocityPosShip, sizeShip, "right"); 

  ILoShip emptyShip = new MtLoShip();
  ILoShip loS_0 = 
      new ConsLoShip(ship_0,
          new ConsLoShip(ship_1,
              new ConsLoShip(ship_2, emptyShip)));
  
  boolean testBigBang(Tester t) {
    MyWorld w = new MyWorld(500,500, bullets_0, loS_0, tickCounter);
    return w.bigBang();
  }
  
 
  // Test method add 
  boolean testAdd(Tester t) {
   return
       t.checkExpect(this.initialPosShip.add(velocityPosShip), new MyPosn(14,150));
  }
  

  // Test method move 
  boolean testMove(Tester t) {
    return 
        t.checkExpect(ship_0.move(), new Ship(new MyPosn(14,150), new MyPosn(4,0), 10, "right"));
  }

  // Test method moveAll
  boolean testMoveAll(Tester t) {
    Ship movedShip = new Ship(new MyPosn(14,150), new MyPosn(4,0), 10, "right");
    Ship movedShip2 = new Ship(new MyPosn(14,170), new MyPosn(4,0), 10, "right");
    Ship movedShip3 = new Ship(new MyPosn(14,190), new MyPosn(4,0), 10, "right");
    return 
        t.checkExpect(loS_0.moveAll(), 
            new ConsLoShip(movedShip,
                new ConsLoShip(movedShip2,
                    new ConsLoShip(movedShip3,
                        emptyShip))));
  }
  
  // Test method addShip 
  boolean testAddShip(Tester t) {
    return 
        t.checkExpect(loS_0.addShip(), new ConsLoShip(ship_0, loS_0));
  }
  
  // Test method removeAllOffScreen
  boolean testRemoveAllOffScreen(Tester t) {
    ILoShip loS_1 = 
        new ConsLoShip(new Ship(new MyPosn(600,0), new MyPosn(4,0), 10, "left")
            , new ConsLoShip( new Ship(new MyPosn(100,100), new MyPosn(4,0), 10, "right"), emptyShip));
  
      ILoShip loS_2 = 
      new ConsLoShip(new Ship(new MyPosn(0,0), new MyPosn(4,0), 10, "right")
          , new ConsLoShip( new Ship(new MyPosn(0,100), new MyPosn(4,0), 10, "right"), emptyShip));
  return
        t.checkExpect(loS_0.removeAllOffScreen(500, 300), loS_0)
        &&
        t.checkExpect(loS_1.removeAllOffScreen(500, 300), new ConsLoShip( new Ship(new MyPosn(100,100), new MyPosn(4,0), 10, "right"), emptyShip))
        &&
        t.checkExpect(loS_2.removeAllOffScreen(500, 300),  emptyShip)
        ;
  }
}
