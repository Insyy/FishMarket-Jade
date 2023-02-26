package fishmarket.agents.broker;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import fishmarket.auction.AuctionInstance;

public class BrokerGUI extends JPanel {

    private JTable table;
    private JFrame frame;
    private final String columnNames[] = { "Vendeur", "Nom du lot", "Montant courant de l'enchere", "Data" };
    private final String title = "Broker's view of the market";

    public BrokerGUI() {
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

    public void refreshTableData(List<AuctionInstance> auctions){
        System.out.println("Refreshing table");
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.getDataVector().removeAllElements();

        for (int i = 0; i < auctions.size(); i++) {
            AuctionInstance auction = auctions.get(i);
            System.out.println("Adding auction " + auction.toString());
            tableModel.addRow(auction.getItem().toStringArray());
        }

        tableModel.fireTableDataChanged();

    }
}