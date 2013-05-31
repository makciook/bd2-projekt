import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;


public class glowneokno extends JFrame {
    private JDBC baza;
    private String[] zamowienia = { "Wejściowe", "Wyjściowe"};
    private String[] sql_zam    = { "wej", "wyj"};
    private String[] kl_zam     = { "dostawca", "klient"};

    public glowneokno() {
        setTitle("Magazyn BD2 2013");
        initComponents();
        this.setVisible(true);
        //baza = new JDBC("Maciek","Maciek","localhost","3306","bd2-baza");
        /**
         * Interfejs phpMyAdmin dostępny pod adresem:
         * https://sv18.vipserv.org:3010/phpmyadmin5/
         *
         * login i hasło jak w połączeniu poniżej:
         */
        baza = new JDBC("makciook_bd2", "Maciek", "mysql-788899.vipserv.org", "3306", "makciook_bd2");
        returnButton.setVisible(false);
        deleteButton_Elem.setVisible(false);
        editButton_Elem.setVisible(false);
        addButon_Elem.setVisible(false);

        // uzupelnij combobox zamowieniami
        for(String s : zamowienia) {
            zamowieniaBox.addItem(s);
        }

        //baza = new JDBC("bd2", "bd2", "46.167.245.192", "3306", "bd2-baza");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        try {
            /** default */
            String q = "SELECT zw.Id, Data, FORMAT(wartosc*100,2) AS Wartosc, zrealizowane, dostawca_Regon AS Dostawca, CONCAT(pr.Imie, CONCAT(' ', pr.Nazwisko)) AS Pracownik FROM zamowienia_wej zw" +
                    " JOIN pracownik pr ON pr.Pesel = zw.pracownik_pesel" +
                    " ORDER BY Id ASC";
            baza.getConnection();
            baza.executeQuery(q);
            this.mainTable.setModel(baza.getAsTableModel());
            mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            mainTable.setColumnSelectionAllowed(false);
            mainTable.setRowSelectionAllowed(true);
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        mainTable.addMouseListener(new MouseAdapter() {
            /** First click on table **/
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {

                }

            }
        });

        zamowieniaBox.addMouseListener(new MouseAdapter() {

        });

