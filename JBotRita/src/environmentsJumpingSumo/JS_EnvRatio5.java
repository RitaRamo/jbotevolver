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

public class JS_EnvRatio5 extends JS_Environment { //ratio5

	private Environment env;
	private static final double MAX_WIDTH_LIMIT_FOR_WALL = 5;//4
	private static final double MAX_WIDTH_LIMIT_FOR_B = 4.5; //3.24
	private static final double MIN_WIDTH_LIMIT_FOR_B = 4; //3.24
	
	
	private static final double WIDTH_HEIGHT_WALLSURRONDED = 10;
	
	

	public JS_EnvRatio5(Simulator simulator, Arguments arguments) {
		super(simulator, arguments);
	}

	public void setup(Environment env, Simulator simulator) { // use this for LoopsOfEnvironmnt
		super.setup(simulator);
		this.env = env;
		init();
	}

	@Override
	public void setup(Simulator simulator) {
		super.setup(simulator);
		env = this;
		init();

	}

	public void init() {
		
		env.addStaticObject(new Wall(simulator, 0, WIDTH_HEIGHT_WALLSURRONDED/2, WIDTH_HEIGHT_WALLSURRONDED, 0.125)); // HorizontalWallNorth
		env.addStaticObject(new Wall(simulator, WIDTH_HEIGHT_WALLSURRONDED/2, 0, 0.125, WIDTH_HEIGHT_WALLSURRONDED)); // VerticalEast
		env.addStaticObject(new Wall(simulator, 0, -WIDTH_HEIGHT_WALLSURRONDED/2, WIDTH_HEIGHT_WALLSURRONDED, 0.125)); // HorizontalSouth
		env.addStaticObject(new Wall(simulator, -WIDTH_HEIGHT_WALLSURRONDED/2, 0, 0.125, WIDTH_HEIGHT_WALLSURRONDED)); // VerticalWest
		
		
		double b = random.nextDouble()
				* (MAX_WIDTH_LIMIT_FOR_B - MIN_WIDTH_LIMIT_FOR_B)
				+ MIN_WIDTH_LIMIT_FOR_B;
	
		
		double a = 16 * b / 63; // ratio 6
		
		//double new_b=b-1.7;
		double new_b=b-1.9;
		double new_a = 16*new_b/63;
		
		
		env.addStaticObject(new Wall(simulator, -(MAX_WIDTH_LIMIT_FOR_WALL-(new_b+MAX_WIDTH_LIMIT_FOR_WALL-0.4)/2),
				a-new_a, new_b+MAX_WIDTH_LIMIT_FOR_WALL -0.4, 0.08)); // centerWall
		
		env.addStaticObject(new Wall(simulator, MAX_WIDTH_LIMIT_FOR_WALL-(new_b+MAX_WIDTH_LIMIT_FOR_WALL-0.4)/2,
				-(a-new_a), new_b+MAX_WIDTH_LIMIT_FOR_WALL-0.4, 0.08));
		
		env.getRobots().get(0).setPosition(new Vector2d(0,-a ));
		
		
		
        double b_up=b-0.6;
		
		double a_up = 16 * b_up / 63; // ratio 6
		
		//double new_b_up=b_up-1.7;
		double new_b_up=b_up-2.125;
		double new_a_up = 16*new_b_up/63;
		
		
		env.addStaticObject(new Wall(simulator, -(MAX_WIDTH_LIMIT_FOR_WALL-(new_b_up+MAX_WIDTH_LIMIT_FOR_WALL-0.4)/2),
				(a_up-new_a_up)+a*2, new_b_up+MAX_WIDTH_LIMIT_FOR_WALL -0.4, 0.08)); // centerWall
		
		env.addStaticObject(new Wall(simulator, MAX_WIDTH_LIMIT_FOR_WALL-(new_b_up+MAX_WIDTH_LIMIT_FOR_WALL-0.4)/2,
				-(a_up-new_a_up)+a*2, new_b_up+MAX_WIDTH_LIMIT_FOR_WALL-0.4, 0.08));
		
		
		env.addPrey(new IntensityPrey(simulator, "Prey " + 0, new Vector2d(0, a_up+a*2), 0, PREY_MASS, PREY_RADIUS,
				1));
		
		
		for(Robot r : env.getRobots()) {
			r.setOrientation(simulator.getRandom().nextDouble()*Math.PI*2);
		}
	}

	public void addPreyAndRobotPosition(double x, double y,
			double randomYPosition) {

		env.addPrey(new IntensityPrey(simulator, "Prey " + 0, new Vector2d(x, y
				+ randomYPosition), 0, PREY_MASS, PREY_RADIUS,
				1));

		env.getRobots().get(0)
				.setPosition(new Vector2d(x, -y + randomYPosition));

	}

	@Override
	public void update(double time) {
		change_PreyInitialDistance = false;
		for (Prey nextPrey : simulator.getEnvironment().getPrey()) {
			IntensityPrey prey = (IntensityPrey) nextPrey;
			if (nextPrey.isEnabled() && prey.getIntensity() <= 0) {
				simulator.stopSimulation();
				numberOfFoodSuccessfullyForaged = 1;
				preyEated = prey;
				change_PreyInitialDistance = true;
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
