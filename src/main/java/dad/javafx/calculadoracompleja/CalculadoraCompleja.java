package dad.javafx.calculadoracompleja;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CalculadoraCompleja extends Application {

	private TextField operando1Text;
	private TextField operando2Text;
	private TextField operando3Text;
	private TextField operando4Text;
	private TextField resultado1Text;
	private TextField resultado2Text;
	private ComboBox<String> operadorCombo;

	private StringProperty operacion = new SimpleStringProperty();

	private Complejo complejo1 = new Complejo();
	private Complejo complejo2 = new Complejo();
	private Complejo resultado = new Complejo();

	public void start(Stage primaryStage) throws Exception {

		operando1Text = new TextField();
		operando1Text.setPrefColumnCount(4);

		operando2Text = new TextField();
		operando2Text.setPrefColumnCount(4);

		operando3Text = new TextField();
		operando3Text.setPrefColumnCount(4);

		operando4Text = new TextField();
		operando4Text.setPrefColumnCount(4);

		resultado1Text = new TextField();
		resultado1Text.setPrefColumnCount(4);
		resultado1Text.setDisable(true);

		resultado2Text = new TextField();
		resultado2Text.setPrefColumnCount(4);
		resultado2Text.setDisable(true);

		operadorCombo = new ComboBox<String>();
		operadorCombo.getItems().addAll("+", "-", "*", "/");

		HBox operadores1Box = new HBox(5, operando1Text, new Label("+"), operando2Text, new Label("i"));
		operadores1Box.setAlignment(Pos.CENTER);

		HBox operadores2Box = new HBox(5, operando3Text, new Label("+"), operando4Text, new Label("i"));
		operadores2Box.setAlignment(Pos.CENTER);

		HBox resultadoBox = new HBox(5, resultado1Text, new Label("+"), resultado2Text, new Label("i"));
		resultadoBox.setAlignment(Pos.CENTER);

		VBox operacionesBox = new VBox(5, operadores1Box, operadores2Box, new Separator(), resultadoBox);
		operacionesBox.setAlignment(Pos.CENTER);

		VBox comboVBox = new VBox(5, operadorCombo);
		comboVBox.setAlignment(Pos.CENTER);

		HBox root = new HBox(5, comboVBox, operacionesBox);
		root.setAlignment(Pos.CENTER);

		Scene scene = new Scene(root, 320, 200);

		primaryStage.setTitle("Calculadora Compleja");
		primaryStage.setScene(scene);
		primaryStage.show();

		operando1Text.textProperty().bindBidirectional(complejo1.realProperty(), new NumberStringConverter());
		operando2Text.textProperty().bindBidirectional(complejo1.imaginarioProperty(), new NumberStringConverter());
		operando3Text.textProperty().bindBidirectional(complejo2.realProperty(), new NumberStringConverter());
		operando4Text.textProperty().bindBidirectional(complejo2.imaginarioProperty(), new NumberStringConverter());
		resultado1Text.textProperty().bindBidirectional(resultado.realProperty(), new NumberStringConverter());
		resultado2Text.textProperty().bindBidirectional(resultado.imaginarioProperty(), new NumberStringConverter());
		operacion.bind(operadorCombo.getSelectionModel().selectedItemProperty());

		operacion.addListener((o, ov, nv) -> onOperacionChanged(nv));

		operadorCombo.getSelectionModel().selectFirst();

	}

	private Object onOperacionChanged(String nv) {
		switch (nv) {
		case "+":
			resultado.realProperty().bind(complejo1.realProperty().add(complejo2.realProperty()));
			resultado.imaginarioProperty().bind(complejo1.imaginarioProperty().add(complejo2.imaginarioProperty()));
			break;

		case "-":
			resultado.realProperty().bind(complejo1.realProperty().subtract(complejo2.realProperty()));
			resultado.imaginarioProperty()
					.bind(complejo1.imaginarioProperty().subtract(complejo2.imaginarioProperty()));
			break;

		case "*":
			resultado.realProperty().bind((complejo1.realProperty().multiply(complejo2.realProperty()))
					.subtract(complejo1.imaginarioProperty().multiply(complejo2.imaginarioProperty())));
			resultado.imaginarioProperty().bind((complejo1.realProperty().multiply(complejo2.imaginarioProperty()))
					.add((complejo1.imaginarioProperty().multiply(complejo2.realProperty()))));
			;
			break;

		case "/":
			resultado.realProperty()
					.bind(complejo1.realProperty().multiply(complejo2.realProperty())
							.add(complejo1.imaginarioProperty().multiply(complejo2.imaginarioProperty()))
							.divide(complejo2.realProperty().multiply(complejo2.realProperty())
									.add(complejo2.imaginarioProperty().multiply(complejo2.imaginarioProperty()))));
			resultado.imaginarioProperty()
					.bind(complejo1.imaginarioProperty().multiply(complejo2.realProperty())
							.subtract(complejo1.realProperty().multiply(complejo2.imaginarioProperty()))
							.divide(complejo2.realProperty().multiply(complejo2.realProperty())
									.add(complejo2.imaginarioProperty().multiply(complejo2.imaginarioProperty()))));
			;
			break;
		}
		return null;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
