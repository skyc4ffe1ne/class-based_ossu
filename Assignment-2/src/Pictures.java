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

}


/*
A Combo consists of a
name describing the resulting picture,
and an operation describing how this image was put together.
*/
class Combo implements IPicture{
  // string describing what simple shape it is
 String name;
 IOperation operation;
 
 Combo(String name, IOperation operation){
   this.name = name; 
   this.operation = operation; 
 }

 
 public int getWidth()
 {
   return this.operation.getWidth();
 }
 
 public int countShape()
 {
   return this.operation.countShape();
 }

 
 public int comboDepth() {
   return this.operation.comboDepth();
 }
 
 public IPicture mirror()
 {
   return new Combo(this.name, this.operation.mirror());
 }

 public String pictureRecipe(int depth) {
   if(depth <= 0)
   {
     return this.name;
   }else {
     return this.operation.pictureRecipe(depth - 1); 
   }
 }

}



interface IOperation
{
  int getWidth();
  int countShape();
  int comboDepth();
  IOperation mirror();
  String pictureRecipe(int depth);
}

class Scale implements IOperation{
  IPicture top; 
  
  Scale(IPicture top)
  {
    this.top = top;
  }
  
  public int getWidth()
  {
    return this.top.getWidth() * 2;
  }
  
  public int countShape() {
    return 0 + this.top.countShape(); 
  }
  
  public int comboDepth() {
    return 1 + this.top.comboDepth(); 
  }

  public IOperation mirror(){
    return new Scale(this.top.mirror());
  }
  
  public String pictureRecipe(int depth) {
    return "Scale(" + this.top.pictureRecipe(depth) + ")";
  }

}

class Overlay implements IOperation{
  IPicture top; 
  IPicture bottom; 

  Overlay(IPicture top, IPicture bottom)
  {
    this.top = top;
    this.bottom = bottom;
  }

  public int getWidth()
  {
    return Math.max(this.top.getWidth(), this.bottom.getWidth());
  }
  
  public int countShape()
  {
   return 0 + this.top.countShape() + this.bottom.countShape();
  }
 
  public int comboDepth() {
    return 1 + Math.max(this.top.comboDepth(), this.bottom.comboDepth());
  } 
  
  public IOperation mirror()
  {
    return new Overlay(this.top.mirror(), this.bottom.mirror());
  }

  public String pictureRecipe(int depth) {
    return "Overlay(" + this.top.pictureRecipe(depth) + ", " + this.bottom.pictureRecipe(depth) + ")";
  }
 
}

class Beside implements IOperation{
  IPicture top; 
  IPicture bottom; 
  
  Beside(IPicture top, IPicture bottom)
  {
    this.top = top;
    this.bottom = bottom;
  }
  
  public int getWidth()
  {
    return this.top.getWidth() + this.bottom.getWidth();
  }

  public int countShape()
  {
    return 0 + this.top.countShape() + this.bottom.countShape();
  }
  
  public int comboDepth() {
    return 1 + Math.max(this.top.comboDepth(), this.bottom.comboDepth());
  } 
  
  public IOperation mirror(){
    return new Beside(this.bottom, this.top);
  }
  
  public String pictureRecipe(int depth) {
     return "Beside(" + this.top.pictureRecipe(depth) + ", " + this.bottom.pictureRecipe(depth) + ")";
  }

}



class ExamplesPicture{
  ExamplesPicture(){}
  
  // Shapes
  IPicture c_0 = new Shape("circle",20);
  IPicture s_0 = new Shape("square",30);

  // Operations and Combo
  IOperation scale_0 = new Scale(c_0); 
  IPicture scaleExample = new Combo("big circle", scale_0); 

  IOperation overlay_0 = new Overlay(s_0, scaleExample); 
  IPicture overlayExample = new Combo("A square on a circle", overlay_0);
  
