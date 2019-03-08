package com.peleadegallos;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

/**
 * Gestor de gestos utilizado para la pantalla de juego, poder realizar fling y usar los botones a la vez
 */
public class GestorGestos extends GestureDetector {
    private Stage escenario;
    /**
     * La coleccion de puntos que tiene para despues calcular l angulo de lanzamiento
     */
    ArrayList<Vector2> fling;
    /**
     * El jugador que realiza el fling
     */
    Jugador jugadorActual;
    /**
     * La pantalla de juego principal
     */
    JuegoPrincipal juego;

    /**
     * Inicializa el gestor de gestos con el escenario y jugador actual, este se cambia con los turnos
     *
     * @param listener  el listener de los gestos
     * @param escenario el escenario
     * @param fling     la coleccion de puntos que tiene acutalmente
     * @param juego     la pantalla de juego principal
     */
    public GestorGestos(GestureListener listener, Stage escenario, ArrayList<Vector2> fling, JuegoPrincipal juego) {
        super(listener);
        this.escenario = escenario;
        this.fling = fling;
        this.juego = juego;
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
     * Cuando se pulsa en la pantalla se eliminan los puntos que habia anteriormente, en el caso de que no lo gestione el escenario
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
            fling.clear();
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
     * En ese caso este va añadiendo la posicion a la coleccion de puntos y lo limita a 90 grados
     * y asi no poder disparar hacia atras
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
            if (jugadorActual.avanza) {
                if (screenX >= (jugadorActual.getX() + juego.PIXEL_METRO_X / 2))//limita angulo de disparo a 90 grados
                    fling.add(new Vector2(screenX, screenY));
            } else {
                if (screenX <= (jugadorActual.getX() + juego.PIXEL_METRO_X / 2))
                    fling.add(new Vector2(screenX, screenY));
            }
            return true;
        } else return true;
    }

    /**
     * No utilizada pero se intenta gestionar primero con el escenario
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
