package franky_BF38;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Enemigo {
	private int velocidad_X, centro_X, centro_Y;
	public int vida = 5;
	private int velocidad_movimiento;
	private Fondo fondo = Principal.getFondo1();
	private Jugador jugador = Principal.getJugador();
	public Rectangle r = new Rectangle(0, 0, 0, 0);

	private ArrayList<Misiles_PX> municion = new ArrayList<Misiles_PX>();

	/**
	 * Funció que actualitza el posicionament del enemic (pacifistas). També incorpora la crida a la funció de seguiment <br/>
	 * i comproba col·lisions amb el jugador.
	 */
	public void update() {
		seguir();
		centro_X += velocidad_X;
		velocidad_X = fondo.getVelocidad_X() * 5 + velocidad_movimiento;
		r.setBounds(centro_X - 25, centro_Y - 25, 50, 60);
		checkCollision();
	}

	/**
	 * Funció que segueix el moviment del jugador, emulant una persecució.
	 */
	public void seguir() {
		if (centro_X < -95 || centro_X > 810) {
			velocidad_movimiento = 0;
		} else if (Math.abs(jugador.getCentro_X() - centro_X) < 5) {
			velocidad_movimiento = 0;
		} else {
			if (jugador.getCentro_X() >= centro_X) {
				velocidad_movimiento = 1;
			} else {
				velocidad_movimiento = -1;
			}
		}
	}

	/**
	 * Funció que permet disparar als pacifistas.
	 */
	public void disparar() {
		Misiles_PX m = new Misiles_PX(centro_X - 50, centro_Y - 15);
		municion.add(m);
	}

	/**
	 * Funció que comproba col·lisions corporals amb el jugador. Si aquest toca a un enemic morirà al instant.
	 */
	private void checkCollision() {
		if (r.intersects(jugador.rect) || r.intersects(jugador.rect2)
				|| r.intersects(jugador.rect3) || r.intersects(jugador.rect4)) {
			Principal.modo = Principal.Estado.Muerto;
		}
	}

	public ArrayList<Misiles_PX> getMisiles_PX() {
		return municion;
	}

	public int getVelocidad_X() {
		return velocidad_X;
	}

	public int getCentro_X() {
		return centro_X;
	}

	public int getCentro_Y() {
		return centro_Y;
	}

	public Fondo getFondo() {
		return fondo;
	}

	public void setVelocidad_X(int velocidad_X) {
		this.velocidad_X = velocidad_X;
	}

	public void setCentro_X(int centro_X) {
		this.centro_X = centro_X;
	}

	public void setCentro_Y(int centro_Y) {
		this.centro_Y = centro_Y;
	}

	public void setFondo(Fondo fondo) {
		this.fondo = fondo;
	}
}
