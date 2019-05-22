package ru.shipova.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.shipova.base.Sprite;
import ru.shipova.math.Rect;

public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(1f);//фон всегда будет занимать всю высоту экрана
        pos.set(worldBounds.pos);//совместили точку (0,0) с центром нашего фона
    }
}
