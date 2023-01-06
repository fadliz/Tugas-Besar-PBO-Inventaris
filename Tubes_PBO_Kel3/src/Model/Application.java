/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Classes.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author asama
 */
public class Application {

    private ArrayList<User> daftarUser = null;
    private ArrayList<Gudang> daftarGudang = null;
    int jumUser = 0;
    int jumGudang = 0;
    private Database db;
    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d");

    public Application() {
        db = new Database();
        //daftarUser = new ArrayList();
        daftarUser = db.readAllUser();
        //daftarGudang = new ArrayList();
        daftarGudang = db.readAllGudang();
        jumUser = daftarUser.size();
        jumGudang = daftarGudang.size();
    }

    public boolean cekNama(String name) {
        boolean benar = true;
        if (DIGIT_PATTERN.matcher(name).find()) {
            benar = false;
        }
        return benar;
    }

    public void addSupplier(int id, String nama, String username, String password) {
        if (jumUser < 100) {
            if (getSupplier(id) == null) {
                if (!(cariUsername(username))) {
                    if (cekNama(nama)) {
                        daftarUser.add(new Supplier(id, nama, username, password));
                        db.saveSupplier(id, nama, username, password);
                        jumUser = daftarUser.size();
                        System.out.println("Data berhasil disimpan");
                    } else {
                        System.out.println("Nama tidak valid");
                    }
                } else {
                    System.out.println("Username sudah digunakan");
                }
            } else {
                System.out.println("ID sudah digunakan");
            }
        }
    }

    public void addPetugas(int id, String nama, String username, String password) {
        if (jumUser < 100) {
            if (getPetugas(id) == null) {
                if (!(cariUsername(username))) {
                    if (cekNama(nama)) {
                        daftarUser.add(new Petugas(id, nama, username, password));
                        db.savePetugas(id, nama, username, password);
                        jumUser = daftarUser.size();
                        System.out.println("Data berhasil disimpan");
                    } else {
                        System.out.println("Nama tidak valid");
                    }
                } else {
                    System.out.println("Username sudah digunakan");
                }
            } else {
                System.out.println("ID sudah digunakan");
            }
        }
    }

    public void ubahSupplier(int id, String nama, String username, String password) {
        Supplier p = getSupplier(id);
        p.setNama(nama);
        p.setUsername(username);
        p.setPassword(password);
        db.updateSupplier(id, nama, username, password);
    }

    public void ubahPetugas(int id, String nama, String username, String password) {
        Petugas p = getPetugas(id);
        p.setNama(nama);
        p.setUsername(username);
        p.setPassword(password);
        db.updatePetugas(id, nama, username, password);
    }

    public Gudang ambilGudang(int n) {
        return daftarGudang.get(n);
    }

    public ArrayList<Gudang> getListGudang() {
        return daftarGudang;
    }

    public User getUser(int n) {
        return daftarUser.get(n);
    }

    public ArrayList<User> getListUser() {
        return daftarUser;
    }

    public Petugas getPetugas(int id) {
        for (int i = 0; i < jumUser; i++) {
            if (daftarUser.get(i) instanceof Petugas) {
                Petugas pt = (Petugas) daftarUser.get(i);
                if (pt.getIdPetugas() == id) {
                    return pt;
                }
            }
        }
        return null;
    }

    public Petugas getUserPetugas(String user) {
        for (int i = 0; i < jumUser; i++) {
            if (daftarUser.get(i) instanceof Petugas) {
                Petugas pt = (Petugas) daftarUser.get(i);
                if (pt.getUsername().equals(user)) {
                    return pt;
                }
            }
        }
        return null;
    }

    public int hitungSupplier() {
        int jumlah = 0;
        for (User i : daftarUser) {
            if (i instanceof Supplier) {
                jumlah++;
            }
        }
        return jumlah;
    }

    public String[] getIDSupplier() {
        String[] s = new String[hitungSupplier()];
        int j = 0;
        for (User i : daftarUser) {
            if (i instanceof Supplier) {
                s[j] = String.valueOf(((Supplier) i).getID());
                j++;
            }
        }
        return s;
    }

    public String[] getIDGudang() {
        String[] s = new String[daftarGudang.size()];
        int j = 0;
        for (Gudang i : daftarGudang) {
            s[j] = String.valueOf(i.getID());
            j++;
        }
        return s;
    }

    public Supplier getSupplier(int id) {
        for (int i = 0; i < jumUser; i++) {
            if (daftarUser.get(i) instanceof Supplier) {
                Supplier py = (Supplier) daftarUser.get(i);
                if (py.getID() == id) {
                    return py;
                }
            }
        }
        return null;
    }

    public Supplier getUserSupplier(String user) {
        for (int i = 0; i < jumUser; i++) {
            if (daftarUser.get(i) instanceof Supplier) {
                Supplier py = (Supplier) daftarUser.get(i);
                if (py.getUsername().equals(user)) {
                    return py;
                }
            }
        }
        return null;
    }

    public void deleteSupplier(int id) {
        int j = -1;
        for (int i = 0; i < jumUser; i++) {
            if (daftarUser.get(i) instanceof Supplier) {
                Supplier py = (Supplier) daftarUser.get(i);
                if (py.getID() == id) {
                    daftarUser.remove(i);
                    db.deleteSupplier(id);
                    jumUser = daftarUser.size();
                    break;
                }
            }
        }
    }

