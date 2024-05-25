package bioskopproject.tiket;

import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import bioskopproject.utilities.Koneksi;
import bioskopproject.home.HomeView;
import bioskopproject.tiket.TiketView;
import static bioskopproject.tiket.TiketView.GENRE;
import static bioskopproject.tiket.TiketView.HARGATIKET;
import static bioskopproject.tiket.TiketView.JUMLAHBAYAR;
import static bioskopproject.tiket.TiketView.JUMLAHPESAN;
import static bioskopproject.tiket.TiketView.KEMBALIAN;
import static bioskopproject.tiket.TiketView.KODEPESANAN;
import static bioskopproject.tiket.TiketView.MASUKKANUANG;
import static bioskopproject.tiket.TiketView.NAMAPEMESAN;
import static bioskopproject.tiket.TiketView.SINOPSIS;
import static bioskopproject.tiket.TiketView.TEXTPRINT;
import static bioskopproject.tiket.TiketView.TIKETFILM;
import static bioskopproject.tiket.TiketView.WAKTU;
import java.sql.Connection;

public class TiketController {
    int times, jmlhbeli, jmlhhitung, bayar, kembalian;
    
    private final TiketTableModel stm = new TiketTableModel();
    
    public void setMaximumFrame(TiketView tv) {
       try {
           tv.setMaximum(true);
       } catch(Exception error) {
           System.err.println("Error at TiketController-setMaximumFrame, details : "+error.toString());
           JOptionPane.showMessageDialog(tv, "Error at TiketController-setMaximumFrame, details : "+error.toString());
       }
     }
    
    /*
    Jenis : METHODE
    NAMA : setUndercoreted
    DESKRIPSI : Method ini digunakan untuk membuat TiketView tidak memiliki dekorasi pada bagian atasnya
    */
    public void setUndercoreted(TiketView tv){
        try {
            tv.putClientProperty("JInternalFrame.isPallete", Boolean.TRUE);
            BasicInternalFrameUI basicInternalFrameUI = (BasicInternalFrameUI) tv.getUI();
            basicInternalFrameUI.setNorthPane(null);
        } catch (Exception error){
            System.err.println("Error at TiketController-setUndercoreted, details : "+error.toString());
            JOptionPane.showMessageDialog(tv, "Error at TiketController-setUndercoreted, details : "+error.toString());
        }
    }
    
    public void setTableModel(TiketView sv){
        try {
            TiketView.tableTiket.setModel(stm);
        } catch (Exception error) {
            System.err.println("Error at TiketController-setTableModel, details : "+error.toString());
            JOptionPane.showMessageDialog(sv, "Error at TiketController-setTableModel, details : "+error.toString());
        }
    }
    
    /*
    Jenis : Methode
    Nama : loadData
    Deskripsi : Method ini digunakan untuk mengambil data Tiket yang ada pada database dan disajikan pada tableSiswa
    */
    public void loadData(TiketView tv){
        try {
            Statement statement;
            ResultSet rs;
            
            String sqlSelect = "SELECT * FROM tb_tiket ORDER BY kode_pesanan ASC";
            
            statement = Koneksi.getConnection().createStatement();
            rs = statement.executeQuery(sqlSelect);
            
            List<Tiket> list = new ArrayList<>();
            while (rs.next()) {
                
                Tiket t = new Tiket();
                t.setKode_pesanan(rs.getString("kode_pesanan"));
                t.setNama_pemesan(rs.getString("nama_pemesan"));
                t.setTiket_film(rs.getString("tiket_film"));
                t.setWaktu(rs.getString("waktu"));
                t.setHarga_tiket(rs.getString("harga_tiket"));
                t.setJumlah_pesan(rs.getString("jumlah_pesan"));
                t.setJumlah_bayar(rs.getString("jumlah_bayar"));
                t.setUang_masuk(rs.getString("uang_masuk"));
                t.setUang_keluar(rs.getString("uang_keluar"));
                list.add(t);
            }
            stm.setList(list);
        } catch (Exception error) {
            System.err.println("Error at TiketController-loadData, details : "+error.toString());
            JOptionPane.showMessageDialog(tv, "Error at TiketController-loadData, details : "+error.toString());
        }
    }
    
