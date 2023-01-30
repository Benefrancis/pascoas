package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class Grafico extends JFrame {

	private static final long serialVersionUID = 1L;

	private Integer inicio;

	private Integer fim;

	private List<LocalDate> pascoas = new ArrayList<>();

	public List<LocalDate> getPascoas() {
		return pascoas;
	}

	public Grafico(List<LocalDate> pascoas) {
		this.pascoas = pascoas;
	}

	public Integer getInicio() {
		return inicio;
	}

	public void setInicio(Integer inicio) {
		this.inicio = inicio;
	}

	public Integer getFim() {
		return fim;
	}

	public void setFim(Integer fim) {
		this.fim = fim;
	}

	public void initUI(List<LocalDate> pascoas) {

		CategoryDataset dataset = createDataset(pascoas);

		JFreeChart chart = createChart(dataset);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		add(chartPanel);

		pack();
		setTitle("Páscoa");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private CategoryDataset createDataset(List<LocalDate> pascoas) {

		var dataset = new DefaultCategoryDataset();

		pascoas.stream().collect(Collectors.groupingBy(element -> element.getMonthValue())).forEach((a, b) -> {

			String s = Month.of(a).getDisplayName(TextStyle.SHORT, Locale.ROOT);

			b.stream().collect(Collectors.groupingBy(e -> e.getDayOfMonth())).forEach((x, y) -> {
				System.out.println(x + "/" + s + ": " + y.size());
				dataset.setValue(y.size(), "Páscoa", x + "/" + s);
			});

		});

		return dataset;
	}

	private JFreeChart createChart(CategoryDataset dataset) {

		JFreeChart chart = ChartFactory.createBarChart(
				"Nº de ocorrências da Páscoa nas datas possíveis, entre " + this.inicio + " e " + this.fim, "",
				"Ocorrências", dataset, PlotOrientation.VERTICAL, true, true, true);

		chart.setBackgroundPaint(Color.white);

		// altera o tema padrão
		mudarTema(chart);

		return chart;
	}

	private JFreeChart mudarTema(JFreeChart chart) {

		var fontName = "SansSerif";
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setVisible(true);

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setUpperMargin(0.15);
		rangeAxis.setTickUnit(new NumberTickUnit(1));

		CategoryItemRenderer renderer = plot.getRenderer();

		CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator("{2}",
				NumberFormat.getInstance());

		renderer.setDefaultItemLabelGenerator(generator);

		renderer.setDefaultItemLabelGenerator(generator);
		renderer.setDefaultItemLabelFont(new Font(fontName, Font.PLAIN, 12));
		renderer.setDefaultItemLabelsVisible(true);
		renderer.setDefaultPositiveItemLabelPosition(
				new ItemLabelPosition(ItemLabelAnchor.CENTER, TextAnchor.CENTER, TextAnchor.CENTER, -Math.PI / 2));

		StandardChartTheme theme = (StandardChartTheme) org.jfree.chart.StandardChartTheme.createJFreeTheme();

		theme.setTitlePaint(Color.decode("#4572a7"));
		theme.setExtraLargeFont(new Font(fontName, Font.PLAIN, 16)); // title
		theme.setLargeFont(new Font(fontName, Font.BOLD, 15)); // axis-title
		theme.setRegularFont(new Font(fontName, Font.PLAIN, 11));
		theme.setRangeGridlinePaint(Color.decode("#C0C0C0"));
		theme.setPlotBackgroundPaint(Color.white);
		theme.setChartBackgroundPaint(Color.white);
		theme.setGridBandPaint(Color.red);
		theme.setAxisOffset(new RectangleInsets(0, 0, 0, 0));
		theme.setBarPainter(new StandardBarPainter());
		theme.setAxisLabelPaint(Color.decode("#666666"));
		theme.apply(chart);

		chart.getCategoryPlot().setOutlineVisible(true);
		chart.getCategoryPlot().getRangeAxis().setAxisLineVisible(true);
		chart.getCategoryPlot().getRangeAxis().setTickMarksVisible(true);
		chart.getCategoryPlot().setRangeGridlineStroke(new BasicStroke());
		chart.getCategoryPlot().getRangeAxis().setTickLabelPaint(Color.decode("#666666"));
		chart.getCategoryPlot().getDomainAxis().setTickLabelPaint(Color.decode("#666666"));
		chart.setTextAntiAlias(true);
		chart.setAntiAlias(true);
		chart.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.decode("#4572a7"));

		BarRenderer rend = (BarRenderer) chart.getCategoryPlot().getRenderer();
		rend.setShadowVisible(true);
		rend.setShadowXOffset(2);
		rend.setShadowYOffset(0);
		rend.setShadowPaint(Color.decode("#C0C0C0"));
		rend.setMaximumBarWidth(0.1);
		return chart;
	}

}
