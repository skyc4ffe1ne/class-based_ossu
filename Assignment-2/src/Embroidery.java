import tester.*;
class EmbroideryPiece{

  String name;  
  IMotif motif;

  EmbroideryPiece(String name, IMotif motif)
  {
   this.name = name; 
   this.motif = motif; 
  }
  
  
  double  averageDifficulty()
  {
   return this.motif.averageDifficulty();
  }

  String  embroideryInfo()
  {
   return this.name + ":" + " " + this.motif.embroideryInfo(false);
  }
}


interface IMotif
{
  double averageDifficulty();
  String embroideryInfo(boolean flag);
}



class CrossStichMotif implements IMotif
{
 String description; 
 double difficult; 

  CrossStichMotif(String description, double difficult){
   this.description = description;
   this.difficult = difficult;
  }
  
 public double averageDifficulty()
  {
   return this.difficult;
  }

 public String embroideryInfo(boolean flag)
  {
   if(flag)
   {
     return this.description + " " + "(cross stitch)" + "."; 
   }else {
     return this.description + " " + "(cross stitch)" + "," + " ";
   }

  }

}


class ChainStichMotif implements IMotif
{
 String description; 
 double difficult; 

  ChainStichMotif(String description, double difficult){
   this.description = description;
   this.difficult = difficult;
  }
  
  
 public double averageDifficulty()
  {
   return this.difficult;
  }
  
  public String embroideryInfo(boolean flag)
  {
   if(flag)
   {
     return this.description + " " + "(chain stitch)" + "."; 
   }else {
     return this.description + " " + "(chain stitch)" + "," + " ";
   }
  }
}


class GroupMotif implements IMotif
{
  String description;
//  IMotif motifs;
  ILoMotif motifs;
  
  
  GroupMotif(String description, ILoMotif motifs)
  {
    this.description = description;
    this.motifs = motifs;
  }
  
  
  public double averageDifficulty()
  {
   return this.motifs.averageDifficulty();
  }

  public String embroideryInfo(boolean flag)
  {
    return this.motifs.embroideryInfo();
  }
}



interface ILoMotif
{
  double averageDifficulty();
  double averageDifficultyHelper(double sumAcc,int acc);
  String embroideryInfo();
} 
  

class ConsLoMotif implements ILoMotif
{
  IMotif first;
  ILoMotif rest;
  
  ConsLoMotif(IMotif first, ILoMotif rest)
  {
   this.first = first;  
   this.rest  = rest;  
  }
  
  public double averageDifficulty()
  {
   return this.averageDifficultyHelper(0.0,0);
  }

  
  public double averageDifficultyHelper(double accSum, int acc)
  {
     accSum += this.first.averageDifficulty(); 
     acc++;
     return this.rest.averageDifficultyHelper(accSum,acc);
  }
  
  
  
  public String embroideryInfo()
  {
        if(this.rest instanceof MtLoMotif) {
         return this.first.embroideryInfo(true) + this.rest.embroideryInfo();
        }else
        {
         return this.first.embroideryInfo(false) + this.rest.embroideryInfo();
        }

  }
  
}


class MtLoMotif implements ILoMotif
{
  MtLoMotif(){}
  
  
  public double averageDifficulty()
  {
   return 0.0; 
  }


  public double averageDifficultyHelper(double accSum, int acc)
  {
    return accSum / acc;
  }


  public String embroideryInfo()
  {
    return "";
  }
}


class ExamplesEmbroidery{
  ExamplesEmbroidery(){}
  
  ILoMotif emptyMotif = new MtLoMotif();

  IMotif bird = new CrossStichMotif("bird", 4.5);
  IMotif tree = new ChainStichMotif("tree", 3.0);
  
  IMotif rose = new CrossStichMotif("rose", 5.0);
  IMotif poppy = new ChainStichMotif("poppy", 4.75);
  IMotif daisy = new CrossStichMotif("daisy", 3.2);
  

  ILoMotif l_flowers = new ConsLoMotif(rose,new ConsLoMotif(poppy,new ConsLoMotif(daisy,emptyMotif)));

  IMotif   g_flowers = new GroupMotif("flowers",l_flowers);

  ILoMotif l_nature  = new ConsLoMotif(bird,new ConsLoMotif(tree,new ConsLoMotif(this.g_flowers,emptyMotif)));

  IMotif   g_nature  = new GroupMotif("nature",l_nature);

  
  EmbroideryPiece p1 = new EmbroideryPiece("Pillow Cover", g_nature);
  
  
  boolean testAverageDifficulty(Tester t)
  {
    return t.checkInexact(this.p1.averageDifficulty(), 4.09,0.1);
  }
  
    boolean testEmbroideryInfo(Tester t)
  {
    return t.checkExpect(this.p1.embroideryInfo(), "Pillow Cover: bird (cross stitch), tree (chain stitch), rose (cross stitch), poppy (chain stitch), daisy (cross stitch).");
  }
 
  
}

