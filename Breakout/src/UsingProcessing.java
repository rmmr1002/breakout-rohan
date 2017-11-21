import java.util.ArrayList;

import processing.core.PApplet;

public class UsingProcessing extends PApplet {
	
	//import processing.sound.*;     // You might have to install the Sound Library for this to work
	//SoundFile file;

	float x=250, y=390, dx=0, dy=0;   // Ball initial coordinates
	int e=250, f=480;                 // Paddle initial coordinates
	int brickcount=0;

	final int blength=50;              //Brick lenght and width,
	final int bwidth=20;
	final int no_of_columns=500;
	final int ellipse_diameter=20;

	int level_count=0;                 // Counts current level
	int no_of_rows=200;  //y coordinate of last row.

	int frame_count=0;              //Current frame
	int count=3;                    // How many balls left
	int score=0;                    // How many bricks have been removed


	ArrayList<rectangle> r=new ArrayList<rectangle>();        // Arraylist contains each brick.

	class rectangle {                                                // Class that defines each brick.
	  public int x1;
	  public int y1;


	  public rectangle(int a, int b) {
	    x1=a;
	    y1=b;
	  }
	}


	public void keyPressed() {
	  if (key == 'r') {
	    reset();
	  } else if (key == CODED) {                  //To move paddle with arrows
	    if (keyCode == LEFT) {
	      e-=10;
	    } else if (keyCode == RIGHT) {
	      e+=10;
	    }
	  }
	}

	public void exit() {                                     // When ball goes out of bounds
	  count--;
	  if (count==0) {
	    println("game over");
	    super.exit();
	  }//let processing carry with it's regular exit routine
	  else
	    reset();
	}


	public void reset() {                              // Restores ball to central position
	  dx=random(-2, 2);
	  dy=random(1, 2);
	  x=e;
	  y=370;
	  ellipse(x, y, ellipse_diameter, ellipse_diameter);
	}


	public void flip(int a)                          // Called when ball needs to bounce
	{ 
	  switch(a) {
	  case 1:               // Bounce for left/right walls
	    dx=-1*dx;
	    break;
	  case 2:             //Bounce for up/down of walls
	    dy=-1*dy;
	    break;
	  case 3:    // Bounce for left corner of paddle
	    dx-=2;
	    dy=-1*dy;
	    break;
	  case 4:         // Bounce for right corner of paddle
	    dx+=2;
	    dy=-1*dy;
	    break;
	  case 5:           // Bounce for middle hotspot of paddle
	    dy+=1;
	    dx=random(2);
	    dy=-1*dy;
	    break;
	  }
	  x=x+dx;                 // To update new x and y
	  y=y+dy;
	}
    public void settings() {
    	size(500, 600);
    }

	public void setup() {
	  
	  //file=new SoundFile(this, "./Super Mario Bros. Original Theme by Nintendo.mp3");
	  //file.loop();
	  for (int i=0; i<no_of_rows; i+=bwidth)                        // Adding bricks
	    for (int j=0; j<no_of_columns; j+=blength) {
	      r.add(new rectangle(j, i));
	    } 

	  brickcount=r.size();  //Count of bricks
	}


