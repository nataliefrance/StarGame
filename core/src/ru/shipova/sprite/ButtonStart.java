package ru.shipova.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.shipova.base.ScaledTouchUpButton;
import ru.shipova.math.Rect;
import ru.shipova.screen.GameScreen;

public class ButtonStart extends ScaledTouchUpButton {

    private Game game;

    public ButtonStart(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("start"));
        this.game = game;
        setHeightProportion(0.15f);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom() + 0.4f);
    }
}
