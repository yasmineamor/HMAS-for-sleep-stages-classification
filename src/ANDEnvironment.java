// author: M.M.Haji
// email : mhaji@cse.shirazu.ac.ir

import java.io.Serializable;

public class ANDEnvironment implements Environment, Serializable
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

    /**
     * Specifies the maximal payoff possible in this environment.
     */
    private int maxPayoff;

    /**
     * Stores the current problem.
     */
    private char[] currentState;


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
    private final int nrActions=5;

    /**
     * Constructs the environment according to the specified problem length and chosen payoff type.
     * Essentially the relevant constants for the environment are calculated here.
     * Moreover, the problem array is generated
     *
     * @param length Specifies the problem length.
     * @param payoffMap Specifies if a payoff map should be provided or a 1000/0 payoff.
     */
    public ANDEnvironment(int length)
    {
        conLength=length;

        currentState = new char[conLength];

        payoffLandscape=false;
        maxPayoff=1000;

        correct=false;
        reset=false;
    }


    /**
     * Generates a new random problem instance.
     */
    public String resetState()
    {
        for(int i=0; i<conLength; i++){
            if(XCSConstants.drand()<0.5){
                currentState[i]='0';
            }else{
                currentState[i]='1';
            }
        }
        reset=false;
        return (new String(currentState));
    }

    /**
     * Executes the action and determines the reward.
     * Distinguishes between the payoff landscape and the 0/1000 reward.
     *
     * @param action Specifies the classification.
     */
    public double executeAction(int action,int action_w)
    {

      boolean all_one = true;
      for(int i=0; i < conLength; i++)
        if( currentState[i] == '0' ){
          all_one = false;
          break;
        }

      int ret = 0;
      if( all_one ){    	  
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
      }
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
	public double executeAction(int action) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double executeAction1(int action, int action_w) {
		// TODO Auto-generated method stub
		return 0;
	}


	
	
}
