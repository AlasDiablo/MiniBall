package fr.alasdiablo.miniball.player;

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
