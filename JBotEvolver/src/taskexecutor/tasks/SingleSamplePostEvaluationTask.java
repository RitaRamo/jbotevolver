package taskexecutor.tasks;

import java.util.ArrayList;
import java.util.Random;
import result.Result;
import simulation.Simulator;
import simulation.robot.Robot;
import taskexecutor.results.PostEvaluationResult;
import taskexecutor.results.SimpleFitnessResult;
import tests.Cronometer;
import evolutionaryrobotics.JBotEvolver;
import evolutionaryrobotics.evaluationfunctions.EvaluationFunction;
import evolutionaryrobotics.neuralnetworks.Chromosome;

public class SingleSamplePostEvaluationTask extends JBotEvolverTask {
	
	private int fitnesssample;
	private int sample;
	private double fitness = 0;
	private Chromosome chromosome;
	private JBotEvolver jBotEvolver;
	private double threshold = 0;
	private int run;
	
	public SingleSamplePostEvaluationTask(int run, JBotEvolver jBotEvolver, int fitnesssample, Chromosome chromosome, int sample, double threshold) {
		super(jBotEvolver);
		this.fitnesssample = fitnesssample;
		this.chromosome = chromosome;
		this.jBotEvolver = jBotEvolver;
		this.sample = sample;
		this.threshold = threshold;
		this.run = run;
	}
	
	@Override
	public void run() {
		jBotEvolver.getArguments().get("--environment").setArgument("fitnesssample", fitnesssample);
		
		Simulator simulator = jBotEvolver.createSimulator(new Random(sample));
		simulator.setFileProvider(getFileProvider());
		ArrayList<Robot> robots = jBotEvolver.createRobots(simulator);
		jBotEvolver.setChromosome(robots, chromosome);
		simulator.addRobots(robots);
//		jBotEvolver.setupBestIndividual(simulator);
		EvaluationFunction eval = jBotEvolver.getEvaluationFunction();
		simulator.addCallback(eval);
		simulator.simulate();
		if(threshold > 0)
			fitness= eval.getFitness() >= threshold ? 1 : 0;
		else
			fitness= eval.getFitness();
	}
	public Result getResult() {
		PostEvaluationResult fr = new PostEvaluationResult(run,fitnesssample,fitness,sample);
		return fr;
	}
}