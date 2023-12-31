package fr.alasdiablo.miniball.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import fr.alasdiablo.miniball.util.MiniBallConst;

public class MenuScreen implements Screen {

    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private final SpriteBatch batch;
    private int middleX;
    private int middleY;
    private final BitmapFont titleFont;
    private final BitmapFont menuFont;
    private final InputProcessor inputProcessor;
    private int select;

    private final Sound succes;
    private final Sound gameboyPluck;

    private final Game game;


    public MenuScreen(Game game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(102.4f * 4, 76.8f * 4, camera);
        this.viewport.apply();
        this.camera.position.set(
                this.camera.viewportWidth / 2,
                this.camera.viewportHeight / 2,
                0
        );
        this.camera.update();

        this.middleX = (int) this.camera.viewportWidth / 2;
        this.middleY = (int) this.camera.viewportHeight / 2;


        final FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("PrStart.ttf"));

        final FreeTypeFontGenerator.FreeTypeFontParameter titleFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        titleFontParameter.size = 32;
        titleFontParameter.color = Color.WHITE;
        this.titleFont = fontGenerator.generateFont(titleFontParameter);

        final FreeTypeFontGenerator.FreeTypeFontParameter menuFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        menuFontParameter.color = Color.WHITE;
        this.menuFont = fontGenerator.generateFont(menuFontParameter);

        this.batch = new SpriteBatch();
        this.batch.setProjectionMatrix(camera.combined);

        this.select = 0;

        this.inputProcessor = new InputMenu();

        this.succes = Gdx.audio.newSound(Gdx.files.internal("sounds/success.mp3"));
        this.gameboyPluck = Gdx.audio.newSound(Gdx.files.internal("sounds/gameboy-pluck.mp3"));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.inputProcessor);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.batch.begin();
        this.titleFont.draw(this.batch, "MiniBall", this.middleX / 2.75f, this.middleY * 2 - (this.middleY / 16f));

        this.menuFont.draw(this.batch, "1 Player", this.middleX / 1.25f, this.middleY + (this.middleY / 4f));
        this.menuFont.draw(this.batch, "2 Player", this.middleX / 1.25f, this.middleY - (this.middleY / 16f));
        this.menuFont.draw(this.batch, "Quit", this.middleX / 1.25f, this.middleY - (this.middleY / 3f));

        switch (this.select) {
            case 0:
                drawSelector(this.middleY / 4f);
                break;
            case 1:
                drawSelector(-(this.middleY / 16f));
                break;
            case 2:
                drawSelector(-(this.middleY / 3f));
                break;
        }

        this.batch.end();
    }

    private void drawSelector(float pos) {
        this.menuFont.draw(this.batch, ">", this.middleX / 2f, this.middleY + pos);
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
        this.titleFont.dispose();
        this.menuFont.dispose();
    }

    private class InputMenu implements InputProcessor {

        private void succesSound() {
            MenuScreen.this.succes.play(MiniBallConst.SOUND_VOLUME);
        }

        private void gameboyPluckSound() {
            MenuScreen.this.gameboyPluck.play(MiniBallConst.SOUND_VOLUME * .4f);
        }

        private float crossMultiplication(float a, float b, float c) {
            return ((c * b) / a);
        }

        private void exit() {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    Gdx.app.exit();
                }
            }, 0.5f);
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            float crossScreenX = this.crossMultiplication(Gdx.graphics.getWidth(), MenuScreen.this.camera.viewportWidth, screenX);
            float crossScreenY = this.crossMultiplication(Gdx.graphics.getHeight(), MenuScreen.this.camera.viewportHeight, screenY);

            if (crossScreenX < MenuScreen.this.middleX && crossScreenY < MenuScreen.this.middleY) {
                MenuScreen.this.select--;
                if (MenuScreen.this.select < 0)
                    MenuScreen.this.select = 2;
                this.gameboyPluckSound();
                return true;
            }
            if (crossScreenX < MenuScreen.this.middleX && crossScreenY > MenuScreen.this.middleY) {
                MenuScreen.this.select++;
                if (MenuScreen.this.select > 2)
                    MenuScreen.this.select = 0;
                this.gameboyPluckSound();
                return true;
            }
            if (crossScreenX > MenuScreen.this.middleX) {
                if (MenuScreen.this.select == 0) MenuScreen.this.game.setScreen(new GameScreen(false));
                if (MenuScreen.this.select == 1) MenuScreen.this.game.setScreen(new GameScreen(true));
                if (MenuScreen.this.select == 2) this.exit();
                this.succesSound();
                return true;
            }
            return false;
        }

        @Override
        public boolean keyDown(int keycode) {
            switch (keycode) {
                case Input.Keys.DOWN:
                    MenuScreen.this.select++;
                    if (MenuScreen.this.select > 2)
                        MenuScreen.this.select = 0;
                    this.gameboyPluckSound();
                    return true;
                case Input.Keys.UP:
                    MenuScreen.this.select--;
                    if (MenuScreen.this.select < 0)
                        MenuScreen.this.select = 2;
                    this.gameboyPluckSound();
                    return true;
                case Input.Keys.ENTER:
                    if (MenuScreen.this.select == 0) MenuScreen.this.game.setScreen(new GameScreen(false));
                    if (MenuScreen.this.select == 1) MenuScreen.this.game.setScreen(new GameScreen(true));
                    if (MenuScreen.this.select == 2) this.exit();
                    this.succesSound();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
        @Override
        public boolean keyUp(int keycode) { return false; }
        @Override
        public boolean keyTyped(char character) {return false;}
        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {return false;}
        @Override
        public boolean mouseMoved(int screenX, int screenY) {return false;}
        @Override
        public boolean scrolled(int amount) {return false;}
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
}
