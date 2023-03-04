package fishmarket.agents.seller;


import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

import fishmarket.auction.AuctionItem;

public class SellerGUI extends JFrame{

    private final static String title = "Seller's view of the market";
    JFrame frame= new JFrame(); 
    private JPanel wholePanel = new JPanel();

    //TOP PANEL
    private JPanel topPanel = new JPanel();

    private JFormattedTextField sellerNameField = new JFormattedTextField();
    private JFormattedTextField initialPriceField  = new JFormattedTextField();
    private JFormattedTextField waitingTimeField  = new JFormattedTextField();
    private JFormattedTextField variationField  = new JFormattedTextField();

    private JLabel sellerNameLabel = new JLabel("Description de l'article");
    private JLabel initialPriceLabel = new JLabel("Prix initial (euros)");
    private JLabel waitingTimeLabel = new JLabel("Temps d'attente (seconde)");
    private JLabel variationLabel = new JLabel("Pas de variation (euros)");

    private JButton subscribeToAuctionBtn = new JButton("Creer annonce");

    //TABLE PANEL
    private JPanel tablePanel = new JPanel(); 
    private JScrollPane scrollPane = new JScrollPane();
    private JTable table;

    public SellerGUI() {
        super(title);
        createGUI();

    }

    public void createGUI(){

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        table = new JTable(new SellerTableModel());

        //TABLE
        wholePanel = new JPanel();
        configureTableSettings();

        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(addLabelText(sellerNameLabel,sellerNameField));
        topPanel.add(addLabelText(initialPriceLabel,initialPriceField));
        topPanel.add(addLabelText(waitingTimeLabel,waitingTimeField));
        topPanel.add(addLabelText(variationLabel,variationField));
        topPanel.add(subscribeToAuctionBtn);
    
        wholePanel.setLayout(new BoxLayout(wholePanel, BoxLayout.Y_AXIS));
        wholePanel.add(topPanel);
        getContentPane().add(wholePanel);

        pack();
        setVisible(true);

    }

    public JPanel addLabelText(JLabel label, JFormattedTextField textField){
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
        ((SellerTableModel) table.getModel()).refreshTableData(auctions, auctionsWon);
    }

    private static class SellerTableModel extends DefaultTableModel {

        private final static String columnNames[] = { "Annonces", "Prix", "Agent Preneur" };
        private final Color ROW_WON_BID_COLOR = Color.GREEN;

        private List<AuctionItem> auctions = new ArrayList<>();
        private List<UUID> auctionsWon = new ArrayList<>();

        SellerTableModel() {
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
