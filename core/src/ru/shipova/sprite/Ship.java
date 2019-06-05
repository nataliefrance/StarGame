package ru.shipova.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.shipova.base.Sprite;
import ru.shipova.math.Rect;
import ru.shipova.pool.BulletPool;
import ru.shipova.pool.ExplosionPool;

public abstract class Ship extends Sprite {

    protected Vector2 v;
    protected Vector2 v0;
    protected Vector2 bulletV;
    protected float bulletHeight;
    protected Sound bulletSound;
    protected int damage;
    protected int healthPoint;

    protected Rect worldBounds;

    protected ExplosionPool explosionPool;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;

    protected float reloadInterval;
    protected float reloadTimer;

    protected float damageAnimateInterval = 0.1f;
    protected float damageAnimateTimer = damageAnimateInterval;


    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public Ship() {
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

        damageAnimateTimer += delta;
        if (damageAnimateTimer >= damageAnimateInterval){
            frame = 0;
        }
    }

    protected void shoot() {
        Bullet bullet1 = bulletPool.obtain();
        bullet1.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
        bulletSound.play(1.0f);
    }

    private void boom(){
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    public void quietDestroy(){
        super.destroy();
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
        explosion.getExplosionSound().stop();
    }

    public void getDamage(int damage){
        healthPoint -= damage;
        if (healthPoint <= 0){
            destroy();
        }
        frame = 1;
        damageAnimateTimer = 0f;
    }
}
