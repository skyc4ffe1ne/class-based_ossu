import tester.Tester;



interface IPicture{
 int getWidth();
 int countShape();
 int comboDepth();
 IPicture mirror(); 
 String pictureRecipe(int depth);
}

class Shape implements IPicture{
  // string describing what simple shape it is
 String kind; 
 int size;
 
 Shape(String kind, int size){
  this.kind = kind; 
  this.size = size; 
 }

 public int getWidth() {
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
 String operation;
 IPicture top; 
 IPicture bottom; 
 
 
Combo(String name, String operation, IPicture top, IPicture bottom){
 this.name = name; 
 this.operation = operation; 
 this.top  = top; 
 this.bottom = bottom; 
}

 public int getWidth()
 {
   if(this.operation.equals("Beside")) {
    return this.top.getWidth() + this.bottom.getWidth();
   }else {
     if(this.operation.equals("Scale"))
     {
       return this.top.getWidth() * 2;
     }else {
       return Math.max(this.top.getWidth(), this.bottom.getWidth());
     }
   }
   
 }
 
 public int countShape() {
   if(this.operation.equals("Scale"))
   {
     return 0 + this.top.countShape(); 
   }else {
     return 0 + this.top.countShape() + this.bottom.countShape();
   }
 }

 public int comboDepth() {
   if(this.operation.equals("Scale"))
   {
     return 1 + this.top.comboDepth(); 
   }else {
     return 1 + Math.max(this.top.comboDepth(), this.bottom.comboDepth());
   }
 }
 
 
 public IPicture mirror(){
  if(this.operation.equals("Beside")) {
    return new Combo(this.name,this.operation,this.bottom.mirror(),this.top.mirror());
  }else {
    return this;
  }
 }
 
 
 public String pictureRecipe(int depth) {
   if (depth <= 0) {
       return this.name;
   }else {
     if(this.operation.equals("Scale"))
     {
       return this.operation + "(" + this.top.pictureRecipe(depth - 1) + ")";
     }else {
       return this.operation + "(" + this.bottom.pictureRecipe(depth - 1) + ", " + this.top.pictureRecipe(depth - 1) + ")";
     }
   }
 }

}

class ExamplesPicture{
  ExamplesPicture(){}
  
  // Pictures
  IPicture c1 = new Shape("circle", 20);
  IPicture s1 = new Shape("square", 30);
  
  IPicture scaleExample = new Combo("big circle", "Scale", c1, null); 

  IPicture overlayExample = new Combo("A square on a circle", "Overlay", scaleExample, s1);

  IPicture besideExample = new Combo("doubled square on circle", "Beside", overlayExample, overlayExample);

  boolean testGetWidth(Tester t)
 {
    return
        t.checkExpect(c1.getWidth(), 20)
        &&
        t.checkExpect(s1.getWidth(), 30)
        &&
        t.checkExpect(overlayExample.getWidth(), 40)
        &&
        t.checkExpect(besideExample.getWidth(), 80)
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
        t.checkExpect(overlayExample.comboDepth(), 2)
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
        t.checkExpect(overlayExample.mirror(), new Combo("A square on a circle", "Overlay", scaleExample, s1))
        &&
        t.checkExpect(besideExample.mirror(), new Combo("doubled square on circle", "Beside", overlayExample, overlayExample))
        ;
 }

 
 boolean testPictureRecipe(Tester t)
 {
    return
        t.checkExpect(besideExample.pictureRecipe(0), "doubled square on circle")
        &&
        t.checkExpect(besideExample.pictureRecipe(2), "Beside(Overlay(square, big circle), Overlay(square, big circle))")
        &&
        t.checkExpect(besideExample.pictureRecipe(3), "Beside(Overlay(square, Scale(circle)), Overlay(square, Scale(circle)))")
        ;
 }

}

