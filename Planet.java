import java.util.*;

public class Planet {

	private String name;
	private double mass;
	private double distance;
	private Planet sun;
	private double orbitalSpeed;
	private double orbitalPeriod;
	private double sunGravForce;
	private int type; //0 for sun, 1 for planet, 2 for moon
	
	private static ArrayList<Planet> planets = new ArrayList<Planet>();
	
	public static final double G = 6.67 * Math.pow(10, -11);
	
	//Regular planets
	
	//Default planet is earth
	public Planet() {
		this("Earth",e(5.98, 24), e(1.5, 11));
	}
	
	public Planet(double m, double d) {
		setMass(m);
		setDistance(d);
		setSun(new Planet(e(1.99, 30)));
		orbitalSpeed = calculateOrbitalSpeed();
		orbitalPeriod = calculateOrbitalPeriod();
		setType(1);
	}
	
	public Planet(String n, double m, double d) {
		this(m, d);
		name = n;
	}
	
	public Planet(double m, double d, Planet s) {
		this(m, d);
		setSun(s);
	}
	
	public Planet(String n, double m, double d, Planet s) {
		this(n, m, d);
		setSun(s);
	}
	
	//Suns
	public Planet(double d) {
		mass = d;
		type = 0;
		name = "Sun";
	}
	
	public Planet(double d, int i) {
		this(e(d, i));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		askForPlanets();
		
		
	}
	
	//Returns d*10^i
	public static double e(double d, int i) {
		return Math.pow(10, i)*d;
	}
	
	public static String scientific(double d) {
		String ret = d + "";
		int place = -1;
		for (int i = 0; i < ret.length(); i++) {
			if(ret.substring(i, i+1).equals("E")) {
				place = i;
			}
		}
		String ret2 = ret;
		if (place != -1) ret2 = ret.substring(0, place) + "*10^" + ret.substring(place+1);
		return ret2;
		/*String ret = "";
		if (Math.abs(d) >= 10) {
			int place = 0;
			double findPlace = d;
			while (Math.abs(findPlace) >= 10) {
				findPlace = findPlace / 10;
				System.out.println(findPlace);
				place++;
			}
			ret = findPlace + "*10^" + place;
			return ret;
		}
		if (Math.abs(d) < 1) {
			int place = 0;
			double findPlace = d;
			while (Math.abs(findPlace) >= 10) {
				findPlace = findPlace * 10;
				place++;
			}
			ret = findPlace + "*10^" + place;
			return ret;
		}
		return ret;*/
	}
	
	public String toString() {
		String ret = "";
		if (type == 1) {
			ret += name + ":\nMass: " + scientific(mass) 
				+ "kg\nDistace to Sun: " 
				+ distance +"m\nOrbital Speed: " + scientific(orbitalSpeed)
				+ "m/s\nOrbital Period: " + scientific(orbitalPeriod) + "s";
		}
		if (type == 2) {
			ret += name + "(orbiting"+getSun().name+" ):\n" 
					+"Mass: " + scientific(mass) 
				+ "kg\nDistace to Planet: " 
				+ distance +"m\nOrbital Speed: " + scientific(orbitalSpeed)
				+ "m/s\nOrbital Period: " + scientific(orbitalPeriod) + "s";
		}
		if (type == 0) {
			ret += name + ":\nMass: " + scientific(mass) + "kg";
		}
		return ret;
	}

	public double calculateOrbitalSpeed() {
		//v=√(GM/R)
		return Math.sqrt((G * sun.getMass())/distance);
		
	}
	public double calculateOrbitalPeriod() {
		//T^2/R^3 = 4pi^2/GM
		//T^2 = 4pi^2R^3/GM
		double opp = (4 * Math.pow(Math.PI, 2) / (G * sun.getMass()));
		double both = opp * Math.pow(distance, 3);
		return Math.sqrt(both);
	}
	
	public static Planet askForSun() {
		Planet su = null;
		System.out.println("What would you like the mass of your sun to be? Use a negative number to just use Earth's sun.");
		Scanner s = new Scanner(System.in);
		String res = s.nextLine();
		try {
			double m = Double.parseDouble(res);
			if (m > 0) {
				su = new Planet(m);
			}
			else {
				su = new Planet(e(1.99, 30));
			}
		}
		catch (Exception E) {
			su = new Planet(e(1.99, 30));
		}
		return su;
	}
	
	public static Planet askForPlanet(Planet su) {
		Planet p = null;
		boolean canContinue = false;
		Scanner s = new Scanner(System.in);
		System.out.println("What would you like to name your planet?");
		String n = s.nextLine();
		double m = 0;
		while (!canContinue) {
			System.out.println("What would you like the mass of your planet to be?");
			try {
				m = Double.parseDouble(s.nextLine());
				canContinue = true;
			}
			catch (Exception e) {
				System.out.println("Invalid number. Try again.");
			}
		}
		double d = 0;
		canContinue = false;
		while (!canContinue) {
			System.out.println("What would you like the distance from the sun of your planet to be?");
			try {
				d = Double.parseDouble(s.nextLine());
				canContinue = true;
			}
			catch (Exception e) {
				System.out.println("Invalid number. Try again.");
			}
		}
		p = new Planet(n, m, d, su);
		return p;
	}
	
	public static void askForPlanets() {
		planets.add(askForSun());
		while(true) {
			System.out.println("Do you want to look at your list of planets or add a new one?\n\tType 1 for add\n\tType 2 for look");
		Scanner s = new Scanner(System.in);
		if (s.nextLine().equals("1")) {
			System.out.println("Do you want to add a planet or a moon?\n\tType 1 for planet\n\tType 2 for moon");
			Scanner s2 = new Scanner(System.in);
			if (s2.nextLine().equals("2")) {
				System.out.println("Which planet should this moon orbit?");
				String n = s2.nextLine();
				for (int i = 0; i < planets.size(); i++) {
					if (planets.get(i).name.toUpperCase().equals(n.toUpperCase())) {
						planets.add(askForPlanet(planets.get(i)));
					}
				}
			}
			else {
				planets.add(askForPlanet(planets.get(0)));
			}
		}
		else {
			for (int i = 0; i < planets.size(); i++) {
				System.out.println(planets.get(i));
			}
		}
		}
		
	}
	
	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Planet getSun() {
		return sun;
	}

	public void setSun(Planet sun) {
		this.sun = sun;
	}

	public double getOrbitalPeriod() {
		return orbitalPeriod;
	}

	public void setOrbitalPeriod(double orbitalPeriod) {
		this.orbitalPeriod = orbitalPeriod;
	}

	public double getSunGravForce() {
		return sunGravForce;
	}

	public void setSunGravForce(double sunGravForce) {
		this.sunGravForce = sunGravForce;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getOrbitalSpeed() {
		return orbitalSpeed;
	}

	public void setOrbitalSpeed(double orbitalSpeed) {
		this.orbitalSpeed = orbitalSpeed;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
