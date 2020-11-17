package fr.alasdiablo.miniball;

import com.badlogic.gdx.Game;
import fr.alasdiablo.miniball.scene.IntroScreen;

public class MiniBall extends Game {

    private IntroScreen introScreen;

    @Override
    public void create() {
        this.introScreen = new IntroScreen();
        this.setScreen(this.introScreen);
    }
}
