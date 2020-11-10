package fr.alasdiablo.miniball;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import fr.alasdiablo.miniball.scene.Intro;

public class MiniBall extends ApplicationAdapter {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;

    private Intro intro;

    @Override
    public void create() {
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(102.4f, 76.8f, camera);
        this.viewport.apply();
        this.camera.position.set(
                this.camera.viewportWidth / 2,
                this.camera.viewportHeight / 2,
                0
        );
        this.camera.update();

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        this.intro = new Intro(
                this.batch,
                (int) this.camera.viewportWidth / 2,
                (int) this.camera.viewportHeight / 2
        );
    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        this.intro.run();

        batch.end();

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
    }

    @Override
    public void dispose() {
        batch.dispose();
        this.intro.dispose();
    }
}
