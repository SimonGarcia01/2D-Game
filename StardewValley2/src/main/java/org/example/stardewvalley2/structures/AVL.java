package org.example.stardewvalley2.structures;
import java.util.ArrayList;
import java.util.List;

public class AVL<T extends Comparable<T>> {

    private Node<T> root;

    public Node<T> getRoot() {
        return root;
    }

    public AVL(){
        this.root = null;
    }
    private int height(Node<T> node) {
        return (node == null) ? 0 : node.getHeight();
    }

    private Node<T> rightRotate(Node<T> subRoot) {
        Node<T> left = subRoot.getLeftChild();
        Node<T> leftRight = left.getRightChild();

        left.setRightChild(subRoot);
        subRoot.setLeftChild(leftRight);


        subRoot.setHeight(Math.max(height(subRoot.getLeftChild()), height(subRoot.getRightChild())) + 1);
        left.setHeight(Math.max(height(left.getLeftChild()), height(left.getRightChild())) + 1);


        return left;
    }

    private Node<T> leftRotate(Node<T> subRoot) {
        Node<T> right = subRoot.getRightChild();
        Node<T> rightLeft = right.getLeftChild();

        right.setLeftChild(subRoot);
        subRoot.setRightChild(rightLeft);


        subRoot.setHeight(Math.max(height(subRoot.getLeftChild()), height(subRoot.getRightChild())) + 1);
        right.setHeight(Math.max(height(right.getLeftChild()), height(right.getRightChild())) + 1);


        return right;
    }


    private int getBalance(Node<T> node) {
        return (node == null) ? 0 : height(node.getRightChild()) - height(node.getLeftChild());
    }

    public void insert(T value) {
        if (root == null) {
            root = new Node<>(value);
        } else {
            root = insertNode(root, value);
        }
    }

    private Node<T> insertNode(Node<T> node, T value) {
        int comparison = value.compareTo(node.getValue());

        if(comparison == 0){
            return node;
        }

        if (comparison < 0) {
            if (node.getLeftChild() == null) {
                node.setLeftChild(new Node<>(value));
            } else {
                node.setLeftChild(insertNode(node.getLeftChild(), value));
            }
        } else {
            if (node.getRightChild() == null) {
                node.setRightChild(new Node<>(value));
            } else {
                node.setRightChild(insertNode(node.getRightChild(), value));
            }
        }


        node.setHeight(1 + Math.max(height(node.getLeftChild()), height(node.getRightChild())));


        return balance(node);
    }

    private Node<T> balance(Node<T> node) {
        int balance = getBalance(node);


        if (balance < -1 && getBalance(node.getLeftChild()) <= 0) {
            return rightRotate(node);
        }


        if (balance > 1 && getBalance(node.getRightChild()) >= 0) {
            return leftRotate(node);
        }


        if (balance < -1 && getBalance(node.getLeftChild()) > 0) {
            node.setLeftChild(leftRotate(node.getLeftChild()));
            return rightRotate(node);
        }


        if (balance > 1 && getBalance(node.getRightChild()) < 0) {
            node.setRightChild(rightRotate(node.getRightChild()));
            return leftRotate(node);
        }

        return node;
    }

    public Node<T> search(T value) {
        return search(root, value);
    }

    private Node<T> search(Node<T> current, T value) {
        if (current == null) {
            return null;
        }

        int comparison = current.getValue().compareTo(value);

        if (comparison == 0) {
            return current;
        } else if (comparison > 0) {
            return search(current.getLeftChild(), value);
        } else {
            return search(current.getRightChild(), value);
        }
    }

    public void delete(T value) {
        if (root == null) {
            throw new NullPointerException("Nothing to delete");
        } else {
            delete(null, root, value);
        }
    }

    private void delete(Node<T> parent, Node<T> current, T value) {
        if (current == null) {
            throw new NullPointerException("Nothing found to delete");
        }

        if (current.getValue().compareTo(value) == 0) {

            if (current.getLeftChild() == null && current.getRightChild() == null) {
                if (current == root) {
                    root = null;
                } else if (parent.getLeftChild() == current) {
                    parent.setLeftChild(null);
                } else {
                    parent.setRightChild(null);
                }
            } else if (current.getRightChild() == null) {
                replaceParentChildLink(parent, current, current.getLeftChild());
            } else if (current.getLeftChild() == null) {
                replaceParentChildLink(parent, current, current.getRightChild());
            } else {
                Node<T> successor = findMin(current.getRightChild());
                current.setValue(successor.getValue());
                delete(current, current.getRightChild(), successor.getValue());
            }


            balance(current);
        } else if (current.getValue().compareTo(value) > 0) {
            delete(current, current.getLeftChild(), value);
        } else {
            delete(current, current.getRightChild(), value);
        }


        current.setHeight(1 + Math.max(height(current.getLeftChild()), height(current.getRightChild())));
        balance(current);
    }

    private void replaceParentChildLink(Node<T> parent, Node<T> oldChild, Node<T> newChild) {
        if (parent == null) {
            root = newChild;
        } else if (parent.getLeftChild() == oldChild) {
            parent.setLeftChild(newChild);
        } else {
            parent.setRightChild(newChild);
        }
    }

    private Node<T> findMin(Node<T> node) {
        while (node.getLeftChild() != null) {
            node = node.getLeftChild();
        }
        return node;
    }

    public List<T> inorder() {
        List<T> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    private void inorder(Node<T> node, List<T> result) {
        if (node != null) {
            inorder(node.getLeftChild(), result);
            result.add(node.getValue());
            inorder(node.getRightChild(), result);
        }
    }

    public List<T> preorder() {
        List<T> result = new ArrayList<>();
        preorder(root, result);
        return result;
    }

    private void preorder(Node<T> node, List<T> result) {
        if (node != null) {
            result.add(node.getValue());
            preorder(node.getLeftChild(), result);
            preorder(node.getRightChild(), result);
        }
    }

    public List<T> postorder() {
        List<T> result = new ArrayList<>();
        postorder(root, result);
        return result;
    }

    private void postorder(Node<T> node, List<T> result) {
        if (node != null) {
            postorder(node.getLeftChild(), result);
            postorder(node.getRightChild(), result);
            result.add(node.getValue());
        }
    }
}