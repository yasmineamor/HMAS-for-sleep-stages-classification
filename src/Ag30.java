import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.StringTokenizer;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class Ag30 extends Agent {
	int numstate=0;
	String state_30;
	Object Ac30;
Object act_w;
//String env_file_name="C:\\Users\\yasmine\\Desktop\\newdb\\training\\30s\\test2fic.txt";
int exploreProbC=0;

	protected void setup() {
		System.out.println("Agent "+getLocalName()+" est lancé "+"\n");
	
		try {
			
		// Création de desciprion de l'agent [Superviseur]
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		// Enregistrement de la description de l'agent dans DF (Directory Facilitator)
		DFService.register(this, dfd);
		System.out.println("Agent "+getLocalName()+" est enregistré dans DF (Directory Facilitator) ");
		} catch (FIPAException e) {
		e.printStackTrace();}

		
				addBehaviour(new CyclicBehaviour(this) {

				@Override
				public void action() {
					
					File repertoire= new File ("C:\\Users\\yasmine\\Desktop\\newdb\\training\\30s");
					String [] listefichiers;

					int countRep = 0;
					listefichiers=repertoire.list();
					
					while((countRep<listefichiers.length))
					{
						if(listefichiers[countRep].endsWith(".txt")==true){
					
					
						String env_file_name="C:\\\\Users\\\\yasmine\\\\Desktop\\\\newdb\\\\training\\\\30s\\\\"+listefichiers[countRep];
								
								ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));			
								 
								 if ( msg!=null  ) {
									 
									 if(msg.getSender().getLocalName().equalsIgnoreCase("bd30")) {
									 //System.out.println("msg reçu de bd30");
									 //recuperer msg(state) et determiner l'action correspondante
									 state_30=msg.getContent();
									 choix_Ac d= new choix_Ac();
									 //System.out.println("State30:"+ state_30);
									 int	act=d.fun(env_file_name, state_30);									  
									 Ac30=(Object)act;
									  //System.out.println("action ag30"+Ac30);
									  
									  
									  //envoi de l'action à env
									  ACLMessage action30 = new ACLMessage(ACLMessage.INFORM);
									  try {
										action30.setContentObject((Serializable) Ac30);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									  action30.addReceiver(new AID("Env", AID.ISLOCALNAME));
									  send(action30);
									  //System.out.println("action envoyée à env");
									  
									  
									 }
									 
								else if ( msg.getSender().getLocalName().equalsIgnoreCase("Env")  ) {
									
									 try {
										 //System.out.println("winning action bien recupar l'agent Ag30");	
										 //recuperation d'objet
										 
											act_w=(Object)msg.getContentObject();
										} catch (UnreadableException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}	
										//System.out.print("\n");
										//System.out.print("AC_W:"+act_w);
										//System.out.print("\n");
										/********************************Reward: update des paramètre************************************/
										Environment e = null;
										int ACT_W;
										int ACT_30;
										ACT_W=(int) act_w;
										ACT_30=(int) Ac30;
										 
										String[] arg ={env_file_name, env_file_name , "10", "5", "1000","0", "5000", "1"};
								        e=new FileEnvironment(arg[0],Integer.parseInt(arg[2]),Integer.parseInt(arg[3]),Integer.parseInt(arg[4]),Integer.parseInt(arg[5]));
										
								        XClassifierSet pop=new XClassifierSet(e.getNrActions());								        
										XClassifierSet matchSet = new XClassifierSet(state_30, pop, 0, e.getNrActions());
								        XClassifierSet actionSet =new XClassifierSet(matchSet,ACT_W);
										
										//ANDEnvironment envi= new ANDEnvironment(800);
					       	            e.executeAction(ACT_30,ACT_W);
					       	            double Reward=e.executeAction(ACT_30,ACT_W);
					       	            //System.out.print("REWARD Ag30:"+Reward);
					       	            actionSet.updateSet(1000, Reward);
					       	            actionSet.runGA(0, state_30, 5);
					       	            //System.out.print("Update du reward est effectué avec succès!!");
					       	            
					       	     
										 }
								else {
					 							//Pendant que le message n'est pas encore arrivé le comportement est bloqué
						 
							block();
						}

					
				}
				}
						if(numstate==2651){
							countRep++;
						}
					}
				}
				});
		
	
	}
	
	protected void takeDown() {
		// Suppression de l'agent depuis le DF
		try {
			DFService.deregister(this);
			System.out.println(getLocalName()+" DEREGISTERED WITH THE DF");
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}
	
}
