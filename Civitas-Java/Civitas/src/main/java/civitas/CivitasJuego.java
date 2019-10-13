/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author elena
 * @date 04/10/2019
 */

public class CivitasJuego {
    private int indiceJugador;
    private Tablero tablero;
    private MazoSorpresas mazo;
    private EstadosJuego estado;
    private GestorEstados gestorEstados;
    private ArrayList<Jugador> jugadores;

    public CivitasJuego(ArrayList<String> nombres){
        jugadores = new ArrayList();
        
        for(String n : nombres){
            jugadores.add(new Jugador(n));
        }
        
        gestorEstados.estadoInicial();
        
        indiceJugador = Dado.getInstance().tirar();
        mazo = new MazoSorpresas();
        this.inicializarTablero(mazo);
        this.inicializarMazoSorpresas(tablero);
    }
    
    private void inicializarTablero(MazoSorpresas mazo){
        tablero = new Tablero(5);
        
        tablero.añadeCasilla(new Casilla("Salida"));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Recogidas",
        50, 20, 100, 100, 50)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Tranvia",
        55, 20, 120, 150, 60)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Aeropuerto",
        60, 20, 140, 200, 70)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Sana",
        65, 20, 160, 250, 80)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Ole",
        80, 20, 200, 300, 90)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Mesa",
        85, 20, 220, 350, 100)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Trompeta",
        90, 20, 240, 400, 110)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Maracas",
        95, 20, 260, 450, 120)));
        tablero.añadeCasilla(new Casilla(400, "Impuesto"));//impuesto
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Solida",
        110, 20, 300, 500, 130)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Coche",
        115, 20, 320, 550, 140)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Pienso",
        120, 20, 340, 600, 150)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Tren",
        125, 20, 360, 650, 160)));
        tablero.añadeJuez();//juez
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Mal",
        140, 20, 400, 700, 170)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Guitarra",
        145, 20, 420, 750, 180)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Hora",
        155, 20, 440, 800, 190)));
        tablero.añadeCasilla(new Casilla(new TituloPropiedad("Calle Diamante",
        170, 20, 460, 850, 200)));        
    }
    
    private void inicializarMazoSorpresas(Tablero tablero){
        mazo.alMazo(new Sorpresa(TipoSorpresa.IRCARCEL, tablero));
        mazo.alMazo(new Sorpresa(TipoSorpresa.IRCARCEL, tablero));
        mazo.alMazo(new Sorpresa(TipoSorpresa.IRCASILLA, tablero, 4, "Ve a la casilla 4"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.IRCASILLA, tablero, 12, "Ve a la casiila 12"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PAGARCOBRAR, -500, "Paga a otro jugador"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PAGARCOBRAR, 500, "Recibes dinero *-*"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PORCASAHOTEL, 100, "Pagas por cada casa y hotel"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PORJUGADOR, 50, "Recibes dinero de los demas"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.SALIRCARCEL, mazo));
        mazo.alMazo(new Sorpresa(TipoSorpresa.SALIRCARCEL, mazo));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PORJUGADOR, -50, "Dale dinero a los demás"));
        mazo.alMazo(new Sorpresa(TipoSorpresa.PAGARCOBRAR, -100, "Pagas a la banca"));                           
    }
    
    private void contabilizarPasosPorSalida(Jugador jugadorActual){
        while(tablero.getPorSalida() > 0){
            jugadorActual.pasaPorSalida();
        }
    }
    
    private void pasarTurno(){ indiceJugador = (indiceJugador+1)%jugadores.size(); }
    
    public void siguientePasoCompletado(OperacionesJuego operacion){
        this.estado = gestorEstados.siguienteEstado(jugadores.get(indiceJugador), estado, operacion);
    }
    
    public boolean construirCasa(int ip){ return jugadores.get(indiceJugador).construirCasa(ip); }
    
    public boolean construirHotel(int ip){ return jugadores.get(indiceJugador).construirHotel(ip); }
    
    public boolean vender(int ip){ return jugadores.get(indiceJugador).vender(ip); }
      
    public boolean hipotecar(int ip){ return jugadores.get(indiceJugador).hipotecar(ip); }
    
    public boolean cancelarHipoteca(int ip){ return jugadores.get(indiceJugador).cancelarHipoteca(ip); }
    
    public boolean salirCarcelPagando(){ return jugadores.get(indiceJugador).salirCarcelPagando(); }
    
    public boolean salirCarcelTirando(){ return jugadores.get(indiceJugador).salirCarcelTirando(); }
    
    public boolean finalDelJuego(){ 
        for(Jugador j : jugadores){
            if(j.enBancarrota()) return true;
        }
        return false;
    }
    
    private ArrayList<Jugador> ranking(){ 
        Collections.sort(jugadores);
        return jugadores;
    }
    
    public Casilla getCasillaActual(){ return tablero.getCasilla(jugadores.get(indiceJugador).getNumCasillaActual()); }
    
    public Jugador getJugadorActual(){ return jugadores.get(indiceJugador); }    
    
    public String infoJugadorTexto(){ return jugadores.get(indiceJugador).toString(); }   
        
    private void avanzaJugador(){ throw new UnsupportedOperationException("No implementado"); }      
    public boolean comprar(){ throw new UnsupportedOperationException("No implementado"); }   
    public OperacionesJuego siguientePaso(){ throw new UnsupportedOperationException("No implementado"); }
    
}