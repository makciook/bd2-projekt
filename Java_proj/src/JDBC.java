/**
 * Created with IntelliJ IDEA.
 * User: maciek
 * Date: 15.05.13
 * Time: 15:54
 * To change this template use File | Settings | File Templates.
 */
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class JDBC {
    private static Connection connection;
    private Statement stmt;
    private ResultSet rs;

    private String url;// =
    private String login;// = "root";
    private String haslo;// = ""
    private String address,port, name;

    public JDBC(String login_, String haslo_, String address_, String port_, String name_) { connection = null; login=login_; haslo=haslo_; address = address_; port=port_; name = name_; }
    public JDBC() { connection = null; }

    public String getUsername() { return login; }
    public String getPassword() { return haslo; }
    public String getAddress() { return address; }
    public String getPort() { return port; }
    public String getName() { return name; }
    public ResultSet getResultSet() { return rs; }

    public void setUsername(String u) { if(!u.isEmpty()) login = u; }
    public void setPassword(String p) { if(!p.isEmpty()) haslo = p; }
    public void setAddress(String a) { if(!a.isEmpty()) address = a; }
    public void setPort(String pp) { if(!pp.isEmpty()) port = pp; }
    public void setName(String n) { if(!n.isEmpty()) name = n; }

    private String lastquery;

    public void resetData() {
        url = null;
        connection = null;
        stmt = null;
        rs = null;
    }

    /**
     * Get's an object from the resultset
     * @param name  Column name
     * @see #rs
     * @return      Result item
     */
    public Object getNextResult(String name) {
        try {
            if(rs.isAfterLast()) return null;
            Object ret = rs.getObject(name);
            rs.next();
            return ret;
        } catch(Exception e) {
            System.out.println("getNextResult exception... "+e.getMessage());
            return -1;
        }
    }

    /**
     * Get's objects from the dataset.
     * @see #rs
     * @param name  List of column names
     * @return      Result item list
     */
    public Object[] getNextResult(String[] name) {
        try {
            Object[] ret = new Object[name.length];
            if(rs.isAfterLast()) return null;
            for(int i=0;i<ret.length;i++) {
                try {
                    ret[i] = rs.getObject(name[i]);
                } catch(Exception e) {
                    ret[i] = name[i];
                }
            }
            rs.next();
            return ret;
        } catch(Exception e) {
            return null;
        }
    }

    public boolean hasNextResult() {
        try {
            if(rs.isLast()) return false;
            return true;
        } catch(Exception e) {
            System.out.println("Blad w hasNextResult");
            return false;
        }
    }

    /**
     * Get's number of results stored in the dataset
     * @return
     */
    public int getResultNumber() {
        try {
            rs.last();
            int ret = rs.getRow();
            rs.first();
            return ret;
        } catch(Exception e) {
            return -1;
        }
    }

    public void createStatement() throws SQLException {
        if(connection!=null && !connection.isClosed()) {
            stmt = connection.createStatement();
        }
    }

    public int getIntResult(String colName) throws SQLException {
        return rs.getInt(colName);
    }

    public String getStringResult(String colName) throws SQLException {
        return rs.getString(colName);
    }

    public void executeQuery(String query) throws SQLException {
        lastquery = query;
        rs = stmt.executeQuery(query);

    }

    public int executeUpdate(String query) throws SQLException {
        return stmt.executeUpdate(query);
    }

    /**
     * Initiates a connection and returns it's instance.<br>
     * Uses url, address, port, name, login and haslo
     * @return
     * @see #connection
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        //"jdbc:mysql://localhost:3306/bd2-baza?characterEncoding=utf8";

        try {
            url = "jdbc:mysql://";
            Class.forName("com.mysql.jdbc.Driver");
            url+=(address+":"+port+"/"+name);
            if(haslo==null || haslo.isEmpty()) haslo="";
            connection = DriverManager.getConnection(url,login,haslo);
            this.createStatement();
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
        }

        return connection;
    }

    public String getLastQuery() {
        return lastquery;
    }

    public boolean closeConnection()
            throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            return true;
        }
        return false;
    }

    public TableModel getAsTableModel() {
        try {

            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            Vector columnNames = new Vector();

            // Get the column names
            for (int column = 0; column < numberOfColumns; column++) {
                columnNames.addElement(metaData.getColumnLabel(column + 1));
            }

            // Get all rows.
            Vector rows = new Vector();

            while (rs.next()) {
                Vector newRow = new Vector();

                for (int i = 1; i <= numberOfColumns; i++) {
                    newRow.addElement(rs.getObject(i));
                }

                rows.addElement(newRow);
            }



            return new DefaultTableModel(rows, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }

            };
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }


    public TableModel getAsEditableTableModel(final ArrayList<Integer> cols, final int rzad) {
        try {

            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            Vector columnNames = new Vector();

            // Get the column names
            for (int column = 0; column < numberOfColumns; column++) {
                columnNames.addElement(metaData.getColumnLabel(column + 1));
            }

            // Get all rows.
            Vector rows = new Vector();

            while (rs.next()) {
                Vector newRow = new Vector();

                for (int i = 1; i <= numberOfColumns; i++) {
                    newRow.addElement(rs.getObject(i));
                }

                rows.addElement(newRow);
            }

            DefaultTableModel model = new DefaultTableModel(rows, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    if( cols.contains( new Integer(column) ) && rzad == row)
                        return true;
                    else return false;
                }
            };

            return model;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
