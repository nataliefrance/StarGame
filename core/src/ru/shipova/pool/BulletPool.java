package ru.shipova.pool;

import ru.shipova.base.SpritesPool;
import ru.shipova.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
