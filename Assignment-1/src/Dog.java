class Dog
{
  String name;
  String breed;
  int yob;
  String state;
  boolean hypoallergenic;
  
  Dog(String name, String breed, int yob, String state, boolean hypoallergenic)
  {
    this.name = name;
    this.breed = breed;
    this.yob = yob;
    this.state = state;
    this.hypoallergenic= hypoallergenic;
  }
}

class ExamplesDog
{
  ExamplesDog(){}
  
  Dog d1 = new Dog("Fido", "Labrador", 2020, "MA", false);
  Dog d2 = new Dog("Buddy", "Poodle", 2019, "NY", true);
  Dog d3 = new Dog("Bella", "Golden Retriever", 2021, "CA", false);
  Dog d4 = new Dog("Hufflepuff", "Wheaten Terrier", 2012, "TX", true);
  Dog d5 = new Dog("Pearl", "Labrador Retriever", 2016, "MA", false);
}