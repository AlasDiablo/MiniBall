package fr.alasdiablo.miniball.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Intro implements Runnable {

    private int frame;
    private final Sound bip;
    private boolean doSound;
    private Texture logo;
    private Texture cover;
    private SpriteBatch batch;

    private int middleX, middleY;

    public Intro(SpriteBatch batch, int middleX, int middleY) {
        this.bip = Gdx.audio.newSound(Gdx.files.internal("sounds/8-bit-ui-interface.mp3"));
        this.doSound = true;
        this.frame = 0;
        this.logo = new Texture("AlasDiablo.png");
        this.cover = new Texture("AlasDiablo-Black.png");
        this.batch = batch;
        this.middleX = middleX;
        this.middleY = middleY;
    }

    @Override
    public void run() {
        if (this.doSound) {
            this.bip.play();
            this.doSound = false;
        }
        this.batch.setColor(1f, 1f, 1f, this.getAlpha());

        this.batch.draw(this.logo, this.middleX - 40, this.middleY - 10, 80, 20);
        this.batch.draw(this.cover, this.middleX - 40 + this.frame, this.middleY - 10, 80, 20);

        this.frame+=4;
    }

    private float getAlpha() {
        final float time = (float) (this.frame - 360);
        return time <= 360f ? 1f - time / 360f : 0f;
    }

    public void dispose() {
        this.bip.dispose();
    }

}
