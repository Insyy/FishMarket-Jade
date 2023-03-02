package fishmarket.agents.broker;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import fishmarket.auction.AuctionInstance;

public class BrokerGUI extends JTable {

    private JFrame frame;

    private final String title = "Broker's view of the market";

    public BrokerGUI() {
        super(new BrokerTableModel());

        configureInitialSettings();

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(this);

        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(scrollPane);

        frame.pack();
        frame.setVisible(true);
    }

    private void configureInitialSettings() {
        this.setFillsViewportHeight(true);
        this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                processRow(table, row, (BrokerTableModel) table.getModel());
                return this;
            }

            private void processRow(JTable table, int row, BrokerTableModel brokerTableModel) {
                try {
                    if (!brokerTableModel.isRowActive(row)) {
                        setBackground(brokerTableModel.ROW_INACTIVE_COLOR);
                        setForeground(Color.WHITE);
                    } else {
                        setBackground(this.getBackground());
                        setForeground(this.getForeground());
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                
            }
        });
    }

    public void recreateGUI(List<AuctionInstance> auctions) {
        ((BrokerTableModel) this.getModel()).refreshTableData(auctions);
    }

    private static class BrokerTableModel extends DefaultTableModel {

        private final static String columnNames[] = { "Vendeur", "Nom du lot", "Montant courant de l'enchere" };
        private List<Boolean> rowStates;

        private final Color ROW_INACTIVE_COLOR = Color.RED;

        BrokerTableModel() {
            super(new Object[][] {},
                    columnNames);
        }

        public boolean isRowActive(int row) throws IndexOutOfBoundsException {
            boolean b = true;
            b = rowStates.get(row);
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