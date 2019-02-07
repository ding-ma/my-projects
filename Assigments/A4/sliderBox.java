import java.awt.Color;
import java.awt.event.ItemListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import acm.gui.TableLayout;

public class sliderBox {

	// Parameters for the Sliderbox
	JPanel myPanel;
	JLabel nameLabel;
	JLabel minLabel;
	JLabel maxLabel;
	JSlider mySlider;
	JLabel sReadout;
	Integer imin;
	Integer imax;

	// Constructor for integer values
	public sliderBox(String name, Integer min, Integer dValue, Integer max) { // Integer values
		myPanel = new JPanel();
		nameLabel = new JLabel(name);
		minLabel = new JLabel(min.toString()); // minimum value
		maxLabel = new JLabel(max.toString()); // maximum value
		mySlider = new JSlider(min, max, dValue);
		sReadout = new JLabel(dValue.toString()); // value that gets read out
		sReadout.setForeground(Color.blue);
		myPanel.setLayout(new TableLayout(1, 5));
		// size of the sliderbox
		myPanel.add(nameLabel, "width=100");
		myPanel.add(minLabel, "width=25");
		myPanel.add(mySlider, "width=100");
		myPanel.add(maxLabel, "width=100");
		myPanel.add(sReadout, "width=80");
		imin = min;
		imax = max;
	}

	// constructor for double values
	public sliderBox(String name, double min, double dValue, double max, double type) { // Integer values
		myPanel = new JPanel();
		nameLabel = new JLabel(name);
		mySlider = new JSlider((int) min, (int) max, (int) dValue);
		if (type == 1) { // scale == 1 is used to distinguish eLoss and bColorfrom the rest
			// since all parameters have 1 as their scale value, they will not be affected
			// by the conversions. only eLoss and bColor will be affected in their
			// designated if clause
			sReadout = new JLabel(dValue + "");
			maxLabel = new JLabel(max + "");
			minLabel = new JLabel(min + "");
			sReadout.setForeground(Color.blue);
		} else if (type == 2) { // eLoss requires a scaling of /10
			sReadout = new JLabel(dValue / 10 + "");
			maxLabel = new JLabel(max / 10 + "");
			minLabel = new JLabel(min + "");
			sReadout.setForeground(Color.blue);
		} else if (type == 3) { // bColor should not have any number displayed
			sReadout = new JLabel("Color");

			maxLabel = new JLabel("");
			minLabel = new JLabel("");
			sReadout.setForeground(Color.red);
		}
		myPanel.setLayout(new TableLayout(1, 5));
		// size of sliderbox
		myPanel.add(nameLabel, "width=100");
		myPanel.add(minLabel, "width=25");
		myPanel.add(mySlider, "width=100");
		myPanel.add(mySlider, "height=35");//adjusts the distance between 2 sliders
		myPanel.add(maxLabel, "width=100");
		myPanel.add(sReadout, "width=80");
		// changing min and max to integers in order to be used for the slider
		imin = (int) min;
		imax = (int) max;
	}

	// getter and setter for the slider values
	public Integer getISlider() {
		return mySlider.getValue();
	}

	public void setISlider(double val, double type) {
		mySlider.setValue((int) val); // int cast is used here to convert the double into integers in order to be
		// displayed on the JSlider since it only accepts in
		if (type == 1) { // same here, scale == 1 is used to distinguish eLoss and bColorfrom the rest
			// since all parameters have true as their boolean value, they will not be
			// affected by the scaling
			sReadout.setText(val + "");
		} else if (type == 2) { // eLoss requires a scaling of /10
			sReadout.setText(val / 10 + "");
		} else if (type == 3) {// bColor shouldn't display any number
			sReadout.setText("Color");
			if (val == 0) {
				sReadout.setText("RED");
				sReadout.setForeground(Color.red);
			}
			if (val == 1) {
				sReadout.setText("BLUE");
				sReadout.setForeground(Color.blue);
			}
			if (val == 2) {
				sReadout.setText("YELLOW");
				sReadout.setForeground(Color.yellow);
			}
			if (val == 3) {
				sReadout.setText("PINK");
				sReadout.setForeground(Color.pink);
			}
			if (val == 4) {
				sReadout.setText("BLACK");
				sReadout.setForeground(Color.black);
			}
			if (val == 5) {
				sReadout.setText("GREEN");
				sReadout.setForeground(Color.green);
			}
		}
	}

	// getting the actual selected value for the computations
	public int addItemListener(ItemListener itemListener) {
		return mySlider.getValue();

	}
}