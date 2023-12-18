/**
 * Táto trieda reprezentuje jedno políčko alebo pozíciu na hracej ploche.
 * Obsahuje atribúty 'x' a 'y' predstavujúce súradnice políčka.
 * @author Filip Klein
 * @version 2023/12/14
 */

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