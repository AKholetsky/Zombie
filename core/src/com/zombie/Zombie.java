package com.zombie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zombie.actors.WhiteWave;
import com.zombie.animation.GameAnimation;
import com.zombie.config.animation.GameAnimationConfig;

public class Zombie extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;

    private LandMap map;
    private WhiteWave whiteWave;
    private OrthoCamController camController;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camController = new OrthoCamController(camera);
		batch = new SpriteBatch();
		map = new LandMap("maps/main_island/main_island_map_config.xml", camera);
		map.create();
		viewport = new FillViewport(map.worldWidth(), map.worldHeight(), camera);
		viewport.apply();
		camera.setToOrtho(true);
		whiteWave = new WhiteWave(
				new GameAnimation(new GameAnimationConfig("animations/white_wave/","white_wave.xml")),
				batch,
				viewport);
		whiteWave.create();
		InputMultiplexer processor = new InputMultiplexer(whiteWave);
		processor.addProcessor(camController);
		Gdx.input.setInputProcessor(processor);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0.2f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		map.render();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		whiteWave.draw(Gdx.graphics.getDeltaTime());
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
}
