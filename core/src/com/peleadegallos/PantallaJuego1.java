package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
    Jugador jugActual;

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

    double angulo;
    GestorGestos gestorGestos;

    public PantallaJuego1(final JuegoPrincipal juego) {
        super(juego);

        mundo = new World(new Vector2(0, -10), true);
        mundo.setAutoClearForces(true);

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
                jugActual.movimiento = Jugador.Movimiento.adelante;
                juego.botonPulsadoMusica(jugActual.sonidoAndar);
                jugActual.angulo = 45;//predefinido
                fling.clear();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                jugActual.movimiento = Jugador.Movimiento.nada;
                if (jugActual.sonidoAndar.isPlaying())
                    jugActual.sonidoAndar.stop();
            }
        });

        btAtras = new Image(juego.manager.get("iconos/flechaIzquierda.png", Texture.class));
        btAtras.setSize(altoPantalla / 7, altoPantalla / 7);
        btAtras.setPosition(5, 5);
        btAtras.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                jugActual.movimiento = Jugador.Movimiento.atras;
                juego.botonPulsadoMusica(jugActual.sonidoAndar);
                jugActual.angulo = 135;//predefinido
                fling.clear();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                jugActual.movimiento = Jugador.Movimiento.nada;
                if (jugActual.sonidoAndar.isPlaying())
                    jugActual.sonidoAndar.stop();
            }
        });

        btSaltarAdelante = new Image(juego.manager.get("iconos/flechaArriba.png", Texture.class));
        btSaltarAdelante.setSize(altoPantalla / 7, altoPantalla / 7);
        btSaltarAdelante.setPosition(anchoPantalla - altoPantalla / 7 * 2 - 10, 5);
        btSaltarAdelante.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                jugActual.movimiento = Jugador.Movimiento.saltaAdelante;
                if (!jugActual.saltando)
                    juego.botonPulsado(jugActual.sonidoSalto);
                jugActual.angulo = 45;//predefinido
                debeSaltar = true;
                fling.clear();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                jugActual.movimiento = Jugador.Movimiento.nada;
                debeSaltar = false;
            }
        });

        btSaltarAtras = new Image(juego.manager.get("iconos/flechaArriba.png", Texture.class));
        btSaltarAtras.setSize(altoPantalla / 7, altoPantalla / 7);
        btSaltarAtras.setPosition(altoPantalla / 7 + 10, 5);
        btSaltarAtras.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                jugActual.movimiento = Jugador.Movimiento.saltaAtras;
                jugActual.angulo = 135;//predefinido
                if (!jugActual.saltando)
                    juego.botonPulsado(jugActual.sonidoSalto);
                debeSaltar = true;
                fling.clear();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                jugActual.movimiento = Jugador.Movimiento.nada;
                debeSaltar = false;
            }
        });

        btDisparo = new Image(juego.manager.get("iconos/disparo.png", Texture.class));
        btDisparo.setSize(altoPantalla / 7, altoPantalla / 7);
        btDisparo.setPosition(altoPantalla / 7 * 3, 5);
        btDisparo.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (jugActual.balasRestantes > 0) {
                    Vector2 inicio = null;
                    inicio = new Vector2(jugActual.getX() / juego.PIXEL_METRO_X, jugActual.getY() / juego.PIXEL_METRO_Y); //Punto de salida de la bala, en la esquina a la que apunte
                    if (jugActual.avanza)                       //prefeinido para cuando lanza a 45
                        inicio.x += jugActual.tamañoX * 1.5f;
                    else
                        inicio.x -= jugActual.tamañoX * 1.5f;
                    inicio.y += jugActual.tamañoY * 1.5f;
                    if (inicio != null) {
                        balasUtilizadas++;
                        jugActual.balasRestantes--;
                        balas.add(new Bala(mundo, inicio, juego, jugActual));
                        escenario.addActor(balas.get(balas.size() - 1));
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
                    if (jugador.turno) {
                        jugadorActual = jugador.fixture.getUserData().toString();
                        jugActual = jugador;
                        gestorGestos.jugadorActual = jugador;
                    } else
                        jugador.movimiento = Jugador.Movimiento.nada;
                    jugador.balasRestantes = jugador.cantidadBalas;
                }
                tiempo = tiempoTurno;
                tiempoString = tiempo + "";
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

        jugador1 = cargaJugadore(juego.selectorPersonajeArma, true, new Vector2(0, 3));
        jugador2 = cargaJugadore(juego.selectorPersonajeArma2, false, new Vector2(15, 3));

        jugador2.avanza = false;
        jugador2.angulo = 135;

        jugador1.fixture.setUserData("jugador1");
        jugador2.fixture.setUserData("jugador2");

        jugadores = new ArrayList<Jugador>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);

        jugActual = jugador1;

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
                    } else {
                        if (hanColisionado(contact, bala.idBala, "jugador2")) {
                            jugador2.setVida(jugador2.getVida() - bala.daño);
                            bala.impacto = true;
                            comprobarFinalizacion();
                        } else {
                            if (hanColisionado(contact, bala.idBala, "suelo")) {
                                bala.impacto = true;
                            } else {
                                if (hanColisionado(contact, bala.idBala, "limiteIzquierda")) {
                                    bala.impacto = true;
                                } else {
                                    if (hanColisionado(contact, bala.idBala, "limiteDerecha")) {
                                        bala.impacto = true;
                                    } else {
                                        for (Bala bala2 : balas) {
                                            if (!bala.impacto) {
                                                if (hanColisionado(contact, bala.idBala, bala2.idBala))
                                                    bala.impacto = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
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

        gestorGestos = new GestorGestos(new GestureDetector.GestureAdapter(), escenario, fling, juego);
        gestorGestos.jugadorActual = jugador1;
        Gdx.input.setInputProcessor(gestorGestos);//Poder usar gestos y que funcionen botones del escenario

        fuente.getData().setScale(0.4f);

        tiempo = tiempoTurno;
        tiempoString = tiempo + "";
    }

    private Jugador cargaJugadore(SelectorPersonajeArma selector, boolean turno, Vector2 posicion) {
        String nombreJug = "dino";
        String arma = "armas/gun.png";
        String bala = "armas/bullet.png";
        String tipoBala = "pistola";
        int cantidadBalasJugador = 1;
        switch (selector.indicePersonaje) {
            case 0:
                nombreJug = "dino";
                break;
            case 1:
                nombreJug = "dino";
                break;
        }

        switch (selector.indiceArma) {
            case 0:
                arma = "armas/gun.png";
                bala = "armas/bullet.png";
                tipoBala = "pistola";
                cantidadBalasJugador = 4;
                break;
            case 1:
                arma = "armas/shotgun.png";
                bala = "armas/bullet.png";
                tipoBala = "uzi";
                cantidadBalasJugador = 6;
                break;
            case 2:
                arma = null;
                bala = "armas/bomb.png";
                tipoBala = "canon";
                cantidadBalasJugador = 1;
                break;
        }

        Texture texturaArma=null; //Para poder cargar un jugador sin arma
        try {
            texturaArma = juego.manager.get(arma, Texture.class);
        }catch (NullPointerException e){}

        TexturasJugador texturas = cargaTexturas(nombreJug);
        Jugador jugador = new Jugador(mundo, texturas.parado, texturas.mov, texturas.salto, texturas.muerto, posicion,
                juego.manager.get(bala, Texture.class),
                texturaArma,
                juego, turno, Jugador.Movimiento.nada, juego.pantallaJuego1, tipoBala, cantidadBalasJugador);
        return jugador;
    }

    private TexturasJugador cargaTexturas(String nombre) {
        TexturasJugador texturasJugador = new TexturasJugador();
        texturasJugador.parado[0] = juego.manager.get(nombre + "/Idle (1).png", Texture.class);
        texturasJugador.parado[1] = juego.manager.get(nombre + "/Idle (2).png", Texture.class);
        texturasJugador.parado[2] = juego.manager.get(nombre + "/Idle (3).png", Texture.class);
        texturasJugador.parado[3] = juego.manager.get(nombre + "/Idle (4).png", Texture.class);
        texturasJugador.parado[4] = juego.manager.get(nombre + "/Idle (5).png", Texture.class);
        texturasJugador.parado[5] = juego.manager.get(nombre + "/Idle (6).png", Texture.class);
        texturasJugador.parado[6] = juego.manager.get(nombre + "/Idle (7).png", Texture.class);
        texturasJugador.parado[7] = juego.manager.get(nombre + "/Idle (8).png", Texture.class);
        texturasJugador.parado[8] = juego.manager.get(nombre + "/Idle (9).png", Texture.class);
        texturasJugador.parado[9] = juego.manager.get(nombre + "/Idle (10).png", Texture.class);

        texturasJugador.mov[0] = juego.manager.get(nombre + "/Walk (1).png", Texture.class);
        texturasJugador.mov[1] = juego.manager.get(nombre + "/Walk (2).png", Texture.class);
        texturasJugador.mov[2] = juego.manager.get(nombre + "/Walk (3).png", Texture.class);
        texturasJugador.mov[3] = juego.manager.get(nombre + "/Walk (4).png", Texture.class);
        texturasJugador.mov[4] = juego.manager.get(nombre + "/Walk (5).png", Texture.class);
        texturasJugador.mov[5] = juego.manager.get(nombre + "/Walk (6).png", Texture.class);
        texturasJugador.mov[6] = juego.manager.get(nombre + "/Walk (7).png", Texture.class);
        texturasJugador.mov[7] = juego.manager.get(nombre + "/Walk (8).png", Texture.class);
        texturasJugador.mov[8] = juego.manager.get(nombre + "/Walk (9).png", Texture.class);
        texturasJugador.mov[9] = juego.manager.get(nombre + "/Walk (10).png", Texture.class);

        texturasJugador.salto[0] = juego.manager.get(nombre + "/Jump (3).png", Texture.class);
        texturasJugador.salto[1] = juego.manager.get(nombre + "/Jump (4).png", Texture.class);
        texturasJugador.salto[2] = juego.manager.get(nombre + "/Jump (5).png", Texture.class);
        texturasJugador.salto[3] = juego.manager.get(nombre + "/Jump (6).png", Texture.class);
        texturasJugador.salto[4] = juego.manager.get(nombre + "/Jump (7).png", Texture.class);
        texturasJugador.salto[5] = juego.manager.get(nombre + "/Jump (8).png", Texture.class);
        texturasJugador.salto[6] = juego.manager.get(nombre + "/Jump (9).png", Texture.class);
        texturasJugador.salto[7] = juego.manager.get(nombre + "/Jump (10).png", Texture.class);
        texturasJugador.salto[8] = juego.manager.get(nombre + "/Jump (11).png", Texture.class);

        texturasJugador.muerto[0] = juego.manager.get(nombre + "/Dead (1).png", Texture.class);
        texturasJugador.muerto[1] = juego.manager.get(nombre + "/Dead (2).png", Texture.class);
        texturasJugador.muerto[2] = juego.manager.get(nombre + "/Dead (3).png", Texture.class);
        texturasJugador.muerto[3] = juego.manager.get(nombre + "/Dead (4).png", Texture.class);
        texturasJugador.muerto[4] = juego.manager.get(nombre + "/Dead (5).png", Texture.class);
        texturasJugador.muerto[5] = juego.manager.get(nombre + "/Dead (6).png", Texture.class);
        texturasJugador.muerto[6] = juego.manager.get(nombre + "/Dead (7).png", Texture.class);
        texturasJugador.muerto[7] = juego.manager.get(nombre + "/Dead (8).png", Texture.class);

        return texturasJugador;
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
                            juego.finalizacionPartida.setTiempo(segundosPartida);
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
            if (contTimer > 5) {
                contTimer = 0;
                tiempo--;
                segundosPartida++;
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
        fuente.draw(batchTexto, juego.idiomas.get("balasRestantes") + ": "+jugActual.balasRestantes, anchoPantalla/18, altoPantalla - altoPantalla / 10*2);
        batchTexto.end();

        if (fling.size() > 1) {
            formas.begin(ShapeRenderer.ShapeType.Line);
            formas.setColor(Color.BLACK);
            Vector2 inicio = null;

            Vector2 centroJugador = new Vector2(jugActual.getX() + jugActual.tamañoX * juego.PIXEL_METRO_X, jugActual.getY() + jugActual.tamañoY * juego.PIXEL_METRO_Y);
            Vector2 ptoFinal = fling.get(fling.size() - 1);
            float yy = altoPantalla - ptoFinal.y - centroJugador.y;
            float xx = ptoFinal.x - centroJugador.x;
            angulo = (float) Math.atan2(yy, xx);
            jugActual.angulo = (float) Math.toDegrees(angulo);
//            System.out.println(jugActual.angulo);

            inicio = new Vector2(jugActual.getX() + jugActual.tamañoX * juego.PIXEL_METRO_X, jugActual.getY() + jugActual.tamañoY * juego.PIXEL_METRO_Y);

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