        returnButton.addMouseListener(new MouseAdapter() {
            /** Powrot **/
            public void mouseClicked(MouseEvent e) {

                try {
                    String q = "SELECT zw.Id, Data, FORMAT(wartosc*100,2) AS Wartosc, zrealizowane, "+kl_zam[zamowieniaBox.getSelectedIndex()]+"_Regon AS "+kl_zam[zamowieniaBox.getSelectedIndex()]+", CONCAT(pr.Imie, CONCAT(' ', pr.Nazwisko)) AS Pracownik FROM zamowienia_"+sql_zam[zamowieniaBox.getSelectedIndex()]+" zw" +
                            " JOIN pracownik pr ON pr.Pesel = zw.pracownik_pesel WHERE 1 = 1";


                    if(!fromTextField.getText().equals("YYYY-MM-DD"))
                        q += " AND zw.Data > '"+fromTextField.getText()+"'";
                    if(!toTextField.getText().equals("YYYY-MM-DD"))
                        q += " AND zw.Data < '"+toTextField.getText()+"'";

                    q += " ORDER BY zw.Id ASC";

                    baza.executeQuery(q);
                    mainTable.setModel(baza.getAsTableModel());
                    tableEnable(true);
                    returnButton.setVisible(false);
                    viewButton.setVisible(true);
                } catch(Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void tableEnable(boolean val) {
        //
        // mainTable.setEnabled(val);
        fakturaMenuitem.setEnabled(!val);
        deleteButton_Zam.setVisible(val);
        addButton.setVisible(val);
        editButton.setVisible(val);
        viewButton.setVisible(val);

        editButton_Elem.setVisible(!val);
        addButon_Elem.setVisible(!val);
        deleteButton_Elem.setVisible(!val);

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

    private void scrollPane1MouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void zamowieniaBoxActionPerformed(ActionEvent e) {
        //public void mouseClicked(MouseEvent e) {
        /** Combobox click **/
            JComboBox target = (JComboBox)e.getSource();
            int ind = target.getSelectedIndex();
            try {

                String q = "SELECT zw.Id, Data, FORMAT(wartosc*100,2) AS Wartosc, zrealizowane, "+kl_zam[zamowieniaBox.getSelectedIndex()]+"_Regon AS "+kl_zam[zamowieniaBox.getSelectedIndex()]+", CONCAT(pr.Imie, CONCAT(' ', pr.Nazwisko)) AS Pracownik FROM zamowienia_"+sql_zam[zamowieniaBox.getSelectedIndex()]+" zw" +
                        " JOIN pracownik pr ON pr.Pesel = zw.pracownik_pesel WHERE 1 = 1";


                if(!fromTextField.getText().equals("YYYY-MM-DD"))
                    q += " AND zw.Data > '"+fromTextField.getText()+"'";
                if(!toTextField.getText().equals("YYYY-MM-DD"))
                    q += " AND zw.Data < '"+toTextField.getText()+"'";

                q += " ORDER BY zw.Id ASC";
                baza.executeQuery(q);
                mainTable.setModel(baza.getAsTableModel());
                tableEnable(true);
            }catch(Exception ex) {
                System.out.println("Baza nie jest jeszcze gotowa! (zamowienia box click)");
            }
        //}
    }

    private void m_Manage_zaminActionPerformed(ActionEvent e) {
        this.zamowieniaBox.setSelectedIndex(0);
        tableEnable(true);
    }

    private void m_Manage_zamoutActionPerformed(ActionEvent e) {
        this.zamowieniaBox.setSelectedIndex(1);
        tableEnable(true);
    }

    private void menuItem12ActionPerformed(ActionEvent e) {
        /** Raport zrealizowane **/
        int ind = this.zamowieniaBox.getSelectedIndex();
        try {
            String q = "SELECT zw.Id, Data, FORMAT(wartosc*100,2) AS Wartosc, zrealizowane, "+kl_zam[zamowieniaBox.getSelectedIndex()]+"_Regon AS "+kl_zam[zamowieniaBox.getSelectedIndex()]+", CONCAT(pr.Imie, CONCAT(' ', pr.Nazwisko)) AS Pracownik FROM zamowienia_"+sql_zam[zamowieniaBox.getSelectedIndex()]+" zw" +
                    " JOIN pracownik pr ON pr.Pesel = zw.pracownik_pesel WHERE 1 = 1" +
                    " AND zw.zrealizowane ='T'";


            if(!fromTextField.getText().equals("YYYY-MM-DD"))
                q += " AND zw.Data > '"+fromTextField.getText()+"'";
            if(!toTextField.getText().equals("YYYY-MM-DD"))
                q += " AND zw.Data < '"+toTextField.getText()+"'";

            q += " ORDER BY zw.Id ASC";

            baza.executeQuery(q);
            mainTable.setModel(baza.getAsTableModel());
            tableEnable(true);
        }catch(Exception ex) {
            System.out.println("Baza nie jest jeszcze gotowa! (raport zrealizowane)");
        }
    }

    private void menuItem13ActionPerformed(ActionEvent e) {
        /** raport niezrealizowane */
        int ind = this.zamowieniaBox.getSelectedIndex();
        try {
            String q = "SELECT zw.Id, Data, FORMAT(wartosc*100,2) AS Wartosc, zrealizowane, "+kl_zam[zamowieniaBox.getSelectedIndex()]+"_Regon AS "+kl_zam[zamowieniaBox.getSelectedIndex()]+", CONCAT(pr.Imie, CONCAT(' ', pr.Nazwisko)) AS Pracownik FROM zamowienia_"+sql_zam[zamowieniaBox.getSelectedIndex()]+" zw" +
                    " JOIN pracownik pr ON pr.Pesel = zw.pracownik_pesel WHERE 1 = 1" +
                    " AND zw.zrealizowane ='N'";


            if(!fromTextField.getText().equals("YYYY-MM-DD"))
                q += " AND zw.Data > '"+fromTextField.getText()+"'";
            if(!toTextField.getText().equals("YYYY-MM-DD"))
                q += " AND zw.Data < '"+toTextField.getText()+"'";

            q += " ORDER BY zw.Id ASC";

            baza.executeQuery(q);
            mainTable.setModel(baza.getAsTableModel());
            tableEnable(true);
        }catch(Exception ex) {
            System.out.println("Baza nie jest jeszcze gotowa! (raport niezrealizowane)");
            System.out.println(ex.getMessage());
        }
    }

    private void button1ActionPerformed(ActionEvent e) {
        fromTextField.setText("YYYY-MM-DD");
        toTextField.setText("YYYY-MM-DD");
        int id = zamowieniaBox.getSelectedIndex();
        zamowieniaBox.setSelectedIndex(id);
        tableEnable(true);
    }

    private void filterButtonActionPerformed(ActionEvent e) {
        int id = zamowieniaBox.getSelectedIndex();
        zamowieniaBox.setSelectedIndex(id);
        tableEnable(true);
    }

    private void deleteButtonActionPerformed(ActionEvent e) {
        /** usuwanie */
        Object id = mainTable.getValueAt(mainTable.getSelectedRow(), 0);   // get id
        try {
            baza.executeUpdate("DELETE FROM pozycje_zam_"+sql_zam[zamowieniaBox.getSelectedIndex()]+" " +
                " WHERE zamowienia_"+sql_zam[zamowieniaBox.getSelectedIndex()]+"_Id ="+id);

            baza.executeUpdate("DELETE FROM zamowienia_"+sql_zam[zamowieniaBox.getSelectedIndex()]+"" +
                " WHERE Id = "+id);

            int id2 = zamowieniaBox.getSelectedIndex();
            zamowieniaBox.setSelectedIndex(id2);

            JOptionPane.showMessageDialog(null,"Poprawnie usunięto wybrany rekord!");

        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,"Nie udało się usunąć rekordu z nastepującego powodu: "+ex.getMessage());
            System.out.println(ex.getMessage());
        }

    }

    private void deleteButton_ElemActionPerformed(ActionEvent e) {
        /** usuwanie elementow zamowienia */
        Object ilosc = mainTable.getValueAt(mainTable.getSelectedRow(), 1);   // get id;
        Object czesci_Id = mainTable.getValueAt(mainTable.getSelectedRow(),2);
        Object zwejid = mainTable.getValueAt(mainTable.getSelectedRow(),0);
        String q = "DELETE FROM pozycje_zam_"+sql_zam[zamowieniaBox.getSelectedIndex()]+" WHERE Ilosc = "+ilosc+" AND czesci_Id = "+czesci_Id+" AND zamowienia_"+sql_zam[zamowieniaBox.getSelectedIndex()]+"_Id = "+zwejid;
        try {
            System.out.println(q);
            baza.executeUpdate(q);
            baza.executeQuery(baza.getLastQuery());
            mainTable.setModel(baza.getAsTableModel());
            /*int id2 = zamowieniaBox.getSelectedIndex();
            zamowieniaBox.setSelectedIndex(id2);    */
            JOptionPane.showMessageDialog(null,"Poprawnie usunięto wybrany rekord!");
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,"Nie udało się usunąć rekordu z nastepującego powodu: "+ex.getMessage());
            ex.getMessage();
        }
    }

    private void scrollPane1KeyTyped(KeyEvent e) {
        // TODO add your code here
    }

    private void editButtonActionPerformed(ActionEvent e) {
        /** edytuj */
        if(editButton.getText().equals("ZAKONCZ")) {
            editButton.setText("Edytuj");
            this.deleteButton_Zam.setEnabled(true);
            this.addButton.setEnabled(true);
            this.viewButton.setEnabled(true);
            String q = "UPDATE zamowienia_"+sql_zam[zamowieniaBox.getSelectedIndex()]+" SET Data = '"+mainTable.getValueAt(mainTable.getSelectedRow(),1)+"'" +
                    ", wartosc = REPLACE('"+mainTable.getValueAt(mainTable.getSelectedRow(),2)+"', ',', '')" +
                    ", zrealizowane = '"+mainTable.getValueAt(mainTable.getSelectedRow(),3)+"'" +
                    ", "+kl_zam[zamowieniaBox.getSelectedIndex()]+"_Regon = '"+mainTable.getValueAt(mainTable.getSelectedRow(),4)+"'" +
                    " WHERE Id = "+mainTable.getValueAt(mainTable.getSelectedRow(),0);
            System.out.println(q);
            try {
                baza.executeUpdate(q);
                baza.executeQuery(baza.getLastQuery());
                baza.getAsTableModel();
                JOptionPane.showMessageDialog(null,"Poprawnie zmieniono wybrany rekord!");
            } catch(Exception exx) {
                JOptionPane.showMessageDialog(null,"Nie udało się zmienić rekordu z nastepującego powodu: "+exx.getMessage());
                System.out.println(exx.getMessage());

            }
        }
        else {
            editButton.setText("ZAKONCZ");
            this.deleteButton_Zam.setEnabled(false);
            this.addButton.setEnabled(false);
            this.viewButton.setEnabled(false);
            ArrayList<Integer> al = new ArrayList();
            al.add(new Integer(1));
            al.add(new Integer(2));
            al.add(new Integer(3));
            al.add(new Integer(4));

            try {
                baza.executeQuery(baza.getLastQuery());
                mainTable.setModel(baza.getAsEditableTableModel(al, mainTable.getSelectedRow()));
            } catch(Exception ex) {
                System.out.println(ex.getMessage());
            }
        }


    }

    private void viewButtonActionPerformed(ActionEvent e) {
        /** wyswietl zamowienie */
        int row = mainTable.getSelectedRow();
        try {
            String q = "SELECT zw.ID As 'NR zamówienia', pzw.Ilosc AS Ilosc, pzw.czesci_Id AS 'Numer czesci', cz.Nazwa as Czesc, FORMAT(cz.wartosc*pzw.Ilosc*100,2) AS Wartosc"+
                    " FROM zamowienia_"+sql_zam[zamowieniaBox.getSelectedIndex()]+" zw " +
                    " JOIN pozycje_zam_"+sql_zam[zamowieniaBox.getSelectedIndex()]+" pzw ON zw.Id=pzw.zamowienia_"+sql_zam[zamowieniaBox.getSelectedIndex()]+"_Id" +
                    " JOIN czesci cz ON cz.Id = pzw.czesci_Id" +
                    //" JOIN "+kl_zam[zamowieniaBox.getSelectedIndex()]+" kl ON zw."+kl_zam[zamowieniaBox.getSelectedIndex()]+" = kl.Regon"+
                    " WHERE zw.Id="+mainTable.getValueAt(row,0);
            if(!fromTextField.getText().equals("YYYY-MM-DD"))
                q += " AND zw.Data > '"+fromTextField.getText()+"'";
            if(!toTextField.getText().equals("YYYY-MM-DD"))
                q += " AND zw.Data < '"+toTextField.getText()+"'";
            System.out.println(q);
            baza.executeQuery(q);
            mainTable.setModel(baza.getAsTableModel());

            returnButton.setVisible(true);
            tableEnable(false);
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    private void editButton_ElemActionPerformed(ActionEvent e) {
        /** edytuj element */
        if(editButton_Elem.getText().equals("ZAKONCZ")) {
            editButton_Elem.setText("Edytuj");
            deleteButton_Elem.setEnabled(true);
            addButon_Elem.setEnabled(true);
            String q = "UPDATE pozycje_zam_"+sql_zam[zamowieniaBox.getSelectedIndex()]+" SET Ilosc=" + mainTable.getValueAt(mainTable.getSelectedRow(),1)+
                    " WHERE zamowienia_"+sql_zam[zamowieniaBox.getSelectedIndex()]+"_Id = "+mainTable.getValueAt(mainTable.getSelectedRow(),0)+ "" +
                    " AND czesci_Id = "+mainTable.getValueAt(mainTable.getSelectedRow(),2);
            System.out.println(q);
            try {
                baza.executeUpdate(q);
                baza.executeQuery(baza.getLastQuery());
                mainTable.setModel(baza.getAsTableModel());
                JOptionPane.showMessageDialog(null,"Poprawnie zmieniono wybrany rekord!");
            } catch(Exception exx) {
                JOptionPane.showMessageDialog(null,"Nie udało się zmienić rekordu z nastepującego powodu: "+exx.getMessage());
                System.out.println(exx.getMessage());

            }
        }
        else {
            editButton_Elem.setText("ZAKONCZ");
            deleteButton_Elem.setEnabled(false);
            addButon_Elem.setEnabled(false);
            ArrayList<Integer> al = new ArrayList();
            al.add(new Integer(1));

            try {
                baza.executeQuery(baza.getLastQuery());
                mainTable.setModel(baza.getAsEditableTableModel(al, mainTable.getSelectedRow()));
            } catch(Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void addButtonActionPerformed(ActionEvent e) {
        /** dodaj zamowienie */
        if(addButton.getText().equals("Dodaj"))
        {
            try {
                this.editButton.setEnabled(false);
                this.deleteButton_Zam.setEnabled(false);
                ArrayList<Integer> x = new ArrayList();
                x.add(new Integer(1));
                x.add(new Integer(3));
                x.add(new Integer(4));
                x.add(new Integer(5));
                baza.executeQuery(baza.getLastQuery());
                DefaultTableModel tbmodel = (DefaultTableModel)baza.getAsEditableTableModel(x,0);
                tbmodel.insertRow(0,new Object[]{"","","","","",""});
                mainTable.setModel(tbmodel);
                addButton.setText("ZAKONCZ");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        else if(addButton.getText().equals("ZAKONCZ")) {
            this.editButton.setEnabled(true);
            this.deleteButton_Zam.setEnabled(true);
            String q="";
            if(zamowieniaBox.getSelectedIndex()==1)     // wyjsciowe
                q = "INSERT INTO zamowienia_"+sql_zam[zamowieniaBox.getSelectedIndex()]+" VALUES(" +
                        "NULL, '" +
                        mainTable.getValueAt(0,1)+"', " +(new Random().nextInt()+500)+
                        ",TRIM('"+mainTable.getValueAt(0,4)+"')," +
                        "(SELECT DISTINCT p.Pesel FROM pracownik p JOIN zamowienia_"+sql_zam[zamowieniaBox.getSelectedIndex()]+" z ON z.pracownik_Pesel =p.Pesel WHERE CONCAT(p.imie,' ',p.Nazwisko) = '"+mainTable.getValueAt(0,5)+"')," +
                        ""+(new Random().nextInt(100))+", NULL, '"+mainTable.getValueAt(0,3)+"')";
            else if(zamowieniaBox.getSelectedIndex()==0)      // wejsciowe
                q = "INSERT INTO zamowienia_"+sql_zam[zamowieniaBox.getSelectedIndex()]+"(" +
                        "Id, Data, nr_faktury_rozch, dostawca_regon, pracownik_Pesel,filia_Nr_filii, wartosc, zrealizowane" +
                        ") VALUES(" +
                        "NULL, '" +
                        mainTable.getValueAt(0,1)+"', " +(new Random().nextInt()+500)+
                        ",TRIM('"+mainTable.getValueAt(0,4)+"')," +
                        "(SELECT DISTINCT p.Pesel FROM pracownik p JOIN zamowienia_"+sql_zam[zamowieniaBox.getSelectedIndex()]+" z ON z.pracownik_Pesel =p.Pesel WHERE CONCAT(p.imie,' ',p.Nazwisko) = '"+mainTable.getValueAt(0,5)+"')," +
                        ""+(new Random().nextInt(100))+", NULL, '"+mainTable.getValueAt(0,3)+"')";
            try {
                System.out.println(q);
                baza.executeUpdate(q);
                JOptionPane.showMessageDialog(null,"Poprawnie dodano wybrany rekord!");
            }  catch(Exception e2) {
                JOptionPane.showMessageDialog(null,"Nie udało się dodać rekordu z nastepującego powodu: "+e2.getMessage());//To change body of catch statement use File | Settings | File Templates.
                DefaultTableModel tbmodel = (DefaultTableModel)baza.getAsTableModel();
                tbmodel.removeRow(0);
                mainTable.setModel(tbmodel);
                e2.printStackTrace();
            } finally {
                addButton.setText("Dodaj");
                try {
                    baza.executeQuery(baza.getLastQuery());
                    mainTable.setModel(baza.getAsTableModel());
                }catch(Exception eee) {}
            }
        }
    }

    private void addButon_ElemActionPerformed(ActionEvent e) {
        /** dodaj element zamowienia */
        String q = "INSERT INTO pozycje_zam_"+sql_zam[zamowieniaBox.getSelectedIndex()]+" VALUES("+mainTable.getValueAt(0,1)+"," +
                ""+mainTable.getValueAt(0,2)+"," +
                ""+mainTable.getValueAt(0,0)+")";
        if(addButon_Elem.getText().equals("Dodaj")) {
            this.editButton_Elem.setEnabled(false);
            this.deleteButton_Elem.setEnabled(false);
            try {
                ArrayList<Integer> x = new ArrayList();
                x.add(new Integer(1));
                x.add(new Integer(2));
                baza.executeQuery(baza.getLastQuery());
                DefaultTableModel tbmodel = (DefaultTableModel)baza.getAsEditableTableModel(x,0);
                tbmodel.insertRow(0,new Object[]{mainTable.getValueAt(1,0).toString(),"","",""});
                mainTable.setModel(tbmodel);
                addButon_Elem.setText("ZAKONCZ");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        else {
            this.editButton_Elem.setEnabled(true);
            this.deleteButton_Elem.setEnabled(true);
            addButon_Elem.setText("Dodaj");

            try {
                System.out.println(q);
                baza.executeUpdate(q);
                JOptionPane.showMessageDialog(null,"Poprawnie dodano wybrany rekord!");
            }  catch(Exception e2) {
                JOptionPane.showMessageDialog(null,"Nie udało się dodać rekordu z nastepującego powodu: "+e2.getMessage());//To change body of catch statement use File | Settings | File Templates.
                DefaultTableModel tbmodel = (DefaultTableModel)baza.getAsTableModel();
                tbmodel.removeRow(0);
                mainTable.setModel(tbmodel);
                e2.printStackTrace();
            } finally {

                try {
                    baza.executeQuery(baza.getLastQuery());
                }catch(Exception eee) {}
            }

        }
    }

    /**
     *
     mainTable = new JTable(){
         private static final long serialVersionUID = 1L;

         public boolean isCellEditable(int row, int column) {
            return false;
         };
     };

     */
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Maciek C
        menuBar1 = new JMenuBar();
        m_File = new JMenu();
        menuItem1 = new JMenuItem();
        menuItem2 = new JMenuItem();
        menuItem3 = new JMenuItem();
        menuItem4 = new JMenuItem();
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
        fakturaMenuitem = new JMenuItem();
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        scrollPane1 = new JScrollPane();
        mainTable = new JTable();
        zamowieniaBox = new JComboBox();
        returnButton = new JButton();
        addButton = new JButton();
        editButton = new JButton();
        deleteButton_Zam = new JButton();
        label1 = new JLabel();
        label2 = new JLabel();
        button1 = new JButton();
        fromTextField = new JTextField();
        toTextField = new JTextField();
        filterButton = new JButton();
        deleteButton_Elem = new JButton();
        viewButton = new JButton();
        editButton_Elem = new JButton();
        addButon_Elem = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== menuBar1 ========
        {

            //======== m_File ========
            {
                m_File.setText("Plik");

                //---- menuItem1 ----
                menuItem1.setText("Zapisz parametry po\u0142\u0105czenia");
                menuItem1.setEnabled(false);
                m_File.add(menuItem1);

                //---- menuItem2 ----
                menuItem2.setText("Wczytaj parametry po\u0142\u0105czenia");
                menuItem2.setEnabled(false);
                m_File.add(menuItem2);

                //---- menuItem3 ----
                menuItem3.setText("Zapisz widok do pliku HTML");
                menuItem3.setEnabled(false);
                m_File.add(menuItem3);

                //---- menuItem4 ----
                menuItem4.setText("Zako\u0144cz");
                m_File.add(menuItem4);
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
                m_Manage_zamin.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        m_Manage_zaminActionPerformed(e);
                    }
                });
                m_Manage.add(m_Manage_zamin);

                //---- m_Manage_zamout ----
                m_Manage_zamout.setText("Zam\u00f3wienia wyj\u015bciowe");
                m_Manage_zamout.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        m_Manage_zamoutActionPerformed(e);
                    }
                });
                m_Manage.add(m_Manage_zamout);

                //---- m_Manage_kategorie ----
                m_Manage_kategorie.setText("Kategorie");
                m_Manage_kategorie.setEnabled(false);
                m_Manage.add(m_Manage_kategorie);

                //---- m_Manage_czesci ----
                m_Manage_czesci.setText("Cz\u0119\u015bci");
                m_Manage_czesci.setEnabled(false);
                m_Manage.add(m_Manage_czesci);

                //---- m_Manage_lokaliz ----
                m_Manage_lokaliz.setText("Lokalizacje");
                m_Manage_lokaliz.setEnabled(false);
                m_Manage.add(m_Manage_lokaliz);

                //======== menu2 ========
                {
                    menu2.setText("Osoby");

                    //---- m_Manage_pracownicy ----
                    m_Manage_pracownicy.setText("Pracownicy");
                    m_Manage_pracownicy.setEnabled(false);
                    menu2.add(m_Manage_pracownicy);

                    //---- m_Manage_klienci ----
                    m_Manage_klienci.setText("Klienci");
                    m_Manage_klienci.setEnabled(false);
                    menu2.add(m_Manage_klienci);

                    //---- m_Manage_dostawcy ----
                    m_Manage_dostawcy.setText("Dostawcy");
                    m_Manage_dostawcy.setEnabled(false);
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
                menuItem12.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        menuItem12ActionPerformed(e);
                    }
                });
                menu4.add(menuItem12);

                //---- menuItem13 ----
                menuItem13.setText("Zam\u00f3wienia do realizacji");
                menuItem13.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        menuItem13ActionPerformed(e);
                    }
                });
                menu4.add(menuItem13);

                //---- fakturaMenuitem ----
                fakturaMenuitem.setText("Faktura zam\u00f3wienia");
                fakturaMenuitem.setEnabled(false);
                menu4.add(fakturaMenuitem);
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
                    scrollPane1.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            scrollPane1MouseClicked(e);
                        }
                    });
                    scrollPane1.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            scrollPane1KeyTyped(e);
                        }
                    });
                    scrollPane1.setViewportView(mainTable);
                }

                //---- zamowieniaBox ----
                zamowieniaBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        zamowieniaBoxActionPerformed(e);
                    }
                });

                //---- returnButton ----
                returnButton.setText("Powr\u00f3t");

                //---- addButton ----
                addButton.setText("Dodaj");
                addButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addButtonActionPerformed(e);
                    }
                });

                //---- editButton ----
                editButton.setText("Edytuj");
                editButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        editButtonActionPerformed(e);
                    }
                });

                //---- deleteButton_Zam ----
                deleteButton_Zam.setText("Usu\u0144");
                deleteButton_Zam.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        deleteButtonActionPerformed(e);
                    }
                });

                //---- label1 ----
                label1.setText("Od:");

                //---- label2 ----
                label2.setText("Do:");

                //---- button1 ----
                button1.setText("Wyczy\u015b\u0107");
                button1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        button1ActionPerformed(e);
                    }
                });

                //---- fromTextField ----
                fromTextField.setText("YYYY-MM-DD");

                //---- toTextField ----
                toTextField.setText("YYYY-MM-DD");

                //---- filterButton ----
                filterButton.setText("Zastosuj");
                filterButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        filterButtonActionPerformed(e);
                    }
                });

                //---- deleteButton_Elem ----
                deleteButton_Elem.setText("Usu\u0144");
                deleteButton_Elem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        deleteButton_ElemActionPerformed(e);
                    }
                });

                //---- viewButton ----
                viewButton.setText("Zobacz");
                viewButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        viewButtonActionPerformed(e);
                    }
                });

                //---- editButton_Elem ----
                editButton_Elem.setText("Edytuj");
                editButton_Elem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        editButton_ElemActionPerformed(e);
                    }
                });

                //---- addButon_Elem ----
                addButon_Elem.setText("Dodaj");
                addButon_Elem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addButon_ElemActionPerformed(e);
                    }
                });

                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(contentPanelLayout.createParallelGroup()
                                .addGroup(contentPanelLayout.createSequentialGroup()
                                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 724, GroupLayout.PREFERRED_SIZE)
                                    .addGap(29, 29, 29)
                                    .addGroup(contentPanelLayout.createParallelGroup()
                                        .addComponent(addButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(editButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(deleteButton_Elem, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                                        .addComponent(deleteButton_Zam, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                                        .addComponent(viewButton, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                                        .addComponent(editButton_Elem, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                                        .addComponent(addButon_Elem, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)))
                                .addGroup(contentPanelLayout.createSequentialGroup()
                                    .addComponent(zamowieniaBox, GroupLayout.PREFERRED_SIZE, 218, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(returnButton)
                                    .addGap(18, 18, 18)
                                    .addComponent(label1)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(fromTextField, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(label2)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(toTextField, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(filterButton, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(button1, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)))
                            .addGap(0, 0, 0))
                );
                contentPanelLayout.setVerticalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(zamowieniaBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(returnButton)
                                .addComponent(label1)
                                .addComponent(label2)
                                .addComponent(fromTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(toTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(filterButton)
                                .addComponent(button1))
                            .addGap(18, 18, 18)
                            .addGroup(contentPanelLayout.createParallelGroup()
                                .addGroup(contentPanelLayout.createSequentialGroup()
                                    .addComponent(viewButton)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(deleteButton_Zam)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(editButton)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(addButton)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(deleteButton_Elem)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(editButton_Elem)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(addButon_Elem))
                                .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 587, GroupLayout.PREFERRED_SIZE))
                            .addGap(17, 17, 17))
                );
            }
            dialogPane.add(contentPanel, BorderLayout.NORTH);
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
    private JMenuItem menuItem1;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JMenuItem menuItem4;
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
    private JMenuItem fakturaMenuitem;
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JScrollPane scrollPane1;
    private JTable mainTable;
    private JComboBox zamowieniaBox;
    private JButton returnButton;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton_Zam;
    private JLabel label1;
    private JLabel label2;
    private JButton button1;
    private JTextField fromTextField;
    private JTextField toTextField;
    private JButton filterButton;
    private JButton deleteButton_Elem;
    private JButton viewButton;
    private JButton editButton_Elem;
    private JButton addButon_Elem;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
