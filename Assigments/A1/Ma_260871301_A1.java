import java.awt.Color;

import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;

@SuppressWarnings("serial")
public class Bounce extends GraphicsProgram {
	private static final int WIDTH = 600;

	public void run() {

		this.resize(600, 800); // changing the window size
		GOval ball = new GOval(0, 400, 80, 80); // adding the ball with (X-cord, Y=cord, x-radius, y-radius)
		ball.setFilled(true); // parameter that allows
		ball.setFillColor(Color.BLUE); // color of the ball
		add(ball);
		GLine ground = new GLine(0, 600, 800, 600);// this line is the set the ground
		add(ground);
		/*
		 * The following two variables, "iheight" and "eloss" are entered by the user.
		 * "iheight" is initial height of the ball and "eloss" is the amount of energy
		 * loss by the ball on each bounce
		 */

		double iheight = readDouble("Height of the ball in meters [0,60]:= ");
		double eloss = readDouble("Energy loss [0,1]=");

		double Gravity = 9.8; // physics constant
		double vt = Math.sqrt(2 * Gravity * iheight); // velocity of an object

		/*
		 * Totaltime and TIME_OUT allows the program to keep track of the time. With the
		 * function of Totaltime += Interval_time, we can allow java to add time until
		 * the TIME_OUT is reached.
		 */
		double TotalTime = 0;
		double TIME_OUT = 45;

		// The following variables are needed to simulate the ball bounce
		double INTERVAL_TIME = 0.1;
		double currentHeight;// acts like a free variable, taking whatever value it is assigned to
		boolean Updir = false; // boolean evaluates true/false
		double updirection = 0;
		double Time = 0;
		
		// BONUS: to make to ball move in x direction
		double positionX = 0;
		double velocityX = 8;

		while (TotalTime < TIME_OUT) {
			/*
			 * allows us to stop the simulation after a set amount of time. while (TRUE) the
			 * instructions are preceded while (FALSE) the instructions are stops
			 */
			if (!Updir) {// if direction not up, let the ball drop down
				currentHeight = iheight - 0.5 * Gravity * Time * Time;// Newtonian physics of a falling object
				if (currentHeight <= 0) {
					/*
					 * if the height of the ball is less than 0, ie touching the ground. The
					 * following code will allow it to bounce back up. Setting both variables equal to 0 will prevent
					 * the ball going "into and bellow" the ground when it is rolling.
					 * 
					 * original code:
					 * iheight = currentHeight;
					 * updirection = currentHeight;
					 */
					iheight = 0;
					updirection = 0;
					Updir = true;
					Time = 0;
					vt = Math.sqrt(1 - eloss) * vt; // taking account the energy loss
				}
			} 
			else { // this part allows program to bounce back the ball once it got up

				// application of the physics with the new height
				currentHeight = updirection + vt * Time - 0.5 * Gravity * Time * Time; 

				// reset initial height to the current height in order to loop the calculations again
				if (currentHeight > iheight) { 
					iheight = currentHeight;
				} 
				else {
					Updir = false; // ball going down
					Time = 0; // resetting the time for 0 in order to recalculate the balling ball
				}
			}
			positionX = positionX + velocityX * INTERVAL_TIME; // movement of the ball in the x direction

			// printing in the console the answers of the calculation for the user at every interval of time. 
			println("Time: " + TotalTime + "X:" + positionX + "Y:" + currentHeight); 

			Time += INTERVAL_TIME; // time used for the fall and bounce of the ball
			TotalTime += INTERVAL_TIME; // time used for the x movement of the ball

			/* pausing the calculation for 300ms every time in order to see the ball. the
			 *program would run too fast otherwise displays the new location of the ball
			 */
			pause(INTERVAL_TIME * 300); 
			
			//displays the new location of the ball with all of physics applied
			add(new GOval(positionX + 40, 560 - currentHeight*10, 1, 1));
			ball.setLocation(positionX, 520 - currentHeight*10);
		}
	}
}
