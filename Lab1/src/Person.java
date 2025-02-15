
class Person {
  String name;
  int age;
  String gender;
  Address address;
  
  Person(String name, int age, String gender, Address address){
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.address = address;
  }
}

class Address{
  String city;
  String cap;
  
  Address(String city, String cap)
  {
    this.city= city;
    this.cap= cap;
  }
}


class ExamplesPerson{
  Address forP1 = new Address("Boston", "MA"); 
  Person p1 = new Person("Tim",20, "male",forP1);

  Address forP2 = new Address("Warwick", "RI"); 
  Person p2 = new Person("Kate",23, "female",forP2);

  Address forP3 = new Address("Nashua", "NH"); 
  Person p3 = new Person("Rebecca",33, "female",forP3);

  Address forP4 = new Address("California", "CA"); 
  Person p4 = new Person("Ben",78, "man",forP4);

  Address forP5 = new Address("LasVegas", "LS"); 
  Person p5 = new Person("Korca",53, "female",forP5);
}