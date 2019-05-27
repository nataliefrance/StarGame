package ru.shipova.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.shipova.base.Sprite;
import ru.shipova.math.Rect;
import ru.shipova.math.Rnd;

public class Star extends Sprite {

    private Vector2 v;
    private Rect worldBounds;
    private float height;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        float vX = Rnd.nextFloat(-0.005f, 0.005f);
        float vY = Rnd.nextFloat(-0.4f, -0.1f);
        v = new Vector2(vX, vY);
        height = Rnd.nextFloat(0.0045f, 0.008f);
        setHeightProportion(height);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, posY);
    }

    @Override
    public void update(float delta) {
        if(height >= 0.008f){
            height = 0.0045f;
        } else {
            height += 0.00003f;
        }
        setHeightProportion(height);

        pos.mulAdd(v, delta);//происходит и сложение, и скалирование.
        if (getRight() < worldBounds.getLeft()){
            setLeft(worldBounds.getRight());
        }
        if (getLeft() > worldBounds.getRight()){
            setRight(worldBounds.getLeft());
        }
        if (getTop() < worldBounds.getBottom()){
            setBottom(worldBounds.getTop());
        }
    }
}
