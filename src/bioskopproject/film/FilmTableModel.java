
package bioskopproject.film;

import bioskopproject.tiket.Tiket;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class FilmTableModel extends AbstractTableModel{
    
    private List<Film> list = new ArrayList<>();
    
    public void insert(Film s) {
        list.add(s);
        fireTableDataChanged();
    }
    
    public void update(int row, Film s) {
        list.set(row, s);
        fireTableDataChanged();
    }
    
    public void delete(int row) {
        list.remove(row);
        fireTableDataChanged();
    }
    
    public Film get(int row) {
        return list.get(row);
    }
    
    public void setList(List<Film> list) {
        this.list = list;
       fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return list.size();
    }
    
    @Override
    public int getColumnCount() {
        return 4;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return rowIndex + 1;
            case 1:
                return list.get(rowIndex).getTiket_film();
            case 2:
                return list.get(rowIndex).getGenre();
            case 3:
                return list.get(rowIndex).getSinopsis();
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
                return "Tiket Film";
            case 2:
                return "Genre";
            case 3:
                return "Sinopsis";
            default:
                return null;
        }
    }
}
