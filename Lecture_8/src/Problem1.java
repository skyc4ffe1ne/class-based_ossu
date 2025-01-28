import tester.*;

class Author{
String name;
String surname;

Author(String name, String surname)
{
 this.name = name; 
 this.surname = surname; 
}


boolean sortSurname(Author other) {
 return this.surname.compareTo(other.surname) <= 0; 
}

}

class Document 
{
Author author;
String title;  
Resources resources;
ILoDocuments bibliography;

Document(Author author,String title,Resources resources,ILoDocuments bibliography)
  {
    this.author = author;
    this.title = title;
    this.resources = resources;
    this.bibliography = bibliography;
  }
  

boolean sameAuthor(Document other) 
  {
   return
       this.author.name.equals(other.author.name)
     && 
       this.author.surname.equals(other.author.surname);
  }

boolean sameTitle(Document other)
  {
    return this.title.equals(other.title);
  }
  
}

interface Resources{
  
  boolean isBook();
}

class Book implements Resources{
  String editor;
  
  Book(String editor){
    this.editor = editor;
  }
  
  public boolean isBook()
  {
    return true; 
  }
  
}

class Wiki implements Resources{
  String url;
  
  Wiki(String url){
    this.url = url;
  }

  public boolean isBook()
  {
    return false; 
  }
}

interface ILoDocuments
{
  ILoDocuments onlyBooks();
  ILoDocuments helperOnlyBooks(Document doc);

  ILoDocuments removeDuplicates();
  ILoDocuments helperRemoveDuplicates(Document doc);
  boolean      contains(Document doc);
  
  ILoDocuments sortByLastName();
  ILoDocuments insert(Document doc); 
}

class ConsLoDocuments implements ILoDocuments
{
  Document first;
  ILoDocuments rest;
    
  ConsLoDocuments(Document first, ILoDocuments rest)
  {
    this.first = first;
    this.rest = rest;
  }
  
  
  public ILoDocuments onlyBooks()
  {
    return this.rest.helperOnlyBooks(this.first);
  }

  public ILoDocuments helperOnlyBooks(Document doc)
  {
    if(doc.resources.isBook()) {
      return new ConsLoDocuments(doc,this.onlyBooks());
    }else {
      return this.onlyBooks();
    }
  }
  
  
  
  public ILoDocuments removeDuplicates()
  {
    return this.rest.helperRemoveDuplicates(this.first);
  }

  public ILoDocuments helperRemoveDuplicates(Document doc)
  {
    if(this.contains(doc)) {
      return this.rest.helperRemoveDuplicates(doc);
    }else {
      return new ConsLoDocuments(doc,this.rest.helperRemoveDuplicates(this.first));
    }
  }
  
  public boolean contains(Document doc) {
    return doc.sameAuthor(this.first) && doc.sameTitle(this.first);
  }

  
  

  public ILoDocuments sortByLastName() {
    return this.rest.sortByLastName().insert(this.first);
  }
  
  
  public ILoDocuments insert(Document other)
  {
    if(this.first.author.sortSurname(other.author)) {
     return new ConsLoDocuments(this.first,this.rest);
    }else {
     return new ConsLoDocuments(other,this.rest.insert(this.first));
    }

  }
  
  
}


class MtLoDocuments implements ILoDocuments
{
  MtLoDocuments(){}
  
  
  public ILoDocuments  onlyBooks()
  {
    return this;
  }

  public ILoDocuments helperOnlyBooks(Document doc)
  {
    if(doc.resources.isBook()) {
      return new ConsLoDocuments(doc,this.onlyBooks());
    }else {
      return this;
    }
  }
  
  
   public ILoDocuments removeDuplicates()
  {
    return this;
  }

  public ILoDocuments helperRemoveDuplicates(Document doc)
  {
    return new ConsLoDocuments(doc,this);
  }
  
  public boolean contains(Document doc) {
    return false;
  }
  
  
  
  public ILoDocuments sortByLastName() {
    return this;
  } 

  public ILoDocuments insert(Document doc) {
    return new ConsLoDocuments(doc,this);
  } 


}



class ExamplesDocuments{
  ExamplesDocuments(){}
  
  // Authors
  Author a0 = new Author("name0","surname0");
  Author a1 = new Author("name1","surname1");
  Author a2 = new Author("name2","surname2");
  Author a3 = new Author("name3","surname3");
  Author a4 = new Author("name4","surname4");
  Author a5 = new Author("name5","surname5");
  
  // Books
  Resources b0 = new Book("editor0");
  Resources b1 = new Book("editor1");
  Resources b2 = new Book("editor2");
  Resources b3 = new Book("editor3");
  Resources b4 = new Book("editor4");
  Resources b5 = new Book("editor5");

