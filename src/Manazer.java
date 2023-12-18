import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Trieda Manazer implementuje rozhranie 'KeyListener' a umožňuje ovládanie hada v hre.
 * Takisto obsahuje aj metódu 'keyPressed' na reakciu a stláčanie kláves a mení smer pohybu hada.
 * Hada je možné ovládať pomocou šípok alebo pomocou kláves WASD, takisto je hru možné pozastaviť klávesou ESC.
 * @author Filip Klein
 * @version 2023/12/14
 */

public class Manazer implements KeyListener {
    private Snake snake;
    private BufferedImage hadikHore;
    private BufferedImage hadikDole;
    private BufferedImage hadikVpravo;
    private BufferedImage hadikVlavo;

    public Manazer(Snake snake) {
        this.snake = snake;
    }
    
    //ovladanie hada - je mozne ho ovladat aj sipkami a takisto aj WASD
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.snake.setHraPozastavena(!this.snake.getHraPozastavena());
        } else if (!this.snake.getHraPozastavena()) {
            Smer novyPohyb = null;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if (this.snake.getRychlostY() != 1) {
                        novyPohyb = Smer.HORE;
                    }
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    if (this.snake.getRychlostY() != -1) {
                        novyPohyb = Smer.DOLE;
                    }
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if (this.snake.getRychlostX() != 1) {
                        novyPohyb = Smer.DOLAVA;
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (this.snake.getRychlostX() != -1) {
                        novyPohyb = Smer.DOPRAVA;
                    }
                    break;
            }

            if (novyPohyb != null) {
                this.snake.setRychlostX(novyPohyb.getRychlostX());
                this.snake.setRychlostY(novyPohyb.getRychlostY());
            }
        }
    }
    
    //had sa stale otoci podla toho, do akeho smeru ide
    public void vykresliHada(Graphics g) {
        try {
            int velkostPolicka = this.snake.getVelkostPolicka();
            Policko hlavaHada = this.snake.getHlavaHada();
            
            if (this.snake.getRychlostY() == -1) {
                // pohyb hore
                this.hadikHore = ImageIO.read(getClass().getResource("foto/hadikHore.png"));
                g.drawImage(this.hadikHore, hlavaHada.getX() * velkostPolicka, hlavaHada.getY() * velkostPolicka, velkostPolicka, velkostPolicka, null);
            } else if (this.snake.getRychlostY() == 1) {
                // pohyb dole
                this.hadikDole = ImageIO.read(getClass().getResource("foto/hadikDole.png"));
                g.drawImage(this.hadikDole, hlavaHada.getX() * velkostPolicka, hlavaHada.getY() * velkostPolicka, velkostPolicka, velkostPolicka, null);
            } else if (this.snake.getRychlostX() == -1) {
                // pohyb vľavo
                this.hadikVlavo = ImageIO.read(getClass().getResource("foto/hadikVlavo.png"));
                g.drawImage(this.hadikVlavo, hlavaHada.getX() * velkostPolicka, hlavaHada.getY() * velkostPolicka, velkostPolicka, velkostPolicka, null);
            } else if (this.snake.getRychlostX() == 1) {
                // pohyb vpravo
                this.hadikVpravo = ImageIO.read(getClass().getResource("foto/hadikVpravo.png"));
                g.drawImage(this.hadikVpravo, hlavaHada.getX() * velkostPolicka, hlavaHada.getY() * velkostPolicka, velkostPolicka, velkostPolicka, null);
            } else {
                //had na zaciatku hry bude otoceny hore
                this.hadikHore = ImageIO.read(getClass().getResource("foto/hadikHore.png"));
                g.drawImage(this.hadikHore, hlavaHada.getX() * velkostPolicka, hlavaHada.getY() * velkostPolicka, velkostPolicka, velkostPolicka, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {
        
    }   
}