/* *****************************************************************************
 *  Cliente que executa a heurística do vizinho mais próximo.
 **************************************************************************** */

import algs4.StdOut;
import algs4.StdDraw;
import algs4.In;
import algs4.*;

public class NearestInsertion {

    public static void main(String[] args) {
        // Verifica se o usuário passou o arquivo como argumento
        if (args.length != 1) {
            StdOut.println("Uso: java -cp src NearestInsertion <arquivo.txt>");
            return;
        }

        // Abre o arquivo usando a classe In da biblioteca algs4
        In in = new In(args[0]);

        // Lê dimensões do canvas
        int width = in.readInt();
        int height = in.readInt();
        int border = 20;

        // Configuração do canvas
        StdDraw.setCanvasSize(width, height + border);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(-border, height);
        StdDraw.enableDoubleBuffering();

        // Inicializa o tour
        Tour tour = new Tour();

        // Lê e insere cada ponto no tour
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point p = new Point(x, y);
            tour.insertNearest(p);
        }

        // Desenha o tour
        tour.draw();
        StdDraw.show();

        // Exibe informações
        StdOut.println(tour);
        StdOut.printf("Comprimento do ciclo = %.4f%n", tour.length());
        StdOut.printf("Número de pontos = %d%n", tour.size());
    }
}
