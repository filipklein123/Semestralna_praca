# Snake obhajoba 21.12.23 - 12:30

- [Snake obhajoba 21.12.23 - 12:30](#snake-obhajoba-211223---1230)
  - [Aplikacia.java](#aplikaciajava)
    - [atribúty, konštruktor, main(String\[\] args)](#atribúty-konštruktor-mainstring-args)
    - [uvodneOkno()](#uvodneokno)
    - [nastavenieObtiaznosti()](#nastavenieobtiaznosti)
    - [vlastnaObtiaznost()](#vlastnaobtiaznost)
    - [prevratenieRychlosti(int tempoHry)](#prevratenierychlostiint-tempohry)
    - [prehralSi(Snake snake, Prekazka prekazka)](#prehralsisnake-snake-prekazka-prekazka)
    - [spustiHru(int pocetPrekazok, int rychlostHry)](#spustihruint-pocetprekazok-int-rychlosthry)
    - [restartujHru(Snake snake, Prekazka prekazka)](#restartujhrusnake-snake-prekazka-prekazka)
  - [HraciaPlocha.java](#hraciaplochajava)
    - [atribúty \& konštruktor](#atribúty--konštruktor)
    - [vykresli(Graphics g)](#vykresligraphics-g)
      - [mriežka hracieho poľa](#mriežka-hracieho-poľa)
      - [hracie pole šachovnicovom štýle](#hracie-pole-šachovnicovom-štýle)
      - [vykreslenie informácií o hre](#vykreslenie-informácií-o-hre)
      - [kontroluje sa, či hra je pozastavená \& vypísanie hlášok](#kontroluje-sa-či-hra-je-pozastavená--vypísanie-hlášok)
  - [Jedlo.java](#jedlojava)
    - [umiestniJedlo(int sirkaPlchy, int vyskaPlochy, int velkostPolicka, Prekazka prekazky)](#umiestnijedloint-sirkaplchy-int-vyskaplochy-int-velkostpolicka-prekazka-prekazky)
    - [koliziaJedlaSPrekazkou(Prekazka prekazky)](#koliziajedlasprekazkouprekazka-prekazky)
    - [vykresli(Graphics g, int velkostPolicka)](#vykresligraphics-g-int-velkostpolicka)
  - [Manazer.java](#manazerjava)
    - [keyPressed(KeyEvent e)](#keypressedkeyevent-e)
    - [vykresliHada(Graphics g)](#vykreslihadagraphics-g)
  - [Policko.java](#polickojava)
  - [Prekazka.java](#prekazkajava)
    - [atribúty \&\& konštruktor](#atribúty--konštruktor-1)
    - [generujNahodnePrekazky(int sirkaPlochy, int vyskaPlochy, int velkostPolicka, int pocetPrekazok)](#generujnahodneprekazkyint-sirkaplochy-int-vyskaplochy-int-velkostpolicka-int-pocetprekazok)
    - [koliziaHadaSPrekazkou(Policko hlavaHada)](#koliziahadasprekazkoupolicko-hlavahada)
    - [vykresli (Graphics g, int velkostPolicka)](#vykresli-graphics-g-int-velkostpolicka)
  - [Smer.java](#smerjava)
  - [Snake.java](#snakejava)
    - [atribúty](#atribúty)
    - [konštruktor Snake(int sirkaPlochy, int vyskaPlochy, int rychlostHry, int pocetPrekazok, int velkostPolicka)](#konštruktor-snakeint-sirkaplochy-int-vyskaplochy-int-rychlosthry-int-pocetprekazok-int-velkostpolicka)
    - [paintComponeent(Graphics g)](#paintcomponeentgraphics-g)
    - [vykresli (Graphics g)](#vykresli-graphics-g)
    - [umiestniJedlo()](#umiestnijedlo)
    - [pohyb()](#pohyb)
      - [kolízia hlavyHada a jedla](#kolízia-hlavyhada-a-jedla)
      - [časť jedla sa presunie na jej predchádzajúcu pozíciu](#časť-jedla-sa-presunie-na-jej-predchádzajúcu-pozíciu)
      - [kontrola kolízie s okrajmi hracej plochy](#kontrola-kolízie-s-okrajmi-hracej-plochy)
      - [pohyb hlavyHada](#pohyb-hlavyhada)
      - [kontrola kolízie hlavyHada s telom](#kontrola-kolízie-hlavyhada-s-telom)
      - [kontrola kolízie hlavyHada s prekážkou](#kontrola-kolízie-hlavyhada-s-prekážkou)
      - [kontrola kolízie jedla s prekážkou](#kontrola-kolízie-jedla-s-prekážkou)
    - [kolizia(Object objekt1, Object objekt2)](#koliziaobject-objekt1-object-objekt2)
    - [actionPerformed(ActionEvent e)](#actionperformedactionevent-e)
    - [keyPressed (KeyEvent e)](#keypressed-keyevent-e)
    - [generujNahodnePolicko()](#generujnahodnepolicko)

## Aplikacia.java

### atribúty, konštruktor, main(String[] args)

```java
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
```

Popis:

- dopísať

### uvodneOkno()

```java
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
```

Popis:

- dopísať

### nastavenieObtiaznosti()

```java
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
```

Popis:

- dopísať

### vlastnaObtiaznost()

```java
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
```

Popis:

- dopísať

### prevratenieRychlosti(int tempoHry)

```java
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
```

Popis:

- dopísať

### prehralSi(Snake snake, Prekazka prekazka)

```java
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
```

Popis:

- dopísať

### spustiHru(int pocetPrekazok, int rychlostHry)

```java
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
```

Popis:

- dopísať

### restartujHru(Snake snake, Prekazka prekazka)

```java
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
```

Popis:

- dopísať

## HraciaPlocha.java

### atribúty & konštruktor

```java
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
```

Popis:

- dopísať

### vykresli(Graphics g)

#### mriežka hracieho poľa

```java
for (int i = 0; i < this.sirkaPlochy / velkostPolicka; i++) {
    g.setColor(new Color(245, 205, 167));
    g.drawLine(i * velkostPolicka, 0, i * velkostPolicka, this.vyskaPlochy);
    g.drawLine(0, i * velkostPolicka, this.sirkaPlochy, i * velkostPolicka);
}
```

Popis:

- dopísať

#### hracie pole šachovnicovom štýle

```java
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
```

Popis:

- dopísať

#### vykreslenie informácií o hre

```java
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
```

Popis:

- dopísať

#### kontroluje sa, či hra je pozastavená & vypísanie hlášok

```java
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
```

Popis:

- dopísať

## Jedlo.java

### umiestniJedlo(int sirkaPlchy, int vyskaPlochy, int velkostPolicka, Prekazka prekazky)

```java
public void umiestniJedlo(int sirkaPlochy, int vyskaPlochy, int velkostPolicka, Prekazka prekazky) {
    Random nahodne = new Random();
    do {
        int x = nahodne.nextInt(sirkaPlochy / velkostPolicka);
        int y = nahodne.nextInt(vyskaPlochy / velkostPolicka);
        this.jedlo = new Policko(x, y);
    } while (this.koliziaJedlaSPrekazkou(prekazky));
}
```

Popis:

- dopísať

### koliziaJedlaSPrekazkou(Prekazka prekazky)

```java
public boolean koliziaJedlaSPrekazkou(Prekazka prekazky) {
    for (Policko prekazka : prekazky.getPrekazky()) {
        if (this.jedlo.getX() == prekazka.getX() && this.jedlo.getY() == prekazka.getY()) {
            return true;
        }
    }
    return false;
}
```

Popis:

- dopísať

### vykresli(Graphics g, int velkostPolicka)

```java
public void vykresli(Graphics g, int velkostPolicka) {
    try {
        this.jablkoImage = ImageIO.read(getClass().getResource("foto/jablko.png"));
        g.drawImage(this.jablkoImage, this.jedlo.getX() * velkostPolicka, this.jedlo.getY() * velkostPolicka, velkostPolicka, velkostPolicka, null);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

Popis:

- dopísať

## Manazer.java

### keyPressed(KeyEvent e)

```java
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
```

Popis:

- dopísať

### vykresliHada(Graphics g)

```java
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
```

Popis:

- dopísať

## Policko.java

```java
public class Policko {
    private int x;
    private int y;

    public Policko(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
```

Popis:

- dopísať

## Prekazka.java

### atribúty && konštruktor

```java
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
```

Popis:

- dopísať

### generujNahodnePrekazky(int sirkaPlochy, int vyskaPlochy, int velkostPolicka, int pocetPrekazok)

```java
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
```

Popis:

- dopísať

### koliziaHadaSPrekazkou(Policko hlavaHada)

```java
public boolean koliziaHadaSPrekazkou(Policko hlavaHada) {
    for (Policko prekazkaPolicko : this.prekazky) {
        if (Snake.kolizia(hlavaHada, prekazkaPolicko)) {
            return true;
        }
    }
    return false;
}
```

Popis:

- dopísať

### vykresli (Graphics g, int velkostPolicka)

```java
public void vykresli(Graphics g, int velkostPolicka) {
    for (Policko prekazkaPolicko : this.prekazky) {
        g.setColor(Color.black);
        g.fill3DRect(prekazkaPolicko.getX() * velkostPolicka, prekazkaPolicko.getY() * velkostPolicka, velkostPolicka, velkostPolicka, true);
    }
}
```

Popis:

- dopísať

## Smer.java

```java
public enum Smer {
    HORE(0, -1),
    DOLE(0, 1),
    DOLAVA(-1, 0),
    DOPRAVA(1, 0);
    
    private int rychlostX;
    private int rychlostY;

    Smer (int rychlostX, int rychlostY) {
        this.rychlostX = rychlostX;
        this.rychlostY = rychlostY;
    }
    
    public int getRychlostX() {
        return this.rychlostX;
    }

    public int getRychlostY() {
        return this.rychlostY;
    }
}
```

Popis:

- dopísať

## Snake.java

### atribúty

```java
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
```

Popis:

- dopísať

### konštruktor Snake(int sirkaPlochy, int vyskaPlochy, int rychlostHry, int pocetPrekazok, int velkostPolicka)

```java
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
```

Popis:

- dopísať

### paintComponeent(Graphics g)

```java
public void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.vykresli(g);
}
```

Popis:

- dopísať

### vykresli (Graphics g)

```java
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
```

Popis:

- dopísať

### umiestniJedlo()

```java
public void umiestniJedlo() {
    this.jedlo.umiestniJedlo(this.sirkaPlochy, this.vyskaPlochy, this.velkostPolicka, this.prekazky);
}
```

Popis:

- dopísať

### pohyb()

#### kolízia hlavyHada a jedla

```java
if (kolizia(this.hlavaHada, this.jedlo.getPolicko())) {
    this.teloHada.add(new Policko(this.jedlo.getPolicko().getX(), this.jedlo.getPolicko().getY()));
    this.umiestniJedlo();
    this.zvuk.zvukJedla();
}
```

Popis:

- dopísať

#### časť jedla sa presunie na jej predchádzajúcu pozíciu

```java
for (int i = this.teloHada.size() - 1; i >= 0; i--) {
    if (i == 0) {
        this.teloHada.get(i).setX(this.hlavaHada.getX());
        this.teloHada.get(i).setY(this.hlavaHada.getY()); 
    } else {
        this.teloHada.get(i).setX(this.teloHada.get(i - 1).getX());
        this.teloHada.get(i).setY(this.teloHada.get(i - 1).getY());
    }
}
```

Popis:

- dopísať

#### kontrola kolízie s okrajmi hracej plochy

```java
if (this.hlavaHada.getX() < 0 || this.hlavaHada.getX() >= this.sirkaPlochy / this.velkostPolicka || this.hlavaHada.getY() < 0 || this.hlavaHada.getY() >= this.vyskaPlochy / this.velkostPolicka) {
    this.koniecHry = true;
}
```

Popis:

- dopísať

#### pohyb hlavyHada

```java
this.hlavaHada.setX(this.hlavaHada.getX() + rychlostX);
this.hlavaHada.setY(this.hlavaHada.getY() + rychlostY);
```

Popis:

- dopísať

#### kontrola kolízie hlavyHada s telom

```java
for (Policko castHada : this.teloHada) {
    if (kolizia(this.hlavaHada, castHada)) {
        this.koniecHry = true;
    }
}
```

Popis:

- dopísať

#### kontrola kolízie hlavyHada s prekážkou

```java
if (this.prekazky.koliziaHadaSPrekazkou(this.hlavaHada)) {
    this.koniecHry = true;
}
```

Popis:

- dopísať

#### kontrola kolízie jedla s prekážkou

```java
if (this.jedlo.koliziaJedlaSPrekazkou(this.prekazky)) {
    this.umiestniJedlo();
}
```

Popis:

- dopísať

### kolizia(Object objekt1, Object objekt2)

```java
public static boolean kolizia(Object objekt1, Object objekt2) {
    if (objekt1 instanceof Policko && objekt2 instanceof Policko) {
        Policko policko1 = (Policko)objekt1;
        Policko policko2 = (Policko)objekt2;
        return policko1.getX() == policko2.getX() && policko1.getY() == policko2.getY();
    }
    return false;
}
```

Popis:

- dopísať

### actionPerformed(ActionEvent e)

```java
public void actionPerformed(ActionEvent e) {
    this.pohyb();
    repaint();

    //kontrola ci skoncila hra
    if (this.koniecHry) {
        Aplikacia aplikacia = new Aplikacia();
        aplikacia.prehralSi(this, this.prekazky);
    }
}
```

Popis:

- dopísať

### keyPressed (KeyEvent e)

```java
public void keyPressed(KeyEvent e) {
    this.manazer.keyPressed(e);
}
```

Popis:

- dopísať

### generujNahodnePolicko()

```java
public Policko generujNahodnePolicko() {
    return new Policko(this.nahodne.nextInt(this.sirkaPlochy / velkostPolicka), this.nahodne.nextInt(this.vyskaPlochy / velkostPolicka));
}
```

Popis:

- dopísať
