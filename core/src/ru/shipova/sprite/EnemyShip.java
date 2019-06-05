package ru.shipova.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.shipova.math.Rect;
import ru.shipova.pool.BulletPool;
import ru.shipova.pool.ExplosionPool;

public class EnemyShip extends Ship {

    private enum State {DESCENT, FIGHT} //descent - спуск

    private State state;
    private Vector2 descentV = new Vector2(0, -0.15f);

    public EnemyShip(BulletPool bulletPool, ExplosionPool explosionPool, Sound bulletSound, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletSound = bulletSound;
        this.v = new Vector2();
        this.v0 = new Vector2();
        this.bulletV = new Vector2();
        this.worldBounds = worldBounds;
        this.reloadTimer = reloadInterval;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        switch (state) {
            case DESCENT:
                if (getTop() <= worldBounds.getTop()){
                    v.set(v0);
                    state = State.FIGHT;
                }
                break;
            case FIGHT:
                reloadTimer += delta;
                if (reloadTimer >= reloadInterval) {
                    reloadTimer = 0f;
                    shoot();
                }
                if (getBottom() < worldBounds.getBottom()) {
                    destroy();
                }
                break;
        }

    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletV_Y,
            int damage,
            float reloadInterval,
            float height,
            int healthPoint) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletV.set(0, bulletV_Y);
        this.v.set(descentV);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        this.healthPoint = healthPoint;
        this.state = State.DESCENT;
    }
}