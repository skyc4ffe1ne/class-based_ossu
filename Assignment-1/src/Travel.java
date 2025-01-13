interface Housing {
  
}

class Hut implements Housing 
{
  // The population must be less than the capacity
  int capacity;
  int population;
  
  Hut(int capacity, int population)
  {
    this.capacity = capacity;
    this.population = population;
  }
}



class Inn implements Housing 
{
  // the population must be less than the capacity
  String name;
  int capacity;
  int population;
  int stalls;
  
  Inn(String name, int capacity, int population, int stalls)
  {
    this.name = name;
    this.capacity = capacity;
    this.population = population;
    this.stalls = stalls;
  }
}

class Castle implements Housing 
{
  // the population must be less than the capacity
  String name;
  String familyName;
  int population;
  int carriageHouse;
  
  
  Castle(String name, String familyname, int population, int carriageHouse)
  {
    this.name = name;
    this.familyName = familyname;
    this.population = population;
    this.carriageHouse = carriageHouse;
  }
}


interface Transporting
{
  
}

class Horse implements Transporting
{
  // They can only go to an inn if there is room in the stables,
  // but they can go to any hut or castle

 String name;
 String color;
 Housing from; 
 Housing to; 
 
 
 
 Horse(String name, String color, Housing from, Housing to)
 {
  this.name = name; 
  this.color = color; 
  this.from = from; 
  this.to = to; 
 }

}

class Carriage implements Transporting
{
  int tonnage;
  Housing from; //inns
  Housing to; // castle
  
  
  Carriage(int tonnage, Housing from, Housing to)
  {
    this.tonnage = tonnage;
    this.from = from;
    this.to = to;
  }

}


class ExamplesTravel 
{
  ExamplesTravel(){}
  
   Housing hovel = new Hut(5,1);
   Housing winterfell = new Castle("Winterfell","Stark",500,6);
   Housing crossroads = new Inn("Inn At The Crossroads", 40, 20, 12);

  
   Transporting horse0 = new Horse("Lorems", "black", this.hovel, this.crossroads);
   Transporting horse1 = new Horse("Rosed", "white", this.hovel, this.winterfell);

   Transporting carriage0 = new Carriage(50,this.crossroads, this.winterfell);
   Transporting carriage1 = new Carriage(30,this.hovel, this.crossroads);
}