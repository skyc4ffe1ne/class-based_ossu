import tester.*;

interface ILoString
{
  ILoString append(String newString);

  ILoString appendLast(String newString); 
   
}


class ConsLoString implements ILoString
{
  String first;
  //ConsLoString rest;
  ILoString rest;
  
  ConsLoString(String first, ILoString rest)
  {
    this.first = first;
    this.rest = rest;
  }
  
  // 
  public  ILoString append(String newString) {
    return new ConsLoString(newString,this);
  }
  
  
//  
//   public  ILoString appendLast(String newString) {
//     return new ConsLoString(this.first, new ConsLoString(newString,this.rest));
//  }
  

  public ILoString appendLast(String newString) 
  {
    return new ConsLoString(this.first, this.rest.appendLast(newString));
  }
  
}

class MtLoString implements ILoString
{
  MtLoString(){}


   public ILoString append(String newString)
   {
     return this;
   }
 
 
  public ILoString appendLast(String newString) 
  {
     return new ConsLoString(newString, this);
  } 
  
   
  
}

  
  
class ExamplesILoString
{
  
  ExamplesILoString(){}
  
  ILoString emptyString = new MtLoString();

  ILoString list_1 = new ConsLoString( "hello", new MtLoString());
  ILoString list_2 = new ConsLoString( "hello", new ConsLoString( "how", new MtLoString()));
  ILoString list_3 = new ConsLoString( "hello", new ConsLoString( "how", new ConsLoString("are", new ConsLoString("u", emptyString))));
  
  
  
  boolean testAppend(Tester t)
  {
    return
    t.checkExpect(list_1.append("how"), new ConsLoString("how", new ConsLoString ("hello", new MtLoString()))) 
    && t.checkExpect(list_2.append("how"), new ConsLoString("how", list_2))
    && t.checkExpect(list_3.append("how"), new ConsLoString("how", list_3))
    ; 
  }

  boolean testAppendLast(Tester t)
  {
    return
    t.checkExpect(list_1.appendLast("how"), new ConsLoString ("hello", new ConsLoString("how",new MtLoString())))
    && 
    t.checkExpect(list_2.appendLast("are"), new ConsLoString ("hello", new ConsLoString("how", new ConsLoString("are",new MtLoString()))))
    ; 
  }
  
}
