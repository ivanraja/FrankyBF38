package franky_BF38;

import java.util.Random;

public class Pacifista extends Enemigo {

	/**
	 * Constructor de la classe Pacifista
	 * @param centro_X Posici� centre x
	 * @param centro_Y Posici� centre y
	 */
	public Pacifista(int centro_X, int centro_Y) {
		setCentro_X(centro_X);
		setCentro_Y(centro_Y);
		generarespera();
	}

	int tempsperdisparar;

	/**
	 * Funci� que genera un temps aleatori d'espera per poder disparar.
	 */
	private void generarespera() {
		Random rand;
		rand = new Random();
		try {
			tempsperdisparar = rand.nextInt(200) + 40;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Funci� que permet disparar al pacifista aleat�riament en el temps.
	 */
	public void step() {
		tempsperdisparar--;
		if (tempsperdisparar == 0) {
			this.disparar();
			generarespera();
		}
	}

}
