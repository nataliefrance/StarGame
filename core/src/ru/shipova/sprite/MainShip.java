package ru.shipova.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.shipova.base.Sprite;
import ru.shipova.math.Rect;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;

public class MainShip extends Sprite {

    private static final int INVALID_POINTER = -1;

    private Vector2 v;
    private final Vector2 v0;
    private boolean pressedLeft;
    private boolean pressedRight;
    private Rect worldBounds;

    private int leftPointer = INVALID_POINTER; //первый палец
    private int rightPointer = INVALID_POINTER; //второй палец


    public MainShip(TextureAtlas atlas) {
        super(atlas.findRegion("mainShip"), 1, 2, 2);
        v = new Vector2();
        v0 = new Vector2(0.5f, 0f);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.15f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case A:
            case LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case D:
            case RIGHT:
                pressedRight = true;
                moveRight();
                break;

        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case A:
            case LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else stop();
                break;
            case D:
            case RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else stop();
                break;
        }
        return false;
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void stop() {
        v.setZero();
    }

    public boolean touchDown(Vector2 touch, int pointer) {
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {//проверка, не нажали ли мы на левую часть экрана
                return false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) {//проверка, не нажали ли мы на правую часть экрана
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else stop();
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else stop();
        }
        return false;
    }
}
