package ru.shipova;
import com.badlogic.gdx.Game;
import ru.shipova.screen.MenuScreen;

public class StarGame extends Game {
	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}
}
