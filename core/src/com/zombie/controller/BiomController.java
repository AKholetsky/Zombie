package com.zombie.controller;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zombie.actors.Tree;

import java.util.ArrayList;
import java.util.List;

public class BiomController {

    private List<Tree> treesToBeCutted;

    public BiomController() {
        this.treesToBeCutted = new ArrayList<>();
    }

    public void add(Tree tree) {
        this.treesToBeCutted.add(tree);
    }

    public Tree checkTree(Vector3 position) {
        for (Tree tree : treesToBeCutted) {
            //Tree zone
            Vector3 treePos = new Vector3(tree.getTreePosition());
            float maxX = treePos.x + tree.treeWidth();
            float minX = treePos.x;
            float maxY = treePos.y + tree.treeHeight();
            float minY = treePos.y;
            if (position.x > minX && position.x < maxX && position.y > minY && position.y < maxY) {
                return tree;
            } else {
                return null;
            }
        }
        return null;
    }




}
