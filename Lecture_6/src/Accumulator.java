import tester.*;
interface IAT
{
  //To compute the number of known ancestors of this ancestor tree
  //(excluding this ancestor tree itself)
  int count();

  int helperCount();

  //To compute how many ancestors of this ancestor tree (excluding this
  //ancestor tree itself) are women older than 40 (in the current year)?
  int femaleAncOver40();
  
  int helperFemaleAncOver40();

  //To compute whether this ancestor tree is well-formed: are all known
  //people younger than their parents?
  boolean wellFormed();
  
  boolean helperWellFormed(int yob);

  //To compute the names of all the known ancestors in this ancestor tree
  //(including this ancestor tree itself)
//  ILoString ancNames();

  
  //In Person:
  //To return the younger of this Person and the given ancestor tree
  public IAT youngerIAT(IAT other);

  boolean helperYoungerIAT(int yob);

  //To compute this ancestor tree's youngest grandparent
  IAT youngestParent();  


  IAT youngestGrandParent();  
  
  
  IAT youngestAncInGen(int gen); 
  
}



class Unknown implements IAT
{
  Unknown(){}

  public int count() 
  {
    return 0;
  }

  public int helperCount() 
  {
    return 0;
  }


  public int femaleAncOver40()
  {
    return 0; 
  }
  public int helperFemaleAncOver40()
  {
    return 0; 
  }
  
  
  public boolean wellFormed()
  {
    return true;
  }

  public boolean helperWellFormed(int yob)
  {
    return true;
  }
  
  public IAT youngerIAT(IAT other) {
    return other;
  }
  
  
  
  public boolean helperYoungerIAT(int yob)
  {
   return false;
  }
  
  
  public  IAT youngestParent() 
  {
    return this;
  }
  
  
  
  public  IAT youngestGrandParent()
  {
    return this;
  }
  

 public IAT youngestAncInGen(int gen) 
 {
    if(gen == 0) 
    {
      return this;
    }
    else 
    {
      return new Unknown();
    }
 }

}


class Person implements IAT {
  String name;
  int yob;
  boolean isMale;
  IAT mom;
  IAT dad;

  Person(String name, int yob, boolean isMale, IAT mom, IAT dad)
  {
    this.name = name;
    this.yob = yob;
    this.isMale = isMale;
    this.mom = mom;
    this.dad = dad;
  }
  
  
  public int count() 
  {
    return this.mom.helperCount() + this.dad.helperCount();
}


// Remove the 1 from the first
 public int helperCount() 
 {
   return 1 + this.mom.helperCount() + this.dad.helperCount(); 
 }
 
 
 
 public int femaleAncOver40()
{
  return this.mom.helperFemaleAncOver40() + this.dad.helperFemaleAncOver40(); 
}
 
 public int helperFemaleAncOver40()
   {
   if(!this.isMale && this.yob < 1980)
     {
       return 1 + this.mom.helperFemaleAncOver40() + this.dad.helperFemaleAncOver40();
     } 
   else 
   {
     return 0 + this.mom.helperFemaleAncOver40() + this.dad.helperFemaleAncOver40();
   }
  }
 
 
  public boolean wellFormed()
  {
    return this.dad.helperWellFormed(this.yob) && this.mom.helperWellFormed(this.yob);
  }
  
  
  public boolean helperWellFormed(int year)
  {
    if(this.yob < year)
    {
      return this.dad.helperWellFormed(this.yob) && this.mom.helperWellFormed(this.yob);
    }else {
      return false;
    }

  }
  
  
  
  
  public IAT youngerIAT(IAT other)
  {
     if(other.helperYoungerIAT(this.yob))
     {
       return this;
     }else {
       return other;
     }
  }
  
  
 public boolean helperYoungerIAT(int yob)
  {
    if(this.yob < yob)
    {
      return true;
    }else
    {
     return false; 
    }

  }
 
 
 public  IAT youngestParent() 
  {
   return this.mom.youngerIAT(this.dad);
  }
 
 
 
 
  public  IAT youngestGrandParent()
  {
    return this.mom.youngestParent().youngerIAT(this.dad.youngestParent());
  }
  
  
  
 public IAT youngestAncInGen(int gen) 
 {
   if(gen == 0)
   {
     return this;
   }
   else 
   {
     return this.mom.youngestAncInGen(gen - 1).youngerIAT(this.dad.youngestAncInGen(gen - 1));
   }
 }

}


