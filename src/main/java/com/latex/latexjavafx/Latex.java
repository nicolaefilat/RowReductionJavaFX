package com.latex.latexjavafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * http://stackoverflow.com/questions/25027060/running-swing-application-in-javafx
 */
public class Latex extends Application {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void loadFonts() {
        javafx.scene.text.Font.loadFont(Latex.class.getResourceAsStream("/org/jlm_cmex10.ttf"), 1);
        javafx.scene.text.Font.loadFont(Latex.class.getResourceAsStream("/org/jlm_cmmi10.ttf"), 1);
        javafx.scene.text.Font.loadFont(Latex.class.getResourceAsStream("/org/jlm_cmsy10.ttf"), 1);
        javafx.scene.text.Font.loadFont(Latex.class.getResourceAsStream("/org/jlm_cmr10.ttf"), 1);
    }


    @Override
    public void start(Stage stage) throws Exception {
        loadFonts();
//        double[][] matrix = {
//                {1, 2, 0, 5, 1, 0, 0, 0},
//                {0, 5, 5, 9, 0, 1, 0, 0},
//                {2, 0, 4, 5, 0, 0, 1, 0},
//                {6, 5, 1, 8, 0, 0, 0, 1}
//        };
        double[][] matrix = {
                {1,2,3},
                {331,2,3},
                {12,134,0},
        };
        List<String> latexStrings = new ArrayList<>();
        List<String> information = new ArrayList<>();
        VBox vbox = new VBox(new MyCanvas(Utils.parseMatrixToLatex(matrix)));
        vbox.setPrefWidth(800);

        Fraction fraction = Fraction.of(-2);
        System.out.println(fraction.toFracLatex());

        RowReduction.rowReducedEchelon(matrix, (v, text) -> {
            latexStrings.add(Utils.parseMatrixToLatex(v));
            information.add(text);
        });
        for (int i = 0; i < latexStrings.size(); i++) {
            String latex = latexStrings.get(i);
            String text = information.get(i);

            MyCanvas canvas = new MyCanvas(latex);
            Label label = new Label(text);
            label.setFont(new Font(20));
            label.setPrefWidth(600);
            label.setMinHeight(30);
            label.setPadding(new Insets(10, 10, 10, 10));
            HBox hbox = new HBox(canvas,label);
            hbox.setAlignment(Pos.CENTER);
//            vbox.getChildren().add(label);
            vbox.getChildren().add(hbox);
            Separator sep = new Separator();
            sep.setOrientation(Orientation.HORIZONTAL);

            vbox.getChildren().add(sep);
        }

        ScrollPane s1 = new ScrollPane();
        s1.setContent(vbox);
        stage.setScene(new Scene(s1));
        stage.setTitle("FXGraphics2DDemo3.java");
        stage.setHeight(800);
        stage.setWidth(800);
        stage.show();


        System.out.println(vbox.getWidth());
        System.out.println(s1.getWidth());



    }
}