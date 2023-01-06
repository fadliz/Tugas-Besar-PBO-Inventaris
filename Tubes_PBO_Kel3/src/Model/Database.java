/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Classes.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mysql.jdbc.Driver;
import java.util.ArrayList;

/**
 *
 * @author asama
 */
public class Database {

    private final String jdbcDriver = "com.mysql.jdbc.Driver";
    private final String server = "jdbc:mysql://localhost/inventaris";//nama database
    private final String dbuser = "root";
    private final String dbpasswd = "";
    Statement statement;
    Connection connection;
    ResultSet rs;

    public Database() {
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.connect();

    }

    public final void connect() { //method untuk mengkoneksikan database 
        try {
            connection = (Connection) DriverManager.getConnection(server, dbuser, dbpasswd);
            System.out.println("Connected Succesfully");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Did not Connect");
        }
    }

    public void disconnect() {
        try {
            connection.close();
            Statement stmt = connection.createStatement();
            stmt.close();
            System.out.println("Connection to Database Severed");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Disconnect Failed");
        }
    }

    public boolean manipulate(String query) {
        boolean cek = false;
        try {
            int row;
            statement = connection.createStatement();
            row = statement.executeUpdate(query);
            if (row > 0) {
                cek = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cek;
    }

    public ResultSet getData(String SQLString) {
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(SQLString);
        } catch (SQLException ex) {
            System.out.println("Error -> " + ex.getMessage());
        }
        return rs;
    }

    public void query(String SQLString) throws SQLException {
        try {
            statement = connection.createStatement();
            statement.executeUpdate(SQLString);
        } catch (SQLException c) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, c);
        }
    }

    public ArrayList<Gudang> readAllGudang() {
        ArrayList<Gudang> daftarGudang = new ArrayList();
        String s = "Select id_gudang, nama_gudang from gudang";
        ResultSet rs1 = getData(s);
        try {
            while (rs1.next()) {
                Gudang g = new Gudang(rs1.getInt("id_gudang"), rs1.getString("nama_gudang"));
                s = "Select id_barang, nama_barang, jumlah, kondisi_baik from barang_gudang where id_gudang = " + rs1.getInt("id_gudang");
                ResultSet rs2 = getData(s);
                while (rs2.next()) {
                    Barang b = new Barang(rs2.getInt("id_barang"), rs2.getString("nama_barang"), rs2.getInt("jumlah"));
                    g.addBarangFromDatabase(b, rs2.getInt("kondisi_baik"));
                }
                daftarGudang.add(g);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        return daftarGudang;
    }

    public ArrayList<User> readAllUser() {
        ArrayList<User> daftarUser = new ArrayList();
        String s = "Select id_petugas, nama, username, password from petugas";
        ResultSet rs1 = getData(s);
        try {
            while (rs1.next()) {
                Petugas pt = new Petugas(rs1.getInt("id_petugas"), rs1.getString("nama"), rs1.getString("username"), rs1.getString("password"));
                daftarUser.add(pt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        s = "Select id_penyedia, nama, username, password from penyedia";
        rs1 = getData(s);
        try {
            while (rs1.next()) {
                Supplier py = new Supplier(rs1.getInt("id_penyedia"), rs1.getString("nama"), rs1.getString("username"), rs1.getString("password"));
                s = "Select id_barang, nama_barang, jumlah from barang_penyedia where id_penyedia = " + rs1.getInt("id_penyedia");
                ResultSet rs2 = getData(s);
                while (rs2.next()) {
                    py.createBarang(rs2.getInt("id_barang"), rs2.getString("nama_barang"), rs2.getInt("jumlah"));
                }
                daftarUser.add(py);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return daftarUser;
    }

    public void savePetugas(int id, String nama, String user, String pass) {
        String s = "insert into petugas values(" + id + ",'" + nama
                + "','" + user + "','" + pass + "')";
        try {
            query(s);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void saveSupplier(int id, String nama, String user, String pass) {
        String s = "insert into penyedia values(" + id + ",'" + nama
                + "','" + user + "','" + pass + "')";
        try {
            query(s);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void saveGudang(int id) {
        String s = "insert into gudang values(" + id + ",null)";
        try {
            query(s);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void saveGudang(int id, String nama) {
        String s = "insert into gudang values(" + id + ",'" + nama + "')";
        try {
            query(s);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void deletePetugas(int id) {
        String s = "delete from petugas where id_petugas = " + id;
        try {
            query(s);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void deleteSupplier(int id) {
        String s = "delete from penyedia where id_penyedia= " + id;
        try {
            query(s);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void deleteGudang(int id) {
        String s = "delete from gudang where id_gudang = " + id;
        try {
            query(s);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void updateSupplier(int id, String nama, String user, String pass) {
        String s = "update penyedia set nama = '" + nama + "', username = '" + user
                + "', password = '" + pass + "' where id_penyedia = " + id;
        try {
            query(s);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void updatePetugas(int id, String nama, String user, String pass) {
        String s = "update Petugas set nama = '" + nama + "', username = '" + user
                + "', password = '" + pass + "' where id_Petugas = " + id;
        try {
            query(s);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void updateGudang(int id, String nama) {
        String s = "update gudang set nama_gudang = '" + nama + "' where id_Gudang = " + id;
        try {
            query(s);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void saveBarang(Supplier py, int id, String nama, int jumlah) {
        String s = "insert into barang_penyedia values(" + py.getID() + "," + id
                + ",'" + nama + "'," + jumlah + ")";
        try {
            query(s);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void updateBarang(Supplier py, int id, int jumlah) {
        String s = "update barang_penyedia set jumlah = " + jumlah + " where id_barang = " + id + " and id_penyedia = " + py.getID();
        try {
            query(s);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void deleteBarang(Supplier py, int id) {
        String s = "delete from barang_penyedia where id_barang = " + id + " and id_penyedia = " + py.getID();
        try {
            query(s);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void saveBarang(Gudang g, Barang b, int id2) {
        String s = "insert into barang_gudang values(" + g.getID() + "," + id2
                + ",'" + b.getNama() + "'," + b.getJumlah() + "," + b.getJumlah() + ",0)";
        try {
            query(s);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void updateBarang(Gudang g, int id, int baik, int jumlah) {
        String s = "update barang_gudang set kondisi_baik = '" + baik
                + "' , kondisi_buruk = '" + (jumlah - baik) + "' where id_barang = " + id + " and id_gudang = " + g.getID();
        try {
            query(s);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void deleteDBBarang(Gudang g, int id) {
        String s = "delete from barang_gudang where id_barang = " + id + " and id_gudang = " + g.getID();
        try {
            query(s);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public ArrayList<Barang> cariBarangGudang(String kunci, int idGudang) {
        ArrayList<Barang> db = new ArrayList();
        String s = "select * from barang_gudang where (id_barang = '" + kunci + "' or nama_barang = '" + kunci + "' or jumlah = '" + kunci
                + "') and id_gudang = " + idGudang;
        rs = getData(s);
        try {
            while (rs.next()) {
                Barang b = new Barang(rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5));
                db.add(b);
            }
        } catch (SQLException e) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, e);
        }
        return db;
    }

    public int getJumBarangGudang() {
        String s = "select count(*) from barang_gudang";
        rs = getData(s);
        try {
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, e);
        }
        return 0;
    }
}
