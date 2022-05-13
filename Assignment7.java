
/**
 * Assignment #7 
 * 
 * Name: Rishabh Manne
 * Student ID: 1222007046
 * Lecture: CSE 205 9:05 AM - 9:55 AM MWF
 * Description: The Assignment7 class creates a DisplayCirclePane object and names it rootPane, while the window is named as well,
 * 				 and adjusts it onto the stage with the specific
 * 				details as shown below and ultimately shows the DisplayCirclePane object onto the screen through the stage.show() method
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Assignment7 extends Application
{
    public static final int WINSIZE_X = 400, WINSIZE_Y = 400;
    private final String WINTITLE = "Assignment7: Drawing Circles";

    @Override
    public void start(Stage stage) throws Exception
    {
        DisplayCirclePane rootPane = new DisplayCirclePane();
        rootPane.setPrefSize(WINSIZE_X, WINSIZE_Y);
        Scene scene = new Scene(rootPane, WINSIZE_X, WINSIZE_Y);
        stage.setTitle(WINTITLE);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Technically this is not needed for JavaFX applications. Added just in
     * case.
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
