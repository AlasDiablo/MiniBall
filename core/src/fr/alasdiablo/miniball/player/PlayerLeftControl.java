package fr.alasdiablo.miniball.player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class PlayerLeftControl implements IPlayer {

    protected boolean up;
    protected boolean left;
    protected boolean down;
    protected boolean right;

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
    public void moveVector(Vector2 vector) {

    }

    @Override
    public Vector2 getVelocity() {
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
        return new Vector2(0f, 0f);
    }
}
