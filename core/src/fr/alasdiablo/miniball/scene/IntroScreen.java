package fr.alasdiablo.miniball.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class IntroScreen implements Screen {

    private final OrthographicCamera camera;
    private final FitViewport viewport;

    private int frame;
    private final Sound bip;
    private boolean doSound;
    private final Texture logo;
    private final Texture cover;
    private final SpriteBatch batch;

    private int middleX;
    private int middleY;

    public IntroScreen() {

        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(102.4f, 76.8f, camera);
        this.viewport.apply();
        this.camera.position.set(
                this.camera.viewportWidth / 2,
                this.camera.viewportHeight / 2,
                0
        );
        this.camera.update();

        this.middleX = (int) this.camera.viewportWidth / 2;
        this.middleY = (int) this.camera.viewportHeight / 2;


        this.batch = new SpriteBatch();
        this.batch.setProjectionMatrix(camera.combined);

        this.bip = Gdx.audio.newSound(Gdx.files.internal("sounds/8-bit-ui-interface.mp3"));
        this.doSound = true;
        this.frame = 0;
        this.logo = new Texture("AlasDiablo.png");
        this.cover = new Texture("AlasDiablo-Black.png");
    }

    private float getAlpha() {
        final float time = (float) (this.frame - 360);
        return time <= 360f ? 1f - time / 360f : 0f;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (this.doSound) {
            this.bip.play();
            this.doSound = false;
        }
        batch.begin();

        this.batch.setColor(1f, 1f, 1f, this.getAlpha());

        this.batch.draw(this.logo, this.middleX - 40, this.middleY - 10, 80, 20);
        this.batch.draw(this.cover, this.middleX - 40 + this.frame, this.middleY - 10, 80, 20);

        batch.end();

        this.frame+=4;
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height);
        this.camera.position.set(
                this.camera.viewportWidth / 2,
                this.camera.viewportHeight / 2,
                0
        );
        this.camera.update();

        this.middleX = (int) this.camera.viewportWidth / 2;
        this.middleY = (int) this.camera.viewportHeight / 2;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {
        this.bip.dispose();
    }

}
