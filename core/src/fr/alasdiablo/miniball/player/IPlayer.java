package fr.alasdiablo.miniball.player;

import com.badlogic.gdx.math.Vector2;

public interface IPlayer {
    void moveUp(int keyCode);
    void moveDown(int keyCode);
    void moveVector(Vector2 vector);
    Vector2 getVelocity();
}
