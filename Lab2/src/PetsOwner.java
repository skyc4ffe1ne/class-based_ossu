import tester.*;
// to represent a pet owner
class Person {
    String name;
    IPet pet;
    int age;
 
    Person(String name, IPet pet, int age) {
        this.name = name;
        this.pet = pet;
        this.age = age;
    }
    
    public boolean isOlder(Person that) {
      return this.age > that.age;
    }
    
   public boolean sameNamePet(String name) {
    return this.pet.sameNamePet(name); 
   }
   
   public boolean perish() {
    return this.pet.perish();
   }
}
// to represent a pet
interface IPet { 
  boolean sameNamePet(String name);
  boolean perish();
}
 
// to represent a pet cat
class Cat implements IPet {
    String name;
    String kind;
    boolean longhaired;
 
    Cat(String name, String kind, boolean longhaired) {
        this.name = name;
        this.kind = kind;
        this.longhaired = longhaired;
    }
    
    public boolean sameNamePet(String name) {
      return this.name == name;
    }
    public boolean perish() {
      return false;
    }
    
}
 
// to represent a pet dog
class Dog implements IPet {
    String name;
    String kind;
    boolean male;
 
    Dog(String name, String kind, boolean male) {
        this.name = name;
        this.kind = kind;
        this.male = male;
    }
    
     public boolean sameNamePet(String name) {
      return this.name == name;
    }

    public boolean perish() {
      return false;
    }
}


class NoPet implements IPet {
 NoPet(){} 
 
 public boolean sameNamePet(String name) {
   return false;  
 }

  public boolean perish() {
    return true;
  }
}

class ExamplesOwners{
  
  IPet noPet = new NoPet();
  IPet d1 = new Dog ("Bibbie", "normal", true);
  IPet d2 = new Dog ("Lottie", "special", false);
  IPet d3 = new Dog ("Ronnie", "anormal", true);

  IPet c1 = new Cat("Loscie", "normal", true);
  IPet c2 = new Cat("Donnie", "special", false);
  IPet c3 = new Cat("Vinnie", "anormal", false);

  
  
  Person  p1 = new Person("Marc", d1, 23);
  Person  p2 = new Person("Tommy", c1, 33);
  Person  p3 = new Person("Nicole", d2, 31);
  
  Person p1_n = new Person("Marco", noPet,48);
  
  boolean testIsOlder(Tester t) {
    return t.checkExpect(this.p1.isOlder(p2), false) 
        && t.checkExpect(this.p1.sameNamePet("Bibbie"), true)
        && t.checkExpect(this.p1.sameNamePet("Hello"), false)
        && t.checkExpect(this.p1.perish(), false)
        && t.checkExpect(this.p1_n.perish(), true)
        ;
  }

}