package fishmarket.agents;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;

/**
 * This example shows how to implement the responder role in
 * a FIPA-request interaction protocol. In this case in particular
 * we use an <code>AchieveREResponder</code> ("Achieve Rational effect")
 * to serve requests to perform actions from other agents. We use a
 * random generator to simulate request refusals and action execution
 * failures.
 * 
 * @author Giovanni Caire - TILAB
 */
public class Seller extends Agent {

	private int currentState = 0;

	int receptionCount = 0;

	protected void setup() {

		Object[] args = getArguments();

		if (args != null && args.length > 0) {

			System.out.println("Name of broker agent is :" + args[0]);

			System.out.println("SELLER Agent " + getLocalName() + " waiting for requests...");
			MessageTemplate template = MessageTemplate.and(
					MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
					MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

			addBehaviour(new AchieveREResponder(this, template) {

				@Override
				protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
					System.out.println("PARTICIPANT " + getLocalName() + ": REQUEST received from "
							+ request.getSender().getName());

					ACLMessage msg;

					switch (++receptionCount) {
						case 1:
							msg = request.createReply();
							msg.setPerformative(ACLMessage.REFUSE);
							return msg;
						case 2:
							// We agree to perform the action. Note that in the FIPA-Request
							// protocol the AGREE message is optional. Return null if you
							// don't want to send it.

							msg = request.createReply();
							msg.setPerformative(ACLMessage.AGREE);
							send(msg);

							msg = request.createReply();
							msg.setPerformative(ACLMessage.FAILURE);
							return msg;

						case 3:

							msg = request.createReply();
							msg.setPerformative(ACLMessage.AGREE);
							send(msg);

							msg = request.createReply();
							msg.setPerformative(ACLMessage.INFORM);
							System.out.println(request.getContent());
							return msg;
						case 4:
							msg = request.createReply();
							msg.setPerformative(ACLMessage.AGREE);
							send(msg);

							msg = request.createReply();
							msg.setPerformative(ACLMessage.INFORM);
							msg.setContent("message displayed!");

							return msg;
						default:
							return null;
					}
				}
			});
		}
	}
}
