/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banking;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author shayoTech
 */
public class Banking extends JApplet {
    
    private static final int JFXPANEL_WIDTH_INT = 300;
    private static final int JFXPANEL_HEIGHT_INT = 250;
    private static JFXPanel fxContainer;
    private String withdrawAmount = "";
    Customer customerAccount;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (Exception e) {
                }
                
                JFrame frame = new JFrame("Banking Simulator");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                JApplet applet = new Banking();
                applet.init();
                
                frame.setContentPane(applet.getContentPane());
                
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                
                applet.start();
            }
        });
    }
    
    @Override
    public void init() {
        fxContainer = new JFXPanel();
        fxContainer.setPreferredSize(new Dimension(JFXPANEL_WIDTH_INT, JFXPANEL_HEIGHT_INT));
        add(fxContainer, BorderLayout.CENTER);
        // create JavaFX scene
        Platform.runLater(new Runnable() {
            
            @Override
            public void run() {
                createScene();
                customerAccount = new Customer();
                customerAccount.askToCreateAccount();
                
            }
        });
    }
    
    private void createScene() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(5);
        
        TextField withdrawAmountInput = new TextField();
        withdrawAmountInput.setPromptText("Enter Withdraw Amount");
        Label placeholder = new Label("");
        Button btn = new Button();
        btn.setText("Cash Withdraw");
        GridPane.setConstraints(withdrawAmountInput, 0, 0);
        GridPane.setConstraints(btn, 0, 1);
        GridPane.setConstraints(placeholder, 0, 2);
        Button btnMakeWithdraw = new Button();
        btnMakeWithdraw.setText("Submit");
        GridPane.setConstraints(btnMakeWithdraw, 0, 1);
        btn.setOnAction((ActionEvent event) -> {
            grid.getChildren().remove(btn);
            grid.getChildren().add(withdrawAmountInput);
            grid.getChildren().add(placeholder);
            grid.getChildren().add(btnMakeWithdraw);
        });
        
         btnMakeWithdraw.setOnAction((ActionEvent event) -> {
             System.out.println("amount"+withdrawAmount);
             withdrawAmount = withdrawAmountInput.getText();
             if(!withdrawAmount.isEmpty()){
                 placeholder.setText("");
            if(Integer.parseInt(withdrawAmount) < 10000 || Integer.parseInt(withdrawAmount) > 400000){
                JOptionPane.showMessageDialog(null,"Transaction Declined");
            }else{
                customerAccount.withdrawFromMyaccount(Double.parseDouble(withdrawAmount));
                String response = "You have successfully withdrawn : "+withdrawAmount;
                placeholder.setText(response);
            }
             }else{
                placeholder.setText("Amount is required"); 
             }}
        );

        
        grid.getChildren().add(btn);
        fxContainer.setScene(new Scene(grid));
    }
    
    
    
    class Account {
        double balance;
        Account(){
            
        }
        
        double withdraw(double amount){
            return balance = balance - amount;
        }
        
        double deposit(double amount){
            return balance = balance+amount;
        }
    }
    
    class BankTeller extends Account {
        void createAccount(){
            this.deposit(100000);
        }
        
    }
    
    class Customer extends Account{
       
        public void askToCreateAccount(){
             BankTeller bank = new BankTeller();
             bank.createAccount();
        }
        public void withdrawFromMyaccount (double Amount){
            this.withdraw(Amount);
        }
    }
    
}
