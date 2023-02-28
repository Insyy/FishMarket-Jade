package fishmarket.performatifs;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

import jade.core.AID;
import jade.lang.acl.*;

public class MessageCreator {

	public static ACLMessage createMessageToBroker(AID receiver, Performatifs perf, Optional<Serializable> content) throws IOException{
		
		ACLMessage msg = new ACLMessage(perf.getJadeEquivalent());
		msg.addReceiver(receiver);
		msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));

		if (content.isPresent())
			msg.setContentObject(content.get());
		return msg;
	}
}
