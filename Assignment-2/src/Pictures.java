import tester.Tester;

interface IShape
{
 
}

class Shape 
{
  String kind;
  int size;
  
  Shape(String kind, int size)
  {
    this.kind = kind;
    this.size = size;
  }

  Shape scale() {
    return new Shape("A big " + this.kind,this.size * 2); 
  }
  
  int getWidth()
  {
    return this.size;
  }

  int helperGetWidth(Shape other)
  {
    if (this.size >= other.size)
    {
      return this.size;
    }else {
      return other.size;
    }
  }
  
  int count()
  {
    return 1;
  }

}



class Combo 
{
  String name;
  String operation;
  
  Combo(String name, String operation)
  {
    this.name = name;
    this.operation = operation;
  }
  
  String overlay(Shape picture1,Shape picture2)
  {
    return "A " + picture1.kind + " on " + picture2.kind;
  }

  String beside(Shape picture1,Shape picture2)
  {
    return "A " + "doubled " + picture1.kind + " on " + picture2.kind;
  }
  
  int getWidth(Shape picture1,Shape picture2)
  {
    return picture1.helperGetWidth(picture2); 
  }
  
  int countShapes(Shape picture1,Shape picture2)
  {
    if (this.name == "Beside")
    {
      return (picture1.count() + picture2.count()) * 2;
    }
    else
    {
      return picture1.count() + picture1.count(); 
    }
  }
  
  
  
}
 



class ExamplesPicture
{
  ExamplesPicture(){}
  
  Shape circle = new Shape("circle", 20);
  Shape square = new Shape("square", 30);
  
  Combo c_overlay =  new Combo("Overlay", "A shape on a shape");
  Combo c_beside  =  new Combo("Beside", "A doubled shape on a shape");
  
  boolean testScale(Tester t) {
   return
   t.checkExpect(this.circle.scale(), new Shape("A big circle",40))
   &&
   t.checkExpect(this.square.scale(), new Shape("A big square",60)); 
  }
  
 boolean testOverlay(Tester t) {
   return
   t.checkExpect(this.c_overlay.overlay(this.square,this.circle), "A square on circle");
  }

 boolean testBeside(Tester t) {
   return
   t.checkExpect(this.c_beside.beside(this.square,this.circle), "A doubled square on circle");
  }
 
 boolean testWidth(Tester t) {
   return
   t.checkExpect(this.circle.getWidth(), 20)
   &&
   t.checkExpect(this.square.getWidth(), 30)
   &&
   t.checkExpect(this.c_beside.getWidth(this.square,this.circle), 30)
   &&
   t.checkExpect(this.c_overlay.getWidth(this.square,this.circle), 30)
   ;
  }

 boolean testCount(Tester t) {
   return
   t.checkExpect(this.circle.count(), 1)
   &&
   t.checkExpect(this.square.count(), 1)
   &&
   t.checkExpect(this.c_beside.countShapes(this.square,this.circle), 4)
   &&
   t.checkExpect(this.c_overlay.countShapes(this.square,this.circle), 2)
   ;
  }

}