package fishmarket.agents.seller;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.awt.Color;
import java.awt.Component;

import fishmarket.auction.AuctionBid;
import fishmarket.auction.AuctionInstance;
import fishmarket.auction.AuctionItem;
import jade.core.Agent;

public class SellerGUI extends JFrame {

    Seller owner = null;

    private final static String title = "Seller's view of the market";
    JFrame frame = new JFrame();
    private JPanel wholePanel = new JPanel();

    // TOP PANEL
    private JPanel topPanel = new JPanel();

    private JFormattedTextField itemNameField = new JFormattedTextField();
    private JFormattedTextField initialPriceField = new JFormattedTextField();
    private JFormattedTextField waitingTimeField = new JFormattedTextField();
    private JFormattedTextField variationIncreaseField = new JFormattedTextField();
    private JFormattedTextField variationDecreaseField = new JFormattedTextField();

    {
        itemNameField.setText("Dinosaur");
        initialPriceField.setText(String.valueOf(38));
        waitingTimeField.setText(String.valueOf(60));
        variationIncreaseField.setText(String.valueOf(34));
        variationDecreaseField.setText(String.valueOf(17));
    }

    private JLabel itemNameLabel = new JLabel("Item's description");
    private JLabel initialPriceLabel = new JLabel("Initial price (EUR)");
    private JLabel waitingTimeLabel = new JLabel("Waiting time (s)");
    private JLabel variationIncreaseLabel = new JLabel("Step of price increase (EUR)");
    private JLabel variationDecreaseLabel = new JLabel("Step of price decrease (EUR)");

    private JButton publishAuctionBtn = new JButton("Publish auction");

    // TABLE PANEL
    private JScrollPane scrollPane;
    private JTable table;

    public SellerGUI(Agent owner) {
        super(title);
        this.owner = (Seller) owner;
        createGUI();

    }

    public void createGUI() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(createPanelWithLabelAndTextField(itemNameLabel, itemNameField));
        topPanel.add(createPanelWithLabelAndTextField(initialPriceLabel, initialPriceField));
        topPanel.add(createPanelWithLabelAndTextField(waitingTimeLabel, waitingTimeField));
        topPanel.add(createPanelWithLabelAndTextField(variationIncreaseLabel, variationIncreaseField));
        topPanel.add(createPanelWithLabelAndTextField(variationDecreaseLabel, variationDecreaseField));
        topPanel.add(publishAuctionBtn);
        // TABLE
        table = new JTable(new SellerTableModel());
        configureTableSettings();
        scrollPane = new JScrollPane(table);

        wholePanel.setLayout(new BoxLayout(wholePanel, BoxLayout.Y_AXIS));
        wholePanel.add(topPanel);
        wholePanel.add(scrollPane);

        setupListeners();

        getContentPane().add(wholePanel);

        pack();
        setVisible(true);

    }

    private void setupListeners() {
        publishAuctionBtn.addActionListener(e -> handlePublishBtnClick(e));
    }

    /* Listeners */

    private void handlePublishBtnClick(java.awt.event.ActionEvent e) {

        try {
            owner.publishAuctionItem(new AuctionItem(
                    itemNameField.getText(),
                    Float.parseFloat(initialPriceField.getText()),
                    Float.parseFloat(waitingTimeField.getText()),
                    Float.parseFloat(variationIncreaseField.getText()),
                    Float.parseFloat(variationDecreaseField.getText())
                    ));

        } catch (Exception event) {
            event.printStackTrace();
            // TODO: handle exception
        }
    }

    public JPanel createPanelWithLabelAndTextField(JLabel label, JFormattedTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(label);
        panel.add(textField);
        return panel;
    }

    private void configureTableSettings() {

        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                processRow(table, row, (SellerTableModel) table.getModel());
                return this;
            }

            private void processRow(JTable table, int row, SellerTableModel sellerTableModel) {
                /*
                 * try {
                 * if (sellerTableModel.isRowWon(row)) {
                 * setBackground(sellerTableModel.ROW_WON_BID_COLOR);
                 * setForeground(Color.WHITE);
                 * } else {
                 * setBackground(this.getBackground());
                 * setForeground(this.getForeground());
                 * }
                 * } catch (IndexOutOfBoundsException e) {
                 * e.printStackTrace();
                 * }
                 */
            }
        });
    }

    public void addBidToTable(AuctionBid bid) {
        ((SellerTableModel) table.getModel()).addBidToTable(bid);
    }

    private static class SellerTableModel extends DefaultTableModel {

        private final static String columnNames[] = { "Item", "Bidder", "Current price" };
        private final Color ROW_WON_BID_COLOR = Color.GREEN;

        SellerTableModel() {
            super(new Object[][] {},
                    columnNames);
        }

        /*
         * public boolean isRowWon(int row) throws IndexOutOfBoundsException {
         * if (auctions == null || auctions.size() < row)
         * return false;
         * return auctionsWon.stream().anyMatch(uuid ->
         * auctions.get(row).getId().equals(uuid));
         * }
         */

        public void addBidToTable(AuctionBid bid) {
            addRow(bid.toStringArray());
            fireTableDataChanged();
        }
    }

}
