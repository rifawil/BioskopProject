
package bioskopproject.film;

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
//import bioskopproject.tiket.TiketView;
import bioskopproject.home.HomeView;

public class FilmController {
    
    private final FilmTableModel stm = new FilmTableModel();
    
    public void setMaximumFrame(FilmView sv) {
       try {
           sv.setMaximum(true);
       } catch(Exception error) {
           System.err.println("Error at FilmController-setMaximumFrame, details : "+error.toString());
           JOptionPane.showMessageDialog(sv, "Error at FilmController-setMaximumFrame, details : "+error.toString());
       }
     }
    
    /*
    Jenis : METHODE
    NAMA : setUndercoreted
    DESKRIPSI : Method ini digunakan untuk membuat FilmView tidak memiliki dekorasi pada bagian atasnya
    */
     public void setUndercoreted(FilmView sv){
        try {
            sv.putClientProperty("JInternalFrame.isPallete", Boolean.TRUE);
            BasicInternalFrameUI basicInternalFrameUI = (BasicInternalFrameUI) sv.getUI();
            basicInternalFrameUI.setNorthPane(null);
        } catch (Exception error){
            System.err.println("Error at FilmController-setUndercoreted, details : "+error.toString());
            JOptionPane.showMessageDialog(sv, "Error at FilmController-setUndercoreted, details : "+error.toString());
        }
    }
     
     public void setTableModel(FilmView sv){
        try {
            FilmView.tableFilm.setModel(stm);
        } catch (Exception error) {
            System.err.println("Error at FilmController-setTableModel, details : "+error.toString());
            JOptionPane.showMessageDialog(sv, "Error at FilmController-setTableModel, details : "+error.toString());
        }
    }
     
     /*
    Jenis : Methode
    Nama : loadData
    Deskripsi : Method ini digunakan untuk mengambil data Film yang ada pada database dan disajikan pada tableSiswa
    */
    public void loadData(FilmView sv){
        try {
            Statement statement;
            ResultSet rs;
            
            String sqlSelect = "SELECT * FROM tb_film ORDER BY tiket_film ASC";
            
            statement = Koneksi.getConnection().createStatement();
            rs = statement.executeQuery(sqlSelect);
            
            List<Film> list = new ArrayList<>();
            while (rs.next()) {
               
                Film s = new Film();
                s.setTiket_film(rs.getString("tiket_film"));
                s.setGenre(rs.getString("genre"));
                s.setSinopsis(rs.getString("sinopsis"));
                list.add(s);
            }
            stm.setList(list);
        } catch (Exception error) {
            System.err.println("Error at FilmController-loadData, details : "+error.toString());
            JOptionPane.showMessageDialog(sv, "Error at FilmController-loadData, details : "+error.toString());
        }
    }
    
    public void searchData(FilmView sv){
        try {
            String parameter = "";
            if (FilmView.COMBOCARI.getSelectedIndex()== 0){
                parameter = "tiket_film";
            }else if (FilmView.COMBOCARI.getSelectedIndex()== 1){
                parameter = "genre";
            }else if (FilmView.COMBOCARI.getSelectedIndex()== 2){
                parameter = "sinopsis";
            }
            
            String keyword = FilmView.TEXTCARI.getText();
            
            Statement statement;
            ResultSet rs;
            
            String sqlSelect = "SELECT * FROM tb_film WHERE " + parameter + " LIKE '%" + keyword + "%' ORDER BY tiket_film ASC";
            
            statement = Koneksi.getConnection().createStatement();
            rs = statement.executeQuery(sqlSelect);
            
            List<Film> list = new ArrayList<>();
            while(rs.next()){
                
                Film s = new Film();
                s.setTiket_film(rs.getString("tiket_film"));
                s.setGenre(rs.getString("genre"));
                s.setSinopsis(rs.getString("sinopsis"));
                list.add(s);
            }
            stm.setList(list);            
        } catch (Exception error){
            System.err.println("Error at FilmController-searchData, details : "+error.toString());
            JOptionPane.showMessageDialog(sv, "Error at FilmController-searchData, details : "+error.toString());
        }
    }
    
    /*
    Method ini berfungsi mengembalikan FilmView kembali ke default
    */
    public void refresh(FilmView sv){
        try {
            FilmView.labelStatus.setText("");
            FilmView.TIKETFILM.setText("");
            FilmView.GENRE.setText("");
            FilmView.SINOPSIS.setText("");
            FilmView.COMBOCARI.setSelectedIndex(0);
            FilmView.TEXTCARI.setText("");
            FilmView.tableFilm.clearSelection();
            
            FilmView.TIKETFILM.setEnabled(false);
            FilmView.GENRE.setEnabled(false);
            FilmView.SINOPSIS.setEnabled(false);
            FilmView.BSimpan.setEnabled(false);
            FilmView.BBatal.setEnabled(false);
            
            FilmView.COMBOCARI.setEnabled(true);
            FilmView.TEXTCARI.setEnabled(true);
            FilmView.tableFilm.setEnabled(true);
            FilmView.BTambah.setEnabled(true);
            FilmView.BUbah.setEnabled(true);
            FilmView.BHapus.setEnabled(true);
            FilmView.BSegarkan.setEnabled(true);
            FilmView.BKeluar.setEnabled(true);
            
            loadData(sv);
        } catch (Exception error){
            System.err.println("Error at FilmController-refresh, details : "+error.toString());
            JOptionPane.showMessageDialog(sv, "Error at FilmController-refresh, details : "+error.toString());
        }
    }
    
