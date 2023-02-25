package fishmarket.agents;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import jade.core.Agent;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

public class Broker extends Agent {

    BrokerTablePanel table;

    @Override
    protected void setup() {
        System.out.println("BROKER Agent " + getLocalName() + " ONLINE");

        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                table = new BrokerTablePanel();
            }
        });

        MessageTemplate template = MessageTemplate.and(
                MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
                MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

        addBehaviour(new AchieveREResponder(this, template) {
            @Override
            protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
                // TODO Auto-generated method stub
                return super.handleRequest(request);
            }
        });
    }

    public class BrokerTablePanel extends JPanel {

        private JTable table;
        private JFrame frame;
        private final String columnNames[] = { "Vendeur", "Nom du lot", "Montant courant de l'enchere", "Data" };
        private final String title = "Broker's view of the market";

        public BrokerTablePanel() {
            super();

            System.out.println("BrokerTablePane!!!!!!!!!!!!!!!!!!");

            // Create a new table with some sample data
            table = new JTable(new DefaultTableModel(
                    new Object[][] {},
                    columnNames));
            table.setFillsViewportHeight(true);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            // Add the table to a scroll pane
            JScrollPane scrollPane = new JScrollPane(table);

            // Add the scroll pane to the panel
            add(scrollPane);

            frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(this);
    
            frame.pack();
            frame.setVisible(true);
        }
    }
}