interface ILoString
{

}


class ConsLoString implements ILoString
{
  String first;
  ConsLoString rest;
  
  ConsLoString(String first, ConsLoString rest)
  {
    this.first = first;
    this.rest = rest;
  }
  
}

  class MtLoString implements ILoString
  {
    MtLoString(){}
  }
  
  
  class ExamplesIAT {
    IAT enid = new Person("Enid", 1904, false, new Unknown(), new Unknown());
    IAT edward = new Person("Edward", 1902, true, new Unknown(), new Unknown());
    IAT emma = new Person("Emma", 1906, false, new Unknown(), new Unknown());
    IAT eustace = new Person("Eustace", 1907, true, new Unknown(), new Unknown());
 
    IAT david = new Person("David", 1925, true, new Unknown(), this.edward);
    IAT daisy = new Person("Daisy", 1927, false, new Unknown(), new Unknown());
    IAT dana = new Person("Dana", 1933, false, new Unknown(), new Unknown());
    IAT darcy = new Person("Darcy", 1930, false, this.emma, this.eustace);
    IAT darren = new Person("Darren", 1935, true, this.enid, new Unknown());
    IAT dixon = new Person("Dixon", 1936, true, new Unknown(), new Unknown());
 
    IAT clyde = new Person("Clyde", 1955, true, this.daisy, this.david);
    IAT candace = new Person("Candace", 1960, false, this.dana, this.darren);
    IAT cameron = new Person("Cameron", 1959, true, new Unknown(), this.dixon);
    IAT claire = new Person("Claire", 1956, false, this.darcy, new Unknown());
 
    IAT bill = new Person("Bill", 1980, true, this.candace, this.clyde);
    IAT bree = new Person("Bree", 1981, false, this.claire, this.cameron);
 
    IAT andrew = new Person("Andrew", 2001, true, this.bree, this.bill);
 
    boolean testCount(Tester t) {
        return
            t.checkExpect(this.andrew.count(), 16) &&
            t.checkExpect(this.david.count(), 1) &&
            t.checkExpect(this.enid.count(), 0) &&
            t.checkExpect(new Unknown().count(), 0);
    }
    
    boolean testFemaleAncOver40(Tester t) {
        return
            t.checkExpect(this.andrew.femaleAncOver40(), 7) &&
            t.checkExpect(this.bree.femaleAncOver40(), 3) &&
            t.checkExpect(this.darcy.femaleAncOver40(), 1) &&
            t.checkExpect(this.enid.femaleAncOver40(), 0) &&
            t.checkExpect(new Unknown().femaleAncOver40(), 0);
    }

    boolean testWellFormed(Tester t) {
        return
            t.checkExpect(this.andrew.wellFormed(), true) &&
            t.checkExpect(new Unknown().wellFormed(), true) &&
            t.checkExpect(
                new Person("Zane", 2000, true, this.andrew, this.bree).wellFormed(),
                false);
    }
    
    
      boolean testYoungerIAT(Tester t) {
      return
          t.checkExpect(this.andrew.youngerIAT(this.bill), this.andrew) &&
          t.checkExpect(this.andrew.youngerIAT(this.bree), this.andrew); 
          
      }
      
      boolean testYoungestParent(Tester t) {
      return
          t.checkExpect(this.andrew.youngestParent(), this.bree) &&
          t.checkExpect(this.bill.youngestParent(), this.candace); 
          
      }

    /*
    boolean testAncNames(Tester t) {
        return
            t.checkExpect(this.david.ancNames(),
                new ConsLoString("David",
                    new ConsLoString("Edward", new MtLoString()))) &&
            t.checkExpect(this.eustace.ancNames(),
                new ConsLoString("Eustace", new MtLoString())) &&
            t.checkExpect(new Unknown().ancNames(), new MtLoString());
    }
    */
    boolean testYoungestGrandparent(Tester t) {
        return
            t.checkExpect(this.andrew.youngestGrandParent(), this.candace)
            && t.checkExpect(this.bree.youngestGrandParent(), this.dixon);
    }
    
    
    boolean testYoungestAncInGen (Tester t) {
        return
            t.checkExpect(this.andrew.youngestAncInGen(2), this.candace)
            && t.checkExpect(this.bree.youngestAncInGen(2), this.dixon);
    }
}