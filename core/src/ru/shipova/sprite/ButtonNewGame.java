package ru.shipova.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.shipova.base.ScaledTouchUpButton;
import ru.shipova.math.Rect;
import ru.shipova.screen.GameScreen;

public class ButtonNewGame extends ScaledTouchUpButton {

    private GameScreen gameScreen;

    public ButtonNewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.12f);
        setBottom(-0.18f);
    }
}
