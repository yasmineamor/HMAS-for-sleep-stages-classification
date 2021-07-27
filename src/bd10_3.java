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


public class bd10_3 extends Agent{
	//String state_10_2;
	String tab_states[]=new String[100000];
	
	//Object Ac10_1;
	Object act_w;
	//String env_file_name="C:\\Users\\yasmine\\Desktop\\newdb\\training\\10s\\test2fic.txt";
    boolean authorise=true;
    int numstate=5;

	
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
				
				try {
					String env_file_name="C:\\\\Users\\\\yasmine\\\\Desktop\\\\newdb\\\\training\\\\10s\\\\"+listefichiers[countRep];
			        BufferedReader env_file = new BufferedReader(new FileReader(env_file_name));
			        String env_line= null;
			        int count=0;
			       
			        while ((env_line = env_file.readLine())!=null) { 
						    
											       	
						    StringTokenizer strTok = new StringTokenizer(env_line, "\t:;=", false);
						    String state = new String(strTok.nextToken());
				        	tab_states[count]=state;
				        	count++;

						
							}	
			        }
			catch (IOException e) {				
				e.printStackTrace();
				System.out.println("lecture echouée!!");
								
				}
				
				//envoi du state2
				if(authorise==true) {
				ACLMessage bd = new ACLMessage(ACLMessage.INFORM);
			    bd.setContent(tab_states[2]);
			    bd.addReceiver(new AID("Ag10_3", AID.ISLOCALNAME));
				send(bd);}
				
				authorise=false;
				
				
					
				
				//envoi des state à chaque demande provenant de ag10_3
				//reception de msg de ag10_3 demandant un state
				ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));			
				 
				 if ( msg!=null  ) {
					  //System.out.println("msg de ag10_3:"+msg.getContent());
					  Object WA = null;
					try {
						WA = msg.getContentObject();
						//System.out.print("WA"+WA);
					} catch (UnreadableException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					  
						//envoi state à ag10_3
					  if(numstate<7957) {
						  ACLMessage newst =new ACLMessage(ACLMessage.INFORM);
							 newst.setContent(tab_states[numstate]);
							 newst.addReceiver(new AID("Ag10_3", AID.ISLOCALNAME));
							 send(newst);
						 numstate=numstate+3;
						 
					  }else {
						  System.out.println("end du fichier");
						  doDelete();

					  }
					 
				 }
					}
				}
				
				if(numstate==7957){
					countRep++;
				}
			}
		});
				
				
}
	
}
