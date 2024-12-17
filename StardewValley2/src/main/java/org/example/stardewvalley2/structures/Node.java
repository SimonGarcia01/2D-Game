package org.example.stardewvalley2.structures;

public class Node<T extends Comparable<T>> {
    private T value;
    private int height;
    private Node leftChild;
    private Node rightChild;

    public Node(T value) {
        this.value = value;
        this.height = 1;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Node<T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node<T> leftChild) {
        this.leftChild = leftChild;
    }

    public Node<T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node<T> rightChild) {
        this.rightChild = rightChild;
    }
}


