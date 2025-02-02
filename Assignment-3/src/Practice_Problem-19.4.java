import tester.Tester;

// A set of integers
// contains an integer at most once;
class Set{
  ILin elements;
  
  Set(ILin elements){
    this.elements = elements;
  }
  
  // Add i to this set
  // unless it is already there
  Set add(int i)
  {
    if(this.in(i))
    {
      return this;
    }else {
      return new Set(new Cin(i,this.elements));
    }
  }
  
  // Is a member of this set ?
  boolean in(int i)
  {
    return this.elements.howToMany(i) > 0;
  }

}

// A bag of integers;
class Bag{
  ILin elements;
  
  Bag (ILin elements){
    this.elements = elements;
  }
  
  // Add i to this Bag 
  Bag add(int i)
  {
    return new Bag(new Cin(i,this.elements));
  }
  
  // How often is i in this Bag ?
  int in(int i)
  {
    return this.elements.howToMany(i);
  }

}


interface ILin
{
  int howToMany(int i);
  int helperHowToMany(int acc,int i);
}

class Cin implements ILin
{
  int first;
  ILin rest;
  
  Cin(int first, ILin rest)
  {
   this.first = first; 
   this.rest = rest; 
  }
  
  public int howToMany(int i)
  {
   return this.helperHowToMany(0, i);
  }
  
  public int  helperHowToMany(int acc, int i)
  {
    if(this.first == i)
    {
      acc ++;
      return this.rest.helperHowToMany(acc, i);
    }else {
      return this.rest.helperHowToMany(acc, i);
    }
  }
  
}


class MTin implements ILin
{
  MTin(){}
  
   public int howToMany(int i)
   {
    return 0;
   }
  
   public int helperHowToMany(int acc, int i)
   {
    return acc;
   }
  
}

class ExamplesInteger{
  ExamplesInteger(){}
  
  // List of integers
  ILin emptyList = new MTin();
  ILin l_0 = new Cin(0, new Cin(1, new Cin(2, new Cin(3, emptyList))));
  ILin l_1 = new Cin(0, new Cin(1, new Cin(2, new Cin(3, new Cin(3, new Cin(4, emptyList))))));
  ILin l_2 = new Cin(10, new Cin(4, new Cin(32, new Cin(13, new Cin(38, new Cin(32, new Cin(32, emptyList)))))));
  ILin l_3 = new Cin(4, new Cin(5, new Cin(7, new Cin(9, emptyList))));
  ILin l_4 = new Cin(2, new Cin(4, new Cin(8, new Cin(6, emptyList))));
  
  // Set of integers
  Set s_0 = new Set(l_0); 
  Set s_1 = new Set(l_3); 
  Set s_2 = new Set(l_4); 

  // Bag of integers
  Bag b_0 = new Bag(l_0); 
  Bag b_1 = new Bag(l_1); 
  Bag b_2 = new Bag(l_2); 


  boolean testIn(Tester t)
  {
    return
        // i is a member of this set ?
        t.checkExpect(s_0.in(0), true)
        &&
        t.checkExpect(s_1.in(0), false)
        &&
        t.checkExpect(s_2.in(6), true)
        &&
        // How many i are in this Bag ?
        t.checkExpect(b_0.in(2), 1)
        &&
        t.checkExpect(b_1.in(3), 2)
        &&
        t.checkExpect(b_2.in(32), 3)
        ;
  }
}