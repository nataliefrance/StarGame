package ru.shipova.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.shipova.base.Sprite;
import ru.shipova.math.Rect;

public class Title extends Sprite{

    public Title(TextureAtlas atlas) {
        super(atlas.findRegion("stargame"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.13f);
        setHeight(0.2f);
        setBottom(worldBounds.getBottom() + 0.65f);
    }
}
