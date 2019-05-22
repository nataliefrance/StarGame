package ru.shipova.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.shipova.base.Sprite;
import ru.shipova.math.Rect;

public class SpaceCat extends Sprite {

    public Vector2 pos;


    public SpaceCat(TextureRegion region, float x, float y) {
        super(region);
        pos = new Vector2(x, y);
        regions[frame].getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.3f);
    }

    public void draw(SpriteBatch batch, float x, float y) {
        batch.draw(
                regions[frame],//текстура
                x, y,//точка отрисовки
                halfWidth, halfHeight,//точка вращения (берём по центру)
                getWidth(), getHeight(),//ширина, высота
                scale, scale,//скалирование по оси Х и по оси У
                angle//угол вращение
        );
    }

}
