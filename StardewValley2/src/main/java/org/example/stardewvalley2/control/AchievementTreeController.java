package org.example.stardewvalley2.control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.example.stardewvalley2.model.Achievement;
import org.example.stardewvalley2.model.Avatar;
import org.example.stardewvalley2.structures.AVL;
import org.example.stardewvalley2.structures.Node;

public class AchievementTreeController {

    private static final double NODE_RADIUS = 35;
    private static final double VERTICAL_SPACING = 60;
    private static final double HORIZONTAL_SPACING = 200;

    @FXML
    private Button closeButton;

    @FXML
    private AnchorPane mainPane;

    private AVL<Achievement> achievements;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane treePane;

    @FXML
    public void initialize() {


        achievements = Avatar.getAchievements();
        if (achievements != null && achievements.getRoot() != null) {
            renderTree();
        }

    }

    private void renderTree() {

        treePane.getChildren().clear();
        int depth = calculateDepth(achievements.getRoot());
        double width = Math.pow(2, depth) * HORIZONTAL_SPACING;
        double height = depth * VERTICAL_SPACING + 100;

        treePane.setPrefWidth(width);
        treePane.setPrefHeight(height);


        displayTree(achievements.getRoot(), treePane, width / 2, NODE_RADIUS * 2, width / 4);
    }

    private void displayTree(Node<Achievement> node, Pane pane, double x, double y, double horizontalSpacing) {
        if (node == null) {
            return;
        }

        if (node.getLeftChild() != null) {
            double childX = x - horizontalSpacing;
            double childY = y + VERTICAL_SPACING;
            Line line = new Line(x, y, childX, childY);
            pane.getChildren().add(line);
            displayTree(node.getLeftChild(), pane, childX, childY, horizontalSpacing / 2);
        }


        if (node.getRightChild() != null) {
            double childX = x + horizontalSpacing;
            double childY = y + VERTICAL_SPACING;
            Line line = new Line(x, y, childX, childY);
            pane.getChildren().add(line);
            displayTree(node.getRightChild(), pane, childX, childY, horizontalSpacing / 2);
        }

        Circle circle = new Circle(x, y, NODE_RADIUS);
        circle.setFill(Color.LIGHTBLUE);
        circle.setStroke(Color.BLACK);
        pane.getChildren().add(circle);


        Text text = new Text(x - NODE_RADIUS , y , node.getValue().toString());
        pane.getChildren().add(text);
    }

    private int calculateDepth(Node<Achievement> node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(calculateDepth(node.getLeftChild()), calculateDepth(node.getRightChild()));
    }

    @FXML
    public void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
