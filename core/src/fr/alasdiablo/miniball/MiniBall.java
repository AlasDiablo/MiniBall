package fr.alasdiablo.miniball;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.Timer;
import fr.alasdiablo.miniball.screen.GameScreen;
import fr.alasdiablo.miniball.screen.IntroScreen;
import fr.alasdiablo.miniball.screen.MenuScreen;

public class MiniBall extends Game {

    private IntroScreen introScreen;
    private MenuScreen menuScreen;

    @Override
    public void create() {
        this.introScreen = new IntroScreen();
        this.menuScreen = new MenuScreen(this);

        this.setScreen(new GameScreen(false));

//        Timer.schedule(new Timer.Task() {
//            @Override
//            public void run() {
//                MiniBall.this.setScreen(MiniBall.this.introScreen);
//            }
//        }, 0.5f);
//
//        Timer.schedule(new Timer.Task() {
//            @Override
//            public void run() {
//                MiniBall.this.setScreen(MiniBall.this.menuScreen);
//            }
//        }, 3.5f);
    }
}
