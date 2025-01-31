import tester.*;

interface IVehicle {
  double cost(double cp);
}


abstract class AVehicle implements IVehicle
{
  double tank; // gallons

  AVehicle(double tank){
    this.tank = tank;
  }
  
  public double cost(double cp)
  {
    return this.tank * cp;
  }

}


class Car extends AVehicle
{
  
  Car(double tank){
    super(tank);
  }
}

class Truck extends AVehicle
{
  
  Truck(double tank){
    super(tank);
  }
}

class Bus extends AVehicle
{
  
  Bus(double tank){
    super(tank);
  }
}


class ExamplesVehicle {
  ExamplesVehicle() {}

  IVehicle c_0 = new Car(100.00);
  IVehicle t_0 = new Truck(150.00);
  IVehicle b_0 = new Bus(200.00);

  boolean testCost(Tester t) {
    return t.checkInexact(c_0.cost(10.00), 1000.0, 0.01) &&
           t.checkInexact(t_0.cost(5.00), 750.0, 0.01) &&
           t.checkInexact(b_0.cost(2.50), 500.0, 0.01) &&
           t.checkInexact(c_0.cost(0.00), 0.0, 0.01);
  }
}