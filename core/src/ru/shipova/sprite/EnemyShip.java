package ru.shipova.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.shipova.math.Rect;
import ru.shipova.pool.BulletPool;

public class EnemyShip extends Ship {

    private Vector2 vGoToScreen;

    public EnemyShip(BulletPool bulletPool, Sound bulletSound, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.bulletSound = bulletSound;
        this.v = new Vector2();
        this.v0 = new Vector2();
        this.bulletV = new Vector2();
        this.worldBounds = worldBounds;
        this.reloadTimer = reloadInterval;
        this.vGoToScreen = new Vector2();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getTop() > worldBounds.getTop()){
            pos.mulAdd(vGoToScreen, delta);
        } else pos.mulAdd(v, delta);
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
            int healthPoint, Vector2 vGoToScreen){
        this.regions = regions;
        this.v0.set(v0);
        this.bulletV.set(0, bulletV_Y);
        this.v.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        setHeightProportion(height);
        this.healthPoint = healthPoint;
        this.vGoToScreen.set(vGoToScreen);
    }
}
