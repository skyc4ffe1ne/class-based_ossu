interface IceCream
{
  
}


class EmptyServing implements IceCream
{
  boolean cone;
  EmptyServing (boolean cone){
    this.cone = cone;
  }
}


class Scooped implements IceCream
{
  IceCream more;
  String flavor;
  
  Scooped(IceCream more, String flavor)
  {
    this.more = more;
    this.flavor = flavor;
  }
}

class ExampleIceCreams {
  ExampleIceCreams(){}

  IceCream empty  = new EmptyServing(false);
  IceCream scoop0 = new Scooped(empty, "mint chip");
  IceCream scoop1 = new Scooped(scoop0, "coffee");
  IceCream scoop2 = new Scooped(scoop1, "black raspberry");
  
  IceCream emptyCone  = new EmptyServing(true);
  IceCream scoop3 = new Scooped(emptyCone, "mint chip");
  IceCream scoop4 = new Scooped(emptyCone, "coffee");
  IceCream scoop5 = new Scooped(emptyCone, "black raspberry");
  
}