import java.awt.BorderLayout;
import javax.swing.JFrame;

public class TestDemo {
	public static void main(String[] args){

	Model model = new Model();
	TextView textView = new TextView();
	GUIView guiView = new GUIView();
	ButtonView buttonView = new ButtonView();
	
	JFrame window = new JFrame("Mini Roundabout");
	
	window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    window.add(buttonView, BorderLayout.SOUTH);
    window.add(textView, BorderLayout.EAST);
    window.add(guiView);
    
    window.setSize(1300, 925);
    window.setLocation(50, 50);
	window.setVisible(true);
	
	CarController controller = new CarController(model, textView, buttonView, guiView);
	}
}