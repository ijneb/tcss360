
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * 
 * @author Abdirizak ali
 * @author Benji Lee
 *
 */
public class OverviewPage extends JPanel {
	private Subproject thisSubproject;


    public OverviewPage(Subproject subproject) {
    	thisSubproject = subproject;
    	setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Create a header panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        
        headerPanel.setBackground(new Color(51, 102, 255));
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        JLabel headerLabel = new JLabel("Overview of " + thisSubproject.getName(), SwingConstants.CENTER);
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Helvetica", Font.BOLD, 32));
        headerPanel.add(headerLabel, BorderLayout.CENTER);


        JPanel pervButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton pervButton = new JButton("Back to Subproject");
        pervButton.setBackground(new Color(0, 153, 0));
        pervButton.setForeground(Color.black);
        pervButton.setFocusPainted(false);
        pervButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // show the perv page
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "Subproject");
            }
        });
        

        pervButtonPanel.setBackground(new Color(51, 102, 255));
        pervButtonPanel.add(pervButton);
        add(pervButtonPanel, BorderLayout.PAGE_END);

        //headerPanel.add(backButtonPanel, BorderLayout.PAGE_END);

        add(headerPanel, BorderLayout.NORTH);

        // Create a panel with tabs
        JTabbedPane tabbedPane = new JTabbedPane();
        // Change the font and color of the tab labels
        Font tabFont = new Font("Helvetica", Font.BOLD, 16);
        Color tabColor = new Color(51, 102, 255);
        tabbedPane.setFont(tabFont);
        tabbedPane.setForeground(tabColor);
        
        add(tabbedPane, BorderLayout.CENTER);

        // Create the Overview tab
        JPanel overviewPanel = new JPanel(new BorderLayout());
        overviewPanel.setBackground(Color.WHITE);
        tabbedPane.addTab("Overview", overviewPanel);

        // Create the Budget tab
        BudgetScreen budgetPanel = new BudgetScreen(thisSubproject);
        budgetPanel.setBackground(Color.WHITE);
        tabbedPane.addTab("Budget", budgetPanel);

        // Create the Item tab
        JPanel documentPanel = new JPanel(new BorderLayout());
        documentPanel.setBackground(Color.WHITE);
        tabbedPane.addTab("Documents", documentPanel);
        
        //Item screen
        ItemScreen docScreen = new ItemScreen(thisSubproject);
        documentPanel.add(docScreen, BorderLayout.CENTER);

        // Add components to the Overview tab
        JPanel overviewTopPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        overviewTopPanel.setBackground(Color.WHITE);
        overviewTopPanel.setBorder(new EmptyBorder(20, 20, 0, 20));
        overviewPanel.add(overviewTopPanel, BorderLayout.NORTH);

        // Add the budget bar graph to the top panel
        JPanel budgetGraphPanel = new JPanel(new GridLayout(2,1));
        budgetGraphPanel.setBackground(Color.WHITE);
        budgetGraphPanel.setPreferredSize(new Dimension(300, 200));
        budgetGraphPanel.setBorder(BorderFactory.createLineBorder(new Color(51, 102, 255), 2));
        overviewTopPanel.add(budgetGraphPanel);
        
        
        // Add the top 3 expenses panel to the top panel
        JPanel expensesPanel = new JPanel(new BorderLayout());
        expensesPanel.setBackground(Color.WHITE);
        expensesPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        overviewTopPanel.add(expensesPanel);

        JLabel expensesLabel = new JLabel("Top 3 Expenses", SwingConstants.CENTER);
        expensesLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
        expensesPanel.add(expensesLabel, BorderLayout.NORTH);
        
        // Add the expense items to the expenses panel
        JPanel expenseItemsPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        expenseItemsPanel.setBackground(Color.WHITE);
        expensesPanel.add(expenseItemsPanel, BorderLayout.CENTER);

        // Add the item list panel to the overview panel
        JPanel itemListPanel = new JPanel(new BorderLayout());
        itemListPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        itemListPanel.setBackground(Color.WHITE);
        itemListPanel.setPreferredSize(new Dimension(300, 400));
        itemListPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        overviewPanel.add(itemListPanel, BorderLayout.CENTER);

        JLabel itemListLabel = new JLabel("Item List", SwingConstants.CENTER);
        itemListLabel.setFont(new Font("Helvetica", Font.BOLD, 16));
        itemListPanel.add(itemListLabel, BorderLayout.NORTH);

        // Add the item list to the item list panel
        JPanel itemsPanel = new JPanel(new GridLayout(0, 1, 0, 10));
        itemsPanel.setBackground(Color.WHITE);
        JScrollPane itemsScrollPane = new JScrollPane(itemsPanel);
        itemsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        itemsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        itemListPanel.add(itemsScrollPane, BorderLayout.CENTER);

        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
                if(sourceTabbedPane.getSelectedIndex() == 0) {
                    //Change Top 3 Expenses
                    expenseItemsPanel.removeAll();
                    subproject.sortItems();
                    List<Item> sortedItems;
                    if(subproject.getItems().size() >= 3) {
                        sortedItems = subproject.getItems().subList(0, 3);
                    }
                    else {
                        sortedItems = subproject.getItems();
                    }
                    for (Item i : sortedItems) {
                        String expenseItemString = "Item: " + i.getName() + "     Cost: $" + String.valueOf(i.getCost());
                        JLabel expenseItemLabel = new JLabel(expenseItemString, SwingConstants.LEFT);
                        expenseItemLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        
                        expenseItemsPanel.add(expenseItemLabel);
                    }

                    //Change Items Panel
                    itemsPanel.removeAll();
                    for (Item item : thisSubproject.getItems()) {
                    JLabel itemLabel = new JLabel(item.getName());
                    itemLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    itemsPanel.add(itemLabel);

                    //Change Progress Bar
                    budgetGraphPanel.removeAll();
                    JLabel progressBarLabel = new JLabel("Budget", SwingConstants.CENTER);
                    progressBarLabel.setFont(new Font("Helvetica", Font.BOLD, 18));
                    budgetGraphPanel.add(progressBarLabel);
                    JProgressBar budgetBar = new JProgressBar();
                    budgetBar.setStringPainted(true);
                    budgetBar.setValue(subproject.getBudget().getValue());
                    budgetBar.setMaximum(subproject.getBudget().getMaximum());
                    budgetGraphPanel.add(budgetBar);
                }
                }
            }
        });
    }
}
