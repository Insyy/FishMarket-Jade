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


    private static class BuyerTableModel extends DefaultTableModel {

        private final static String columnNames[] = { "Description", "Current price" };
        private final Color ROW_WON_BID_COLOR = Color.GREEN;

        private List<AuctionItem> auctions = new ArrayList<>();
        private List<UUID> auctionsWon = new ArrayList<>();

        BuyerTableModel() {
            super(new Object[][] {},
                    columnNames);
        }

        public boolean isRowWon(final int row) throws IndexOutOfBoundsException {
            if (auctions == null || auctions.size() < row)
                return false;
            return auctionsWon.stream().anyMatch(uuid -> auctions.get(row).getId().equals(uuid));
        }

        

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        public void refreshTableData(final List<AuctionItem> auctions, final List<UUID> auctionsWon) {

            System.out.println("Refreshing table with data from " + auctions);

            this.auctions = auctions;
            this.auctionsWon = auctionsWon;

            getDataVector().removeAllElements();
            auctions.forEach(auction -> {
                addRow(auction.toStringArrayGUI(null));
            });
            System.out.println(getRowCount() + " rows found");
            fireTableDataChanged();
        }
    }

    private final static String title = "Buyer's view of the market";

    private JPanel wholePanel = new JPanel();
    //TOP PANEL
    private final JPanel topPanel = new JPanel();
    private final JButton automaticStartBtn = new JButton("Start in automatic mode");
    private final JButton manualStartBtn = new JButton("Start in manual mode");
    private final JLabel initialAmountLabel = new JLabel("Initial amount (EUR): ");

    private final JFormattedTextField initialAmountText = new JFormattedTextField(NumberFormat.getInstance());
    //TABLE PANEL
    private JScrollPane scrollPane;

    private JTable table;
    //BOTTOM PANEL
    private final JPanel bottomPanel = new JPanel();
    private final JButton subscribeToAuctionBtn = new JButton("Subscribe to selected auction(s)");
    private final JButton bidBtn = new JButton("Bid on selected auction");

    public BuyerGUI() {
        super(title);

        createGUI();        
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

        scrollPane = new JScrollPane(table);

        //BOTTOM PANEL
        bottomPanel.add(subscribeToAuctionBtn);
        bottomPanel.add(bidBtn);

        wholePanel.setLayout(new BoxLayout(wholePanel, BoxLayout.Y_AXIS));
        wholePanel.add(topPanel);
        wholePanel.add(scrollPane);
        wholePanel.add(bottomPanel);

        setupListeners();

        getContentPane().add(wholePanel);

        pack();
        setVisible(true);

    }

    private void setupListeners() {
        automaticStartBtn.addActionListener(e -> automaticStartListerner(e));
        manualStartBtn.addActionListener(e -> manualStartListerner(e));
        initialAmountText.addActionListener(e -> initialAmountListerner(e));
        subscribeToAuctionBtn.addActionListener(e -> subscribeListerner(e));
        bidBtn.addActionListener(e -> bidListerner(e));
    }

    /* Listeners */

    private void automaticStartListerner(java.awt.event.ActionEvent e) {
        System.out.println("Text=" + automaticStartBtn.getActionCommand());
    }


    private void manualStartListerner(java.awt.event.ActionEvent e) {
        System.out.println("Text=" + manualStartBtn.getActionCommand());
    }

    private void initialAmountListerner(java.awt.event.ActionEvent e) {
        System.out.println("Text=" + initialAmountText.getText());
    }


    private void subscribeListerner(java.awt.event.ActionEvent e) {
        System.out.println("Text=" + subscribeToAuctionBtn.getText());
    }

    private void bidListerner(java.awt.event.ActionEvent e) {
        System.out.println("Text=" + bidBtn.getText());
    }




    public void refreshTableData(final List<AuctionItem> auctions, final List<UUID> auctionsWon) {
        ((BuyerTableModel) table.getModel()).refreshTableData(auctions, auctionsWon);
    }

    private void configureTableSettings() {

        
        table.setCellSelectionEnabled(false);
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(true);

        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
                    final boolean hasFocus, final int row, final int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                processRow(table, row, (BuyerTableModel) table.getModel());
                return this;
            }

            private void processRow(final JTable table, final int row, final BuyerTableModel sellerTableModel) {
                try {
                    if (sellerTableModel.isRowWon(row)) {
                        setBackground(sellerTableModel.ROW_WON_BID_COLOR);
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