package ru.shipova.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.shipova.math.Rect;
import ru.shipova.pool.BulletPool;
import ru.shipova.pool.ExplosionPool;

import static com.badlogic.gdx.Input.Keys.A;
import static com.badlogic.gdx.Input.Keys.D;
import static com.badlogic.gdx.Input.Keys.LEFT;
import static com.badlogic.gdx.Input.Keys.RIGHT;

public class MainShip extends Ship {

    private static final int INVALID_POINTER = -1;
    private static final int HEALTH_POINT = 100;

    private boolean pressedLeft;
    private boolean pressedRight;

    private Sound moveSound;

//    private boolean autoShoot;

    private int leftPointer = INVALID_POINTER; //первый палец
    private int rightPointer = INVALID_POINTER; //второй палец

    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, Sound bulletSound) {
        super(atlas.findRegion("dogShip"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.v = new Vector2();
        this.v0 = new Vector2(0.5f, 0f);
        this.bulletV = new Vector2(0, 0.5f);
//        bullet1Pos = new Vector2();
//        bullet2Pos = new Vector2();
        this.moveSound = Gdx.audio.newSound(Gdx.files.internal("audio/move.mp3"));
        this.reloadInterval = 0.3f;
        this.bulletHeight = 0.01f;
        this.damage = 1;
        this.bulletSound = bulletSound;
        this.healthPoint = HEALTH_POINT;
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
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
//        if (autoShoot) {
//            reloadTimer += delta;
//            if (reloadTimer >= reloadInterval){
//                reloadTimer = 0f;
//                shoot();
//            }
//        }
    }

    public void startNewGame() {
        this.healthPoint = HEALTH_POINT;
        this.pos.x = worldBounds.pos.x;
        this.v.set(0, 0);
        flushDestroy();
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
//            case UP:
//            case W:
//                shoot();
//                break;
//            case Q:
//                autoShoot = !autoShoot;
//                break;
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

//    private void shoot() {
//        Bullet bullet1 = bulletPool.obtain();
//        Bullet bullet2 = bulletPool.obtain();
//        bullet1Pos.set(pos);
//        bullet1Pos.x -= 0.03f;
//        bullet1Pos.y += 0.03f;
//        bullet2Pos.set(pos);
//        bullet2Pos.x += 0.03f;
//        bullet2Pos.y += 0.03f;
//        bullet1.set(this, bulletRegion, bullet1Pos, bulletV, 0.01f, worldBounds, 1);
//        bullet2.set(this, bulletRegion, bullet2Pos, bulletV, 0.01f, worldBounds, 1);
//    }

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

    public boolean isBulletCollision(Rect bullet) {
        return !(
                bullet.getRight() < getLeft()
                        || bullet.getLeft() > getRight()
                        || bullet.getBottom() > pos.y
                        || bullet.getTop() < getBottom()
        );
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
}
