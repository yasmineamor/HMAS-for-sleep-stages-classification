
import java.io.Serializable;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.Date;

/**
 * This class is the XCS itself. It stores the population and the posed problem.
 * The class provides methods for the main learning cycles in XCS distinguishing between
 * single-step and multi-step problems as well as exploration vs. exploitation trials.
 * Moreover, it handles the performance evaluation.
 *
 * @author    Martin V. Butz
 * @version   XCSJava 1.0
 * @since     JDK1.1
 */
public class XCS implements Serializable
{
    /**
     * Stores the posed problem.
     */
    private Environment env;

    /**
     * Stores the current population of XCS.
     */
    private XClassifierSet pop;

    /**
     * Stores the specified output File, where the performance will be written.
     */
    private File outFile;

    /**
     * Specifies the number of exploration problems/trials to solve in one experiment.
     */
    private int maxProblems=20000;

    /**
     * Specifies the number of investigated experiments.
     */
    private int nrExps=10;


    /**
     * Stores the relevant constants in XCS.
     *
     * @see XCSConstants
     */
    private static XCSConstants cons;

    public static void main(String args[])
    {
        String envFileString=null;
        Environment e = null;

	XCSConstants.setSeed(1+ (new Date()).getTime() % 10000);

        if(args.length >= 6){
          if(args[0].equals("mp")){
            System.out.println("Construct Multiplexer problem of length "+args[2]+" and payoff type "+args[5]);
            e=new MPEnvironment((new Integer(args[2])).intValue(), (new Integer(args[5])).intValue());

          }else if(args[0].equals("and")){
            System.out.println("Construct AND problem of length "+args[2]+" and payoff type "+args[5]);
            e=new ANDEnvironment((new Integer(args[2])).intValue());

          }else{ // the environment is specified in a file:
            System.out.println("The environment ");
            XCSConstants.epsilon_0 = (Integer.parseInt(args[4])) / 100.0;
            e=new FileEnvironment(args[0],Integer.parseInt(args[2]),Integer.parseInt(args[3]),Integer.parseInt(args[4]),Integer.parseInt(args[5]));

          }
        }else{
	    // In case of a wrong number of arguments print out the usage.
            System.out.println("Usage: java XCS inputFile outputFile messageLength numberOfActions maxPayoff payoffLandscape [numberOfTrials] [numberOfExperiments]");
            return;
        }

        XCS xcs=new XCS(e, args[1]);
	if(args.length > 6){
	    xcs.setNumberOfTrials((new Integer(args[6])).intValue());
	}
	if(args.length > 7){
	    xcs.setNumberOfExperiments((new Integer(args[7])).intValue());
	}
        xcs.runXCS();
        return;
    }

    /**
     * Constructs the XCS system.
     */
    public XCS(Environment e, String outFileString)
    {
        env=e;

        //specify output file
        outFile=new File(outFileString);

        //initialize XCS
        pop=null;
        cons = new XCSConstants();
    }

    /**
     * Resets the maximal number of trials in one experiment. (The default is set to 20000)
     */
    public void setNumberOfTrials(int trials)
    {
	maxProblems=trials;
    }

    /**
     * Resets the number of experiments. (The default is set to ten.)
     */
    public void setNumberOfExperiments(int exps)
    {
	nrExps=exps;
    }

    /**
     * Runs the posed problem with XCS.
     * The function essentially initializes the output File and then runs the experiments.
     *
     * @see #startExperiments
     */
    public void runXCS()
    {
        FileWriter fW=null;
        BufferedWriter bW=null;
        PrintWriter pW = null;
        try{
            fW = new FileWriter(outFile);
            bW = new BufferedWriter(fW);
            pW = new PrintWriter(bW);
        }catch(Exception e){System.out.println("Mistake in create file Writers"+e);}

        startExperiments(pW);

        try{
            pW.flush();
            bW.flush();
            fW.flush();
            fW.close();
        }catch(Exception e){System.out.println("Mistake in closing the file writer!"+e);}
    }

    /**
     * This function runs the number of experiments specified. After the initialization of the empty
     * population, either one single- or one multi-step problem is executed.
     *
     * @param pW The print writer refers to the output file for the performance evaluation. The experiments are separated by
     * the text 'Next Experiment'.
     * @see XClassifierSet#XClassifierSet(int)
     * @see Environment#isMultiStepProblem
     * @see #doOneSingleStepExperiment
     * @see #doOneMultiStepExperiment
     */
    private void startExperiments(PrintWriter pW)
    {
        for(int expCounter=0; expCounter < nrExps; expCounter++){
            pW.println("Next Experiment");
            System.out.println("Experiment Nr."+(expCounter+1));

            //Initialize Population
            pop=new XClassifierSet(env.getNrActions());

            if(!env.isMultiStepProblem()){
                doOneSingleStepExperiment(pW);
            }else{
                doOneMultiStepExperiment(pW);
            }
            pop.printSet();
            pop=null;
        }
    }

    /**************************** Single Step Experiments ***************************/

