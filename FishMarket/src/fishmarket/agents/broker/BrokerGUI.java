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

    private static class BrokerTableModel extends DefaultTableModel {

        private final static String columnNames[] = { "Vendeur", "Nom du lot", "Montant courant de l'enchere" };
        private List<Boolean> rowStates = new ArrayList<>();

        private final Color ROW_INACTIVE_COLOR = Color.BLACK;

        BrokerTableModel() {
            super(new Object[][] {},
                    columnNames);
        }

        public boolean isRowActive(final int row) throws IndexOutOfBoundsException {
            if (rowStates.size() < row)
                return false;
            return rowStates.get(row);
        }

        public void refreshTableData(final List<AuctionInstance> auctions) {
            getDataVector().removeAllElements();
            rowStates.clear();
            for (int i = 0; i < auctions.size(); i++) {
                final AuctionInstance auction = auctions.get(i);

                addRow(auction.getItem().toStringArrayGUI(auction.getSeller().getName()));
                rowStates.add(auction.isActive());
            }
            fireTableDataChanged();
        }
    }

    private final JFrame frame;

    private final String title = "Broker's view of the market";

    public BrokerGUI() {
        super(new BrokerTableModel());

        configureInitialSettings();

        // Add the table to a scroll pane
        final JScrollPane scrollPane = new JScrollPane(this);

        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(scrollPane);

        frame.pack();
        frame.setVisible(true);
    }

    public void recreateGUI(final List<AuctionInstance> auctions) {
        ((BrokerTableModel) this.getModel()).refreshTableData(auctions);
    }

    private void configureInitialSettings() {
        this.setFillsViewportHeight(true);
        this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
                    final boolean hasFocus, final int row, final int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                processRow(table, row, (BrokerTableModel) table.getModel());
                return this;
            }

            private void processRow(final JTable table, final int row, final BrokerTableModel brokerTableModel) {
                try {
                    if (!brokerTableModel.isRowActive(row)) {
                        setBackground(brokerTableModel.ROW_INACTIVE_COLOR);
                        setForeground(Color.WHITE);
                        
                    } else {
                        setBackground(this.getBackground());
                        setForeground(this.getForeground());
                    }
                } catch (final IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                
            }
        });
    }

}