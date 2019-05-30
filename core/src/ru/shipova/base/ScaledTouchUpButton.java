package ru.shipova.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class ScaledTouchUpButton extends Sprite {

    private static final float PRESS_SCALE = 0.9f;

    private int pointer;//каким пальцем нажата кнопка
    private boolean pressed;

    private Sound zhmakSound;

    public ScaledTouchUpButton(TextureRegion region) {
        super(region);
        zhmakSound = Gdx.audio.newSound(Gdx.files.internal("audio/zhmak.mp3"));
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (pressed || !isMe(touch)) {
            return false;
        }
        this.scale = PRESS_SCALE;
        this.pressed = true;
        this.pointer = pointer;
        zhmakSound.play(1.0f);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (this.pointer != pointer || !pressed) {
            return false;
        }
        if (isMe(touch)) {
            action();
        }
        pressed = false;
        scale = 1f;
        return false;
    }

    public abstract void action();
}