    /**
     * Executes one single-step experiment monitoring the performance.
     *
     * @see #doOneSingleStepProblemExplore
     * @see #doOneSingleStepProblemExploit
     * @see #writePerformance
     */
    void doOneSingleStepExperiment(PrintWriter pW)
    {
        int explore=0;
        int[] correct = new int[50];
        double[] sysError = new double[50];

        for (int exploreProbC=0; exploreProbC < maxProblems; exploreProbC+=explore){
            explore = (explore+1)%2;

            String state = env.resetState();

	    if(explore==1){
                doOneSingleStepProblemExplore(state, exploreProbC);
            }else{
                doOneSingleStepProblemExploit(state, exploreProbC, correct, sysError);
            }
            if(exploreProbC%50==0 && explore==0 && exploreProbC>0){
                writePerformance(pW, correct, sysError, exploreProbC);
            }
        }
    }

    /**
     * Executes one main learning loop for a single step problem.
     *
     * @see XClassifierSet#XClassifierSet(String,XClassifierSet,int,int)
     * @see PredictionArray#PredictionArray
     * @see PredictionArray#randomActionWinner
     * @see XClassifierSet#XClassifierSet(XClassifierSet,int)
     * @see Environment#executeAction
     * @see XClassifierSet#updateSet
     * @see XClassifierSet#runGA
     * @param state The actual problem instance.
     * @param counter The number of problems observed so far in exploration.
     */
    private void doOneSingleStepProblemExplore(String state, int counter)
    {
        XClassifierSet matchSet = new XClassifierSet(state, pop, counter, env.getNrActions());

        PredictionArray predictionArray = new PredictionArray(matchSet, env.getNrActions());

        int actionWinner = predictionArray.randomActionWinner();

        XClassifierSet actionSet = new XClassifierSet(matchSet, actionWinner);

        double reward = env.executeAction( actionWinner );

        actionSet.updateSet(0., reward);

        actionSet.runGA(counter, state, env.getNrActions());
    }

    /**
     * Executes one main performance evaluation loop for a single step problem.
     *
     * @see XClassifierSet#XClassifierSet(String,XClassifierSet,int,int)
     * @see PredictionArray#PredictionArray
     * @see PredictionArray#bestActionWinner
     * @see Environment#executeAction
     * @param state The actual problem instance.
     * @param counter The number of problems observed so far in exploration.
     * @param correct The array stores the last fifty correct/wrong exploitation classifications.
     * @param sysError The array stores the last fifty predicted-received reward differences.
     */
    private void doOneSingleStepProblemExploit(String state, int counter, int[] correct, double[] sysError)
    {
        XClassifierSet matchSet = new XClassifierSet(state, pop, counter, env.getNrActions());

        PredictionArray predictionArray = new PredictionArray (matchSet, env.getNrActions());

        int actionWinner = predictionArray.bestActionWinner();

        double reward = env.executeAction( actionWinner );

        if(env.wasCorrect())
            correct[counter%50]=1;
        else
            correct[counter%50]=0;

        sysError[counter%50] = Math.abs(reward - predictionArray.getBestValue());
    }




    /************************** Multi-step Experiments *******************************/

    /**
     * Executes one multi step experiment and monitors the performance.
     *
     * @see #doOneMultiStepProblemExplore
     * @see #doOneMultiStepProblemExploit
     * @see #writePerformance
     */
    void doOneMultiStepExperiment(PrintWriter pW)
    {
        int explore=0, exploreStepCounter=0;
        int[] stepsToFood = new int[50];
        double[] sysError = new double[50];

        for (int exploreTrialC=0; exploreTrialC < maxProblems; exploreTrialC+=explore){
            explore = (explore+1)%2;

            String state = env.resetState();
            if(explore==1){
                exploreStepCounter=doOneMultiStepProblemExplore(state, exploreStepCounter);
            }else{
                doOneMultiStepProblemExploit(state, stepsToFood, sysError, exploreTrialC, exploreStepCounter);
            }
            if(exploreTrialC%50==0 && explore==0 && exploreTrialC>0){
                writePerformance(pW, stepsToFood, sysError, exploreTrialC);
            }
        }
    }

