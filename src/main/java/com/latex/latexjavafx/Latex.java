package com.latex.latexjavafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

    private String parseMatrixToLatex(double[][] v) {
        StringBuilder answer = new StringBuilder("\\begin{bmatrix}\n");
        for (double[] doubles : v) {
            for (int j = 0; j < doubles.length; j++) {
                answer.append(doubles[j]);
                if (j + 1 != doubles.length)
                    answer.append(" & ");
            }
            answer.append("\\\\");
        }
        answer.append("\\end{bmatrix}");
        return answer.toString();
    }

    @Override
    public void start(Stage stage) throws Exception {
        loadFonts();
        double[][] matrix = {
                {-4, -5, 20, 1},
                {0, 0, 0, 5},
                {8, 15, -60, -4},
        };
        List<String> latexStrings = new ArrayList<>();
        List<String> information = new ArrayList<>();
        VBox stack = new VBox(new MyCanvas(parseMatrixToLatex(matrix)));

        RowReduction.rowReducedEchelon(matrix, (v, text) -> {
            latexStrings.add(parseMatrixToLatex(v));
            information.add(text);
        });
        for (int i = 0; i < latexStrings.size(); i++) {
            String latex = latexStrings.get(i);
            String text = information.get(i);

            MyCanvas canvas = new MyCanvas(latex);
            Label label = new Label(text);
            label.setFont(new Font(20));
            label.setPadding(new Insets(10, 10, 10, 10));
            stack.getChildren().add(label);
            stack.getChildren().add(canvas);
        }

        ScrollPane s1 = new ScrollPane();
        s1.setPrefSize(800, 800);
        s1.setContent(stack);


        stage.setScene(new Scene(s1));
        stage.setTitle("FXGraphics2DDemo3.java");
        stage.setHeight(800);
        stage.setWidth(800);
        stage.show();

    }
}