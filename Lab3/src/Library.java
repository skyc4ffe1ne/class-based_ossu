import tester.*;


class Book{
  /* Template :
   * Fields :
   * ... this.title...  -- String
   * ... this.author... -- String
   * ... this.price...  -- double
   * 
   * Methods:
   * ... this.salePrice(double)...  --double 
   * ... this.sameAuthor(Book)...   --Boolean 
   * 
   */
  String title;
  Author author;
  double price; 
  
  Book(String title, Author author, double price){
   this.title = title;
   this.author = author;
   this.price = price;
  }
  
  //Compute the sale price of this Book given using
  //the given discount rate (as a percentage)
  double salePrice(int discount ) {
   return this.price - ((this.price * discount) / 100); 
  }
  
  // Book Book -> Boolean 
  // Is the given book author the same as this 
  boolean sameAuthor(Book that) {
    return this.author.name.equals(that.author.name) &&
           this.author.yob == that.author.yob;
  }
  
  
  Book reducePrice() {
    return new Book(this.title, this.author, this.salePrice(30));
  }
}


class Author 
{
  
  /*Template
   * Fields:
   * ... this.name ...  -- String
   * ... this.yob  ...  -- int 
   * 
   * Methods:
   * ... this.sameAuthor(Author) ... -- Boolean
   */
  String name;
  int yob;
  
  Author(String name, int yob){
    this.name = name;
    this.yob = yob;
  }
  
  
  boolean sameAuthor(Author that) { 
  return this.name.equals(that.name) &&
         this.yob == that.yob;
  }
  
}




class ExampleBook{
  
  Author a1 = new Author("Author1",1950);
  Author a2 = new Author("Author2",1985);
  Author a3 = new Author("Author3",1956);
  Author a4 = new Author("Author1",1950);
  
  Book b1 = new Book("Titolo", a1, 60);
  Book b2 = new Book("Titolo", a4, 30);
  Book b3 = new Book("Titolo", a2, 20);
  Book b4 = new Book("Titolo", a3, 90);

  boolean testSalePrice(Tester t) {
    return t.checkExpect(this.b1.salePrice(30),42.0)
       && t.checkExpect(this.b1.sameAuthor(this.b2), true) 
       && t.checkExpect(this.b1.sameAuthor(this.b3), false) 
       && t.checkExpect(this.a1.sameAuthor(a2), false)
       && t.checkExpect(this.a1.sameAuthor(a4), true)
       && t.checkExpect(this.b1.reducePrice(), new Book(this.b1.title,this.b1.author,42))
       && t.checkExpect(this.b2.reducePrice(), new Book(this.b2.title,this.b2.author,21))
       ;
  }
}