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
    this.flour = water; 
    this.water = water;

    this.yeast = malt; 
    this.salt = salt; 

    this.malt = malt;
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
    this.yeast = yeast * YEAST; 
    this.salt  =  salt * SALT;   
    this.malt  = yeast * MALT;
    
    if(this.salt < 0) {
      throw new IllegalArgumentException("Salt and yeast must be 1/20 of the flour. Flour is to hight");
    }
  }
}

class Utils {
  boolean checkValues(double left, String leftName, double right, String rightName) {
    if(Math.abs(right - left) < 0.001)  {
      return true;
    }else {
      throw new IllegalArgumentException(leftName + " and " + rightName + " must have the same value");
    }
  }
}

class ExamplesBagelRecipe{
  ExamplesBagelRecipe(){}
  
  BagelRecipe br_1 = new BagelRecipe(20.0, 20.0, 30.0, 40.0, 40.0);
  BagelRecipe br_2 = new BagelRecipe(1000.0, 10.0);
  BagelRecipe br_3 = new BagelRecipe(500.0, 5.0);
  BagelRecipe br_4 = new BagelRecipe(100.0, 1.0);

  boolean testException(Tester t) {
    return
        t.checkConstructorException(new IllegalArgumentException("Salt and yeast must be 1/20 of the flour. Flour is to hight"),
        "BagelRecipe", 500.0, 100.0)
        &&
        t.checkConstructorException(new IllegalArgumentException("Salt and yeast must be 1/20 of the flour. Flour is to hight"),
        "BagelRecipe", 500.0, 30.0)
        &&
        t.checkConstructorException(new IllegalArgumentException("Salt and yeast must be 1/20 of the flour. Flour is to hight"),
        "BagelRecipe", 200.0, 20.0)
        ;
  }

}