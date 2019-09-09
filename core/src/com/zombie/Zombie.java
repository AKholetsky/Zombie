package com.zombie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zombie.actors.Tree;
import com.zombie.actors.WalkingZombie;
import com.zombie.actors.WhiteWave;
import com.zombie.animation.GameAnimation;
import com.zombie.config.animation.GameAnimationConfig;
import com.zombie.controller.BiomController;
import com.zombie.controller.InputController;

public class Zombie extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;

    private LandMap map;
    private WhiteWave whiteWave;
    private OrthoCamController camController;
    private WalkingZombie walkingZombie;
    private Tree tree;

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
				batch);
		whiteWave.create();

		tree = new Tree(
				new GameAnimation(new GameAnimationConfig("animations/d_tropic_palm_01_0/", "d_tropic_palm_01_0.xml")),
				new GameAnimation(new GameAnimationConfig("animations/d_tropic_palm_01_s/", "d_tropic_palm_01_s.xml")),
				new GameAnimation(new GameAnimationConfig("animations/d_tropic_palm_01_stump/", "d_tropic_palm_01_stump.xml")),
				batch
		);
		tree.create();

		BiomController biomController = new BiomController();
		biomController.add(tree);

		walkingZombie = new WalkingZombie(
				new GameAnimation(new GameAnimationConfig("animations/anim_woodcutter_walk_up/", "anim_woodcutter_walk_up.xml")),
				new GameAnimation(new GameAnimationConfig("animations/anim_woodcutter_walk_down/", "anim_woodcutter_walk_down.xml")),
				new GameAnimation(new GameAnimationConfig("animations/anim_woodcutter_stand/", "anim_woodcutter_stand.xml")),
				new GameAnimation(new GameAnimationConfig("animations/anim_woodcutter_woodcut/", "anim_woodcutter_woodcut.xml")),
				new GameAnimation(new GameAnimationConfig("animations/anim_woodcutter_walkwood_up/", "anim_woodcutter_walkwood_up.xml")),
				new GameAnimation(new GameAnimationConfig("animations/anim_woodcutter_walkwood_down/", "anim_woodcutter_walkwood_down.xml")),
				batch,
				viewport,
				biomController);
		walkingZombie.create();

		InputController inputController = new InputController(viewport, biomController);
		inputController.regMoveTo(whiteWave);
		inputController.regMoveTo(walkingZombie);
		inputController.regMoveAndCut(walkingZombie);

		InputMultiplexer processor = new InputMultiplexer(inputController);
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
		float deltaTime = Gdx.graphics.getDeltaTime();
		whiteWave.draw(deltaTime);
		walkingZombie.draw(deltaTime);
		tree.draw(deltaTime);
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
