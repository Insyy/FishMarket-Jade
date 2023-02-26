package fishmarket.agents.broker;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import fishmarket.auction.AuctionInstance;

public class BrokerGUI extends JPanel {

    private JTable table;
    private JFrame frame;

    private final String title = "Broker's view of the market";

    public BrokerGUI() {
        super();
        table = new JTable(new BrokerTableModel());

        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                BrokerTableModel brokerTableModel = (BrokerTableModel) table.getModel();
                processRow(table, row, brokerTableModel);
                return this;
            }

            private void processRow(JTable table, int row, BrokerTableModel brokerTableModel) {
                if (!brokerTableModel.isRowActive(row)) {
                    setBackground(brokerTableModel.ROW_INACTIVE_COLOR);
                    setForeground(Color.WHITE);
                } else {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }
            }
        });

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

    public void recreateGUI(List<AuctionInstance> auctions) {
        ((BrokerTableModel) table.getModel()).refreshTableData(auctions);
    }

    private static class BrokerTableModel extends DefaultTableModel {

        private final static String columnNames[] = { "Vendeur", "Nom du lot", "Montant courant de l'enchere" };
        private List<Boolean> rowStates;

        private final Color ROW_INACTIVE_COLOR = Color.RED;

        BrokerTableModel() {
            super(new Object[][] {},
                    columnNames);
        }

        public boolean isRowActive(int row) {
            boolean b = true;
            try {
                b = rowStates.get(row);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            return b;
        }

        public void refreshTableData(List<AuctionInstance> auctions) {
            getDataVector().removeAllElements();
            rowStates = new ArrayList<>();

            for (int i = 0; i < auctions.size(); i++) {
                AuctionInstance auction = auctions.get(i);

                addRow(auction.getItem().toStringArrayGUI(auction.getSeller().getName()));
                rowStates.add(auction.isActive());
            }
            fireTableDataChanged();
        }
    }

}