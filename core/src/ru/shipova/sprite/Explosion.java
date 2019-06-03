package ru.shipova.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.shipova.base.Sprite;

public class Explosion extends Sprite {

    private float animateInterval = 0.05f;
    private float animateTimer;

    private Sound explosionSound;

    public Explosion(TextureAtlas atlas, Sound explosionSound) {
        super(atlas.findRegion("explosion"), 2, 4, 8);
        this.explosionSound = explosionSound;
    }

    public void set(float height, Vector2 pos){
        this.pos.set(pos);
        setHeightProportion(height);
        explosionSound.play();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        animateTimer += delta;
        if (animateTimer >= animateInterval){
            animateTimer = 0f;
            if (++frame == regions.length){
                destroy();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }
}
