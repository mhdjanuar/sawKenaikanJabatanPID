package application.models;

/**
 *
 * @author mhdja
 */
public class KaryawanModel {
    private int id;
    private String nik;     // ✅ kolom baru
    private String name;
    private int usia;
    private String kontak;
    private String email;
    private String gender;
    private String alamat;
    private String jabatan;
    
    // === Getter & Setter untuk NIK ===
    /**
     * @return the nik
     */
    public String getNik() {
        return nik;
    }

    /**
     * @param nik the nik to set
     */
    public void setNik(String nik) {
        this.nik = nik;
    }

    // === Getter & Setter lainnya ===
    public int getUsia() {
        return usia;
    }

    public void setUsia(int usia) {
        this.usia = usia;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }
}
