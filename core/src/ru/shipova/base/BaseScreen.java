package ru.shipova.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

public abstract class BaseScreen implements Screen, InputProcessor {
    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    //работает 60 раз в секунду
    @Override
    public void render(float delta) {

    }

    //изменили размер экрана
    @Override
    public void resize(int width, int height) {

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

        return false;
    }

    //отпустили тач или клик мыши, координаты мыши или пальца
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    //тапнули в одном месте, протащили по экрану, отпустили в другом месте
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

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
