package com.peleadegallos;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

/**
 * The type Gestos scroll records.
 */
public class GestosScrollRecords extends GestureDetector {
    private Stage escenario;
    private int altoPantalla;
    private Vector2 pInicio;
    private PantallaLogros logros;

    /**
     * Instantiates a new Gestos scroll records.
     *
     * @param listener     the listener
     * @param escenario    the escenario
     * @param altoPantalla the alto pantalla
     * @param logros       the logros
     */
    public GestosScrollRecords(GestureDetector.GestureListener listener, Stage escenario, int altoPantalla, PantallaLogros logros) {
        super(listener);
        this.escenario = escenario;
        this.altoPantalla = altoPantalla;
        this.logros = logros;
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
            pInicio = new Vector2(screenX, screenY);
            return true;
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
            if ((pInicio.y - screenY) % (altoPantalla / 100) == 0) {
                if (pInicio.y > screenY) {
                    if (logros.posDedo < (logros.juego.datosGuardados.getRecords().size() - 6))
                        logros.posDedo++;
                } else {
                    if (logros.posDedo > 0)
                        logros.posDedo--;
                }
                pInicio = new Vector2(screenX, screenY);
            }
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
        System.out.println(amount + "scrolllll");
        escenario.scrolled(amount);
        super.scrolled(amount);
        return false;
    }
}