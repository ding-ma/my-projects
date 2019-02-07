import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.gui.TableLayout;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class bSim extends GraphicsProgram implements ChangeListener, ItemListener {

	// Parameters used in this program
	private static final int WIDTH = 1200; // n.b. screen coordinates
	public static final int HEIGHT = 600;
	public static final int OFFSET = 200;

//initial values for the program if the user does not input any value
	//loss coefficient is from 1 to 10 because of how the slider works in java
	private static final int NUMBALLS = 15; // # balls to simulate
	private static final double MINSIZE = 1; // Minumum ball size
	private static final double MAXSIZE = 25; // Maximum ball size
	private static final double XMIN = 10; // Min X starting location
	private static final double XMAX = 50; // Max X starting location
	private static final double YMIN = 50; // Min Y starting location
	private static final double YMAX = 100; // Max Y starting location
	private static final double EMIN = 7; // Minimum loss coefficient
	private static final double EMAX = 9; // Maximum loss coefficient
	private static final double VMIN = 0.5; // Minimum X velocity
	private static final double VMAX = 3.0; // Maximum Y velocity

	// Parameters used to the sliders
	RandomGenerator rgen = new RandomGenerator();// creating the random generator
	private bTree myTree;
	double PS_NumBalls = NUMBALLS;
	double PS_MinSize = MINSIZE*2;
	double PS_MaxSize = MAXSIZE*2;
	double PS_XMin= XMIN;
	double PS_Xmax=XMAX;
	double PS_Ymin=YMIN;
	double PS_Ymax=YMAX;
	double PS_LossMin=EMIN;
	double PS_LossMax=EMAX;
	double PS_XVelMin=VMIN;
	double PS_XVelMax=VMAX;

	// parameters for single ball sliders
	double bColor;
	double PS_BallSize;
	double PS_Loss;
	double PS_Xvel;

	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		/*
		 * slider that controls various parameters all the values have been casted into
		 * a double in order to facilate the calculations the last value is the
		 * conversion value. if the object has a 1, it is not affected by the scaling if
		 * the object has a 2, its number will be divided by 10 if the object has a 3
		 * (only bColor), it will not display any number eLoss sliders are have 2 as
		 * their conversion since they need to be divided by 10. the rest of the
		 * parameters have true because they don't need to be divided by 10 except
		 * bColor
		 */
		if (source == numballsSlider.mySlider) {
			PS_NumBalls = numballsSlider.getISlider();
			numballsSlider.setISlider(PS_NumBalls, 1);
		}

		else if (source == minSizeSlider.mySlider) {
			PS_MinSize = (double) minSizeSlider.getISlider();
			minSizeSlider.setISlider(PS_MinSize, 1);
		} else if (source == maxSizeSlider.mySlider) {
			PS_MaxSize = (double) maxSizeSlider.getISlider();
			maxSizeSlider.setISlider(PS_MaxSize, 1);
		} else if (source == xminSlider.mySlider) {
			PS_XMin = (double) xminSlider.getISlider();
			xminSlider.setISlider(PS_XMin, 1);
		} else if (source == xmaxSlider.mySlider) {
			PS_Xmax = (double) xmaxSlider.getISlider();
			xmaxSlider.setISlider(PS_Xmax, 1);
		} else if (source == yminSlider.mySlider) {
			PS_Ymin = (double) yminSlider.getISlider();
			yminSlider.setISlider(PS_Ymin, 1);
		} else if (source == ymaxSlider.mySlider) {
			PS_Ymax = (double) ymaxSlider.getISlider();
			ymaxSlider.setISlider(PS_Ymax, 1);
		}

		else if (source == lossminSlider.mySlider) {
			PS_LossMin = (double) lossminSlider.getISlider();
			lossminSlider.setISlider(PS_LossMin, 2);
		} else if (source == lossmaxSlider.mySlider) {
			PS_LossMax = (double) lossmaxSlider.getISlider();
			lossmaxSlider.setISlider(PS_LossMax, 2);
		} else if (source == xvelminSlider.mySlider) {
			PS_XVelMin = (double) xvelminSlider.getISlider();
			xvelminSlider.setISlider(PS_XVelMin, 1);
		} else if (source == xvelmaxSlider.mySlider) {
			PS_XVelMax = (double) xvelmaxSlider.getISlider();
			xvelmaxSlider.setISlider(PS_XVelMax, 1);
		}

		// for single ball
		// fix color
		else if (source == colorSlider.mySlider) {
			bColor = (double) colorSlider.getISlider();
			colorSlider.setISlider(bColor, 3);
		} else if (source == ballSizeSlider.mySlider) {
			PS_BallSize = (double) ballSizeSlider.getISlider();
			ballSizeSlider.setISlider(PS_BallSize, 1);
		} else if (source == ballLossSlider.mySlider) {
			PS_Loss = (double) ballLossSlider.getISlider();
			ballLossSlider.setISlider(PS_Loss, 2);
		} else if (source == ballxVelSlider.mySlider) {
			PS_Xvel = (double) ballxVelSlider.getISlider();
			ballxVelSlider.setISlider(PS_Xvel, 1);
		}

	}

	// creating sliderbox, one for each paramter
	sliderBox numballsSlider;
	sliderBox minSizeSlider;
	sliderBox maxSizeSlider;
	sliderBox xminSlider;
	sliderBox xmaxSlider;
	sliderBox yminSlider;
	sliderBox ymaxSlider;
	sliderBox lossminSlider;
	sliderBox lossmaxSlider;
	sliderBox xvelminSlider;
	sliderBox xvelmaxSlider;
	JComboBox<String> bSimC;
	sliderBox colorSlider;
	sliderBox ballSizeSlider;
	sliderBox ballLossSlider;
	sliderBox ballxVelSlider;

	// this is used for the drop down menu in order to run/clear/exit the program
	void setChoosers() {
		bSimC = new JComboBox<String>();
		bSimC.addItem("bSim");
		bSimC.addItem("Run");
		// bSimC.addItem("Clear and Restart");
		bSimC.addItem("Quit");
		add(bSimC, NORTH);
		addJComboListeners();

	}

	// this is the listener for the drop down menu
	void addJComboListeners() {
		bSimC.addItemListener((ItemListener) this);
	}

	// these are the listeners for the various sliderbox
	void addssliderBoxListeners() {
		numballsSlider.addItemListener((ItemListener) this);
		minSizeSlider.addItemListener((ItemListener) this);
		maxSizeSlider.addItemListener((ItemListener) this);
		xminSlider.addItemListener((ItemListener) this);
		xmaxSlider.addItemListener((ItemListener) this);
		yminSlider.addItemListener((ItemListener) this);
		ymaxSlider.addItemListener((ItemListener) this);
		lossminSlider.addItemListener((ItemListener) this);
		lossmaxSlider.addItemListener((ItemListener) this);
		xvelminSlider.addItemListener((ItemListener) this);
		xvelmaxSlider.addItemListener((ItemListener) this);
	}

	public static void main(String[] args) { // Standalone Applet
		new bSim().start(args);
	}

	boolean start = false; // false at the begining because we want to start the program only when "Run" is
	// selected
	boolean reverse; // reverses each time, clears the screen when "run" is pressed so a new
	// simulation can start

	public void itemStateChanged(ItemEvent e) {
		JComboBox source = (JComboBox) e.getSource();
		if (source == bSimC) {
			if (bSimC.getSelectedIndex() == 1) { // run button
				// System.out.println("Starting Simulation");
				start = true; // once the button is pressed, it is set to true, it will start the program
				drawground(); // calls the method to draw the line for the ball to bounce on
				reverse = true; // sets to true in order to be able to erase everything on the second trial
				source.setSelectedIndex(0); // resets the JComboBox counter back to 0 in order to be pressed again

				if (reverse) {
					removeAll(); // clears the screen from the old balls
					resetBallPos(); // method to reset the ball position
					drawground(); // method to draw the ground

				}
				

			}

			//			if (bSimC.getSelectedIndex()==2) { //clear button
			//				//System.out.println("Screen Cleared");
			//				removeAll(); //clears the screen from the old balls
			//				resetBallPos(); //method to reset the ball position	
			//				drawground();
			//			}

			if (bSimC.getSelectedIndex() == 2) { // exit button
				// System.out.println("Program Exited");
				System.exit(0);// quit the program

			}
		}

	}

	private void resetBallPos() {
		bTree.xpos = 0; // resets position to 0
		bTree.clearTree(); // method called to clear the binary tree in bTree
	}

	public void run() {
		JPanel eastPannel = new JPanel();
		JLabel gen_sim = new JLabel("General Simulation Paramters");
		eastPannel.setLayout(new GridLayout(17, 1));
		// eastPannel.setPreferredSize(new Dimension(320, 240) );
		eastPannel.add(gen_sim);
		/*
		 * the followeing are all the listeners for various parameters numbers in the
		 * first line of each listeners goes as follows (minimum, default, maximum) Same
		 * logic as before, the last value is the conversion value. if the object has a
		 * 1, it is not affected by the scaling if the object has a 2, its number will
		 * be divided by 10 if the object has a 3 (only bColor), it will not display any
		 * number eLoss sliders are have 2 as their conversion since they need to be
		 * divided by 10. the rest of the parameters have true because they don't need
		 * to be divided by 10 except bColor
		 */

		numballsSlider = new sliderBox("NUMBALLS:", 1, 15, 25);// for number of balls
		eastPannel.add(numballsSlider.myPanel, "EAST");
		numballsSlider.mySlider.addChangeListener((ChangeListener) this);
	
		minSizeSlider = new sliderBox("MIN SIZE:", 1.0, 1.0, 25.0, 1); // for minimum ball size
		eastPannel.add(minSizeSlider.myPanel, "EAST");
		minSizeSlider.mySlider.addChangeListener((ChangeListener) this);

		maxSizeSlider = new sliderBox("MAX SIZE:", 1.0, 25.0, 25.0, 1); // maximum ball size
		eastPannel.add(maxSizeSlider.myPanel, "EAST");
		maxSizeSlider.mySlider.addChangeListener((ChangeListener) this);

		xminSlider = new sliderBox("X MIN:", 1.0, 10.0, 200.0, 1); // minimum x position
		eastPannel.add(xminSlider.myPanel, "EAST");
		xminSlider.mySlider.addChangeListener((ChangeListener) this);

		xmaxSlider = new sliderBox("X MAX:", 1.0, 75.0, 200.0, 1); // maximum x position
		eastPannel.add(xmaxSlider.myPanel, "EAST");
		xmaxSlider.mySlider.addChangeListener((ChangeListener) this);

		yminSlider = new sliderBox("Y MIN:", 1.0, 10.0, 100.0, 1);// minimum y position
		eastPannel.add(yminSlider.myPanel, "EAST");
		yminSlider.mySlider.addChangeListener((ChangeListener) this);

		ymaxSlider = new sliderBox("Y MAX:", 1.0, 50.0, 100.0, 1); // maximum y position
		eastPannel.add(ymaxSlider.myPanel, "EAST");
		ymaxSlider.mySlider.addChangeListener((ChangeListener) this);

		// note that the eLoss slider are set to false in order to be divided by 10
		lossminSlider = new sliderBox("LOSS MIN:", 0.0, 6.0, 10, 2); // minimum loss coefficient
		eastPannel.add(lossminSlider.myPanel, "EAST");
		lossminSlider.mySlider.addChangeListener((ChangeListener) this);

		lossmaxSlider = new sliderBox("LOSS MAX:", 0.0, 8.0, 10.0, 2); // maximum loss coefficient
		eastPannel.add(lossmaxSlider.myPanel, "EAST");
		lossmaxSlider.mySlider.addChangeListener((ChangeListener) this);

		xvelminSlider = new sliderBox("X VEL MIN:", 0.0, 1.0, 10.0, 1); // minimum x velocity
		eastPannel.add(xvelminSlider.myPanel, "EAST");
		xvelminSlider.mySlider.addChangeListener((ChangeListener) this);

		xvelmaxSlider = new sliderBox("X VEL MAX:", 0.0, 3.0, 10.0, 1); // maximum x velocity
		eastPannel.add(xvelmaxSlider.myPanel, "EAST");
		xvelmaxSlider.mySlider.addChangeListener((ChangeListener) this);

		// for single ball

		JLabel SingleBall = new JLabel("Single Ball Instance Parameter");
		eastPannel.add(SingleBall);
		eastPannel.setLayout(new GridLayout(17, 1));
		eastPannel.setSize(new Dimension(100, 100));

		colorSlider = new sliderBox("Color:", 0.0, 2.0, 5.0, 3); // maximum x velocity
		eastPannel.add(colorSlider.myPanel, "EAST");
		colorSlider.mySlider.addChangeListener((ChangeListener) this);
		
		ballSizeSlider = new sliderBox("Ball Size:", 0.0, 5.0, 8.0, 1); // maximum x velocity
		eastPannel.add(ballSizeSlider.myPanel, "EAST");
		ballSizeSlider.mySlider.addChangeListener((ChangeListener) this);
		
		ballLossSlider = new sliderBox("E Loss:", 0.0, 5.0, 10.0, 2); // maximum x velocity
		eastPannel.add(ballLossSlider.myPanel, "EAST");
		ballLossSlider.mySlider.addChangeListener((ChangeListener) this);

		ballxVelSlider = new sliderBox("X Vel:", 0.0, 3.0, 5.0, 1); // maximum x velocity
		eastPannel.add(ballxVelSlider.myPanel, "EAST");
		ballxVelSlider.mySlider.addChangeListener((ChangeListener) this);
		
		

		add(eastPannel, EAST);
		setChoosers();

		this.resize(WIDTH, HEIGHT + OFFSET); // resizes the window size
		myTree = new bTree(); // creation of an instance of the bTree
		drawground(); // calls the method to draw the line for the ball to bounce on

		
		while (true) {
			pause(200);// 200ms of pause between press of run and start of simulation
			if (start) {
				startmethod(); // since bSimC.getSelectedIndex()==1 is selected, it sets start to true, hence
				// allowing the program to run

			}
			start = false; // the simulation, returns to false in order to stop the program

		}

	}

	public void startmethod() {
		int numballsSlider = (int) PS_NumBalls; // casted int for PS_NumBalls in order to make it work in the for loop.
		// Then, the value is passed to numballsSlider in order to make the
		// forloop work
		for (int i = 0; i < numballsSlider; i++) { // repeats as need for the number of balls it is selected
			// System.out.println("123");
			// Generate a series of random gballs and let the simulation run till completion
			RandomGenerator rgen = RandomGenerator.getInstance(); // creating instance of random generator
			double Xi = rgen.nextDouble(PS_XMin, PS_Xmax); // generating value for current Xi
			double Yi = rgen.nextDouble(PS_Ymin, PS_Ymax); // generating value for current Yi
			double iSize = rgen.nextDouble(2 * PS_MinSize, 2 * PS_MaxSize); // generating value for current size
			Color iColor = rgen.nextColor(); // generating color for current color
			double iLoss = rgen.nextDouble(PS_LossMin / 10, PS_LossMax / 10); // generating value for current loss, divided by 10 because the slider is from 0 to 10, eloss has to be between [0,1]
			// coefficient
			double iVel = rgen.nextDouble(PS_XVelMin, PS_XVelMax); // generating value for current X velocity
			gBall iBall = new gBall(Xi, Yi, iSize, iColor, iLoss, iVel); // generate instance of all the values for the
			// ball
			add(iBall.myball); // Add to display list
			myTree.addNode(iBall); // Save instance
			iBall.start(); // Start this instance, displaying on the screen
			// System.out.println(iLoss);
			// System.out.println(iSize);

		}
		while (myTree.isRunning());
		GLabel myLabel = new GLabel("Click mouse to continue"); // display message when all the ball have stoped
		// bouncing
		myLabel.setLocation(WIDTH - myLabel.getWidth() - 900, HEIGHT - myLabel.getHeight()); // location of that label
		myLabel.setColor(Color.RED); // color of that label
		add(myLabel);
		waitForClick(); // wait for user click
		myLabel.setLabel("All Sorted!"); // message displayed for sorted balls
		// myTree.inorder();
		myTree.moveSort(); // Lay out balls from left to right in size order

	}

	private void drawground() { // method created to draw the line
		// Create the ground plane
		GRect gPlane = new GRect(0, HEIGHT-100, 5000, 2);// creating the ground, put 3840 as the width for people with 4k display playing on full screen
		gPlane.setColor(Color.BLACK); // setting color to black
		gPlane.setFilled(true);
		add(gPlane);
	}

	int x, y;

	{
		addMouseListener(this);
		addMouseMotionListener(this);

		setSize(5000, 5000);
		setVisible(true);
	}

	public void mousePressed(MouseEvent e) {
		x = e.getX();
		y = e.getY();

		System.out.println("Xpos: " + x);
		System.out.println("Ypos: " + y);
	}
}
