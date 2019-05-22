package ru.shipova.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.shipova.base.BaseScreen;
import ru.shipova.math.Rect;
import ru.shipova.sprite.Background;
import ru.shipova.sprite.SpaceCat;

public class MenuScreen extends BaseScreen {

    private Texture bg;
    private Background background;
    private SpaceCat spaceCat;
    private Texture sc_texture;

    private Vector2 v;
    private Vector2 buffer;

    private final float LEN = 0.1f;

    @Override
    public void show() {
        super.show();
        bg = new Texture("background.png");
        background = new Background(new TextureRegion(bg));
        sc_texture = new Texture("cat.png");
        spaceCat = new SpaceCat(new TextureRegion(sc_texture), 0.00f,  0.00f);
        buffer = new Vector2();

        v = new Vector2(0.01f, 0.01f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        super.render(delta);
        Gdx.gl.glClearColor(0.4f, 0.3f, 0.9f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        spaceCat.draw(batch, spaceCat.pos.x, spaceCat.pos.y);
        batch.end();
        buffer.set(touch);
        if (buffer.sub(spaceCat.pos).len() <= LEN){
            spaceCat.pos.set(touch);
        } else spaceCat.pos.add(v);

    }

    @Override
    public void dispose() {
        bg.dispose();
        sc_texture.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        spaceCat.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        v.set(touch.cpy().sub(spaceCat.pos).setLength(LEN));
        return super.touchDown(touch, pointer);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        super.hide();
    }


}
