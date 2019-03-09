package com.peleadegallos;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Json;


/**
 * La clase que se encarga de la gestion de las pantallas y de la carga de recursos
 */
public class JuegoPrincipal extends Game {
    /**
     * La pantalla Menu inicio.
     */
    MenuInicio menuInicio;
    /**
     * La pantalla Opciones.
     */
    Opciones opciones;
    /**
     * La pantalla Info.
     */
    AcercaDe info;
    /**
     * La pantalla Pantalla juego.
     */
    PantallaJuego pantallaJuego;
    /**
     * La pantalla Finalizacion partida.
     */
    FinalizacionPartida finalizacionPartida;
    /**
     * La pantalla Selector personaje arma.
     */
    SelectorPersonajeArma selectorPersonajeArma;
    /**
     * La pantalla Selector personaje arma 2.
     */
    SelectorPersonajeArma selectorPersonajeArma2;
    /**
     * La pantalla Selector mapa.
     */
    SelectorMapa selectorMapa;
    /**
     * La pantalla Logros.
     */
    PantallaLogros logros;
    /**
     * La pantalla de ayuda
     */
    PantallaAyuda ayuda;
    /**
     * Los Datos guardados.
     */
    ObjetoGuardado datosGuardados = new ObjetoGuardado();

    /**
     * Los Animales que hay que cargar
     */
    String[] animales = new String[]{"dino", "dog"};
    /**
     * Las Acciones de cada animal
     */
    String[] acciones = new String[]{"Idle", "Walk", "Jump", "Dead"};
    /**
     * La cantiad de acciones que tiene cada accion
     */
    int[] cantidadAcciones = new int[]{10, 10, 12, 8};
    /**
     * Los Mapas.
     */
    Mapa[] mapas = new Mapa[2];

    /**
     * Donde se gestionan los Idiomas.
     */
    I18NBundle idiomas;

    /**
     * Manager de carga de los recursos
     */
    AssetManager manager;
    /**
     * Si la Musica encendida.
     */
    public boolean musicaEncendida;
    /**
     * Si la Vibracion encendida.
     */
    public boolean vibracionEncendida;
    /**
     * El Tiempo que vibra
     */
    public int tiempoVibrar = 50;
    /**
     * The Debug.
     */
    public boolean debug = true;
    /**
     * Si esta activado el Modo desarrollo.
     */
    public boolean modoDesarrollo;
    /**
     * Equivalencia Pixel metro x.
     */
    float PIXEL_METRO_X;  //Escala para box2s
    /**
     * Equivalencia Pixel metro y.
     */
    float PIXEL_METRO_Y;  //Escala para box2s
    /**
     * Los Metros x del juego
     */
    float metrosX = 16;
    /**
     * Los Metros y del juego
     */
    float metrosY = 9;

    /**
     * Vibra el dispositivo un tiempo establecido y suena el sonido pasado como parametro
     *
     * @param sonido el sonido que sonará
     */
    public void botonPulsado(Sound sonido) {
        if (vibracionEncendida)
            Gdx.input.vibrate(tiempoVibrar);
        if (musicaEncendida && sonido != null) {
            sonido.play();
        }
    }

    /**
     * Vibra el dispositivo un tiempo establecido y suena la musica pasada como parametro
     * Se utiliza para que los pasos se escuchen de forma continuada
     *
     * @param musica the musica
     */
    public void botonPulsadoMusica(Music musica) {
        if (vibracionEncendida)
            Gdx.input.vibrate(tiempoVibrar);
        if (musicaEncendida) {
            musica.setLooping(true);
            musica.play();
        }
    }

    /**
     * Las shared Preferences.
     */
    Preferences preferences;
    /**
     * El Adaptador para codigo android.
     */
    AdaptadorCodigoAndroid adaptadorCodigoAndroid;

    /**
     * Establece adaptador notificaciones.
     *
     * @param handler el handler
     */
    public void setAdaptadorNotificaciones(AdaptadorCodigoAndroid handler) {
        this.adaptadorCodigoAndroid = handler;
    }

