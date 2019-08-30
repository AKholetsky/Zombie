package com.zombie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zombie.animation.GameAnimation;
import com.zombie.config.animation.GameAnimationConfig;

public class Zombie extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;

    private LandMap map;
    private GameAnimation whiteWave;
    private GameAnimation animWoodcutterWakeUp;

    private float stateTime;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.update();
		camera.setToOrtho(true, 1920, 1080);
		viewport = new FitViewport(1920, 1080, camera);
		map = new LandMap("maps/main_island/main_island_map_config.xml", camera);
		map.create();
		stateTime = 0f;
		whiteWave = new GameAnimation(new GameAnimationConfig("animations/white_wave/","white_wave.xml"));
		whiteWave.create();
		animWoodcutterWakeUp = new GameAnimation(new GameAnimationConfig("animations/anim_woodcutter_wakeup/", "anim_woodcutter_wakeup.xml"));
		animWoodcutterWakeUp.create();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0.2f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		map.render();
		batch.setTransformMatrix(camera.view);
		batch.setProjectionMatrix(camera.projection);
		stateTime += Gdx.graphics.getDeltaTime();
		batch.begin();
		batch.draw(whiteWave.currentFrame(stateTime), 1, 1);
		batch.draw(animWoodcutterWakeUp.currentFrame(stateTime), 100, 100);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.update();
	}
}
