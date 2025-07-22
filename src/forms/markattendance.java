package forms;

import com.github.sarxos.webcam.Webcam;
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
import utility.bdutility;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import javax.imageio.ImageIO;
import java.awt.geom.Ellipse2D;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.netbeans.lib.awtextra.AbsoluteConstraints;

/**
 * Mark Attendance Form: Scans QR codes, sends OTP, verifies OTP, and records attendance.
 */
public class markattendance extends javax.swing.JFrame implements Runnable, ThreadFactory {
    private static final Logger logger = Logger.getLogger(markattendance.class);
    private WebcamPanel panel = null;
    private Webcam webcam = null;
    private ExecutorService executor = Executors.newSingleThreadExecutor(this);
    public volatile boolean running = true;
    private BufferedImage imagee = null;
    private Map<String, String> resultMap = new HashMap<>();

    public markattendance() {
        initComponents();
        bdutility.setImage(this, "images/hellobg.jpg", 1262, 828);
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.black));
        ensureOtpsTable();
        initWebcam();
        Timer timer = new Timer(1, e -> updateTime());
        timer.start();
    }

    private void ensureOtpsTable() {
        try (Connection con = ConnectionProvider.getCon()) {
            if (con == null) {
                logger.error("Failed to connect to database");
                return;
            }
            String sql = "CREATE TABLE IF NOT EXISTS otps (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "email VARCHAR(255) NOT NULL, " +
                "otp VARCHAR(10) NOT NULL, " +
                "expires_at DATETIME NOT NULL, " +
                "INDEX idx_email (email))";
            try (Statement stmt = con.createStatement()) {
                stmt.executeUpdate(sql);
                logger.info("otps table created or already exists");
            }
        } catch (SQLException ex) {
            logger.error("Failed to create otps table", ex);
        }
    }

    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String formattedTime = sdf.format(new Date());
        lblTime.setText(formattedTime);
    }

    @SuppressWarnings("unchecked")
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

        btnExit.setFont(new java.awt.Font("Tahoma", 1, 14));
        btnExit.setText("X");
        btnExit.addActionListener(evt -> btnExitActionPerformed(evt));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 30));
        jLabel1.setText("Mark Attendance");

        lblimage.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 14));
        jLabel3.setText("Date");

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 14));
        jLabel4.setText("Time");

        lblTime.setFont(new java.awt.Font("Verdana", 1, 16));
        lblTime.setText("Time");

        lblname.setFont(new java.awt.Font("Verdana", 1, 14));

        lblChecIinCheckout.setFont(new java.awt.Font("Verdana", 1, 14));

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
    }

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {
        running = false;
        stopWebcam();
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
        this.dispose();
    }

    public static void main(String args[]) {
        System.setProperty("webcam.debug", "true");
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.error("Failed to set Nimbus Look and Feel", ex);
        }
        java.awt.EventQueue.invokeLater(() -> new markattendance().setVisible(true));
    }

    private javax.swing.JButton btnExit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblChecIinCheckout;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblimage;
    private javax.swing.JLabel lblname;
    private javax.swing.JPanel webCamPanel;

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                logger.error("Thread interrupted", ex);
            }

            try {
                if (webcam == null || !webcam.isOpen()) {
                    logger.warn("Webcam not open, attempting to reinitialize");
                    stopWebcam();
                    initWebcam();
                    continue;
                }
                BufferedImage image = webcam.getImage();
                if (image == null) {
                    logger.debug("No image captured from webcam");
                    continue;
                }
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                Result result = new MultiFormatReader().decode(bitmap);
                if (result != null) {
                    logger.info("QR code detected: " + result.getText());
                    String jsonString = result.getText();
                    Gson gson = new Gson();
                    java.lang.reflect.Type type = new TypeToken<Map<String, String>>() {}.getType();
                    resultMap = gson.fromJson(jsonString, type);
                    String finalPath = bdutility.getPath("images\\" + resultMap.get("email") + ".jpg");
                    SwingUtilities.invokeLater(() -> CirCularImageFrame(finalPath));
                }
            } catch (NotFoundException ex) {
                // No QR code found
            } catch (Exception ex) {
                logger.error("QR scanning error: " + ex.getMessage(), ex);
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
        webcam = Webcam.getDefault();
        if (webcam == null) {
            logger.error("No webcam detected");
            JOptionPane.showMessageDialog(this, "No webcam detected!", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        logger.info("Webcam detected: " + webcam.getName());
        Dimension safeResolution = new Dimension(640, 480);
        if (webcam.isOpen()) {
            try {
                webcam.close();
            } catch (Exception ex) {
                logger.warn("Failed to close webcam: " + ex.getMessage(), ex);
            }
        }
        webcam.setViewSize(safeResolution);
        int maxRetries = 3;
        int retryDelayMs = 1000;
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                webcam.open();
                panel = new WebcamPanel(webcam);
                panel.setPreferredSize(safeResolution);
                panel.setFPSDisplayed(true);
                webCamPanel.add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 577, 490));
                executor = Executors.newSingleThreadExecutor(this);
                executor.execute(this);
                logger.info("Webcam initialized successfully");
                return;
            } catch (Exception ex) {
                logger.error("Webcam initialization attempt " + attempt + " failed: " + ex.getMessage(), ex);
                if (attempt < maxRetries) {
                    try {
                        Thread.sleep(retryDelayMs);
                    } catch (InterruptedException ie) {
                        logger.warn("Retry interrupted", ie);
                    }
                }
            }
        }
        logger.error("Failed to initialize webcam after " + maxRetries + " attempts");
        JOptionPane.showMessageDialog(this, "Webcam initialization failed: Camera may be in use", "Error", JOptionPane.ERROR_MESSAGE);
        dispose();
    }

    private void stopWebcam() {
        if (webcam != null && webcam.isOpen()) {
            webcam.close();
        }
    }

    private void CirCularImageFrame(String imagePath) {
        try (Connection con = ConnectionProvider.getCon()) {
            if (con == null) {
                logger.error("Database connection failed");
                showPopUpforCertainDuration("Database connection failed", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM userdetails WHERE email = ?")) {
                ps.setString(1, resultMap.get("email").trim());
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    logger.warn("User not found: " + resultMap.get("email"));
                    showPopUpforCertainDuration("User is not registered or deleted", "Invalid QR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                logger.info("User found: " + rs.getString("name"));

                imagee = null;
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    try {
                        imagee = ImageIO.read(imageFile);
                        imagee = createcurcularImage(imagee);
                        ImageIcon icon = new ImageIcon(imagee);
                        lblimage.setIcon(icon);
                    } catch (Exception ex) {
                        logger.error("Error loading image", ex);
                    }
                } else {
                    BufferedImage imageeee = new BufferedImage(300, 300, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = imageeee.createGraphics();
                    g2d.setColor(Color.BLACK);
                    g2d.fillOval(25, 25, 250, 250);
                    g2d.setFont(new Font("Serif", Font.BOLD, 250));
                    g2d.setColor(Color.WHITE);
                    g2d.drawString(String.valueOf(resultMap.get("name").charAt(0)).toUpperCase(), 75, 225);
                    g2d.dispose();
                    ImageIcon imageIconn = new ImageIcon(imageeee);
                    lblimage.setIcon(imageIconn);
                }

                lblname.setHorizontalAlignment(JLabel.CENTER);
                lblname.setText(resultMap.get("name"));

                String otp = generateOTP();
                storeOTP(con, resultMap.get("email").trim(), otp);
                sendOTPByEmail(resultMap.get("email").trim(), otp);
                boolean isValid = showOTPDialog(resultMap.get("email").trim());
                if (isValid) {
                    logger.info("OTP verified, checking attendance");
                    if (!checkIncheckOut()) {
                        return;
                    }
                } else {
                    logger.warn("Invalid OTP entered");
                    showPopUpforCertainDuration("Invalid or expired OTP", "OTP Error", JOptionPane.ERROR_MESSAGE);
                    clearUserDetails();
                }
            }
        } catch (SQLException ex) {
            logger.error("Database error: " + ex.getMessage(), ex);
            showPopUpforCertainDuration("Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    private void storeOTP(Connection con, String email, String otp) throws SQLException {
        try {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expiresAt = now.plusMinutes(5);
            try (PreparedStatement ps = con.prepareStatement(
                "INSERT INTO otps (email, otp, expires_at) VALUES (?, ?, ?)")) {
                ps.setString(1, email);
                ps.setString(2, otp);
                ps.setTimestamp(3, Timestamp.valueOf(expiresAt));
                ps.executeUpdate();
                logger.info("OTP stored for " + email);
            }
            try (PreparedStatement ps = con.prepareStatement(
                "DELETE FROM otps WHERE expires_at < ?")) {
                ps.setTimestamp(1, Timestamp.valueOf(now));
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            logger.error("Failed to store OTP for " + email + ": " + ex.getMessage(), ex);
            throw ex;
        }
    }

    private void sendOTPByEmail(String email, String otp) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("Your e-mail Id", "Your Google App Password");
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("your-email@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Your OTP for Attendance");
            message.setText("Your OTP is: " + otp);
            Transport.send(message);
            logger.info("OTP email sent to " + email);
            JOptionPane.showMessageDialog(this, "OTP sent to " + email, "OTP Sent", JOptionPane.INFORMATION_MESSAGE);
        } catch (MessagingException e) {
            logger.error("Failed to send OTP email: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(this, "Failed to send OTP email: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean showOTPDialog(String email) {
        JDialog otpDialog = new JDialog(this, "Enter OTP", true);
        otpDialog.setSize(300, 150);
        otpDialog.setLayout(null);
        otpDialog.setLocationRelativeTo(this);

        JLabel lblPrompt = new JLabel("Enter OTP:");
        lblPrompt.setBounds(20, 20, 100, 30);
        otpDialog.add(lblPrompt);

        JTextField txtOTP = new JTextField();
        txtOTP.setBounds(120, 20, 150, 30);
        otpDialog.add(txtOTP);

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(120, 60, 100, 30);
        otpDialog.add(btnSubmit);

        final boolean[] isValid = {false};
        btnSubmit.addActionListener(e -> {
            String enteredOTP = txtOTP.getText().trim();
            if (enteredOTP.isEmpty()) {
                JOptionPane.showMessageDialog(otpDialog, "Please enter OTP");
                return;
            }
            try (Connection con = ConnectionProvider.getCon()) {
                if (con == null) {
                    logger.error("Database connection failed in OTP dialog");
                    JOptionPane.showMessageDialog(otpDialog, "Database connection failed");
                    return;
                }
                try (PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM otps WHERE email = ? AND otp = ? AND expires_at > NOW()")) {
                    ps.setString(1, email);
                    ps.setString(2, enteredOTP);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        isValid[0] = true;
                        try (PreparedStatement deletePs = con.prepareStatement(
                            "DELETE FROM otps WHERE email = ? AND otp = ?")) {
                            deletePs.setString(1, email);
                            deletePs.setString(2, enteredOTP);
                            deletePs.executeUpdate();
                            logger.info("OTP verified and deleted for " + email);
                        }
                    } else {
                        logger.warn("Invalid or expired OTP for " + email);
                        JOptionPane.showMessageDialog(otpDialog, "Invalid or expired OTP");
                    }
                }
                otpDialog.dispose();
            } catch (SQLException ex) {
                logger.error("Database error in OTP dialog: " + ex.getMessage(), ex);
                JOptionPane.showMessageDialog(otpDialog, "Database error: " + ex.getMessage());
            }
        });

        otpDialog.setVisible(true);
        return isValid[0];
    }

    private void showPopUpforCertainDuration(String popUpMessage, String popUpHeader, Integer iconId) {
        final JOptionPane optionPane = new JOptionPane(popUpMessage, iconId);
        final JDialog dialog = optionPane.createDialog(popUpHeader);
        Timer timer = new Timer(5000, e -> {
            dialog.dispose();
            clearUserDetails();
        });
        timer.setRepeats(false);
        timer.start();
        dialog.setVisible(true);
    }

    private void clearUserDetails() {
        lblChecIinCheckout.setText("");
        lblChecIinCheckout.setBackground(null);
        lblChecIinCheckout.setForeground(null);
        lblChecIinCheckout.setOpaque(false);
        lblname.setText("");
        lblimage.setIcon(null);
    }

    private BufferedImage createcurcularImage(BufferedImage image) {
        int diameter = 285;
        BufferedImage resizedImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(image, 0, 0, diameter, diameter, null);
        g2.dispose();

        BufferedImage circularImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        g2 = circularImage.createGraphics();
        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, diameter, diameter);
        g2.setClip(circle);
        g2.drawImage(resizedImage, 0, 0, null);
        g2.dispose();

        return circularImage;
    }

    private boolean checkIncheckOut() {
        String popUpHeader = null;
        String popUpMessage = null;
        Color color = null;
        try (Connection con = ConnectionProvider.getCon()) {
            if (con == null) {
                logger.error("Database connection failed in checkIncheckOut");
                showPopUpforCertainDuration("Database connection failed", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            try (PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM userattendance WHERE date = ? AND userid = ?")) {
                ps.setString(1, currentDate.format(dateFormatter));
                ps.setInt(2, Integer.parseInt(resultMap.get("id")));
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String checkOutDateTime = rs.getString("checkout");
                    if (checkOutDateTime != null) {
                        popUpMessage = "Already Checked Out For the Day";
                        popUpHeader = "Invalid Checkout";
                        showPopUpforCertainDuration(popUpMessage, popUpHeader, JOptionPane.ERROR_MESSAGE);
                        return false;
                    }

                    String checkInDateTime = rs.getString("checkin");
                    LocalDateTime checkInLocalDateTime = LocalDateTime.parse(checkInDateTime, dateTimeFormatter);
                    Duration duration = Duration.between(checkInLocalDateTime, currentDateTime);

                    long hours = duration.toHours();
                    long minutes = duration.minusHours(hours).toMinutes();
                    long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();

                    if (!(hours > 0 || (hours == 0 && minutes >= 10))) {
                        long remainingMinutes = 9 - minutes;
                        long remainingSeconds = 60 - seconds;
                        popUpMessage = String.format(
                            "Your work duration is less than 10 minutes\nYou can check out after: %d minutes and %d seconds",
                            remainingMinutes, remainingSeconds);
                        popUpHeader = "Duration Warning";
                        showPopUpforCertainDuration(popUpMessage, popUpHeader, JOptionPane.WARNING_MESSAGE);
                        return false;
                    }

                    try (PreparedStatement updatePs = con.prepareStatement(
                        "UPDATE userattendance SET checkout = ?, workduration = ? WHERE date = ? AND userid = ?")) {
                        updatePs.setString(1, currentDateTime.format(dateTimeFormatter));
                        updatePs.setString(2, hours + " Hours and " + minutes + " Minutes");
                        updatePs.setString(3, currentDate.format(dateFormatter));
                        updatePs.setInt(4, Integer.parseInt(resultMap.get("id")));
                        updatePs.executeUpdate();
                    }
                    popUpHeader = "Checkout";
                    popUpMessage = "Checked Out at " + currentDateTime.format(dateTimeFormatter) +
                                   "\nWork Duration: " + hours + " Hours and " + minutes + " Minutes";
                    color = Color.RED;
                } else {
                    try (PreparedStatement insertPs = con.prepareStatement(
                        "INSERT INTO userattendance (userid, date, checkin) VALUES (?, ?, ?)")) {
                        insertPs.setInt(1, Integer.parseInt(resultMap.get("id")));
                        insertPs.setString(2, currentDate.format(dateFormatter));
                        insertPs.setString(3, currentDateTime.format(dateTimeFormatter));
                        insertPs.executeUpdate();
                    }
                    popUpHeader = "CheckIn";
                    popUpMessage = "Checked In at " + currentDateTime.format(dateTimeFormatter);
                    color = Color.GREEN;
                }
            }

            lblChecIinCheckout.setHorizontalAlignment(JLabel.CENTER);
            lblChecIinCheckout.setText(popUpHeader);
            lblChecIinCheckout.setForeground(color);
            lblChecIinCheckout.setBackground(Color.DARK_GRAY);
            lblChecIinCheckout.setOpaque(true);

            showPopUpforCertainDuration(popUpMessage, popUpHeader, JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (SQLException ex) {
            logger.error("Error in checkIncheckOut: " + ex.getMessage(), ex);
            showPopUpforCertainDuration("Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (imagee != null) {
            g.drawImage(imagee, 0, 0, null);
        }
    }
}
