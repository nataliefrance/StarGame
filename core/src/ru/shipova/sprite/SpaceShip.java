package ru.shipova.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.shipova.base.Sprite;
import ru.shipova.math.Rect;

import static com.badlogic.gdx.Input.Keys.DOWN;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.UP;

public class SpaceShip extends Sprite {

    private Vector2 v;
    private static final float V_X = 0.05f;
    private static final float V_Y = 0f;

    public SpaceShip(TextureAtlas atlas) {
        super(atlas.findRegion("spaceShip"));
        setHeightProportion(0.15f);
        v = new Vector2(0.05f, 0f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom() + 0.1f);
    }

    public void move(int keycode) {
        if (keycode == LEFT) {
            v.set(-V_X, V_Y);
            pos.add(v);
        }
        if (keycode == UP) {
            v.set(V_Y, V_X);
            pos.add(v);
        }
        if (keycode == RIGHT) {
            v.set(V_X, V_Y);
            pos.add(v);
        }
        if (keycode == DOWN) {
            v.set(V_Y, -V_X);
            pos.add(v);
        }
    }


}
