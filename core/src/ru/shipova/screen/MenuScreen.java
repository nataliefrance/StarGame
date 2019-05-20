package ru.shipova.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.shipova.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private SpriteBatch batch;
    private Texture img;
    private Vector2 touch;
    private Vector2 speed;
    private Vector2 position;
    private float rightBorder;
    private float topBorder;
    private float leftBorder;
    private float bottomBorder;
    private float speedX;
    private float speedY;
    private Vector2 direction;
    private boolean isTouched;

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        img = new Texture("cat.jpg");
        touch = new Vector2();
        speedX = 5f;
        speedY = 5f;
        speed = new Vector2(speedX, speedY);
        position = new Vector2();
        direction = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 1, 1, 1);//цвет фона
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, position.x, position.y);
        batch.end();
        rightBorder = position.x + img.getWidth();
        topBorder = position.y + img.getHeight();
        leftBorder = position.x;
        bottomBorder = position.y;

        if (isTouched){
            direction.x = touch.x - position.x;
            direction.y = touch.y - position.y;
            direction.nor();
            speed.scl(direction);
            position.add(speed);
            speed.set(speedX, speedY);
                if (Math.abs(touch.x - position.x) < 1 || Math.abs(touch.y - position.y) < 1){
                    isTouched = false;
                }
        }

        //картинка катается туда-сюда
//        if (leftBorder >= 0 && bottomBorder >= 0) {
//            if (rightBorder < Gdx.graphics.getWidth() && topBorder < Gdx.graphics.getHeight()) {
//                position.add(speed);
//            } else speed.set(-speedX, -speedY);
//            position.add(speed);
//        } else speed.set(speedX, speedY);
//        position.add(speed);

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
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

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        System.out.println("touch.x = " + touch.x + " touch.y = " + touch.y);
        isTouched = true;
        return false;
    }
}
