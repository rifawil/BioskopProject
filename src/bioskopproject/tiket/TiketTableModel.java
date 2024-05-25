
package bioskopproject.tiket;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class TiketTableModel extends AbstractTableModel{
    
    private List<Tiket> list = new ArrayList<>();
    
    public void insert(Tiket s) {
        list.add(s);
        fireTableDataChanged();
    }
    
    public void update(int row, Tiket s) {
        list.set(row, s);
        fireTableDataChanged();
    }
    
    public void delete(int row) {
        list.remove(row);
        fireTableDataChanged();
    }
    
    public Tiket get(int row) {
        return list.get(row);
    }
    
    public void setList(List<Tiket> list) {
        this.list = list;
       fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return list.size();
    }
    
    @Override
    public int getColumnCount() {
        return 10;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return rowIndex + 1;
            case 1:
                return list.get(rowIndex).getKode_pesanan();
            case 2:
                return list.get(rowIndex).getNama_pemesan();
            case 3:
                return list.get(rowIndex).getTiket_film();
            case 4:
                return list.get(rowIndex).getWaktu();
            case 5:
                return list.get(rowIndex).getHarga_tiket();
            case 6:
                return list.get(rowIndex).getJumlah_pesan();
            case 7:
                return list.get(rowIndex).getJumlah_bayar();
            case 8:
                return list.get(rowIndex).getUang_masuk();
            case 9:
                return list.get(rowIndex).getUang_keluar();
            default:
                return null;
        }
    }
    
    @Override
    public String getColumnName(int column) {
        switch(column){
            case 0:
                return "No";
            case 1:
                return "Kode Pesanan";
            case 2:
                return "Nama Pemesan";
            case 3:
                return "Tiket Film";
            case 4:
                return "Waktu";
            case 5:
                return "Harga Tiket";
            case 6:
                return "Jumlah Pesan";
            case 7:
                return "Jumlah Bayar";
            case 8:
                return "Uang Masuk";
            case 9:
                return "Kembalian";
            default:
                return null;
        }
    }
    
}
