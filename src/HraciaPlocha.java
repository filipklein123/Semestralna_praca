import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Táto trieda sa stará o vizuálnu reprezentáciu hracej plochy hada.
 * Stará sa aj o vykreslenie hracej plochy s mriežkou a šachovnicovým vzorom.
 * Zobrazuje informácie o priebehu hry (dĺžka hada, čas hry). Teda hra poskytuje hráčovi prehľad o aktuálnom stave hry.
 * @author Filip Klein
 * @version 2023/12/14
 */

public class HraciaPlocha {

    private int sirkaPlochy;
    private int vyskaPlochy;
    private int velkostPolicka;
    private Snake snake;

    public HraciaPlocha(int sirkaPlochy, int vyskaPlochy, int velkostPolicka, Snake snake) {
        this.sirkaPlochy = sirkaPlochy;
        this.vyskaPlochy = vyskaPlochy;
        this.velkostPolicka = velkostPolicka;
        this.snake = snake;
    }

    public void vykresli(Graphics g) {
        //mriezka hracieho pola
        for (int i = 0; i < this.sirkaPlochy / velkostPolicka; i++) {
            g.setColor(new Color(245, 205, 167));
            g.drawLine(i * velkostPolicka, 0, i * velkostPolicka, this.vyskaPlochy);
            g.drawLine(0, i * velkostPolicka, this.sirkaPlochy, i * velkostPolicka);
        }

        //hracie pole v sachovnicovom style
        for (int i = 0; i < this.sirkaPlochy / velkostPolicka; i++) {
            for (int j = 0; j < this.vyskaPlochy / velkostPolicka; j++) {
                if ((i + j) % 2 == 0) {
                    g.setColor(new Color(255, 218, 142));
                } else {
                    g.setColor(new Color(250, 213, 138));
                }
                g.fillRect(i * this.velkostPolicka, j * this.velkostPolicka, this.velkostPolicka, this.velkostPolicka);
            }
        }

        //vykreslenie informacii o hre
        String bodovyText = String.format("Dĺžka hada: %d | Čas v hre: %d s", this.snake.getTeloHada().size(), this.snake.getCasHry() / 1000);
        long casTeraz = System.currentTimeMillis();

        g.setColor(Color.blue);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(bodovyText, this.velkostPolicka - 16, this.velkostPolicka);

        //zaistuje to, ze ak hru pausneme, tak sa cas takisto zastavi... pri opatovnom spusteni hry bude cas rovnaky, ako pred pozastavenim hry
        if (this.snake.getHraPozastavena() && !this.snake.getCasZastaveny()) {
            this.snake.setCasKedyPozastavena(casTeraz);
            this.snake.setCasZastaveny(true);
        } else if (!this.snake.getHraPozastavena() && this.snake.getCasZastaveny()) {
            long casOdSpusteniaPoPozastaveni = (casTeraz - this.snake.getCasKedyPozastavena());
            if (casOdSpusteniaPoPozastaveni > 0) {
                this.snake.setCasOdStartu(this.snake.getCasOdStartu() + casOdSpusteniaPoPozastaveni);
            }

            this.snake.setCasZastaveny(false);
        }

        //kontroluje sa, ci hra je pozastavena
        if (!this.snake.getHraPozastavena()) {
            this.snake.setCasHry(casTeraz - this.snake.getCasOdStartu());
            g.drawString(bodovyText, this.velkostPolicka - 16, this.velkostPolicka);
        }

        // vypisanie hlasok
        if (this.snake.getKoniecHry()) {
            g.setColor(Color.red);
            g.drawString("Koniec hry!", this.velkostPolicka - 16, this.velkostPolicka * 2);
        } else if (this.snake.getHraPozastavena()) {
            g.setColor(Color.black);
            g.drawString("Hra je pozastavená, stlačením klávesy ESC sa vrátite do hry.", this.velkostPolicka - 16, this.velkostPolicka * 2);
        }

    }
}
