package algs4;

import java.awt.*;

/**
 * Implementação simples de uma árvore k-d (2D) compatível com a classe algs4.Point.
 *
 * Permite inserir pontos e buscar o mais próximo (nearest neighbor).
 * Todas as comparações internas usam distância ao quadrado (sem raiz quadrada),
 * assim como no código do João.
 */
public class KdTree {

    // Nó da árvore
    private static class Node {
        Point p;           // ponto armazenado
        Node left, right;  // subárvores
        boolean vertical;  // eixo de comparação (x: true, y: false)

        Node(Point p, boolean vertical) {
            this.p = p;
            this.vertical = vertical;
        }
    }

    private Node root;
    private int size;

    public KdTree() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // Inserção de um ponto
    public void insert(Point p) {
        if (p == null) throw new IllegalArgumentException("ponto nulo");
        root = insert(root, p, true);
    }

    private Node insert(Node node, Point p, boolean vertical) {
        if (node == null) {
            size++;
            return new Node(p, vertical);
        }

        // Ignora duplicatas
        if (node.p.x() == p.x() && node.p.y() == p.y()) {
            return node;
        }

        // Compara conforme o eixo
        if (compare(p, node.p, node.vertical) < 0)
            node.left = insert(node.left, p, !node.vertical);
        else
            node.right = insert(node.right, p, !node.vertical);

        return node;
    }

    // Busca o ponto mais próximo
    public Point nearest(Point query) {
        if (query == null) throw new IllegalArgumentException("ponto nulo");
        if (root == null) return null;
        return nearest(root, query, root.p, distanceSquared(query, root.p));
    }

    private Point nearest(Node node, Point query, Point best, double bestDistSquared) {
        if (node == null) return best;

        double dSquared = distanceSquared(query, node.p);
        if (dSquared < bestDistSquared) {
            best = node.p;
            bestDistSquared = dSquared;
        }

        Node first, second;
        if (compare(query, node.p, node.vertical) < 0) {
            first = node.left;
            second = node.right;
        } else {
            first = node.right;
            second = node.left;
        }

        best = nearest(first, query, best, bestDistSquared);
        bestDistSquared = distanceSquared(query, best);

        double delta = (node.vertical ? query.x() - node.p.x() : query.y() - node.p.y());
        if (delta * delta < bestDistSquared)
            best = nearest(second, query, best, bestDistSquared);

        return best;
    }

    // Compara dois pontos de acordo com o eixo
    private int compare(Point a, Point b, boolean vertical) {
        if (vertical)
            return Double.compare(a.x(), b.x());
        else
            return Double.compare(a.y(), b.y());
    }

    // Calcula a distância ao quadrado entre dois pontos
    private double distanceSquared(Point a, Point b) {
        double dx = a.x() - b.x();
        double dy = a.y() - b.y();
        return dx * dx + dy * dy;
    }
}
