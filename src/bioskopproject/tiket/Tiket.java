
package bioskopproject.tiket;


public class Tiket {
    
    private String kode_pesanan;
    private String nama_pemesan;
    private String tiket_film;
    private String genre;
    private String sinopsis;
    private String waktu;
    private String harga_tiket;
    private String jumlah_pesan;
    private String uang_masuk;
    private String uang_keluar;
    private String jumlah_bayar;

    public String getKode_pesanan() {
        return kode_pesanan;
    }

    public void setKode_pesanan(String kode_pesanan) {
        this.kode_pesanan = kode_pesanan;
    }

    public String getNama_pemesan() {
        return nama_pemesan;
    }

    public void setNama_pemesan(String nama_pemesan) {
        this.nama_pemesan = nama_pemesan;
    }

    public String getTiket_film() {
        return tiket_film;
    }

    public void setTiket_film(String tiket_film) {
        this.tiket_film = tiket_film;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getHarga_tiket() {
        return harga_tiket;
    }

    public void setHarga_tiket(String harga_tiket) {
        this.harga_tiket = harga_tiket;
    }

    public String getJumlah_pesan() {
        return jumlah_pesan;
    }

    public void setJumlah_pesan(String jumlah_pesan) {
        this.jumlah_pesan = jumlah_pesan;
    }

    public String getUang_masuk() {
        return uang_masuk;
    }

    public void setUang_masuk(String uang_masuk) {
        this.uang_masuk = uang_masuk;
    }

    public String getUang_keluar() {
        return uang_keluar;
    }

    public void setUang_keluar(String uang_keluar) {
        this.uang_keluar = uang_keluar;
    }

    public String getJumlah_bayar() {
        return jumlah_bayar;
    }

    public void setJumlah_bayar(String jumlah_bayar) {
        this.jumlah_bayar = jumlah_bayar;
    }
}
