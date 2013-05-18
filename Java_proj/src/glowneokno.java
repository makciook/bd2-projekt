import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

public class glowneokno extends JFrame {
    private JDBC baza;

    public glowneokno() {
        setTitle("Magazyn BD2 2013");
        initComponents();
        this.setVisible(true);
        //baza = new JDBC("","","localhost","3306","bd2-baza");
        baza = new JDBC("bd2", "bd2", "46.167.245.192", "3306", "bd2-baza");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        try {
            baza.executeQuery("SELECT * FROM dostawca");
            this.mainTable.setModel(baza.getAsTableModel());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void m_DB_setDataActionPerformed(ActionEvent e) {
        OknoConnection oknocon = new OknoConnection(this, baza);
        oknocon.setVisible(true);
    }

    private void m_DB_connectActionPerformed(ActionEvent e) {
        try {
            baza.getConnection();
            JOptionPane.showMessageDialog(null,"Połączono!");
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(null,"Błąd podczas łączenia\nSprawdź dane i spróbuj ponownie\n"+ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void m_DB_disconnectActionPerformed(ActionEvent e) {
        try {
            baza.closeConnection();
            JOptionPane.showMessageDialog(null,"Rozłączono poprawnie");
        } catch(SQLException ex) {
            JOptionPane.showMessageDialog(null,"Błąd podczas rozłączania:\n"+ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Maciek C
        menuBar1 = new JMenuBar();
        m_File = new JMenu();
        m_DB = new JMenu();
        m_DB_setData = new JMenuItem();
        m_DB_connect = new JMenuItem();
        m_DB_disconnect = new JMenuItem();
        m_Manage = new JMenu();
        m_Manage_zamin = new JMenuItem();
        m_Manage_zamout = new JMenuItem();
        m_Manage_kategorie = new JMenuItem();
        m_Manage_czesci = new JMenuItem();
        m_Manage_lokaliz = new JMenuItem();
        menu2 = new JMenu();
        m_Manage_pracownicy = new JMenuItem();
        m_Manage_klienci = new JMenuItem();
        m_Manage_dostawcy = new JMenuItem();
        menu4 = new JMenu();
        menuItem12 = new JMenuItem();
        menuItem13 = new JMenuItem();
        menuItem14 = new JMenuItem();
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        scrollPane1 = new JScrollPane();
        mainTable = new JTable();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== menuBar1 ========
        {

            //======== m_File ========
            {
                m_File.setText("Plik");
            }
            menuBar1.add(m_File);

            //======== m_DB ========
            {
                m_DB.setText("Baza danych");

                //---- m_DB_setData ----
                m_DB_setData.setText("Ustaw dane po\u0142\u0105czenia");
                m_DB_setData.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        m_DB_setDataActionPerformed(e);
                    }
                });
                m_DB.add(m_DB_setData);

                //---- m_DB_connect ----
                m_DB_connect.setText("Po\u0142\u0105cz");
                m_DB_connect.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        m_DB_connectActionPerformed(e);
                    }
                });
                m_DB.add(m_DB_connect);

                //---- m_DB_disconnect ----
                m_DB_disconnect.setText("Roz\u0142\u0105cz");
                m_DB_disconnect.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        m_DB_disconnectActionPerformed(e);
                    }
                });
                m_DB.add(m_DB_disconnect);
            }
            menuBar1.add(m_DB);

            //======== m_Manage ========
            {
                m_Manage.setText("Zarz\u0105dzaj");

                //---- m_Manage_zamin ----
                m_Manage_zamin.setText("Zam\u00f3wienia wej\u015bciowe");
                m_Manage.add(m_Manage_zamin);

                //---- m_Manage_zamout ----
                m_Manage_zamout.setText("Zam\u00f3wienia wyj\u015bciowe");
                m_Manage.add(m_Manage_zamout);

                //---- m_Manage_kategorie ----
                m_Manage_kategorie.setText("Kategorie");
                m_Manage.add(m_Manage_kategorie);

                //---- m_Manage_czesci ----
                m_Manage_czesci.setText("Cz\u0119\u015bci");
                m_Manage.add(m_Manage_czesci);

                //---- m_Manage_lokaliz ----
                m_Manage_lokaliz.setText("Lokalizacje");
                m_Manage.add(m_Manage_lokaliz);

                //======== menu2 ========
                {
                    menu2.setText("Osoby");

                    //---- m_Manage_pracownicy ----
                    m_Manage_pracownicy.setText("Pracownicy");
                    menu2.add(m_Manage_pracownicy);

                    //---- m_Manage_klienci ----
                    m_Manage_klienci.setText("Klienci");
                    menu2.add(m_Manage_klienci);

                    //---- m_Manage_dostawcy ----
                    m_Manage_dostawcy.setText("Dostawcy");
                    menu2.add(m_Manage_dostawcy);
                }
                m_Manage.add(menu2);
            }
            menuBar1.add(m_Manage);

            //======== menu4 ========
            {
                menu4.setText("Raporty");

                //---- menuItem12 ----
                menuItem12.setText("Zam\u00f3wienia zrealizowane");
                menu4.add(menuItem12);

                //---- menuItem13 ----
                menuItem13.setText("Zam\u00f3wienia do realizacji");
                menu4.add(menuItem13);

                //---- menuItem14 ----
                menuItem14.setText("Faktura zam\u00f3wienia");
                menu4.add(menuItem14);
            }
            menuBar1.add(menu4);
        }
        setJMenuBar(menuBar1);

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

                //======== scrollPane1 ========
                {
                    scrollPane1.setViewportView(mainTable);
                }

                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 724, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(145, Short.MAX_VALUE))
                );
                contentPanelLayout.setVerticalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                            .addContainerGap(46, Short.MAX_VALUE)
                            .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 549, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                );
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- okButton ----
                okButton.setText("OK");
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("Cancel");
                buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
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
    private JMenuBar menuBar1;
    private JMenu m_File;
    private JMenu m_DB;
    private JMenuItem m_DB_setData;
    private JMenuItem m_DB_connect;
    private JMenuItem m_DB_disconnect;
    private JMenu m_Manage;
    private JMenuItem m_Manage_zamin;
    private JMenuItem m_Manage_zamout;
    private JMenuItem m_Manage_kategorie;
    private JMenuItem m_Manage_czesci;
    private JMenuItem m_Manage_lokaliz;
    private JMenu menu2;
    private JMenuItem m_Manage_pracownicy;
    private JMenuItem m_Manage_klienci;
    private JMenuItem m_Manage_dostawcy;
    private JMenu menu4;
    private JMenuItem menuItem12;
    private JMenuItem menuItem13;
    private JMenuItem menuItem14;
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JScrollPane scrollPane1;
    private JTable mainTable;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
