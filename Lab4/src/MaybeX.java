import tester.Tester;

// 1. MaybeX
interface IMaybeInt{
}

// Represent an absent value 
class MtInt implements IMaybeInt{
  MtInt(){}
}

class Int implements IMaybeInt{
 int value; 
 
 Int(int value){
   this.value = value;
 }

}

// 2. ListOfIntegers

interface ILoIntegers{
  int moreRepetitive();
  int count(int currentAcc, int accTem, int number);
  int getFirst();
  int compare(int currentAcc, int preAcc, int preNumber, int currentNumber);
  int compareNumber(int currentNumber, int preNumber); 
  int compareAcc(int currentAcc, int prevAcc); 
}


class ConsLoInteger implements ILoIntegers{
 int first; 
 ILoIntegers rest;
 
 
 ConsLoInteger(int first, ILoIntegers rest){
   this.first = first;
   this.rest = rest;
 }
 
 public int moreRepetitive() {
   return this.count(0, 0, 0);
 }
 
 public int count(int currentAcc, int preAcc, int preNumber) {
  if(this.first == this.rest.getFirst()) {
    return this.rest.count(currentAcc + 1, preAcc, preNumber);
  }else {
    if(this.rest instanceof MtLoInteger) {
      return compare(currentAcc, preAcc, preNumber, this.first);
    }
    return this.rest.count(0, this.compareAcc(currentAcc, preAcc), this.compare(currentAcc, preAcc, preNumber, this.first));
  }
 }
 
 public int compare(int currentAcc, int preAcc, int preNumber, int currentNumber) {

   if(currentAcc > preAcc) {
     return currentNumber;
   }else if(currentAcc == preAcc) {
     return this.compareNumber(currentNumber, preNumber);
   }else {
     return preNumber;
   }
 }
 
 public int compareNumber(int currentNumber, int preNumber) {
   return currentNumber > preNumber ? currentNumber : preNumber;
 }
 
 public int compareAcc(int currentAcc, int prevAcc) {
   return currentAcc > prevAcc ? currentAcc : prevAcc;
 }
 
 public int getFirst() {
   return this.first;
 }
 
}

class MtLoInteger implements ILoIntegers{
  MtLoInteger(){}
  
  public int moreRepetitive() {
   return 0;
 }
 
 public int count(int currentAcc, int preAcc, int number) {
   return 0;
 }
 
 public int compare(int currentAcc, int preAcc, int preNumber, int currentNumber) {
   return 0;
 }
 
 public int compareNumber(int currentNumber, int preNumber) {
   return 0;
 }
 
 public int getFirst() {
   return 0;
 }
 
 public int compareAcc(int currentAcc, int prevAcc) {
   return 0;
 }

}



class ExamplesIntegers{
  ExamplesIntegers(){}
  
  ILoIntegers emptyList = new MtLoInteger();

  ILoIntegers l_0 = new ConsLoInteger(1,new ConsLoInteger(2,new ConsLoInteger(3,new ConsLoInteger(3,new ConsLoInteger(3, emptyList)))));
  ILoIntegers l_1 = new ConsLoInteger(1,new ConsLoInteger(1,new ConsLoInteger(1,new ConsLoInteger(1,new ConsLoInteger(3, emptyList)))));
  ILoIntegers l_2 = new ConsLoInteger(1,new ConsLoInteger(1,new ConsLoInteger(5,new ConsLoInteger(5,new ConsLoInteger(5,new ConsLoInteger(4,new ConsLoInteger(3,new ConsLoInteger(4,new ConsLoInteger(4,new ConsLoInteger(4, emptyList ))))))))));
  
  boolean testMoreRepetitive(Tester t) {
    return 
        t.checkExpect(l_2.moreRepetitive(), 5)
        &&
        t.checkExpect(l_0.moreRepetitive(), 3)
        &&
        t.checkExpect(l_1.moreRepetitive(), 1)
        &&
        t.checkExpect(emptyList.moreRepetitive(), 0)
        ;
  }

}
