// Problems 18.1 - 18.4 on page 225

// Exercise 18.1

//// Exercise 18.1.1

////// Time representation for Schedules 
class Time {
  int hour;
  int minutes;
  
  Time(int hour, int minutes){
    this.hour = hour;
    this.minutes = minutes;
  }
}

////// Represents the departure and arrival time of the train
class Schedules {
 Time departure;
 Time arrival;
 
 Schedules(Time departure, Time arrival)
 {
   this.departure = departure;
   this.arrival = arrival;
 }
 
}
////// Represents the city of departure and arrival of the train
class Route{
  String cityDeparture;
  String cityArrival;
  
  Route(String cityDeparture, String cityArrival)
  {
    this.cityDeparture = cityDeparture; 
    this.cityArrival = cityArrival; 
  }
}


class Train{
  Schedules s;
  Route r;
  
  Train(Schedules s, Route r)
  {
    this.s = s;
    this.r = r;
  }
}

interface IStop{
  
}

class ConsLoStop implements IStop{
  String first;
  IStop rest;
  
 ConsLoStop(String first, IStop rest)
 {
   this.first = first;
   this.rest = rest;
 }

}

class MtLoStop implements IStop{
 MtLoStop(){}
}

class ExpressTrain extends Train{
  IStop st;
  String name;
  
  ExpressTrain(Schedules s, Route r, IStop st, String name)
  {
    super(s,r);
    this.st = st;
    this.name = name;
  }

}

//// Exercise 18.1.2

class Place {
  String name;
  String location;
  
  Place(String name, String location){
    this.name = name;
    this.location = location;
  }

}

class Restaurant {
  String name;
  String price;
  Place place;
  
  Restaurant(String name, String price, Place place){
    this.name = name;
    this.price = price;
    this.place = place;
  }
}

class ChineseRestaurant extends Restaurant {
  boolean usesMSG;
  
  ChineseRestaurant(String name, String price, Place place, boolean usesMSG){
   super(name,price,place); 
   this.usesMSG = usesMSG; 
  }
}

//// Exercise 18.1.3

class Vehicle{
  int mileage;
  int price;
  
  Vehicle(int mileage, int price)
  {
    this.mileage = mileage;
    this.price = price;
  }
}

class Sedan extends Vehicle{
  Sedan(int mileage, int price)
  {
    super(mileage,price);
  }
}


// Exercise 18.2
//Abstract over the common fields of Lion, Snake, and Monkey 
//in the class diagram of figure 14 (page 32).
interface IZooAnimal{}

class ZooAnimal implements IZooAnimal {
  String name;
  int weight;
  
  ZooAnimal(String name,int weight)
  {
    this.name = name;
    this.weight = weight;
  }
}

class Lion extends ZooAnimal{
  int meat;
  
  Lion(String name, int weight, int meat){
    super(name,weight);
    this.meat = meat;
  }
}

class Snake extends ZooAnimal{
  int length;
  
  Snake(String name, int weight, int length){
    super(name,weight);
    this.length = length;
  }
}

class Monkey extends ZooAnimal{
  String food;
  
  Monkey(String name, int weight, String food){
    super(name,weight);
    this.food = food;
  }
}

// Exercise 18.3
// Abstract over the common fields in the class diagram of figure 16 (page 34). 

interface ITaxiVehicle{}

class TaxiVehicle implements ITaxiVehicle
{
  int idNum;
  int passengers;
  int pricePerMile;
  
  TaxiVehicle(int idNum, int passengers, int pricePerMile){
    this.idNum = idNum; 
    this.passengers = passengers; 
    this.pricePerMile = pricePerMile; 
  }
}

class Cab extends TaxiVehicle
{
  Cab(int idNum, int passengers, int pricePerMile){
    super(idNum,passengers,pricePerMile);
  }
}

class Limo extends TaxiVehicle
{
  int minRental;

  Limo(int idNum, int passengers, int pricePerMile,int minRental){
    super(idNum,passengers,pricePerMile);
    this.minRental = minRental; 
  }
}

class Van extends TaxiVehicle
{
  boolean access;

  Van(int idNum, int passengers, int pricePerMile, boolean access){
    super(idNum,passengers,pricePerMile);
    this.access = access;
  }
}
