interface IAT {}



class Perso implements IAT{
  String name;
  int age ;
  String gender;
  IAT mom;
  IAT dad;
  
  Perso(String name,int age, String gender, IAT mom, IAT dad){
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

  IAT p7 = new Perso ("Brian", 78, "male",unknow,unknow);
  IAT p6 = new Perso ("Viktoria", 81, "female",unknow,unknow);

  IAT p4 = new Perso ("Brian", 78, "male",unknow,unknow);
  IAT p5 = new Perso ("Viktoria", 81, "female",unknow,unknow);
  
  IAT p2 = new Perso ("Mike", 48, "male", p6,p7);
  IAT p3 = new Perso ("Rihanna", 38, "female",p5,p4);

  IAT p1 = new Perso ("Brian", 18, "male",p3,p2);

 
}


