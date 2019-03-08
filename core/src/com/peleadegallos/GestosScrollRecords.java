package com.peleadegallos;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

/**
 * Gestor de gestos utilizado para poder desplazar records y asi poder ver todos
 */
public class GestosScrollRecords extends GestureDetector {
    /**
     * El escenario que gestiona el resto de gsto o pulsaciones
     */
    private Stage escenario;
    /**
     * El alto de la pantalla, de esto depende cuando hay que mover el dedo para desplazar una posicion
     */
    private int altoPantalla;
    /**
     * El punto donde se toca la primera vez
     */
    private Vector2 pInicio;
    /**
     * La pantalla de logros en la que se desplazaran los records
     */
    private PantallaLogros logros;

    /**
     * Inicializa el gestor de gestos
     *
     * @param listener     el listener de los gestos
     * @param escenario    el escenario
     * @param altoPantalla el alto de la pantalla
     * @param logros       la pantalla de logros en la que se utilizara
     */
    public GestosScrollRecords(GestureDetector.GestureListener listener, Stage escenario, int altoPantalla, PantallaLogros logros) {
        super(listener);
        this.escenario = escenario;
        this.altoPantalla = altoPantalla;
        this.logros = logros;
    }

    /**
     * No utilizada pero se intenta gestionar primero con el escenario
     *
     * @param keycode
     * @return
     */
    @Override
    public boolean keyDown(int keycode) {
        escenario.keyDown(keycode);
        super.keyDown(keycode);
        return false;
    }

    /**
     * No utilizada pero se intenta gestionar primero con el escenario
     *
     * @param keycode
     * @return
     */
    @Override
    public boolean keyUp(int keycode) {
        escenario.keyUp(keycode);
        super.keyUp(keycode);
        return false;
    }

    /**
     * No utilizada pero se intenta gestionar primero con el escenario
     *
     * @param character
     * @return
     */
    @Override
    public boolean keyTyped(char character) {
        escenario.keyTyped(character);
        super.keyTyped(character);
        return false;
    }

    /**
     * Intenta gestionar primero con escenario y sino, guarda el punto donde se pulsa como inicial
     *
     * @param screenX la posicion el eje x que se pulsa
     * @param screenY la posicion el eje y que se pulsa
     * @param pointer el dedo utilizado
     * @param button  el boton pulsado
     * @return true si se gestiona, false si no
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (!escenario.touchDown(screenX, screenY, pointer, button)) {
            super.touchDown(screenX, screenY, pointer, button);
            pInicio = new Vector2(screenX, screenY);
            return true;
        } else return true;
    }

    /**
     * Primero se intenta gestionar con el escenario y despues con el listener
     *
     * @param screenX la posicion el eje x que se pulsa
     * @param screenY la posicion el eje y que se pulsa
     * @param pointer el dedo utilizado
     * @param button  el boton pulsado
     * @return true si se gestiona, false si no
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!escenario.touchUp(screenX, screenY, pointer, button)) {
            super.touchUp(screenX, screenY, pointer, button);
            return false;
        } else return true;
    }

    /**
     * Primero se intenta gestionar con el escenario y despues con el listener
     * Si se desplaza el dedo lo suficiente avana una posicion en los recordas mostrados en caso de que estos no entren
     *
     * @param screenX la posicion el eje x que se pulsa
     * @param screenY la posicion el eje y que se pulsa
     * @param pointer el dedo utilizado
     * @return true si se gestiona, false si no
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!escenario.touchDragged(screenX, screenY, pointer)) {
            super.touchDragged(screenX, screenY, pointer);
            if ((pInicio.y - screenY) > (altoPantalla / 120)) {
                if (pInicio.y > screenY) {
                    if (logros.posDedo < (logros.juego.datosGuardados.getRecords().size() - 6)) { //6 es la cantidad de records que se mostraran a la vez
                        logros.posDedo++;
                    }
                } else {
                    if (logros.posDedo > 0)
                        logros.posDedo--;
                }
                pInicio = new Vector2(screenX, screenY);
            }
            return true;
        } else return true;
    }

    /**
     * No utilizada pero se intenta gestionar primero con el escenario
     *
     * @param screenX
     * @param screenY
     * @return
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        escenario.mouseMoved(screenX, screenY);
        super.mouseMoved(screenX, screenY);
        return false;
    }

    /**
     * No utilizada pero se intenta gestionar primero con el escenario
     *
     * @param amount
     * @return
     */
    @Override
    public boolean scrolled(int amount) {
        escenario.scrolled(amount);
        super.scrolled(amount);
        return false;
    }
}