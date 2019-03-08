package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PantallaAyuda extends PlantillaEscenas {

    /**
     * Las imagenes
     */
    Texture[] imagenes;
    /**
     * Los textos informativos
     */
    String[] textos;
    /**
     * El boton adelante.
     */
    Image btadelante,
    /**
     * El boton atras.
     */
    btatras;
    /**
     * La imagen seleccionado.
     */
    Image imagenMostrada;
    /**
     * El Indice de la imagen mostrada
     */
    int indiceImagen = 0;
    /**
     * La altura del mensage
     */
    float y;
    /**
     * Batch para escribir por pantalla
     */
    SpriteBatch batch;

    /**
     * Inicializa la pantalla de ayuda
     *
     * @param juego the juego
     */
    public PantallaAyuda(final JuegoPrincipal juego) {
        super(juego);

        //Cambian los imagenes elegidas
        btadelante = new Image(juego.manager.get("iconos/flechaDerecha.png", Texture.class));
        btadelante.setSize(altoPantalla / 8, altoPantalla / 8);
        btadelante.setPosition(anchoPantalla * 0.99f - altoPantalla / 8, altoPantalla / 4);
        btadelante.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                indiceImagen++;
                if (indiceImagen >= imagenes.length)
                    indiceImagen = 0;
                actualizaImg(indiceImagen);
                juego.botonPulsado(sonidoClick);
                return true;
            }
        });

        btatras = new Image(juego.manager.get("iconos/flechaIzquierda.png", Texture.class));
        btatras.setSize(altoPantalla / 8, altoPantalla / 8);
        btatras.setPosition(anchoPantalla * 0.01f, altoPantalla / 4);
        btatras.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                indiceImagen--;
                if (indiceImagen < 0)
                    indiceImagen = imagenes.length - 1;
                actualizaImg(indiceImagen);
                juego.botonPulsado(sonidoClick);
                return true;
            }
        });
        batch = new SpriteBatch();

        imagenes = new Texture[8];
        for (int i = 0; i < imagenes.length; i++) {
            imagenes[i] = juego.manager.get("tutorial/tutorial" + (i + 1) + ".png", Texture.class);
        }
        textos = new String[8];
        textos[0] = juego.idiomas.get("tuto1");
        textos[1] = juego.idiomas.get("tuto2");
        textos[2] = juego.idiomas.get("tuto3");
        textos[3] = juego.idiomas.get("tuto4");
        textos[4] = juego.idiomas.get("tuto5");
        textos[5] = juego.idiomas.get("tuto6");
        textos[6] = juego.idiomas.get("tuto7");
        textos[7] = juego.idiomas.get("tuto8");
    }

    /**
     * Funcion que cambia la imagen seleccionada, si esta en el escenario lo elimina y despues lo añade
     *
     * @param indice el indice de imagen que se tiene que mostrar
     */
    private void actualizaImg(int indice) {
        if (imagenMostrada != null)
            imagenMostrada.remove();
        btatras.remove();
        btadelante.remove();
        home.remove();
        imagenMostrada = new Image(imagenes[indice]);
        imagenMostrada.setPosition(0, 0);
        imagenMostrada.setSize(anchoPantalla, altoPantalla);
        escenario.addActor(imagenMostrada);
        escenario.addActor(btadelante);
        escenario.addActor(btatras);
        escenario.addActor(home);
    }

    /**
     * Se ejecuta al mostrar la pantalla, añade los actores y actualiza la imagen del mapa
     */
    @Override
    public void show() {
        super.show();

        indiceImagen = 0;

        actualizaImg(indiceImagen);

        fuente.getData().setScale(escalado025);

        Gdx.input.setInputProcessor(escenario);
    }

    /**
     * Renderiza la pantalla llamando a la clase padre
     *
     * @param delta el tiempo desde la ultima ejecucion
     */
    @Override
    public void render(float delta) {
        super.render(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK))
            juego.setScreen(juego.menuInicio);

        escenario.act();
        escenario.draw();

        switch (indiceImagen) {
            case 0:
                y = 5;
                break;
            case 1:
                y = 1.5f;
                break;
            case 2:
                y = 6;
                break;
            case 3:
                y = 7;
                break;
            case 4:
                y = 6.75f;
                break;
            case 5:
                y = 2.25f;
                break;
            case 6:
                y = 2.25f;
                break;
            case 7:
                y = 2;
                break;
        }
        batch.begin();
        fuente.draw(batch, (indiceImagen+1)+"."+textos[indiceImagen], anchoPantalla * 0.01f, altoPantalla / 10 * y);
//        fuente.draw(batch, juego.idiomas.get("vibrar"), 4, altoPantalla / 10 * 7);
//        fuente.draw(batch, juego.idiomas.get("desarrollo"), 4, altoPantalla / 10 * 5 * 1.05f);
        batch.end();
    }

    /**
     * Se ejecuta al desaparecer la pantalla, elimina los actores
     */
    @Override
    public void hide() {
        super.hide();

        if (imagenMostrada != null)
            imagenMostrada.remove();
        home.remove();
        btadelante.remove();
        btatras.remove();

        Gdx.input.setInputProcessor(null);
    }
}
