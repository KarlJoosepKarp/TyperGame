package typegame;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Typegame extends Application {

    private static final HashMap<StackPane, String> letters = new HashMap<>();
    private Pane root = new Pane();
    private String letter;
    private int score;

    public static void main(String[] args) {
        launch(args);
    }

    private Color colorCircle() {
        int red = ThreadLocalRandom.current().nextInt(100, 252);
        int green = ThreadLocalRandom.current().nextInt(100, 252);
        int blue = ThreadLocalRandom.current().nextInt(100, 252);
        return Color.rgb(red, green, blue);
    }


    private StackPane makeCircle() {
        Circle circle = new Circle(100, 100, 35);
        circle.setFill(colorCircle());
        Random xCoord = new Random();
        Random yCoord = new Random();
        Random number = new Random();
        char c = (char) (number.nextInt(26) + 'a');
        letter = String.valueOf(c);
        Text text = new Text(letter);
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setFont(Font.font("Impact", 20));
        StackPane stack = new StackPane();
        stack.setLayoutX(xCoord.nextInt(450));
        stack.setLayoutY(yCoord.nextInt(450));
        stack.getChildren().addAll(circle, text);
        return stack;
    }

    private void animation(StackPane pane) {
        FadeTransition circleAnimation = new FadeTransition(Duration.millis(1000), pane);
        circleAnimation.setFromValue(1.0);
        circleAnimation.setToValue(0.0);
        circleAnimation.setAutoReverse(true);
        Animation.Status animation = circleAnimation.getStatus();
        if (animation == Animation.Status.RUNNING) {
            root.getChildren().remove(pane);
            return;
        }
        if (animation == Animation.Status.STOPPED) {
            circleAnimation.play();
        }
    }
    /*private StackPane makeScoreBoard() {
        Rectangle rectangle = new Rectangle(100, 50);
        rectangle.setFill(Color.WHITE);
        Text text = new Text("Score: " + score);
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setFont(Font.font("Impact", 20));
        StackPane stack = new StackPane();
        stack.setLayoutX(500);
        stack.setLayoutY(0);
        stack.getChildren().addAll(rectangle, text);
        return stack;
    }
*/

    private void checkPosistsion(StackPane circles) {
        boolean collisionDetected = false;
        for (Map.Entry<StackPane, String> j : letters.entrySet()) {
            if (circles.getBoundsInParent().intersects(j.getKey().getBoundsInParent())) {
                collisionDetected = true;
            }
        }
        if (collisionDetected) {
            return;
        } else {
            letters.put(circles, letter);
            root.getChildren().add(circles);
        }
    }

    private void makeFirstCircles() {
        while (letters.size() < 5) {
            StackPane circles = makeCircle();
            checkPosistsion(circles);
            root.getChildren().addAll(makeScoreBoard());
        }
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(root, 600, 600);
        makeFirstCircles();
        root.setOnKeyPressed(keyEvent -> {
                    if (letters.containsValue(keyEvent.getText())) {
                        for (Map.Entry<StackPane, String> j : letters.entrySet()) {
                            if (j.getValue().toLowerCase().equals(keyEvent.getText())) {
                                letters.remove(j.getKey());
                                animation(j.getKey());
                                break;
                            }
                        }
                        while (letters.size() < 5) {
                            StackPane circles = makeCircle();
                            checkPosistsion(circles);
                        }
                        score += 1;
                    } else {
                        score -= 1;
                    }
                root.getChildren().add(makeScoreBoard());
                }
        );
        root.setFocusTraversable(true);
        root.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
