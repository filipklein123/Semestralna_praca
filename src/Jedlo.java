import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Táto trieda reprezentuje jedlo v hre.
 * Obsahuje atribúty pree umiestnenie jedla na hracej ploche, kontrolu kolízií s prekážkami a vykresľovanie obrázka jablka na pozícií jedla.
 * Celkovo je trieda zodpovedná za správu jedla v hre a zabezpečuje jeho správne umiestnenie a vizuálnu reprezentáciu.
 * @author Filip Klein
 * @version 2023/12/14
 */

public class Jedlo {

    private Policko jedlo;
    private BufferedImage jablkoImage;

    //umiestnenie jedla na nahodnu poziciu, takisto zabezpecuje, aby sa jedlo nedostalo do kolizie s prekazkami
    public void umiestniJedlo(int sirkaPlochy, int vyskaPlochy, int velkostPolicka, Prekazka prekazky) {
        Random nahodne = new Random();
        do {
            int x = nahodne.nextInt(sirkaPlochy / velkostPolicka);
            int y = nahodne.nextInt(vyskaPlochy / velkostPolicka);
            this.jedlo = new Policko(x, y);
        } while (this.koliziaJedlaSPrekazkou(prekazky));
    }
    
    //kontroluje ci sa stala kolizia
    public boolean koliziaJedlaSPrekazkou(Prekazka prekazky) {
        for (Policko prekazka : prekazky.getPrekazky()) {
            if (this.jedlo.getX() == prekazka.getX() && this.jedlo.getY() == prekazka.getY()) {
                return true;
            }
        }
        return false;
    }
    
    //vykresluje obrazok jablka na umiestneni jedla
    public void vykresli(Graphics g, int velkostPolicka) {
        try {
            this.jablkoImage = ImageIO.read(getClass().getResource("foto/jablko.png"));
            g.drawImage(this.jablkoImage, this.jedlo.getX() * velkostPolicka, this.jedlo.getY() * velkostPolicka, velkostPolicka, velkostPolicka, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Policko getPolicko() {
        return this.jedlo;
    }
}