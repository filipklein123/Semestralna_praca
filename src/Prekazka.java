import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 * Táto trieda reprezentuje prekážky v hre.
 * Umožňuje inicializáciu a generovanie prekážok na náhodných polohách.
 * Celkovo je táto trieda navrhnutá pre správu a vykresľovanie prekážok v hre.
 * @author Filip Klein
 * @version 2023/12/14
 */

public class Prekazka {
    
    private ArrayList<Policko> prekazky = new ArrayList<>();
    private Policko hlavaHada;
    private int sirkaPlochy;
    private int vyskaPlochy;
    private int velkostPolicka;
    private int pocetPrekazok;

    public Prekazka(int sirkaPlochy, int vyskaPlochy, int velkostPolicka, int pocetPrekazok){
        this.sirkaPlochy = sirkaPlochy;
        this.vyskaPlochy = vyskaPlochy;
        this.velkostPolicka = velkostPolicka;
        this.pocetPrekazok = pocetPrekazok;
        this.prekazky = new ArrayList<>();
        this.generujNahodnePrekazky(this.sirkaPlochy, this.vyskaPlochy, this.velkostPolicka, this.pocetPrekazok);
    }

    public ArrayList<Policko> getPrekazky() {
        return this.prekazky;
    }

    public void generujNahodnePrekazky(int sirkaPlochy, int vyskaPlochy, int velkostPolicka, int pocetPrekazok) {
        this.prekazky.clear();
        Random nahodne = new Random();
        for (int i = 0; i < pocetPrekazok; i++) {
            int x;
            int y;
            do {
                x = nahodne.nextInt(sirkaPlochy / velkostPolicka);
                y = nahodne.nextInt(vyskaPlochy / velkostPolicka);
            } while (this.koliziaHadaSPrekazkou(this.hlavaHada));

            this.prekazky.add(new Policko(x, y));
        }
    }

    public boolean koliziaHadaSPrekazkou(Policko hlavaHada) {
        for (Policko prekazkaPolicko : this.prekazky) {
            if (Snake.kolizia(hlavaHada, prekazkaPolicko)) {
                return true;
            }
        }
        return false;
    }
    
    //vykresli prekazky na vygenerovane polohy
    public void vykresli(Graphics g, int velkostPolicka) {
        for (Policko prekazkaPolicko : this.prekazky) {
            g.setColor(Color.black);
            g.fill3DRect(prekazkaPolicko.getX() * velkostPolicka, prekazkaPolicko.getY() * velkostPolicka, velkostPolicka, velkostPolicka, true);
        }
    }
}