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

public class Ag10_3 extends Agent {
	int numstate=2;
	String state_10_3;
	Object Ac10_3;
Object act_w;
//String env_file_name="C:\\Users\\yasmine\\Desktop\\newdb\\training\\10s\\test2fic.txt";
int exploreProbC=0;
Object numerostate;
int NumState=0;

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
					
					File repertoire= new File ("C:\\Users\\yasmine\\Desktop\\newdb\\training\\10s");
					String [] listefichiers;

					int countRep = 0;
					listefichiers=repertoire.list();
					
					while((countRep<listefichiers.length))
					{
						if(listefichiers[countRep].endsWith(".txt")==true){
					
					
						String env_file_name="C:\\\\Users\\\\yasmine\\\\Desktop\\\\newdb\\\\training\\\\10s\\\\"+listefichiers[countRep];
								
								       	
					
								
								
								ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));			
								 
								 if ( msg!=null  ) {
									 if(msg.getSender().getLocalName().equalsIgnoreCase("bd30")) {
										 try {
											// System.out.println("winning action bien recupar l'agent Ag10_1");	
											 //recuperation d'objet
											 
											 numerostate=(Object)msg.getContentObject();
											 NumState=(int) numerostate;
											} catch (UnreadableException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}	
									 }
									 else if(msg.getSender().getLocalName().equalsIgnoreCase("bd10_3")) {
										 
									
									 //System.out.println("msg reçu de bd10_3");
									 //recuperer msg(state) et determiner l'action correspondante
									 state_10_3=msg.getContent();
									 if(NumState<2500) {
									 choix_Act d= new choix_Act();
									 //System.out.println("State10_3:"+ state_10_3);
									  int	act=d.fun(env_file_name, state_10_3);									  
									  Ac10_3=(Object)act;
									  //System.out.println("action ag10_3"+Ac10_3);
									  
									  
									  //envoi de l'action à env
									  ACLMessage action10_3 = new ACLMessage(ACLMessage.INFORM);
									  try {
										  action10_3.setContentObject((Serializable) Ac10_3);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									  action10_3.addReceiver(new AID("Env", AID.ISLOCALNAME));
									  send(action10_3);
									  //System.out.println("action envoyée à env");
									
									 }
									 else {
										 
										 choixAct10_explore choix= new choixAct10_explore();
										 //System.out.println("State10_3:"+ state_10_3);
										  int	act=choix.fun(env_file_name, state_10_3);									  
										  Ac10_3=(Object)act;
										  //System.out.println("action ag10_3"+Ac10_3);
										  
										  
										  //envoi de l'action à env
										  ACLMessage action10_3 = new ACLMessage(ACLMessage.INFORM);
										  try {
											  action10_3.setContentObject((Serializable) Ac10_3);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										  action10_3.addReceiver(new AID("Env", AID.ISLOCALNAME));
										  send(action10_3);
										  //System.out.println("action envoyée à env");
										  
										  
									 }
								    
									 }
									 
								else if ( msg.getSender().getLocalName().equalsIgnoreCase("Env")  ) {
									
									 try {
										 //System.out.println("winning action bien recupar l'agent Ag10_3");	
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
										int ACT_10_3;
										ACT_W=(int) act_w;
										ACT_10_3=(int) Ac10_3;
										 
										String[] arg ={env_file_name, env_file_name, "10", "5", "1000","0", "5000", "1"};
								        e=new FileEnvironment(arg[0],Integer.parseInt(arg[2]),Integer.parseInt(arg[3]),Integer.parseInt(arg[4]),Integer.parseInt(arg[5]));
								        
								        XClassifierSet pop=new XClassifierSet(e.getNrActions());								        
										XClassifierSet matchSet = new XClassifierSet(state_10_3, pop, 0, e.getNrActions());
								        XClassifierSet actionSet =new XClassifierSet(matchSet,ACT_W);
										
										//ANDEnvironment envi= new ANDEnvironment(800);
					       	            e.executeAction(ACT_10_3,ACT_W);
					       	            double Reward=e.executeAction(ACT_10_3,ACT_W);
					       	            //System.out.print("REWARD Ag10_3:"+Reward);
					       	            //System.out.println("\n");
					       	            actionSet.updateSet(1000, Reward);
					       	            actionSet.runGA(0, state_10_3, 5);
					       	            //System.out.print("Update du reward est effectué avec succès!!");
					       	            //System.out.println("\n");

					       	         
										 }
								else {
					 			//Pendant que le message n'est pas encore arrivé le comportement est bloqué
						 		block();
						}

					
				}
						}
						if(numstate==7955){
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
