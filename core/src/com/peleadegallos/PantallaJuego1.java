package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;

public class PantallaJuego1 extends PlantillaEscenas {

    Stage escenario;

    World mundo;

    Jugador jugador1;
    Jugador jugador2;

    String jugadorActual = "jugador1";

    ArrayList<Jugador> jugadores;
    ArrayList<Suelo> suelos;

    ArrayList<Bala> balas;

    Image btAdelante, btAtras, btSaltarAdelante, btSaltarAtras, btDisparo;

    OrthographicCamera camera;
    Box2DDebugRenderer renderer;

    Skin skin;
    TextButton btCambioPersonaje;

    ArrayList<Vector2> fling;
    ShapeRenderer formas;

    SpriteBatch batchTexto;

    ActorLimiteMapa limiteMapa;

    public PantallaJuego1(final JuegoPrincipal juego) {
        super(juego);

        mundo = new World(new Vector2(0, -10), true);

        camera = new OrthographicCamera(juego.metrosX, juego.metrosY);
        renderer = new Box2DDebugRenderer();
        escenario = new Stage(new FitViewport(anchoPantalla, altoPantalla));
        escenario.setDebugAll(true);
        camera.translate(6, 4);


        btAdelante = new Image(juego.manager.get("iconos/flechaDerecha.png", Texture.class));
        btAdelante.setSize(altoPantalla / 7, altoPantalla / 7);
        btAdelante.setPosition(anchoPantalla - altoPantalla / 7 - 5, 5);
        btAdelante.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (juego.vibracionEncendida)
                    Gdx.input.vibrate(juego.tiempoVibrar);
                for (Jugador jugador : jugadores) {
                    if (jugador.turno)
                        jugador.movimiento = Jugador.Movimiento.adelante;
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                for (Jugador jugador : jugadores) {
                    if (jugador.turno)
                        jugador.movimiento = Jugador.Movimiento.nada;
                }
            }
        });

        btAtras = new Image(juego.manager.get("iconos/flechaIzquierda.png", Texture.class));
        btAtras.setSize(altoPantalla / 7, altoPantalla / 7);
        btAtras.setPosition(5, 5);
        btAtras.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (juego.vibracionEncendida)
                    Gdx.input.vibrate(juego.tiempoVibrar);
                for (Jugador jugador : jugadores) {
                    if (jugador.turno)
                        jugador.movimiento = Jugador.Movimiento.atras;
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                for (Jugador jugador : jugadores) {
                    if (jugador.turno)
                        jugador.movimiento = Jugador.Movimiento.nada;
                }
            }
        });

        btSaltarAdelante = new Image(juego.manager.get("iconos/flechaArriba.png", Texture.class));
        btSaltarAdelante.setSize(altoPantalla / 7, altoPantalla / 7);
        btSaltarAdelante.setPosition(anchoPantalla - altoPantalla / 7 * 2 - 10, 5);
        btSaltarAdelante.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (juego.vibracionEncendida)
                    Gdx.input.vibrate(juego.tiempoVibrar);
                for (Jugador jugador : jugadores) {
                    if (jugador.turno)
                        jugador.movimiento = Jugador.Movimiento.saltaAdelante;
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                for (Jugador jugador : jugadores) {
                    if (jugador.turno)
                        jugador.movimiento = Jugador.Movimiento.nada;
                }
            }
        });

        btSaltarAtras = new Image(juego.manager.get("iconos/flechaArriba.png", Texture.class));
        btSaltarAtras.setSize(altoPantalla / 7, altoPantalla / 7);
        btSaltarAtras.setPosition(altoPantalla / 7 + 10, 5);
        btSaltarAtras.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (juego.vibracionEncendida)
                    Gdx.input.vibrate(juego.tiempoVibrar);
                for (Jugador jugador : jugadores) {
                    if (jugador.turno)
                        jugador.movimiento = Jugador.Movimiento.saltaAtras;
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                for (Jugador jugador : jugadores) {
                    if (jugador.turno)
                        jugador.movimiento = Jugador.Movimiento.nada;
                }
            }
        });

        btDisparo = new Image(juego.manager.get("iconos/disparo.png", Texture.class));
        btDisparo.setSize(altoPantalla / 7, altoPantalla / 7);
        btDisparo.setPosition(altoPantalla / 7 * 3, 5);
        btDisparo.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (juego.vibracionEncendida)
                    Gdx.input.vibrate(juego.tiempoVibrar);
                Vector2 inicio = null;
                Vector2 centroJugador = null;
                double angulo = Math.toRadians(45); //angulo predefinido
                for (Jugador jugador : jugadores) {
                    if (jugador.turno) {
                        inicio = new Vector2(jugador.getX() / juego.PIXEL_METRO_X, jugador.getY() / juego.PIXEL_METRO_Y); //Punto de salida de la bala, en la esquina a la que apunte
                        if (jugador.avanza)
                            inicio.x += jugador.tamañoX * 1.5f;
                        else
                            inicio.x -= jugador.tamañoX * 1.5f;
                        inicio.y += jugador.tamañoY * 1.5f;

                        if (fling.size() > 1) {
                            centroJugador = new Vector2(jugador.getX() + jugador.tamañoX * juego.PIXEL_METRO_X, jugador.getY() + jugador.tamañoY * juego.PIXEL_METRO_Y);
                            Vector2 ptoFinal = fling.get(fling.size() - 1);
                            float yy = altoPantalla - ptoFinal.y - centroJugador.y;
                            float xx = ptoFinal.x - centroJugador.x;
                            angulo = (float) Math.atan2(yy, xx);
                        }
                    }
                }
                if (inicio != null) {
                    balas.add(new Bala(mundo, juego.manager.get("dino1.png", Texture.class), inicio, juego, (float) angulo));
                    escenario.addActor(balas.get(balas.size() - 1));
                }
                return true;
            }
        });

        skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));         //Skin para botones y fuente (creada con hierro v5)

        btCambioPersonaje = new TextButton(juego.idiomas.get("cambioPersonaje"), skin);
        btCambioPersonaje.setSize(anchoPantalla / 5, altoPantalla / 7);
        btCambioPersonaje.setPosition(anchoPantalla / 2 - btCambioPersonaje.getWidth() / 2, 0);
        btCambioPersonaje.getLabel().setFontScale(0.5f);
        btCambioPersonaje.getLabel().setColor(Color.BLACK);
        btCambioPersonaje.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (juego.vibracionEncendida)
                    Gdx.input.vibrate(juego.tiempoVibrar);
                for (Jugador jugador : jugadores) {
                    jugador.turno = !jugador.turno;
                    if (jugador.turno)
                        jugadorActual = jugador.fixture.getUserData().toString();
                }
            }
        });

        formas = new ShapeRenderer();
        batchTexto = new SpriteBatch();
    }

    @Override
    public void show() {
        super.show();
        fling = new ArrayList<Vector2>();
        balas = new ArrayList<Bala>();
        //Limites  visible 0-8x 0-15y
        suelos = new ArrayList<Suelo>();
        suelos.add(new Suelo(mundo, juego.manager.get("2.png", Texture.class), new Vector2(0, 0), juego, anchoPantalla, altoPantalla, false));
        suelos.add(new Suelo(mundo, juego.manager.get("2.png", Texture.class), new Vector2(2, 0), juego, anchoPantalla, altoPantalla, false));
        suelos.add(new Suelo(mundo, juego.manager.get("2.png", Texture.class), new Vector2(4, 0.5f), juego, anchoPantalla, altoPantalla, true));
        suelos.add(new Suelo(mundo, juego.manager.get("2.png", Texture.class), new Vector2(6, 0.75f), juego, anchoPantalla, altoPantalla, true));
        suelos.add(new Suelo(mundo, juego.manager.get("2.png", Texture.class), new Vector2(8, 0.75f), juego, anchoPantalla, altoPantalla, true));
        suelos.add(new Suelo(mundo, juego.manager.get("2.png", Texture.class), new Vector2(10, 0.5f), juego, anchoPantalla, altoPantalla, true));
        suelos.add(new Suelo(mundo, juego.manager.get("2.png", Texture.class), new Vector2(12, 0), juego, anchoPantalla, altoPantalla, false));
        suelos.add(new Suelo(mundo, juego.manager.get("2.png", Texture.class), new Vector2(14, 0), juego, anchoPantalla, altoPantalla, false));


        jugador1 = new Jugador(mundo, juego.manager.get("dino1.png", Texture.class), new Vector2(0, 3), juego, true, Jugador.Movimiento.nada);
        jugador2 = new Jugador(mundo, juego.manager.get("dino1.png", Texture.class), new Vector2(15, 3), juego, false, Jugador.Movimiento.nada);

        jugador1.fixture.setUserData("jugador1");
        jugador2.fixture.setUserData("jugador2");


        jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);

        for (Suelo suelo : suelos) {
            escenario.addActor(suelo);
        }

        limiteMapa = new ActorLimiteMapa(mundo, juego, altoPantalla, anchoPantalla);
        escenario.addActor(limiteMapa);

        escenario.addActor(jugador1);
        escenario.addActor(jugador2);

        escenario.addActor(btAtras);
        escenario.addActor(btAdelante);
        escenario.addActor(btSaltarAtras);
        escenario.addActor(btSaltarAdelante);

        escenario.addActor(btCambioPersonaje);

        escenario.addActor(btDisparo);


        mundo.setContactListener(new ContactListener() {

            private boolean hanColisionado(Contact contact, Object nombreObjeto1, Object nombreObjeto2) {
                return (contact.getFixtureA().getUserData().equals(nombreObjeto1) && contact.getFixtureB().getUserData().equals(nombreObjeto2))
                        || (contact.getFixtureA().getUserData().equals(nombreObjeto2) && contact.getFixtureB().getUserData().equals(nombreObjeto1));
            }

            @Override
            public void beginContact(Contact contact) {
                if (hanColisionado(contact, "jugador1", "suelo")) {
                    jugador1.saltando = false;
                }
                if (hanColisionado(contact, "jugador2", "suelo")) {
                    jugador2.saltando = false;
                }

                if (hanColisionado(contact, "bala", "jugador1")) {
                    for (Bala bala : balas) {
                        jugador1.vida += bala.daño;
                        bala.impacto = true;
                    }
                }
                if (hanColisionado(contact, "bala", "jugador2")) {
                    for (Bala bala : balas) {
                        jugador2.vida -= bala.daño;
                        bala.impacto = true;
                    }
                }
                if (hanColisionado(contact, "bala", "suelo")) {
                    for (Bala bala : balas) {
                        bala.impacto = true;
                    }
                }

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        Gdx.input.setInputProcessor(new GestorGestos(new GestureDetector.GestureAdapter(), escenario, fling));//Poder usar gestos y que funcionen botones del escenario

        fuente.getData().setScale(0.4f);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            juego.setScreen(juego.menuInicio);
        }

        batchTexto.begin();
        fuente.draw(batchTexto, jugadorActual, anchoPantalla / 15, altoPantalla - altoPantalla / 8);
        batchTexto.end();

        if (fling.size() > 1) {
            formas.begin(ShapeRenderer.ShapeType.Line);
            formas.setColor(Color.BLACK);
            Vector2 inicio = null;
            for (Jugador jugador : jugadores) {
                if (jugador.turno) {
                    inicio = new Vector2(jugador.getX() + jugador.tamañoX * juego.PIXEL_METRO_X, jugador.getY() + jugador.tamañoY * juego.PIXEL_METRO_Y);
                }
            }
            if (inicio != null)
                formas.line(inicio.x, inicio.y, fling.get(fling.size() - 1).x, altoPantalla - fling.get(fling.size() - 1).y);
            formas.end();
        }

        escenario.act();
        mundo.step(delta, 6, 2);
        camera.update();
        renderer.render(mundo, camera.combined);
        escenario.draw();
    }

    @Override
    public void hide() {
        for (Suelo suelo : suelos) {
            suelo.elimina();
            suelo.remove();
        }
        jugador1.elimina();
        jugador1.remove();
        jugador2.elimina();
        jugador2.remove();

        for (Bala bala : balas) {
            bala.elimina();
            bala.remove();
        }

        btAtras.remove();
        btAdelante.remove();
        btSaltarAtras.remove();
        btSaltarAdelante.remove();

        btCambioPersonaje.remove();
        btDisparo.remove();

        limiteMapa.elimina();
        limiteMapa.remove();

        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        escenario.dispose();
        mundo.dispose();
    }
}
