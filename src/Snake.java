import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;
import javax.swing.JPanel;

/**
 * Táto trieda využíva Swing.
 * Logika tejto triedy zahŕňa atribúty ako napr. nastavenie hracej plochy a obsahuje metódy na poskytnutie informácií o stave hry.
 * Celá táto trieda je prepojom medzi všetkými triedami, teda berie sa ako hlavná.
 * @author Filip Klein
 * @version 2023/12/14
 */

public class Snake extends JPanel implements ActionListener, KeyListener {
    //atributy
    private int sirkaPlochy;
    private int vyskaPlochy;
    private int velkostPolicka;
    private int pocetPrekazok;
    private int rychlostX;
    private int rychlostY;

    private boolean koniecHry = false;
    private boolean hraPozastavena = false;
    private long casOdStartu;
    private long casKedyPozastavena;
    private boolean casZastaveny;
    private long casHry;
    
    private Zvuk zvuk;
    private Policko hlavaHada;
    private HraciaPlocha hraciaPlocha;
    private ArrayList<Policko> teloHada;
    private Jedlo jedlo;
    private Random nahodne;
    private Timer hernyCyklus;
    private Prekazka prekazky;
    private Manazer manazer;
    
    //konstruktor
    public Snake(int sirkaPlochy, int vyskaPlochy, int rychlostHry, int pocetPrekazok, int velkostPolicka) {
        //inicializacia atributov
        this.vyskaPlochy = vyskaPlochy;
        this.sirkaPlochy = sirkaPlochy;
        this.pocetPrekazok = pocetPrekazok;
        this.velkostPolicka = velkostPolicka;
        this.zvuk = new Zvuk();
        this.manazer = new Manazer(this);
        this.nahodne = new Random();

        //nastavenie jpanel
        setPreferredSize(new Dimension(this.sirkaPlochy, this.vyskaPlochy));
        addKeyListener(this.manazer);
        setFocusable(true);

        //inicializacia hernych atributov
        this.hraciaPlocha = new HraciaPlocha(sirkaPlochy, vyskaPlochy, velkostPolicka, this);
        this.prekazky = new Prekazka(sirkaPlochy, vyskaPlochy, velkostPolicka, pocetPrekazok);
        this.prekazky.generujNahodnePrekazky(sirkaPlochy, vyskaPlochy, velkostPolicka, pocetPrekazok);
        this.hlavaHada = this.generujNahodnePolicko();
        this.teloHada = new ArrayList<>();
        this.casOdStartu = System.currentTimeMillis();
        this.casKedyPozastavena = casOdStartu;
        this.casZastaveny = false;
        this.jedlo = new Jedlo();
        this.nahodne = new Random();
        this.jedlo.umiestniJedlo(sirkaPlochy, vyskaPlochy, velkostPolicka, this.prekazky);
        this.rychlostX = 0;
        this.rychlostY = 0;
        this.hernyCyklus = new Timer(rychlostHry, this);
        this.hernyCyklus.start();
    }