  IOperation beside_0 = new Beside(overlayExample,overlayExample); 
  IPicture besideExample = new Combo("doubled square on circle", beside_0);
  
  
  IPicture circle1 = new Shape("circle", 20);
  IPicture square1 = new Shape("square", 20);
  IPicture bigCircle = new Combo("big circle", new Scale(circle1));
  IPicture squareOnCircle = new Combo("square on circle", new Overlay(square1, bigCircle));
  IPicture doubledSquareOnCircle = new Combo("doubled square on circle",
          new Beside(squareOnCircle, squareOnCircle));

  boolean testGetWidth(Tester t)
 {
    return
        t.checkExpect(c_0.getWidth(), 20)
        &&
        t.checkExpect(s_0.getWidth(), 30)
        &&
        t.checkExpect(overlayExample.getWidth(), 40)
        &&
        t.checkExpect(besideExample.getWidth(), 80)
        &&
        t.checkExpect(circle1.getWidth(), 20) && t.checkExpect(bigCircle.getWidth(), 40)
        && t.checkExpect(squareOnCircle.getWidth(), 40)
        && t.checkExpect(doubledSquareOnCircle.getWidth(), 80)
        ;
 }
  
 boolean testCountShape(Tester t)
 {
    return
        t.checkExpect(c_0.countShape(), 1)
        &&
        t.checkExpect(s_0.countShape(), 1)
        &&
        t.checkExpect(overlayExample.countShape(), 2)
        &&
        t.checkExpect(besideExample.countShape(), 4)
        &&
        t.checkExpect(circle1.countShape(), 1) && t.checkExpect(bigCircle.countShape(), 1)
        && t.checkExpect(squareOnCircle.countShape(), 2)
        && t.checkExpect(doubledSquareOnCircle.countShape(), 4)
        ;
 }

 boolean testComboDepth(Tester t)
 {
    return
        t.checkExpect(c_0.comboDepth(), 0)
        &&
        t.checkExpect(s_0.comboDepth(), 0)
        &&
        t.checkExpect(overlayExample.comboDepth(), 2)
        &&
        t.checkExpect(besideExample.comboDepth(), 3)
        &&
        t.checkExpect(circle1.comboDepth(), 0) && t.checkExpect(bigCircle.comboDepth(), 1)
        && t.checkExpect(squareOnCircle.comboDepth(), 2)
        && t.checkExpect(doubledSquareOnCircle.comboDepth(), 3)
        ;
 }

 boolean testMirror(Tester t)
 {

   IPicture overlayExampleTop = new Combo("A square on a circle", overlay_0);
   IPicture overlayExampleBottom = new Combo("A square on a circle", overlay_0);
   
   IOperation beside_1 = new Beside(overlayExampleTop,overlayExampleBottom);

    return
        t.checkExpect(overlayExample.mirror(), new Combo("A square on a circle", overlay_0))
        &&
        t.checkExpect(besideExample.mirror(), new Combo("doubled square on circle", beside_0))
        &&
        t.checkExpect(overlay_0.mirror(), overlay_0)
        &&
        t.checkExpect(beside_0.mirror(), new Beside(overlayExample,overlayExample))
        &&
        t.checkExpect(beside_1.mirror(), new Beside(overlayExampleBottom,overlayExampleTop))
        &&
        t.checkExpect(circle1.mirror(), circle1) && t.checkExpect(bigCircle.mirror(), bigCircle)
        && t.checkExpect(squareOnCircle.mirror(), squareOnCircle)
        && t.checkExpect(doubledSquareOnCircle.mirror(), doubledSquareOnCircle.mirror())
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
        &&
         t.checkExpect(circle1.pictureRecipe(0), "circle")
            && t.checkExpect(bigCircle.pictureRecipe(0), "big circle")
            && t.checkExpect(bigCircle.pictureRecipe(1), "Scale(circle)")
            && t.checkExpect(squareOnCircle.pictureRecipe(0), "square on circle")
            && t.checkExpect(squareOnCircle.pictureRecipe(1), "Overlay(square, big circle)")
            && t.checkExpect(squareOnCircle.pictureRecipe(2), "Overlay(square, Scale(circle))")
            && t.checkExpect(doubledSquareOnCircle.pictureRecipe(3),
                    "Beside(Overlay(square, Scale(circle)), Overlay(square, Scale(circle)))") 
        ;
 }

}

