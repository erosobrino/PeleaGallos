package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;
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

    Bala bala;
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

    String tiempoString = 20 + "";
    int tiempoTurno = 20;
    int tiempo = tiempoTurno;
    int contTimer = 0;
    int segundosPartida = 0;

    boolean partidaAcabada;
    int idGanador;
    boolean visible;
    int balasUtilizadas = 0;
    boolean debeSaltar = false; //salto continuo con sonido, contact listener

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
                for (Jugador jugador : jugadores) {
                    if (jugador.turno) {
                        jugador.movimiento = Jugador.Movimiento.adelante;
                        juego.botonPulsado(jugador.sonidoAndar);
                    }
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
                for (Jugador jugador : jugadores) {
                    if (jugador.turno) {
                        jugador.movimiento = Jugador.Movimiento.atras;
                        juego.botonPulsado(jugador.sonidoAndar);
                    }
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
                for (Jugador jugador : jugadores) {
                    if (jugador.turno) {
                        jugador.movimiento = Jugador.Movimiento.saltaAdelante;
                        if (!jugador.saltando)
                            juego.botonPulsado(jugador.sonidoSalto);
                    }
                }
                debeSaltar = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                for (Jugador jugador : jugadores) {
                    if (jugador.turno)
                        jugador.movimiento = Jugador.Movimiento.nada;
                }
                debeSaltar = false;
            }
        });

        btSaltarAtras = new Image(juego.manager.get("iconos/flechaArriba.png", Texture.class));
        btSaltarAtras.setSize(altoPantalla / 7, altoPantalla / 7);
        btSaltarAtras.setPosition(altoPantalla / 7 + 10, 5);
        btSaltarAtras.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                for (Jugador jugador : jugadores) {
                    if (jugador.turno) {
                        jugador.movimiento = Jugador.Movimiento.saltaAtras;
                        if (!jugador.saltando)
                            juego.botonPulsado(jugador.sonidoSalto);
                    }
                }
                debeSaltar = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                for (Jugador jugador : jugadores) {
                    if (jugador.turno)
                        jugador.movimiento = Jugador.Movimiento.nada;
                }
                debeSaltar = false;
            }
        });

        btDisparo = new Image(juego.manager.get("iconos/disparo.png", Texture.class));
        btDisparo.setSize(altoPantalla / 7, altoPantalla / 7);
        btDisparo.setPosition(altoPantalla / 7 * 3, 5);
        btDisparo.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Vector2 inicio = null;
                Vector2 centroJugador = null;
                double angulo;
                for (Jugador jugador : jugadores) {
                    if (jugador.turno) {
                        if (jugador.avanza)
                            angulo = Math.toRadians(45); //angulo predefinido
                        else
                            angulo = Math.toRadians(135); //angulo predefinido
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
                        if (inicio != null) {
                            balasUtilizadas++;
                            balas.add(new Bala(mundo, juego.manager.get("dino/Idle (1).png", Texture.class), inicio, juego, (float) angulo));
                            escenario.addActor(balas.get(balas.size() - 1));
                        }
                    }
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
                juego.botonPulsado(sonidoClick);
                for (Jugador jugador : jugadores) {
                    jugador.turno = !jugador.turno;
                    if (jugador.turno)
                        jugadorActual = jugador.fixture.getUserData().toString();
                    else
                        jugador.movimiento = Jugador.Movimiento.nada;
                }
                fling.clear();
            }
        });

        formas = new ShapeRenderer();
        batchTexto = new SpriteBatch();

        Timer.Task t = Timer.schedule(new Timer.Task() {
                                          @Override
                                          public void run() {
                                              timer();
                                          }
                                      }
                , 0, 0.200f
        );
    }

    @Override
    public void show() {
        super.show();
        fling = new ArrayList<Vector2>();
        balas = new ArrayList<Bala>();
        idGanador = 0;
        partidaAcabada = false;
        visible = true;
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

        Texture[] parado = new Texture[10];
        parado[0] = juego.manager.get("dino/Idle (1).png", Texture.class);
        parado[1] = juego.manager.get("dino/Idle (2).png", Texture.class);
        parado[2] = juego.manager.get("dino/Idle (3).png", Texture.class);
        parado[3] = juego.manager.get("dino/Idle (4).png", Texture.class);
        parado[4] = juego.manager.get("dino/Idle (5).png", Texture.class);
        parado[5] = juego.manager.get("dino/Idle (6).png", Texture.class);
        parado[6] = juego.manager.get("dino/Idle (7).png", Texture.class);
        parado[7] = juego.manager.get("dino/Idle (8).png", Texture.class);
        parado[8] = juego.manager.get("dino/Idle (9).png", Texture.class);
        parado[9] = juego.manager.get("dino/Idle (10).png", Texture.class);

        Texture[] mov = new Texture[10];
        mov[0] = juego.manager.get("dino/Walk (1).png", Texture.class);
        mov[1] = juego.manager.get("dino/Walk (2).png", Texture.class);
        mov[2] = juego.manager.get("dino/Walk (3).png", Texture.class);
        mov[3] = juego.manager.get("dino/Walk (4).png", Texture.class);
        mov[4] = juego.manager.get("dino/Walk (5).png", Texture.class);
        mov[5] = juego.manager.get("dino/Walk (6).png", Texture.class);
        mov[6] = juego.manager.get("dino/Walk (7).png", Texture.class);
        mov[7] = juego.manager.get("dino/Walk (8).png", Texture.class);
        mov[8] = juego.manager.get("dino/Walk (9).png", Texture.class);
        mov[9] = juego.manager.get("dino/Walk (10).png", Texture.class);

        Texture[] salto = new Texture[9];
        salto[0] = juego.manager.get("dino/Jump (3).png", Texture.class);
        salto[1] = juego.manager.get("dino/Jump (4).png", Texture.class);
        salto[2] = juego.manager.get("dino/Jump (5).png", Texture.class);
        salto[3] = juego.manager.get("dino/Jump (6).png", Texture.class);
        salto[4] = juego.manager.get("dino/Jump (7).png", Texture.class);
        salto[5] = juego.manager.get("dino/Jump (8).png", Texture.class);
        salto[6] = juego.manager.get("dino/Jump (9).png", Texture.class);
        salto[7] = juego.manager.get("dino/Jump (10).png", Texture.class);
        salto[8] = juego.manager.get("dino/Jump (11).png", Texture.class);

        Texture[] muerto = new Texture[8];
        muerto[0] = juego.manager.get("dino/Dead (1).png", Texture.class);
        muerto[1] = juego.manager.get("dino/Dead (2).png", Texture.class);
        muerto[2] = juego.manager.get("dino/Dead (3).png", Texture.class);
        muerto[3] = juego.manager.get("dino/Dead (4).png", Texture.class);
        muerto[4] = juego.manager.get("dino/Dead (5).png", Texture.class);
        muerto[5] = juego.manager.get("dino/Dead (6).png", Texture.class);
        muerto[6] = juego.manager.get("dino/Dead (7).png", Texture.class);
        muerto[7] = juego.manager.get("dino/Dead (8).png", Texture.class);

        jugador1 = new Jugador(mundo, parado, mov, salto, muerto, new Vector2(0, 3), juego, true, Jugador.Movimiento.nada);
        jugador2 = new Jugador(mundo, parado, mov, salto, muerto, new Vector2(15, 3), juego, false, Jugador.Movimiento.nada);

        jugador1.fixture.setUserData("jugador1");
        jugador2.fixture.setUserData("jugador2");

        jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);

        for (Suelo suelo : suelos) {
            escenario.addActor(suelo);
            suelo.fixture.setFriction(0.7f);
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
                    if (jugador1.saltando) {
                        jugador1.saltando = false;
                        jugador1.tocaSuelo = true;
                        if (debeSaltar)
                            juego.botonPulsado(jugador1.sonidoSalto);
                    }
                }
                if (hanColisionado(contact, "jugador2", "suelo")) {
                    if (jugador2.saltando) {
                        jugador2.saltando = false;
                        jugador2.tocaSuelo = true;
                        if (debeSaltar)
                            juego.botonPulsado(jugador2.sonidoSalto);
                    }
                }
                if (hanColisionado(contact, "jugador1", "jugador2")) {
                    if (jugador1.saltando) {
                        jugador1.saltando = false;
                        jugador1.tocaSuelo = true;
                        if (debeSaltar)
                            juego.botonPulsado(jugador1.sonidoSalto);
                    }
                    if (jugador2.saltando) {
                        jugador2.saltando = false;
                        jugador2.tocaSuelo = true;
                        if (debeSaltar)
                            juego.botonPulsado(jugador2.sonidoSalto);
                    }
                }

                for (Bala bala : balas) {
                    if (hanColisionado(contact, bala.idBala, "jugador1")) {
                        jugador1.setVida(jugador1.getVida() - bala.daño);
                        bala.impacto = true;
                        comprobarFinalizacion();
                    }
                    if (hanColisionado(contact, bala.idBala, "jugador2")) {
                        jugador2.setVida(jugador2.getVida() - bala.daño);
                        bala.impacto = true;
                        comprobarFinalizacion();
                    }
                    if (hanColisionado(contact, bala.idBala, "suelo")) {
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

        tiempo = tiempoTurno;
        tiempoString = tiempo + "";
    }

    private void comprobarFinalizacion() {
        for (final Jugador jugador : jugadores) {
            if (!partidaAcabada) {
                if (!jugador.vivo) {
                    partidaAcabada = true;
                    if (jugador.fixture.getUserData().equals("jugador1"))
                        idGanador = 2;
                    else
                        idGanador = 1;
                    Gdx.input.setInputProcessor(null);
                    fling.clear();
                    //Cuando acaba la partida espera 1 segundo y ejecuta el siguiente codigo
                    escenario.addAction(Actions.sequence(Actions.delay(2), Actions.run(new Runnable() {
                        @Override
                        public void run() {
                            juego.finalizacionPartida.pantallaAnterior = juego.pantallaJuego1;
                            juego.finalizacionPartida.ganador = idGanador;
                            juego.finalizacionPartida.tiempo = segundosPartida;
                            juego.finalizacionPartida.balas = balasUtilizadas;
                            if (jugadorActual.equals("jugador1"))
                                juego.finalizacionPartida.framesGanador = jugador1.frameParadoAv;
                            else
                                juego.finalizacionPartida.framesGanador = jugador2.frameParadoAv;
                            juego.pantallaJuego1 = new PantallaJuego1(juego);
                            juego.setScreen(juego.finalizacionPartida);
                        }
                    })));
                }
            }
        }
    }

    private void timer() {
        if (visible && !partidaAcabada) {
            contTimer++;
            segundosPartida++;
            if (contTimer > 5) {
                contTimer = 0;
                tiempo--;
                tiempoString = tiempo + "";
                if (tiempo <= 0) {
                    tiempo = 20;
                    InputEvent evento = new InputEvent();
                    evento.setType(InputEvent.Type.touchDown);
                    btCambioPersonaje.fire(evento);
                    evento.setType(InputEvent.Type.touchUp);
                    btCambioPersonaje.fire(evento);
                }
            }
            for (Jugador jugador : jugadores) {
                jugador.cambiaFrame();
            }
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            juego.setScreen(juego.menuInicio);
        }

        batchTexto.begin();
        fuente.draw(batchTexto, jugadorActual, anchoPantalla / 3, altoPantalla - altoPantalla / 10);
        fuente.draw(batchTexto, tiempoString + "s", anchoPantalla / 3 * 2, altoPantalla - altoPantalla / 10);
        fuente.draw(batchTexto, jugador1.getVida() + "", anchoPantalla / 18, altoPantalla - altoPantalla / 10);
        fuente.draw(batchTexto, jugador2.getVida() + "", anchoPantalla - anchoPantalla / 12, altoPantalla - altoPantalla / 10);
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

        for (int i = balas.size() - 1; i >= 0; i--) {
            if (balas.get(i).impacto) { //Se elimina despues del mundo step para evitar error al renderizar la bala a null
                balas.get(i).elimina();
                balas.get(i).remove();
                balas.remove(i);
            }
        }
    }

    @Override
    public void hide() {
        visible = false;
        for (Suelo suelo : suelos) {
            suelo.elimina();
            suelo.remove();
        }
        jugador1.elimina();
        jugador1.remove();
        jugador2.elimina();
        jugador2.remove();

        if (bala != null) {
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