    //vykreslenie komponentov
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.vykresli(g);
    }
    
    //vykreslenie hernych prvkov
    public void vykresli(Graphics g) {
        this.hraciaPlocha.vykresli(g);
        this.jedlo.vykresli(g, this.velkostPolicka);
        this.manazer.vykresliHada(g);
        this.prekazky.vykresli(g, this.velkostPolicka);
                
        //telo hada
        g.setColor(new Color(108, 220, 156));
        for (Policko castHada : this.teloHada) {
            g.fillOval(castHada.getX() * this.velkostPolicka, castHada.getY() * this.velkostPolicka, this.velkostPolicka, this.velkostPolicka);
        }
    }

    //umiestnenie jedla
    public void umiestniJedlo() {
        this.jedlo.umiestniJedlo(this.sirkaPlochy, this.vyskaPlochy, this.velkostPolicka, this.prekazky);
    }

    //pohyb hada
    public void pohyb() {
        if (!this.hraPozastavena) {
            if (kolizia(this.hlavaHada, this.jedlo.getPolicko())) {
                this.teloHada.add(new Policko(this.jedlo.getPolicko().getX(), this.jedlo.getPolicko().getY()));
                this.umiestniJedlo();
                this.zvuk.zvukJedla();
            }

            //ked had zoberie jedlo, kazda cast tela sa presunie na jej predchadzajucu poziciu
            for (int i = this.teloHada.size() - 1; i >= 0; i--) {
                if (i == 0) {
                    this.teloHada.get(i).setX(this.hlavaHada.getX());
                    this.teloHada.get(i).setY(this.hlavaHada.getY()); 
                } else {
                    this.teloHada.get(i).setX(this.teloHada.get(i - 1).getX());
                    this.teloHada.get(i).setY(this.teloHada.get(i - 1).getY());
                }
            }

            //kontroluje koliziu s okrajmi hracej plochy
            if (this.hlavaHada.getX() < 0 || this.hlavaHada.getX() >= this.sirkaPlochy / this.velkostPolicka || this.hlavaHada.getY() < 0 || this.hlavaHada.getY() >= this.vyskaPlochy / this.velkostPolicka) {
                this.koniecHry = true;
            }

            //pohyb hlavy hada
            this.hlavaHada.setX(this.hlavaHada.getX() + rychlostX);
            this.hlavaHada.setY(this.hlavaHada.getY() + rychlostY);

            //kontrola kolizie hlavy hada s jeho telom
            for (Policko castHada : this.teloHada) {
                if (kolizia(this.hlavaHada, castHada)) {
                    this.koniecHry = true;
                }
            }

            //kontrola kolizie hlavyHada s prekazkou
            if (this.prekazky.koliziaHadaSPrekazkou(this.hlavaHada)) {
                this.koniecHry = true;
            }

            //kontrola kolizie jedla s prekazkou
            if (this.jedlo.koliziaJedlaSPrekazkou(this.prekazky)) {
                this.umiestniJedlo();
            }
        }
    }

    //tato metoda sluzi na detekciu kolizie medzi dvoma objektami
    public static boolean kolizia(Object objekt1, Object objekt2) {
        if (objekt1 instanceof Policko && objekt2 instanceof Policko) {
            Policko policko1 = (Policko)objekt1;
            Policko policko2 = (Policko)objekt2;
            return policko1.getX() == policko2.getX() && policko1.getY() == policko2.getY();
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.pohyb();
        repaint();

        //kontrola ci skoncila hra
        if (this.koniecHry) {
            Aplikacia aplikacia = new Aplikacia();
            aplikacia.prehralSi(this, this.prekazky);
        }
    }

    public void keyPressed(KeyEvent e) {
        this.manazer.keyPressed(e);
    }

    //gettery a settery pre ostatne triedy
    public Policko getHlavaHada() {
        return this.hlavaHada;
    }

    public void setHlavaHada(Policko hlavaHada) {
        this.hlavaHada = hlavaHada;
    }

    public Policko generujNahodnePolicko() {
        return new Policko(this.nahodne.nextInt(this.sirkaPlochy / velkostPolicka), this.nahodne.nextInt(this.vyskaPlochy / velkostPolicka));
    }

    public int getSirkaPlochy() {
        return this.sirkaPlochy;
    }

    public int getVyskaPlochy() {
        return this.vyskaPlochy;
    }

    public int getPocetPrekazok() {
        return this.pocetPrekazok;
    }

    public int getVelkostPolicka() {
        return this.velkostPolicka;
    }

    public Timer getHernyCyklus() {
        return this.hernyCyklus;
    }    

    public long getCasKedyPozastavena() {
        return this.casKedyPozastavena;
    }

    public void setCasKedyPozastavena(long casKedyPozastavena) {
        this.casKedyPozastavena = casKedyPozastavena;
    }

    public boolean getCasZastaveny() {
        return this.casZastaveny;
    }

    public void setCasZastaveny(boolean casZastaveny) {
        this.casZastaveny = casZastaveny;
    }

    public long getCasOdStartu() {
        return this.casOdStartu;
    }

    public void setCasOdStartu(long casOdStartu) {
        this.casOdStartu = casOdStartu;
    }

    public long getCasHry() {
        return this.casHry;
    }

    public void setCasHry(long casHry) {
        this.casHry = casHry;
    }

    public ArrayList<Policko> getTeloHada() {
        return this.teloHada;
    }

    public boolean getHraPozastavena() {
        return this.hraPozastavena;
    }

    public void setHraPozastavena(boolean hraPozastavena) {
        this.hraPozastavena = hraPozastavena;
    }

    public int getRychlostX() {
        return this.rychlostX;
    }

    public void setRychlostX(int rychlostX) {
        this.rychlostX = rychlostX;
    }

    public int getRychlostY() {
        return this.rychlostY;
    }
    
    public void setRychlostY(int rychlostY) {
        this.rychlostY = rychlostY;
    }

    public boolean getKoniecHry() {
        return this.koniecHry;
    }

    public void setKoniecHry(boolean koniecHry) {
        this.koniecHry = koniecHry;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }    
}