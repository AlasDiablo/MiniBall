package fr.alasdiablo.miniball.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import fr.alasdiablo.miniball.player.IAPlayer;
import fr.alasdiablo.miniball.player.IPlayer;
import fr.alasdiablo.miniball.player.PlayerLeftControl;
import fr.alasdiablo.miniball.player.PlayerRightControl;

import java.util.Arrays;
import java.util.List;

public class GameScreen implements Screen {

    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private final SpriteBatch batch;
    private final World world;
    private final Texture terrain;
    private final Sprite playerLeftSprite;
    private final Body playerRight;
    private final Body goalRightBody;
    private final Body goalLeftBody;
    private final Sprite playerRightSprite;
    private final InputProcessor playerInput;
    private final Box2DDebugRenderer debugRenderer;
    private final IPlayer playerLeftControl;
    private final IPlayer playerRightControl;
    private final float worldWidth = 102.4f, worldHeight = 76.8f;
    private final Sprite ballSprite;
    private int middleX;
    private int middleY;

    public final Body ball;
    public final Body playerLeft;

    private boolean goal = false, goalAtLeft = false, goalAtRight = false;

    public GameScreen(boolean twoPlayer) {
        // ---------------------- Setup camera and view ----------------------
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(this.worldWidth, this.worldHeight, camera);
        this.viewport.apply();
        this.camera.position.set(
                this.camera.viewportWidth / 2,
                this.camera.viewportHeight / 2,
                0
        );
        this.camera.update();

        this.middleX = (int) this.camera.viewportWidth / 2;
        this.middleY = (int) this.camera.viewportHeight / 2;

        // ---------------------- Create sprite batch ----------------------
        this.batch = new SpriteBatch();
        this.batch.setProjectionMatrix(camera.combined);

        // ---------------------- Create terrain texture ----------------------
        this.terrain = new Texture("terrain.png");

        // ---------------------- Create world ----------------------
        this.world = new World(new Vector2(0, 0), false);

        // ---------------------- Create player shape and fixture ----------------------
        final CircleShape playerShape = new CircleShape();
        playerShape.setRadius(this.worldHeight / 40f);
        final FixtureDef playerFixtureDef = new FixtureDef();
        playerFixtureDef.shape = playerShape;
        playerFixtureDef.density = 1f;
        playerFixtureDef.restitution = .25f;

        // ---------------------- Create player left body ----------------------
        final BodyDef playerLeftBodyDef = new BodyDef();
        playerLeftBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerLeftBodyDef.position.set(10f, this.worldHeight/2f);
        playerLeftBodyDef.linearDamping = 1.5f;
        this.playerLeft = this.world.createBody(playerLeftBodyDef);
        this.playerLeft.createFixture(playerFixtureDef);

        // ---------------------- Create player left sprite ----------------------
        this.playerLeftSprite = new Sprite(new Texture("player_left.png"), 32, 32);
        this.playerLeftSprite.setScale(.125f);

        // ---------------------- Create player right body ----------------------
        final BodyDef playerRightBodyDef = new BodyDef();
        playerRightBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerRightBodyDef.position.set(90f, this.worldHeight/2f);
        playerRightBodyDef.linearDamping = 1.5f;
        this.playerRight = this.world.createBody(playerRightBodyDef);
        this.playerRight.createFixture(playerFixtureDef);
        // ---------------------- Create player right sprite ----------------------
        this.playerRightSprite = new Sprite(new Texture("player_right.png"), 32, 32);
        this.playerRightSprite.setScale(.125f);

        // ---------------------- Create ball ----------------------
        // ---------------------- Create ball shape and fixture ----------------------
        final CircleShape ballShape = new CircleShape();
        ballShape.setRadius(this.worldHeight / 85f);
        final FixtureDef ballFixtureDef = new FixtureDef();
        ballFixtureDef.shape = ballShape;
        ballFixtureDef.density = 1f;
        ballFixtureDef.restitution = .5f;

        // ---------------------- Create ball body ----------------------
        final BodyDef ballBodyDef = new BodyDef();
        ballBodyDef.type = BodyDef.BodyType.DynamicBody;
        ballBodyDef.position.set(this.worldWidth/2f, this.worldHeight/2f);
        ballBodyDef.linearDamping = 1.5f;
        this.ball = this.world.createBody(ballBodyDef);
        this.ball.createFixture(ballFixtureDef);

        // ---------------------- Create ball sprite ----------------------
        this.ballSprite = new Sprite(new Texture("ball.png"), 32, 32);
        this.ballSprite.setScale(.0625f);

        // ---------------------- Handle input ----------------------
        this.playerInput = new PlayerInput();

        // ---------------------- enable player movement ----------------------
        this.playerRightControl = new PlayerRightControl();
        if (twoPlayer) this.playerLeftControl = new PlayerLeftControl();
        else this.playerLeftControl = new IAPlayer(this);


        // ---------------------- Make terrain shape ----------------------
        final List<Vector2> vectorList = Arrays.asList(
                new Vector2(this.worldWidth * (7.6171875f / 100f), this.worldHeight * (41.015625f / 100f)),
                new Vector2(this.worldWidth * (7.6171875f / 100f), this.worldHeight * (15.625f / 100f)),
                new Vector2(this.worldWidth * (8.7890625f / 100f), this.worldHeight * (14.0625f / 100f)),
                new Vector2(this.worldWidth * (91.2109375f / 100f), this.worldHeight * (14.0625f / 100f)),
                new Vector2(this.worldWidth * (92.3828125f / 100f), this.worldHeight * (15.625f / 100f)),
                new Vector2(this.worldWidth * (92.3828125f / 100f), this.worldHeight * (41.015625f / 100f)),
                new Vector2(this.worldWidth * (96.2890625f / 100f), this.worldHeight * (41.015625f / 100f)),
                new Vector2(this.worldWidth * (96.2890625f / 100f), this.worldHeight * (58.59375f / 100f)),
                new Vector2(this.worldWidth * (92.3828125f / 100f), this.worldHeight * (58.59375f / 100f)),
                new Vector2(this.worldWidth * (92.3828125f / 100f), this.worldHeight * (84.375f / 100f)),
                new Vector2(this.worldWidth * (91.2109375f / 100f), this.worldHeight * (85.9375f / 100f)),
                new Vector2(this.worldWidth * (8.7890625f / 100f), this.worldHeight * (85.9375f / 100f)),
                new Vector2(this.worldWidth * (7.6171875f / 100f), this.worldHeight * (84.375f / 100f)),
                new Vector2(this.worldWidth * (7.6171875f / 100f), this.worldHeight * (58.59375f / 100f)),
                new Vector2(this.worldWidth * (3.7109375f / 100f), this.worldHeight * (58.59375f / 100f)),
                new Vector2(this.worldWidth * (3.7109375f / 100f), this.worldHeight * (41.015625f / 100f)),
                new Vector2(this.worldWidth * (7.6171875f / 100f), this.worldHeight * (41.015625f / 100f))
        );
        final ChainShape terrainShape = new ChainShape();
        terrainShape.createChain(vectorList.toArray(new Vector2[]{}));
        final FixtureDef terrainFixtureDef = new FixtureDef();
        final BodyDef terrainBodyDef = new BodyDef();
        terrainFixtureDef.shape = terrainShape;
        terrainBodyDef.type = BodyDef.BodyType.StaticBody;
        final Body terrainBody = this.world.createBody(terrainBodyDef);
        terrainBody.createFixture(terrainFixtureDef);
        // ---------------------- goal left----------------------

        final List<Vector2> goalLeftVectorList = Arrays.asList(
                new Vector2(this.worldWidth * (7.6171875f / 100f), this.worldHeight * (58.59375f / 100f)),
                new Vector2(this.worldWidth * (3.7109375f / 100f), this.worldHeight * (58.59375f / 100f)),
                new Vector2(this.worldWidth * (3.7109375f / 100f), this.worldHeight * (41.015625f / 100f)),
                new Vector2(this.worldWidth * (7.6171875f / 100f), this.worldHeight * (41.015625f / 100f)),
                new Vector2(this.worldWidth * (7.6171875f / 100f), this.worldHeight * (58.59375f / 100f))
        );

        final ChainShape goalLeftShape = new ChainShape();
        goalLeftShape.createChain(goalLeftVectorList.toArray(new Vector2[]{}));
        final FixtureDef goalLeftFixtureDef = new FixtureDef();
        final BodyDef goalLeftBodyDef = new BodyDef();
        goalLeftFixtureDef.shape = goalLeftShape;
        goalLeftFixtureDef.isSensor = true;
        goalLeftBodyDef.type = BodyDef.BodyType.StaticBody;
        this.goalLeftBody = this.world.createBody(goalLeftBodyDef);
        this.goalLeftBody.createFixture(goalLeftFixtureDef);


        // ---------------------- goal right----------------------

        final List<Vector2> goalRightVectorList = Arrays.asList(
                new Vector2(this.worldWidth * (92.3828125f / 100f), this.worldHeight * (41.015625f / 100f)),
                new Vector2(this.worldWidth * (96.2890625f / 100f), this.worldHeight * (41.015625f / 100f)),
                new Vector2(this.worldWidth * (96.2890625f / 100f), this.worldHeight * (58.59375f / 100f)),
                new Vector2(this.worldWidth * (92.3828125f / 100f), this.worldHeight * (58.59375f / 100f)),
                new Vector2(this.worldWidth * (92.3828125f / 100f), this.worldHeight * (41.015625f / 100f))
        );

        final ChainShape goalRightShape = new ChainShape();
        goalRightShape.createChain(goalRightVectorList.toArray(new Vector2[]{}));
        final FixtureDef goalRightFixtureDef = new FixtureDef();
        final BodyDef goalRightBodyDef = new BodyDef();
        goalRightFixtureDef.shape = goalRightShape;
        goalRightFixtureDef.isSensor = true;
        goalRightBodyDef.type = BodyDef.BodyType.StaticBody;
        this.goalRightBody = this.world.createBody(goalRightBodyDef);
        this.goalRightBody.createFixture(goalRightFixtureDef);

        // ---------------------- box 2d debug ----------------------
        this.debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void render(float delta) {
        // set background
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tick world
        if (!this.goal) {
            this.playerLeft.applyForceToCenter(this.playerLeftControl.getVelocity(), true);
            this.playerRight.applyForceToCenter(this.playerRightControl.getVelocity(), true);
            world.step(Gdx.graphics.getDeltaTime(), 1, 1);
        }

        // tick player and ball sprite position
        this.playerLeftSprite.setPosition(this.playerLeft.getPosition().x - this.playerLeftSprite.getWidth()/2f, this.playerLeft.getPosition().y - this.playerLeftSprite.getHeight()/2f);
        this.playerRightSprite.setPosition(this.playerRight.getPosition().x - this.playerRightSprite.getWidth()/2f, this.playerRight.getPosition().y - this.playerRightSprite.getHeight()/2f);
        this.ballSprite.setPosition(this.ball.getPosition().x - this.ballSprite.getWidth()/2f, this.ball.getPosition().y - this.ballSprite.getHeight()/2f);

        // tick ball rotation
        this.ballSprite.setRotation(this.ball.getAngle());

        // star drawing
        this.batch.begin();
        this.batch.draw(this.terrain, this.middleX - 50.55f, this.middleY - 38.375f, this.worldWidth, this.worldHeight);
        this.playerLeftSprite.draw(this.batch);
        this.playerRightSprite.draw(this.batch);
        this.ballSprite.draw(this.batch);

        this.batch.end();
        // ---------------------- box 2d debug ----------------------
        this.drawDebugLine(
                this.playerLeft.getPosition(),
                this.playerLeftControl.getVelocity(),
                this.camera.combined
        );
        this.drawDebugLine(
                this.playerRight.getPosition(),
                this.playerRightControl.getVelocity(),
                this.camera.combined
        );
        this.debugRenderer.render(this.world, this.camera.combined);
    }

    private final ShapeRenderer debugShapeRenderer = new ShapeRenderer();
    public void drawDebugLine(Vector2 start, Vector2 end, Matrix4 projectionMatrix) {
        Gdx.gl.glLineWidth(2);
        debugShapeRenderer.setProjectionMatrix(projectionMatrix);
        debugShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugShapeRenderer.setColor(Color.WHITE);
        debugShapeRenderer.line(start, new Vector2(start).add(end));
        debugShapeRenderer.end();
        Gdx.gl.glLineWidth(1);
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
        this.world.setContactListener(new ContactListener() {
            @Override public void beginContact(Contact contact) {
                if (contact.getFixtureA().getBody().equals(GameScreen.this.goalLeftBody) && contact.getFixtureB().getBody().equals(GameScreen.this.ball)) {
                    GameScreen.this.goal = true;
                    GameScreen.this.goalAtLeft = true;
                }
                if (contact.getFixtureA().getBody().equals(GameScreen.this.goalRightBody) && contact.getFixtureB().getBody().equals(GameScreen.this.ball)) {
                    GameScreen.this.goal = true;
                    GameScreen.this.goalAtRight = true;
                }
            }
            @Override public void endContact(Contact contact) {}
            @Override public void preSolve(Contact contact, Manifold oldManifold) {}
            @Override public void postSolve(Contact contact, ContactImpulse impulse) {}
        });
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    private class PlayerInput implements InputProcessor {

        @Override
        public boolean keyDown(int keycode) {
            GameScreen.this.playerLeftControl.moveDown(keycode);
            GameScreen.this.playerRightControl.moveDown(keycode);
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            GameScreen.this.playerLeftControl.moveUp(keycode);
            GameScreen.this.playerRightControl.moveUp(keycode);
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

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
