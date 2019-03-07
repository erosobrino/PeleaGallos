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
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

/**
 * The type Pantalla juego.
 */
public class PantallaJuego extends PlantillaEscenas {

    /**
     * The Mundo.
     */
    World mundo;

    /**
     * The Jugador 1.
     */
    Jugador jugador1;
    /**
     * The Jugador 2.
     */
    Jugador jugador2;
    /**
     * The Jug actual.
     */
    Jugador jugActual;

    /**
     * The Jugador actual.
     */
    String jugadorActual = "jugador1";

    /**
     * The Jugadores.
     */
    ArrayList<Jugador> jugadores;
    /**
     * The Suelos.
     */
    ArrayList<Suelo> suelos;

    /**
     * The Bala.
     */
    Bala bala;
    /**
     * The Balas.
     */
    ArrayList<Bala> balas;

    /**
     * The Bt adelante.
     */
    Image btAdelante, /**
     * The Bt atras.
     */
    btAtras, /**
     * The Bt saltar adelante.
     */
    btSaltarAdelante, /**
     * The Bt saltar atras.
     */
    btSaltarAtras, /**
     * The Bt disparo.
     */
    btDisparo;

    /**
     * The Camera.
     */
    OrthographicCamera camera;
    /**
     * The Renderer.
     */
    Box2DDebugRenderer renderer;

    /**
     * The Skin.
     */
    Skin skin;
    /**
     * The Bt cambio personaje.
     */
    TextButton btCambioPersonaje;

    /**
     * The Fling.
     */
    ArrayList<Vector2> fling;
    /**
     * The Formas.
     */
    ShapeRenderer formas;

    /**
     * The Batch texto.
     */
    SpriteBatch batchTexto;

    /**
     * The Limite mapa.
     */
    ActorLimiteMapa limiteMapa;

    /**
     * The Tiempo string.
     */
    String tiempoString = 20 + "";
    /**
     * The Tiempo turno.
     */
    int tiempoTurno = 20;
    /**
     * The Tiempo.
     */
    int tiempo = tiempoTurno;
    /**
     * The Cont timer.
     */
    int contTimer = 0;
    /**
     * The Segundos partida.
     */
    int segundosPartida = 0;

    /**
     * The Partida acabada.
     */
    boolean partidaAcabada;
    /**
     * The Id ganador.
     */
    int idGanador;
    /**
     * The Visible.
     */
    boolean visible;
    /**
     * The Balas utilizadas.
     */
    int balasUtilizadas = 0;
    /**
     * The Debe saltar.
     */
    boolean debeSaltar = false; //salto continuo con sonido, contact listener

    /**
     * The Angulo.
     */
    double angulo;
    /**
     * The Gestor gestos.
     */
    GestorGestos gestorGestos;

    /**
     * The Mapa.
     */
    String mapa;

    /**
     * The Id actual.
     */
    int idActual = 1;

    /**
     * The Timer.
     */
    Timer.Task timer;

    /**
     * Instantiates a new Pantalla juego.
     *
     * @param juego the juego
     */
    public PantallaJuego(final JuegoPrincipal juego) {
        super(juego);

        mundo = new World(new Vector2(0, -10), true);
        mundo.setAutoClearForces(true);

        camera = new OrthographicCamera(juego.metrosX, juego.metrosY);
        renderer = new Box2DDebugRenderer();
//        escenario = new Stage(new FitViewport(anchoPantalla, altoPantalla));
//        escenario.setDebugAll(true);
        camera.translate(6, 4);

        btAdelante = new Image(juego.manager.get("iconos/flechaDerecha.png", Texture.class));
        btAdelante.setSize(altoPantalla / 7, altoPantalla / 7);
        btAdelante.setPosition(anchoPantalla - altoPantalla / 7 - 5, 5);
        btAdelante.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                fling.clear();
                jugActual.movimiento = Jugador.Movimiento.adelante;
                juego.botonPulsadoMusica(jugActual.sonidoAndar);
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
                fling.clear();
                jugActual.movimiento = Jugador.Movimiento.atras;
                juego.botonPulsadoMusica(jugActual.sonidoAndar);
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
                fling.clear();
                jugActual.movimiento = Jugador.Movimiento.saltaAdelante;
                if (!jugActual.saltando)
                    juego.botonPulsado(jugActual.sonidoSalto);
                debeSaltar = true;
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
                fling.clear();
                jugActual.movimiento = Jugador.Movimiento.saltaAtras;
                if (!jugActual.saltando)
                    juego.botonPulsado(jugActual.sonidoSalto);
                debeSaltar = true;
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
        btCambioPersonaje.getLabel().setFontScale(escalado05);
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
                        if (jugador.fixture.getUserData().equals("jugador1"))
                            idActual = 1;
                        else
                            idActual = 2;
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
    }

