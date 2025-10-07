import algs4.*;

/**
 * Template da classe Tour para a heurística do vizinho mais próximo.
 *
 * Primeira etapa sugerida:
 *  - implemente os métodos de lista encadeada e chame {@link #insertNearestNaive(Point)}.
 * Segunda etapa:
 *  - implemente a classe algs4.KdTree e utilize {@link #insertNearestKd(Point)}
 *    para acelerar a busca do vizinho mais próximo.
 */
public class Tour {

    private static class Node {
        private Point point;
        private Node next;
    }

    private Node start;
    private int count;
    private final boolean useKdTree;
    private KdTree kdTree;

    public Tour() {
        this(false);
    }

    public Tour(boolean useKdTree) {
        this.useKdTree = useKdTree;
        this.start = null;
        this.count = 0;
        if (useKdTree) {
            kdTree = new KdTree();
        }
    }

    public Tour(Point a, Point b, Point c, Point d) {
        this();
        throw new UnsupportedOperationException("Implementar construtor de depuração");
    }

    //retorna o número de pontos no tour
    public int size() {
        return count;
    }

    //soma as distâncias entre todos os pontos na lista circular
    public double length() {
        if (start == null || count == 0)
            return 0.0;

        double total = 0.0;
        Node atual = start;

        do {
            total += atual.point.distanceTo(atual.next.point);
            atual = atual.next;
        } while (atual != start);

        return total;
    }


    public String toString() {
        if (start == null)
            return "(vazio)";

        StringBuilder sb = new StringBuilder();
        Node atual = start;
        do {
            sb.append(atual.point.toString()).append("\n");
            atual = atual.next;
        } while (atual != start);

        return sb.toString();
    }


    public void draw() {
        if (start == null)
            return;

        Node atual = start;
        do {
            // desenha ponto
            atual.point.draw();
            // desenha linha até o próximo ponto
            atual.point.drawTo(atual.next.point);
            atual = atual.next;
        } while (atual != start);
    }

    public void insertNearest(Point p) {
        if (useKdTree) {
            insertNearestKd(p);
        } else {
            insertNearestNaive(p);
        }
    }

    /**
     * Versão ingênua: percorre toda a lista, calcula a distância para cada nó
     * usando {@link Point#distanceTo(Point)} e insere o novo ponto após o vizinho
     * mais próximo encontrado.
     */
    public void insertNearestNaive(Point p) {
        Node novo = new Node();
        novo.point = p;

        // Caso 1: lista vazia → novo nó é o início e aponta para si mesmo
        if (start == null) {
            start = novo;
            novo.next = novo;
            count = 1;
            return;
        }

        // Caso 2: apenas 1 nó → insere após o único elemento
        if (count == 1) {
            start.next = novo;
            novo.next = start;
            count++;
            return;
        }

        // Caso 3: procura o nó mais próximo de p
        Node maisProximo = start;
        Node atual = start;
        double menorDist = atual.point.distanceTo(p);

        do {
            double dist = atual.point.distanceTo(p);
            if (dist < menorDist) {
                menorDist = dist;
                maisProximo = atual;
            }
            atual = atual.next;
        } while (atual != start);

        // Insere novo nó após o mais próximo encontrado
        novo.next = maisProximo.next;
        maisProximo.next = novo;

        count++;

    }

    /**
     * Versão otimizada: utiliza o {@link KdTree} para localizar rapidamente o
     * ponto mais próximo e insere o novo nó na lista. Requer que a classe
     * algs4.KdTree esteja totalmente implementada.
     */
    public void insertNearestKd(Point p) {
        Node novo = new Node();
        novo.point = p;

        // Caso 1: tour vazio
        if (start == null) {
            start = novo;
            novo.next = novo;
            count = 1;
            kdTree.insert(p);
            return;
        }

        // Caso 2: apenas 1 ponto
        if (count == 1) {
            start.next = novo;
            novo.next = start;
            count++;
            kdTree.insert(p);
            return;
        }

        // Caso 3: busca o ponto mais próximo via KdTree
        Point nearest = kdTree.nearest(p);

        // Encontrar o nó correspondente na lista
        Node atual = start;
        Node maisProximo = start;
        do {
            if (atual.point.x() == nearest.x() && atual.point.y() == nearest.y()) {
                maisProximo = atual;
                break;
            }
            atual = atual.next;
        } while (atual != start);

        // Inserir novo nó após o nó mais próximo
        novo.next = maisProximo.next;
        maisProximo.next = novo;
        count++;

        // Inserir também na KdTree
        kdTree.insert(p);
    }


    public static void main(String[] args) {
        // Cria o tour sem usar KdTree (ingênuo)
        Tour tour = new Tour(false);

        // Lê coordenadas do arquivo (via entrada padrão < data/arquivo.txt)
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point p = new Point(x, y);
            tour.insertNearestNaive(p); // força o método ingênuo
        }

        // Mostra resultados
        StdOut.println(tour);
        StdOut.println();
        StdOut.println("Comprimento do ciclo = " + tour.length());
        StdOut.println("Número de pontos = " + tour.size());

        // Desenha o tour (opcional)
        StdDraw.setXscale(0, 600);
        StdDraw.setYscale(0, 600);
        tour.draw();


    }
}
