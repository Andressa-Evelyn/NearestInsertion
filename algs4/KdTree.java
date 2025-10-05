package algs4;

/**
 * Estrutura KdTree a ser implementada pelo aluno.
 */
public class KdTree {

    private static class Node {
        Point2D p;        // ponto armazenado
        RectHV rect;      // região correspondente
        Node left, right; // subárvores
        boolean vertical; // nível de divisão (true = vertical, false = horizontal)

        Node(Point2D p, RectHV rect, boolean vertical) {
            this.p = p;
            this.rect = rect;
            this.vertical = vertical;
        }
    }

    private Node root;
    private int size;


    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        throw new UnsupportedOperationException("implementar size()");
    }

    //insere um ponto na árvore
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("p é nulo");
        root = insert(root, p, true, 0, 0, 1, 1);
    }

    //Insere ponto com alternância entre divisão vertical e horizontal
    private Node insert(Node node, Point2D p, boolean vertical, double xmin, double ymin, double xmax, double ymax) {
        if (node == null) {
            size++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax), vertical);
        }

        if (node.p.equals(p)) return node; // já existe

        if (vertical) {
            if (p.x() < node.p.x())
                node.left = insert(node.left, p, !vertical, xmin, ymin, node.p.x(), ymax);
            else
                node.right = insert(node.right, p, !vertical, node.p.x(), ymin, xmax, ymax);
        } else {
            if (p.y() < node.p.y())
                node.left = insert(node.left, p, !vertical, xmin, ymin, xmax, node.p.y());
            else
                node.right = insert(node.right, p, !vertical, xmin, node.p.y(), xmax, ymax);
        }

        return node;
    }

    //verifica se já existe
    private boolean contains(Node node, Point2D p) {
        if (node == null) return false;
        if (node.p.equals(p)) return true;

        if (node.vertical) {
            if (p.x() < node.p.x()) return contains(node.left, p);
            else return contains(node.right, p);
        } else {
            if (p.y() < node.p.y()) return contains(node.left, p);
            else return contains(node.right, p);
        }
    }

    //retorna o ponto mais próximo do ponto consultado
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("p é nulo");
        if (root == null) return null;
        return nearest(root, p, root.p, Double.POSITIVE_INFINITY);
    }

    //Retorna o ponto mais próximo (usa poda por região retangular)
    private Point2D nearest(Node node, Point2D query, Point2D best, double bestDist) {
        if (node == null) return best;

        double d = query.distanceSquaredTo(node.p);
        if (d < bestDist) {
            best = node.p;
            bestDist = d;
        }

        Node first, second;
        if ((node.vertical && query.x() < node.p.x()) || (!node.vertical && query.y() < node.p.y())) {
            first = node.left;
            second = node.right;
        } else {
            first = node.right;
            second = node.left;
        }

        best = nearest(first, query, best, bestDist);
        bestDist = query.distanceSquaredTo(best);

        if (second != null && node.rect.distanceSquaredTo(query) < bestDist) {
            best = nearest(second, query, best, bestDist);
        }

        return best;
    }
}
