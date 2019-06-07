package ru.shipova.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.shipova.base.ScaledTouchUpButton;
import ru.shipova.math.Rect;
import ru.shipova.screen.GameScreen;

public class ButtonNewGame extends ScaledTouchUpButton {

    private Game game;

    public ButtonNewGame(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("new_game"));
        this.game = game;

    }

    @Override
    public void action() {
        game.setScreen(new GameScreen(game));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.12f);
        setBottom(worldBounds.getBottom() + 0.3f);
    }
}
