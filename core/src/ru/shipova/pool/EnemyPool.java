package ru.shipova.pool;

import com.badlogic.gdx.audio.Sound;

import ru.shipova.base.SpritesPool;
import ru.shipova.math.Rect;
import ru.shipova.sprite.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private Sound enemyBulletSound;
    private Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Sound enemyBulletSound, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.enemyBulletSound = enemyBulletSound;
        this.worldBounds = worldBounds;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, explosionPool, enemyBulletSound, worldBounds);
    }
}
