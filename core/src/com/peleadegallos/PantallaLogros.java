package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PantallaLogros extends PlantillaEscenas {

    SpriteBatch texto;

    public PantallaLogros(JuegoPrincipal juego) {
        super(juego);

        texto = new SpriteBatch();
    }

    @Override
    public void show() {
        super.show();

        escenario.addActor(fondo);
        escenario.addActor(home);
        fuente.getData().setScale(escalado025);

        Gdx.input.setInputProcessor(escenario);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK))
            juego.setScreen(juego.menuInicio);

        escenario.act();
        escenario.draw();

        texto.begin();
        float y = altoPantalla / 8 * 6.5f;
        fuente.draw(texto, juego.idiomas.get("jugador") + "1", anchoPantalla / 10 * 1, y);
        fuente.draw(texto, juego.idiomas.get("arma") + "", anchoPantalla / 10 * 2.75f, y);
        fuente.draw(texto, juego.idiomas.get("jugador") + "2", anchoPantalla / 10 * 3.75f, y);
        fuente.draw(texto, juego.idiomas.get("arma") + "", anchoPantalla / 10 * 5.5f, y);
        fuente.draw(texto, juego.idiomas.get("mapa") + "", anchoPantalla / 10 * 6.75f, y);
        fuente.draw(texto, juego.idiomas.get("balasRestantes") + "", anchoPantalla / 10 * 7.75f, y);
        fuente.draw(texto, juego.idiomas.get("tiempo") + "", anchoPantalla / 10 * 8.75f, y);
        y -= altoPantalla / 8;

        for (int i=0;i<juego.records.size();i++) {
            fuente.draw(texto, (i+1)+"", anchoPantalla / 10 * 0.25f, y);
            if (juego.records.get(i).idGanador==1)
                fuente.setColor(Color.YELLOW);
            else
                fuente.setColor(Color.RED);
            fuente.draw(texto, juego.idiomas.get(juego.records.get(i).jugador1), anchoPantalla / 10 * 1, y);
            fuente.draw(texto, juego.idiomas.get(juego.records.get(i).arma1), anchoPantalla / 10 * 2.75f, y);
            if (juego.records.get(i).idGanador==2)
                fuente.setColor(Color.YELLOW);
            else
                fuente.setColor(Color.RED);
            fuente.draw(texto, juego.idiomas.get(juego.records.get(i).jugador2), anchoPantalla / 10 * 3.75f, y);
            fuente.draw(texto, juego.idiomas.get(juego.records.get(i).arma2), anchoPantalla / 10 * 5.5f, y);
            fuente.setColor(Color.BLACK);
            fuente.draw(texto,juego.idiomas.get(juego.records.get(i).mapa), anchoPantalla / 10 * 6.75f, y);
            fuente.draw(texto, juego.records.get(i).balas + "", anchoPantalla / 10 * 7.75f, y);
            fuente.draw(texto, juego.records.get(i).tiempo + "s", anchoPantalla / 10 * 8.75f, y);

            y -= altoPantalla / 8;
        }
        texto.end();
    }

    @Override
    public void hide() {
        super.hide();
        fondo.remove();
        home.remove();

        Gdx.input.setInputProcessor(null);
    }
}