    @Override
    public void create() {
        preferences = Gdx.app.getPreferences("PeleaDeGallos");
        musicaEncendida = preferences.getBoolean("musica", true);   //Intenta cargarlas y si no las encuentra las pone a true
        vibracionEncendida = preferences.getBoolean("vibracion", true);
        modoDesarrollo = preferences.getBoolean("desarrollo", true);

        cargaDatos();   //Carga las partidas

        idiomas = I18NBundle.createBundle(Gdx.files.internal("locale/locale")); //Gestion para multilenguaje

        manager = new AssetManager();                         //Carga las imagenes y audio

        manager.load("suelo.png", Texture.class);    //Imagen del suelo

        manager.load("nubes/nube1.png", Texture.class);    //Imagen nube
        manager.load("nubes/cloud_PNG2.png", Texture.class);    //Imagen nube
        manager.load("nubes/cloud_PNG14.png", Texture.class);    //Imagen nube
        manager.load("nubes/cloud_PNG18.png", Texture.class);    //Imagen nube
        manager.load("nubes/cloud_PNG27.png", Texture.class);    //Imagen nube

        manager.load("iconos/home.png", Texture.class);         //Iconos
        manager.load("iconos/bell.png", Texture.class);
        manager.load("iconos/bell-slash.png", Texture.class);
        manager.load("iconos/info-circle.png", Texture.class);
        manager.load("iconos/volume-mute.png", Texture.class);
        manager.load("iconos/volume-up.png", Texture.class);
        manager.load("iconos/flechaArriba.png", Texture.class);
        manager.load("iconos/flechaAbajo.png", Texture.class);
        manager.load("iconos/flechaDerecha.png", Texture.class);
        manager.load("iconos/flechaIzquierda.png", Texture.class);
        manager.load("iconos/disparo.png", Texture.class);
        manager.load("iconos/developer.png", Texture.class);
        manager.load("iconos/developerNo.png", Texture.class);

        for (int i = 0; i < animales.length; i++) {                         //Carga todas las texturas para los
            for (int j = 0; j < acciones.length; j++) {                     //distintos animales y sus aciones
                for (int k = 1; k <= cantidadAcciones[j]; k++) {
                    manager.load(animales[i] + "/" + acciones[j] + " (" + k + ").png", Texture.class);
                }
            }
        }

        manager.load("tutorial/tutorial1.png", Texture.class); //Imagenes para tutorial
        manager.load("tutorial/tutorial2.png", Texture.class);
        manager.load("tutorial/tutorial3.png", Texture.class);
        manager.load("tutorial/tutorial4.png", Texture.class);
        manager.load("tutorial/tutorial5.png", Texture.class);
        manager.load("tutorial/tutorial6.png", Texture.class);
        manager.load("tutorial/tutorial7.png", Texture.class);
        manager.load("tutorial/tutorial8.png", Texture.class);

        manager.load("Music.wav", Music.class);                  //Musica y sonidos
        manager.load("sonidos/sonidoClick.mp3", Sound.class);
        manager.load("sonidos/sonidoCanon.mp3", Sound.class);
        manager.load("sonidos/sonidoSalto.mp3", Sound.class);
        manager.load("sonidos/sonidoEscopeta.mp3", Sound.class);
        manager.load("sonidos/sonidoAndar.mp3", Music.class);

        manager.load("armas/bullet.png", Texture.class);    //Texturas de las armas
        manager.load("armas/gun.png", Texture.class);
        manager.load("armas/shotgun.png", Texture.class);
        manager.load("armas/bomb.png", Texture.class);

        manager.load("mapas/mapa1.png", Texture.class);         //Texturas de los mapas
        manager.load("mapas/mapa2.png", Texture.class);
        manager.load("mapas/mapaCuadrado1.png", Texture.class);
        manager.load("mapas/mapaCuadrado2.png", Texture.class);

        manager.load("skin/fuente200.fnt", BitmapFont.class);    //Fuente

        manager.finishLoading();                             //Espera a que acabe de cargar

        Personaje[] personajes = new Personaje[animales.length];    //Carga los personajes con sus texturas y sus propiedades
        for (int i = 0; i < personajes.length; i++) {
            personajes[i] = new Personaje(manager.get(animales[i] + "/Idle (1).png", Texture.class), 100, 2, animales[i]);
            if (animales[i].equals("dog")) {
                personajes[i].setVida(80);
                personajes[i].setVelocidad(3);
            }
        }

        Arma[] armas = new Arma[3];                 //Carga las armas con sus propiedades
        armas[0] = new Arma(manager.get("armas/gun.png", Texture.class), 10, 4, 3, "pistola");
        armas[1] = new Arma(manager.get("armas/shotgun.png", Texture.class), 10, 6, 2, "uzi");
        armas[2] = new Arma(manager.get("armas/bomb.png", Texture.class), 2, 1, 10, "canon");

        mapas[0] = new Mapa();                      //Carga los mapas con sus texturas, rozamiento, nombre y puntos
        mapas[0].mapa = manager.get("mapas/mapa1.png", Texture.class);
        mapas[0].cuadradoMapa = manager.get("mapas/mapaCuadrado1.png", Texture.class);
        mapas[0].puntos[0] = new Vector2(0, 0);
        mapas[0].puntos[1] = new Vector2(2, 0);
        mapas[0].puntos[2] = new Vector2(4, 0.5f);
        mapas[0].puntos[3] = new Vector2(6, 0.75f);
        mapas[0].puntos[4] = new Vector2(8, 0.75f);
        mapas[0].puntos[5] = new Vector2(10, 0.5f);
        mapas[0].puntos[6] = new Vector2(12, 0);
        mapas[0].puntos[7] = new Vector2(14, 0);
        mapas[0].rozamiento = 0.7f;
        mapas[0].nombre = "tierra";

        mapas[1] = new Mapa();
        mapas[1].mapa = manager.get("mapas/mapa2.png", Texture.class);
        mapas[1].cuadradoMapa = manager.get("mapas/mapaCuadrado2.png", Texture.class);
        mapas[1].puntos[0] = new Vector2(0, 0);
        mapas[1].puntos[1] = new Vector2(2, 0.65f);
        mapas[1].puntos[2] = new Vector2(4, 0);
        mapas[1].puntos[3] = new Vector2(6, -0.5f);
        mapas[1].puntos[4] = new Vector2(8, -0.5f);
        mapas[1].puntos[5] = new Vector2(10, 0f);
        mapas[1].puntos[6] = new Vector2(12, 0.65f);
        mapas[1].puntos[7] = new Vector2(14, 0);
        mapas[1].rozamiento = 0.5f;
        mapas[1].nombre = "nieve";

        menuInicio = new MenuInicio(this);      //Crea las diferentes pantallas
        opciones = new Opciones(this);
        info = new AcercaDe(this);
        pantallaJuego = new PantallaJuego(this);
        finalizacionPartida = new FinalizacionPartida(this);
        selectorPersonajeArma = new SelectorPersonajeArma(this, personajes, armas, 1);
        selectorPersonajeArma2 = new SelectorPersonajeArma(this, personajes, armas, 2);
        selectorMapa = new SelectorMapa(this, mapas, 3);
        ayuda = new PantallaAyuda(this);

        setScreen(menuInicio);                             //Cambia al menu de inicio
    }

