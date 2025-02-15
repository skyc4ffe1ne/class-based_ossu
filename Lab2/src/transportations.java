// Represents a mode of transportation
interface IMOT {
  boolean isMoreFuelEfficientThat(int mpg);
}
 
// Represents a bicycle as a mode of transportation
class Bicycle implements IMOT {
  String brand;
 


  Bicycle(String brand) {
    this.brand = brand;
  }

   public boolean isMoreFuelEfficientThat(int mpg){
    return true;
  }
  

}
 
// Represents a car as a mode of transportation
class Car implements IMOT {
  String make;
  int mpg; // represents the fuel efficiency in miles per gallon
 
  Car(String make, int mpg) {
    this.make = make;
    this.mpg = mpg;
  }
  
    public boolean isMoreFuelEfficientThat(int mpg){
    return this.mpg >= mpg;
  }
 
}
 
// Keeps track of how a person is transported
class Person_1 {
  String name;
  IMOT mot;
 
  Person_1(String name, IMOT mot) {
    this.name = name;
    this.mot = mot;
  }

  public boolean isMoreFuelEfficientThat(int mpg){
    return this.mot.isMoreFuelEfficientThat(mpg);
  }

}

class ExamplesPerson_1 {
  IMOT diamondback = new Bicycle("Diamondback");
  IMOT toyota = new Car("Toyota", 30);
  IMOT lamborghini = new Car("Lamborghini", 17);
 
  Person_1 bob = new Person_1("Bob", diamondback);
  Person_1 ben = new Person_1("Ben", toyota);
  Person_1 becca = new Person_1("Becca", lamborghini);
  
}