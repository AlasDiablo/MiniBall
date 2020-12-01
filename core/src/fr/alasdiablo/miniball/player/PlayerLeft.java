package fr.alasdiablo.miniball.player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class PlayerLeft implements IPlayer {

    public boolean up;
    public boolean left;
    public boolean down;
    public boolean right;


    @Override
    public void moveUp(int keyCode) {
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
    public void moveDown(int keyCode) {
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
        if (this.up && !this.left && !this.down && !this.right)
            return new Vector2(0f, 10f);
        else if (!this.up && this.left && !this.down && !this.right)
            return new Vector2(-10f, 0f);
        else if (!this.up && !this.left && this.down && !this.right)
            return new Vector2(-10f, 0f);





        return new Vector2(0f, 0f);
    }
}
