package com.zombie.controller;

import com.badlogic.gdx.InputAdapter;

import java.util.ArrayList;
import java.util.List;

public class GlobalInputProcessor extends InputAdapter {

    private boolean dragged;

    private List<TouchUp> touchUpList;
    private List<Dragged> draggedList;
    private List<Scrolled> scrolledList;

    public GlobalInputProcessor() {
        this.touchUpList = new ArrayList<>();
        this.scrolledList = new ArrayList<>();
        this.draggedList = new ArrayList<>();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (dragged) {
            dragged = false;
            return false;
        }
        for (TouchUp touchUp : touchUpList) {
            touchUp.touchUp(screenX, screenY);
        }
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        dragged = true;
        for (Dragged dragged1 : draggedList) {
            dragged1.dragged(screenX, screenY);
        }
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean scrolled(int amount) {
        for (Scrolled scrolled : scrolledList) {
            scrolled.scrolled(amount);
        }
        return super.scrolled(amount);
    }

    public void registerTouchUp(TouchUp touchUp) {
        this.touchUpList.add(touchUp);
    }

    public void registerDragged(Dragged dragged) {
        this.draggedList.add(dragged);
    }

    public void registerScrolled(Scrolled scrolled) {
        this.scrolledList.add(scrolled);
    }
}
