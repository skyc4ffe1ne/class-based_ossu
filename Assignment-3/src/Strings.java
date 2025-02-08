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
    
    // takes this sorted list of Strings and a given sorted list of Strings (otherLoS),
    // and produces a sorted list of Strings that contains all items in both original lists,
    // including duplicates. 
    ILoString merge(ILoString otherLoS);
    
    // produces a new list of Strings containing the same elements as this list of Strings,
    // but in reverse order.
    ILoString reverse();
    ILoString helperReverse(int acc, int accInternal, ILoString originalList);  
    int getLength();
    
    // determines if this list contains pairs of identical strings,
    // that is, the first and second strings are the same,
    // the third and fourth are the same,
    // the fifth and sixth are the same, etc.
    boolean isDoubledList();
    boolean helperIsDoubledList(String first);
    
    // that determines whether this list contains the same words reading the list in either order.
    boolean isPalindromeList();
    boolean helperIsPalindromeList(ILoString reverseList);
}

// to represent an empty list of Strings
class MtLoString implements ILoString {
    MtLoString(){}
    
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
    
    public ILoString merge(ILoString otherLoS)
    {
      return otherLoS;
    }

    public int getLength() {
      return 0;
    }

    public ILoString reverse()
    {
      return this;
    }

    public ILoString helperReverse(int acc, int accInternal, ILoString originalList)
    {
        return this;
    }
   
    public boolean isDoubledList()
    {
      return false;
    }

    public boolean helperIsDoubledList(String first)
    {
      return false;
    }
    
    public boolean isPalindromeList()
    {
      return true;
    }
    
