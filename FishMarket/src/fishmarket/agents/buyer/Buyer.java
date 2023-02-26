package fishmarket.agents.buyer;

import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import jade.domain.FIPANames;

import java.util.Date;

/**
 * This example shows how to implement the initiator role in
 * a FIPA-request interaction protocol. In this case in particular
 * we use an <code>AchieveREInitiator</code> ("Achieve Rational effect")
 * to request a dummy action to a number of agents (whose local
 * names must be specified as arguments).
 * 
 * @author Giovanni Caire - TILAB
 */
public class Buyer extends Agent {

	public static final String TAG = "BUYER AGENT | ";

	protected void setup() {
		// Read names of responders as arguments
		Object[] args = getArguments();
		if (args != null && args.length > 0) {

			System.out.println(TAG + "Name of broker agent is :" + args[0]);

			// Fill the REQUEST message
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			for (int i = 0; i < args.length; ++i) {
				msg.addReceiver(new AID((String) args[i], AID.ISLOCALNAME));
			}
			msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
			// We want to receive a reply in 10 secs
			msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
			msg.setContent("Display me please");

			addBehaviour(new AchieveREInitiator(this, msg) {
				protected void handleInform(ACLMessage inform) {
					System.out.println(
							TAG + inform.getSender().getName() + " successfully performed the requested action");
					System.out.println(TAG + "INFORM: " + inform.getContent());
				}

				protected void handleRefuse(ACLMessage refuse) {
					System.out.println(TAG + refuse.getSender().getName() + " refused to perform the requested action");
				}

				protected void handleFailure(ACLMessage failure) {
					if (failure.getSender().equals(myAgent.getAMS())) {
						// FAILURE notification from the JADE runtime: the receiver
						// does not exist
						System.out.println(TAG + "Responder does not exist");
					} else {
						System.out.println(
								TAG + failure.getSender().getName() + " failed to perform the requested action");
					}
				}

			});
		} else {
			System.out.println("INITIATOR No responder specified.");
		}
	}
}
