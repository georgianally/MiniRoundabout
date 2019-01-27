import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Model implements Observer {
	
	private ArrayList<Car> carsOnRoad = new ArrayList<Car>();
	private int carX, carY;
	private int roadEndX, roadEndY;
	private int destinationX, destinationY;
	private static Car car;
	public ArrayList<Thread> threads = new ArrayList<Thread>();
	
	public void createCar(int id, int start, int destination, int nextInt, int frequency) {
		car = new Car(id, start, destination, nextInt, carsOnRoad, frequency);
		carsOnRoad.add(car);
		createXY(start, destination);
		car.setCoordinates(carX, carY, roadEndX, roadEndY, destinationX, destinationY);
	}
	
	private void createXY(int start, int destination) {
		switch (start) {
		case 0: //A
			carX = -100;
			carY = 260;
			roadEndX = 320;
			roadEndY = 260;
			break;
		case 1: //B
			carX = 510;
			carY = -50;
			roadEndX = 510;
			roadEndY = 160;
			break;
		case 2: //C
			carX = 1050;
			carY = 340;
			roadEndX = 610;
			roadEndY = 350;
			break;
		case 3: //D
			carX = 450;
			carY = 750;
			roadEndX = 450;
			roadEndY = 440;
			break;
		}
		
		switch (destination) {
		case 4: //Roundabout
			destinationX = 320;
			destinationY = 260;
			break;
		case 0: //A
			destinationX = -200;
			destinationY = 350;
			break;
		case 1: //B
			destinationX = 450;
			destinationY = -50;
			break;
		case 2: //C
			destinationX = 1050;
			destinationY = 260;
			break;
		case 3: //D
			destinationX = 510;
			destinationY = 750;
			break;
		}
	}
	
	public ArrayList<Car> getCarsOnRoad() {
		return carsOnRoad;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		car = (Car) arg1;
		car.setRoadCars(car);
	}
	
	public void clear() {
		for(Car car : carsOnRoad){
			car.stop();
		}
		carsOnRoad.clear();
	}
}