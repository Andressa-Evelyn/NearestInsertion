package algs4;

import java.awt.*;

/**
 * Implementação simples de uma árvore k-d (2D) compatível com a classe algs4.Point.
 *
 * Permite inserir pontos e buscar o mais próximo (nearest neighbor).
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


    public void insert(Point p) {
        if (p == null) throw new IllegalArgumentException("ponto nulo");
        root = insert(root, p, true);
    }

    private Node insert(Node node, Point p, boolean vertical) {
        if (node == null) {
            size++;
            return new Node(p, vertical);
        }

        if (node.p.x() == p.x() && node.p.y() == p.y()) {
            return node; // ignora duplicata
        }

        if (compare(p, node.p, node.vertical) < 0)
            node.left = insert(node.left, p, !node.vertical);
        else
            node.right = insert(node.right, p, !node.vertical);

        return node;
    }


    public Point nearest(Point query) {
        if (query == null) throw new IllegalArgumentException("ponto nulo");
        if (root == null) return null;
        return nearest(root, query, root.p, query.distanceTo(root.p));
    }

    private Point nearest(Node node, Point query, Point best, double bestDist) {
        if (node == null) return best;

        double d = query.distanceTo(node.p);
        if (d < bestDist) {
            best = node.p;
            bestDist = d;
        }

        Node first, second;
        if (compare(query, node.p, node.vertical) < 0) {
            first = node.left;
            second = node.right;
        } else {
            first = node.right;
            second = node.left;
        }

        best = nearest(first, query, best, bestDist);
        bestDist = query.distanceTo(best);

        double delta = (node.vertical ? query.x() - node.p.x() : query.y() - node.p.y());
        if (delta * delta < bestDist)
            best = nearest(second, query, best, bestDist);

        return best;
    }


    private int compare(Point a, Point b, boolean vertical) {
        if (vertical)
            return Double.compare(a.x(), b.x());
        else
            return Double.compare(a.y(), b.y());
    }
}
