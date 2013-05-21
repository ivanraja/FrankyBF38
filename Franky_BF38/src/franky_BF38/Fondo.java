package franky_BF38;

public class Fondo {
	private int fondo_X, fondo_Y, velocidad_X; // bgX, bgY, speedX

	/**
	 * Constructor de la classe Fondo
	 * @param x Posició x
	 * @param y Posició y
	 */
	public Fondo(int x, int y) {
		fondo_X = x;
		fondo_Y = y;
		velocidad_X = 0;
	}

	/**
	 * Funció que actualitza el posicionament del fons de pantalla. Sobreposem dos fons de pantalla per emular un <br/>
	 * moviment de fons suau i real.
	 */
	public void update() {
		fondo_X += velocidad_X;
		if (fondo_X <= -2160) { // Si la coordenada X del fons arriba a -2160,
								// tornem a carregar un nou fons afegint 4320
								// (2160px endavant)
			fondo_X += 4320;
		}
	}

	public int getFondo_X() {
		return fondo_X;
	}

	public int getFondo_Y() {
		return fondo_Y;
	}

	public int getVelocidad_X() {
		return velocidad_X;
	}

	public void setFondo_X(int fondo_X) {
		this.fondo_X = fondo_X;
	}

	public void setFondo_Y(int fondo_Y) {
		this.fondo_Y = fondo_Y;
	}

	public void setVelocidad_X(int velocidad_X) {
		this.velocidad_X = velocidad_X;
	}
}