    /**
     * Carga los datos de las partidas
     */
    private void cargaDatos() {
        try {
            if (Gdx.files.local("records.json").exists()) {
                FileHandle in = Gdx.files.local("records.json");
                Json json = new Json();
                datosGuardados = json.fromJson(ObjetoGuardado.class, in.readString());
            } else
                datosGuardados = new ObjetoGuardado();
        } catch (RuntimeException e) {
        }
    }

    /**
     * Guarda los datos de las partidas
     */
    public void guardaDatos() {
        try {
            Json json = new Json();
            FileHandle out = Gdx.files.local("records.json");
            out.writeString(json.toJson(datosGuardados), false);
            notificacion();
        } catch (RuntimeException e) {
        }
    }

    /**
     * Borra los datos de las partidas
     */
    public void borraDatos() {
        if (Gdx.files.local("records.json").exists()) {
            try {
                Gdx.files.local("records.json").delete();
                cargaDatos();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Envia una notificacion al usuario si see cumple alguna de las condiciones,
     * si se enviaran dos notifciaciones o no hubiese quitado la anterior, la nueva la sustituye
     * En la partida 1 y 10 intenta compartir un mensage en las redes sociales, si se publicase la app
     * en el mensage se incluiria la ruta a la tienda de aplicaciones
     */
    private void notificacion() {
        if (datosGuardados.getBalas() > 80 && datosGuardados.getBalas() < 180)
            adaptadorCodigoAndroid.nuevaNotificacion("Pelea de Gallos", idiomas.get("balas") + ": " + datosGuardados.getBalas());
        if (datosGuardados.getTiempo() > 60 && datosGuardados.getTiempo() < 120)
            adaptadorCodigoAndroid.nuevaNotificacion("Pelea de Gallos", idiomas.get("tiempoTotal") + ": " + String.format("%02d:%02d", datosGuardados.getTiempo() / 60, datosGuardados.getTiempo() % 60));
        if (datosGuardados.getRecords().size() == 1) {
            adaptadorCodigoAndroid.nuevaNotificacion("Pelea de Gallos", idiomas.get("partidas") + ": " + datosGuardados.getRecords().size());
            adaptadorCodigoAndroid.comparteEnRS(idiomas.get("redesSociales"));
        } else {
            if (datosGuardados.getRecords().size() == 10) {
                adaptadorCodigoAndroid.comparteEnRS(idiomas.get("redesSociales"));
                adaptadorCodigoAndroid.nuevaNotificacion("Pelea de Gallos", idiomas.get("partidas") + ": " + datosGuardados.getRecords().size());
            } else {
                if (datosGuardados.getRecords().size() % 100 == 0) {
                    adaptadorCodigoAndroid.nuevaNotificacion("Pelea de Gallos", idiomas.get("partidas") + ": " + datosGuardados.getRecords().size());
                }
            }
        }
    }

    /**
     * Da la vuelta a una textura y la devuelve como Sprite
     *
     * @param imagen la imagen a rotar
     * @return la imagen rotada, esta solo se rota en el eje x
     */
    public Sprite espejo(Texture imagen) {
        Sprite sprite = new Sprite(imagen);
        sprite.flip(true, false);
        return sprite;
    }
}
