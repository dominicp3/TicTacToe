import java.awt.*;
import javax.swing.*;

public class DrawLine extends JPanel {
	
	
	private static final long serialVersionUID = 1L;


	public DrawLine() {
		this.setLayout(null);
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		
		int squareSize = 90;
		int leftX = (500 - 3 * squareSize) / 2;
		int rightX = 500 - leftX;
		
		
		for (int i = 1; i < 3; i++) {
//			Horizontal
			g.drawLine(leftX, leftX + (i * squareSize), rightX, leftX + (i * squareSize));
//			Vertical		
			g.drawLine(leftX + (i * squareSize), leftX, leftX + (i * squareSize), rightX);
		}
	
		
			
			
	}

}
