package franky_BF38;

import java.awt.Image;
import java.awt.Rectangle;

public class Plataforma {
	private int plataforma_X, plataforma_Y, velocidad_X, tipo;
	public Image plat_Imagen;
	private Rectangle r;

	private Jugador jugador = Principal.getJugador();
	private Fondo fondo = Principal.getFondo1();

	/**
	 * Constructor de la classe Plataforma.
	 * @param x Posició x de la plataforma
	 * @param y Posició y de la plataforma
	 * @param tipos Tipus de plataforma
	 */
	public Plataforma(int x, int y, int tipos) {
		plataforma_X = x * 40;
		plataforma_Y = y * 40;
		tipo = tipos;

		r = new Rectangle();

		if (tipo == 5) {
			plat_Imagen = Principal.interior;
		} else if (tipo == 8) {
			plat_Imagen = Principal.superior;
		} else if (tipo == 4) {
			plat_Imagen = Principal.lateral_izquierda;
		} else if (tipo == 6) {
			plat_Imagen = Principal.lateral_derecha;
		} else if (tipo == 2) {
			plat_Imagen = Principal.inferior;
		} else {
			tipo = 0;
		}
	}

	/**
	 * Funció que actualitza el posicionament de les plataformes. També controla les col·lisions laterals/verticals amb el personatge.
	 */
	public void update() {
		velocidad_X = fondo.getVelocidad_X() * 5;
		plataforma_X += velocidad_X;
		r.setBounds(plataforma_X, plataforma_Y, 40, 40);

		if (r.intersects(jugador.ref) && tipo != 0) {
			colisionVertical(jugador.rect, jugador.rect2);
			colisionLateral(jugador.rect3, jugador.rect4, jugador.pie_izquierdo,
					jugador.pie_derecho);
		}
	}

	/**
	 * Funció que comproba col·lisions verticals amb el nivell i el personatge.
	 * @param rtop Rectangle superior
	 * @param rbot Rectangle inferior
	 */
	public void colisionVertical(Rectangle rtop, Rectangle rbot) {
		if (rbot.intersects(r) && tipo == 8) {
			jugador.setSalto(false);
			jugador.setVelocidad_Y(0);
			jugador.setCentro_Y(plataforma_Y - 63);
		}
	}

	/**
	 * Funció que comproba col·lisions laterals amb el nivell i el personatge.
	 * @param rleft Rectangle lateral esquerre
	 * @param rright Rectangle lateral dret
	 * @param leftfoot Rectangle de peu esquerre
	 * @param rightfoot Rectangle de peu dret
	 */
	public void colisionLateral(Rectangle rleft, Rectangle rright,
			Rectangle leftfoot, Rectangle rightfoot) {
		if (tipo != 5 && tipo != 2 && tipo != 0) {
			if (rleft.intersects(r)) {
				jugador.setCentro_X(plataforma_X + 102);
				jugador.setVelocidad_X(0);
			} else if (leftfoot.intersects(r)) {
				jugador.setCentro_X(plataforma_X + 85);
				jugador.setVelocidad_X(0);
			}
			if (rright.intersects(r)) {
				jugador.setCentro_X(plataforma_X - 62);
				jugador.setVelocidad_X(0);
			} else if (rightfoot.intersects(r)) {
				jugador.setCentro_X(plataforma_X - 45);
				jugador.setVelocidad_X(0);
			}
		}
	}

	public int getPlataforma_X() {
		return plataforma_X;
	}

	public int getPlataforma_Y() {
		return plataforma_Y;
	}

	public int getVelocidad_X() {
		return velocidad_X;
	}

	public int getTipo() {
		return tipo;
	}

	public Image getPlat_Imagen() {
		return plat_Imagen;
	}

	public Fondo getFondo() {
		return fondo;
	}

	public void setPlataforma_X(int plataforma_X) {
		this.plataforma_X = plataforma_X;
	}

	public void setPlataforma_Y(int plataforma_Y) {
		this.plataforma_Y = plataforma_Y;
	}

	public void setVelocidad_X(int velocidad_X) {
		this.velocidad_X = velocidad_X;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public void setPlat_Imagen(Image plat_Imagen) {
		this.plat_Imagen = plat_Imagen;
	}

	public void setFondo(Fondo fondo) {
		this.fondo = fondo;
	}

}
