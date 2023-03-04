package fishmarket.agents.buyer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.awt.Color;
import java.awt.Component;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import fishmarket.auction.AuctionItem;

public class BuyerGUI extends JFrame {


    private final static String title = "Buyer's view of the market";

    private JPanel wholePanel = new JPanel();

    //TOP PANEL
    private JPanel topPanel = new JPanel();
    private JButton automaticStartBtn = new JButton("Start in automatic mode");
    private JButton manualStartBtn = new JButton("Start in manual mode");
    private JLabel initialAmountLabel = new JLabel("Initial amount (EUR): ");
    private JFormattedTextField initialAmountText = new JFormattedTextField(NumberFormat.getInstance());

    //TABLE PANEL
    private JPanel tablePanel = new JPanel(); 
    private JScrollPane scrollPane = new JScrollPane();
    private JTable table;

    //BOTTOM PANEL
    private JPanel bottomPanel = new JPanel();
    private JButton subscribeToAuctionBtn = new JButton("Subscribe to selected auction(s)");
    private JButton bidBtn = new JButton("Bid on selected auction");

    public BuyerGUI() {
        super(title);

        createGUI();
        configureTableSettings();
        
    }

    public void createGUI() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // TOP PANEL
        topPanel.add(automaticStartBtn);
        topPanel.add(initialAmountLabel);

        initialAmountText.setValue(Integer.valueOf(1000));
        topPanel.add(initialAmountText);

        topPanel.add(manualStartBtn);

        // TABLE
        table = new JTable(new BuyerTableModel());
        configureTableSettings();

        scrollPane.add(table);
        tablePanel.add(scrollPane);

        //BOTTOM PANEL
        bottomPanel.add(subscribeToAuctionBtn);
        bottomPanel.add(bidBtn);

        wholePanel = new JPanel();
        wholePanel.setLayout(new BoxLayout(wholePanel, BoxLayout.Y_AXIS));
        wholePanel.add(topPanel);
        wholePanel.add(tablePanel);
        wholePanel.add(bottomPanel);

        getContentPane().add(wholePanel);

        pack();
        setVisible(true);

    }

    private void configureTableSettings() {

        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                processRow(table, row, (BuyerTableModel) table.getModel());
                return this;
            }

            private void processRow(JTable table, int row, BuyerTableModel sellerTableModel) {
                try {
                    if (sellerTableModel.isRowWon(row)) {
                        setBackground(sellerTableModel.ROW_WON_BID_COLOR);
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

    public void refreshTableData(List<AuctionItem> auctions, List<UUID> auctionsWon) {
        ((BuyerTableModel) table.getModel()).refreshTableData(auctions, auctionsWon);
    }

    private static class BuyerTableModel extends DefaultTableModel {

        private final static String columnNames[] = { "Description", "Current price" };
        private final Color ROW_WON_BID_COLOR = Color.GREEN;

        private List<AuctionItem> auctions = new ArrayList<>();
        private List<UUID> auctionsWon = new ArrayList<>();

        BuyerTableModel() {
            super(new Object[][] {},
                    columnNames);
        }

        public boolean isRowWon(int row) throws IndexOutOfBoundsException {
            if (auctions == null || auctions.size() < row)
                return false;
                return auctionsWon.stream().anyMatch(uuid -> auctions.get(row).getId().equals(uuid));
        }

        public void refreshTableData(List<AuctionItem> auctions, List<UUID> auctionsWon) {

            this.auctionsWon = auctionsWon;

            getDataVector().removeAllElements();
            auctions.forEach(auction -> {
                addRow(auction.toStringArrayGUI(null));
            });
            fireTableDataChanged();
        }
    }
}