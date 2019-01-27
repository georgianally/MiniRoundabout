import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GUIView extends JPanel implements Observer{
	
	private Car car;
	private BufferedImage bg, carImg, 
							redCar, redCarL, redCarR, 
							yellowCar, yellowCarL, yellowCarR, 
							blueCar, blueCarL, blueCarR;
	private int bgWidth, bgHeight;
	private int carWidth, carHeight;
	Observable observable;
	
	public GUIView() {
		loadImages();
		init();
	}

	  private void init() {
		  bgWidth = bg.getWidth();
		  bgHeight = bg.getHeight();
		  carWidth = redCar.getWidth();
		  carHeight = redCar.getHeight();
	}

	private synchronized void loadImages() {
		try {
		bg = ImageIO.read(getClass().getResource("/roundabout.png"));
			redCar = ImageIO.read(getClass().getResource("/redCar2.png"));
			redCarL = ImageIO.read(getClass().getResource("/redCarL.png"));
			redCarR = ImageIO.read(getClass().getResource("/redCarR.png"));
			blueCar = ImageIO.read(getClass().getResource("/blueCar2.png"));
			blueCarL = ImageIO.read(getClass().getResource("/blueCarL.png"));
			blueCarR = ImageIO.read(getClass().getResource("/blueCarR.png"));
			yellowCar = ImageIO.read(getClass().getResource("/yellowCar2.png"));
			yellowCarL = ImageIO.read(getClass().getResource("/yellowCarL.png"));
			yellowCarR = ImageIO.read(getClass().getResource("/yellowCarR.png"));
		} catch(IOException ioe) {System.out.println("Unable to open file");}
	}
	
	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg, 0, 0, bgWidth, bgHeight, null);
		
		if (car != null) {
			for(Car car : car.getRoadCars()){
				switch(car.getColour()){
				case "Red":
					if(car.getIndicator() == 1) {
						carImg = redCarL;}
					else if(car.getIndicator() == 2) {
						carImg = redCarR;}
					else {
						carImg = redCar;}
					break;
				case "Blue":
					if(car.getIndicator() == 1) {
						carImg = blueCarL;}
					else if(car.getIndicator() == 2) {
						carImg = blueCarR;}
					else {
						carImg = blueCar;}
					break;
				case "Yellow":
					if(car.getIndicator() == 1) {
						carImg = yellowCarL;}
					else if(car.getIndicator() == 2) {
						carImg = yellowCarR;}
					else {
						carImg = yellowCar;}
					break;
				};
				

				setImageRotation(car);
				
			g.drawImage(carImg, car.getCarX(), car.getCarY(), carWidth, carHeight, this);
			}
		}
	}

	@Override
	public synchronized void update(Observable data, Object arg) {
		car = (Car) arg;
		car.getEvent();
		repaint();
	}
	
	private void setImageRotation(Car car) {
		BufferedImage carImgDown = rotate(carImg, 90);
		BufferedImage carImgLeft = rotate(carImgDown, 90);
		BufferedImage carImgUp = rotate(carImgLeft, 90);
		BufferedImage carImgRightDown = rotate(carImg, 45);
		BufferedImage carImgLeftDown = rotate(carImgDown, 45);
		BufferedImage carImgRightUp = rotate(carImgUp, 45);
		BufferedImage carImgLeftUp = rotate(carImgLeft, 45);
		
		
		//Remove comments from below to enable 45 degree rotation
		switch(car.getEvent()){
		case 6: //Moving left
			carImg = carImgLeft;
			break;
		case 7: //Moving down
//			if(car.getSection() == 2) {
//				carImg = carImgRightDown;
//			}
//			else if(car.getSection() == 3) {
//				carImg = carImgLeftDown;
//			}
//			else {
				carImg = carImgDown;
//			}
			break;
		case 8: //Moving up
//			if(car.getSection() == 4) {
//				carImg = carImgLeftUp;
//			}
//			else if(car.getSection() == 1) {
//				carImg = carImgRightUp;
//			}
//			else {
				carImg = carImgUp;
//			}
			break;
		case 9:
			if(car.getStart() == "B"){
				carImg = carImgDown;
			}
			else if(car.getStart() == "C"){
				carImg = carImgLeft;
			}
			else if(car.getStart() == "D"){
				carImg = carImgUp;
			}
		};
	}
	
	public static BufferedImage rotate(BufferedImage src, int rotationAmount) {
	    double rotationRequired = Math.toRadians(rotationAmount);
	    double locationX = src.getWidth() / 2;
	    double locationY = src.getHeight() / 2;
	    AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
	    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	    return op.filter(src, null);
	}
}