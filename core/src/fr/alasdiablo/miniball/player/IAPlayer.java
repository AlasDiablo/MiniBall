package fr.alasdiablo.miniball.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import fr.alasdiablo.miniball.screen.GameScreen;
import fr.alasdiablo.miniball.util.Point;

public class IAPlayer implements IPlayer {

    private final GameScreen gameScreen;

    private boolean check;

    public IAPlayer(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.check = true;
    }

    @Override
    public Vector2 getVelocity() {
        final Point goalTarget = new Point(95f, 38.4f);
        final Point goalAly = new Point(10f, 38.4f);
        final Point player = new Point(this.gameScreen.playerLeft.getPosition().x, this.gameScreen.playerLeft.getPosition().y);
        final Point ball = new Point(this.gameScreen.ball.getPosition().x, this.gameScreen.ball.getPosition().y);

        Vector2 out;
        if (player.getX() >= ball.getX() && this.check) {
            out = createVector(player, goalAly, 100f);
            this.check = false;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    IAPlayer.this.check = true;
                }
            }, 0.1f);
        }
        else if (createVector(player, ball, 1f).len() <= 3f) out = createVector(player, goalTarget, 200f);
        else out = createVector(player, ball, 200f);


        return out;
    }

    private Vector2 createVector(Point a, Point b, float mul) {
        final float x = (b.getX() - a.getX()) * mul;
        final float y = (b.getY() - a.getY()) * mul;
        return new Vector2(
                Math.min( // make sure that x is between -800f and 800f
                        Math.max(x, -800f),
                        800f
                ),
                Math.min( // make sure that y is between -800f and 800f
                        Math.max(y, -800f),
                        800f
                )
        );
    }

    @Override public void moveUp(int keyCode) {}

    @Override public void moveDown(int keyCode) {}

    @Override public void moveVector() {}
}
