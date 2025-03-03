import tester.*;

class Runner {
  String name;
  int age, bib, pos, time;
  boolean isMale;
  
  Runner(String name, int age, int bib, boolean isMale,  int pos, int time){
    this.name = name;
    this.age = age;
    this.bib = bib;
    this.isMale = isMale;
    this.pos = pos;
    this.time = time;
  }
  
  boolean isMale() {
    return this.isMale;
  }
  
    boolean isFemale() {
    return !this.isMale;
  }

    boolean isPosUnder50() {
    return this.bib <= 50;
  }

}

interface IRunnerPredicate{
  boolean apply(Runner r);
}

// Helper classes
class RunnerIsMale implements IRunnerPredicate{
  public boolean apply(Runner r) {return r.isMale;}
}
class RunnerIsFemale implements IRunnerPredicate{
  public boolean apply(Runner r) {return !r.isMale;}
}
class RunnerIsInFirst50 implements IRunnerPredicate{
  public boolean apply(Runner r) {return r.pos <= 50;}
}

class RunnerFinishedUnder4Hours implements IRunnerPredicate{
  public boolean apply(Runner r) {return r.time < 240;}
}

class AndPredicate implements IRunnerPredicate{
  IRunnerPredicate left,right;

  AndPredicate(IRunnerPredicate left, IRunnerPredicate right){
    this.left = left;
    this.right = right;
  }

  public boolean apply(Runner r) {return !r.isMale && r.time < 240;}
}


interface ILoRunner {
  // From this :
//  ILoRunner findAllMaleRunners();
//  ILoRunner findAllFemaleRunners();
//  ILoRunner findAllUnderPos50();
  // To this :
  ILoRunner find(IRunnerPredicate pred);
}


class MtLoRunner implements ILoRunner {
  
  public ILoRunner find(IRunnerPredicate pred){
    return this;
  }

}


class ConsLoRunner implements ILoRunner {
  Runner first;
  ILoRunner rest;

  ConsLoRunner(Runner first, ILoRunner rest){
    this.first = first;
    this.rest = rest;
  }
  
  // From 3 of this :
//  public ILoRunner findAllMaleRunners(){
//    if(this.first.isMale()) {
//      return new ConsLoRunner(this.first, this.rest.findAllMaleRunners());
//    }else {
//      return this.rest.findAllMaleRunners();
//    }
//  }
//
  
  // To this :
  public ILoRunner find(IRunnerPredicate pred) {
    if(pred.apply(this.first)) {
        return new ConsLoRunner(this.first, this.rest.find(pred));
    }else {
      return this.rest.find(pred);
    }
  }

}


class ExamplesRunner {
  ExamplesRunner(){}
  Runner johnny = new Runner("Kelly", 97, 999, true, 30, 360);
  Runner frank  = new Runner("Shorter", 32, 888, true, 245, 130);
  Runner bill = new Runner("Rogers", 36, 777, true, 119, 129);
  Runner joan = new Runner("Benoit", 29, 444, false, 18, 155);
   
  ILoRunner mtlist = new MtLoRunner();
  ILoRunner list1 = new ConsLoRunner(johnny, new ConsLoRunner(joan, mtlist));
  ILoRunner list2 = new ConsLoRunner(frank, new ConsLoRunner(bill, list1));

  // Test find methods
  boolean testFindMethods(Tester t) {
    return
      t.checkExpect(this.list2.find(new RunnerIsFemale()),
                    new ConsLoRunner(this.joan, new MtLoRunner())) 
      &&
      t.checkExpect(this.list2.find(new RunnerIsMale()),
                    new ConsLoRunner(this.frank,
                      new ConsLoRunner(this.bill,
                        new ConsLoRunner(this.johnny, new MtLoRunner()))))
      &&
      t.checkExpect(this.list2.find(new RunnerFinishedUnder4Hours()),
                    new ConsLoRunner(this.frank,
                      new ConsLoRunner(this.bill,
                        new ConsLoRunner(this.joan, new MtLoRunner()))))
      &&
      t.checkExpect(this.list2.find(new AndPredicate(new RunnerIsFemale(), new RunnerFinishedUnder4Hours())),
                        new ConsLoRunner(this.joan, new MtLoRunner()))
      ;
  }
}