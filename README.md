# RowReductionJavaFX
Java UI program that displays row reduction steps with Latex

## Examples 

You can check that this examples work on <a href="https://www.wolframalpha.com/input?i=Reduced+row+echelon+form&assumption=%7B%22F%22%2C+%22ReducedRowEchelon%22%2C+%22theMatrix%22%7D+-%3E%22%7B%7B-4%2C+8%2C+1%2C+-10%7D%2C+++++++++++++++++%7B-2%2C+0%2C+-6%2C+8%7D%2C+++++++++++++++++%7B1%2C+-2%2C+-1%2C+3%7D%7D%22&assumption=%7B%22C%22%2C+%22Reduced+row+echelon+form%22%7D+-%3E+%7B%22Calculator%22%7D"> WolframAlpha </a> 
<img src="https://user-images.githubusercontent.com/35890341/158055128-f8bf13f4-c396-4dfb-869a-6d6d746a1329.png" width = 700/>
<hr/>
<img src="https://user-images.githubusercontent.com/35890341/158055208-ea45ee06-c04e-4788-8b18-1a1064740bc0.png" width = 700/>


## Usage
1. Install the dependencies with Maven
2. In the `Latex.java` class in main change the matrix variable to your own and run the application. 
```java
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
                {-4, 8, 1, -10},
                {-2, 0, -6, 8},
                {1, -2, -1, 3},
        };
        // other code
   }
```
Hope you will find this useful. I tried to make the algorithm use as little fractions as possbile but this does not always work. \
Have a nice day, and enjoy math :smile: !


