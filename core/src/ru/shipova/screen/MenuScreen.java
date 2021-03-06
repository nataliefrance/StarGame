package ru.shipova.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.shipova.base.BaseScreen;
import ru.shipova.math.Rect;
import ru.shipova.sprite.Background;
import ru.shipova.sprite.ButtonExit;
import ru.shipova.sprite.ButtonStart;
import ru.shipova.sprite.Star;
import ru.shipova.sprite.Title;

public class MenuScreen extends BaseScreen {

    private static final int STAR_COUNT = 256;

    private Game game;

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private Star[] starArray;
    private ButtonExit buttonExit;
    private ButtonStart buttonStart;
    private Title title;

    private Music music;

    public MenuScreen(Game game){
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/background.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/menuAtlas.pack");
        starArray = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            starArray[i] = new Star(atlas);
        }
        buttonExit = new ButtonExit(atlas);
        buttonStart = new ButtonStart(atlas, game);
        title = new Title(atlas);
        music = Gdx.audio.newMusic(Gdx.files.internal("audio/starwars.mp3"));
        music.setVolume(1);
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.4f, 0.3f, 0.9f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : starArray) {
            star.draw(batch);
        }
        buttonExit.draw(batch);
        buttonStart.draw(batch);
        title.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        music.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : starArray) {
            star.resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
        buttonStart.resize(worldBounds);
        title.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        buttonExit.touchDown(touch, pointer);
        buttonStart.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        buttonExit.touchUp(touch, pointer);
        buttonStart.touchUp(touch, pointer);
        return false;
    }

    private void update(float delta) {
        for (Star star : starArray) {
            star.update(delta);
        }
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
