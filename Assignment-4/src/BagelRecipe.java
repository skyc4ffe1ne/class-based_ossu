import tester.*;

class BagelRecipe{
  double flour, water, yeast, salt, malt;
  
  double TEASPOONS = 48.0; // cup of teaspoons
  double FLOUR = 4.25; // cup of flour 
  double YEAST = 5.0 / TEASPOONS; // cup of yeast
  double SALT  = 10.0 / TEASPOONS; // cup of salt 
  double MALT  = 11.0 / 5.0; // Malt == YEAST 
  
  BagelRecipe(double flour,double water,double yeast,double salt,double malt)
  {
    this.flour = new Utils().checkValues(flour,"Flour", water, "Water"); 
    this.water = water;
    this.yeast = new Utils().checkValues(yeast,"Yeast", malt, "Malt"); 
    this.salt  = new Utils().checkValues(salt + yeast, "Salt", flour, "Flour"); 
    this.malt  = malt;
    
  }
  
  
  BagelRecipe(double flour, double yeast)
  {
    this.flour = water; 
    this.water = flour;
    this.yeast = yeast; 
    this.salt = (flour / 20) - yeast;   
    this.malt = yeast;
  }
  
  BagelRecipe(double flour, double yeast, double salt)
  {
    this.flour = flour * FLOUR; 
    this.water = flour * FLOUR;
    this.yeast = new Utils().checkValues(yeast * YEAST,"Yeast", yeast * YEAST, "Malt"); 
    this.salt  = new Utils().checkValues(salt * SALT, "Salt", flour * FLOUR / 20, "Flour");   
    this.malt  = yeast * MALT;
  }
  
  
  boolean sameRecipe(BagelRecipe other) {
    return Math.abs(this.flour - other.flour) < 0.001 &&
           Math.abs(this.water - other.water) < 0.001 &&
           Math.abs(this.yeast - other.yeast) < 0.001 &&
           Math.abs(this.salt - other.salt) < 0.001 &&
           Math.abs(this.malt - other.malt) < 0.001;
}

}

class Utils {
  double checkValues(double left, String leftName, double right, String rightName) {
    if(leftName.equals("Salt")) {
        if(Math.abs(left - (right / 20)) > 0.001) {
          throw new IllegalArgumentException("Salt and Yeast must be 1/20 of the Flour. Flour is to hight");
        }else {
          return left;
        }
    }else {
      if(Math.abs(right - left) < 0.001)  {
        return left;
      }else {
        throw new IllegalArgumentException(leftName + " and " + rightName + " must have the same value");
      }
    }
  }
}

class ExamplesBagelRecipe{
  ExamplesBagelRecipe(){}
  
  BagelRecipe br_1 = new BagelRecipe(100.0, 100.0, 5.0, 0.0, 5.0);
  BagelRecipe br_2 = new BagelRecipe(1000.0, 10.0);
  BagelRecipe br_3 = new BagelRecipe(500.0, 5.0);
  BagelRecipe br_4 = new BagelRecipe(100.0, 1.0);
  BagelRecipe br_5 = new BagelRecipe(2.0, 6.0, 0.1); 
  BagelRecipe br_6 = new BagelRecipe(40, 40, 2, 0, 2);
  BagelRecipe br_7 = new BagelRecipe(40, 40, 2.001, 0, 2);

  boolean testException(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException("Salt and Yeast must be 1/20 of the Flour. Flour is to hight"),
        "BagelRecipe", 100.0, 100.0, 3.0, 1.0, 3.0)
        &&
        t.checkConstructorException(
                new IllegalArgumentException("Flour and Water must have the same value"),
                "BagelRecipe", 200.0, 300.0, 5.0, 2.5, 5.0) 
        &&
        t.checkConstructorException(
                new IllegalArgumentException("Yeast and Malt must have the same value"),
                "BagelRecipe", 400.0, 400.0, 10.0, 2.0, 12.0)
        &&
        t.checkConstructorException(
            new IllegalArgumentException("Salt and Yeast must be 1/20 of the Flour. Flour is to hight"),
            "BagelRecipe", 2.0, 6.0, 1.0)
        &&
        t.checkConstructorException(
            new IllegalArgumentException("Salt and Yeast must be 1/20 of the Flour. Flour is to hight"),
            "BagelRecipe", 2.0, 6.0, 2.0)
        ;
  }
  
  boolean testSameRecipe(Tester t) {
    return 
        t.checkExpect(br_1.sameRecipe(br_2), false)
        &&
        t.checkExpect(br_2.sameRecipe(br_3), false)
        &&
        t.checkExpect(br_6.sameRecipe(br_7), true)
        ;
  }
}