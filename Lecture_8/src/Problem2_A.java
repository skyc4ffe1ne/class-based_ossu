import tester.*;

interface ILoIntegers{
 boolean variantA();
 boolean helpVariantA(Number number,boolean flagOdd,boolean flagEven);

 boolean variantB();
 boolean helpVariantB(Number number,boolean flagInclusive,boolean flagOdd,boolean flagEven);


 boolean variantC();
 int variantCountC(int acc);
}


class Number{
  int number;
  
  Number(int number)
  {
    this.number = number;
  }
  
  boolean isEven()
  {
   return this.number % 2 == 0;  
  }
  
  boolean isOdd()
  {
   return this.number % 2 != 0;
  }
  
  boolean isInclusive()
  {
   return this.number >= 5 && this.number <= 10;
  }
  
  int count()
  {
    return 1;
  }

}

class ConsLoIntegers implements ILoIntegers
{
 Number first;
 ILoIntegers rest;
 
 
  ConsLoIntegers(Number first,ILoIntegers rest)
  {
    this.first = first;
    this.rest = rest;
  }
  

  public boolean variantA()
  {
    return this.rest.helpVariantA(this.first, false, false);
  }
  
  public boolean helpVariantA(Number number,boolean flagOdd, boolean flagEven)
  {
    
    if(number.isInclusive())
    {
      if(number.isOdd()) {
         return this.rest.helpVariantA(this.first,true,flagEven);
      }
      if(number.isEven()) {
         return this.rest.helpVariantA(this.first,flagOdd,true);
      }
      return true;
    }else {
      return false;
    }

  }
  
  
  public boolean variantB()
  {
    return this.rest.helpVariantB(this.first,false, false, false);
  }
  
  public boolean helpVariantB(Number number,boolean flagInclusive,boolean flagOdd, boolean flagEven)
  {
    
    if(number.isInclusive() && !flagInclusive)
    {
      return this.rest.helpVariantB(this.first,true,flagOdd, flagEven);
    }else if(number.isOdd() && !flagOdd) 
    {
      return this.rest.helpVariantB(this.first,flagInclusive,true,flagEven);
    }else if(number.isEven() && !flagEven)
    {
      return this.rest.helpVariantB(this.first,flagInclusive,flagOdd,true);
    }
    else
    {
      return false;
    }

  }
  
  
  public boolean variantC()
  {
    return this.variantCountC(0) == 3 && this.helpVariantB(this.first,false,false,false);
  }
  
  public int variantCountC(int acc)
  {
    acc++;
    return this.rest.variantCountC(acc); 
  }
}



class MtLoIntegers implements ILoIntegers{
  MtLoIntegers(){}
  
  public boolean variantA()
  {
   return false; 
  }
  

  public boolean helpVariantA(Number number, boolean flagOdd, boolean flagEven)
  {

    if(number.isInclusive())
    {
      if(number.isOdd()) {
         return flagOdd = true;
      }
      if(number.isEven()) {
         return flagEven = true; 
      }
      return flagOdd && flagEven;
    }else {
    return flagOdd && flagEven;
    }
  }
  
  
  public boolean variantB()
  {
   return false; 
  }
  

  public boolean helpVariantB(Number number,boolean flagInclusive, boolean flagOdd, boolean flagEven)
  {

    if(number.isInclusive() && !flagInclusive)
    {
      flagInclusive = true;
      return flagEven && flagOdd && flagInclusive;
    }else if(number.isOdd() && !flagOdd) 
    {
       flagOdd = true; 
       return flagEven && flagOdd && flagInclusive;
    }else if(number.isEven() && !flagEven)
    {
       flagEven = true; 
       return flagEven && flagOdd && flagInclusive;
    }
    else
    {
      return flagEven && flagOdd && flagInclusive;
    }

  }

  public boolean variantC()
  {
    return false;
  }
 
  public int variantCountC(int acc)
  {
    return acc;
  }

}


class ExamplesInteger{
  ExamplesInteger(){}
  
  Number a = new Number(6);
  Number b = new Number(5);
  Number c = new Number(7);
  Number d = new Number(4);
  Number e = new Number(3);
  Number f = new Number(6);

  ILoIntegers emptyList = new MtLoIntegers();

  ILoIntegers list_0 = new ConsLoIntegers(a, new ConsLoIntegers(b,emptyList));
  ILoIntegers list_1 = new ConsLoIntegers(a, new ConsLoIntegers(b, new ConsLoIntegers(c, emptyList)));
  ILoIntegers list_2 = new ConsLoIntegers(d, new ConsLoIntegers(e,emptyList));
  ILoIntegers list_3 = new ConsLoIntegers(d, new ConsLoIntegers(a,emptyList));
  ILoIntegers list_4 = new ConsLoIntegers(a, new ConsLoIntegers(b, new ConsLoIntegers(f, emptyList)));
  ILoIntegers list_5 = new ConsLoIntegers(a, new ConsLoIntegers(b, new ConsLoIntegers(f, new ConsLoIntegers(e,emptyList))));
  
  
  
  boolean testVariantA(Tester t)
  {
    return
        t.checkExpect(list_0.variantA(), true)
        &&
        t.checkExpect(list_1.variantA(), true)
        &&
        t.checkExpect(list_2.variantA(), false)
        &&
        t.checkExpect(list_3.variantA(), false)
        ;
  }
  boolean testVariantB(Tester t)
    {
      return
          t.checkExpect(list_0.variantB(), false)
          &&
          t.checkExpect(list_1.variantB(), false)
          &&
          t.checkExpect(list_2.variantB(), false)
          &&
          t.checkExpect(list_3.variantB(), false)
          &&
          t.checkExpect(list_4.variantB(), true)
          ;
    }

//  boolean testCountC(Tester t)
//    {
//      return
//          t.checkExpect(list_0.variantC(), 2)
//          &&
//          t.checkExpect(list_1.variantC(), 3)
//          &&
//          t.checkExpect(list_2.variantC(), 2)
//          &&
//          t.checkExpect(list_3.variantC(), 2)
//          &&
//          t.checkExpect(list_4.variantC(), 3)
//          ;
//    }

  boolean testVariantC(Tester t)
    {
      return
          t.checkExpect(list_0.variantC(), false)
          &&
          t.checkExpect(list_1.variantC(), true)
          &&
          t.checkExpect(list_2.variantC(), false)
          &&
          t.checkExpect(list_3.variantC(), false)
          &&
          t.checkExpect(list_4.variantC(), true)
          &&
          t.checkExpect(list_5.variantC(), false)
          ;
    }  
 
}