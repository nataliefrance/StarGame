package ru.shipova.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.shipova.base.BaseScreen;
import ru.shipova.base.Font;
import ru.shipova.math.Rect;
import ru.shipova.pool.BulletPool;
import ru.shipova.pool.EnemyPool;
import ru.shipova.pool.ExplosionPool;
import ru.shipova.sprite.Background;
import ru.shipova.sprite.Bullet;
import ru.shipova.sprite.ButtonNewGame;
import ru.shipova.sprite.EnemyShip;
import ru.shipova.sprite.MainShip;
import ru.shipova.sprite.MessageGameOver;
import ru.shipova.sprite.Star;
import ru.shipova.utils.EnemyGenerator;


public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;
    private static final String SCORE = "Score: ";
    private static final String HEALTH_POINT = "HP: ";
    private static final String LEVEL = "Level: ";
    private static final int SCORE_FOR_NEW_LEVEL = 10;

    private enum State {PLAYING, PAUSE, GAME_OVER}

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private Star[] starArray;
    private Music music;
    private Sound piuSound;
    private Sound explosionSound;
    private Sound enemyBulletSound;

    private MainShip mainShip;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private EnemyGenerator enemyGenerator;
    private State state;

    private MessageGameOver messageGameOver;
    private ButtonNewGame buttonNewGame;

    private int score;
    private int level;

    private Font font;
    private StringBuilder sbScore;
    private StringBuilder sbHealthPoint;
    private StringBuilder sbLevel;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/background.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.pack");
        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(0.03f); //3% от высоты экрана
        starArray = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            starArray[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("audio/explosion.mp3"));
        explosionPool = new ExplosionPool(atlas, explosionSound);
        piuSound = Gdx.audio.newSound(Gdx.files.internal("audio/piu.mp3"));
        mainShip = new MainShip(atlas, bulletPool, explosionPool, piuSound);
        enemyBulletSound = Gdx.audio.newSound(Gdx.files.internal("audio/enemyPiu.mp3"));
        enemyPool = new EnemyPool(bulletPool, explosionPool, enemyBulletSound, worldBounds, mainShip);
        enemyGenerator = new EnemyGenerator(worldBounds, enemyPool, atlas);
        score = 0;
        level = 1;
        sbScore = new StringBuilder();
        sbHealthPoint = new StringBuilder();
        sbLevel = new StringBuilder();
        music = Gdx.audio.newMusic(Gdx.files.internal("audio/gameMusic.mp3"));
        music.setVolume(0.2f);
        music.setLooping(true);
        music.play();
        messageGameOver = new MessageGameOver(atlas);
        buttonNewGame = new ButtonNewGame(atlas, this);
        state = State.PLAYING;
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        freeAllDestroyedActiveObjects();
        draw();
    }

    private void update(float delta) {
        for (Star star : starArray) {
            star.update(delta);
        }

        explosionPool.updateActiveSprites(delta);

        if (state == State.PLAYING) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyGenerator.generate(delta, level);
        }
    }

    private void checkCollisions() {
        if (state != State.PLAYING) {
            return;
        }

        List<EnemyShip> enemyList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();

        for (EnemyShip enemyShip : enemyList) {
            if (enemyShip.isDestroyed()) {
                continue;
            }
            float minDistance = enemyShip.getHalfWidth() + mainShip.getHalfWidth();
            //dst рассчитывает дистанцию между векторами
            if (enemyShip.pos.dst(mainShip.pos) < minDistance) {
                enemyShip.destroy();
                mainShip.getDamage(mainShip.getHealthPoint());
                state = State.GAME_OVER;
            }

            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemyShip.isBulletCollision(bullet)) {
                    enemyShip.getDamage(bullet.getDamage());
                    if (enemyShip.isDestroyed()) {
                        score += enemyShip.getScore();
                    }
                    bullet.destroy();
                }
            }

            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() == mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.getDamage(bullet.getDamage());
                    bullet.destroy();
                    if (mainShip.isDestroyed()) {
                        state = State.GAME_OVER;
                    }
                }
            }
        }
    }

    private void freeAllDestroyedActiveObjects() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.4f, 0.3f, 0.9f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : starArray) {
            star.draw(batch);
        }

        explosionPool.drawActiveSprites(batch);

        if (state == State.PLAYING) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);

            int levelUpdate = score / SCORE_FOR_NEW_LEVEL;

            if (levelUpdate > level - 1) {
                level += 1;
            }
        } else if (state == State.GAME_OVER) {
            messageGameOver.draw(batch);
            buttonNewGame.draw(batch);
        }
        printInfo();
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : starArray) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        messageGameOver.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        music.dispose();
        piuSound.dispose();
        explosionSound.dispose();
        enemyBulletSound.dispose();
        font.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchDown(touch, pointer);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchUp(touch, pointer);
        }
        return false;
    }

    @Override
    public void pause() {
        super.pause();
        if (state == State.GAME_OVER) {
            return;
        }
        state = State.PAUSE;
        music.pause();
    }

    @Override
    public void resume() {
        super.resume();
        if (state == State.GAME_OVER) {
            return;
        }
        state = State.PLAYING;
        music.play();
    }

    public void startNewGame() {
        state = State.PLAYING;

        score = 0;

        mainShip.startNewGame();
        bulletPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
    }

    private void printInfo() {
        sbScore.setLength(0);
        font.draw(batch, sbScore.append(SCORE).append(score), worldBounds.getLeft(), worldBounds.getTop() - 0.01f);

        sbHealthPoint.setLength(0);
        font.draw(batch, sbHealthPoint.append(HEALTH_POINT).append(mainShip.getHealthPoint()), worldBounds.pos.x, worldBounds.getTop() - 0.01f, Align.center); //Align.center - выравнивание по центру

        sbLevel.setLength(0);
        font.draw(batch, sbLevel.append(LEVEL).append(level), worldBounds.getRight(), worldBounds.getTop() - 0.01f, Align.right);
    }
}