    /**
     * Executes one learning trial in a multi-step problem.
     * A trial normally ends when the food is reached or the teletransportation
     * threshold is reached.
     *
     * @see XCSConstants#teletransportation
     * @see XClassifierSet#XClassifierSet(String,XClassifierSet,int,int)
     * @see PredictionArray#PredictionArray
     * @see PredictionArray#randomActionWinner
     * @see XClassifierSet#XClassifierSet(XClassifierSet,int)
     * @see Environment#executeAction
     * @see XClassifierSet#confirmClassifiersInSet
     * @see XClassifierSet#updateSet
     * @see XClassifierSet#runGA
     * @param state The reseted perception of the problem.
     * @param stepCounter The number of exploration steps executed so far.
     * @return The updated number of exploration setps.
     */
    private int doOneMultiStepProblemExplore(String state, int stepCounter)
    {
        XClassifierSet prevActionSet=null;
        double prevReward=0.;
        int steps;
        String prevState=null;

        for(steps=0; steps<cons.teletransportation; steps++){
            XClassifierSet matchSet = new XClassifierSet(state, pop, stepCounter+steps, env.getNrActions());

            PredictionArray predictionArray = new PredictionArray(matchSet, env.getNrActions());

            int actionWinner = predictionArray.randomActionWinner();

            XClassifierSet actionSet = new XClassifierSet(matchSet, actionWinner);

            double reward = env.executeAction( actionWinner );

	    if(prevActionSet!=null){
		prevActionSet.confirmClassifiersInSet();
		prevActionSet.updateSet(predictionArray.getBestValue(), prevReward);
                prevActionSet.runGA(stepCounter+steps, prevState, env.getNrActions());
            }

            if(env.doReset()){
		actionSet.confirmClassifiersInSet();
                actionSet.updateSet(0., reward);
                actionSet.runGA(stepCounter+steps, state, env.getNrActions());
                break;
            }
            prevActionSet=actionSet;
            prevReward=reward;
            prevState=state;
            state=env.getCurrentState();
        }
        return stepCounter+steps;
    }

    /**
     * Executes one performance evaluation trial a multi-step problem.
     * Similar to Wilson's exploitation the function updates the parameters of the classifiers
     * but it does not execute the genetic algorithm.
     * A trial normally ends when the food is reached or the teletransportation
     * threshold is reached.
     *
     * @see XCSConstants#teletransportation
     * @see XClassifierSet#XClassifierSet(String,XClassifierSet,int,int)
     * @see PredictionArray#PredictionArray
     * @see PredictionArray#bestActionWinner
     * @see XClassifierSet#XClassifierSet(XClassifierSet,int)
     * @see Environment#executeAction
     * @see XClassifierSet#confirmClassifiersInSet
     * @see XClassifierSet#updateSet
     * @param state The reseted perception of the problem.
     * @param stepsToGoal The last fifty numbers of steps to the goal during exploitation.
     * @param sysError The averaged prediction errors of the last fifty trials.
     * @param trialCounter The number of exploration trials executed so far.
     * @param stepCounter The number of exploration steps executed so far.
     */
    private void doOneMultiStepProblemExploit(String state, int[] stepsToGoal, double[] sysError, int trialCounter, int stepCounter)
    {
        XClassifierSet prevActionSet=null;
        double prevReward=0., prevPrediction=0.;
        int steps;

        sysError[trialCounter%50]=0.;
        for( steps=0; steps<cons.teletransportation; steps++){
            XClassifierSet matchSet = new XClassifierSet(state, pop, stepCounter, env.getNrActions());

            PredictionArray predictionArray = new PredictionArray(matchSet, env.getNrActions());

            int actionWinner = predictionArray.bestActionWinner();

            XClassifierSet actionSet = new XClassifierSet(matchSet, actionWinner);

            double reward = env.executeAction( actionWinner );

            if(prevActionSet!=null){
		prevActionSet.confirmClassifiersInSet();
                prevActionSet.updateSet(predictionArray.getBestValue(), prevReward);
                sysError[trialCounter%50]+=(double)Math.abs(cons.gamma * predictionArray.getValue(actionWinner) + prevReward
                                                            - prevPrediction) / (double)env.getMaxPayoff();
            }

            if(env.doReset()){
		actionSet.confirmClassifiersInSet();
                actionSet.updateSet(0., reward);
                sysError[trialCounter%50]+=(double)Math.abs(reward - predictionArray.getValue(actionWinner))
                    / (double)env.getMaxPayoff();
                steps++;
                break;
            }
            prevActionSet=actionSet;
            prevPrediction=predictionArray.getValue(actionWinner);
            prevReward=reward;
            state=env.getCurrentState();
        }
        sysError[trialCounter%50]/=steps;
        stepsToGoal[trialCounter%50]=steps;
    }

    /*##########---- Output ----##########*/

    /**
     * Writes the performance of the XCS to the specified file.
     * The function writes time performance systemError actualPopulationSizeInMacroClassifiers.
     * Performance and system error are averaged over the last fifty trials.
     *
     * @param pW The reference where to write the performance.
     * @param performance The performance in the last fifty exploration trials.
     * @param sysError The system error in the last fifty exploration trials.
     * @param exploreProbC The number of exploration trials executed so far.
     */
    private void writePerformance(PrintWriter pW, int[] performance, double[] sysError, int exploreProbC)
    {
        double perf=0.;
        double serr=0.;
        for(int i=0; i<50; i++){
            perf+=performance[i];
            serr+=sysError[i];
        }
        perf/=50.;
        serr/=50.;
        pW.println("" + exploreProbC + " " + (float)perf + " " + (float)serr + " " + pop.getSize());
	// System.out.println("" + exploreProbC + " " + (float)perf + " " + (float)serr + " " + pop.getSize());
    }
}
