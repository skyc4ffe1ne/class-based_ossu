import tester.Tester;

interface IEntertainment {
    //compute the total price of this Entertainment
    double totalPrice();
    //computes the minutes of entertainment of this IEntertainment
    int duration();
    //produce a String that shows the name and price of this IEntertainment
    String format();
    //is this IEntertainment the same as that one?
    boolean sameEntertainment(IEntertainment that);
    boolean isPodcast(IEntertainment that); 
    boolean isMagazine(IEntertainment that); 
    boolean isTVSeries(IEntertainment that); 
 
    
    // We assume a magazine provides 5 minutes of entertainment per page and TV series and podcasts provide 50 minutes of entertainment per episode.
    int MINUTE_M = 5; 
    int MINUTE_P_S = 50; 
}

abstract class AEntertainment implements IEntertainment{
  String name;
  double price;
  int installments;

  AEntertainment(String name, double price, int installments){
   this.name = name; 
   this.price = price; 
   this.installments = installments; 
  }

  public double totalPrice() {
    return this.price * this.installments;
  }
  
  public int duration() {
    return MINUTE_P_S * installments;
  }
  
  public String format() {
    return this.name + ", " + this.price + ".";
  }
  
  public boolean sameEntertainment(IEntertainment that) {
    return false;
  }

  public boolean isPodcast(IEntertainment that) {
      return false;
  }

  public boolean isMagazine(IEntertainment that) {
      return false;
  }

  public boolean isTVSeries(IEntertainment that) {
      return false;
  }

}

class Magazine extends AEntertainment{
  String genre;
  int pages;
  
  Magazine(String name, double price, String genre, int pages, int installments){
    super(name,price,installments);
    this.genre = genre;
    this.pages = pages;
  }
  
  //computes the minutes of entertainment of this Magazine, (includes all installments)
  public int duration() {
      return MINUTE_M * pages;
  }

  public boolean sameEntertainment(IEntertainment that) {
    return that.isMagazine(this);
}

  public boolean isMagazine(IEntertainment that) {
    return true;
  }

}

class TVSeries extends AEntertainment{
  String corporation;
  
  TVSeries(String name, double price, int installments, String  corporation){
    super(name,price,installments);
    this.corporation = corporation;
  }

  public boolean sameEntertainment(IEntertainment that) {
    return that.isTVSeries(this);
  } 
  
  public boolean isTVSeries(IEntertainment that) {
    return true;
  }
}

class Podcast extends AEntertainment{
  
  Podcast(String name, double price, int installments){
    super(name,price,installments);
  }

  public boolean sameEntertainment(IEntertainment that) {
    return that.isPodcast(this);
  } 

  public boolean isPodcast(IEntertainment that) {
    return true;
  }
}

class ExamplesEntertainment {
  IEntertainment rollingStone = new Magazine("Rolling Stone", 2.55, "Music", 60, 12);
  IEntertainment houseOfCards = new TVSeries("House of Cards", 5.25, 13, "Netflix");
  IEntertainment serial = new Podcast("Serial", 0.0, 8);
  
  IEntertainment m_1 = new Magazine("Magazine", 4.00, "Cards", 35, 2);
  IEntertainment s_1 = new TVSeries("Series", 5.00, 12, "Netflix");
  IEntertainment p_1 = new Podcast("Podcast", 1.0, 8);
  
  //testing total price method
  boolean testTotalPrice(Tester t) {
      return
         t.checkInexact(this.rollingStone.totalPrice(), 2.55*12, .0001) 
         &&   
         t.checkInexact(this.houseOfCards.totalPrice(), 5.25*13, .0001)
         &&
         t.checkInexact(this.serial.totalPrice(), 0.0, .0001)
         &&
         t.checkInexact(this.m_1.totalPrice(), 4.0*2, .0001) 
         &&
         t.checkInexact(this.s_1.totalPrice(), 5.00*12, .0001)
         &&
         t.checkInexact(this.p_1.totalPrice(), 1.0*8, .0001)
      ;
  }
  
  //testing duration method
  boolean testDuration(Tester t) {
    int MINUTE_P_S = 50;
    int MINUTE_M = 5;
      return
         t.checkExpect(this.rollingStone.duration(), MINUTE_M * 60) 
         &&   
         t.checkExpect(this.houseOfCards.duration(), MINUTE_P_S * 13)
         &&
         t.checkExpect(this.serial.duration(), MINUTE_P_S * 8)
         &&
         t.checkExpect(this.m_1.duration(), MINUTE_M * 35) 
         &&
         t.checkExpect(this.s_1.duration(), MINUTE_P_S * 12)
         &&
         t.checkExpect(this.p_1.duration(), MINUTE_P_S * 8)
      ;
  }
  
  //testing format method
  boolean testFormat(Tester t) {
      return
         t.checkExpect(this.rollingStone.format(), "Rolling Stone, 2.55.") 
         &&   
         t.checkExpect(this.houseOfCards.format(), "House of Cards, 5.25.")
         &&
         t.checkExpect(this.serial.format(), "Serial, 0.0.")
         &&
         t.checkExpect(this.m_1.format(), "Magazine, 4.0.") 
         &&
         t.checkExpect(this.s_1.format(), "Series, 5.0.")
         &&
         t.checkExpect(this.p_1.format(), "Podcast, 1.0.")
      ;
  }
  
  //testing format method
  boolean testSameEntertainmnet(Tester t) {
    return
        t.checkExpect(houseOfCards.sameEntertainment(s_1), true)
        &&
        t.checkExpect(houseOfCards.sameEntertainment(m_1), false)
        &&
        t.checkExpect(rollingStone.sameEntertainment(m_1), true)
        &&
        t.checkExpect(serial.sameEntertainment(rollingStone), false)
        &&
        t.checkExpect(serial.sameEntertainment(p_1), true)
        ;
  }
}
