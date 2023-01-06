/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author asama
 */
public class Petugas extends User {

    private int idPetugas;

    public Petugas(int id, String nama, String username, String password) {
        super(nama, username, password);
        idPetugas = id;
    }

    /**
     * @return the idPetugas
     */
    public int getIdPetugas() {
        return idPetugas;
    }

    /**
     * @param idPetugas the idPetugas to set
     */
    public void setIdPetugas(int idPetugas) {
        this.idPetugas = idPetugas;
    }

    public void tambahBarang(Gudang g, Barang b, int id2) {
        g.addBarang(b, id2);
    }

    public void ubahBarang(Gudang g, int n, int baik) {
        g.getBarang(n).updateKondisi(baik);
    }

    public void hapusBarang(Gudang g, int id) {
        if (g.getBarang(id).getID() == id) {
            g.deleteBarang(id);
        } else {
            System.out.println("\nBarang dengan id tsb tidak ada");
        }
    }
}
