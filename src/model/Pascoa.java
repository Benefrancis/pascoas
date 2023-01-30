package model;

import java.time.LocalDate;
import java.time.Month;

public abstract class Pascoa {

	public static LocalDate of(int year) {

		int numeroDeOuro = (year % 19) + 1;

		int seculo = year / 100 + 1;

		int umQuartoDoSeculo = (3 * seculo / 4) - 12;

		int lua = (8 * seculo + 5) / 25 - 5;

		int domingo = (5 * year / 4) - umQuartoDoSeculo - 10;

		int epacto = (11 * numeroDeOuro + 20 + lua - umQuartoDoSeculo) % 30;

		if ((epacto == 25 && numeroDeOuro > 11) || epacto == 24) {
			epacto++;
		}

		int LuaCheia = 44 - epacto;

		if (LuaCheia < 21) {
			LuaCheia += 30;
		}

		int domingoDePascoa = LuaCheia + 7 - ((domingo + LuaCheia) % 7);

		Month mesDaPascoa = Month.MARCH;

		if (domingoDePascoa > 31) {
			mesDaPascoa = Month.APRIL;
			domingoDePascoa -= 31;
		}

		return LocalDate.of(year, mesDaPascoa, domingoDePascoa);
	}

}
