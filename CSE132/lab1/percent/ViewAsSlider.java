package lab1.percent;

import javax.swing.BoundedRangeModel;
import javax.swing.JSlider;

public class ViewAsSlider extends JSlider {


	public ViewAsSlider(BoundedRangeModel model) {
		super(model);
		this.setMajorTickSpacing(20);
		this.setMinorTickSpacing(10);
		this.setPaintTicks(true);
		this.setPaintLabels(true);
		// setSnapToTicks(snap);
	}


}

