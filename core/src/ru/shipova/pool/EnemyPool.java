package ru.shipova.pool;

import com.badlogic.gdx.audio.Sound;

import ru.shipova.base.SpritesPool;
import ru.shipova.math.Rect;
import ru.shipova.sprite.EnemyShip;
import ru.shipova.sprite.MainShip;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private Sound enemyBulletSound;
    private Rect worldBounds;
    private MainShip mainShip;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Sound enemyBulletSound, Rect worldBounds, MainShip mainShip) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.enemyBulletSound = enemyBulletSound;
        this.worldBounds = worldBounds;
        this.mainShip = mainShip;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, explosionPool, enemyBulletSound, worldBounds, mainShip);
    }
}