    private void confirmacion() {
        final Dialog dialogo = new Dialog("", skin);
        dialogo.setModal(true);
        dialogo.setMovable(false);
        dialogo.setResizable(false);

        Label.LabelStyle estilo = new Label.LabelStyle(fuente, Color.BLACK);
        Label texto = new Label(juego.idiomas.get("estasSeguro"), estilo);

        TextButton btSi = new TextButton(juego.idiomas.get("si"), skin);
        btSi.getLabel().setColor(Color.BLACK);
        btSi.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dialogo.hide();
                dialogo.cancel();
                dialogo.remove();
                juego.setScreen(juego.menuInicio);
                return true;
            }

        });

        TextButton btNo = new TextButton(juego.idiomas.get("no"), skin);
        btNo.getLabel().setColor(Color.BLACK);
        btNo.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                dialogo.hide();
                dialogo.cancel();
                dialogo.remove();
                timer = Timer.schedule(new Timer.Task() {
                                           @Override
                                           public void run() {
                                               timer();
                                           }
                                       }
                        , 0, 0.200f
                );
                return true;
            }

        });
        int pad = altoPantalla / 80;
        dialogo.getContentTable().add(texto).pad(pad);

        Table tabla = new Table();
        tabla.add(btSi).pad(pad).width(anchoPantalla / 7 * 2);
        tabla.add(btNo).pad(pad).width(anchoPantalla / 7 * 2);
        dialogo.getButtonTable().add(tabla).center().padBottom(pad);

        dialogo.show(escenario);
        escenario.addActor(dialogo);
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
        suelos = cargaSuelos(juego.selectorMapa.indiceMapa);

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
                    jugador1.indiceAux = 0;
                    if (jugador1.saltando) {
                        jugador1.saltando = false;
                        jugador1.tocaSuelo = true;
                        if (debeSaltar)
                            juego.botonPulsado(jugador1.sonidoSalto);
                    }
                }
                if (hanColisionado(contact, "jugador2", "suelo")) {
                    jugador2.indiceAux = 0;
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
                        jugador1.indiceAux = 0;
                        if (debeSaltar)
                            juego.botonPulsado(jugador1.sonidoSalto);
                    }
                    if (jugador2.saltando) {
                        jugador2.saltando = false;
                        jugador2.tocaSuelo = true;
                        jugador2.indiceAux = 0;
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

        fuente.getData().setScale(escalado04);

        tiempo = tiempoTurno;
        tiempoString = tiempo + "";

        timer = Timer.schedule(new Timer.Task() {
                                   @Override
                                   public void run() {
                                       timer();
                                   }
                               }
                , 0, 0.200f
        );
    }

    private ArrayList<Suelo> cargaSuelos(int indice) {
        ArrayList<Suelo> suelos = new ArrayList<Suelo>();
        boolean relleno = false;
        for (Vector2 punto : juego.mapas[indice].puntos) {
            mapa = juego.mapas[indice].nombre;
            relleno = false;
            if (punto.y > 0)
                relleno = true;
            Suelo suelo = new Suelo(mundo, juego.mapas[indice].cuadradoMapa, punto, juego, anchoPantalla, altoPantalla, relleno);
            suelo.fixture.setFriction(juego.mapas[indice].rozamiento);
            suelos.add(suelo);
            escenario.addActor(suelo);
        }
        return suelos;
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
                nombreJug = "dog";
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

        Texture texturaArma = null; //Para poder cargar un jugador sin arma
        try {
            texturaArma = juego.manager.get(arma, Texture.class);
        } catch (NullPointerException e) {
        }

        TexturasJugador texturas = cargaTexturas(nombreJug);
        Jugador jugador = new Jugador(mundo, texturas.parado, texturas.mov, texturas.salto, texturas.muerto, posicion,
                juego.manager.get(bala, Texture.class),
                texturaArma,
                juego, turno, Jugador.Movimiento.nada, juego.pantallaJuego, tipoBala, cantidadBalasJugador);
        jugador.setVida(selector.personajes[selector.indicePersonaje].getVida());
        jugador.nombre = nombreJug;
        jugador.arma = arma;
        if (jugador.arma != null) {
            jugador.arma = jugador.arma.substring(jugador.arma.indexOf("/") + 1);
            jugador.arma = jugador.arma.substring(0, jugador.arma.indexOf(".png"));
        } else
            jugador.arma = "bomba";
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
                            juego.finalizacionPartida.pantallaAnterior = juego.pantallaJuego;
                            juego.finalizacionPartida.ganador = idGanador;
                            juego.finalizacionPartida.setTiempo(segundosPartida);
                            juego.finalizacionPartida.balas = balasUtilizadas;
                            juego.finalizacionPartida.jugadores = jugadores;
                            juego.finalizacionPartida.mapa = mapa;
                            if (jugadorActual.equals("jugador1"))
                                juego.finalizacionPartida.framesGanador = jugador1.frameParadoAv;
                            else
                                juego.finalizacionPartida.framesGanador = jugador2.frameParadoAv;
                            juego.pantallaJuego = new PantallaJuego(juego);
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
            timer.cancel();
            confirmacion();
        }

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
        if (juego.debug)
            renderer.render(mundo, camera.combined);
        escenario.draw();

        for (int i = balas.size() - 1; i >= 0; i--) {
            if (balas.get(i).impacto) { //Se elimina despues del mundo step para evitar error al renderizar la bala a null
                balas.get(i).elimina();
                balas.get(i).remove();
                balas.remove(i);
            }
        }

        batchTexto.begin();
        fuente.draw(batchTexto, juego.idiomas.get("jugador") + ": " + idActual, anchoPantalla / 3, altoPantalla - altoPantalla / 10);
        fuente.draw(batchTexto, tiempoString + "s", anchoPantalla / 3 * 2, altoPantalla - altoPantalla / 10);
        fuente.draw(batchTexto, jugador1.getVida() + "", anchoPantalla / 18, altoPantalla - altoPantalla / 10);
        fuente.draw(batchTexto, jugador2.getVida() + "", anchoPantalla - anchoPantalla / 12, altoPantalla - altoPantalla / 10);
        fuente.draw(batchTexto, juego.idiomas.get("balasRestantes") + ": " + jugActual.balasRestantes, anchoPantalla / 18, altoPantalla - altoPantalla / 10 * 2);
        batchTexto.end();
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
