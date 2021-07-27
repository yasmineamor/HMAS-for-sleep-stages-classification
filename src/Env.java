import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.StringTokenizer;

import javax.swing.text.TabableView;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class Env extends Agent {
	static Object Action_Collection[] = new Object[4];
    int Winning_action;
    int last_Winning_action=0;
	Object Acti;
	String state_30;
	Object TT[] = new Object[100000];
	Object action30;
	Object action10_1;
	Object action10_2;
	Object action10_3;
	float nbf=0;
	float PCC;
	boolean ok;
	//int j=0;
	Object numstate;
	int NumState=0;
	int correct0;
	int correct1;
	int correct2;
	int correct3;
	int correct4;

	// class w classified as ...
	int W_N1;
	int W_N2;
	int W_N3;
	int W_R;
	
	// class N1 classified as ...
	int N1_W;
	int N1_N2;
	int N1_N3;
	int N1_R;
	
	// class N2 classified as ...
	int N2_W;
	int N2_N1;
	int N2_N3;
	int N2_R;
	
	// class N3 classified as ...
	int N3_W;
	int N3_N1;
	int N3_N2;
	int N3_R;
	
	// class R classifie as ...
	int R_W;
	int R_N1;
	int R_N2;
	int R_N3;

	
	int[][] Matrice_Confusion=new int[6][6];

	
	static Object tableau_final[] = new Object[100000];
	
	/** Cette méthode est appelé directement apèes la création de l'agent pour permettre
	  * l'initialisation et l'affectation des différents comportements à cet agent 
	  * */
	public static String [] A= {"Ag30","Ag10_1","Ag10_2","Ag10_3"};	
	protected void setup() {
		System.out.println(getLocalName()+" STARTED");
		
			try {
			// Création de desciprion de l'agent [Acheteur]
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			// Enregistrement de la description de l'agent dans DF (Directory Facilitator)
			DFService.register(this, dfd);
			System.out.println(getLocalName()+" REGISTERED WITH THE DF");
			} catch (FIPAException e) {
			e.printStackTrace();
			}
			
			
			
		
		
			
			addBehaviour(new CyclicBehaviour() {

				public void action() {
					
					int [] done = new int [A.length];

				    for (int i=0; i<A.length;i++)
					{
						done[i]=0;
					}
					int count=0;
					
					//lecture de la base de données de test
					//int j=0;
					//int k=0;
					
					
					
					
					try {
						String env_file_name="C:\\Users\\yasmine\\Desktop\\newdb\\testbd30.txt";
				        BufferedReader env_file = new BufferedReader(new FileReader(env_file_name));
				        String env_line= null;
				        int a=0;
				        String sCurrentLine;
				        while (((sCurrentLine = env_file.readLine()) != null)) { { 
							   
					        	TT[a]=sCurrentLine;
					        	
					        	a++;

							
								}	
				        }}
				catch (IOException e) {				
					e.printStackTrace();
					System.out.println("lecture echouée!!");
									
					}
					
					
				   //Reception du state de l'environnement
				    MessageTemplate modele = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
				    while (count< A.length) {
				    
				   	ACLMessage messageRecu = myAgent.receive(modele);
					if ( messageRecu!=null  ) 
				 	   /***tant que la boite n"est pas vide**/
						if(messageRecu.getSender().getLocalName().equalsIgnoreCase("bd30")){
							try {
								 	
								 //recuperation d'objet
								 
								 numstate=(Object)messageRecu.getContentObject();
								 NumState=(int) numstate;
								} catch (UnreadableException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}	
							
							
							
								
						}
						
						else
					   { 			    String ag = messageRecu.getSender().getLocalName();
					                				
					                int p = recherche(ag,A);
					               
					            	if(done[p]==0)
									   {
								    		if(p==0) {
								    			try {
													action30=messageRecu.getContentObject();
													Action_Collection[0]=action30;
													//System.out.println("action30:"+action30);																													
													System.out.print("\n");	
													done [p]=1;
											  		   count++; 
												} catch (UnreadableException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
								    			

								    		}
								    		if(p==1) {
								    			try {
													action10_1=messageRecu.getContentObject();
													Action_Collection[1]=action10_1;
													//System.out.println("action10_1:"+action10_1);																													
													System.out.print("\n");	
													done [p]=1;
											  		   count++; 
												} catch (UnreadableException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
								    			

								    		}
								    		if(p==2) {
								    			try {
													action10_2=messageRecu.getContentObject();
													Action_Collection[2]=action10_2;
                                                    //System.out.println("action10_2:"+action10_2);																													
													System.out.print("\n");	
													done [p]=1;
											  		   count++; 
												} catch (UnreadableException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
								    			

								    		}
								    		if(p==3) {
								    			try {
													action10_3=messageRecu.getContentObject();
													Action_Collection[3]=action10_3;
													//System.out.println("action10_3:"+action10_3);																													
													System.out.print("\n");	
													done [p]=1;
											  		   count++; 
												} catch (UnreadableException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
								    			

								    		}  
									   } 
					            	
					            	
					            	
					            	if((done[0]==1)&&(done[1]==1)&&(done[2]==1)&&(done[3]==1)) {
					            		//System.out.print("envoyer un reply");
					            		ACLMessage reply=messageRecu.createReply();
					            		/****************************************ACTION COLLECTION*************************************************/
									    System.out.print("ACTION COLLECTION:");
										for(int j=0;j<4;j++) {
										
										System.out.print("|"+Action_Collection[j]);
										
										
										
										}
										  System.out.print("\n");
										  
										  /**************************************WINNING ACTION*************************************************/
										  
										  int A30,A10_1,A10_2,A10_3;
										  int nb0=0,nb1=0,nb2=0,nb3=0,nb4=0;
											 
											 A30=Integer.parseInt((String) Action_Collection[0].toString());
											 A10_1=Integer.parseInt((String) Action_Collection[1].toString());
											 A10_2=Integer.parseInt((String) Action_Collection[2].toString());
											 A10_3=Integer.parseInt((String) Action_Collection[3].toString());
											 
											 
										// calcul du nombre d'agents qui ont donné la classe 0 (w) 
											 if(A30==0) {
												 nb0++;
											 }
											 if(A10_1==0) {
												 nb0++;
											 }
											 if(A10_2==0) {
												 nb0++;
											 }
											 if(A10_3==0) {
												 nb0++;
											 }
											// calcul du nombre d'agents qui ont donné la classe 1 (N1) 
											 if(A30==1) {
												 nb1++;
											 }
											 if(A10_1==1) {
												 nb1++;
											 }
											 if(A10_2==1) {
												 nb1++;
											 }
											 if(A10_3==1) {
												 nb1++;
											 }
														 
											// calcul du nombre d'agents qui ont donné la classe 2 (N2) 
											 if(A30==2) {
												 nb2++;
											 }
											 if(A10_1==2) {
												 nb2++;
											 }
											 if(A10_2==2) {
												 nb2++;
											 }
											 if(A10_3==2) {
												 nb2++;
											 }
											// calcul du nombre d'agents qui ont donné la classe 3 (N3)
											 if(A30==3) {
												 nb3++;
											 }
											 if(A10_1==3) {
												 nb3++;
											 }
											 if(A10_2==3) {
												 nb3++;
											 }
											 if(A10_3==3) {
												 nb3++;
											 }
											// calcul du nombre d'agents qui ont donné la classe 4 (R) 
											 if(A30==4) {
												 nb4++;
											 }
											 if(A10_1==4) {
												 nb4++;
											 }
											 if(A10_2==4) {
												 nb4++;
											 }
											 if(A10_3==4) {
												 nb4++;
											 }
										
										// la plupart des agents ont choisi la classe 0
										 
										 if((nb0>nb1)&&(nb0>nb2)&&(nb0>nb3)&&(nb0>nb4)) {
											 
											 
											 if((last_Winning_action!=0)&&(ok==true)) {												 
												 int [] tab = new int [2];
												 tab[0]=last_Winning_action;
												 tab[1]=0;
												
												 
												 int n = tab.length;
												 int randomNumber = (int) Math.random()*n ;
												 int nombreAleaDeTonTableau = tab[randomNumber];
												 Winning_action= nombreAleaDeTonTableau;
											 }else {
												 Winning_action=0;
											 }
											
											 int B=Integer.parseInt((String) TT[NumState]);
											 if(B==Winning_action) {
												 nbf++;
												 if(Winning_action==0) {
													 correct0++;
												 }
												 if(Winning_action==1) {
													 correct1++;
												 }
												 if(Winning_action==2) {
													 correct2++;
												 }
												 if(Winning_action==3) {
													 correct3++;
												 }
												 if(Winning_action==4) {
													 correct4++;
												 }
											 }else {
												 if ((Winning_action==1)&&(B==0))
												 {
													 W_N1++;
												 }
												 if ((Winning_action==2)&&(B==0))
												 {
													 W_N2++;
												 }
												 if ((Winning_action==3)&&(B==0))
												 {
													 W_N3++;
												 }
												 if ((Winning_action==4)&&(B==0))
												 {
													 W_R++;
												 }
												 if ((Winning_action==0)&&(B==1))
												 {
													 N1_W++;
												 }
												 if ((Winning_action==2)&&(B==1))
												 {
													 N1_N2++;
												 }
												 if ((Winning_action==3)&&(B==1))
												 {
													 N1_N3++;
												 }
												 if ((Winning_action==4)&&(B==1))
												 {
													 N1_R++;
												 }
												 if ((Winning_action==0)&&(B==2))
												 {
													 N2_W++;
												 }
												 if ((Winning_action==1)&&(B==2))
												 {
													 N2_N1++;
												 }
												 if ((Winning_action==3)&&(B==2))
												 {
													 N2_N3++;
												 }
												 if ((Winning_action==4)&&(B==2))
												 {
													 N2_R++;
												 }
												 if ((Winning_action==0)&&(B==3))
												 {
													 N3_W++;
												 }
												 if ((Winning_action==1)&&(B==3))
												 {
													 N3_N1++;
												 }
												 if ((Winning_action==2)&&(B==3))
												 {
													 N3_N2++;
												 }
												 if ((Winning_action==4)&&(B==3))
												 {
													 N3_R++;
												 }
												 if ((Winning_action==0)&&(B==4))
												 {
													 R_W++;
												 }
												 if ((Winning_action==1)&&(B==4))
												 {
													 R_N1++;
												 }
												 if ((Winning_action==2)&&(B==4))
												 {
													 R_N2++;
												 }
												 if ((Winning_action==3)&&(B==4))
												 {
													 R_N3++;
												 }
											 }
											 
											 
										 }
										// la plupart des agents ont choisi la classe 1
										 
										 if((nb1>nb0)&&(nb1>nb2)&&(nb1>nb3)&&(nb1>nb4)) {
											 
											 if((last_Winning_action!=1)&&(ok==true)) {												 
											
												 int [] tab = new int [2];
												 tab[0]=last_Winning_action;
												 tab[1]=1;
												
												 
												 int n = tab.length;
												 int randomNumber = (int) Math.random()*n ;
												 int nombreAleaDeTonTableau = tab[randomNumber];
												 Winning_action= nombreAleaDeTonTableau;
												 
											 }else {
												 Winning_action=1;
											 }
											 //Winning_action=1;
											 int B=Integer.parseInt((String) TT[NumState]);
											 if(B==Winning_action) {
												 nbf++;
												 if(Winning_action==0) {
													 correct0++;
												 }
												 if(Winning_action==1) {
													 correct1++;
												 }
												 if(Winning_action==2) {
													 correct2++;
												 }
												 if(Winning_action==3) {
													 correct3++;
												 }
												 if(Winning_action==4) {
													 correct4++;
												 }
											 }else {
												 if ((Winning_action==1)&&(B==0))
												 {
													 W_N1++;
												 }
												 if ((Winning_action==2)&&(B==0))
												 {
													 W_N2++;
												 }
												 if ((Winning_action==3)&&(B==0))
												 {
													 W_N3++;
												 }
												 if ((Winning_action==4)&&(B==0))
												 {
													 W_R++;
												 }
												 if ((Winning_action==0)&&(B==1))
												 {
													 N1_W++;
												 }
												 if ((Winning_action==2)&&(B==1))
												 {
													 N1_N2++;
												 }
												 if ((Winning_action==3)&&(B==1))
												 {
													 N1_N3++;
												 }
												 if ((Winning_action==4)&&(B==1))
												 {
													 N1_R++;
												 }
												 if ((Winning_action==0)&&(B==2))
												 {
													 N2_W++;
												 }
												 if ((Winning_action==1)&&(B==2))
												 {
													 N2_N1++;
												 }
												 if ((Winning_action==3)&&(B==2))
												 {
													 N2_N3++;
												 }
												 if ((Winning_action==4)&&(B==2))
												 {
													 N2_R++;
												 }
												 if ((Winning_action==0)&&(B==3))
												 {
													 N3_W++;
												 }
												 if ((Winning_action==1)&&(B==3))
												 {
													 N3_N1++;
												 }
												 if ((Winning_action==2)&&(B==3))
												 {
													 N3_N2++;
												 }
												 if ((Winning_action==4)&&(B==3))
												 {
													 N3_R++;
												 }
												 if ((Winning_action==0)&&(B==4))
												 {
													 R_W++;
												 }
												 if ((Winning_action==1)&&(B==4))
												 {
													 R_N1++;
												 }
												 if ((Winning_action==2)&&(B==4))
												 {
													 R_N2++;
												 }
												 if ((Winning_action==3)&&(B==4))
												 {
													 R_N3++;
												 }
											 }
										 }
										// la plupart des agents ont choisi la classe 2
										 
										 if((nb2>nb0)&&(nb2>nb1)&&(nb2>nb3)&&(nb2>nb4)) {
											 if((last_Winning_action!=2)&&(ok==true)) {												 
												 int [] tab = new int [2];
												 tab[0]=last_Winning_action;
												 tab[1]=2;
												
												 
												 int n = tab.length;
												 int randomNumber = (int) Math.random()*n ;
												 int nombreAleaDeTonTableau = tab[randomNumber];
												 Winning_action= nombreAleaDeTonTableau;
											 }else {
												 Winning_action=2;
											 }
											 
											// Winning_action=2;
											 int B=Integer.parseInt((String) TT[NumState]);
											 if(B==Winning_action) {
												 nbf++;
												 if(Winning_action==0) {
													 correct0++;
												 }
												 if(Winning_action==1) {
													 correct1++;
												 }
												 if(Winning_action==2) {
													 correct2++;
												 }
												 if(Winning_action==3) {
													 correct3++;
												 }
												 if(Winning_action==4) {
													 correct4++;
												 }
											 }else {
												 if ((Winning_action==1)&&(B==0))
												 {
													 W_N1++;
												 }
												 if ((Winning_action==2)&&(B==0))
												 {
													 W_N2++;
												 }
												 if ((Winning_action==3)&&(B==0))
												 {
													 W_N3++;
												 }
												 if ((Winning_action==4)&&(B==0))
												 {
													 W_R++;
												 }
												 if ((Winning_action==0)&&(B==1))
												 {
													 N1_W++;
												 }
												 if ((Winning_action==2)&&(B==1))
												 {
													 N1_N2++;
												 }
												 if ((Winning_action==3)&&(B==1))
												 {
													 N1_N3++;
												 }
												 if ((Winning_action==4)&&(B==1))
												 {
													 N1_R++;
												 }
												 if ((Winning_action==0)&&(B==2))
												 {
													 N2_W++;
												 }
												 if ((Winning_action==1)&&(B==2))
												 {
													 N2_N1++;
												 }
												 if ((Winning_action==3)&&(B==2))
												 {
													 N2_N3++;
												 }
												 if ((Winning_action==4)&&(B==2))
												 {
													 N2_R++;
												 }
												 if ((Winning_action==0)&&(B==3))
												 {
													 N3_W++;
												 }
												 if ((Winning_action==1)&&(B==3))
												 {
													 N3_N1++;
												 }
												 if ((Winning_action==2)&&(B==3))
												 {
													 N3_N2++;
												 }
												 if ((Winning_action==4)&&(B==3))
												 {
													 N3_R++;
												 }
												 if ((Winning_action==0)&&(B==4))
												 {
													 R_W++;
												 }
												 if ((Winning_action==1)&&(B==4))
												 {
													 R_N1++;
												 }
												 if ((Winning_action==2)&&(B==4))
												 {
													 R_N2++;
												 }
												 if ((Winning_action==3)&&(B==4))
												 {
													 R_N3++;
												 }
											 }
										 }
										// la plupart des agents ont choisi la classe 3
										 
										 if((nb3>nb0)&&(nb3>nb1)&&(nb3>nb2)&&(nb3>nb4)) {
											 if((last_Winning_action!=3)&&(ok==true)) {												 
												 int [] tab = new int [2];
												 tab[0]=last_Winning_action;
												 tab[1]=3;
												
												 
												 int n = tab.length;
												 int randomNumber = (int) Math.random()*n ;
												 int nombreAleaDeTonTableau = tab[randomNumber];
												 Winning_action= nombreAleaDeTonTableau;
											 }else {
												 Winning_action=3;
											 }
											 //Winning_action=3;
											 int B=Integer.parseInt((String) TT[NumState]);
											 if(B==Winning_action) {
												 nbf++;
												 if(Winning_action==0) {
													 correct0++;
												 }
												 if(Winning_action==1) {
													 correct1++;
												 }
												 if(Winning_action==2) {
													 correct2++;
												 }
												 if(Winning_action==3) {
													 correct3++;
												 }
												 if(Winning_action==4) {
													 correct4++;
												 }
											 }else {
												 if ((Winning_action==1)&&(B==0))
												 {
													 W_N1++;
												 }
												 if ((Winning_action==2)&&(B==0))
												 {
													 W_N2++;
												 }
												 if ((Winning_action==3)&&(B==0))
												 {
													 W_N3++;
												 }
												 if ((Winning_action==4)&&(B==0))
												 {
													 W_R++;
												 }
												 if ((Winning_action==0)&&(B==1))
												 {
													 N1_W++;
												 }
												 if ((Winning_action==2)&&(B==1))
												 {
													 N1_N2++;
												 }
												 if ((Winning_action==3)&&(B==1))
												 {
													 N1_N3++;
												 }
												 if ((Winning_action==4)&&(B==1))
												 {
													 N1_R++;
												 }
												 if ((Winning_action==0)&&(B==2))
												 {
													 N2_W++;
												 }
												 if ((Winning_action==1)&&(B==2))
												 {
													 N2_N1++;
												 }
												 if ((Winning_action==3)&&(B==2))
												 {
													 N2_N3++;
												 }
												 if ((Winning_action==4)&&(B==2))
												 {
													 N2_R++;
												 }
												 if ((Winning_action==0)&&(B==3))
												 {
													 N3_W++;
												 }
												 if ((Winning_action==1)&&(B==3))
												 {
													 N3_N1++;
												 }
												 if ((Winning_action==2)&&(B==3))
												 {
													 N3_N2++;
												 }
												 if ((Winning_action==4)&&(B==3))
												 {
													 N3_R++;
												 }
												 if ((Winning_action==0)&&(B==4))
												 {
													 R_W++;
												 }
												 if ((Winning_action==1)&&(B==4))
												 {
													 R_N1++;
												 }
												 if ((Winning_action==2)&&(B==4))
												 {
													 R_N2++;
												 }
												 if ((Winning_action==3)&&(B==4))
												 {
													 R_N3++;
												 }
											 }
										 }
										// la plupart des agents ont choisi la classe 4
										 
										 if((nb4>nb0)&&(nb4>nb1)&&(nb4>nb2)&&(nb4>nb3)) {
											 if((last_Winning_action!=4)&&(ok==true)) {												 
												 int [] tab = new int [2];
												 tab[0]=last_Winning_action;
												 tab[1]=4 ;
												
												 
												 int n = tab.length;
												 int randomNumber = (int) Math.random()*n ;
												 int nombreAleaDeTonTableau = tab[randomNumber];
												 Winning_action= nombreAleaDeTonTableau;
											 }else {
											 
												 Winning_action=4;
											 }
											 
											 
											 //Winning_action=4;
											 int B=Integer.parseInt((String) TT[NumState]);
											 if(B==Winning_action) {
												 nbf++;
												 if(Winning_action==0) {
													 correct0++;
												 }
												 if(Winning_action==1) {
													 correct1++;
												 }
												 if(Winning_action==2) {
													 correct2++;
												 }
												 if(Winning_action==3) {
													 correct3++;
												 }
												 if(Winning_action==4) {
													 correct4++;
												 }
											 }else {
												 if ((Winning_action==1)&&(B==0))
												 {
													 W_N1++;
												 }
												 if ((Winning_action==2)&&(B==0))
												 {
													 W_N2++;
												 }
												 if ((Winning_action==3)&&(B==0))
												 {
													 W_N3++;
												 }
												 if ((Winning_action==4)&&(B==0))
												 {
													 W_R++;
												 }
												 if ((Winning_action==0)&&(B==1))
												 {
													 N1_W++;
												 }
												 if ((Winning_action==2)&&(B==1))
												 {
													 N1_N2++;
												 }
												 if ((Winning_action==3)&&(B==1))
												 {
													 N1_N3++;
												 }
												 if ((Winning_action==4)&&(B==1))
												 {
													 N1_R++;
												 }
												 if ((Winning_action==0)&&(B==2))
												 {
													 N2_W++;
												 }
												 if ((Winning_action==1)&&(B==2))
												 {
													 N2_N1++;
												 }
												 if ((Winning_action==3)&&(B==2))
												 {
													 N2_N3++;
												 }
												 if ((Winning_action==4)&&(B==2))
												 {
													 N2_R++;
												 }
												 if ((Winning_action==0)&&(B==3))
												 {
													 N3_W++;
												 }
												 if ((Winning_action==1)&&(B==3))
												 {
													 N3_N1++;
												 }
												 if ((Winning_action==2)&&(B==3))
												 {
													 N3_N2++;
												 }
												 if ((Winning_action==4)&&(B==3))
												 {
													 N3_R++;
												 }
												 if ((Winning_action==0)&&(B==4))
												 {
													 R_W++;
												 }
												 if ((Winning_action==1)&&(B==4))
												 {
													 R_N1++;
												 }
												 if ((Winning_action==2)&&(B==4))
												 {
													 R_N2++;
												 }
												 if ((Winning_action==3)&&(B==4))
												 {
													 R_N3++;
												 }
											 }
										 }
										 
						            	//tous équivalents			 
										 if(((nb0==1)&&(nb1==1)&&(nb2==1)&&(nb3==1))) {
											 
											 if(ok==true) {
											 if(last_Winning_action==0) {
												 Winning_action=0;
											 }
											 if(last_Winning_action==1) {
												 Winning_action=1;
											 }
											 if(last_Winning_action==2) {
												 Winning_action=2;
											 }
											 if(last_Winning_action==3) {
												 Winning_action=3;
											 }
											 if(last_Winning_action==4) {
												 Winning_action=4;
											 }}else if (ok==false) {
												 int [] tab = new int [4];
												 tab[0]=0;
												 tab[1]=1;
												 tab[2]=2;
												 tab[3]=3;							

												int n = tab.length;
												int randomNumber = (int) Math.random()*n ;
												int nombreAleaDeTonTableau = tab[randomNumber];
												 Winning_action= nombreAleaDeTonTableau;
											 }
										
											 int B=Integer.parseInt((String) TT[NumState]);
											 if(B==Winning_action) {
												 nbf++;
												 if(Winning_action==0) {
													 correct0++;
												 }
												 if(Winning_action==1) {
													 correct1++;
												 }
												 if(Winning_action==2) {
													 correct2++;
												 }
												 if(Winning_action==3) {
													 correct3++;
												 }
												 if(Winning_action==4) {
													 correct4++;
												 }
											 }else {
												 if ((Winning_action==1)&&(B==0))
												 {
													 W_N1++;
												 }
												 if ((Winning_action==2)&&(B==0))
												 {
													 W_N2++;
												 }
												 if ((Winning_action==3)&&(B==0))
												 {
													 W_N3++;
												 }
												 if ((Winning_action==4)&&(B==0))
												 {
													 W_R++;
												 }
												 if ((Winning_action==0)&&(B==1))
												 {
													 N1_W++;
												 }
												 if ((Winning_action==2)&&(B==1))
												 {
													 N1_N2++;
												 }
												 if ((Winning_action==3)&&(B==1))
												 {
													 N1_N3++;
												 }
												 if ((Winning_action==4)&&(B==1))
												 {
													 N1_R++;
												 }
												 if ((Winning_action==0)&&(B==2))
												 {
													 N2_W++;
												 }
												 if ((Winning_action==1)&&(B==2))
												 {
													 N2_N1++;
												 }
												 if ((Winning_action==3)&&(B==2))
												 {
													 N2_N3++;
												 }
												 if ((Winning_action==4)&&(B==2))
												 {
													 N2_R++;
												 }
												 if ((Winning_action==0)&&(B==3))
												 {
													 N3_W++;
												 }
												 if ((Winning_action==1)&&(B==3))
												 {
													 N3_N1++;
												 }
												 if ((Winning_action==2)&&(B==3))
												 {
													 N3_N2++;
												 }
												 if ((Winning_action==4)&&(B==3))
												 {
													 N3_R++;
												 }
												 if ((Winning_action==0)&&(B==4))
												 {
													 R_W++;
												 }
												 if ((Winning_action==1)&&(B==4))
												 {
													 R_N1++;
												 }
												 if ((Winning_action==2)&&(B==4))
												 {
													 R_N2++;
												 }
												 if ((Winning_action==3)&&(B==4))
												 {
													 R_N3++;
												 }
											 }
										 }
						                 if(((nb0==1)&&(nb1==1)&&(nb2==1)&&(nb4==1))) {
											 if(ok==true) {
						                	 if(last_Winning_action==0) {
												 Winning_action=0;
											 }
						                	 if(last_Winning_action==1) {
												 Winning_action=1;
											 }
						                	 if(last_Winning_action==2) {
												 Winning_action=2;
											 }
						                	 if(last_Winning_action==3) {
						                													 
												Winning_action=3;
											 }
						                	 if(last_Winning_action==4) {
												 Winning_action=4;
											 }}else if(ok==false) {
												 int [] tab = new int [4];
												 tab[0]=0;
												 tab[1]=1;
												 tab[2]=2;
												 tab[3]=4;
												 
												int n = tab.length;
												int randomNumber = (int) Math.random()*n ;
												int nombreAleaDeTonTableau = tab[randomNumber];
												Winning_action= nombreAleaDeTonTableau;
											 }
											
											 int B=Integer.parseInt((String) TT[NumState]);
											 if(B==Winning_action) {
												 nbf++;
												 if(Winning_action==0) {
													 correct0++;
												 }
												 if(Winning_action==1) {
													 correct1++;
												 }
												 if(Winning_action==2) {
													 correct2++;
												 }
												 if(Winning_action==3) {
													 correct3++;
												 }
												 if(Winning_action==4) {
													 correct4++;
												 }
											 }else {
												 if ((Winning_action==1)&&(B==0))
												 {
													 W_N1++;
												 }
												 if ((Winning_action==2)&&(B==0))
												 {
													 W_N2++;
												 }
												 if ((Winning_action==3)&&(B==0))
												 {
													 W_N3++;
												 }
												 if ((Winning_action==4)&&(B==0))
												 {
													 W_R++;
												 }
												 if ((Winning_action==0)&&(B==1))
												 {
													 N1_W++;
												 }
												 if ((Winning_action==2)&&(B==1))
												 {
													 N1_N2++;
												 }
												 if ((Winning_action==3)&&(B==1))
												 {
													 N1_N3++;
												 }
												 if ((Winning_action==4)&&(B==1))
												 {
													 N1_R++;
												 }
												 if ((Winning_action==0)&&(B==2))
												 {
													 N2_W++;
												 }
												 if ((Winning_action==1)&&(B==2))
												 {
													 N2_N1++;
												 }
												 if ((Winning_action==3)&&(B==2))
												 {
													 N2_N3++;
												 }
												 if ((Winning_action==4)&&(B==2))
												 {
													 N2_R++;
												 }
												 if ((Winning_action==0)&&(B==3))
												 {
													 N3_W++;
												 }
												 if ((Winning_action==1)&&(B==3))
												 {
													 N3_N1++;
												 }
												 if ((Winning_action==2)&&(B==3))
												 {
													 N3_N2++;
												 }
												 if ((Winning_action==4)&&(B==3))
												 {
													 N3_R++;
												 }
												 if ((Winning_action==0)&&(B==4))
												 {
													 R_W++;
												 }
												 if ((Winning_action==1)&&(B==4))
												 {
													 R_N1++;
												 }
												 if ((Winning_action==2)&&(B==4))
												 {
													 R_N2++;
												 }
												 if ((Winning_action==3)&&(B==4))
												 {
													 R_N3++;
												 }
											 }
										 }
						if(((nb0==1)&&(nb2==1)&&(nb3==1)&&(nb4==1))) {
							if(ok==true) {
							if(last_Winning_action==0) {
								 Winning_action=0;
							 }
							if(last_Winning_action==1) {
								Winning_action=1;
							 }
							if(last_Winning_action==2) {
								 Winning_action=2;
							 }
							if(last_Winning_action==3) {
								 Winning_action=3;
							 }
							if(last_Winning_action==4) {
								 Winning_action=4;
							 }}else if(ok==false) {
								 int [] tab = new int [4];
								 tab[0]=0;
								 tab[1]=2;
								 tab[2]=3;
								 tab[3]=4;
								 
								 int n = tab.length;
								 int randomNumber = (int) Math.random()*n ;
								 int nombreAleaDeTonTableau = tab[randomNumber];
								 Winning_action= nombreAleaDeTonTableau;
							 }
											
											 int B=Integer.parseInt((String) TT[NumState]);
											 if(B==Winning_action) {
												 nbf++;
												 if(Winning_action==0) {
													 correct0++;
												 }
												 if(Winning_action==1) {
													 correct1++;
												 }
												 if(Winning_action==2) {
													 correct2++;
												 }
												 if(Winning_action==3) {
													 correct3++;
												 }
												 if(Winning_action==4) {
													 correct4++;
												 }
											 }else {
												 if ((Winning_action==1)&&(B==0))
												 {
													 W_N1++;
												 }
												 if ((Winning_action==2)&&(B==0))
												 {
													 W_N2++;
												 }
												 if ((Winning_action==3)&&(B==0))
												 {
													 W_N3++;
												 }
												 if ((Winning_action==4)&&(B==0))
												 {
													 W_R++;
												 }
												 if ((Winning_action==0)&&(B==1))
												 {
													 N1_W++;
												 }
												 if ((Winning_action==2)&&(B==1))
												 {
													 N1_N2++;
												 }
												 if ((Winning_action==3)&&(B==1))
												 {
													 N1_N3++;
												 }
												 if ((Winning_action==4)&&(B==1))
												 {
													 N1_R++;
												 }
												 if ((Winning_action==0)&&(B==2))
												 {
													 N2_W++;
												 }
												 if ((Winning_action==1)&&(B==2))
												 {
													 N2_N1++;
												 }
												 if ((Winning_action==3)&&(B==2))
												 {
													 N2_N3++;
												 }
												 if ((Winning_action==4)&&(B==2))
												 {
													 N2_R++;
												 }
												 if ((Winning_action==0)&&(B==3))
												 {
													 N3_W++;
												 }
												 if ((Winning_action==1)&&(B==3))
												 {
													 N3_N1++;
												 }
												 if ((Winning_action==2)&&(B==3))
												 {
													 N3_N2++;
												 }
												 if ((Winning_action==4)&&(B==3))
												 {
													 N3_R++;
												 }
												 if ((Winning_action==0)&&(B==4))
												 {
													 R_W++;
												 }
												 if ((Winning_action==1)&&(B==4))
												 {
													 R_N1++;
												 }
												 if ((Winning_action==2)&&(B==4))
												 {
													 R_N2++;
												 }
												 if ((Winning_action==3)&&(B==4))
												 {
													 R_N3++;
												 }
											 }
										 }
						if(((nb0==1)&&(nb1==1)&&(nb3==1)&&(nb4==1))) {
							 if(ok==true){
							if(last_Winning_action==0) {
								 Winning_action=0;
							 }
							if(last_Winning_action==1) {
								 Winning_action=1;
							 }
							if(last_Winning_action==2) {
								
								 Winning_action= 2;
							 }
							if(last_Winning_action==3) {
								 Winning_action=3;
							 }
							if(last_Winning_action==4) {
								 Winning_action=4;
							 }} else if(ok==false) {
								 int [] tab = new int [4];
								 tab[0]=0;
								 tab[1]=1;
								 tab[2]=3;
								 tab[3]=4;
								 
								 int n = tab.length;
								 int randomNumber = (int) Math.random()*n ;
								 int nombreAleaDeTonTableau = tab[randomNumber];
								 Winning_action= nombreAleaDeTonTableau;
							 }
						
							 int B=Integer.parseInt((String) TT[NumState]);
							 if(B==Winning_action) {
								 nbf++;
								 if(Winning_action==0) {
									 correct0++;
								 }
								 if(Winning_action==1) {
									 correct1++;
								 }
								 if(Winning_action==2) {
									 correct2++;
								 }
								 if(Winning_action==3) {
									 correct3++;
								 }
								 if(Winning_action==4) {
									 correct4++;
								 }
							 }else {
								 if ((Winning_action==1)&&(B==0))
								 {
									 W_N1++;
								 }
								 if ((Winning_action==2)&&(B==0))
								 {
									 W_N2++;
								 }
								 if ((Winning_action==3)&&(B==0))
								 {
									 W_N3++;
								 }
								 if ((Winning_action==4)&&(B==0))
								 {
									 W_R++;
								 }
								 if ((Winning_action==0)&&(B==1))
								 {
									 N1_W++;
								 }
								 if ((Winning_action==2)&&(B==1))
								 {
									 N1_N2++;
								 }
								 if ((Winning_action==3)&&(B==1))
								 {
									 N1_N3++;
								 }
								 if ((Winning_action==4)&&(B==1))
								 {
									 N1_R++;
								 }
								 if ((Winning_action==0)&&(B==2))
								 {
									 N2_W++;
								 }
								 if ((Winning_action==1)&&(B==2))
								 {
									 N2_N1++;
								 }
								 if ((Winning_action==3)&&(B==2))
								 {
									 N2_N3++;
								 }
								 if ((Winning_action==4)&&(B==2))
								 {
									 N2_R++;
								 }
								 if ((Winning_action==0)&&(B==3))
								 {
									 N3_W++;
								 }
								 if ((Winning_action==1)&&(B==3))
								 {
									 N3_N1++;
								 }
								 if ((Winning_action==2)&&(B==3))
								 {
									 N3_N2++;
								 }
								 if ((Winning_action==4)&&(B==3))
								 {
									 N3_R++;
								 }
								 if ((Winning_action==0)&&(B==4))
								 {
									 R_W++;
								 }
								 if ((Winning_action==1)&&(B==4))
								 {
									 R_N1++;
								 }
								 if ((Winning_action==2)&&(B==4))
								 {
									 R_N2++;
								 }
								 if ((Winning_action==3)&&(B==4))
								 {
									 R_N3++;
								 }
							 }
						}

						if(((nb1==1)&&(nb2==1)&&(nb3==1)&&(nb4==1))) {
							 
							if(ok==true) {
							if(last_Winning_action==0) {
								
								 Winning_action= 0;
							 }
							if(last_Winning_action==1) {
								 Winning_action=1;
							 }
							if(last_Winning_action==2) {
								 Winning_action=2;
							 }
							if(last_Winning_action==3) {
								 Winning_action=3;
							 }
							if(last_Winning_action==4) {
								 Winning_action=4;
							 }}
							else if(ok==false) {
								int [] tab = new int [4];
								 tab[0]=1;
								 tab[1]=2;
								 tab[2]=3;
								 tab[3]=4;
								
								 int n = tab.length;
								 int randomNumber = (int) Math.random()*n ;
								 int nombreAleaDeTonTableau = tab[randomNumber];
								 Winning_action= nombreAleaDeTonTableau;
							}
							
							 int B=Integer.parseInt((String) TT[NumState]);
							 if(B==Winning_action) {
								 nbf++;
								 if(Winning_action==0) {
									 correct0++;
								 }
								 if(Winning_action==1) {
									 correct1++;
								 }
								 if(Winning_action==2) {
									 correct2++;
								 }
								 if(Winning_action==3) {
									 correct3++;
								 }
								 if(Winning_action==4) {
									 correct4++;
								 }
							 }else {
								 if ((Winning_action==1)&&(B==0))
								 {
									 W_N1++;
								 }
								 if ((Winning_action==2)&&(B==0))
								 {
									 W_N2++;
								 }
								 if ((Winning_action==3)&&(B==0))
								 {
									 W_N3++;
								 }
								 if ((Winning_action==4)&&(B==0))
								 {
									 W_R++;
								 }
								 if ((Winning_action==0)&&(B==1))
								 {
									 N1_W++;
								 }
								 if ((Winning_action==2)&&(B==1))
								 {
									 N1_N2++;
								 }
								 if ((Winning_action==3)&&(B==1))
								 {
									 N1_N3++;
								 }
								 if ((Winning_action==4)&&(B==1))
								 {
									 N1_R++;
								 }
								 if ((Winning_action==0)&&(B==2))
								 {
									 N2_W++;
								 }
								 if ((Winning_action==1)&&(B==2))
								 {
									 N2_N1++;
								 }
								 if ((Winning_action==3)&&(B==2))
								 {
									 N2_N3++;
								 }
								 if ((Winning_action==4)&&(B==2))
								 {
									 N2_R++;
								 }
								 if ((Winning_action==0)&&(B==3))
								 {
									 N3_W++;
								 }
								 if ((Winning_action==1)&&(B==3))
								 {
									 N3_N1++;
								 }
								 if ((Winning_action==2)&&(B==3))
								 {
									 N3_N2++;
								 }
								 if ((Winning_action==4)&&(B==3))
								 {
									 N3_R++;
								 }
								 if ((Winning_action==0)&&(B==4))
								 {
									 R_W++;
								 }
								 if ((Winning_action==1)&&(B==4))
								 {
									 R_N1++;
								 }
								 if ((Winning_action==2)&&(B==4))
								 {
									 R_N2++;
								 }
								 if ((Winning_action==3)&&(B==4))
								 {
									 R_N3++;
								 }
							 }
						}
						
						
						
						//le nombre d'agents qui ont proposé l'action 0 est égale aux nombre d'agents qui ont choisi l'action 1	 
						if((nb0==2)&&(nb1==2)) {
							 if(ok==true) {
							if(last_Winning_action==0) {
								 Winning_action=0;
							 }
							else if(last_Winning_action==1) {
								 Winning_action=1;
							 }
							else {
								int [] tab = new int [3];
								 tab[0]=last_Winning_action;
								 tab[1]=1;
								 tab[2]=0;
								
								 
								 int n = tab.length;
								 int randomNumber = (int) Math.random()*n ;
								 int nombreAleaDeTonTableau = tab[randomNumber];
								 Winning_action= nombreAleaDeTonTableau;
							}} else if(ok==false) {
								int [] tab = new int [2];
								 tab[0]=0;
								 tab[1]=1;

								 int n = tab.length;
								 int randomNumber = (int) Math.random()*n ;
								 int nombreAleaDeTonTableau = tab[randomNumber];
								 Winning_action= nombreAleaDeTonTableau;
								//Winning_action=1;
							}
							
							
							 int B=Integer.parseInt((String) TT[NumState]);
							 if(B==Winning_action) {
								 nbf++;
								 if(Winning_action==0) {
									 correct0++;
								 }
								 if(Winning_action==1) {
									 correct1++;
								 }
								 if(Winning_action==2) {
									 correct2++;
								 }
								 if(Winning_action==3) {
									 correct3++;
								 }
								 if(Winning_action==4) {
									 correct4++;
								 }
							 }else {
								 if ((Winning_action==1)&&(B==0))
								 {
									 W_N1++;
								 }
								 if ((Winning_action==2)&&(B==0))
								 {
									 W_N2++;
								 }
								 if ((Winning_action==3)&&(B==0))
								 {
									 W_N3++;
								 }
								 if ((Winning_action==4)&&(B==0))
								 {
									 W_R++;
								 }
								 if ((Winning_action==0)&&(B==1))
								 {
									 N1_W++;
								 }
								 if ((Winning_action==2)&&(B==1))
								 {
									 N1_N2++;
								 }
								 if ((Winning_action==3)&&(B==1))
								 {
									 N1_N3++;
								 }
								 if ((Winning_action==4)&&(B==1))
								 {
									 N1_R++;
								 }
								 if ((Winning_action==0)&&(B==2))
								 {
									 N2_W++;
								 }
								 if ((Winning_action==1)&&(B==2))
								 {
									 N2_N1++;
								 }
								 if ((Winning_action==3)&&(B==2))
								 {
									 N2_N3++;
								 }
								 if ((Winning_action==4)&&(B==2))
								 {
									 N2_R++;
								 }
								 if ((Winning_action==0)&&(B==3))
								 {
									 N3_W++;
								 }
								 if ((Winning_action==1)&&(B==3))
								 {
									 N3_N1++;
								 }
								 if ((Winning_action==2)&&(B==3))
								 {
									 N3_N2++;
								 }
								 if ((Winning_action==4)&&(B==3))
								 {
									 N3_R++;
								 }
								 if ((Winning_action==0)&&(B==4))
								 {
									 R_W++;
								 }
								 if ((Winning_action==1)&&(B==4))
								 {
									 R_N1++;
								 }
								 if ((Winning_action==2)&&(B==4))
								 {
									 R_N2++;
								 }
								 if ((Winning_action==3)&&(B==4))
								 {
									 R_N3++;
								 }
							 }

						}
						//le nombre d'agents qui ont proposé l'action 0 est égale aux nombre d'agents qui ont choisi l'action 2	 
						if((nb0==2)&&(nb2==2)) {
							if(ok==true) {
							if(last_Winning_action==0) {
								 Winning_action=0;
							 }
							else if(last_Winning_action==2) {
								 Winning_action=2;
							 }
							else {
								int [] tab = new int [3];
								 tab[0]=last_Winning_action;
								 tab[1]=2;
								 tab[2]=0;
								
								 
								 int n = tab.length;
								 int randomNumber = (int) Math.random()*n ;
								 int nombreAleaDeTonTableau = tab[randomNumber];
								 Winning_action= nombreAleaDeTonTableau;
							}} 
							else if(ok==false) {
								int [] tab = new int [2];
								 tab[0]=0;
								 tab[1]=2;

								 int n = tab.length;
								 int randomNumber = (int) Math.random()*n ;
								 int nombreAleaDeTonTableau = tab[randomNumber];
								 Winning_action= nombreAleaDeTonTableau;
							}
							
							 int B=Integer.parseInt((String) TT[NumState]);
							 if(B==Winning_action) {
								 nbf++;
								 if(Winning_action==0) {
									 correct0++;
								 }
								 if(Winning_action==1) {
									 correct1++;
								 }
								 if(Winning_action==2) {
									 correct2++;
								 }
								 if(Winning_action==3) {
									 correct3++;
								 }
								 if(Winning_action==4) {
									 correct4++;
								 }
							 }else {
								 if ((Winning_action==1)&&(B==0))
								 {
									 W_N1++;
								 }
								 if ((Winning_action==2)&&(B==0))
								 {
									 W_N2++;
								 }
								 if ((Winning_action==3)&&(B==0))
								 {
									 W_N3++;
								 }
								 if ((Winning_action==4)&&(B==0))
								 {
									 W_R++;
								 }
								 if ((Winning_action==0)&&(B==1))
								 {
									 N1_W++;
								 }
								 if ((Winning_action==2)&&(B==1))
								 {
									 N1_N2++;
								 }
								 if ((Winning_action==3)&&(B==1))
								 {
									 N1_N3++;
								 }
								 if ((Winning_action==4)&&(B==1))
								 {
									 N1_R++;
								 }
								 if ((Winning_action==0)&&(B==2))
								 {
									 N2_W++;
								 }
								 if ((Winning_action==1)&&(B==2))
								 {
									 N2_N1++;
								 }
								 if ((Winning_action==3)&&(B==2))
								 {
									 N2_N3++;
								 }
								 if ((Winning_action==4)&&(B==2))
								 {
									 N2_R++;
								 }
								 if ((Winning_action==0)&&(B==3))
								 {
									 N3_W++;
								 }
								 if ((Winning_action==1)&&(B==3))
								 {
									 N3_N1++;
								 }
								 if ((Winning_action==2)&&(B==3))
								 {
									 N3_N2++;
								 }
								 if ((Winning_action==4)&&(B==3))
								 {
									 N3_R++;
								 }
								 if ((Winning_action==0)&&(B==4))
								 {
									 R_W++;
								 }
								 if ((Winning_action==1)&&(B==4))
								 {
									 R_N1++;
								 }
								 if ((Winning_action==2)&&(B==4))
								 {
									 R_N2++;
								 }
								 if ((Winning_action==3)&&(B==4))
								 {
									 R_N3++;
								 }
							 }
							

						}

						// le nombre d'agents qui ont proposé l'action 0 est égale aux nombre d'agents qui ont choisi l'action 3	 
						if((nb0==2)&&(nb3==2)) {
							if(ok==true) {
							if(last_Winning_action==0) {
								 Winning_action=0;
							 }
							else if(last_Winning_action==3) {
								 Winning_action=3;
							 }else {
								 int [] tab = new int [3];
								 tab[0]=last_Winning_action;
								 tab[1]=0;
								 tab[2]=3;
								
								 
								 int n = tab.length;
								 int randomNumber = (int) Math.random()*n ;
								 int nombreAleaDeTonTableau = tab[randomNumber];
								 Winning_action= nombreAleaDeTonTableau;
							 }}
							else if(ok==false) {
								int [] tab = new int [2];
								 tab[0]=0;
								 tab[1]=3;

								 int n = tab.length;
								 int randomNumber = (int) Math.random()*n ;
								 int nombreAleaDeTonTableau = tab[randomNumber];
								 Winning_action= nombreAleaDeTonTableau;
							}
					
							 int B=Integer.parseInt((String) TT[NumState]);
							 if(B==Winning_action) {
								 nbf++;
								 if(Winning_action==0) {
									 correct0++;
								 }
								 if(Winning_action==1) {
									 correct1++;
								 }
								 if(Winning_action==2) {
									 correct2++;
								 }
								 if(Winning_action==3) {
									 correct3++;
								 }
								 if(Winning_action==4) {
									 correct4++;
								 }
							 }else {
								 if ((Winning_action==1)&&(B==0))
								 {
									 W_N1++;
								 }
								 if ((Winning_action==2)&&(B==0))
								 {
									 W_N2++;
								 }
								 if ((Winning_action==3)&&(B==0))
								 {
									 W_N3++;
								 }
								 if ((Winning_action==4)&&(B==0))
								 {
									 W_R++;
								 }
								 if ((Winning_action==0)&&(B==1))
								 {
									 N1_W++;
								 }
								 if ((Winning_action==2)&&(B==1))
								 {
									 N1_N2++;
								 }
								 if ((Winning_action==3)&&(B==1))
								 {
									 N1_N3++;
								 }
								 if ((Winning_action==4)&&(B==1))
								 {
									 N1_R++;
								 }
								 if ((Winning_action==0)&&(B==2))
								 {
									 N2_W++;
								 }
								 if ((Winning_action==1)&&(B==2))
								 {
									 N2_N1++;
								 }
								 if ((Winning_action==3)&&(B==2))
								 {
									 N2_N3++;
								 }
								 if ((Winning_action==4)&&(B==2))
								 {
									 N2_R++;
								 }
								 if ((Winning_action==0)&&(B==3))
								 {
									 N3_W++;
								 }
								 if ((Winning_action==1)&&(B==3))
								 {
									 N3_N1++;
								 }
								 if ((Winning_action==2)&&(B==3))
								 {
									 N3_N2++;
								 }
								 if ((Winning_action==4)&&(B==3))
								 {
									 N3_R++;
								 }
								 if ((Winning_action==0)&&(B==4))
								 {
									 R_W++;
								 }
								 if ((Winning_action==1)&&(B==4))
								 {
									 R_N1++;
								 }
								 if ((Winning_action==2)&&(B==4))
								 {
									 R_N2++;
								 }
								 if ((Winning_action==3)&&(B==4))
								 {
									 R_N3++;
								 }
							 }
						}

						// le nombre d'agents qui ont proposé l'action 0 est égale aux nombre d'agents qui ont choisi l'action 4	 
						if((nb0==2)&&(nb4==2)) {
							if(ok==true) {
							if(last_Winning_action==0) {
							 Winning_action=0;
						 }
						else if(last_Winning_action==4) {
							 Winning_action=4;
						 }else {
							 int [] tab = new int [3];
							 tab[0]=last_Winning_action;
							 tab[1]=0;
							 tab[2]=4;
							
							 
							 int n = tab.length;
							 int randomNumber = (int) Math.random()*n ;
							 int nombreAleaDeTonTableau = tab[randomNumber];
							 Winning_action= nombreAleaDeTonTableau;
							 
						 }} else if(ok==false){
							 int [] tab = new int [2];
							 tab[0]=0;
							 tab[1]=4;

						     int n = tab.length;
							 int randomNumber = (int) Math.random()*n ;
							 int nombreAleaDeTonTableau = tab[randomNumber];
							 Winning_action= nombreAleaDeTonTableau;
						 }
							
							 int B=Integer.parseInt((String) TT[NumState]);
							 if(B==Winning_action) {
								 nbf++;
								 if(Winning_action==0) {
									 correct0++;
								 }
								 if(Winning_action==1) {
									 correct1++;
								 }
								 if(Winning_action==2) {
									 correct2++;
								 }
								 if(Winning_action==3) {
									 correct3++;
								 }
								 if(Winning_action==4) {
									 correct4++;
								 }
							 }else {
								 if ((Winning_action==1)&&(B==0))
								 {
									 W_N1++;
								 }
								 if ((Winning_action==2)&&(B==0))
								 {
									 W_N2++;
								 }
								 if ((Winning_action==3)&&(B==0))
								 {
									 W_N3++;
								 }
								 if ((Winning_action==4)&&(B==0))
								 {
									 W_R++;
								 }
								 if ((Winning_action==0)&&(B==1))
								 {
									 N1_W++;
								 }
								 if ((Winning_action==2)&&(B==1))
								 {
									 N1_N2++;
								 }
								 if ((Winning_action==3)&&(B==1))
								 {
									 N1_N3++;
								 }
								 if ((Winning_action==4)&&(B==1))
								 {
									 N1_R++;
								 }
								 if ((Winning_action==0)&&(B==2))
								 {
									 N2_W++;
								 }
								 if ((Winning_action==1)&&(B==2))
								 {
									 N2_N1++;
								 }
								 if ((Winning_action==3)&&(B==2))
								 {
									 N2_N3++;
								 }
								 if ((Winning_action==4)&&(B==2))
								 {
									 N2_R++;
								 }
								 if ((Winning_action==0)&&(B==3))
								 {
									 N3_W++;
								 }
								 if ((Winning_action==1)&&(B==3))
								 {
									 N3_N1++;
								 }
								 if ((Winning_action==2)&&(B==3))
								 {
									 N3_N2++;
								 }
								 if ((Winning_action==4)&&(B==3))
								 {
									 N3_R++;
								 }
								 if ((Winning_action==0)&&(B==4))
								 {
									 R_W++;
								 }
								 if ((Winning_action==1)&&(B==4))
								 {
									 R_N1++;
								 }
								 if ((Winning_action==2)&&(B==4))
								 {
									 R_N2++;
								 }
								 if ((Winning_action==3)&&(B==4))
								 {
									 R_N3++;
								 }
							 }
							 
						}

							// le nombre d'agents qui ont proposé l'action 1 est égale aux nombre d'agents qui ont choisi l'action 2	 
						if((nb1==2)&&(nb2==2)) {
							if(ok==true) {
							if(last_Winning_action==1) {
								 Winning_action=1;
							 }
							else if(last_Winning_action==2) {
								 Winning_action=2;
							 }
							else {
								int [] tab = new int [3];
								 tab[0]=last_Winning_action;
								 tab[1]=1;
								 tab[2]=2;
								
								 
								 int n = tab.length;
								 int randomNumber = (int) Math.random()*n ;
								 int nombreAleaDeTonTableau = tab[randomNumber];
								 Winning_action= nombreAleaDeTonTableau;
							}} 
							else if(ok==false) {
								int [] tab = new int [2];
								 tab[0]=1;
								 tab[1]=2;

								 int n = tab.length;
								 int randomNumber = (int) Math.random()*n ;
								 int nombreAleaDeTonTableau = tab[randomNumber];
								 Winning_action= nombreAleaDeTonTableau;
								//Winning_action=1;
							}
							
							 int B=Integer.parseInt((String) TT[NumState]);
							 if(B==Winning_action) {
								 nbf++;
								 if(Winning_action==0) {
									 correct0++;
								 }
								 if(Winning_action==1) {
									 correct1++;
								 }
								 if(Winning_action==2) {
									 correct2++;
								 }
								 if(Winning_action==3) {
									 correct3++;
								 }
								 if(Winning_action==4) {
									 correct4++;
								 }
							 }else {
								 if ((Winning_action==1)&&(B==0))
								 {
									 W_N1++;
								 }
								 if ((Winning_action==2)&&(B==0))
								 {
									 W_N2++;
								 }
								 if ((Winning_action==3)&&(B==0))
								 {
									 W_N3++;
								 }
								 if ((Winning_action==4)&&(B==0))
								 {
									 W_R++;
								 }
								 if ((Winning_action==0)&&(B==1))
								 {
									 N1_W++;
								 }
								 if ((Winning_action==2)&&(B==1))
								 {
									 N1_N2++;
								 }
								 if ((Winning_action==3)&&(B==1))
								 {
									 N1_N3++;
								 }
								 if ((Winning_action==4)&&(B==1))
								 {
									 N1_R++;
								 }
								 if ((Winning_action==0)&&(B==2))
								 {
									 N2_W++;
								 }
								 if ((Winning_action==1)&&(B==2))
								 {
									 N2_N1++;
								 }
								 if ((Winning_action==3)&&(B==2))
								 {
									 N2_N3++;
								 }
								 if ((Winning_action==4)&&(B==2))
								 {
									 N2_R++;
								 }
								 if ((Winning_action==0)&&(B==3))
								 {
									 N3_W++;
								 }
								 if ((Winning_action==1)&&(B==3))
								 {
									 N3_N1++;
								 }
								 if ((Winning_action==2)&&(B==3))
								 {
									 N3_N2++;
								 }
								 if ((Winning_action==4)&&(B==3))
								 {
									 N3_R++;
								 }
								 if ((Winning_action==0)&&(B==4))
								 {
									 R_W++;
								 }
								 if ((Winning_action==1)&&(B==4))
								 {
									 R_N1++;
								 }
								 if ((Winning_action==2)&&(B==4))
								 {
									 R_N2++;
								 }
								 if ((Winning_action==3)&&(B==4))
								 {
									 R_N3++;
								 }
							 }
						}

						// le nombre d'agents qui ont proposé l'action 1 est égale aux nombre d'agents qui ont choisi l'action 3	 
						if((nb1==2)&&(nb3==2)) {
							 if(ok==true) {
							if(last_Winning_action==1) {
								 Winning_action=1;
							 }
							else if(last_Winning_action==3) {
								 Winning_action=3;
							 }else {
								 int [] tab = new int [3];
								 tab[0]=last_Winning_action;
								 tab[1]=1;
								 tab[2]=3;
								
								 
								 int n = tab.length;
								 int randomNumber = (int) Math.random()*n ;
								 int nombreAleaDeTonTableau = tab[randomNumber];
								 Winning_action= nombreAleaDeTonTableau;
							 }}
							 else if(ok==false) {
								 int [] tab = new int [2];
								 tab[0]=1;
								 tab[1]=3;

								int n = tab.length;
								 int randomNumber = (int) Math.random()*n ;
								 int nombreAleaDeTonTableau = tab[randomNumber];
								 Winning_action= nombreAleaDeTonTableau;
								 //Winning_action=1;
							 }
							
							 int B=Integer.parseInt((String) TT[NumState]);
							 if(B==Winning_action) {
								 nbf++;
								 if(Winning_action==0) {
									 correct0++;
								 }
								 if(Winning_action==1) {
									 correct1++;
								 }
								 if(Winning_action==2) {
									 correct2++;
								 }
								 if(Winning_action==3) {
									 correct3++;
								 }
								 if(Winning_action==4) {
									 correct4++;
								 }
							 }else {
								 if ((Winning_action==1)&&(B==0))
								 {
									 W_N1++;
								 }
								 if ((Winning_action==2)&&(B==0))
								 {
									 W_N2++;
								 }
								 if ((Winning_action==3)&&(B==0))
								 {
									 W_N3++;
								 }
								 if ((Winning_action==4)&&(B==0))
								 {
									 W_R++;
								 }
								 if ((Winning_action==0)&&(B==1))
								 {
									 N1_W++;
								 }
								 if ((Winning_action==2)&&(B==1))
								 {
									 N1_N2++;
								 }
								 if ((Winning_action==3)&&(B==1))
								 {
									 N1_N3++;
								 }
								 if ((Winning_action==4)&&(B==1))
								 {
									 N1_R++;
								 }
								 if ((Winning_action==0)&&(B==2))
								 {
									 N2_W++;
								 }
								 if ((Winning_action==1)&&(B==2))
								 {
									 N2_N1++;
								 }
								 if ((Winning_action==3)&&(B==2))
								 {
									 N2_N3++;
								 }
								 if ((Winning_action==4)&&(B==2))
								 {
									 N2_R++;
								 }
								 if ((Winning_action==0)&&(B==3))
								 {
									 N3_W++;
								 }
								 if ((Winning_action==1)&&(B==3))
								 {
									 N3_N1++;
								 }
								 if ((Winning_action==2)&&(B==3))
								 {
									 N3_N2++;
								 }
								 if ((Winning_action==4)&&(B==3))
								 {
									 N3_R++;
								 }
								 if ((Winning_action==0)&&(B==4))
								 {
									 R_W++;
								 }
								 if ((Winning_action==1)&&(B==4))
								 {
									 R_N1++;
								 }
								 if ((Winning_action==2)&&(B==4))
								 {
									 R_N2++;
								 }
								 if ((Winning_action==3)&&(B==4))
								 {
									 R_N3++;
								 }
							 }
						}



							// le nombre d'agents qui ont proposé l'action 1 est égale aux nombre d'agents qui ont choisi l'action 4	 
							 if((nb1==2)&&(nb4==2)) {
								 
								 if(ok==true) {
								 if(last_Winning_action==1) {
									 Winning_action=1;
								 }
								else if(last_Winning_action==4) {
									 Winning_action=4;
								 }
								else {
									int [] tab = new int [3];
									 tab[0]=last_Winning_action;
									 tab[1]=1;
									 tab[2]=4;
									
									 
									 int n = tab.length;
									 int randomNumber = (int) Math.random()*n ;
									 int nombreAleaDeTonTableau = tab[randomNumber];
									 Winning_action= nombreAleaDeTonTableau;
								}}
								 else if(ok==false) {
									 int [] tab = new int [2];
									 tab[0]=1;
									 tab[1]=4;

									 int n = tab.length;
									 int randomNumber = (int) Math.random()*n ;
									 int nombreAleaDeTonTableau = tab[randomNumber];
									 Winning_action= nombreAleaDeTonTableau;
									 //Winning_action=1;
								 }
								 
								 int B=Integer.parseInt((String) TT[NumState]);
								 if(B==Winning_action) {
									 nbf++;
									 if(Winning_action==0) {
										 correct0++;
									 }
									 if(Winning_action==1) {
										 correct1++;
									 }
									 if(Winning_action==2) {
										 correct2++;
									 }
									 if(Winning_action==3) {
										 correct3++;
									 }
									 if(Winning_action==4) {
										 correct4++;
									 }
								 }else {
									 if ((Winning_action==1)&&(B==0))
									 {
										 W_N1++;
									 }
									 if ((Winning_action==2)&&(B==0))
									 {
										 W_N2++;
									 }
									 if ((Winning_action==3)&&(B==0))
									 {
										 W_N3++;
									 }
									 if ((Winning_action==4)&&(B==0))
									 {
										 W_R++;
									 }
									 if ((Winning_action==0)&&(B==1))
									 {
										 N1_W++;
									 }
									 if ((Winning_action==2)&&(B==1))
									 {
										 N1_N2++;
									 }
									 if ((Winning_action==3)&&(B==1))
									 {
										 N1_N3++;
									 }
									 if ((Winning_action==4)&&(B==1))
									 {
										 N1_R++;
									 }
									 if ((Winning_action==0)&&(B==2))
									 {
										 N2_W++;
									 }
									 if ((Winning_action==1)&&(B==2))
									 {
										 N2_N1++;
									 }
									 if ((Winning_action==3)&&(B==2))
									 {
										 N2_N3++;
									 }
									 if ((Winning_action==4)&&(B==2))
									 {
										 N2_R++;
									 }
									 if ((Winning_action==0)&&(B==3))
									 {
										 N3_W++;
									 }
									 if ((Winning_action==1)&&(B==3))
									 {
										 N3_N1++;
									 }
									 if ((Winning_action==2)&&(B==3))
									 {
										 N3_N2++;
									 }
									 if ((Winning_action==4)&&(B==3))
									 {
										 N3_R++;
									 }
									 if ((Winning_action==0)&&(B==4))
									 {
										 R_W++;
									 }
									 if ((Winning_action==1)&&(B==4))
									 {
										 R_N1++;
									 }
									 if ((Winning_action==2)&&(B==4))
									 {
										 R_N2++;
									 }
									 if ((Winning_action==3)&&(B==4))
									 {
										 R_N3++;
									 }
								 }
							 }

							 
								// le nombre d'agents qui ont proposé l'action 2 est égale aux nombre d'agents qui ont choisi l'action 3	 
								 if((nb2==2)&&(nb3==2)) {
									 if(ok==true) {
									 if(last_Winning_action==2) {
										 Winning_action=2;
									 }
									else if(last_Winning_action==3) {
										 Winning_action=3;
									 }
									else {
										int [] tab = new int [3];
										 tab[0]=last_Winning_action;
										 tab[1]=2;
										 tab[2]=3;
										
										 
										 int n = tab.length;
										 int randomNumber = (int) Math.random()*n ;
										 int nombreAleaDeTonTableau = tab[randomNumber];
										 Winning_action= nombreAleaDeTonTableau;
									}}
									 else if(ok==false) {
										/* int [] tab = new int [2];
										 tab[0]=2;
										 tab[1]=3;

										 int n = tab.length;
										 int randomNumber = (int) Math.random()*n ;
										 int nombreAleaDeTonTableau = tab[randomNumber];
										 Winning_action= nombreAleaDeTonTableau;*/
										 Winning_action=1;
									 }
									 
									 int B=Integer.parseInt((String) TT[NumState]);
									 if(B==Winning_action) {
										 nbf++;
										 if(Winning_action==0) {
											 correct0++;
										 }
										 if(Winning_action==1) {
											 correct1++;
										 }
										 if(Winning_action==2) {
											 correct2++;
										 }
										 if(Winning_action==3) {
											 correct3++;
										 }
										 if(Winning_action==4) {
											 correct4++;
										 }
									 }else {
										 if ((Winning_action==1)&&(B==0))
										 {
											 W_N1++;
										 }
										 if ((Winning_action==2)&&(B==0))
										 {
											 W_N2++;
										 }
										 if ((Winning_action==3)&&(B==0))
										 {
											 W_N3++;
										 }
										 if ((Winning_action==4)&&(B==0))
										 {
											 W_R++;
										 }
										 if ((Winning_action==0)&&(B==1))
										 {
											 N1_W++;
										 }
										 if ((Winning_action==2)&&(B==1))
										 {
											 N1_N2++;
										 }
										 if ((Winning_action==3)&&(B==1))
										 {
											 N1_N3++;
										 }
										 if ((Winning_action==4)&&(B==1))
										 {
											 N1_R++;
										 }
										 if ((Winning_action==0)&&(B==2))
										 {
											 N2_W++;
										 }
										 if ((Winning_action==1)&&(B==2))
										 {
											 N2_N1++;
										 }
										 if ((Winning_action==3)&&(B==2))
										 {
											 N2_N3++;
										 }
										 if ((Winning_action==4)&&(B==2))
										 {
											 N2_R++;
										 }
										 if ((Winning_action==0)&&(B==3))
										 {
											 N3_W++;
										 }
										 if ((Winning_action==1)&&(B==3))
										 {
											 N3_N1++;
										 }
										 if ((Winning_action==2)&&(B==3))
										 {
											 N3_N2++;
										 }
										 if ((Winning_action==4)&&(B==3))
										 {
											 N3_R++;
										 }
										 if ((Winning_action==0)&&(B==4))
										 {
											 R_W++;
										 }
										 if ((Winning_action==1)&&(B==4))
										 {
											 R_N1++;
										 }
										 if ((Winning_action==2)&&(B==4))
										 {
											 R_N2++;
										 }
										 if ((Winning_action==3)&&(B==4))
										 {
											 R_N3++;
										 }
									 }
								 }
									// le nombre d'agents qui ont proposé l'action 2 est égale aux nombre d'agents qui ont choisi l'action 4	 
								 if((nb2==2)&&(nb4==2)) {
									 
									 if(ok==true) {
									 if(last_Winning_action==2) {
										 Winning_action=2;
									 }
									else if(last_Winning_action==4) {
										 Winning_action=4;
									 }else {
										
										 int [] tab = new int [3];
										 tab[0]=last_Winning_action;
										 tab[1]=2;
										 tab[2]=4;
										
										 
										 int n = tab.length;
										 int randomNumber = (int) Math.random()*n ;
										 int nombreAleaDeTonTableau = tab[randomNumber];
										 Winning_action= nombreAleaDeTonTableau;
									 }}
									 else if(ok==false) {
										 int [] tab = new int [2];
										 tab[0]=2;
										 tab[1]=4;

										int n = tab.length;
										 int randomNumber = (int) Math.random()*n ;
										 int nombreAleaDeTonTableau = tab[randomNumber];
										 Winning_action= nombreAleaDeTonTableau;
										 
									 }
									
									 int B=Integer.parseInt((String) TT[NumState]);
									 if(B==Winning_action) {
										 nbf++;
										 if(Winning_action==0) {
											 correct0++;
										 }
										 if(Winning_action==1) {
											 correct1++;
										 }
										 if(Winning_action==2) {
											 correct2++;
										 }
										 if(Winning_action==3) {
											 correct3++;
										 }
										 if(Winning_action==4) {
											 correct4++;
										 }
									 }else {
										 if ((Winning_action==1)&&(B==0))
										 {
											 W_N1++;
										 }
										 if ((Winning_action==2)&&(B==0))
										 {
											 W_N2++;
										 }
										 if ((Winning_action==3)&&(B==0))
										 {
											 W_N3++;
										 }
										 if ((Winning_action==4)&&(B==0))
										 {
											 W_R++;
										 }
										 if ((Winning_action==0)&&(B==1))
										 {
											 N1_W++;
										 }
										 if ((Winning_action==2)&&(B==1))
										 {
											 N1_N2++;
										 }
										 if ((Winning_action==3)&&(B==1))
										 {
											 N1_N3++;
										 }
										 if ((Winning_action==4)&&(B==1))
										 {
											 N1_R++;
										 }
										 if ((Winning_action==0)&&(B==2))
										 {
											 N2_W++;
										 }
										 if ((Winning_action==1)&&(B==2))
										 {
											 N2_N1++;
										 }
										 if ((Winning_action==3)&&(B==2))
										 {
											 N2_N3++;
										 }
										 if ((Winning_action==4)&&(B==2))
										 {
											 N2_R++;
										 }
										 if ((Winning_action==0)&&(B==3))
										 {
											 N3_W++;
										 }
										 if ((Winning_action==1)&&(B==3))
										 {
											 N3_N1++;
										 }
										 if ((Winning_action==2)&&(B==3))
										 {
											 N3_N2++;
										 }
										 if ((Winning_action==4)&&(B==3))
										 {
											 N3_R++;
										 }
										 if ((Winning_action==0)&&(B==4))
										 {
											 R_W++;
										 }
										 if ((Winning_action==1)&&(B==4))
										 {
											 R_N1++;
										 }
										 if ((Winning_action==2)&&(B==4))
										 {
											 R_N2++;
										 }
										 if ((Winning_action==3)&&(B==4))
										 {
											 R_N3++;
										 }
									 }
								 }
								 
									// le nombre d'agents qui ont proposé l'action 3 est égale aux nombre d'agents qui ont choisi l'action 4	 
								 if((nb3==2)&&(nb4==2)) {
									 if(ok==true) {
									
									
									 if(last_Winning_action==3) {
										 Winning_action=3;
									 }
									else if(last_Winning_action==4) {
										 Winning_action=4;
									 }else {
										 int [] tab = new int [3];
										 tab[0]=last_Winning_action;
										 tab[1]=3;
										 tab[2]=4;
										
										 
										 int n = tab.length;
										 int randomNumber = (int) Math.random()*n ;
										 int nombreAleaDeTonTableau = tab[randomNumber];
										 Winning_action= nombreAleaDeTonTableau;
									 }}
									 else if(ok==false) {
										 int [] tab = new int [2];
										 tab[0]=3;
										 tab[1]=4;

										 int n = tab.length;
										 int randomNumber = (int) Math.random()*n ;
										 int nombreAleaDeTonTableau = tab[randomNumber];
										 Winning_action= nombreAleaDeTonTableau;
									 }
								
									 
									 int B=Integer.parseInt((String) TT[NumState]);
									 if(B==Winning_action) {
										 nbf++;
										 if(Winning_action==0) {
											 correct0++;
										 }
										 if(Winning_action==1) {
											 correct1++;
										 }
										 if(Winning_action==2) {
											 correct2++;
										 }
										 if(Winning_action==3) {
											 correct3++;
										 }
										 if(Winning_action==4) {
											 correct4++;
										 }
									 }else {
										 if ((Winning_action==1)&&(B==0))
										 {
											 W_N1++;
										 }
										 if ((Winning_action==2)&&(B==0))
										 {
											 W_N2++;
										 }
										 if ((Winning_action==3)&&(B==0))
										 {
											 W_N3++;
										 }
										 if ((Winning_action==4)&&(B==0))
										 {
											 W_R++;
										 }
										 if ((Winning_action==0)&&(B==1))
										 {
											 N1_W++;
										 }
										 if ((Winning_action==2)&&(B==1))
										 {
											 N1_N2++;
										 }
										 if ((Winning_action==3)&&(B==1))
										 {
											 N1_N3++;
										 }
										 if ((Winning_action==4)&&(B==1))
										 {
											 N1_R++;
										 }
										 if ((Winning_action==0)&&(B==2))
										 {
											 N2_W++;
										 }
										 if ((Winning_action==1)&&(B==2))
										 {
											 N2_N1++;
										 }
										 if ((Winning_action==3)&&(B==2))
										 {
											 N2_N3++;
										 }
										 if ((Winning_action==4)&&(B==2))
										 {
											 N2_R++;
										 }
										 if ((Winning_action==0)&&(B==3))
										 {
											 N3_W++;
										 }
										 if ((Winning_action==1)&&(B==3))
										 {
											 N3_N1++;
										 }
										 if ((Winning_action==2)&&(B==3))
										 {
											 N3_N2++;
										 }
										 if ((Winning_action==4)&&(B==3))
										 {
											 N3_R++;
										 }
										 if ((Winning_action==0)&&(B==4))
										 {
											 R_W++;
										 }
										 if ((Winning_action==1)&&(B==4))
										 {
											 R_N1++;
										 }
										 if ((Winning_action==2)&&(B==4))
										 {
											 R_N2++;
										 }
										 if ((Winning_action==3)&&(B==4))
										 {
											 R_N3++;
										 }
									 }
								
								 }
								 
								 Matrice_Confusion[0][0]=0;
								 Matrice_Confusion[0][1]=0;
								 Matrice_Confusion[0][2]=1;
								 Matrice_Confusion[0][3]=2;
								 Matrice_Confusion[0][4]=3;
								 Matrice_Confusion[0][5]=4;
								 
								 
								 Matrice_Confusion[1][0]=0;
								 Matrice_Confusion[2][0]=1;
								 Matrice_Confusion[3][0]=2;
								 Matrice_Confusion[4][0]=3;
								 Matrice_Confusion[5][0]=4;
								 
								 
								 Matrice_Confusion[1][1]=correct0;
								 Matrice_Confusion[2][2]=correct1;
								 Matrice_Confusion[3][3]=correct2;
								 Matrice_Confusion[4][4]=correct3;
								 Matrice_Confusion[5][5]=correct4;
								 
								 Matrice_Confusion[1][2]=W_N1;
								 Matrice_Confusion[1][3]=W_N2;
								 Matrice_Confusion[1][4]=W_N3;
								 Matrice_Confusion[1][5]=W_R;
								 
								 Matrice_Confusion[2][1]=N1_W;
								 Matrice_Confusion[2][3]=N1_N2;
								 Matrice_Confusion[2][4]=N1_N3;
								 Matrice_Confusion[2][5]=N1_R;
								 
								 Matrice_Confusion[3][1]=N2_W;
								 Matrice_Confusion[3][2]=N2_N1;
								 Matrice_Confusion[3][4]=N2_N3;
								 Matrice_Confusion[3][5]=N2_R;
								
								 Matrice_Confusion[4][1]=N3_W;
								 Matrice_Confusion[4][2]=N3_N1;
								 Matrice_Confusion[4][3]=N3_N2;
								 Matrice_Confusion[4][5]=N3_R;
								 
								 Matrice_Confusion[5][1]=R_W;
								 Matrice_Confusion[5][2]=R_N1;
								 Matrice_Confusion[5][3]=R_N2;
								 Matrice_Confusion[5][4]=R_N3;
								
								 last_Winning_action=Winning_action;
								 int compar=Integer.parseInt((String) TT[NumState]);
								 if(compar==last_Winning_action) {
									 ok=true;
								 } else {
									 ok=false;
								 }
								 
								
							        
								 System.out.println("LAST WINNING ACTION:"+last_Winning_action);
								 
/**********************************ENVOI du WINNING ACTION A TOUS LES AGENTS********************************/

								 
								 try {
									Acti=Winning_action;
									tableau_final[NumState]=Acti;
									
									//System.out.println("|"+tableau_final[NumState]);
									
									reply.setContentObject((Serializable) Acti);
									reply.addReceiver(new AID("Ag30", AID.ISLOCALNAME));
									reply.addReceiver(new AID("Ag10_3", AID.ISLOCALNAME));									
									reply.addReceiver(new AID("Ag10_1", AID.ISLOCALNAME));
									reply.addReceiver(new AID("Ag10_2", AID.ISLOCALNAME));
									reply.addReceiver(new AID("bd30", AID.ISLOCALNAME));
									reply.addReceiver(new AID("bd10_1", AID.ISLOCALNAME));
									reply.addReceiver(new AID("bd10_2", AID.ISLOCALNAME));
									reply.addReceiver(new AID("bd10_3", AID.ISLOCALNAME));
									myAgent.send(reply);
									//System.out.print("REPLY:"+reply);
									
									//System.out.println("envoi du reply.....");
									//System.out.println("\n");

									
									
									
									
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								 
								 
					            		
					            		
					          		
					            		
					            		
					            		
					            		
					            		
					            		
					            		
					            		
					            		
					            		
					            		
					            		
					            		
					            		
					            		
					            		
					            		
					            		
					            		
					            		
					            		
					            		
					            		
					            		
					            		
					            		
					            	}
									
					            	
					}
				
					
				    }	
				       // j++;
				        
				        
			         
							
							
							System.out.println("NBF!!!!!!!!!!!!!!"+nbf); 
							PCC= (nbf*100)/2650;
							System.out.println("PCC final:"+PCC);
							System.out.println("numstate"+NumState);
							System.out.println("CORRECT_W..."+correct0);
							System.out.println("CORRECT_N1..."+correct1);
							System.out.println("CORRECT_N2..."+correct2);
							System.out.println("CORRECT_N3..."+correct3);
							System.out.println("CORRECT_R..."+correct4);
							System.out.println("\n");
							System.out.println("CLASSIFICATION FINALE:");
							System.out.println("\n");
							for(int y=0;y<2651;y++) {
								System.out.print("|"+tableau_final[y]);
							}
							System.out.println("\n");
							
							System.out.println("\n");
							System.out.println("MATRICE DE CONFUSION:");
							System.out.println("\n");
							for(int i=0;i<6;i++) {
								for(int j=0;j<6;j++) {
									System.out.print("|"+Matrice_Confusion[i][j]);
								}
								System.out.println("\n");
							}
							System.out.println("\n");

						
					  
					      
				}
							
				});
			
		
			
			
	}
	
	
	
	protected void takeDown() {
		// Suppression de l'agent [Acheteur] depuis le DF
		try {
			DFService.deregister(this);
			System.out.println(getLocalName()+" DEREGISTERED WITH THE DF");
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}
	
	
	/************************************fonction recherche****************************************/
	
	private static int recherche(String ch, String[]B)
		
		{ 
		 boolean trouve=false;
		 int i = 0;
		   do {
		      if (B[i].equalsIgnoreCase(ch)) {
		        
		        trouve=true;
		      }
		      else {
		        i++;
		      }
		    } while (i < B.length && trouve==false);
			
			
			
			if(trouve==false)
			{
				return -1;}
			else 
			return i ;
			
		}
	

}