    /*
    Nama Methode : tableFilmAction
    Deksripsi : Method ini digunakan untuk perilaku table yang mana jika kita mengklik salah satu data maka data tersebut akan diset
    ke masing masing itemnya
    */
    public void tableFilmAction(final FilmView sv){
        FilmView.tableFilm.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = FilmView.tableFilm.getSelectedRow();
                // nilai -1 menandakan bahwa tidak ada data sama sekali yang diklik pada table
                if (row != -1) {
                    Film s = stm.get(row);
                    
                    FilmView.TIKETFILM.setText(s.getTiket_film());
                    FilmView.GENRE.setText(s.getGenre());
                    FilmView.SINOPSIS.setText(s.getSinopsis());
                }
            }
        });
    }
    
     public void buttonTambahAction(FilmView sv) {
        try {
            FilmView.labelStatus.setText("INSERT");
            FilmView.TIKETFILM.setText("");
            FilmView.GENRE.setText("");
            FilmView.SINOPSIS.setText("");
            FilmView.COMBOCARI.setSelectedIndex(0);
            FilmView.TEXTCARI.setText("");
            FilmView.tableFilm.clearSelection();
            
            FilmView.TIKETFILM.setEnabled(true);
            FilmView.GENRE.setEnabled(true);
            FilmView.SINOPSIS.setEnabled(true);
            FilmView.BSimpan.setEnabled(true);
            FilmView.BBatal.setEnabled(true);
            
            FilmView.COMBOCARI.setEnabled(false);
            FilmView.TEXTCARI.setEnabled(false);
            FilmView.tableFilm.setEnabled(false);
            FilmView.BTambah.setEnabled(false);
            FilmView.BUbah.setEnabled(false);
            FilmView.BHapus.setEnabled(false);
            FilmView.BSegarkan.setEnabled(false);
            FilmView.BKeluar.setEnabled(false);
            
            FilmView.GENRE.requestFocus();
        } catch (Exception error) {
            System.err.println("Error at FilmController-buttonTambahAction, details : "+error.toString());
            JOptionPane.showMessageDialog(sv, "Error at FilmController-buttonTambahAction, details : "+error.toString());
        }
    }
    
    public void buttonUbahAction(FilmView sv) {
        try {
           int row = FilmView.tableFilm.getSelectedRow();
        if (row == -1){
            JOptionPane.showMessageDialog(sv, "Silahkan klik data yang ingin diubah");
        }else{
            FilmView.labelStatus.setText("UPDATE");
            FilmView.TIKETFILM.setEnabled(true);
            FilmView.GENRE.setEnabled(true);
            FilmView.SINOPSIS.setEnabled(true);
            FilmView.BSimpan.setEnabled(true);
            FilmView.BBatal.setEnabled(true);
            
            FilmView.COMBOCARI.setEnabled(false);
            FilmView.TEXTCARI.setEnabled(false);
            FilmView.tableFilm.setEnabled(false);
            FilmView.BTambah.setEnabled(false);
            FilmView.BUbah.setEnabled(false);
            FilmView.BHapus.setEnabled(false);
            FilmView.BSegarkan.setEnabled(false);
            FilmView.BKeluar.setEnabled(false);
            
            FilmView.GENRE.requestFocus();
            }
        } catch (Exception error) {
             System.err.println("Error at FilmController-buttonUbahAction, details : "+error.toString());
            JOptionPane.showMessageDialog(sv, "Error at FilmController-buttonUbahAction, details : "+error.toString());
        }
    }
    
    public void buttonHapusAction(FilmView sv){
        try {
            int row = FilmView.tableFilm.getSelectedRow();
                // nilai -1 menandakan bahwa tidak ada data sama sekali yang diklik pada table
            if (row == -1) {
                JOptionPane.showMessageDialog(sv, "Silahkan Klik Data Yang Ingin DiHapus");
            } else {
                String tiket_film = stm.get(row).getTiket_film();
                
                int konfirmasi = JOptionPane.showConfirmDialog(sv, "Apakah anda yakin ingin menghapus data "+tiket_film+"?",
                        "Konfirmasi", JOptionPane.YES_NO_OPTION);
                
                if(konfirmasi == JOptionPane.YES_OPTION){
                    PreparedStatement ps;
                    
                    String sqlDelete = "DELETE FROM tb_film WHERE tiket_film = ?";
                    
                    ps = Koneksi.getConnection().prepareStatement(sqlDelete);
                    ps.setString(1, tiket_film);
                    int isSuccess = ps.executeUpdate();
                    if (isSuccess == 0) {
                        JOptionPane.showMessageDialog(sv, "Data Gagal Dihapus", "Pesan", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(sv, "Data Berhasil diHapus", "Pesan", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        } catch (HeadlessException | SQLException error) {
            System.err.println("Error at FilmController-buttonHapusAction, details : "+error.toString());
            JOptionPane.showMessageDialog(sv, "Error at FilmController-buttonHapusAction, details : "+error.toString());
        }
    }
    
    /*
    Method Ini berfungsi mengembalikan SiswaView ke default
    */
    public void buttonSegarkanAction(FilmView sv) {
        try {
            refresh(sv);
        } catch (Exception error) {
           System.err.println("Error at FilmController-buttonSegarkanAction, details : "+error.toString());
           JOptionPane.showMessageDialog(sv, "Error at FilmController-buttonSegarkanAction, details : "+error.toString()); 
        }
    }
    
    /*
    Jenis : FUNCTION
    Nama : buttonTutupAction
    */
    public void buttonTutupAction(FilmView sv){
        try {
            sv.dispose();
            HomeView.menuItemFilm.setEnabled(true);
        } catch (Exception error) {
            System.err.println("Error at FilmController-buttonTutupAction, details : "+error.toString());
            JOptionPane.showMessageDialog(sv, "Error at FilmController-buttonTutupAction, details : "+error.toString()); 
        }
    }
    
    /*
    Jenis : FUNCTION
    Nama : validasiData
    Deskripsi : Function ini digunakan untuk menseleksi item-item apa saja yang keadaan dalam kosong,
    jika terdapat item yang kosong maka akan memberikan nilai true (berarti terdapat data kosong),
    dan juga akan memberikan nilai false jika tidak ada terdapat satu pun item yang kosong
    */
    private boolean validasiData(FilmView sv) {
        boolean b = true;
        try {
            if (FilmView.TIKETFILM.getText().equals("")) {
                JOptionPane.showMessageDialog(sv, "Judul Film tidak boleh kosong!");
            } else if (FilmView.GENRE.getText().equals("")) {
                JOptionPane.showMessageDialog(sv, "Genre tidak boleh kosong!");
            } else if (FilmView.SINOPSIS.getText().equals("")) {
                JOptionPane.showMessageDialog(sv, "Sinopsis tidak boleh kosong!");
            } else {
                b = false;
            }
        } catch (Exception error) {
           System.err.println("Error at FilmController-validasiData, details : "+error.toString());
            JOptionPane.showMessageDialog(sv, "Error at FilmController-validasiData, details : "+error.toString()); 
        }
        return b;
    }
    
    public void buttonSimpanAction(FilmView sv) {
        try {
            boolean b = validasiData(sv);
            if (b == false) {
     
                Film s = new Film();
                s.setTiket_film(FilmView.TIKETFILM.getText());
                s.setGenre(FilmView.GENRE.getText());
                s.setSinopsis(FilmView.SINOPSIS.getText());
                
                PreparedStatement ps;
                if (FilmView.labelStatus.getText().equals("INSERT")) {
                    String sqlInsert = "INSERT INTO tb_film VALUES (?, ?, ?)";
                    
                    ps = Koneksi.getConnection().prepareStatement(sqlInsert);
                    ps.setString(1, s.getTiket_film());
                    ps.setString(2, s.getGenre());
                    ps.setString(3, s.getSinopsis());
                    int isSuccess = ps.executeUpdate();
                    
                    if (isSuccess != 1) {
                        JOptionPane.showMessageDialog(sv, "Data Gagal DiSimpan", "Pesan", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(sv, "Data Berhasil diSimpan", "Pesan", JOptionPane.INFORMATION_MESSAGE);
                    }
                    refresh(sv);
                } else if (FilmView.labelStatus.getText().equals("UPDATE")){
                    String sqlUpdate = "UPDATE tb_film SET genre = ?,"
                            +"sinopsis = ? WHERE tiket_film = ?";
                    
                    ps = Koneksi.getConnection().prepareStatement(sqlUpdate);
                    ps.setString(1, s.getGenre());
                    ps.setString(2, s.getSinopsis());
                    ps.setString(3, s.getTiket_film());
                    int isSuccess = ps.executeUpdate();
                    
                    if (isSuccess != -1){
                        JOptionPane.showMessageDialog(sv, "Data berhasil Diubah","Pesan",JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(sv, "Data gagal Diubah","Pesan",JOptionPane.ERROR_MESSAGE);
                    }
                    refresh(sv);
                }
            }
        } catch (SQLException | HeadlessException error) {
            System.err.println("Error at FilmController-buttonSimpanAction, details : "+error.toString());
            JOptionPane.showMessageDialog(sv, "Error at FilmController-buttonSimpanAction, details : "+error.toString());
        }
    }
    
    public void buttonBatalAction(FilmView sv){
        try {
            refresh(sv);
        } catch (Exception error) {
            System.err.println("Error at FilmController-buttonBatalAction, details : "+error.toString());
            JOptionPane.showMessageDialog(sv, "Error at FilmController-buttonBatalAction, details : "+error.toString());
        }
    }
    
    
    
    
    
}