    public void TIKET(TiketView tv) {
        try {
            Statement statement;
            ResultSet rs;
            
            String sqlSelect = "SELECT * FROM tb_film";
            
            statement = Koneksi.getConnection().createStatement();
            rs = statement.executeQuery(sqlSelect);
            while (rs.next()){
                TIKETFILM.addItem(rs.getString("tiket_film"));
            }
        } catch (Exception error) {
            System.err.println("Gagal tampil judul, details : "+error.toString());
            JOptionPane.showMessageDialog(tv, "Error at TiketaController-TIKET, details : "+error.toString());
        }
    }
    
    public void TIKETFILM(TiketView tv) {
        try {
            Statement statement;
            ResultSet rs;
            
            String sqlSelect = "SELECT tb_film.genre, "
                    + "tb_film.sinopsis FROM tb_film WHERE tiket_film = "
                    + "'"+TIKETFILM.getSelectedItem()+"'";
            
            statement = Koneksi.getConnection().createStatement();
            rs = statement.executeQuery(sqlSelect);
            while (rs.next()) {
                Object[]ob = new Object[3];
                ob[1] = rs.getString(1);
                ob[2] = rs.getString(2);
                GENRE.setText((String)ob[1]);
                SINOPSIS.setText((String)ob[2]);
            } rs.close(); statement.close(); 
        } catch (Exception error) {
            System.err.println("Gagal tampil Tiket, details : "+error.toString());
            JOptionPane.showMessageDialog(tv, "Error at TiketController-TIKETFILM, details : "+error.toString());
        }
    }
    
    public void searchData(TiketView tv){
        try {
            String parameter = "";
            if (TiketView.COMBOCARI.getSelectedIndex()== 0){
                parameter = "kode_pesanan";
            }else if (TiketView.COMBOCARI.getSelectedIndex()== 1){
                parameter = "nama_pemesan";
            }else if (TiketView.COMBOCARI.getSelectedIndex()== 2){
                parameter = "tiket_film";
            }else if (TiketView.COMBOCARI.getSelectedIndex()== 3){
                parameter = "waktu";
            }
            
            String keyword = TiketView.TEXTCARI.getText();
            
            Statement statement;
            ResultSet rs;
            
            String sqlSelect = "SELECT * FROM tb_tiket WHERE " + parameter + " LIKE '%" + keyword + "%' ORDER BY kode_pesanan ASC";
            
            statement = Koneksi.getConnection().createStatement();
            rs = statement.executeQuery(sqlSelect);
            
            List<Tiket> list = new ArrayList<>();
            while(rs.next()){
                
                Tiket t = new Tiket();
                t.setKode_pesanan(rs.getString("kode_pesanan"));
                t.setNama_pemesan(rs.getString("nama_pemesan"));
                t.setTiket_film(rs.getString("tiket_film"));
                t.setWaktu(rs.getString("waktu"));
                t.setHarga_tiket(rs.getString("harga_tiket"));
                t.setJumlah_pesan(rs.getString("jumlah_pesan"));
                t.setJumlah_bayar(rs.getString("jumlah_bayar"));
                t.setUang_masuk(rs.getString("uang_masuk"));
                t.setUang_keluar(rs.getString("uang_keluar"));
                list.add(t);
            }
            stm.setList(list);            
        } catch (Exception error){
            System.err.println("Error at TiketController-searchData, details : "+error.toString());
            JOptionPane.showMessageDialog(tv, "Error at TiketController-searchData, details : "+error.toString());
        }
    }
    
    
    public void refresh(TiketView tv){
        try {
            TiketView.labelStatus.setText("");
            TiketView.KODEPESANAN.setText("");
            TiketView.NAMAPEMESAN.setText("");
            TiketView.TIKETFILM.setSelectedIndex(0);
            TiketView.GENRE.setText("");
            TiketView.SINOPSIS.setText("");
            TiketView.WAKTU.setSelectedIndex(0);
            TiketView.HARGATIKET.setText("");
            TiketView.JUMLAHPESAN.setText("");
            TiketView.JUMLAHBAYAR.setText("");
            TiketView.MASUKKANUANG.setText("");
            TiketView.KEMBALIAN.setText("");
            TiketView.COMBOCARI.setSelectedIndex(0);
            TiketView.TEXTCARI.setText("");
            TiketView.TEXTPRINT.setText("");
            TiketView.tableTiket.clearSelection();
            
            TiketView.KODEPESANAN.setEnabled(false);
            TiketView.NAMAPEMESAN.setEnabled(false);
            TiketView.TIKETFILM.setEnabled(false);
            TiketView.GENRE.setEnabled(false);
            TiketView.SINOPSIS.setEnabled(false);
            TiketView.WAKTU.setEnabled(false);
            TiketView.HARGATIKET.setEnabled(false);
            TiketView.JUMLAHPESAN.setEnabled(false);
            TiketView.JUMLAHBAYAR.setEnabled(false);
            TiketView.MASUKKANUANG.setEnabled(false);
            TiketView.KEMBALIAN.setEnabled(false);
            TiketView.BJumlah.setEnabled(false);
            TiketView.BBayar.setEnabled(false);
            TiketView.BSimpan.setEnabled(false);
            TiketView.BBatal.setEnabled(false);
            
            TiketView.COMBOCARI.setEnabled(true);
            TiketView.TEXTCARI.setEnabled(true);
            TiketView.tableTiket.setEnabled(true);
            TiketView.BTambah.setEnabled(true);
            TiketView.BUbah.setEnabled(true);
            TiketView.BHapus.setEnabled(true);
            TiketView.BSegarkan.setEnabled(true);
            TiketView.BKeluar.setEnabled(true);
            
            loadData(tv);
        } catch (Exception error){
            System.err.println("Error at TiketController-refresh, details : "+error.toString());
            JOptionPane.showMessageDialog(tv, "Error at TiketController-refresh, details : "+error.toString());
        }
    }
    
