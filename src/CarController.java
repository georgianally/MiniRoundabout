import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class CarController {
	private Model model;
	private TextView TextView;
	private ButtonView ButtonView;
	private GUIView GUIView;	
	int frequency = 1000;
	Random r = new Random();
	
	public CarController(Model model, TextView view, ButtonView view2, GUIView view3){
		this.model = model;
		this.TextView = view;
		this.ButtonView = view2;
		this.GUIView = view3;
		this.ButtonView.addActionListener(new Listener());
	}
	
	public void testCase1() {
		clearSystem();
		model.createCar(0, 0, 4, r.nextInt(3), frequency);
		model.createCar(1, 0, 4, r.nextInt(3), frequency);
		carSetup();
	}

	public void testCase2() {
		clearSystem();
		model.createCar(0, 0, 3, r.nextInt(3), frequency);
		model.createCar(1, 0, 2, r.nextInt(3), frequency);
		carSetup();
	}
	
	public void testCase3() {
		clearSystem();
		randomCarCreation(30, 1500);
	}
	
	public void testCase4() {
		clearSystem();
		randomCarCreation(30, 500);
	}
	
	private void inputTestCase() {
		clearSystem();
		int start = 0;
		int dest = 0;
		switch(ButtonView.getStart()) {
		case "A": start = 0; break;
		case "B": start = 1; break;
		case "C": start = 2; break;
		case "D": start = 3; break;
		}
		switch(ButtonView.getDestination()) {
		case "A": dest = 0; break;
		case "B": dest = 1; break;
		case "C": dest = 2; break;
		case "D": dest = 3; break;
		}
		model.createCar(1, start, dest, r.nextInt(3), 1000);
		carSetup();
	}
	
	private void randomInputTestCase() {
		clearSystem();
		int amount = ButtonView.getCarAmount();
		frequency = ButtonView.getFrequency();
		randomCarCreation(amount, frequency);
	}
	
	private void randomCarCreation(int a, int f) {
		int amount = r.nextInt(a + 1 - 3) + 3;
		for(int i = 0; i < amount; i++) {
		model.createCar(i, r.nextInt(4), r.nextInt(4), r.nextInt(3), f);
		}
		TextView.displayAmount(amount);
		carSetup();
	}
	
	public void carSetup() {
		for (Car car: model.getCarsOnRoad()) {
		car.addObserver(TextView);	
		car.addObserver(GUIView);
		car.addObserver(model);
		
		Thread carThread = new Thread(car);
		carThread.start();
		}
	}
	
	private void clearSystem() {
		model.clear();
		TextView.clear();
	}
	
	class Listener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(ButtonView.testButton1)) {
				testCase1();
			}
			
			else if(e.getSource().equals(ButtonView.testButton2)) {
				testCase2();
			}

			else if(e.getSource().equals(ButtonView.testButton3)) {
				testCase3();
			}
			else if(e.getSource().equals(ButtonView.testButton4)) {
				testCase4();
			}
			else if(e.getSource().equals(ButtonView.createButton)) {
				try{
					
					inputTestCase();
				}

				catch(NumberFormatException ex){
					ButtonView.displayErrorMessage("You need to enter 3 integers");
				}
			}
			else if(e.getSource().equals(ButtonView.createRandomButton)) {
				try{
					
					randomInputTestCase();
				}

				catch(NumberFormatException ex){
					ButtonView.displayErrorMessage("You need to enter an integer");
				}
			}
			else if(e.getSource().equals(ButtonView.slowButton)) {
				for(Car car : model.getCarsOnRoad()) {
					car.setSpeed(30);
				}
			}
			else if(e.getSource().equals(ButtonView.mediumButton)) {
				for(Car car : model.getCarsOnRoad()) {
					car.setSpeed(15);
				}
			}
			else if(e.getSource().equals(ButtonView.fastButton)) {
				for(Car car : model.getCarsOnRoad()) {
					car.setSpeed(5);
				}
				System.out.println(e.getSource().toString());
			}
		}
	}
}