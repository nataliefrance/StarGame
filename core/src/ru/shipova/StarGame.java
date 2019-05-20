package ru.shipova;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

public class StarGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("background.jpg");
		Vector2 v1 = new Vector2(2,3);
		Vector2 v2 = new Vector2(0,-1);
		v1.add(v2);
		System.out.println("add: " + v1.x + " " + v1.y);

		v1.set(7, 10);
		v2.set(3, 3);
		v1.sub(v2);
		System.out.println("sub: " + v1.x + " " + v1.y);

		Vector2 v3 = new Vector2(4, 6);
		v3.scl(0.9f);
		System.out.println("v3: " + v3.x + " " + v3.y);

		//вычисляем угол между векторами в радианах
		v1.nor();
		v2.nor();
        System.out.println(Math.acos(v1.dot(v2)));

	}

	@Override
	public void render () {//выполняется 60 раз в секунду
		Gdx.gl.glClearColor(1, 0, 0, 1);//цвет фона
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
