package fr.alasdiablo.miniball.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {

    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private final SpriteBatch batch;
    private final World world;
    private int middleX;
    private int middleY;

    private final Texture terrain;

    private final Body playerLeft;
    private final Sprite playerLeftSprite;

    private final Body playerRight;
    private final Sprite playerRightSprite;

    private final InputProcessor playerInput;

    public GameScreen() {
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

        this.terrain = new Texture("terrain.png");

        this.world = new World(new Vector2(0, 0), false);

        final CircleShape circleShape = new CircleShape();
        circleShape.setRadius(76.8f / 40f);

        final FixtureDef playerFixtureDef = new FixtureDef();
        playerFixtureDef.shape = circleShape;
        playerFixtureDef.density = 1f;
        playerFixtureDef.restitution = .25f;
        playerFixtureDef.friction = 1.5f;

        final BodyDef playerLeftBodyDef = new BodyDef();
        playerLeftBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerLeftBodyDef.position.set(10f, 76.8f/2f);
        playerLeftBodyDef.linearDamping = 1.5f;
        this.playerLeft = this.world.createBody(playerLeftBodyDef);
        this.playerLeft.createFixture(playerFixtureDef);
        this.playerLeftSprite = new Sprite(new Texture("player_left.png"), 32, 32);
        this.playerLeftSprite.setScale(.125f);

        final BodyDef playerRightBodyDef = new BodyDef();
        playerRightBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerRightBodyDef.position.set(80f, 76.8f/2f);
        playerRightBodyDef.linearDamping = 1.5f;
        this.playerRight = this.world.createBody(playerRightBodyDef);
        this.playerRight.createFixture(playerFixtureDef);
        this.playerRightSprite = new Sprite(new Texture("player_right.png"), 32, 32);
        this.playerRightSprite.setScale(.125f);

        this.playerInput = new PlayerInput();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(Gdx.graphics.getDeltaTime(), 1, 1);

        this.playerLeftSprite.setPosition(this.playerLeft.getPosition().x - this.playerLeftSprite.getWidth()/2f, this.playerLeft.getPosition().y - this.playerLeftSprite.getHeight()/2f);
        this.playerRightSprite.setPosition(this.playerRight.getPosition().x - this.playerRightSprite.getWidth()/2f, this.playerRight.getPosition().y - this.playerRightSprite.getHeight()/2f);

        this.batch.begin();
        this.batch.draw(this.terrain, this.middleX - 50, this.middleY - 38, 102.4f, 76.8f);
        this.playerLeftSprite.draw(this.batch);
        this.playerRightSprite.draw(this.batch);
        this.batch.end();
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
    public void dispose() {
        this.batch.dispose();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.playerInput);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    private class PlayerInput implements InputProcessor {

        @Override
        public boolean keyDown(int keycode) {
            GameScreen.this.playerLeft.applyForceToCenter(new Vector2(10000f, 0f), true);
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}

}
