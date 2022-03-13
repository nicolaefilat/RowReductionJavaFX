package com.latex.latexjavafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

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
        double[][] matrix = {
                {1, 2, 0, 5, 1, 0, 0, 0},
                {0, 5, 5, 9, 0, 1, 0, 0},
                {2, 0, 4, 5, 0, 0, 1, 0},
                {6, 5, 1, 8, 0, 0, 0, 1}
        };
//        double[][] matrix = {
//                {-4, 8, 10, -10},
//                {3, -6, -6, 8},
//                {1, -2, -1, 3},
//        };
        List<Pair<String,String>> latexStrings = new ArrayList<>();

        VBox vbox = new VBox();

        Fraction fraction = Fraction.of(-2);
        System.out.println(fraction.toFracLatex());

        RowReduction.rowReducedEchelon(matrix, (v, text) -> {
            latexStrings.add(new Pair<>(text, Utils.parseMatrixToLatex(v)));
        });

        ScrollPane s1 = new ScrollPane();
        s1.setContent(vbox);
        stage.setScene(new Scene(s1));
        stage.setTitle("FXGraphics2DDemo3.java");
        stage.setHeight(800);
        stage.setWidth(800);
        stage.show();

        Runnable runnable = () -> {
            vbox.getChildren().clear();
            System.out.println(latexStrings.size() + " Size");
            MyCanvas myCanvas = new MyCanvas(Utils.parseMatrixToLatex(matrix));
            HBox hbox = new HBox(myCanvas);
            final double threshold = 20;
            double currentWidth = myCanvas.getWidth();
            for (Pair<String, String> latexString : latexStrings) {
                String text = latexString.getKey();
                String latexMatrix = latexString.getValue();

                List<MyCanvas> canvases = List.of(new MyCanvas(text),new MyCanvas(latexMatrix));
                for(MyCanvas canvas : canvases){
                    currentWidth += canvas.getWidth();
                    if(currentWidth + threshold < stage.getWidth())
                        hbox.getChildren().add(canvas);
                    else {
                        vbox.getChildren().add(hbox);
                        currentWidth = canvas.getWidth();
                        hbox = new HBox(canvas);
                    }
                }

            }
            if(hbox.getChildren().size() > 0)
                vbox.getChildren().add(hbox);
        };

        runnable.run();

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            runnable.run();
        });

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            runnable.run();
        });

    }
}