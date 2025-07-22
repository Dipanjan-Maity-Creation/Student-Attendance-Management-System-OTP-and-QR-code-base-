/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forms;

import com.github.sarxos.webcam.Webcam;
import static com.github.sarxos.webcam.Webcam.getDefault;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import dao.ConnectionProvider;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
//import java.sql.Date;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.SimpleFormatter;
import javax.swing.BorderFactory;
import javax.swing.Timer;
import utility.bdutility;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.netbeans.lib.awtextra.AbsoluteConstraints;

public class markattendance extends javax.swing.JFrame implements Runnable, ThreadFactory{

  
        private WebcamPanel panel=null;
        private Webcam webcam = null;
        private ExecutorService executor= Executors.newSingleThreadExecutor(this);
        public volatile boolean running=true;
        
   
    
    public markattendance() {
        initComponents();
        bdutility.setImage(this, "images/hellobg.jpg", 1262, 828);
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.black));
        initWebcam();
        //this.getRootPane().setBorder(BorderFactory.createMatteBorder(4,4,4,4, Color.black));
        
      // Time timer = new Timer(1,e-updateTime());
        Timer timer=new Timer(1, e->updateTime());
        timer.start();
    }
    


    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String formattedTime = sdf.format(new Date());
        System.out.println(formattedTime);  // Print to console for debugging
        lblTime.setText(formattedTime);
    }


    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnExit = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblimage = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        lblname = new javax.swing.JLabel();
        lblChecIinCheckout = new javax.swing.JLabel();
        webCamPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1262, 828));
        setMinimumSize(new java.awt.Dimension(1262, 828));
        setUndecorated(true);

        btnExit.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnExit.setText("X");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 30)); // NOI18N
        jLabel1.setText("Mark Attendance");

        lblimage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel3.setText("Date");

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel4.setText("Time");

        lblTime.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        lblTime.setText("Time");

        lblname.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

        lblChecIinCheckout.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

        webCamPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(402, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(522, 522, 522)
                        .addComponent(btnExit)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblname, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblChecIinCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(143, 143, 143))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(webCamPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblimage, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(131, 131, 131)
                        .addComponent(jLabel4))
                    .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(105, 105, 105))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel1)))
                .addGap(106, 106, 106)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addComponent(lblTime)
                        .addGap(18, 18, 18)
                        .addComponent(lblimage, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(webCamPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(79, 79, 79)
                .addComponent(lblname, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblChecIinCheckout, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        running=false;
        stopWebcam();
        if(executor!=null && !executor.isShutdown()){
            executor.shutdown();
        }
        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

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
            java.util.logging.Logger.getLogger(markattendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(markattendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(markattendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(markattendance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new markattendance().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblChecIinCheckout;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblimage;
    private javax.swing.JLabel lblname;
    private javax.swing.JPanel webCamPanel;
    // End of variables declaration//GEN-END:variables

    
    Map<String,String> resultMap=new HashMap<String,String>();
    @Override
    
    public void run() {
       
        do {
try {

Thread.sleep(1000);

} catch (InterruptedException ex) {

}

try{

Result result=null;

BufferedImage image =null;


if(webcam.isOpen()){

if((image=webcam.getImage())==null){

continue;

}

}

LuminanceSource source = new BufferedImageLuminanceSource(image);
BinaryBitmap bitmap = new BinaryBitmap (new HybridBinarizer(source));

try{
    result=new MultiFormatReader().decode(bitmap);
}catch(NotFoundException ex){
    
}


if(result!=null){
    String jsonString=result.getText();
    Gson gson=new Gson();
    java.lang.reflect.Type type=new TypeToken<Map<String,String>>(){
    }.getType();
    resultMap=gson.fromJson(jsonString, type);
    
    String finalPath=bdutility.getPath("images\\"+resultMap.get("email")+".jpg");
    CirCularImageFrame(finalPath);
  
}

}catch (Exception ex) {
    ex.printStackTrace();
    
        }

        } while (running);
        
    }
        
    @Override
    public Thread newThread(Runnable r) {
        
        Thread t = new Thread(r, "My Thread");
        t.setDaemon(true);
        return t;
        
    }

    private void initWebcam() {
      
        webcam=Webcam.getDefault();
if (webcam != null) {
Dimension[] resolutions = webcam.getViewSizes();
Dimension maxResolution=resolutions [resolutions.length-1];
if(webcam.isOpen()) {
webcam.close();
}


webcam.setViewSize(maxResolution);
webcam.open();

panel = new WebcamPanel (webcam);

panel.setPreferredSize (maxResolution);

panel.setFPSDisplayed(true);

webCamPanel.add(panel,new org.netbeans.lib.awtextra.AbsoluteConstraints(0,0,577,490));
executor.execute(this);
executor.shutdown();

}
else{
    System.out.println("issue with webcam");
}
        
    }

    private void stopWebcam(){
        if (webcam!=null && webcam.isOpen()){
            webcam.close();
        }
    }
    
    private BufferedImage imagee=null;
    private void CirCularImageFrame(String imagePath) {
        try{
            Connection con=ConnectionProvider.getCon();
            Statement st=con.createStatement();
           // ResultSet rs=st.executeQuery("select * from userdetails where email=' "+ resultMap.get("email") + "';");
           ResultSet rs = st.executeQuery("select * from userdetails where email='" + resultMap.get("email").trim() + "';");

            if (!rs.next()){
                showPopUpforCertainDuration("User is not Register or deleted","Invalis Qr",JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            imagee=null;
            File imageFile=new File(imagePath);
            
            if (imageFile.exists()) {
            try{
            imagee=ImageIO.read(new File (imagePath));
            imagee=createcurcularImage(imagee);
            ImageIcon icon=new ImageIcon(imagee);
            lblimage.setIcon(icon);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

}
            else{
                
                BufferedImage imageeee =new BufferedImage (300, 300, BufferedImage.TYPE_INT_ARGB);

                  Graphics2D g2d=imageeee.createGraphics();

                   g2d.setColor(Color.BLACK);

                   g2d.fillOval(25, 25, 250, 250);

                   g2d.setFont(new Font("Serif", Font. BOLD, 250));

                   g2d.setColor(Color.WHITE);

                   g2d.drawString (String.valueOf(resultMap.get("name").charAt(0)).toUpperCase(), 75, 225);

                   g2d.dispose();

                   ImageIcon imageIconn=new ImageIcon(imageeee);

                   lblimage.setIcon(imageIconn);

                   this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

                   this.pack();

                   this.setLocationRelativeTo(null);
                   this.setVisible(true);
            }
            
            lblname.setHorizontalAlignment(JLabel.CENTER);
            lblname.setText(resultMap.get("name"));
            if(!checkIncheckOut()){
             return;
            }
            
        }catch(Exception ex){
            ex.printStackTrace();
        
        }
       
    }

    private void showPopUpforCertainDuration(String popUpMessage,String popUpHeader,Integer iconId) throws HeadlessException{
        
        final JOptionPane optionPane=new JOptionPane (popUpMessage, iconId);
        final JDialog dialog=optionPane.createDialog (popUpHeader);
        Timer timer=new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dialog.dispose();
              clearUserDetails();
            }

        });
        
            timer.setRepeats (false);
            timer.start();
            dialog.setVisible(true);
    }
    private void clearUserDetails() {
        lblChecIinCheckout.setText("");
        lblChecIinCheckout.setBackground(null);
        lblChecIinCheckout.setForeground(null);
        lblChecIinCheckout.setOpaque (false);
        lblname.setText("");
        lblimage.setIcon(null);
               
            }

    private BufferedImage createcurcularImage(BufferedImage image) {
        
        int diameter=285;

BufferedImage resizedImage=new BufferedImage (diameter, diameter, BufferedImage. TYPE_INT_ARGB);

Graphics2D g2 =resizedImage.createGraphics();

g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

g2.drawImage(image, 0, 0, diameter, diameter, null);

g2.dispose();;

BufferedImage circularImage=new BufferedImage (diameter, diameter, BufferedImage.TYPE_INT_ARGB);

g2 =circularImage.createGraphics();

Ellipse2D. Double circle=new Ellipse2D.Double (0, 0, diameter, diameter);

g2.setClip(circle);

g2.drawImage (resizedImage, 0, 0, null);

g2.dispose();

return circularImage;
        
    }

    private boolean checkIncheckOut() throws HeadlessException, SQLException{
        
        String popUpHeader = null;
        String popUpMessage = null;
        Color color = null;
        Connection con=ConnectionProvider.getCon();
        Statement st=con.createStatement();

        LocalDate currentDate=LocalDate.now();
        DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        LocalDateTime currentDateTime =LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ResultSet rs=st.executeQuery("select * from userattendance where date='"+currentDate.format(dateFormatter)+"' and userid="+Integer.valueOf(resultMap.get("id"))+";");
        
        Connection connection=ConnectionProvider.getCon();
        if(rs.next()){
            String checkOutDateTime=rs.getString(4);
            
            if (checkOutDateTime != null) {
            popUpMessage="Already Checkout For the Day";
            popUpHeader = "Invalid Checkout";
            
                showPopUpforCertainDuration(popUpMessage, popUpHeader,JOptionPane.ERROR_MESSAGE);
                return false;
                }
            
            String checkInDateTime=rs.getString(3);
            LocalDateTime checkInLocalDateTime =LocalDateTime.parse(checkInDateTime, dateTimeFormatter);
            Duration duration=Duration.between(checkInLocalDateTime, currentDateTime);
            
            long hours=duration.toHours();
            long minutes = duration.minusHours (hours).toMinutes();
            long seconds = duration.minusHours (hours).minusMinutes (minutes).getSeconds();
            
            if (! (hours > 0 || (hours==0 && minutes >= 10))) {

                    long remainingMinutes=9-minutes;
                        long remainingSeconds=60-seconds;
                        popUpMessage=String.format("Your work duration is less than 10 minutes\nYou can check out after: %d minutes ans %d seconds",remainingMinutes,remainingSeconds);
                        popUpHeader="Duration Warning";
                        
                   showPopUpforCertainDuration(popUpMessage, popUpHeader, JOptionPane.WARNING_MESSAGE);
                   return false;
            } 
            
            
//            String updateQuary="update user userattendance set checkout=?,workduration=? where date=? and userid=?";

            String updateQuary = "UPDATE userattendance SET checkout=?, workduration=? WHERE date=? AND userid=?";
            PreparedStatement preparedStatement=connection.prepareStatement (updateQuary);
            preparedStatement.setString(1, currentDateTime.format (dateTimeFormatter));
            preparedStatement.setString(2, "" + hours + " Hours and "+ minutes + "Minutes");
            preparedStatement.setString (3, currentDate.format(dateFormatter));
            preparedStatement.setString(4, resultMap.get("id"));
            preparedStatement.executeUpdate();
            popUpHeader="Checkout";
            
            popUpMessage="Checked Out at "+currentDateTime.format(dateFormatter)+"\n Work Duration"+hours+"Hours and "+minutes+" Minutes";
            color=Color.RED;
            
        }else{
            
            String insertQuery="INSERT INTO userattendance(userid, date, checkin) VALUES(?,?,?)";

            PreparedStatement preparedStatement=connection.prepareStatement (insertQuery);

            preparedStatement.setString(1, resultMap.get("id"));

            preparedStatement.setString(2, currentDate.format(dateFormatter));

            preparedStatement.setString(3, currentDateTime.format(dateTimeFormatter));

            preparedStatement.executeUpdate();

            popUpHeader="CheckIn";

            popUpMessage = "Check In at "+currentDateTime.format(dateTimeFormatter);

            color=Color. GREEN;
              
        }
        
        lblChecIinCheckout.setHorizontalAlignment (JLabel.CENTER);
        lblChecIinCheckout.setText (popUpHeader);
        lblChecIinCheckout.setForeground (color);
        lblChecIinCheckout.setBackground(Color. DARK_GRAY);
        lblChecIinCheckout.setOpaque (true);
        
        showPopUpforCertainDuration(popUpMessage, popUpHeader, JOptionPane.INFORMATION_MESSAGE);
//        return true;
        return Boolean.TRUE;
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        if(imagee !=null){
            g.drawImage(imagee,0,0,null);
        }
    }
    
}
