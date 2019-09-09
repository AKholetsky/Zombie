package com.zombie.controller;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zombie.actors.Tree;

import java.util.ArrayList;
import java.util.List;

public class InputController extends InputAdapter {

    private final BiomController biomController;
    private List<MoveTo> simpleMove;
    private List<MoveAndCut> moveAndCuts;
    private Viewport viewport;

    public InputController(Viewport viewport, BiomController biomController) {
        this.simpleMove = new ArrayList<>();
        this.moveAndCuts = new ArrayList<>();
        this.viewport = viewport;
        this.biomController = biomController;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector3 unproject = this.viewport.unproject(new Vector3(screenX, screenY, 0));
        Tree tree = biomController.checkTree(unproject);
        if (tree != null) {
            for (MoveAndCut moveAndCut : moveAndCuts) {
                moveAndCut.moveAndCut(unproject);
            }
        } else {
            for (MoveTo moveTo : simpleMove) {
                moveTo.moveTo(unproject);
            }
        }
        return false;
    }

    public void regMoveTo(MoveTo moveTo) {
        this.simpleMove.add(moveTo);
    }

    public void regMoveAndCut(MoveAndCut moveAndCut) {
        this.moveAndCuts.add(moveAndCut);
    }
}
