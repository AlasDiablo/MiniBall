package fr.alasdiablo.miniball;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import fr.alasdiablo.miniball.scene.IntroScreen;
import fr.alasdiablo.miniball.scene.MenuScreen;

public class MiniBall extends Game {

    private IntroScreen introScreen;
    private MenuScreen menuScreen;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        this.introScreen = new IntroScreen();
        this.menuScreen = new MenuScreen();
        this.setScreen(this.menuScreen);
    }
}
