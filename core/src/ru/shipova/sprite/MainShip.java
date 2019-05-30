package ru.shipova.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.shipova.base.Sprite;
import ru.shipova.math.Rect;
import ru.shipova.pool.BulletPool;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;
import static com.badlogic.gdx.Input.Keys.UP;

public class MainShip extends Sprite {

    private static final int INVALID_POINTER = -1;

    private Vector2 v;
    private final Vector2 v0;
    private Vector2 bulletV;
    private Vector2 bulletPos;

    private boolean pressedLeft;
    private boolean pressedRight;

    private Rect worldBounds;
    private Sound piuSound;
    private Sound moveSound;

    private BulletPool bulletPool;
    private TextureRegion bulletRegion;

    private int leftPointer = INVALID_POINTER; //первый палец
    private int rightPointer = INVALID_POINTER; //второй палец


    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("mainShip"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        v = new Vector2();
        v0 = new Vector2(0.5f, 0f);
        bulletV = new Vector2(0, 0.5f);
        bulletPos = new Vector2();
        piuSound = Gdx.audio.newSound(Gdx.files.internal("audio/piu.mp3"));
        moveSound = Gdx.audio.newSound(Gdx.files.internal("audio/move.mp3"));
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.15f);
        setBottom(worldBounds.getBottom() + 0.02f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        if (getRight() > worldBounds.getRight()){
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()){
            setLeft(worldBounds.getLeft());
            stop();
        }
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
            case UP:
                shoot();
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
        moveSound.play(1.0f);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
        moveSound.play(1.0f);
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

    private void shoot(){
        Bullet bullet = bulletPool.obtain();
        bulletPos.set(pos);
        bulletPos.y += getHalfHeight();
        bullet.set(this, bulletRegion, bulletPos, bulletV, 0.01f, worldBounds, 1);
        piuSound.play(1.0f);
    }
}
