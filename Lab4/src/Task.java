
import tester.*;

interface IPre{
  boolean hasCycle();
  boolean helperHasCycle(int id);
  boolean hasId(int id);
}


class Task {
 int id;  
 IPre pre;

 Task(int id, IPre pre){
    this.id = id;
    this.pre = pre;
 }

  boolean hasCycle()
  {
    return pre.hasCycle();
  }
} 

class ConsPre implements IPre{
  int first;
  IPre rest;
  
  ConsPre(int first, IPre rest){
    this.first = first;
    this.rest = rest;
  }
  
  
  public boolean hasCycle()
  {
    return this.rest.helperHasCycle(this.first);
  }
  
  public boolean helperHasCycle(int id)
  {
    if (this.hasId(id)) {
      return true;
    }else {
      return this.rest.helperHasCycle(this.first);
    }
  }
  

public boolean hasId(int id)
{
  if(this.first == id) {
    return true;
  }else {
    return this.rest.hasId(id);
  }
}

}

class MtLoPre implements IPre{
MtLoPre(){}

public boolean hasCycle()
{
  return false; 
}

public boolean helperHasCycle(int id)
  {
    return false;
  }
  
  public boolean hasId(int id) {
    return false;
  }
}


interface ITask{
 boolean hasCycle();
}

class ConsTask implements ITask{
  Task first;
  ITask rest;
  
  ConsTask(Task first, ITask rest){
    this.first = first;
    this.rest = rest;
  }
  
  public boolean hasCycle() {
    return first.hasCycle();
  }

}


class MtLoTask implements ITask{
  MtLoTask(){}
  
  public boolean hasCycle()
  {
    return false;
  }
}

class ExamplesTask{
  ExamplesTask(){}
  
  IPre emptyPre = new MtLoPre();

  // Independent tasks
  Task t_0 = 
      new Task(1, emptyPre);  
  
  Task t_1 = 
      new Task(2, emptyPre);

  Task t_2 = 
      new Task(3, emptyPre);


// List of prerequisites & dependent tasks 
  IPre pre_0 =
      new ConsPre(1,
          new ConsPre(2,
              new ConsPre(3, 
                  emptyPre)));

  Task t_3 = 
      new Task(4, pre_0);

  IPre pre_1 =
      new ConsPre(1,
          new ConsPre(2,
              new ConsPre(3,
                  new ConsPre(4,
                      emptyPre))));
  Task t_4 = 
      new Task(5, pre_1);
 

  // List of tasks
  ITask emptyTask = new MtLoTask();

  ITask lt_0 =
      new ConsTask(t_0,
          new ConsTask(t_1,
              new ConsTask(t_2,
                  emptyTask)));

  ITask lt_1 = new ConsTask(t_3, lt_0);

  ITask lt_2 = new ConsTask(t_4, lt_1);
  
  
  IPre pre_2 = new ConsPre(7, emptyPre);
  Task t_5 = new Task(6, pre_2);
  IPre pre_3 = new ConsPre(6, emptyPre);
  Task t_6 = new Task(7, pre_3);

  ITask lt_3 = new ConsTask(t_5, new ConsTask(t_6, emptyTask));

  
  // Test the method hasCycle
  boolean testHasCycle(Tester t) {
    return 
        t.checkExpect(pre_0.hasCycle(), false)
        &&
        t.checkExpect(pre_1.hasCycle(), false)
        &&
        t.checkExpect(lt_3.hasCycle(), true);
  }
}