    public boolean helperIsPalindromeList(ILoString reverseList)
    {
      return true; 
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
   
   public ILoString merge(ILoString otherLoS)
   {
     if(!this.isSorted()) {
      throw new IllegalStateException("The current list is not sorted: " + this );
     }else if(!otherLoS.isSorted())
     {
      throw new IllegalArgumentException("The given list is not sorted: " +  otherLoS);
     }else {
       return new ConsLoString(this.first,this.rest.merge(otherLoS));
     }
   }
   
   public int getLength()
   {
     return 1 + this.rest.getLength();
   }
   
   public ILoString reverse()
   {
     return this.helperReverse(this.getLength(), 0, this);
   }

   public ILoString helperReverse(int acc, int accInternal, ILoString originalList) {

     if(accInternal == acc - 1) // accInternal start from 0, that's why i need acc - 1
     {
       return new ConsLoString(this.first, originalList.helperReverse(acc - 1, 0, originalList ));
     }else {
       return this.rest.helperReverse(acc, accInternal + 1, originalList);
     }
   }
   
   
    public boolean isDoubledList()
    {
      return this.rest.helperIsDoubledList(this.first);
    }
    
    public boolean helperIsDoubledList(String first)
    {
      if(this.first.equals(first)) {
        return true;
      }else {
        return this.rest.helperIsDoubledList(this.first);
      }
    }
    
    public boolean isPalindromeList()
    {
      return this.helperIsPalindromeList(this.reverse());
    }
    
    public boolean helperIsPalindromeList(ILoString reverseList)
    {
     if(this.first.equals(reverseList.getFirst())) {
       return this.rest.helperIsPalindromeList(reverseList.getRest());
     }else {
       return false;
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
    
    
    // test the method interleave for the list of Strings
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
    
    // test the method merge for the list of Strings
    boolean testMerge(Tester t)
    {

      ILoString cat_dog = new ConsLoString("A ",
        new ConsLoString("beautiful ",
            new ConsLoString("cat ",
                new ConsLoString("dance.",
                    new ConsLoString("A ",
                        new ConsLoString("curious ",
                            new ConsLoString("dog.",
                                new MtLoString())))))));
      return 
          t.checkExpect(cat_dancing.merge(dog_curious), cat_dog)
          &&
          t.checkException(
              new IllegalStateException("The current list is not sorted: " + mary),
                mary, "merge", cat_dancing)
          &&
          t.checkException(
              new IllegalArgumentException("The given list is not sorted: " + mary),
                cat_dancing, "merge", mary)
      ;
    }
    
    // test the method getLength for the list of Strings
    boolean testGetLength(Tester t)
    {
     return
         t.checkExpect(cat_dancing.getLength(), 4)
         &&
         t.checkExpect(dog_curious.getLength(), 3)
         &&
         t.checkExpect(dancing_moon.getLength(), 5)
         &&
         t.checkExpect(mary.getLength(), 5)
        ;

    }
    

    // test the method interleave for the list of Strings
    boolean testReverse(Tester t)
    {

      ILoString tac = new ConsLoString("dance.",
        new ConsLoString("cat ",
            new ConsLoString("beautiful ",
                new ConsLoString("A ",
                    new MtLoString())))); 

      ILoString mary_reversed = new ConsLoString("lamb.",
          new ConsLoString("little ",
              new ConsLoString("a ",
                  new ConsLoString("had ",
                      new ConsLoString("Mary ", new MtLoString())))));

      ILoString dog_curious_reversed = new ConsLoString("dog.", 
                  new ConsLoString("curious ", 
                      new ConsLoString("A ", new MtLoString())));

      ILoString dancing_moon_reversed = new ConsLoString("dance.", 
                  new ConsLoString("we ", 
                      new ConsLoString("moon ", 
                          new ConsLoString("the ", 
                              new ConsLoString("Under ", new MtLoString())))));

      return 
          t.checkExpect(cat_dancing.reverse(), tac)
          &&
          t.checkExpect(mary.reverse(), mary_reversed)
          &&
          t.checkExpect(dog_curious.reverse(), dog_curious_reversed)
          &&
          t.checkExpect(dancing_moon.reverse(), dancing_moon_reversed)
          ;
    }
    
    // test the method isDoubledList for the list of Strings
    boolean testIsDoubledList(Tester t)
    {

      ILoString l_0 = new ConsLoString("h", new ConsLoString("h", new MtLoString()));
      ILoString l_1 = new ConsLoString("h", new ConsLoString("2", new ConsLoString("h", new MtLoString())));
      ILoString l_2 = new ConsLoString("h", new ConsLoString("2", new ConsLoString("h", new ConsLoString("h", new MtLoString()))));
      ILoString l_3 = new ConsLoString("h", new ConsLoString("2", new ConsLoString("h", new ConsLoString("2", new ConsLoString("2", new MtLoString())))));
      ILoString l_4 = new MtLoString();

      return
          t.checkExpect(cat_dancing.isDoubledList(), false)
          &&
          t.checkExpect(mary.isDoubledList(), false)
          &&
          t.checkExpect(dog_curious.isDoubledList(), false)
          &&
          t.checkExpect(dancing_moon.isDoubledList(), false)
          &&
          t.checkExpect(l_0.isDoubledList(), true)
          &&
          t.checkExpect(l_1.isDoubledList(), false)
          &&
          t.checkExpect(l_2.isDoubledList(), true)
          &&
          t.checkExpect(l_3.isDoubledList(), true)
          &&
          t.checkExpect(l_4.isDoubledList(), false)
          ;
    }
    

    // test the method isPalindromeList for the list of Strings
    boolean testIsPalindromeList(Tester t)
    {

      ILoString l_0 = new ConsLoString("a", new ConsLoString("b", new ConsLoString("c", new ConsLoString("b", new ConsLoString("a", new MtLoString())))));
      ILoString l_1 = new MtLoString();

      return
          t.checkExpect(cat_dancing.isPalindromeList(), false)
          &&
          t.checkExpect(mary.isPalindromeList(), false)
          &&
          t.checkExpect(dog_curious.isPalindromeList(), false)
          &&
          t.checkExpect(dancing_moon.isPalindromeList(), false)
          &&
          t.checkExpect(l_0.isPalindromeList(), true)
          &&
          t.checkExpect(l_1.isPalindromeList(), true)
          ;
    }
}
