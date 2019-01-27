import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextView extends JPanel implements Observer {
	
	Observable observable;
	JTextArea textArea;
	Box box;
	
	public TextView() {
		this.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		textArea = new JTextArea ("Console Log of Car Movements:", 43, 25);
		textArea.setEditable(false);
		box = Box.createVerticalBox();
		box.add(new JScrollPane(textArea));
		add(box);
	}

	@Override
	public void update(Observable data, Object arg) {
			display((Car) arg);
		}
		
		public void display(Car car){
			int event = car.getEvent();
			
			if (event == 1){
				textArea.append(car.getColour() + " Car " + car.getId() + " CREATED on road " + car.getStart() + "\n" );
			}
			if (event == 2 || event == 6 || event == 7 || event == 8){
				if(car.getCarX() % 10 == 0 && car.getCarY() % 10 == 0) { //If divisible by 10, display movement
				textArea.append(car.getColour() + " Car " + car.getId() + " moves to " + car.getCarX() + ", " + car.getCarY() + "\n" );
				}
			}
			if (event == 3){
				textArea.append(car.getColour() + " Car " + car.getId() + " stops at roundabout" + "\n" );
			}
			if (event == 4){
				textArea.append(car.getColour() + " Car " + car.getId() + " reaches  DESTINATION " + car.getDestination() + "\n" );
			}
			if (event == 5){
				textArea.append(car.getColour() + " Car " + car.getId() + " stops for collision" + "\n" );
			}
			if (event == 9){
				textArea.append(car.getColour() + " Car " + car.getId() + " waits until safe" + "\n" );
			}
			if (event == 10){
				textArea.append(car.getColour() + " Car " + car.getId() + " INDICATES left" + "\n" );
			}
			if (event == 11){
				textArea.append( car.getColour() + " Car " + car.getId() + " INDICATES right" + "\n" );
			}
		}
		
		public void clear(){
			textArea.setText(null);
		}

		public void displayAmount(int amount) {
			textArea.append(amount + " Cars Created." + "\n" );
		}
}