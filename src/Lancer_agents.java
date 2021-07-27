import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class Lancer_agents {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Runtime rt = Runtime.instance();
		Profile p = new ProfileImpl();
		
		AgentController envi;
		AgentController A30;
		AgentController A10_1;
		AgentController A10_2;
		AgentController A10_3;
		AgentController BD10_1;
		AgentController BD10_2;
		AgentController BD10_3;
		AgentController BD30;



	

		  /*  Création du runtime  */
           // Runtime rt = Runtime.instance();
            rt.setCloseVM(true);

        /*  Lancement de la plate-forme  */
        Profile pMain = new ProfileImpl(null, 8888, null);
        AgentContainer mc = rt.createMainContainer(pMain);
        ContainerController cc = rt.createAgentContainer(p);
        /*  Création du conteneur pour les agents  */
        //Profile pContainer = new ProfileImpl(null, 8888, null);
        //AgentContainer cont = rt.createAgentContainer(pContainer);

        /*  Lancement d'un agent  */
        try {
        	
        	envi = cc.createNewAgent("Env", "Env", null);
    		envi.start();
    		
    		
    		BD10_1 = cc.createNewAgent("bd10_1", "bd10_1", null);
    		BD10_1.start();
    		
    		BD10_2 = cc.createNewAgent("bd10_2", "bd10_2", null);
    		BD10_2.start();
    		
    		BD10_3 = cc.createNewAgent("bd10_3", "bd10_3", null);
    		BD10_3.start();
    		
    		BD30 = cc.createNewAgent("bd30", "bd30", null);
    		BD30.start();
    		
    		A10_1 = cc.createNewAgent("Ag10_1", "Ag10_1", null);
    		A10_1.start();
    		
    		A10_2 = cc.createNewAgent("Ag10_2", "Ag10_2", null);
    		A10_2.start();
    		
    		A10_3 = cc.createNewAgent("Ag10_3", "Ag10_3", null);
    		A10_3.start();
    		
    		A30 = cc.createNewAgent("Ag30", "Ag30", null);
    		A30.start();
    		 		
				
			
        }
        catch (Exception e) {
			e.printStackTrace();
		}
       
	}
}
