interface IMenu {}

class Soup implements IMenu{
 String name;
 int price;
 boolean vegetarian;
 
 
 Soup(String name, int price, boolean vegetarian){
   this.name = name;
   this.price = price;
   this.vegetarian = vegetarian;
 }

}


class Salad implements IMenu{
 String name;
 double price;
 boolean vegetarian;
 String dressed;
 
 
Salad(String name, double price, boolean vegetarian,String dressed){
   this.name = name;
   this.price = price;
   this.vegetarian = vegetarian;
   this.dressed = dressed;
 }

}

class Sandwich implements IMenu{
 String name;
 double price;
 String bread;
 String filling_1;  
 String filling_2;  
 
 
 Sandwich(String name, double price, String bread ,String filling_1, String filling_2){
   this.name = name;
   this.price = price;
   this.bread = bread;
   this.filling_1 = filling_1;
   this.filling_2 = filling_2;
 }

}

class Mergeplate implements IMenu{
 IMenu firstPlate;
 IMenu secondPlate;
 IMenu thirdPlate;
 
 Mergeplate(IMenu firstPlate, IMenu secondPlate,IMenu thirdPlate ) 
  {
   this.firstPlate = firstPlate;
   this.secondPlate = secondPlate;
   this.thirdPlate = thirdPlate;
  }
}

class ExamplesMenu
{
  IMenu plateSoup = new Soup("Zuppa",2,false);
  IMenu plateSalad = new Salad("Insalata",3.34,true,"Verza");
  IMenu plateSandwich = new Sandwich("Sandwich",2.99,"00","meat","tomato");
  
  IMenu firstMenu =  new Mergeplate(plateSoup,plateSalad,plateSandwich);
}