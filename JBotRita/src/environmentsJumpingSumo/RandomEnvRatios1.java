package environmentsJumpingSumo;

import java.awt.Color;

import net.jafama.FastMath;
import physicalobjects.IntensityPrey;
import mathutils.Vector2d;
import simulation.Simulator;
import simulation.environment.Environment;
import simulation.physicalobjects.Prey;
import simulation.physicalobjects.Wall;
import simulation.robot.Robot;
import simulation.util.Arguments;

public class RandomEnvRatios1 extends JS_Environment { //ratio5
	
	private static final double MAX_WIDTH_LIMIT_FOR_WALL = 5;//4
	private static final double MAX_WIDTH_LIMITALLOW_FOR_WALL = 4.625; //3.7
	private static final double MIN_WIDTH_LIMIT_FOR_WALL = 1.75;//1.4
	private static final double WIDTH_HEIGHT_WALLSURRONDED = 10;
	private Environment env;
	//double random1, random2;
	
	private double ratio;
	
	public RandomEnvRatios1(Simulator simulator, Arguments arguments) {
		super(simulator, arguments);

	}

	public void setup(Environment env, Simulator simulator, double ratio	) { // use this for LoopsOfEnvironmnt
		super.setup(simulator);
		this.env=env;
		this.ratio=ratio;
//		this.random1=random1;
//		this.random2=random2;
		init();
	}

	@Override
	public void setup(Simulator simulator) {
		super.setup(simulator);
		env=this;
		
		init();
		
	}