    public void deletePetugas(int id) {
        int j = -1;
        for (int i = 0; i < jumUser; i++) {
            if (daftarUser.get(i) instanceof Petugas) {
                Petugas pt = (Petugas) daftarUser.get(i);
                if (pt.getIdPetugas() == id) {
                    daftarUser.remove(i);
                    db.deletePetugas(id);
                    jumUser = daftarUser.size();
                    break;
                }
            }
        }
    }

    public int findUsername(String user) {
        for (User p : daftarUser) {
            if (p.getUsername().equals(user)) {
                return daftarUser.indexOf(p);
            }
        }
        return -1;
    }

    public void addGudang(int id) {
        daftarGudang.add(new Gudang(id));
        jumGudang = daftarGudang.size();
    }

    public void tambahGudang(int id, String nama) {
        daftarGudang.add(new Gudang(id, nama));
        db.saveGudang(id, nama);
        jumGudang = daftarGudang.size();
    }

    public Gudang getGudang(int id) {
        for (int i = 0; i < jumGudang; i++) {
            if (daftarGudang.get(i).getID() == id) {
                return daftarGudang.get(i);
            }
        }
        return null;
    }

    public void ubahGudang(int id, String nama) {
        db.updateGudang(id, nama);
        Gudang g = getGudang(id);
        g.setNama_gudang(nama);
    }

    public void deleteGudang(int id) {
        int j = -1;
        for (int i = 0; i < jumGudang; i++) {
            if (daftarGudang.get(i).getID() == id) {
                daftarGudang.remove(i);
                db.deleteGudang(id);
                jumGudang = daftarGudang.size();
                break;
            }
        }
    }

    private boolean cariUsername(String user) {
        for (int i = 0; i < jumUser; i++) {
            if (daftarUser.get(i).getUsername().equals(user)) {
                return true;
            }
        }
        return false;
    }

    public void menuPyTambahBrg(Supplier py, int id, String nama, int jumlah) {
        if (py.findBarang(id) == -1) {
            py.createBarang(id, nama, jumlah);
            db.saveBarang(py, id, nama, jumlah);
            System.out.println("Barang berhasil ditambahkan");
        } else {
            System.out.println("Maaf, kode barang sudah ada");
        }
    }

    public void menuPyUbahBrg(Supplier py, int id, int jumlah) {
        int j = py.findBarang(id);
        if (j != -1) {
            py.ubahBarang(id, jumlah);
            db.updateBarang(py, id, jumlah);
            System.out.println("Data barang telah diubah");
        } else {
            System.out.println("Data barang tidak ada");
        }
    }

    public void menuPyDeleteBrg(Supplier py, int id) {
        int j = py.findBarang(id);
        if (j != -1) {
            py.hapusBarang(id);
            db.deleteBarang(py, id);
            System.out.println("Data barang telah dihapus");
        } else {
            System.out.println("Data barang tidak ada");
        }
    }

    public void menuPyView(Supplier py) {
        py.view();
    }

    public void menuPtInputBrg(Gudang g, Barang b) {
        g.addBarang(b, b.getID());
        db.saveBarang(g, b, b.getID());
    }

    public void menuPtTambahBrg(Petugas pt, Supplier py, Gudang g, int id, int id2) {
        if (py.findBarang(id) != -1) {
            if (g.findBarang(id2) == -1) {
                Barang b = py.getBarang(py.findBarang(id));
                py.hapusBarang(id);
                menuPyDeleteBrg(py, id);
                pt.tambahBarang(g, b, id2);
                db.saveBarang(g, py.getBarang(py.findBarang(id)), id2);
                System.out.println("Barang berhasil ditambahkan");
            } else {
                System.out.println("ID Baru sudah digunakan");
            }
        } else {
            System.out.println("Barang tidak ada");
        }
    }

    public void menuPtEditBrg(Petugas pt, Gudang g, int id, int baik, int jumlah) {
        int i = g.findBarang(id);
        if (i != -1) {
            pt.ubahBarang(g, id, baik);
            db.updateBarang(g, id, baik, jumlah);
            System.out.println("Data sudah di-update");
        } else {
            System.out.println("Data barang tidak ada");
        }
    }

    public void menuPtDeleteBrg(Petugas pt, Gudang g, int id) {
        pt.hapusBarang(g, id);
        db.deleteDBBarang(g, id);
    }

    public void menuPtViewGudang(int id) {
        Gudang g = getGudang(id);
        if (g != null) {
            g.view();
        } else {
            System.out.println("Gudang tidak ada");
        }
    }

    public User login(String user, String pass) {
        boolean masuk = false;
        int i = 0;
        while (i < jumUser) {
            if ((daftarUser.get(i).getUsername()).equals(user)) {
                if ((daftarUser.get(i).getPassword()).equals(pass)) {
                    return daftarUser.get(i);
                }
            }
            i++;
        }
        return null;
    }

    public ArrayList<Barang> cariBarangGudang(String kunci, int idGudang) {
        return db.cariBarangGudang(kunci, idGudang);
    }

    public ArrayList<Gudang> getAllGudang() {
        return db.readAllGudang();
    }

    public int getJumBarangGudang() {
        return db.getJumBarangGudang();
    }
}
