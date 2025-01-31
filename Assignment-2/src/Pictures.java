import tester.Tester;



interface IPicture{
 IPicture scale(); 
 double getWidth();
 int countShape();
 int comboDepth();
 IPicture mirror(); 
 String pictureRecipe(int depth);
}

class Shape implements IPicture{
  // string describing what simple shape it is
 String kind; 
 double size;
 
 Shape(String kind, double size){
  this.kind = kind; 
  this.size = size; 
 }
 
 
 public IPicture scale()
 {
   if(this.kind.startsWith("b")) {
   return new Shape(this.kind, this.size * 2);
   }else {
   return new Shape("big " + this.kind, this.size * 2);
   }
 }
 

 public double getWidth() {
   return this.size;
 }
 
 public int countShape() {
     return 1;
 }

 public int comboDepth() {
     return 0;
 }

 public IPicture mirror()
 {
   return this;
 }

 public String pictureRecipe(int depth) {
  return this.kind; 
 }

 String combine(String other) {
   return this.kind + other;
 }

}


/*
A Combo consists of a
name describing the resulting picture,
and an operation describing how this image was put together.
*/
class Combo implements IPicture{
  // string describing what simple shape it is
 String name;
 Operation operation;
 IPicture top; 
 IPicture bottom; 
 
 
Combo(String name, Operation operation, IPicture top, IPicture bottom){
 this.name = name; 
 this.operation = operation; 
 this.top  = top; 
 this.bottom = bottom; 
}

 public IPicture scale()
 {
   return new Combo(this.name, this.operation.scale(), this.top.scale(),this.bottom.scale());
 }

 public double getWidth()
 {
   if(this.operation.kind == "Beside") {
    return this.top.getWidth() + this.bottom.getWidth();
   }else {
      if(this.top.getWidth() >= this.bottom.getWidth()) {
       return this.top.getWidth(); 
     }else {
       return this.bottom.getWidth();
     }
   }
   
 }
 
 
 public int countShape() {
     return 0 + this.top.countShape() + this.bottom.countShape();
 }

 public int comboDepth() {
     return 1 + this.top.comboDepth() + this.bottom.comboDepth();
 }
 
 
 public IPicture mirror(){
  if(this.operation.kind == "Beside") {
    return new Combo(this.name,this.operation.mirror(),this.bottom,this.top);
  }else {
    return this;
  }
 }
 
 
 public String pictureRecipe(int depth) {
   if (depth < 0) {
       return this.name;
   }else {
       return this.operation.kind + "(" + this.bottom.pictureRecipe(depth - 1) + ", " + this.top.pictureRecipe(depth - 1) + ")";
   }
 }
 
 


}

class Operation {
  String kind;
  String description;
  
  Operation(String kind, String description){
    this.kind = kind;
    this.description = description;
  }
  
  
  Operation scale(){
   return new Operation("Scale",   "takes a single picture and draws it twice as large");
  }
  
  Operation beside(){
   return new Operation("Beside",  "takes two pictures, and draws picture1 to the left of picture2");
  }

  Operation overlay(){
   return new Operation("Overlay", "takes two pictures, and draws top-picture on top of bottom-picture, with their centers aligned");
  }
  
  Operation mirror()
  {
    return new Operation("Mirror", "takes a two picture and draws it top-picture and bottom-picture flipped");
  }

}

class ExamplesPicture{
  ExamplesPicture(){}
  
  
  // Operations 
  Operation scale_0   = new Operation("Scale",   "takes a single picture and draws it twice as large");
  Operation beside_0  = new Operation("Beside",  "takes two pictures, and draws picture1 to the left of picture2");
  Operation overlay_0 = new Operation("Overlay", "takes two pictures, and draws top-picture on top of bottom-picture, with their centers aligned");
  Operation mirror_0  = new Operation("Mirror",  "takes a two picture and draws it top-picture and bottom-picture flipped");

  // Pictures
  IPicture c1 = new Shape("circle", 20.0);
  IPicture s1 = new Shape("square", 30.0);
  
  IPicture scaleExample = c1.scale(); 

  IPicture overlayExample = new Combo("A square on a circle", overlay_0, scaleExample, s1);

  IPicture besideExample = new Combo("doubled square on circle", beside_0, overlayExample, overlayExample);

 boolean testScale(Tester t)
 {
   return 
     t.checkExpect(c1.scale(), 
         new Shape("big circle", 40.0))
     &&
     t.checkExpect(overlayExample.scale(),
         new Combo("A square on a circle", scale_0, new Shape("big circle", 80.0), new Shape("big square", 60)))
     ; 
 }
 
 
  boolean testGetWidth(Tester t)
 {
    return
        t.checkInexact(c1.getWidth(), 20.0, 0.01)
        &&
        t.checkInexact(s1.getWidth(), 30.0, 0.01)
        &&
        t.checkInexact(overlayExample.getWidth(), 40.0, 0.01)
        &&
        t.checkInexact(besideExample.getWidth(), 80.0, 0.01)
        ;
 }
  
  
 boolean testCountShape(Tester t)
 {
    return
        t.checkExpect(c1.countShape(), 1)
        &&
        t.checkExpect(s1.countShape(), 1)
        &&
        t.checkExpect(overlayExample.countShape(), 2)
        &&
        t.checkExpect(besideExample.countShape(), 4)
        ;
 }

 boolean testComboDepth(Tester t)
 {
    return
        t.checkExpect(c1.comboDepth(), 0)
        &&
        t.checkExpect(s1.comboDepth(), 0)
        &&
        t.checkExpect(overlayExample.comboDepth(), 1)
        &&
        t.checkExpect(besideExample.comboDepth(), 3)
        ;
 }

 boolean testMirror(Tester t)
 {
    return
        t.checkExpect(c1.mirror(), c1)
        &&
        t.checkExpect(s1.mirror(), s1)
        &&
        t.checkExpect(overlayExample.mirror(), new Combo("A square on a circle", overlay_0, new Shape("big circle", 40.0), new Shape("square", 30)))
        &&
        t.checkExpect(besideExample.mirror(), new Combo("doubled square on circle", mirror_0, overlayExample, overlayExample))
        ;
 }

 
 boolean testPictureRecipe(Tester t)
 {
    return
//        t.checkExpect(besideExample.pictureRecipe(0), "doubled square on circle")
//        &&
//        t.checkExpect(besideExample.pictureRecipe(2), "Beside(Overlay(square, big circle), Overlay(square, big circle))")
//        &&
        t.checkExpect(besideExample.pictureRecipe(3), "Beside(Overlay(square, scale(circle)), Overlay(square, scale(circle)))")
        ;
 }

}

