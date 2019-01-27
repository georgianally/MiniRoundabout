import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Car extends Observable implements Runnable{
	
	private int id;
	private int start;
	private int destination;
	private String[] roadNames = {"A", "B", "C", "D", "Roundabout"};
	private String[] colours = {"Red", "Blue", "Yellow"}; //Select random colour
	private String colour; //which colour image used
	private int indicator = 0; //0 = off, 1 = L, 2 = R
	private int speed = 15; //sleep time between movement increments
	private int carX, carY;
	private int destinationX, destinationY;
	private int roadEndX, roadEndY;
	private int width = 65;
	private int event; //1 = car created, 2 = move right, 3 = stop at roundabout, 4 = reaches destination, 5 = stops for collision, 6 = move left, 7 = move down, 8 = move up, 9 = wait at roundabout until safe
	private int section = -1;
	private Boolean onRoundabout = false;
	private ArrayList<Car> roadCars = new ArrayList<Car>();
	private final AtomicBoolean go = new AtomicBoolean(true);
	int startCoordinate;
	int frequency;
	Random r = new Random();
	
	private int[][] controlPoints = {
			{5, 410, 260}, //1 controlPoints[0][1] && controlPoints[0][2]
			{1, 450, 230}, //2 exit B(1)
			{5, 510, 230}, //3
			{2, 540, 260}, //4 exit C(2)
			{5, 540, 350}, //5
			{3, 510, 380}, //6 exit D(3)
			{5, 450, 380}, //7
			{0, 410, 350} 	//8 exit A(0)
	};

	public Car (int num, int s, int d, int c, ArrayList<Car> cars, int f){
		id = num;
		start = s;
		destination = d;
		colour = colours[c];
		roadCars = cars;
		frequency = f;
		speed = r.nextInt(20 + 1 - 15) + 15; //random speed between 15 and 20
	}
	
	public void setCoordinates(int carX2, int carY2, int roadEndX2, int roadEndY2, int destinationX2, int destinationY2) {
		carX = carX2;
		carY = carY2;
		roadEndX = roadEndX2;
		roadEndY = roadEndY2;
		destinationX = destinationX2;
		destinationY = destinationY2;
		switch (start) { //starting coordinate points based on start position
		case 0:
			startCoordinate = 0;
			break;
		case 1:
			 startCoordinate = 2;
			 break;
		case 2:
			 startCoordinate = 4;
			break;
		case 3:
			 startCoordinate = 6;
			break;
		}	
	}

	public void run(){
		try
	      {
			//Car Created
			Thread.sleep(r.nextInt( 200 ));
			event = 1;
			notifyObserver();
				if(id != 0) { //if not the first car, wait (gives cars time to not spawn on top of each other)
					Thread.sleep(frequency * id);
				}
	
			// move down start road to roundabout
			while (carX != roadEndX || carY != roadEndY) {
				move(roadEndX, roadEndY);
				Thread.sleep(r.nextInt( speed ));
			}
	    }
		 catch ( InterruptedException exception )
			{System.out.printf( "%s \n", "terminated prematurely due to interruption" );} 
		
		// stop at roundabout, check if at destination
		if (carX == destinationX && carY == destinationY)
		{
			try
		      {
				Thread.sleep(r.nextInt( 10000 ));
				event = 3;
				notifyObserver();
				} catch ( InterruptedException exception )
				{System.out.printf( "%s \n", "terminated prematurely due to interruption" );} 
		}
		else {// stop and check if roundabout is safe
			try
		      {
				Thread.sleep(r.nextInt( 500 ));
				checkSection();
				//when safe - move round roundabout until at destination road - then move to destination
					indicate();
					int q = 0;
				for(int i = startCoordinate; i < 8; i++) {
					if(controlPoints[i][0] == destination) { //If controlPoint == destination road, move to destination
						//indicator = 1;
						section = -1;
						event = 10;
						while (carX != destinationX || carY != destinationY) {
							q++;
							onRoundabout = false;
							move(destinationX, destinationY);
							Thread.sleep(r.nextInt( speed ));
							if(q > 100) {
								indicator = 0;
							}
						}
						break;
					}
					else { //If not destination, move to next control point
								while (carX != controlPoints[i][1] || carY != controlPoints[i][2]) {
									setSection(i);
									onRoundabout = true;
									move(controlPoints[i][1], controlPoints[i][2]);
									Thread.sleep(r.nextInt( speed ));
									}
						} 
						if(i == 7){ //reset control points
							i = -1;
						}
					}
				// Destination reached
				carX = -100;
				carY = -100;
				event = 4;
				notifyObserver();
				}
		 catch ( InterruptedException exception )
			{System.out.printf( "%s \n", "terminated prematurely due to interruption" );} ;
		}
	}

	public void move(int X, int Y){
		if(go.get()){
			if(carX < X){ 
				checkCollision("moveRight", X);
				carX = carX + 1;
				event = 2;
			}
			if (carX > X) {
				checkCollision("moveLeft", X);
				carX = carX - 1;
				event = 6;
			}
			if(carY < Y){ 
				checkCollision("moveDown", Y);
				carY = carY + 1;
				event = 7;
			}
			if (carY > Y) {
				checkCollision("moveUp", Y);
				carY = carY - 1;
				event = 8;
			}
			notifyObserver();
		}
	}

	public void checkCollision(String movement, int movePos){
		for(Car car : roadCars) {
			if(movement == "moveRight"){
				while(this.getCarX() > (car.getCarX() - width) && 
						id != car.getId() && 
						car.getCarX() > this.getCarX() && 
						car.getCarY() == this.getCarY()){
						if(go.get()){
							collision(5);
						}	 
				}
			}
				if(movement == "moveLeft"){
					while(this.getCarX() < (car.getCarX() + width) && 
							id != car.getId() && 
							car.getCarX() < this.getCarX() && 
							car.getCarY() == this.getCarY()) {
					if(go.get()){
						collision(6);
					}
				}
			}
				if(movement == "moveUp"){
					while(this.getCarY() < (car.getCarY() + width) && 
							id != car.getId() && 
							car.getCarY() < this.getCarY() && 
							car.getCarX() == this.getCarX()) {
					if(go.get()){
						collision(8);
					}
				}
			}
			if(movement == "moveDown"){
				while(this.getCarY() > (car.getCarY() - width) && 
						id != car.getId() && 
						car.getCarY() > this.getCarY() && 
						car.getCarX() == this.getCarX()) {
					if(go.get()){
						collision(7);
					}
				}
			}
		}
	}
	
	//Checks if safe to move onto roundabout
	private void checkSection() {
		for(Car car : roadCars) {
			if(startCoordinate == 0) {
				section = 1;
				while((car.getSection() == section && car.getId() != id && car.isOnRoundabout()) || 
						(car.getSection() == 4 && car.getId() != id && car.isOnRoundabout() && car.getDestination() != "A")) {
					if(go.get()){
					waitRound();
					}
				}
			}
			if(startCoordinate == 2) {
				section = 2;
				while((car.getSection() == section && car.getId() != id && car.isOnRoundabout()) || 
				(car.getSection() == 1 && car.getId() != id && car.isOnRoundabout() && car.getDestination() != "B")) {
					if(go.get()){
					waitRound();
					}
				}
			}
			if(startCoordinate == 4) {
				section = 3;
				while((car.getSection() == section && car.getId() != id && car.isOnRoundabout()) || 
				(car.getSection() == 2 && car.getId() != id && car.isOnRoundabout() && car.getDestination() != "C")){
					if(go.get()){
					waitRound();
					}
				}
			}
			if(startCoordinate == 6) {
				section = 4;
				while((car.getSection() == section && car.getId() != id && car.isOnRoundabout()) || 
				(car.getSection() == 3 && car.getId() != id && car.isOnRoundabout() && car.getDestination() != "C")){
					if(go.get()){
					waitRound();
					}
				}
			}
		}
	}

	private void setSection(int i) {
		onRoundabout = true;
		switch(i) {
		case 0:
		case 1:
			section = 1;
			break;
		case 2:
		case 3:
			section = 2;
			break;
		case 4:
		case 5:
			section = 3;
			break;
		case 6:
		case 7:
			section = 4;
			break;
			
		}
	}
	
	private void indicate() {
		switch (this.getStart()) {
		case "A":
			if(this.getDestination() == "B") {
				indicateLeft();
			}
			else if(this.getDestination() == "D" || this.getDestination() == "A") {
				indicateRight();
			}
			break;
		case "B":
			if(this.getDestination() == "C") {
				indicateLeft();
			}
			else if(this.getDestination() == "A" || this.getDestination() == "B") {
				indicateRight();
			}
			break;
		case "C":
			if(this.getDestination() == "D") {
				indicateLeft();
			}
			else if(this.getDestination() == "B" || this.getDestination() == "C") {
				indicateRight();
			}
			break;
		case "D":
			if(this.getDestination() == "A") {
				indicateLeft();
			}
			else if(this.getDestination() == "C" || this.getDestination() == "D") {
				indicateRight();
			}
			break;
		}
	}


	private void collision(int eventNumber){
		event = eventNumber;
		notifyObserver();
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void waitRound(){ //waiting at roundabout
		event = 9; 
		notifyObserver();
		try {
			Thread.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void notifyObserver() {
		setChanged();
		notifyObservers(this);
	}

	private void indicateLeft() {
		indicator = 1;
		event = 10;
		notifyObserver();
	}
	
	private void indicateRight() {
		indicator = 2;
		event = 11;
		notifyObserver();
	}
	
	public int getSection() {
		return section;
	}
	
	public int getId() {
		return id;
	}
	
	public String getStart() {
		return roadNames[start];
	}
	
	public String getDestination() {
		return roadNames[destination];
	}

	public String getColour() {
		return colour;
	}


	public int getCarX() {
		return carX;
	}
	
	public int getCarY() {
		return carY;
	}
	
	public void setRoadCars(Car car) {
		if(!roadCars.contains(car)){
			roadCars.add(car);
		}
	}
	
	public ArrayList<Car> getRoadCars() {
		return roadCars;
	}
	
	public void stop(){
		go.set(false);
	}

	public int getEvent() {
		return event;
	}
	
	public Boolean isOnRoundabout() {
		return onRoundabout;
	}
	
	public int getIndicator() {
		return indicator;
	}
	
	public void setSpeed(int newSpeed) {
		speed = newSpeed;
	}
}