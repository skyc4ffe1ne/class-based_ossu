class Player{
  int bulletLeft;
  
  Player(int bulletLeft){
    this.bulletLeft = 10;
  }
  
}


// Bullet designed in the world
class Bullet {
  
  WorldImage draw() {
    new LineImage(); //  ???
  }
}


// 1. Every time space bar is pressed, bullet o the player -1
// 2. Check if the bullet are <= 0, if they are finish.