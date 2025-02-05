// CS 2510, Assignment 3

import tester.*;

// to represent a list of Strings
interface ILoString {
    // combine all Strings in this list into one
    String combine();
    // determines whether this list is sorted in alphabetical order, in a case-insensitive way.
    boolean isSorted();
    boolean helperIsSorted(String el);
    
    // takes a list of Strings, and a given list (otherLoS) produces a new list where the
    // first, third, fifth elements are from the list
    // second, fourth, sixth elements are from the given list  
    ILoString interleave(ILoString otherLos);
    String getFirst();
    ILoString getRest();
    ILoString filter();
}

// to represent an empty list of Strings
class MtLoString implements ILoString {
    MtLoString(){}
    
    // combine all Strings in this list into one
    public String combine() {
       return "";
    }  
    
    public boolean isSorted()
    {
      return true;
    }
    
    public boolean helperIsSorted(String el)
    {
      return true;
    }
    
    public ILoString interleave(ILoString otherLoS)
    {
      return otherLoS;
    }
    
    public String getFirst()
    {
      return "";
    }
    
    public ILoString getRest()
    {
      return this;
    }
    
    public ILoString filter()
    {
      return this;
    } 
    
}


// to represent a nonempty list of Strings
class ConsLoString implements ILoString {
    String first;
    ILoString rest;
    
    ConsLoString(String first, ILoString rest){
        this.first = first;
        this.rest = rest;  
    }
    
    /*
     TEMPLATE
     FIELDS:
     ... this.first ...         -- String
     ... this.rest ...          -- ILoString
     
     METHODS
     ... this.combine() ...     -- String
     
     METHODS FOR FIELDS
     ... this.first.concat(String) ...        -- String
     ... this.first.compareTo(String) ...     -- int
     ... this.rest.combine() ...              -- String
     
     */
    
    // combine all Strings in this list into one
    public String combine(){
        return this.first.concat(this.rest.combine());
    }  
    
    public boolean isSorted()
    {
     return this.rest.helperIsSorted(this.first);
    }
    
    public boolean helperIsSorted(String el)
    {
     if(el.toLowerCase().compareTo(this.first.toLowerCase()) < 0)
     {
       return this.rest.isSorted();
     }else {
       return false;
     }
    }
    
    
   public ILoString interleave(ILoString otherLoS)
   {
      return new ConsLoString(this.first, new ConsLoString(otherLoS.getFirst(), this.rest.interleave(otherLoS.getRest()))).filter();
   }
    
   public String getFirst()
   {
     return this.first;
   }
   
   public ILoString getRest()
   {
     return this.rest;
   }
   
   public ILoString filter()
   {
    if(this.first == "")
    {
      return this.rest.filter();
    }else {
      return new ConsLoString(this.first, this.rest.filter());
    }
   }
  
}

// to represent examples for lists of strings
class ExamplesStrings{
    
    ILoString mary = new ConsLoString("Mary ",
                    new ConsLoString("had ",
                        new ConsLoString("a ",
                            new ConsLoString("little ",
                                new ConsLoString("lamb.", new MtLoString())))));
    
    ILoString cat_dancing = new ConsLoString("A ", new ConsLoString("beautiful ", new ConsLoString("cat ", new ConsLoString("dance.", new MtLoString()))));
    ILoString dog_curious = new ConsLoString("A ", new ConsLoString("curious ", new ConsLoString("dog.", new MtLoString())));
    ILoString dancing_moon = new ConsLoString("Under ", new ConsLoString("the ", new ConsLoString("moon ", new ConsLoString("we ", new ConsLoString("dance.", new MtLoString())))));
    
    

    // test the method combine for the lists of Strings
    boolean testCombine(Tester t){
        return 
            t.checkExpect(this.mary.combine(), "Mary had a little lamb.")
            &&
            t.checkExpect(this.cat_dancing.combine(), "A beautiful cat dance.")
            &&
            t.checkExpect(this.dog_curious.combine(), "A curious dog.")
            &&
            t.checkExpect(this.dancing_moon.combine(), "Under the moon we dance.")
            ;
    }
   
    // test the method isSorted for the list of Strings
    boolean testIsSorted(Tester t)
    {
      return 
      t.checkExpect(this.mary.isSorted(), false)
      &&
      t.checkExpect(this.cat_dancing.isSorted(), true)
      &&
      t.checkExpect(this.dog_curious.isSorted(), true)
      &&
      t.checkExpect(this.dancing_moon.isSorted(), false)
      ;
    }
    
    
    boolean testInterleave(Tester t)
    {
      
      ILoString mary_cat = new ConsLoString("Mary ",
          new ConsLoString("A ",
              new ConsLoString("had ",
                  new ConsLoString("beautiful ",
                      new ConsLoString("a ",
                          new ConsLoString("cat ",
                              new ConsLoString("little ",
                                  new ConsLoString("dance.",
                                      new ConsLoString("lamb.", new MtLoString())))))))));

      ILoString mt = new MtLoString();
      ILoString list1 = new ConsLoString("a", new ConsLoString("b", new ConsLoString("c", mt)));
      ILoString list2 = new ConsLoString("1", new ConsLoString("2", new ConsLoString("3", mt)));
      ILoString list3 = new ConsLoString("a", new ConsLoString("1", new ConsLoString("b", new ConsLoString("2", new ConsLoString("c", new ConsLoString("3", mt))))));
      return
          t.checkExpect(this.mary.interleave(cat_dancing), mary_cat)
          &&
          t.checkExpect(mt.interleave(list1), list1) 
          &&
          t.checkExpect(list1.interleave(mt), list1) 
          &&
          t.checkExpect(list1.interleave(list2), list3)
          ;
    }
}