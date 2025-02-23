import tester.*;
class Author
{
  String firstName;
  String lastName;
  
  Author(String firstName,String lastName)
  {
    this.firstName = firstName;
    this.lastName = lastName;
  }
  
  
  boolean sameAuthor(Author that)
  {
    return this.firstName.equals(that.firstName) && 
        this.lastName.equals(that.lastName);
  }
}


class Book
 {
   String name;
   Author author;
   
   
   Book(String name, Author author)
   {
     this.name = name;
     this.author = author;
   }
   
   boolean sameBook(Book that)
   {
     return this.name.equals(that.name) 
           && this.author.sameAuthor(that.author);
   }
   
 }


class ExampleBook
{
  ExampleBook(){}

 Author a_0 = new Author("A", "B"); 
 Book b_0 = new Book("title", a_0); 
 Book b_1 = new Book("title", a_0); 

  boolean testSameBook(Tester t)
  {
   return
       t.checkExpect(b_0.sameBook(b_1), true);
  }
}