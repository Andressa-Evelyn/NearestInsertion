/* *****************************************************************************
 *  Cliente que executa a heurística do vizinho mais próximo.
 **************************************************************************** */

import algs4.StdIn;
import algs4.StdOut;
import algs4.StdDraw;

public class NearestInsertion {

    public static void main(String[] args) {
        int width = StdIn.readInt();
        int height = StdIn.readInt();
        int border = 20;
        StdDraw.setCanvasSize(width, height + border);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(-border, height);
        StdDraw.enableDoubleBuffering();

        Tour tour = new Tour();
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point p = new Point(x, y);
            tour.insertNearest(p);
        }

        tour.draw();
        StdDraw.show();
        StdOut.println(tour);
        StdOut.printf("Comprimento do ciclo = %.4f", tour.length());
        StdOut.printf("Número de pontos = %d", tour.size());
    }
}