	public void init() {
		env.addStaticObject(new Wall(simulator, 0, WIDTH_HEIGHT_WALLSURRONDED/2, WIDTH_HEIGHT_WALLSURRONDED, 0.08)); // HorizontalWallNorth
		env.addStaticObject(new Wall(simulator, WIDTH_HEIGHT_WALLSURRONDED/2, 0, 0.08, WIDTH_HEIGHT_WALLSURRONDED)); // VerticalEast
		env.addStaticObject(new Wall(simulator, 0, -WIDTH_HEIGHT_WALLSURRONDED/2, WIDTH_HEIGHT_WALLSURRONDED, 0.08)); // HorizontalSouth
		env.addStaticObject(new Wall(simulator, -WIDTH_HEIGHT_WALLSURRONDED/2, 0, 0.08, WIDTH_HEIGHT_WALLSURRONDED)); // VerticalWest
		
		
		double a = 1.3;

		
		double b= a* FastMath.sqrtQuick(ratio*ratio - 1);  
		
		env.addStaticObject(new Wall(simulator, 0, 0, b * 2 - 0.8,0.08)); //centerWall
		addPreyAndRobotPosition(0, a, 0);
		
		
		if ( b<3.8) {
			
			double availableSpaceForNewWall= MAX_WIDTH_LIMIT_FOR_WALL-(b-0.4)-0.4;
			double newWidth=random.nextDouble()*(availableSpaceForNewWall-0.2)+0.2;
			env.addStaticObject(new Wall(simulator,  MAX_WIDTH_LIMIT_FOR_WALL -( newWidth) / 2,0, newWidth, 0.08)); // rightWall
			env.addStaticObject(new Wall(simulator, -MAX_WIDTH_LIMIT_FOR_WALL+ (newWidth) / 2, 0, newWidth, 0.08)); // leftWall
		}		
			
		
		
		
		
		
		
//		double b = random.nextDouble()
//				* (MAX_WIDTH_LIMITALLOW_FOR_WALL - MIN_WIDTH_LIMIT_FOR_WALL)
//				+ MIN_WIDTH_LIMIT_FOR_WALL;
//
//		
//		double a = b / FastMath.sqrtQuick(ratio*ratio - 1);  
//		
//		env.addStaticObject(new Wall(simulator, 0, 0, b * 2 - 0.8,0.08)); //centerWall
//		addPreyAndRobotPosition(0, a, 0);
//		
//		
//		if ( b<3.8) {
//			
//		double availableSpaceForNewWall= MAX_WIDTH_LIMIT_FOR_WALL-(b-0.4)-0.4;
//		double newWidth=random.nextDouble()*(availableSpaceForNewWall-0.2)+0.2;
//			env.addStaticObject(new Wall(simulator,  MAX_WIDTH_LIMIT_FOR_WALL -( newWidth) / 2,0, newWidth, 0.08)); // rightWall
//			env.addStaticObject(new Wall(simulator, -MAX_WIDTH_LIMIT_FOR_WALL+ (newWidth) / 2, 0, newWidth, 0.08)); // leftWall
//		}		
//			
		
	
		
		
		
		
//		if (random.nextDouble() < 0.5) {
//
//			env.addStaticObject(new Wall(simulator, 0, 0, b * 2 - 0.4, 0.125)); //centerWall
//			
//			addPreyAndRobotPosition(0, a, 0);
//		
//			if (random.nextDouble() < 0.5 && b<2) {
//				double width = b - 0.2;
//				env.addStaticObject(new Wall(simulator, width / 2 + 2.5 - width,a, width, 0.125)); // rightWall
//				env.addStaticObject(new Wall(simulator, -width / 2 - (2.5 - width), a, width, 0.125)); // leftWall
//				
////				env.addStaticObject(new Wall(simulator, width / 2 + 2.5 - width,0, width, 0.125)); // rightWall
////				env.addStaticObject(new Wall(simulator, -width / 2 - (2.5 - width), 0, width, 0.125)); // leftWall
//				
//				
//			
//			}
//		}else{
//			
//			double randomYPosition_For_Wall=random.nextDouble()* (MAX_HEIGHT_LIMIT_FOR_WALL - MIN_HEIGHT_LIMIT_FOR_WALL)
//					+ MIN_HEIGHT_LIMIT_FOR_WALL; 
//					
//			double newb_forWall=b+ (2.3-b);  // -> putting the wall larger (in case <0.5-> to put the right wall larger, else left wall larger
//
//			double b_forTheOtherWall= random.nextDouble()* (MAX_WIDTH_LIMIT_FOR_WALL - MIN_WIDTH_LIMIT_FOR_WALL)+ MIN_WIDTH_LIMIT_FOR_WALL -0.6;   
//			  	
//			if(random.nextDouble()<0.5){  
//		
//				env.addStaticObject(new Wall(simulator, -newb_forWall/2 -0.2 ,randomYPosition_For_Wall, newb_forWall, 0.125));  //leftWall
//				addPreyAndRobotPosition(-b, a,randomYPosition_For_Wall);
//				env.addStaticObject(new Wall(simulator, b_forTheOtherWall / 2 + 2.5 - b_forTheOtherWall, randomYPosition_For_Wall, b_forTheOtherWall, 0.125)); // rightWall
//			
//			}			
//			else{
//				
//				env.addStaticObject(new Wall(simulator, newb_forWall/2+0.2 ,randomYPosition_For_Wall, newb_forWall, 0.125)); //rightWall
//				addPreyAndRobotPosition(b, a,randomYPosition_For_Wall);
//				env.addStaticObject(new Wall(simulator, -b_forTheOtherWall / 2 - 2.5 + b_forTheOtherWall, randomYPosition_For_Wall, b_forTheOtherWall, 0.125)); // leftWall
//			}
//		
//		}
		for(Robot r : env.getRobots()) {
			r.setOrientation(simulator.getRandom().nextDouble()*Math.PI*2);
		}
	}

	
	public void addPreyAndRobotPosition(double x, double y, double randomYPosition){
		
		env.addPrey(new IntensityPrey(simulator, "Prey " + 0,
				new Vector2d(x, y+ randomYPosition), 0, PREY_MASS, PREY_RADIUS,
				1));
		
		env.getRobots().get(0).setPosition(new Vector2d( x, -y+ randomYPosition));
	
	}
	
	@Override
	public void update(double time) {
		change_PreyInitialDistance = false;
		for (Prey nextPrey : simulator.getEnvironment().getPrey()) {
			IntensityPrey prey = (IntensityPrey) nextPrey;
			if (nextPrey.isEnabled() && prey.getIntensity() <= 0) {
				simulator.stopSimulation();
				numberOfFoodSuccessfullyForaged = 1;
			}
			if (prey.getIntensity() <= 0)
				prey.setColor(Color.WHITE);
			else if (prey.getIntensity() < 9)
				prey.setColor(Color.BLACK);
			else if (prey.getIntensity() < 13)
				prey.setColor(Color.GREEN.darker());
			else
				prey.setColor(Color.RED);
		}

	}

}
