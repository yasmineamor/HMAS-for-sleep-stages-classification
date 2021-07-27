import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.StringTokenizer;


import javax.swing.JOptionPane;
public class choix_Ac {
	private BufferedReader env_file;
    //String env_file_name="C:\\Users\\yasmine\\Desktop\\newdb\\training\\30s\\test2fic.txt";
    
	

		
		
   public int fun (String env_file_name, String state)
    { 
			 int actionWinner =0;
			 int exploreProbC=0;
			 Environment e = null;
			 String[] arg ={env_file_name, env_file_name, "10", "5", "1000","200", "100000", "1"};
			 //XCSConstants.setSeed(1+ (new Date()).getTime() % 10000);
	         //XCSConstants.epsilon_0 = (Integer.parseInt(arg[4])) / 100.0;
	         e=new FileEnvironment(arg[0],Integer.parseInt(arg[2]),Integer.parseInt(arg[3]),Integer.parseInt(arg[4]),Integer.parseInt(arg[5]));
	        //System.out.println(state);
	         XCS xcs=new XCS(e, arg[1]);
	         XClassifierSet pop;
	         int exploreexploit=0;
             int[] correct = new int[50];
             double[] sysError = new double[50];
             
             PrintWriter pW = null;
	         try{
        	
        	
	        	 XClassifierSet Fpop=new XClassifierSet(e.getNrActions());
        	          // int i =1;
	        	 for(int expCounter=0; expCounter < 1; expCounter++){
	        		
	                 pop=new XClassifierSet(e.getNrActions());
	                 
	                
         	       
         	    	
         	    	XClassifierSet matchSet = new XClassifierSet(state, pop, exploreProbC, e.getNrActions());
         	        PredictionArray predictionArray = new PredictionArray(matchSet, e.getNrActions());
       	            actionWinner = predictionArray.randomActionWinner();
                     
         	      if(exploreProbC%50==0 && exploreexploit==0 && exploreProbC>0){
                     
         	    	double perf=0.;
         	        double serr=0.;
         	        for(int j=0; j<50; j++){
         	            perf+=correct[j];
         	            serr+=sysError[j];
         	        }
         	        perf/=50.;
         	        serr/=50.;
         	        pW.println("" + exploreProbC + " " + (float)perf + " " + (float)serr + " " + pop.getSize());
         		
         	    }
                  }
	                 
	                 
	                 
        
	        	 
        
        
        
        
		}
		catch(Exception ex){
            ex.printStackTrace();
			}
		
		return actionWinner;
		
		 }
		
		


}
