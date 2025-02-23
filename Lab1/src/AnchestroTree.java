interface IAT {}



class Persona implements IAT{
  String name;
  int age ;
  String gender;
  IAT mom;
  IAT dad;
  
  Persona(String name,int age, String gender, IAT mom, IAT dad){
   this.name = name; 
   this.age = age; 
   this.gender = gender; 
   this.mom = mom; 
   this.dad = dad; 
  }

}

class Unknows implements IAT{
 String value;
 
 Unknows(String value){
   this.value = value;
 }

}


class ExamplesAnchestors{
  
  IAT unknow = new Unknows("Unknow");

  IAT p7 = new Persona ("Brian", 78, "male",unknow,unknow);
  IAT p6 = new Persona ("Viktoria", 81, "female",unknow,unknow);

  IAT p4 = new Persona ("Brian", 78, "male",unknow,unknow);
  IAT p5 = new Persona ("Viktoria", 81, "female",unknow,unknow);
  
  IAT p2 = new Persona ("Mike", 48, "male", p6,p7);
  IAT p3 = new Persona ("Rihanna", 38, "female",p5,p4);

  IAT p1 = new Persona ("Brian", 18, "male",p3,p2);

 
}


