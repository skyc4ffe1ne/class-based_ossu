import tester.*;                // The tester library
import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.funworld.*;      // the abstract World class and the big-bang library
import javalib.worldcanvas.WorldCanvas;

import java.awt.Color;          // general colors (as triples of red,green,blue values)
                                // and predefined colors (Color.RED, Color.GRAY, etc.)

 
interface ITree {
  WorldImage  draw();
}
 
class Leaf implements ITree {
  int size; // represents the radius of the leaf
  Color color; // the color to draw it
  
  Leaf(int size, Color color)
  {
    this.size = size;
    this.color = color;
  }
  
  public WorldImage draw()
  {
    return 
          new CircleImage(this.size, OutlineMode.SOLID, this.color);
  }
  
}
 

class Stem implements ITree {
  // How long this stick is
  int length;
  // The angle (in degrees) of this stem, relative to the +x axis
  double theta;
  // The rest of the tree
  ITree tree;

  Stem(int length, double theta, ITree tree)
  {
    this.length = length; 
    this.theta = theta; 
    this.tree = tree; 
  }

  
  public WorldImage draw()
  {
    WorldImage stem = 
              new LineImage(
                  new Posn(
                      0, 
                      this.length 
                  ),
                  Color.GRAY
              )
              .movePinholeTo(new Posn(0, - this.length / 2));

    WorldImage restTree = new OverlayImage(this.tree.draw(), stem);
    return restTree;
  }
}

 
class Branch implements ITree {
  // How long the left and right branches are
  int leftLength;
  int rightLength;
  // The angle (in degrees) of the two branches, relative to the +x axis,
  double leftTheta;
  double rightTheta;
  // The remaining parts of the tree
  ITree left;
  ITree right;
  
  Branch(int leftLength, int rightLength, double leftTheta, double rightTheta, ITree left, ITree right)
  {
    this.leftLength = leftLength;
    this.rightLength = rightLength;
    this.leftTheta = leftTheta;
    this.rightTheta = rightTheta;
    this.left = left;
    this.right = right;
  }

   public WorldImage draw()
  {
    int x1 = (int) Math.round(this.leftLength * Math.cos(Math.toRadians(this.leftTheta)));
    int y1 = (int) Math.round(this.leftLength * Math.sin(Math.toRadians(this.leftTheta)));
    
    WorldImage leftBranch = 
        new OverlayImage(
            left.draw(),
            new LineImage(
              new Posn(x1, y1),
              Color.GRAY
              ).movePinhole(x1 / 2, y1 / 2)
            ).movePinhole(- x1, - y1);

    int x2 = (int) Math.round(this.rightLength * Math.cos(Math.toRadians(this.rightTheta)));
    int y2 = (int) Math.round(this.rightLength * Math.sin(Math.toRadians(this.rightTheta)));
   
    WorldImage rightBranch =
        new OverlayImage(
            right.draw(),
            new LineImage(
                new Posn(x2, y2),
                Color.GRAY).movePinhole(x2 / 2, y2 / 2)
            ).movePinhole(- x2, - y2);

    return
           new OverlayImage(leftBranch, rightBranch);
  }

}

class ExamplesTree{
  ExamplesTree(){}

  // Tree
  ITree tree1 = new Branch(30, 30, 135, 40, new Leaf(10, Color.RED), new Leaf(15, Color.BLUE));
  ITree tree2 = new Branch(30, 30, 115, 65, new Leaf(15, Color.GREEN), new Leaf(8, Color.ORANGE));

  // Leaf 
  ITree l_0 = new Leaf(10, Color.RED);
  ITree l_1 = new Leaf(15, Color.BLUE);
  ITree l_2 = new Leaf(15, Color.GREEN);
  ITree l_3 = new Leaf(8, Color.ORANGE);

  // Stem
  ITree s_0 = new Stem(50, 90, tree1);
  ITree s_1 = new Stem(40, 90, tree2);

  // Branch 
  ITree b_0 = new Branch(30, 30, 135, 40, l_0, l_1);
  ITree b_1 = new Branch(30, 30, 115, 60, l_2, l_3);

  boolean testImages(Tester t) {
    return t.checkExpect(new RectangleImage(30, 20, OutlineMode.SOLID, Color.GRAY),
                         new RectangleImage(30, 20, OutlineMode.SOLID, Color.GRAY));
  } 
  
  boolean testDrawTree(Tester t) {
    WorldCanvas c = new WorldCanvas(500, 500);
    WorldScene s = new WorldScene(500, 500);

    int ORIGINPOINTX = 500 / 2;
    int ORIGINPOINTY = 500 / 2;

    return 
        c.drawScene(s.placeImageXY(s_0.draw(), ORIGINPOINTX, ORIGINPOINTY))
        && c.show()
       ;
  } 
}