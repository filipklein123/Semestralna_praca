import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Táto trieda sa stará o spúšťanie celej hry pomocou 'main' metódy, ktorá hráča odkáže na úvodné okno hry s inštrukciami.
 * Takisto si hráč môže vybrať obtiažnosť hry akú chce.
 * Sú dané tri klasické obtiažnosti: 'ľahká', 'stredná' a 'ťažká' a ak by hráčovi nevyhovovala žiadna z týchto obtiažnosti, je pridaná aj štvrtá obtiažnosť - 'vlastná'.
 * Vo vlastnej obtiažnosti si hráč vyberie počet prekážok a takisto aj rýchlosť hry.
 * @author Filip Klein
 * @version 2023/12/14
 */

//skusam 
public class Aplikacia {
    
    private Zvuk zvuk;
    
    public Aplikacia() {
        this.zvuk = new Zvuk();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Aplikacia aplikacia = new Aplikacia();
            aplikacia.uvodneOkno();
        });
    }

    //uvodne okno sa zobrazi hned po spusteni hry...
    public void uvodneOkno() {
        JPanel panel = new JPanel();
        JLabel uvodnyText = new JLabel("<html><b>Inštrukcie hry:</b><br>"
                + "Na pohyb hada použite šípky alebo klávesy WASD.<br>"
                + "Klávesa ESC slúži na pozastavenie hry.<br>"
                + "<br>"
                + "V ďalšom kroku si vyberte preferovanú obtiažnosť hry.</html>");
        panel.add(uvodnyText);

        Object[] options = {"Ďalej", "Koniec"};

        int vysledok = JOptionPane.showOptionDialog(
            null,
            panel,
            "Vitajte v hre Snake!",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[0]);

        if (vysledok == 0) {
            this.nastavenieObtiaznosti();
        } else if (vysledok == 1) {
            System.exit(0);
        }
    }
    
    //hrac ma 4 moznosti na vyber obtiaznosti 
    public void nastavenieObtiaznosti() {
        String[] vyberObtiaznost = {"Vlastná", "Ťažká", "Stredná", "Ľahká"};
        int obtiaznosti = JOptionPane.showOptionDialog(null, "Vyberte si obtiažnosť hry.", 
                            "Nastavenie obtiažnosti", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, 
                            null, vyberObtiaznost, vyberObtiaznost[0]);
    
        switch (obtiaznosti) {
            case 0:
                // vlastna
                this.vlastnaObtiaznost();
                break;
            case 1: 
                // tazka
                this.spustiHru(15, 60);
                break;
            case 2:
                // stredna
                this.spustiHru(10, 90);
                break;
            case 3:
                // lahka
                this.spustiHru(5, 120);
                break;
            default:
                System.exit(0);
        }
    }

    //je k dispozicii si vybrat aj vlastnu obtiaznost, kde si hrac nastavi kolko chce prekazok a takisto aku chce rychlu hru
    public void vlastnaObtiaznost() {
        JTextField pocetPrekazokPolicko = new JTextField();
        JTextField tempoHryPolicko = new JTextField();

        tempoHryPolicko.setForeground(Color.GRAY);
        tempoHryPolicko.setText("Zadaj 1 až 10");
        tempoHryPolicko.addFocusListener(new FocusAdapter() {
            
            //takto vyzera policko, ktore je aktivne
            @Override
            public void focusGained(FocusEvent e) {
                if (tempoHryPolicko.getText().equals("Zadaj 1 až 10")) {
                    tempoHryPolicko.setText("");
                    tempoHryPolicko.setForeground(Color.BLACK);
                }
            }
            
            //takto vyzera policko, ked je neaktivne
            @Override
            public void focusLost(FocusEvent e) {
                if (tempoHryPolicko.getText().isEmpty()) {
                    tempoHryPolicko.setForeground(Color.GRAY);
                    tempoHryPolicko.setText("Zadaj 1 až 10");
                }
            }
        });

        //samotne okno na vpisanie zelanych hodnot (pocet prekazok & rychlost hry) 
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Počet prekážok:"));
        panel.add(pocetPrekazokPolicko);
        panel.add(new JLabel("Tempo hry:"));
        panel.add(tempoHryPolicko);

        UIManager.put("OptionPane.okButtonText", "Hrať");
        UIManager.put("OptionPane.cancelButtonText", "Späť");

        int vysledok = JOptionPane.showConfirmDialog(null, panel, "Vlastná obtiažnosť",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (vysledok == JOptionPane.OK_OPTION) {
            try {
                int pocetPrekazok = Integer.parseInt(pocetPrekazokPolicko.getText());
                int tempoHry = this.prevratenieRychlosti(Integer.parseInt(tempoHryPolicko.getText()));
                this.spustiHru(pocetPrekazok, tempoHry);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Zadajte číselnú hodnotu.", "Chyba", JOptionPane.ERROR_MESSAGE);
                this.vlastnaObtiaznost();
            }
        } else {
            this.nastavenieObtiaznosti();
        }
    }
    
    public int prevratenieRychlosti(int tempoHry) {
        int minRychlost = 25;
        int maxRychlost = 300;
        int prevratenaRychlost;

        if (tempoHry >= 10) {
            prevratenaRychlost = 1;
        } else {
            prevratenaRychlost = 120 - tempoHry * 12;
        }
        return Math.min(maxRychlost, Math.max(minRychlost, prevratenaRychlost));
    } 
    
    public void prehralSi(Snake snake, Prekazka prekazky) {
        this.zvuk.zvukKoncaHry();
        snake.getHernyCyklus().stop();
        Object[] options = {"Opakovať", "Skončiť", "Zmeniť obtiažnosť"};
        int odpoved = JOptionPane.showOptionDialog(snake, "Prehral si! Čo chceš urobiť?", "Koniec hry", 
                      JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (odpoved == JOptionPane.YES_OPTION) {
            this.restartujHru(snake, prekazky);
        } else if (odpoved == JOptionPane.CANCEL_OPTION) {
            //uzatvorenie predosleho herneho okna pri zmene obtiaznosti
            JFrame okno = (JFrame)SwingUtilities.getRoot(snake);
            okno.dispose();
            this.nastavenieObtiaznosti();
        } else {
            System.exit(0);
        }
    }

    public void spustiHru(int pocetPrekazok, int rychlostHry) {        
        int sirkaPlochy = 1400;
        int vyskaPlochy = 800;
        int velkostPolicka = 40;

        Prekazka prekazka = new Prekazka(sirkaPlochy, vyskaPlochy, velkostPolicka, pocetPrekazok);
        prekazka.generujNahodnePrekazky(sirkaPlochy, vyskaPlochy, rychlostHry, pocetPrekazok);

        JFrame okno = new JFrame("Snake - Semestrálna práca - Filip Klein");
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Snake snake = new Snake(sirkaPlochy, vyskaPlochy, rychlostHry, pocetPrekazok, velkostPolicka);
        okno.add(snake);
        okno.pack();
        okno.setLocationRelativeTo(null);
        okno.setResizable(false);
        okno.setVisible(true);
    }

    public void restartujHru(Snake snake, Prekazka prekazky) {
        snake.setHlavaHada(snake.generujNahodnePolicko());
        snake.getTeloHada().clear();
        snake.umiestniJedlo();
        
        prekazky.getPrekazky().clear();
        prekazky.generujNahodnePrekazky(snake.getSirkaPlochy(), snake.getVyskaPlochy(), snake.getVelkostPolicka(), snake.getPocetPrekazok());
        
        if (prekazky.getPrekazky().isEmpty()) {
            prekazky.generujNahodnePrekazky(snake.getSirkaPlochy(), snake.getVyskaPlochy(), snake.getVelkostPolicka(), snake.getPocetPrekazok());
        }

        snake.setKoniecHry(false);
        snake.setHraPozastavena(false);
        snake.getHernyCyklus().start();
        
        snake.setRychlostX(0);
        snake.setRychlostY(0);
        snake.setCasZastaveny(false);
        snake.setCasOdStartu(System.currentTimeMillis());
    }
}