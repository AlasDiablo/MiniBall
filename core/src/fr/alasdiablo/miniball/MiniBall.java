package fr.alasdiablo.miniball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.Timer;
import fr.alasdiablo.miniball.screen.IntroScreen;
import fr.alasdiablo.miniball.screen.MenuScreen;

public class MiniBall extends Game {

    private final IntroScreen introScreen;
    private final MenuScreen menuScreen;

    public MiniBall() {
        this.introScreen = new IntroScreen();
        this.menuScreen = new MenuScreen();
    }

    @Override
    public void create() {


        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                MiniBall.this.setScreen(MiniBall.this.introScreen);
            }
        }, .5f);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                MiniBall.this.setScreen(MiniBall.this.menuScreen);
            }
        }, 3.5f);
    }
}