  // Wiki Articles 
  Resources w0 = new Wiki("link0");
  Resources w1 = new Wiki("link1");
  Resources w2 = new Wiki("link2");
  Resources w3 = new Wiki("link3");
  Resources w4 = new Wiki("link4");
  Resources w5 = new Wiki("link5");

  // EmptyList
  ILoDocuments emptyList = new MtLoDocuments();
  
  
  // Documents
  Document d0 = new Document(a0, "title0", b0, emptyList);
  ILoDocuments l0 =  new ConsLoDocuments(d0,emptyList);

  Document d1 = new Document(a1, "title1", b1, l0);
  ILoDocuments l1 =  new ConsLoDocuments(d0,new ConsLoDocuments(d1,emptyList));

  Document d2 = new Document(a2, "title2", b2, l1);
  ILoDocuments l2 =  new ConsLoDocuments(d0,new ConsLoDocuments(d1,new ConsLoDocuments(d2,emptyList)));

  Document d3 = new Document(a3, "title3", b3, l2);
  ILoDocuments l3 =  new ConsLoDocuments(d0,new ConsLoDocuments(d1,new ConsLoDocuments(d2,new ConsLoDocuments(d3,emptyList))));

  Document d4 = new Document(a4, "title4", w0, l3);
  ILoDocuments l4 =  new ConsLoDocuments(d0,new ConsLoDocuments(d1,new ConsLoDocuments(d2,new ConsLoDocuments(d3,new ConsLoDocuments(d4,emptyList)))));

  Document d5 = new Document(a5, "title5", w1, l4);
  ILoDocuments l5 =  new ConsLoDocuments(d0,new ConsLoDocuments(d1,new ConsLoDocuments(d2,new ConsLoDocuments(d3,new ConsLoDocuments(d4,new ConsLoDocuments(d5,emptyList))))));

  Document d5_2 = new Document(a5, "title5", w1, l4);
  ILoDocuments l5_2 =  new ConsLoDocuments(d0,new ConsLoDocuments(d1,new ConsLoDocuments(d2,new ConsLoDocuments(d3,new ConsLoDocuments(d4,new ConsLoDocuments(d5, new ConsLoDocuments(d5_2,emptyList)))))));

  Document d4_2 = new Document(a4, "title4", w0, l3);
  ILoDocuments l4_2 =  new ConsLoDocuments(d0,new ConsLoDocuments(d1,new ConsLoDocuments(d2,new ConsLoDocuments(d3,new ConsLoDocuments(d4,new ConsLoDocuments(d4_2,emptyList))))));
  
  Document d3_2 = new Document(a3, "title3", b3, l2);
  ILoDocuments l3_2 =  new ConsLoDocuments(d3,new ConsLoDocuments(d3_2,emptyList));

  Document d2_2 = new Document(a2, "title2", b2, l1);
  ILoDocuments l2_2 =  new ConsLoDocuments(d2_2,new ConsLoDocuments(d0,new ConsLoDocuments(d1,new ConsLoDocuments(d2,emptyList))));


  
  boolean testOnlyBooks(Tester t)
  {
    return
        t.checkExpect(l5.onlyBooks(), new ConsLoDocuments(d0,new ConsLoDocuments(d1,new ConsLoDocuments(d2,new ConsLoDocuments(d3,emptyList)))))
        &&
        t.checkExpect(l4.onlyBooks(), l3)
        &&
        t.checkExpect(l3.onlyBooks(), l3)
        &&
        t.checkExpect(l2.onlyBooks(), l2)
        &&
        t.checkExpect(l1.onlyBooks(), l1)
        &&
        t.checkExpect(l0.onlyBooks(), l0)
        ;
  }
  
  
 boolean testRemoveDuplicate(Tester t)
  {
    return
//        t.checkExpect(l2_2.removeDuplicates(), new ConsLoDocuments(d2_2,new ConsLoDocuments(d0,new ConsLoDocuments(d1, emptyList))))
//        &&
        t.checkExpect(l3_2.removeDuplicates(), new ConsLoDocuments(d3, emptyList)) 
        &&
        t.checkExpect(l4_2.removeDuplicates(), l4) 
        &&
        t.checkExpect(l5_2.removeDuplicates(), l5)
        &&
        t.checkExpect(l2.removeDuplicates(), l2)
        &&
        t.checkExpect(l1.removeDuplicates(), l1)
        &&
        t.checkExpect(l0.removeDuplicates(), l0)
        ;
  }
  
 
  boolean testSortByLastName(Tester t)
  {
    return
        t.checkExpect(l0.sortByLastName(),l0)
        &&
        t.checkExpect(l1.sortByLastName(),l1)
        &&
        t.checkExpect(l2_2.sortByLastName(),new ConsLoDocuments(d0,new ConsLoDocuments(d1,new ConsLoDocuments(d2_2,new ConsLoDocuments(d2,emptyList)))))
        ;

  }
 
  
}

