module com.latex.latexjavafx {
    requires javafx.graphics;
    requires jlatexmath;
    requires java.desktop;
    requires javafx.controls;
    requires fxgraphics2d;


    opens com.latex.latexjavafx to javafx.fxml;
    exports com.latex.latexjavafx;
}