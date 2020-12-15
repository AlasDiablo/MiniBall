package fr.alasdiablo.miniball.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

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
            int x = Gdx.input.getX();
            if (x > Gdx.graphics.getWidth()/2) {
                float deltaX = Gdx.input.getDeltaX() * 100f;
                float deltaY = -Gdx.input.getDeltaY() * 100f;
                this.vector2 = new Vector2(
                        Math.min( // make sure that x is between -800f and 800f
                                Math.max(deltaX, -800f),
                                800f
                        ),
                        Math.min( // make sure that y is between -800f and 800f
                                Math.max(deltaY, -800f),
                                800f
                        )
                );
            }
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
