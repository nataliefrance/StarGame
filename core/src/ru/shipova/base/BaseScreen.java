package ru.shipova.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.shipova.math.MatrixUtils;
import ru.shipova.math.Rect;


public abstract class BaseScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;
    private Vector2 touch;
    private Rect screenBounds;
    private Rect worldBounds;
    private Rect glBounds;

    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        this.batch = new SpriteBatch();
        this.touch = new Vector2();
        this.screenBounds = new Rect();
        this.worldBounds = new Rect();
        this.glBounds = new Rect(0, 0, 1f, 1f);

        this.worldToGl = new Matrix4();
        this.screenToWorld = new Matrix3();
    }

    //работает 60 раз в секунду
    @Override
    public void render(float delta) {

    }

    //изменили размер экрана
    @Override
    public void resize(int width, int height) {
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = width / (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * aspect);
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldToGl);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        resize(worldBounds);
    }

    public void resize(Rect worldBounds) {

    }

    //свернули экран
    @Override
    public void pause() {

    }

    //развернули экран
    @Override
    public void resume() {

    }

    //закрыли экран
    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    //нажали кнопку
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    //отпустили кнопку
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    //возращает символ, который печатает данная кнопка
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    //тач по экрану или клик мыши, координаты мыши или пальца
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDown(touch, pointer);
        System.out.println(touch.x + " " + touch.y);
        return false;
    }

    public boolean touchDown(Vector2 touch, int pointer) {
        return false;
    }

    //отпустили тач или клик мыши, координаты мыши или пальца
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchUp(touch, pointer);
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }

    //тапнули в одном месте, протащили по экрану, отпустили в другом месте
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDragged(touch, pointer);
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
