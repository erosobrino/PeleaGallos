package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SelectorPersonajeArma extends PlantillaEscenas {

    Personaje[] personajes;
    Arma[] armas;
    Image imgPersonaje, imgArmas;
    Image btadelantePer, btatrasPer, btadelanteArm, btatrasArm;
    Stage escenario;
    int indicePersonaje = 0, indiceArma = 0;
    SpriteBatch spriteBatch;
    int idJugador;
    TextButton continuar;

    public SelectorPersonajeArma(final JuegoPrincipal juego, final Personaje[] personajes, final Arma[] armas, final int idJugador) {
        super(juego);
        this.personajes = personajes;
        this.armas = armas;
        this.idJugador = idJugador;

        escenario = new Stage();

        btadelantePer = new Image(juego.manager.get("iconos/flechaArriba.png", Texture.class));
        btadelantePer.setSize(altoPantalla / 7, altoPantalla / 7);
        btadelantePer.setPosition(anchoPantalla / 8 * 1.25f, altoPantalla - altoPantalla / 3);
        btadelantePer.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                indicePersonaje++;
                if (indicePersonaje >= personajes.length)
                    indicePersonaje = 0;
                actualizaImagenPersonaje(indicePersonaje);
                juego.botonPulsado(sonidoClick);
                return true;
            }
        });

        btadelanteArm = new Image(juego.manager.get("iconos/flechaArriba.png", Texture.class));
        btadelanteArm.setSize(altoPantalla / 7, altoPantalla / 7);
        btadelanteArm.setPosition(anchoPantalla / 2 * 1.05f, altoPantalla - altoPantalla / 3);
        btadelanteArm.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                indiceArma++;
                if (indiceArma >= armas.length)
                    indiceArma = 0;
                actualizaImagenArma(indiceArma);
                juego.botonPulsado(sonidoClick);
                return true;
            }
        });

        btatrasPer = new Image(juego.manager.get("iconos/flechaAbajo.png", Texture.class));
        btatrasPer.setSize(altoPantalla / 7, altoPantalla / 7);
        btatrasPer.setPosition(anchoPantalla / 8 * 1.25f, altoPantalla - altoPantalla / 2 * 1.65f);
        btatrasPer.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                indicePersonaje--;
                if (indicePersonaje < 0)
                    indicePersonaje = personajes.length - 1;
                actualizaImagenPersonaje(indicePersonaje);
                juego.botonPulsado(sonidoClick);
                return true;
            }
        });

        btatrasArm = new Image(juego.manager.get("iconos/flechaAbajo.png", Texture.class));
        btatrasArm.setSize(altoPantalla / 7, altoPantalla / 7);
        btatrasArm.setPosition(anchoPantalla / 2 * 1.05f, altoPantalla - altoPantalla / 2 * 1.65f);
        btatrasArm.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                indiceArma--;
                if (indiceArma < 0)
                    indiceArma = armas.length - 1;
                actualizaImagenArma(indiceArma);
                juego.botonPulsado(sonidoClick);
                return true;
            }
        });

        continuar = new TextButton(juego.idiomas.get("jugar"), skin);
        continuar.setSize(anchoPantalla / 7 * 5, altoPantalla / 5 * 12 / 10);
        continuar.setPosition(anchoPantalla-anchoPantalla / 7, altoPantalla / 5 * 3);
        continuar.scaleBy(2);
        continuar.getLabel().setPosition(2, 2);
        continuar.getLabel().setFontScale(0.75f);
        continuar.getLabel().setColor(Color.BLACK);
        continuar.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.botonPulsado(sonidoClick);
                if (idJugador>1)
                    juego.setScreen(juego.pantallaJuego1);
                else
                    juego.setScreen(juego.selectorPersonajeArma2);
            }
        });

        actualizaImagenArma(indiceArma);
        actualizaImagenPersonaje(indicePersonaje);

        spriteBatch = new SpriteBatch();
    }


    //Necesario hacer asi porque sino no se puede eliminar del escenario y se superponen las imagenes
    private void actualizaImagenPersonaje(int indice) {
        if (imgPersonaje != null)
            imgPersonaje.remove();
        imgPersonaje = new Image(personajes[indice].getTextura());
        imgPersonaje.setPosition(anchoPantalla / 8, altoPantalla - altoPantalla / 6 * 3.75f);
        imgPersonaje.setSize(juego.PIXEL_METRO_X * 2, juego.PIXEL_METRO_Y * 2);
        escenario.addActor(imgPersonaje);
    }

    private void actualizaImagenArma(int indice) {
        if (imgArmas != null)
            imgArmas.remove();
        imgArmas = new Image(armas[indice].getTextura());
        imgArmas.setPosition(anchoPantalla / 2, altoPantalla - altoPantalla / 6 * 3.75f);
        imgArmas.setSize(juego.PIXEL_METRO_X * 2, juego.PIXEL_METRO_Y * 2);
        escenario.addActor(imgArmas);
    }

    @Override
    public void show() {
        super.show();

        escenario.addActor(fondo);
        escenario.addActor(btatrasPer);
        escenario.addActor(btatrasArm);
        escenario.addActor(btadelantePer);
        escenario.addActor(btadelanteArm);
        escenario.addActor(home);

        Gdx.input.setInputProcessor(escenario);

        fuente.getData().setScale(0.25f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        escenario.act();
        escenario.draw();

        spriteBatch.begin();
        fuente.getData().setScale(0.35f);
        fuente.draw(spriteBatch, juego.idiomas.get("jugador") + ": " + idJugador, anchoPantalla / 4 * 1.1f, altoPantalla - altoPantalla / 10);

        fuente.getData().setScale(0.25f);
        fuente.draw(spriteBatch, juego.idiomas.get("vida") + ": " + personajes[indicePersonaje].getVida(), anchoPantalla / 4 * 1.1f, altoPantalla - altoPantalla / 6 * 2 * 1.25f);
        fuente.draw(spriteBatch, juego.idiomas.get("velocidadPer") + ": " + personajes[indicePersonaje].getVelocidad(), anchoPantalla / 4 * 1.1f, altoPantalla - altoPantalla / 6 * 4 * 0.75f);

        fuente.draw(spriteBatch, juego.idiomas.get("alcance") + ": " + armas[indiceArma].getAlcance(), anchoPantalla / 2 * 1.25f, altoPantalla - altoPantalla / 3*0.65f);
        fuente.draw(spriteBatch, juego.idiomas.get("balasTotales") + ": " + armas[indiceArma].getBalas(), anchoPantalla / 2 * 1.25f, altoPantalla - altoPantalla / 3*0.95f);
        fuente.draw(spriteBatch, juego.idiomas.get("daño") + ": " + armas[indiceArma].getDaño(), anchoPantalla / 2 * 1.25f, altoPantalla - altoPantalla / 3*1.15f);
        spriteBatch.end();
    }

    @Override
    public void hide() {
        super.hide();

        home.remove();
        fondo.remove();
        btatrasPer.remove();
        btatrasArm.remove();
        btadelantePer.remove();
        btadelanteArm.remove();

        Gdx.input.setInputProcessor(null);
    }
}
