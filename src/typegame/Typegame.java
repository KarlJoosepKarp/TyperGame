package typegame;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Typegame extends Application {

    private static final String [] alphabet = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
    private static final HashMap<StackPane, String> letters = new HashMap<>();
    private static int score = 0;

    public static void main(String[] args) {
        launch(args);
    }
    public StackPane makeCircle(){
        Circle circle = new Circle(100,100,20);
        circle.setFill(Color.YELLOW);
        Random xCoord = new Random();
        Random yCoord = new Random();
        Random r = new Random();
        char c = (char)(r.nextInt(26) + 'a');
        String s = String.valueOf(c);
        Text text = new Text(s);
        text.setBoundsType(TextBoundsType.VISUAL);
        StackPane stack = new StackPane();
        stack.setLayoutX(xCoord.nextInt(450));
        stack.setLayoutY(yCoord.nextInt(450));
        stack.getChildren().addAll(circle,text);
        letters.put(stack,s);

        return stack;
    }
    public StackPane makeScoreBoard(){
        Rectangle rectangle = new Rectangle(100,50);
        rectangle.setFill(Color.LIGHTCYAN);
        Text text = new Text("Score: "+ score);
        text.setBoundsType(TextBoundsType.VISUAL);
        StackPane stack = new StackPane();
        stack.setLayoutX(400);
        stack.setLayoutY(0);
        stack.getChildren().addAll(rectangle,text);
        return stack;
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root,500,500);
        while(letters.size() < 5){
        Node circles = makeCircle();
        Node scoreboard = makeScoreBoard();
        root.getChildren().addAll(circles,scoreboard);
        }
        root.setOnKeyPressed(keyEvent -> {
            if(letters.containsValue(keyEvent.getText())){
                for(Map.Entry<StackPane,String> j:letters.entrySet()){
                    if(j.getValue().toLowerCase().equals(keyEvent.getText())){
                        System.out.println("okei");
                        root.getChildren().remove(j.getKey());
                    }


                }
                letters.values().remove(keyEvent.getText());
                letters.values().remove(keyEvent.getText());
                while(letters.size() < 6){
                    Node circles = makeCircle();
                    root.getChildren().add(circles);
                    score += 1;
                }


            }
            else{
                score -= 1;

            }

            root.getChildren().addAll(makeScoreBoard());
        });
        root.setFocusTraversable(true);
        root.requestFocus();
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
