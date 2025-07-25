/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import dao.ConnectionProvider;
import java.awt.Color;
import java.awt.Image;
import static java.awt.Image.SCALE_SMOOTH;
import java.io.File;
import java.sql.*;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import utility.bdutility;
//import com.java.*;

/**
 *
 * @author NEHA MAITY
 */
public class viewdetails extends javax.swing.JFrame {

    /**
     * Creates new form viewdetails
     */
    public viewdetails() {
    initComponents();
    bdutility.setImage(this, "images/hellobg.jpg", 1262, 828);
    this.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.black));
    
    try {
        fetchUser(null);  // Load all users when the window opens
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error loading data: " + ex.getMessage());
    }
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchTEXT = new javax.swing.JLabel();
        btnExit = new javax.swing.JButton();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        lblImage = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        regtable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtsearch = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1262, 828));
        setMinimumSize(new java.awt.Dimension(1262, 828));
        setUndecorated(true);

        searchTEXT.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        searchTEXT.setText("View Details");

        btnExit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnExit.setText("X");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        jInternalFrame1.setVisible(true);

        lblImage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblImageMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addComponent(lblImage, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 26, Short.MAX_VALUE))
        );

        regtable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Name", "Gender", "Email", "contact", "address", "state", "country", "uniqueregid", "imagename"
            }
        ));
        regtable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                regtableMouseClicked(evt);
            }
        });
        regtable.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                regtableComponentShown(evt);
            }
        });
        jScrollPane2.setViewportView(regtable);

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 20)); // NOI18N
        jLabel2.setText("Search");

        txtsearch.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtsearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsearchActionPerformed(evt);
            }
        });
        txtsearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtsearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(432, 432, 432)
                        .addComponent(searchTEXT)
                        .addGap(527, 527, 527)
                        .addComponent(btnExit))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(520, 520, 520)
                        .addComponent(jLabel2)
                        .addGap(20, 20, 20)
                        .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(searchTEXT))
                    .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtsearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(210, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void lblImageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImageMouseClicked
        // TODO add your handling code here:
      

    }//GEN-LAST:event_lblImageMouseClicked

    private void txtsearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtsearchActionPerformed

    private void regtableComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_regtableComponentShown
        // TODO add your handling code here:
        try{
           fetchUser(null);
        }catch(Exception ex){
            ex.printStackTrace();
            
        }
           
    }//GEN-LAST:event_regtableComponentShown

    private void txtsearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtsearchKeyReleased
        // TODO add your handling code here:
        try{
        fetchUser(txtsearch.getText().toString());
        }catch(Exception ex){
            ex.printStackTrace();
            
        }
        
    }//GEN-LAST:event_txtsearchKeyReleased

    private void regtableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_regtableMouseClicked
        // TODO add your handling code here:
        int index=regtable.getSelectedRow();
        TableModel model=regtable.getModel();
        String name=Objects.isNull(model.getValueAt(index, 9)) ? null : model.getValueAt(index, 9).toString();
            if (!Objects.isNull(name)) {
                String imagePath =bdutility.getPath("/images" +File.separator + name);
                File imageFile= new File(imagePath);
if (imageFile.exists()) {
            ImageIcon icon =new ImageIcon (imagePath);
            Image image= icon.getImage().getScaledInstance (322, 286, Image.SCALE_SMOOTH);
            ImageIcon resizedIcon= new ImageIcon(image);
            lblImage.setIcon(resizedIcon);
       } else {
lblImage.setIcon(null);

JOptionPane.showMessageDialog(null, "Either image has been deleted or not found.", "Image not found",

JOptionPane.WARNING_MESSAGE);

}

} else {

lblImage.setIcon(null);
                
            }
    }//GEN-LAST:event_regtableMouseClicked
   private void fetchUser(String searchTEXT) throws Exception {
    DefaultTableModel model = (DefaultTableModel) regtable.getModel();
    model.setRowCount(0);  // Reset the table before adding new data

    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    try {
        // Establish connection to database
        con = ConnectionProvider.getCon();
        if (con == null) {
            System.out.println("Connection failed.");
            return;
        }
        System.out.println("Database connection successful.");

        // Prepare the SQL query
        String query = "SELECT * FROM USERDETAILS";
        if (searchTEXT != null && !searchTEXT.trim().isEmpty()) {
            query = "SELECT * FROM userdetails WHERE name LIKE ? OR email LIKE ?";
        }

        System.out.println("Executing query: " + query);

        // Prepare the statement
        st = con.prepareStatement(query);

        // Set the parameters if search text is provided
        if (searchTEXT != null && !searchTEXT.trim().isEmpty()) {
            String searchPattern = "%" + searchTEXT + "%";
            st.setString(1, searchPattern);
            st.setString(2, searchPattern);
        }

        // Execute the query
        rs = st.executeQuery();

        // If no results are found
        if (!rs.next()) {
            System.out.println("No records found.");
            return;
        }

        // Log the data fetched
        do {
            System.out.println("ID: " + rs.getString("id"));
            System.out.println("Name: " + rs.getString("name"));
            model.addRow(new Object[]{
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("gender"),
                rs.getString("email"),
                rs.getString("contact"),
                rs.getString("address"),
                rs.getString("state"),
                rs.getString("country"),
                rs.getString("uniqueregid"),
                rs.getString("imagename")
            });
        } while (rs.next());

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Something went wrong: " + ex.getMessage());
    } finally {
        if (rs != null) try { rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        if (st != null) try { st.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        if (con != null) try { con.close(); } catch (SQLException ex) { ex.printStackTrace(); }
    }
}



       
       
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(viewdetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(viewdetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(viewdetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(viewdetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new viewdetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblImage;
    private javax.swing.JTable regtable;
    private javax.swing.JLabel searchTEXT;
    private javax.swing.JTextField txtsearch;
    // End of variables declaration//GEN-END:variables
}
