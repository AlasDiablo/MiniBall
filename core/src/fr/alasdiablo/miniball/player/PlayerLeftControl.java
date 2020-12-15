package fr.alasdiablo.miniball.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class PlayerLeftControl implements IPlayer {

    protected boolean up;
    protected boolean left;
    protected boolean down;
    protected boolean right;

    protected Vector2 vector2;

    public PlayerLeftControl() {
        this.vector2 = new Vector2(0f, 0f);
    }

    @Override
    public void moveDown(int keyCode) {
        switch (keyCode) {
            case Input.Keys.Z:
                this.up = true;
                break;
            case Input.Keys.Q:
                this.left = true;
                break;
            case Input.Keys.S:
                this.down = true;
                break;
            case Input.Keys.D:
                this.right = true;
                break;
        }
    }

    @Override
    public void moveUp(int keyCode) {
        switch (keyCode) {
            case Input.Keys.Z:
                this.up = false;
                break;
            case Input.Keys.Q:
                this.left = false;
                break;
            case Input.Keys.S:
                this.down = false;
                break;
            case Input.Keys.D:
                this.right = false;
                break;
        }
    }
    @Override
    public void moveVector() {
        if (Gdx.input.isTouched()) {
            int x = Gdx.input.getX();
            if (x < Gdx.graphics.getWidth()/2) {
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
    public Vector2 getVelocity() {
        // Update moving vector
        this.moveVector();
        // up left down right
        if (this.up && !this.left && !this.down && !this.right)
            return new Vector2(0f, 2000f);
        else if (!this.up && this.left && !this.down && !this.right)
            return new Vector2(-2000f, 0f);
        else if (!this.up && !this.left && this.down && !this.right)
            return new Vector2(0f, -2000f);
        else if (!this.up && !this.left && !this.down && this.right)
            return new Vector2(2000f, 0f);
        // up-left up-right down-left down-right
        else if (this.up && this.left && !this.down && !this.right)
            return new Vector2(-1000f, 1000f);
        else if (this.up && !this.left && !this.down && this.right)
            return new Vector2(1000f, 1000f);
        else if (!this.up && this.left && this.down && !this.right)
            return new Vector2(-1000f, -1000f);
        else if (!this.up && !this.left && this.down && this.right)
            return new Vector2(1000f, -1000f);
        // null vector by default and for other key combination
        return this.vector2;
    }
}
