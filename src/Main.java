import java.awt.EventQueue;
import java.awt.Frame;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Pascoa;
import view.Grafico;

public class Main {

	public static void main(String[] args) {

		// Fila de eventos
		EventQueue.invokeLater(() -> {

			var inicio = Integer.parseInt(JOptionPane.showInputDialog("Informe o ano inicial: ", 1583));

			var fim = Integer.parseInt(JOptionPane.showInputDialog("Informe o ano final: ", LocalDate.now().getYear()));

			if (inicio > fim)
				throw new RuntimeException("O Ano Final deve ser maior do que o ano inicial");

			List<LocalDate> pascoas = new ArrayList<>();

			var ano = inicio;

			while (ano <= fim) {

				LocalDate pascoa = Pascoa.of(ano);

				System.out.printf("%nPáscoa de %s: %s%n", ano,
						pascoa.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

				pascoas.add(pascoa);

				ano++;
			}

			var ex = new Grafico(pascoas);
			ex.setInicio(inicio);
			ex.setFim(fim);
			ex.initUI(pascoas);
			ex.setVisible(true);
			ex.setLocationRelativeTo(null);
			ex.setExtendedState(Frame.MAXIMIZED_BOTH);

		});
	}
}