	public void draw()
	{
	  if (frame_count<180) {                                              //Welcome screen, first run.
	    background(255, 215, 50);
	    textSize(64);

	    fill(255, 0, 0);
	    text("Welcome", 100, 150);
	    fill(0, 0, 255);
	    String s="Press R to play, Use Arrows to move paddle";
	    textSize(20);
	    text(s, 40, 200, 500, 50);

	    int x=60;                                // Drawing Welcome X
	    int y=250;
	    fill(0);
	    for (x=70; x<=410; x+=8, y+=8)
	      ellipse(x, y, 10, 10);

	    int w=250;
	    for (int z=410; z>=70; z-=8, w+=8)
	      ellipse(z, w, 10, 10);

	    frame_count++;
	  } else {

	    if (level_count==0)           //To determie what level you're on
	      no_of_rows=200;
	    else if (level_count==1)
	      no_of_rows=300;
	    else if (level_count==2);
	    no_of_rows=400;
	    background(0);

	    fill(255);
	    textSize(24);
	    text("Balls:", 350, 560);
	    text(count, 410, 560);
	    text("Score:", 50, 560);
	    text(score, 120, 560);
	    text("Level:", 210, 560);
	    text(level_count+1, 275, 560);

	    for (int c=0; c<r.size(); c++)                            //Drawing bricks
	    {  
	      float a=random(0, 255);
	      float b=random(0, 255);
	      float d=random(0, 255);
	      fill(a, b, d);
	      stroke(a, b, d);
	      rect(r.get(c).x1, r.get(c).y1, blength, bwidth);
	    }

	    x=x+dx;         // Setting initial x vector
	    y=y+dy;         // Setting initial y vector

	    fill(255);
	    rect(e, f, 100, 2);           // Paddle

	    if (dx>=0 && dy<=0)           // Ball moving upward and right
	    {
	      if (x>500)
	        flip(1);
	      else if (y<0)
	        flip(2);
	      else {
	        if (y<=499)
	          ellipse(x, y, ellipse_diameter, ellipse_diameter);
	      }
	    } else if (dx<=0 && dy>=0)              // Ball moving downward/left
	    {
	      if (x<0)
	        flip(1);
	      // when to bounce
	      else if ((((x>=e)&&(x<=(e+45)))&&(abs(f-y)<2))) // Ball hitting the paddle left
	        flip(3);
	      else if ((x>=e+55)&&(x<=(e+100))&&(abs(f-y)<2)) // Ball hitting the paddle right
	        flip(4);
	      else if ((((x>e+45)&&(x<(e+55)))&&(abs(f-y)<2))) // Ball hitting the paddle middle 
	        flip(5);
	      else {                           // not bouncing going down
	        if (y<=499)
	          ellipse(x, y, ellipse_diameter, ellipse_diameter);            
	        else
	          exit();                        // Out of bounds
	      }
	    } else if (dx<=0 && dy<=0)      // ball moving left and up
	    {
	      if (x<0)     
	        flip(1);
	      else if (y<0)
	        flip(2);
	      else {
	        if (y<=499)
	          ellipse(x, y, ellipse_diameter, ellipse_diameter);
	      }
	    } else                                     // Ball moving right/down
	    {
	      if (x>500)
	        flip(1);
	      else if ((x>=e)&&(x<=(e+100))&&(f-y<5))
	        flip(2);
	      else {
	        if (y<=499)
	          ellipse(x, y, ellipse_diameter, ellipse_diameter);
	        else
	          exit();                     // Out of bounds
	      }
	    }

	    for (int s=0; s<r.size(); s++) {      // Checking if ball is hitting a brick using boundary of brick
	      if ( ( (x>r.get(s).x1)&&(x<=r.get(s).x1+blength) ) && (abs(y-r.get(s).y1)<1.5))       //Upperwall brick
	      {
	        flip(2);
	        r.remove(s);
	        score++;
	      } else if ( ( (x>r.get(s).x1)&&(x<=r.get(s).x1+blength) ) && (abs(y-(r.get(s).y1+bwidth))<1.5))   // Bottom wall
	      {
	        flip(2);
	        r.remove(s);
	        score++;
	      } else if ( ( (y>r.get(s).y1)&&(y<=r.get(s).y1+bwidth) ) && ( (abs(x-r.get(s).x1)<1.5)))  // Left wall
	      {
	        flip(1);
	        r.remove(s);
	        score++;
	      } else if ( ( (y>r.get(s).y1)&&(y<=r.get(s).y1+bwidth) ) && ( (abs(x-(r.get(s).x1+blength))<2)))   // Right wall
	      {
	        flip(1);
	        r.remove(s);
	        score++;
	      }
	    }


	    frame_count++;

	    if (score==100)             // To decide level
	      level_count=2;
	    else if (score==120)
	      level_count=3;

	    if (dx>3||dy>3)            //Ball speed regulator
	    {
	      dx--;
	      dy--;
	    }

	    if (score==brickcount && level_count==3)           // When all bricks are gone.
	    {
	      textSize(80);
	      fill(255, 0, 0);
	      text("Winner!", 50, 250);            //When all levels complete.
	    }
	  }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main("UsingProcessing");

	}

}
