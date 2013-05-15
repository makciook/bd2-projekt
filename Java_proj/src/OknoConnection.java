import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Wed May 15 16:04:21 CEST 2013
 */



/**
 * @author Maciek C
 */
public class OknoConnection extends JDialog {
    private JDBC bazunia;


    public OknoConnection(Frame owner, JDBC baza) {
        super(owner);
        initComponents();
        bazunia = baza;
        addr_username.setText( bazunia.getUsername() );
        addr_passwd.setText( bazunia.getPassword() );
        addr_host.setText( bazunia.getAddress() );
        addr_dbname.setText( bazunia.getName() );
        addr_port.setText( bazunia.getPort() );
    }

    public OknoConnection(Dialog owner) {
        super(owner);
        initComponents();
    }

    private void testujButtonActionPerformed(ActionEvent e) {
        String uname = bazunia.getUsername();
        String pas = bazunia.getPassword();
        String adr = bazunia.getAddress();
        String nam = bazunia.getName();
        String por = bazunia.getPort();

        bazunia.setUsername(this.addr_username.getText());
        bazunia.setPassword(this.addr_passwd.getText());
        bazunia.setAddress(this.addr_host.getText());
        bazunia.setName(this.addr_dbname.getText());
        bazunia.setPort(this.addr_port.getText());
        try {
            bazunia.getConnection();
            conn_ok.setEnabled(true);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,"Błąd podczas nawiązywania połączenia:\n"+ex.getMessage());
        }
        finally {  // resetujemy dane połączenia, to był tylko test, odpalamy z powrotem poprzednie połączenie
            try {
                bazunia.closeConnection();
            } catch(Exception exx) {
                exx.printStackTrace();
            }
            bazunia.setUsername(uname);
            bazunia.setPassword(pas);
            bazunia.setAddress(adr);
            bazunia.setName(nam);
            bazunia.setPort(por);
            try {
                bazunia.getConnection();
            } catch(Exception exx) {
                exx.printStackTrace();
            }
        }
    }

    private void conn_okActionPerformed(ActionEvent e) {
        bazunia.setUsername(this.addr_username.getText());
        bazunia.setPassword(this.addr_passwd.getText());
        bazunia.setAddress(this.addr_host.getText());
        bazunia.setName(this.addr_dbname.getText());
        bazunia.setPort(this.addr_port.getText());
        try {
            bazunia.getConnection();
            conn_ok.setEnabled(true);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,"Błąd podczas nawiązywania połączenia:\n"+ex.getMessage());
        }
    }

    private void conn_cancelActionPerformed(ActionEvent e) {
        this.setVisible(false);
        this.dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Maciek C
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        label4 = new JLabel();
        addr_username = new JTextField();
        addr_host = new JTextField();
        addr_dbname = new JTextField();
        label5 = new JLabel();
        addr_port = new JTextField();
        addr_passwd = new JPasswordField();
        testujButton = new JButton();
        label6 = new JLabel();
        buttonBar = new JPanel();
        conn_ok = new JButton();
        conn_cancel = new JButton();

        //======== this ========
        setTitle("Ustaw dane po\u0142\u0105czenia");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

            // JFormDesigner evaluation mark
            dialogPane.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {

                //---- label1 ----
                label1.setText("Username");

                //---- label2 ----
                label2.setText("Password");

                //---- label3 ----
                label3.setText("Address");

                //---- label4 ----
                label4.setText("DB name");

                //---- label5 ----
                label5.setText(":");

                //---- testujButton ----
                testujButton.setText("Testuj");
                testujButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        testujButtonActionPerformed(e);
                    }
                });

                //---- label6 ----
                label6.setText("jdbc:mysql://");

                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(contentPanelLayout.createParallelGroup()
                                .addGroup(contentPanelLayout.createSequentialGroup()
                                    .addGroup(contentPanelLayout.createParallelGroup()
                                        .addGroup(contentPanelLayout.createSequentialGroup()
                                            .addComponent(label2)
                                            .addGap(18, 18, 18)
                                            .addComponent(addr_passwd, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(contentPanelLayout.createSequentialGroup()
                                            .addGroup(contentPanelLayout.createParallelGroup()
                                                .addComponent(label1)
                                                .addComponent(label3)
                                                .addComponent(label4))
                                            .addGap(18, 18, 18)
                                            .addGroup(contentPanelLayout.createParallelGroup()
                                                .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(addr_username, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                                    .addComponent(addr_dbname, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE))
                                                .addGroup(contentPanelLayout.createSequentialGroup()
                                                    .addComponent(label6)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(addr_host, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(label5, GroupLayout.PREFERRED_SIZE, 9, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                    .addComponent(addr_port, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)))))
                                    .addContainerGap(60, Short.MAX_VALUE))
                                .addGroup(GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                                    .addGap(0, 226, Short.MAX_VALUE)
                                    .addComponent(testujButton, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
                                    .addGap(83, 83, 83))))
                );
                contentPanelLayout.setVerticalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addGap(11, 11, 11)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label1)
                                .addComponent(addr_username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label2)
                                .addComponent(addr_passwd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label3)
                                .addComponent(label6)
                                .addComponent(addr_host, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label5)
                                .addComponent(addr_port, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label4)
                                .addComponent(addr_dbname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                            .addComponent(testujButton))
                );
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 0.0};

                //---- conn_ok ----
                conn_ok.setText("OK");
                conn_ok.setEnabled(false);
                conn_ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        conn_okActionPerformed(e);
                    }
                });
                buttonBar.add(conn_ok, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- conn_cancel ----
                conn_cancel.setText("Cancel");
                conn_cancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        conn_cancelActionPerformed(e);
                    }
                });
                buttonBar.add(conn_cancel, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Maciek C
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JTextField addr_username;
    private JTextField addr_host;
    private JTextField addr_dbname;
    private JLabel label5;
    private JTextField addr_port;
    private JPasswordField addr_passwd;
    private JButton testujButton;
    private JLabel label6;
    private JPanel buttonBar;
    private JButton conn_ok;
    private JButton conn_cancel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
