class Book 
{
  String author;
  String title;
  ILoReference bibliography;
  String publisher;

  Book(String author, String title, ILoReference bibliography, String publisher)
  {
    this.author = author;
    this.title = title;
    this.bibliography = bibliography;
    this.publisher = publisher;
  }
 
}


class Wiki 
{
  String author;
  String title;
  ILoReference bibliography;
  String url;

  Wiki (String author, String title, ILoReference bibliography, String url)
  {
    this.author = author;
    this.title = title;
    this.bibliography = bibliography;
    this.url = url;
  }
}




interface ILoReference{
  
}

class ConsLoReference implements ILoReference
{
  Book first;
  ILoReference rest;
  
  ConsLoReference(Book first, ILoReference rest)
  {
   this.first = first; 
   this.rest = rest; 
  }
}

class MtLoReference implements ILoReference
{
  MtLoReference(){}
}



class Bibliography {
  String lastName;
  String firstName;
  String title;
  
  Bibliography(String lastName, String firstName, String title)
  {
    this.lastName = lastName;
    this.firstName = firstName;
    this.title = title;
  }
  
}




class ExamplesDocument{
  ExamplesDocument(){}
  
  
  ILoReference emptyReference = new MtLoReference();

  Book b0_without_reference = new Book("author0","title0",emptyReference,"publisher0");
  Book b1_without_reference = new Book("author1","title1",emptyReference,"publisher1");
  Book b2_without_reference = new Book("author2","title2",emptyReference,"publisher2");


  Wiki w0_without_reference = new Wiki("author0","title0",emptyReference,"link0");
  Wiki w1_without_reference = new Wiki("author1","title1",emptyReference,"link1");
  Wiki w2_without_reference = new Wiki("author2","title2",emptyReference,"link2");


  ILoReference reference_for_b3 = new ConsLoReference(b0_without_reference, new ConsLoReference(b1_without_reference, emptyReference));

  Book b3 = new Book("author3","title3",reference_for_b3,"publisher3");
  

     
}