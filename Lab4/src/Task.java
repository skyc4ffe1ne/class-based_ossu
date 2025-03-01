import tester.*;

class Task {
 int id;  
 IPre pre;

 Task(int id, IPre pre){
    this.id = id;
    this.pre = pre;
 }

  boolean hasCycle(ITask tasks)
  {
    return pre.hasCycle(id, tasks);
  }
  
  int getTask() {
    return pre.getPre();
  }
} 

interface IPre{
  boolean hasCycle(int id, ITask tasks);
  boolean helperHasCycle(int id, ITask tasks);
  boolean hasId(int id);
  int getPre();
}

class ConsPre implements IPre{
  int first;
  IPre rest;
  
  ConsPre(int first, IPre rest){
    this.first = first;
    this.rest = rest;
  }
  
  public boolean hasCycle(int firstId, ITask tasks) {
    return  this.helperHasCycle(firstId, tasks);
  }
  
  public boolean helperHasCycle(int firstId, ITask tasks){
    if(tasks.getTask() == firstId){
      return true;
    }else {
      return this.rest.helperHasCycle(firstId, tasks);
    }
  }
  
  public int getPre()
  {
    return this.first;
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

  public boolean hasCycle(int id , ITask tasks)
  {
    return false; 
  }

  public boolean helperHasCycle(int id, ITask tasks)
    {

      return false;
    }
  
   public boolean hasId(int id) {
    return false;
   }

   public int getPre() {
    return 0; 
   }
}


interface ITask{
 boolean hasCycle();
 int getTask();
}

class ConsTask implements ITask{
  Task first;
  ITask rest;
  
  ConsTask(Task first, ITask rest){
    this.first = first;
    this.rest = rest;
  }

  public boolean hasCycle()
  {
    if (this.first.hasCycle(this.rest)) { 
      return true;
    }else {
      return this.rest.hasCycle();
    } 
  }
  
  public int getTask() {
    return first.getTask(); 
  }
  
  

}


class MtLoTask implements ITask{
  MtLoTask(){}
  
  public boolean hasCycle()
  {
    return false;
  }
 
  public int getTask() {
    return 0;
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

  
  Task t_7 = new Task(8, new ConsPre(9, emptyPre));
  Task t_8 = new Task(9, new ConsPre(10, emptyPre));
  IPre pre_4 = new ConsPre(11, emptyPre);
  Task t_9 = new Task(10, pre_4);
  IPre pre_5 = new ConsPre(12,emptyPre);
  Task t_10 = new Task(11, pre_5);
  Task t_11 = new Task(12, new ConsPre(13, emptyPre));
  Task t_12 = new Task(13, new ConsPre(12, emptyPre));

  ITask lt_4 = 
      new ConsTask(t_7,
          new ConsTask(t_8,
              new ConsTask(t_9,
                  new ConsTask(t_10,
                      new ConsTask(t_11,
                          new ConsTask(t_12,
                                   emptyTask))))));
      

  // Test the method hasCycle
  boolean testHasCycle(Tester t) {
    return 
        t.checkExpect(lt_3.hasCycle(), true)
        &&
        t.checkExpect(lt_1.hasCycle(), false)
        &&
        t.checkExpect(lt_2.hasCycle(), false)
        &&
        t.checkExpect(lt_4.hasCycle(), true)
        ;
  }
}