    /*
    Nama Methode : tableTiketAction
    Deksripsi : Method ini digunakan untuk perilaku table yang mana jika kita mengklik salah satu data maka data tersebut akan diset
    ke masing masing itemnya
    */
    public void tableTiketAction(final TiketView sv){
        TiketView.tableTiket.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = TiketView.tableTiket.getSelectedRow();
                // nilai -1 menandakan bahwa tidak ada data sama sekali yang diklik pada table
                if (row != -1) {
                    Tiket s = stm.get(row);
                    
                    TiketView.KODEPESANAN.setText(s.getKode_pesanan());
                    TiketView.NAMAPEMESAN.setText(s.getNama_pemesan());                    
                    TiketView.TIKETFILM.setSelectedItem(s.getTiket_film());
                    TiketView.GENRE.setText(s.getGenre());
                    TiketView.SINOPSIS.setText(s.getSinopsis());
                    TiketView.WAKTU.setSelectedItem(s.getWaktu());
                    TiketView.HARGATIKET.setText(s.getHarga_tiket());
                    TiketView.JUMLAHPESAN.setText(s.getJumlah_pesan());
                    TiketView.JUMLAHBAYAR.setText(s.getJumlah_bayar());
                    TiketView.MASUKKANUANG.setText(s.getUang_masuk());
                    TiketView.KEMBALIAN.setText(s.getUang_keluar());
                }
            }
        });
    }
    
    /*
    Jenis : Function
    Nama : getKodeOtomatis
    Deskripsi : Function ini digunakan untuk membuat suatu ID menjadi otomatis dengan suatu format tertentu,
    pada kasus ini adalah NIS dan kita akan menggunakan format NIS-XXXX
    */
    private String getKodeOtomatis(TiketView tv) {
    String kode = "";
    try {
        Statement statement;
        ResultSet rs;
        
        String sqlSelect = "SELECT kode_pesanan FROM tb_tiket ORDER BY kode_pesanan DESC LIMIT 1";
        
        statement = Koneksi.getConnection().createStatement();
        rs = statement.executeQuery(sqlSelect);
        
        if (rs.next()) {
            String kodeOnDB = rs.getString("kode_pesanan");
            
            int kodeTerakhir = Integer.parseInt(kodeOnDB.substring(3)); // Start from index 3 to ignore "ID-"
            if ((kodeTerakhir >= 1) && (kodeTerakhir < 10)) {
                kode = "ID-000" + (kodeTerakhir + 1);
            } else if ((kodeTerakhir >= 10) && (kodeTerakhir < 100)) {
                kode = "ID-00" + (kodeTerakhir + 1);
            } else if ((kodeTerakhir >= 100) && (kodeTerakhir < 1000)) {
                kode = "ID-0" + (kodeTerakhir + 1);
            } else if ((kodeTerakhir >= 1000) && (kodeTerakhir < 10000)) {
                kode = "ID-" + (kodeTerakhir + 1);
            }
        } else {
            kode = "ID-0001";
        }
        rs.close(); // Close ResultSet when done
    } catch (SQLException | NumberFormatException error) {
        System.err.println("Error at TiketController-getKodeOtomatis, details : "+error.toString());
        JOptionPane.showMessageDialog(tv, "Error at TiketController-getKodeOtomatis, details : "+error.toString());
    }
    return kode;
}

    
    public void waktuAction(TiketView tv) {
        try {
            String time = "";
            time = (String)WAKTU.getSelectedItem();
            if (time=="Senin-Kamis") {
                times=50000;
            } 
            else if (time=="Jumat") {
                times=60000;
            }
            else if (time=="Weekend & Tanggal Merah") {
                times=75000;
            }
            HARGATIKET.setText("" + times);
        } catch (Exception error) {
            System.err.println("Error at TiketController-waktuAction, details : "+error.toString());
            JOptionPane.showMessageDialog(tv, "Error at TiketController-waktuAction, details : "+error.toString());
        }
    }
    
    public void buttonHitungJumlahAction(TiketView tv) {
        try {
            jmlhbeli=Integer.parseInt(JUMLAHPESAN.getText());
            jmlhhitung = times * jmlhbeli;
            
            JUMLAHBAYAR.setText("" + jmlhhitung);
            
        } catch (Exception error) {
            System.err.println("Error at TiketController-buttonHitungAction, details : "+error.toString());
            JOptionPane.showMessageDialog(tv, "Error at TiketController-buttonHitungAction, details : "+error.toString());
        }
        TiketView.JUMLAHBAYAR.setEnabled(true);
    }
    
    public void bayarAction(TiketView tv) {
        try {
            jmlhhitung = Integer.parseInt(JUMLAHBAYAR.getText());
            bayar = Integer.parseInt(MASUKKANUANG.getText());
            
            kembalian = bayar - jmlhhitung;
            KEMBALIAN.setText("" + kembalian);
        } catch (Exception error) {
            System.err.println("Error at TiketController-bayarAction, details : "+error.toString());
            JOptionPane.showMessageDialog(tv, "Error at TiketController-bayarAction, details : "+error.toString());
        }
    }
    
    public void buttonTambahAction(TiketView tv) {
        try {
            TiketView.labelStatus.setText("INSERT");
            TiketView.KODEPESANAN.setText(getKodeOtomatis(tv));
            TiketView.NAMAPEMESAN.setText("");
            TiketView.TIKETFILM.setSelectedIndex(0);
            TiketView.GENRE.setText("");
            TiketView.SINOPSIS.setText("");
            TiketView.WAKTU.setSelectedIndex(0);
            TiketView.HARGATIKET.setText("");
            TiketView.JUMLAHPESAN.setText("");
            TiketView.JUMLAHBAYAR.setText("");
            TiketView.MASUKKANUANG.setText("");
            TiketView.KEMBALIAN.setText("");
            TiketView.COMBOCARI.setSelectedIndex(0);
            TiketView.TEXTCARI.setText("");
            TiketView.tableTiket.clearSelection();
            
            TiketView.KODEPESANAN.setEnabled(false);
            TiketView.NAMAPEMESAN.setEnabled(true);
            TiketView.TIKETFILM.setEnabled(true);
            TiketView.GENRE.setEnabled(true);
            TiketView.SINOPSIS.setEnabled(true);
            TiketView.WAKTU.setEnabled(true);
            TiketView.HARGATIKET.setEnabled(true);
            TiketView.JUMLAHPESAN.setEnabled(true);
            TiketView.JUMLAHBAYAR.setEnabled(true);
            TiketView.MASUKKANUANG.setEnabled(true);
            TiketView.KEMBALIAN.setEnabled(true);
            TiketView.BJumlah.setEnabled(true);
            TiketView.BBayar.setEnabled(true);
            TiketView.BSimpan.setEnabled(true);
            TiketView.BBatal.setEnabled(true);
            
            TiketView.COMBOCARI.setEnabled(false);
            TiketView.TEXTCARI.setEnabled(false);
            TiketView.tableTiket.setEnabled(false);
            TiketView.BTambah.setEnabled(false);
            TiketView.BUbah.setEnabled(false);
            TiketView.BHapus.setEnabled(false);
            TiketView.BSegarkan.setEnabled(false);
            TiketView.BKeluar.setEnabled(true);
            
            TiketView.NAMAPEMESAN.requestFocus();
        } catch (Exception error) {
            System.err.println("Error at TiketController-buttonTambahAction, details : "+error.toString());
            JOptionPane.showMessageDialog(tv, "Error at TiketController-buttonTambahAction, details : "+error.toString());
        }
    }
    
    public void buttonUbahAction(TiketView tv) {
        try {
           int row = TiketView.tableTiket.getSelectedRow();
        if (row == -1){
            JOptionPane.showMessageDialog(tv, "Silahkan klik data yang ingin diubah");
        }else{
            TiketView.labelStatus.setText("UPDATE");
            TiketView.KODEPESANAN.setEnabled(true);
            TiketView.NAMAPEMESAN.setEnabled(true);
            TiketView.TIKETFILM.setEnabled(true);
            TiketView.GENRE.setEnabled(true);
            TiketView.SINOPSIS.setEnabled(true);
            TiketView.WAKTU.setEnabled(true);
            TiketView.HARGATIKET.setEnabled(true);
            TiketView.JUMLAHPESAN.setEnabled(true);
            TiketView.JUMLAHBAYAR.setEnabled(false);
            TiketView.MASUKKANUANG.setEnabled(true);
            TiketView.KEMBALIAN.setEnabled(true);
            TiketView.BJumlah.setEnabled(true);
            TiketView.BBayar.setEnabled(true);
            TiketView.BSimpan.setEnabled(true);
            TiketView.BBatal.setEnabled(true);
            
            TiketView.JUMLAHBAYAR.setEnabled(false);
            TiketView.COMBOCARI.setEnabled(true);
            TiketView.TEXTCARI.setEnabled(true);
            TiketView.tableTiket.setEnabled(true);
            TiketView.BTambah.setEnabled(true);
            TiketView.BUbah.setEnabled(true);
            TiketView.BHapus.setEnabled(true);
            TiketView.BSegarkan.setEnabled(true);
            TiketView.BKeluar.setEnabled(true);
            
            TiketView.NAMAPEMESAN.requestFocus();
            }
        } catch (Exception error) {
             System.err.println("Error at TiketController-buttonUbahAction, details : "+error.toString());
            JOptionPane.showMessageDialog(tv, "Error at TiketController-buttonUbahAction, details : "+error.toString());
        }
    }
    
     public void buttonHapusAction(TiketView tv){
        try {
            int row = TiketView.tableTiket.getSelectedRow();
                // nilai -1 menandakan bahwa tidak ada data sama sekali yang diklik pada table
            if (row == -1) {
                JOptionPane.showMessageDialog(tv, "Silahkan Klik Data Yang Ingin DiHapus");
            } else {
                String kode_pesanan = stm.get(row).getKode_pesanan();
                
                int konfirmasi = JOptionPane.showConfirmDialog(tv, "Apakah anda yakin ingin menghapus data ini "+kode_pesanan+"?",
                        "Konfirmasi", JOptionPane.YES_NO_OPTION);
                
                if(konfirmasi == JOptionPane.YES_OPTION){
                    PreparedStatement ps;
                    
                    String sqlDelete = "DELETE FROM tb_tiket WHERE kode_pesanan = ?";
                    
                    ps = Koneksi.getConnection().prepareStatement(sqlDelete);
                    ps.setString(1, kode_pesanan);
                    int isSuccess = ps.executeUpdate();
                    if (isSuccess == 0) {
                        JOptionPane.showMessageDialog(tv, "Data Gagal Dihapus", "Pesan", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(tv, "Data Berhasil diHapus", "Pesan", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        } catch (HeadlessException | SQLException error) {
            System.err.println("Error at TiketController-buttonHapusAction, details : "+error.toString());
            JOptionPane.showMessageDialog(tv, "Error at TiketController-buttonHapusAction, details : "+error.toString());
        }
    }
    
     /*
    Method Ini berfungsi mengembalikan TiketView ke default
    */
    public void buttonSegarkanAction(TiketView tv) {
        try {
            refresh(tv);
        } catch (Exception error) {
           System.err.println("Error at TiketController-buttonSegarkanAction, details : "+error.toString());
           JOptionPane.showMessageDialog(tv, "Error at TiketController-buttonSegarkanAction, details : "+error.toString()); 
        }
    }
    
    /*
    Jenis : FUNCTION
    Nama : buttonTutupAction
    */
    public void buttonTutupAction(TiketView tv){
        try {
            tv.dispose();
            HomeView.menuItemTiket.setEnabled(true);
        } catch (Exception error) {
            System.err.println("Error at TiketController-buttonTutupAction, details : "+error.toString());
            JOptionPane.showMessageDialog(tv, "Error at TiketController-buttonTutupAction, details : "+error.toString()); 
        }
    }
    
    /*
    Jenis : FUNCTION
    Nama : validasiData
    Deskripsi : Function ini digunakan untuk menseleksi item-item apa saja yang keadaan dalam kosong,
    jika terdapat item yang kosong maka akan memberikan nilai true (berarti terdapat data kosong),
    dan juga akan memberikan nilai false jika tidak ada terdapat satu pun item yang kosong
    */
    private boolean validasiData(TiketView tv) {
        boolean b = true;
        try {
            if (TiketView.KODEPESANAN.getText().equals("")) {
                JOptionPane.showMessageDialog(tv, "Kode Pesanan tidak boleh kosong!");
            } else if (TiketView.NAMAPEMESAN.getText().equals("")) {
                JOptionPane.showMessageDialog(tv, "Nama tidak boleh kosong!");
            } else if (TiketView.JUMLAHPESAN.getText().equals("")) {
                JOptionPane.showMessageDialog(tv, "Jumlah Pesan tidak boleh kosong!");
            } else if (TiketView.MASUKKANUANG.getText().equals("")) {
                JOptionPane.showMessageDialog(tv, "Masukkan Uang tidak boleh kosong!");
            } else {
                b = false;
            }
        } catch (Exception error) {
           System.err.println("Error at TiketController-validasiData, details : "+error.toString());
            JOptionPane.showMessageDialog(tv, "Error at TiketController-validasiData, details : "+error.toString()); 
        }
        return b;
    }
    
    public void buttonSimpanAction(TiketView tv) {
        try {
            boolean b = validasiData(tv);
            if (b == false) {
              
                Tiket t = new Tiket();
                t.setKode_pesanan(TiketView.KODEPESANAN.getText());
                t.setNama_pemesan(TiketView.NAMAPEMESAN.getText());
                t.setTiket_film(TiketView.TIKETFILM.getSelectedItem().toString());
                //t.setGenre(TiketView.GENRE.getText());
                //t.setSinopsis(TiketView.SINOPSIS.getText());
                t.setWaktu(TiketView.WAKTU.getSelectedItem().toString());
                t.setHarga_tiket(TiketView.HARGATIKET.getText());
                t.setJumlah_pesan(TiketView.JUMLAHPESAN.getText());
                t.setJumlah_bayar(TiketView.JUMLAHBAYAR.getText());
                t.setUang_masuk(TiketView.MASUKKANUANG.getText());
                t.setUang_keluar(TiketView.KEMBALIAN.getText());
                
                Statement statement;
                ResultSet rs;
            
                String sqlSelect = "SELECT tb_film.tiket_film FROM tb_film WHERE tiket_film='"
                        +TIKETFILM.getSelectedItem()+"' ";
            
                statement = Koneksi.getConnection().createStatement();
                rs = statement.executeQuery(sqlSelect);
                PreparedStatement ps;
                while (rs.next()) {
                if (TiketView.labelStatus.getText().equals("INSERT")) {
                    String tiket = rs.getString("tiket_film");
                    String sqlInsert = "INSERT INTO tb_tiket VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    
                    
                    ps = Koneksi.getConnection().prepareStatement(sqlInsert);
                    ps.setString(1, t.getKode_pesanan());
                    ps.setString(2, t.getNama_pemesan());
                    ps.setString(3, tiket);
                    ps.setString(4, t.getWaktu());
                    ps.setString(5, t.getHarga_tiket());
                    ps.setString(6, t.getJumlah_pesan());
                    ps.setString(7, t.getJumlah_bayar());
                    ps.setString(8, t.getUang_masuk());
                    ps.setString(9, t.getUang_keluar());
                    int isSuccess = ps.executeUpdate();
                    
                    if (isSuccess != 1) {
                        JOptionPane.showMessageDialog(tv, "Data Gagal DiSimpan", "Pesan", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(tv, "Data Berhasil diSimpan", "Pesan", JOptionPane.INFORMATION_MESSAGE);
                    }
                    refresh(tv);
                } else if (TiketView.labelStatus.getText().equals("UPDATE")){
                    String tiket = rs.getString("tiket_film");
                    String sqlUpdate = "UPDATE tb_tiket SET nama_pemesan = ?, tiket_film = ?, waktu = ?," +
                             "harga_tiket = ?, jumlah_pesan = ?, jumlah_bayar = ?, uang_masuk = ?, uang_keluar = ? WHERE kode_pesanan = ?";
            
                    
                    ps = Koneksi.getConnection().prepareStatement(sqlUpdate);
                    ps.setString(1, t.getNama_pemesan());
                    ps.setString(2, tiket);
                    ps.setString(3, t.getWaktu());
                    ps.setString(4, t.getHarga_tiket());
                    ps.setString(5, t.getJumlah_pesan());
                    ps.setString(6, t.getJumlah_bayar());
                    ps.setString(7, t.getUang_masuk());
                    ps.setString(8, t.getUang_keluar());
                    ps.setString(9, t.getKode_pesanan());
                    int isSuccess = ps.executeUpdate();
                    
                    if (isSuccess != -1){
                        JOptionPane.showMessageDialog(tv, "Data berhasil Diubah","Pesan",JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(tv, "Data gagal Diubah","Pesan",JOptionPane.ERROR_MESSAGE);
                    }
                    refresh(tv);
                }
                }
            }
        } catch (SQLException | HeadlessException error) {
            System.err.println("Error at TiketController-buttonSimpanAction, details : "+error.toString());
            JOptionPane.showMessageDialog(tv, "Error at TiketController-buttonSimpanAction, details : "+error.toString());
        }
    }
    
    public void buttonBatalAction(TiketView tv){
        try {
            refresh(tv);
        } catch (Exception error) {
            System.err.println("Error at TiketController-buttonBatalAction, details : "+error.toString());
            JOptionPane.showMessageDialog(tv, "Error at TiketController-buttonBatalAction, details : "+error.toString());
        }
    }
    
    public void BRecordAction(TiketView tv) {
        try {
            TEXTPRINT.append("\t\t          Tiket Bioskop           \n\n" +
                    "========================================================================" + "n\n" +
                    "Kode Pesanan :\t\t\t" + KODEPESANAN.getText() + "\n\n" +
                    "Nama :\t\t\t" + NAMAPEMESAN.getText() + "\n\n" +
                    "Judul Film :\t\t\t" + TIKETFILM.getSelectedItem() + "\n\n" +
                    "Waktu :\t\t\t" + WAKTU.getSelectedItem() + "\n\n" +
                    "Harga Tiket :\t\t\t" + HARGATIKET.getText() + "\n\n" +
                    "Jumlah Pesan :\t\t\t" + JUMLAHPESAN.getText() + "\n\n" +
                    "Total Harga :\t\t\t" + JUMLAHBAYAR.getText() + "\n\n" +
                    "Uang Bayar :\t\t\t" + MASUKKANUANG.getText() + "\n\n" +
                    "Kembalian :\t\t\t" + KEMBALIAN.getText() + "\n\n" +
                    "============================================================================");
        } catch (Exception error) {
            System.err.println("Error at TiketController-BRecordAction, details : "+error.toString());
            JOptionPane.showMessageDialog(tv, "Error at TiketController-BRecordAction, details  : "+error.toString());
        }
    }
    
    public void printAction(TiketView tv) {
        try {
            TEXTPRINT.print();
        } catch (Exception error) {
            System.err.println("Error at TiketController-printAction, details : "+error.toString());
            JOptionPane.showMessageDialog(tv, "Error at TiketController-printlAction, details : "+error.toString());
        }
    }
    
}
