package ru.shipova.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.shipova.math.Rect;
import ru.shipova.utils.Regions;

public class Sprite extends Rect {

    protected float angle;
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame;
    private boolean isDestroyed;

    public Sprite() {
    }

    public Sprite(TextureRegion region) {
        this.regions = new TextureRegion[1];
        this.regions[0] = region;
    }

    //rows - строки, cols - столбцы
    public Sprite(TextureRegion region, int rows, int cols, int frames) {
        this.regions = Regions.split(region, rows, cols, frames);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],//текстура
                getLeft(), getBottom(),//точка отрисовки
                halfWidth, halfHeight,//точка вращения (берём по центру)
                getWidth(), getHeight(),//ширина, высота
                scale, scale,//скалирование по оси Х и по оси У
                angle//угол вращение
        );
    }

    //delta - некий отрезок времени
    public void update(float delta) {

    }

    public void resize(Rect worldBounds) {

    }

    public void setHeightProportion(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public boolean touchDown(Vector2 touch, int pointer) {
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void flushDestroyed() {
        isDestroyed = false;
    }

    public void destroy() {
        isDestroyed = true;
    }
}
