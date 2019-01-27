import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ButtonView extends JPanel{
	
	Box carSpeedBox;
	private JLabel carSpeedLabel = new JLabel("Car Speed: ");
	JButton slowButton  = new JButton("Slow");
	JButton mediumButton  = new JButton("Medium");
	JButton fastButton  = new JButton("Fast");
	
	Box testCaseBox;
	JButton testButton1 = new JButton("Test Case 1");
	JButton testButton2 = new JButton("Test Case 2");
	JButton testButton3 = new JButton("Test Case 3");
	JButton testButton4 = new JButton("Test Case 4");
	
	private JLabel customInputsLabel = new JLabel("Custom Inputs:");
	
	Box customStarDestBox;
	private JLabel startLabel = new JLabel("Start Point of Car: ");
	JTextField startData  = new JTextField("A", 10);
	private JLabel destinationLabel = new JLabel("Destination Point of Car: ");
	JTextField destinationData  = new JTextField("B", 10);
	JButton createButton = new JButton("Create Car");
	
	Box randomCarBox;
	private JLabel carAmountLabel = new JLabel("Max Amount of Cars: ");
	JTextField carAmount  = new JTextField(10);
	String[] frequencyChoices = { "Slow", "Medium", "Fast"};
	private JLabel frequencyLabel = new JLabel("Frequency: ");
	JComboBox frequencyList = new JComboBox(frequencyChoices);
	JButton createRandomButton = new JButton("Create Random Car");
	
	Box mainBox;
	
	ButtonView(){
		
		frequencyList.setSelectedItem(frequencyChoices[1]);
		carSpeedBox = Box.createHorizontalBox();
		customStarDestBox = Box.createHorizontalBox();
		randomCarBox = Box.createHorizontalBox();
		testCaseBox = Box.createHorizontalBox();
		mainBox = Box.createVerticalBox();
		
		carSpeedBox.add(carSpeedLabel);
		carSpeedBox.add(slowButton);
		carSpeedBox.add(mediumButton);
		carSpeedBox.add(fastButton);
		
		customStarDestBox.add(startLabel);
		customStarDestBox.add(Box.createRigidArea(new Dimension(5,0)));
		customStarDestBox.add(startData);
		customStarDestBox.add(Box.createRigidArea(new Dimension(10,0)));
		customStarDestBox.add(destinationLabel);
		customStarDestBox.add(Box.createRigidArea(new Dimension(5,0)));
		customStarDestBox.add(destinationData);
		customStarDestBox.add(createButton);
		
		randomCarBox.add(carAmountLabel);
		randomCarBox.add(Box.createRigidArea(new Dimension(5,0)));
		randomCarBox.add(carAmount);
		randomCarBox.add(Box.createRigidArea(new Dimension(5,0)));
		randomCarBox.add(frequencyLabel);
		randomCarBox.add(frequencyList);
		randomCarBox.add(createRandomButton);
		
		testCaseBox.add(testButton1);
		testCaseBox.add(testButton2);
		testCaseBox.add(testButton3);
		testCaseBox.add(testButton4);
		
		mainBox.add(carSpeedBox);
		mainBox.add(Box.createRigidArea(new Dimension(0,10)));
		mainBox.add(testCaseBox);
		mainBox.add(Box.createRigidArea(new Dimension(0,10)));
		mainBox.add(customInputsLabel);
		mainBox.add(Box.createRigidArea(new Dimension(0,10)));
		mainBox.add(customStarDestBox);
		mainBox.add(Box.createRigidArea(new Dimension(0,10)));
		mainBox.add(randomCarBox);
		mainBox.add(Box.createRigidArea(new Dimension(0,20)));
		add(mainBox);
	}
	
	   void addActionListener(ActionListener listenForButton){
		   slowButton.addActionListener(listenForButton);
		   mediumButton.addActionListener(listenForButton);
		   fastButton.addActionListener(listenForButton);
		   testButton1.addActionListener(listenForButton);
		   testButton2.addActionListener(listenForButton);
		   testButton3.addActionListener(listenForButton);
		   testButton4.addActionListener(listenForButton);
		   createButton.addActionListener(listenForButton);
		   createRandomButton.addActionListener(listenForButton);
		   frequencyList.addActionListener(listenForButton);
	   }
	   

	   
		public int getCarAmount(){
			return Integer.parseInt(carAmount.getText());	
		}
	   
		public String getStart(){
			return startData.getText().toUpperCase();
		}
		
		public String getDestination(){
			return destinationData.getText().toUpperCase();
		}
		
		public int getFrequency(){
			int frequency = 1000;
			switch ((String) frequencyList.getSelectedItem()){
			case "Slow": frequency = 2500; break;
			case "Medium": frequency = 1000; break;
			case "Fast": frequency = 500; break;
			}
			return frequency;
			
		}
		
		void displayErrorMessage(String errorMessage){
			JOptionPane.showMessageDialog(this, errorMessage);
		}
}