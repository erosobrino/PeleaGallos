package com.peleadegallos;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public class GestorGestos extends GestureDetector {
    private Stage escenario;
    ArrayList<Vector2> fling;
    Jugador jugadorActual;
    JuegoPrincipal juego;

    public GestorGestos(GestureListener listener, Stage escenario, ArrayList<Vector2> fling, JuegoPrincipal juego) {
        super(listener);
        this.escenario = escenario;
        this.fling = fling;
        this.juego = juego;
    }


    @Override
    public boolean keyDown(int keycode) {
        escenario.keyDown(keycode);
        super.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        escenario.keyUp(keycode);
        super.keyUp(keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        escenario.keyTyped(character);
        super.keyTyped(character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (!escenario.touchDown(screenX, screenY, pointer, button)) {
            super.touchDown(screenX, screenY, pointer, button);
            fling.clear();
            if (jugadorActual.avanza)
            jugadorActual.angulo=45;
            else
                jugadorActual.angulo=135;
            return false;
        } else return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!escenario.touchUp(screenX, screenY, pointer, button)) {
            super.touchUp(screenX, screenY, pointer, button);
            return false;
        } else return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!escenario.touchDragged(screenX, screenY, pointer)) {
            super.touchDragged(screenX, screenY, pointer);
//        System.out.println(screenX + " " + screenY);
            if (jugadorActual.avanza) {
                if (screenX >= (jugadorActual.getX() + juego.PIXEL_METRO_X / 2))//limita angulo de disparo a 90 grados
                    fling.add(new Vector2(screenX, screenY));
            } else {
                if (screenX <= (jugadorActual.getX() + juego.PIXEL_METRO_X / 2))
                    fling.add(new Vector2(screenX, screenY));
            }
//            System.out.println(jugadorActual.fixture.getUserData());
            return true;
        } else return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        escenario.mouseMoved(screenX, screenY);
        super.mouseMoved(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        escenario.scrolled(amount);
        super.scrolled(amount);
        return false;
    }
}
