
package bioskopproject.home;

import static java.awt.Frame.MAXIMIZED_BOTH;
import javax.swing.JOptionPane;
import bioskopproject.film.FilmView;
import bioskopproject.tiket.TiketView;

public class HomeController {
    
    public void setFullScreen(HomeView hv) {
        try {
            hv.setExtendedState(MAXIMIZED_BOTH);
        } catch (Exception error) {
            System.err.println("Error at HomeController-setFullScreen, details : "+error.toString());
           JOptionPane.showMessageDialog(hv, "Error at HomeController-setFullScreen, details : "+error.toString());
        }
    }
    
     public void menuItemKeluarAction(HomeView hv) {
        try {
            int konfirmasi = JOptionPane.showConfirmDialog(hv, "Apakah anda yakin ingin keluar?",
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
            
            if(konfirmasi == JOptionPane.YES_OPTION){
                JOptionPane.showMessageDialog(hv, "Terima Kasih");
                System.exit(0);
            }
        } catch (Exception error) {
             System.err.println("Error at HomeController-menuItemKeluarAction, details : "+error.toString());
           JOptionPane.showMessageDialog(hv, "Error at HomeController-menuItemKeluarAction, details : "+error.toString());
        }
}
     
     public void menuItemFilmAction(HomeView hv) {
        try {
            HomeView.menuItemFilm.setEnabled(false);
            FilmView sv = new FilmView();
            HomeView.panelHome.add(sv);
            sv.setVisible(true);
        } catch (Exception error) {
            System.err.println("Error at HomeController-menuItemFilmAction, details : "+error.toString());
           JOptionPane.showMessageDialog(hv, "Error at HomeController-menuItemFilmAction, details : "+error.toString());
        }
    }
     
      public void menuItemTiketAction(HomeView hv) {
        try {
            HomeView.menuItemTiket.setEnabled(false);
            TiketView tv = new TiketView();
            HomeView.panelHome.add(tv);
            tv.setVisible(true);
        } catch (Exception error) {
            System.err.println("Error at HomeController-menuItemTiketAction, details : "+error.toString());
           JOptionPane.showMessageDialog(hv, "Error at HomeController-menuItemTiketAction, details : "+error.toString());
        }
    }
     
     
}
