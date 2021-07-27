// author: M.M.Haji
// email : mhaji@cse.shirazu.ac.ir

import java.io.*;
import java.util.*;

public class FileEnvironment implements Environment, Serializable
{
    /**
     * Specifies the length of each presented problem.
     */
    public static int conLength;


    /**
     * Defines if either a payoff landscape or a 1000/0 payoff is provided after the execution of a classification.
     * The payoff Landscape is constructed as in Wilson's
     * Classifier fitness based on accuracy paper (Evolutionary Computation Journal, 1995).
     */
    public static boolean payoffLandscape;


    /*#####---- The following parameters are set internally dependent on the value of conLength and payoffLandscape ----#####*/

    private BufferedReader env_file;
    private String env_line; // one line from environment
    private String env_file_name;
    private StringTokenizer strTok; // to tokenize env_line

    /**
     * Specifies the maximal payoff possible in this environment.
     */
    private int maxPayoff;

    /**
     * Stores the current problem.
     */
    String currentState;


    /*#####---- The following parameters are used internally ----#####*/

    /**
     * Stores if the last classification was correct.
     */
    private boolean correct;

    /**
     * Is set to true after a classification was executed
     */
    private boolean reset;

    /**
     * In the problem there are two classifications possible!
     */
    private final int nrActions;

    /**
     * Constructs the environment according to the specified problem length and chosen payoff type.
     * Essentially the relevant constants for the environment are calculated here.
     * Moreover, the problem array is generated
     *
     * @param file_name Specifies the environment file.
     * @param condition_length Specifies the message length.
     * @param num_of_actions Specifies the number of actions.
     * @param max_payoff Specifies the maximum payoff (typically 1000).
     */
    public FileEnvironment(String file_name, int condition_length, int num_of_actions, int max_payoff,int payOff)
    {
      nrActions = num_of_actions;
      env_file_name = file_name;
      try {
        env_file = new BufferedReader(new FileReader(file_name));
      }
      catch (Exception ex) {
        System.err.println("error: can't read from environment file");
      }

      conLength = condition_length;

      currentState = "";

      if( payOff == 0 )
        payoffLandscape = false; // <- ???
      else
        payoffLandscape = true;
      maxPayoff = max_payoff;

      correct = false;
      reset = false;
    }


    /**
     * Generates a new random problem instance.
     */
    public String resetState()
    {
      try{
        do{
          env_line = env_file.readLine();
          if (env_line == null) {
            env_file = new BufferedReader(new FileReader(env_file_name));
            env_line = env_file.readLine();
          }
        }while( env_line.length() < 1 );
          strTok = new StringTokenizer(env_line, "\t,:;=+ ", false);
      }catch(Exception ex){
        System.err.println("error: can't read from environment file");
      }
      reset = false;

      currentState = new String(strTok.nextToken());
      return currentState;
    }

    /**
     * Executes the action and determines the reward.
     * Distinguishes between the payoff landscape and the 0/1000 reward.
     *
     * @param action Specifies the classification.
     */
    public double executeAction(int action, int action_w)
    {

        int ret = 0;
            	  
      	  if( Math.abs(action-action_w)==0 )
            ret = maxPayoff;
          else if( Math.abs(action-action_w)==1 )
          	ret = 800;
          else if( Math.abs(action-action_w)==2 )
          	ret = 600;
          else if( Math.abs(action-action_w)==3 )
          	ret = 400;
          else 
          	ret = 200;     
        
        if( ret == maxPayoff )
          correct = true;
        else
          correct = false;

        reset = true;
        return (double) ret;
    	
    	
    	
    	
    
     
    }


    /**
     * Returns true if the last executed action was a correct classification
     */
    public boolean wasCorrect()
    {
        return correct;
    }

    /**
     * Returns false since the problem is a single step problem
     */
    public boolean isMultiStepProblem()
    {
        return false;
    }

    /**
     * Returns true after the current problem was classified
     */
    public boolean doReset()
    {
        return reset;
    }

    /**
     * Returns the problem length
     */
    public int getConditionLength()
    {
        return conLength;
    }

    /**
     * Returns the maximal payoff possible in the current problem.
     * The maximal payoff is determined out of the payoff type. If the payoff type 1000/0 is selected
     * this function will return 1000, otherwise the maximal value depends on the problem size.
     */
    public int getMaxPayoff()
    {
        return maxPayoff;
    }

    /**
     * Returns the number of possible actions.
     */
    public int getNrActions()
    {
        return nrActions;
    }

    /**
     * Returns the current problem
     */
    public String getCurrentState()
    {
        return new String(currentState);
    }


	@Override
	public double executeAction1(int action, int action_w) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double executeAction(int action) {
		// TODO Auto-generated method stub
		return 0;
	}
}
