package ru.shipova.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.shipova.base.Sprite;
import ru.shipova.math.Rect;

public class SpaceCat extends Sprite {
    public SpaceCat(TextureRegion region) {
        super(region);
        regions[frame].getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.3f);
    }
}
