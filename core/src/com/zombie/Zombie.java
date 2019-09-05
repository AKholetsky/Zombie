package com.zombie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zombie.actors.WalkingZombie;
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
    private WalkingZombie walkingZombie;

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

		walkingZombie = new WalkingZombie(
				new GameAnimation(new GameAnimationConfig("animations/anim_woodcutter_walk_up/", "anim_woodcutter_walk_up.xml")),
				batch,
				viewport);
		walkingZombie.create();
		InputMultiplexer processor = new InputMultiplexer(whiteWave);
		processor.addProcessor(camController);
		processor.addProcessor(walkingZombie);
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
		float deltaTime = Gdx.graphics.getDeltaTime();
		whiteWave.draw(deltaTime);
		walkingZombie.draw(deltaTime);
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
