import java.awt.Color;
import java.util.Scanner;

import acm.graphics.GLine;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.util.RandomGenerator;

public class gBall extends Thread {
	// Variables used to take the values of the constructor
	double Xi;
	double Yi;
	public double bSize;
	public Color bColor;
	public double bLoss;
	public double bVel;
	public GOval myball;
	public boolean isMoving = true;
	/**
	 * The constructor specifies the parameters for simulation:
	 *
	 * @param Xi     double The initial X position of the center of the ball
	 * @param Yi     double The initial Y position of the center of the ball
	 * @param bSize  double The radius of the ball in simulation units
	 * @param bVel   double X velocity of ball
	 * @param bColor
	 * @param bLoss
	 */

	public gBall(double Xi, double Yi, double bSize, Color bColor, double bloss, double bVel) {

		// Get simulation parameters from bSim
		this.Xi = Xi;
		this.Yi = Yi;
		this.bSize = bSize;
		this.bColor = bColor;
		this.bLoss = bloss;
		this.bVel = bVel;
		myball = new GOval(bSize, bSize);
		myball.setFilled(true);
		myball.setFillColor(bColor);
		myball.setLocation(Xi * 6, Yi * 6);

	}

	Scanner sc = new Scanner(System.in);


	public void run() {

		double Gravity = 9.8; // physics constant
		double vt = Math.sqrt(2 * Gravity * Yi); // velocity of an object
		double TotalTime = 0;

		// The following variables are needed to simulate the ball bounce
		double INTERVAL_TIME = 0.1;
		double currentHeight;// acts like a free variable, taking whatever value it is assigned to
		boolean Updir = false; // boolean evaluates true/false
		double updirection = 0;
		double Time = 0;
		double xi = 0;
		// adding the ball with (X-cord, Y=cord, x-radius, y-radius)
		/*
		 * The following two variables, "iheight" and "eloss" are entered by the user.
		 * "iheight" is initial height of the ball and "eloss" is the amount of energy
		 * loss by the ball on each bounce
		 */
		while (vt > 2) {
			/*
			 * allows us to stop the simulation after a set amount of time. while (TRUE) the
			 * instructions are preceded while (FALSE) the instructions are stops
			 */

			if (!Updir) {// if direction not up, let the ball drop down
				// Newtonian physics of a falling object
				currentHeight = Yi - 0.5 * Gravity * Time * Time;
				if (currentHeight <= 0) {
					/*
					 * if the height of the ball is less than 0, ie touching the ground. The
					 * following code will allow it to bounce back up. Setting both variables equal
					 * to 0 will prevent the ball going "into and bellow" the ground when it is
					 * rolling.
					 * 
					 * original code: iheight = currentHeight; updirection = currentHeight;
					 */
					Yi = 0;
					updirection = 0;
					Updir = true;
					Time = 0;
					vt = Math.sqrt(1 - bLoss) * vt; // taking account the energy loss
				}
			} else { // this part allows program to bounce back the ball once it got up

				// application of the physics with the new height
				currentHeight = updirection + vt * Time - 0.5 * Gravity * Time * Time;

				// reset initial height to the current height in order to loop the calculations
				// again
				if (currentHeight > Yi) {
					Yi = currentHeight;
				} else {
					Updir = false; // ball going down
					Time = 0; // resetting the time for 0 in order to recalculate the balling ball
				}
			}
			xi = Xi + bVel * TotalTime * 10; // movement of the ball in the x direction
			Time += INTERVAL_TIME; // time used for the fall and bounce of the ball
			TotalTime += INTERVAL_TIME; // time used for the x movement of the ball

			// printing in the console the answers of the calculation for the user at every
			// interval of time.

			/*
			 * pausing the calculation for 300ms every time in order to see the ball. the
			 * program would run too fast otherwise displays the new location of the ball
			 */
			try { // pause for 15 milliseconds
				Thread.sleep(15);
			} catch (InterruptedException e) { // will display error if there is any
				e.printStackTrace();
			}

			// displays the new location of the ball with all of physics applied

			myball.setLocation(xi, 500 - bSize - currentHeight); // setting new locations for the ball after calculating

		}
		this.isMoving = false; //after the whileloop, it returns false, the ball stops bouncing
	}
	
	}


