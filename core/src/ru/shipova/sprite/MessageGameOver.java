package ru.shipova.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.shipova.base.Sprite;
import ru.shipova.math.Rect;

public class MessageGameOver extends Sprite {
    public MessageGameOver(TextureAtlas atlas) {
        super(atlas.findRegion("game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.16f);
        setHeight(0.2f);
        setBottom(0.009f);
    }
}
