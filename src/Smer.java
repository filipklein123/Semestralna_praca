/**
 * Trieda enum, ktorá reprezentuje možné smery pohybu hada v hre.
 * Obsahuje štyri konštanty: 'HORE', 'DOLE', 'DOLAVA', 'DOPRAVA'
 * Celkovo to uľahčuje riadenie pohybu hada v jednotlivých smeroch.
 * @author Filip Klein
 * @version 2023/12/14
 */

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