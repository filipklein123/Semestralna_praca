import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Trieda Zvuk sa stará o prehrávanie zvukov.
 * Hra umožňuje prehrať zvuky jedlo a koniec hry, čo celkovo zlepšuje dojem a zážitok z hry.
 * @author Filip Klein
 * @version 2023/12/14
 */

public class Zvuk {
    private Clip zvukJedla;
    private Clip zvukKoncaHry;

    public Zvuk() {
        try {
            this.zvukJedla = AudioSystem.getClip();
            this.zvukJedla.open(AudioSystem.getAudioInputStream(Zvuk.class.getResource("zvuk/food.wav")));
            
            this.zvukKoncaHry = AudioSystem.getClip();
            this.zvukKoncaHry.open(AudioSystem.getAudioInputStream(Zvuk.class.getResource("zvuk/gameover.wav")));

        } catch (LineUnavailableException | UnsupportedAudioFileException | java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void zvukJedla() {
        if (this.zvukJedla.isRunning()) {
            this.zvukJedla.stop();
        }
        this.zvukJedla.setFramePosition(0);
        this.zvukJedla.start();
    }

    public void zvukKoncaHry() {
        if (this.zvukKoncaHry.isRunning()) {
            this.zvukKoncaHry.stop();
        }
        this.zvukKoncaHry.setFramePosition(0);
        this.zvukKoncaHry.start();
    }
}