package fr.alasdiablo.miniball.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerRightControl extends PlayerLeftControl {

    @Override
    public void moveDown(int keyCode) {
        switch (keyCode) {
            case Input.Keys.UP:
                this.up = true;
                break;
            case Input.Keys.LEFT:
                this.left = true;
                break;
            case Input.Keys.DOWN:
                this.down = true;
                break;
            case Input.Keys.RIGHT:
                this.right = true;
                break;
        }
    }

    @Override
    public void moveVector() {
        if (Gdx.input.isTouched()) {
            try {
                int x = Gdx.input.getX(0);
                if (x > Gdx.graphics.getWidth()/2) this.vector2 = performInput(0);
            } catch (Exception ignored) {}
            try {
                int x = Gdx.input.getX(1);
                if (x > Gdx.graphics.getWidth()/2) this.vector2 = performInput(1);
            } catch (Exception ignored) {}
        }
    }

    @Override
    public void moveUp(int keyCode) {
        switch (keyCode) {
            case Input.Keys.UP:
                this.up = false;
                break;
            case Input.Keys.LEFT:
                this.left = false;
                break;
            case Input.Keys.DOWN:
                this.down = false;
                break;
            case Input.Keys.RIGHT:
                this.right = false;
                break;
        }
    }
}
