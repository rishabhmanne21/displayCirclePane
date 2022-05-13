/**
 * Assignment #7
 *
 * Name: Rishabh Manne
 * Student ID: 1222007046
 * Lecture: CSE 205 9:05 AM - 9:55 AM MWF
 * Description: This class extends the GridPane class and contains a HBox, which has the controls that the user can use in order to draw the circles,
 *              and a canvas beneath where the circles will appear. It also provides the handler classes that will perform event handling procedures whenever
 *              the user does something as a means of creating a circle. It also has other methods that involve a placeholder and manipulating it so that it shows
 *              where a potential circle will be drawn and will disappear and the actual circle will become apparent.
 */         

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DisplayCirclePane extends GridPane
{

	// add instance variables
	private ArrayList<Circle> circleList;
	private ArrayList<Circle> otherList;

	private CanvasPane canvas; // where to draw Circles
	private ComboBox<String> comboBoxColors;					// instance variables that are the components(control panel + canvas) of the GUI, and a circleList that contains
	private Button btnErase, btnUndo;                           // the list of all the circles that will be drawn on the canvas.
	private Color presentColor = Color.BLACK;					// black is the default color of the circles that the user will draw
	
	HBox ctrlPanel;
	private int x1, y1, x2, y2, x3, y3;							// the coordinates of the mouse position that the user controls


	//constructor
	public DisplayCirclePane()
	{
		// Initialize data models
		circleList = new ArrayList<>();
		// step 1: initailize instance variables & set up the layout
		comboBoxColors = new ComboBox();
		comboBoxColors.setPromptText("black");
		comboBoxColors.getItems().addAll("black", "red", "blue", "green", "orange");			//initializing the variables to default variables and creating their respect
		btnErase = new Button("Erase");                                                         // components are being initialized to different colors/prompt texts
		btnUndo = new Button("Undo");

		ctrlPanel = new HBox();							//the HBox, which will hold the control panel, is being initialized here(controls the user can use in this GUI)

		ctrlPanel.getChildren().addAll(btnUndo, btnErase, comboBoxColors);  // adding the components to the HBox, ctrlPanel
	
		// Instantiate
		// Create a pane for drawing circles
		canvas = new CanvasPane();								// creating a new canvas to draw the circles and giving it a white background
		canvas.setStyle("-fx-background-color: white;");


		this.add(ctrlPanel, 0, 0);						// adding the HBox to the GridPane so that it will display on the window
		this.add(canvas,0,3 );                          // adding the canvas right beneath the control panel


		//step 3- register your canvas to listen to mouse events
		canvas.setOnMousePressed(new PointerHandler());
		canvas.setOnMouseDragged(new PointerHandler());			// registering the canvas to the same handler but for three separate actions
		canvas.setOnMouseReleased(new PointerHandler());        // (when user presses, drags, and releases their mouse when drawing the circles

		// Optional adjustments to the layout

		// Resize the canvas automatically
		GridPane.setVgrow(canvas, Priority.ALWAYS);
		GridPane.setHgrow(canvas, Priority.ALWAYS);					// adjusting the layout of the gridPane to fit the window
		// Make the ComboBox of colors to fill the space of the control panel
		comboBoxColors.setMaxWidth(Double.MAX_VALUE);
		GridPane.setHgrow(comboBoxColors, Priority.ALWAYS);
		// Set the preferred size of the control buttons (1/3 the size of the
		// initial window)
		double btnPrefWidth = Assignment7.WINSIZE_X / 3;
		btnUndo.setPrefWidth(btnPrefWidth);
		btnErase.setPrefWidth(btnPrefWidth);			// adjusting the size of the controls so that they span the width of the window
		comboBoxColors.setPrefWidth(btnPrefWidth);

		//step3 : register your buttons and Combobox with its handler objects
		btnUndo.setOnAction(new ButtonHandler());
		btnErase.setOnAction(new ButtonHandler());				// registering the buttons to their respective handlers, which will handle event handling procedures
		comboBoxColors.setOnAction(new ColorComboBoxHandler());
	}

	/**
	 * CanvasPane is the panel where Circles will be drawn on.
	 */
	private class CanvasPane extends Pane
	{   //instance variables
		private Circle placeholder;
		private boolean isPlaceholderOn;


		public CanvasPane()
		{
			//implement the constrctor
			placeholder = new Circle(0,0,0,Color.TRANSPARENT);				// implementing the constructor by creating a default circle with the placeholder, which will temporarily
			placeholder.setStroke(Color.BLACK);                             // have no coordinates, but has a black stroke
		}

		public void drawPlaceHolder(int x, int y, int radius)		// this method draws the placeholder which depicts where and how a particular circle will be drawn on the canvas
		{
			// Change the position of the placeholder
			//write your code here
			placeholder.setCenterX(x);
			placeholder.setCenterY(y);				// setting the center coordinates and radius to the values that will be passed in the parameter
			placeholder.setRadius(radius);
			isPlaceholderOn = false;
			// If this is the first time we draw the placeholder, add it to the canvas
			if (!isPlaceholderOn)
			{
				//write your code here
				canvas.getChildren().add(placeholder);				// if the placeholder isn't already on the canvas, it will be added onto the canvas
				isPlaceholderOn = true;
			}
			
	


		}

		public void draw(int x, int y, int radius) 			// this draw method will actually draw the circles that the user wants to draw onto the canvas
		{
			Circle c1 = new Circle(x,y,radius);
			canvas.getChildren().add(c1);
			c1.setFill(presentColor);			// creating a circle with the attributes that are passed into the parameters and adding it to the canvas and circleList arrayList
			circleList.add(c1);
			repaint();
			
		}
		
		
		public void erasePlaceHolder()			// this method will erase the placeholder from the canvas
		{
			// Simply remove the placeholder Circle from the canvas
			// write your code here
			canvas.getChildren().remove(placeholder);		// method call that removes the placeholder from the canvas completely

		}

		/**
		 * Erase and redraw all Circles in the Circle list (not including the
		 * placeholder)
		 */
		public void repaint()
		{
			// Redraw all circles in the list
			this.getChildren().clear();
			for (Circle c : circleList)				// this will clear the canvas and repaint all the circles that are in the arrayList, aside from the placeholder
			{
				this.getChildren().add(c);
			}

			// Make the control panel always visible
			ctrlPanel.toFront();			// the HBox will always be visible, hence, the toFront() method call
		}
	}

	/**
	 * Step 2: ButtonListener defines actions to take in case the "Undo" or "Erase"
	 * button is clicked
	 */
	private class ButtonHandler implements EventHandler<ActionEvent>   // the buttonHandler class
	{

		@Override
		public void handle(ActionEvent e)
		{
			Object source = e.getSource();			// storing the source of the event in the variable "source"

			// Check if source refers to the Erase button
			if (source == btnErase)
			{
				//write your code here
				canvas.getChildren().clear();			// if the source is from the erase button, the entire canvas is cleared
				otherList = new ArrayList<>(circleList);  // copying the circleList to another arrayList to save the content when it is cleared for "undoing a erase" purpose
				circleList.clear();						// clearing the circleList so that the old circles aren't drawn when user hits erase and wants to start fresh
				
			}
			// Check if source refers to the Undo button
			else if (source == btnUndo)
			{
				// Erase the last Circle in the list
				// write your code here
				if(canvas.getChildren().isEmpty()) 
				{										// if the source is from the undo button, but the erase feature was pressed right before it, the original 
					canvas.repaint();                   // status of the canvas is restored
					circleList = otherList;				// copying the otherList's circles back to the circleList so that the original status of the canvas is restored
				}
				else
				{
					canvas.getChildren().remove(circleList.get(circleList.size()-1));
					circleList.remove(circleList.size()-1);								// the last state of the canvas is restored by simply deleting the last drawn circle
				}





				// Repaint the Canvas
				canvas.repaint();		// the canvas is again repainted without the presence of the last drawn circle(because of the undo button press)
			}
		}

	}

	/**
	 * Step2: A listener class used to set the color chosen by the user via the
	 * ComboBox of Colors.
	 */
	private class ColorComboBoxHandler implements EventHandler<ActionEvent>		// a handler class for the comboBox
	{

		@Override
		public void handle(ActionEvent e)
		{
			//write your code here

			if(comboBoxColors.getValue().equals("red"))
			{
				presentColor = Color.RED;
			}
			else if(comboBoxColors.getValue().equals("green")) 
			{
				presentColor = Color.GREEN;
			}														// if the user selects a particular color, then that color will be stored in a presentColor variable that is applied
			else if(comboBoxColors.getValue().equals("blue"))       // to the attributes of the next circle that the user will draw(look at mouse handler methods below)
			{
				presentColor = Color.BLUE;
			}
			else if(comboBoxColors.getValue().equals("orange")) 
			{
				presentColor = Color.ORANGE;
			}
			else
			{
				presentColor = Color.BLACK;					// if no other color besides black is selected, then black will be the color of the circle
			}

		}

	}

	/**
	 * A listener class that handles any mouse events on the Canvas
	 */
	private class PointerHandler implements EventHandler<MouseEvent>	// a handler class for the user's mouse commands
	{
		// 1=pressed, 2=dragged, 3=released
		
		@Override
		public void handle(MouseEvent e)
		{
			//write your code here

			if(e.getEventType() == MouseEvent.MOUSE_PRESSED) 
			{
				x1 = (int) e.getSceneX();						// if the mouse is pressed, then that's where the center of the circle will be, so the coordinates are obtained
				y1 = (int) e.getSceneY();                       // and assigned to the placeholder
			} 
			else if(e.getEventType() == MouseEvent.MOUSE_DRAGGED) 
			{
				x2 = (int) e.getSceneX();                // If the mouse is dragged, then each time the radius is updated by obtaining the new coordinates and using the helper method below
				y2 = (int) e.getSceneY();                // to calculate the radius. Each time the mouse is dragged the placeholder's radius is updated and drawn on the canvas
				canvas.placeholder.setRadius(getDistance(x1,y1,x2,y2));
				canvas.drawPlaceHolder(x1,y1, (int)canvas.placeholder.getRadius());
				
			}
			else if(e.getEventType() == MouseEvent.MOUSE_RELEASED)
			{
				x3 = (int) e.getSceneX();
				y3 = (int) e.getSceneY();
				canvas.erasePlaceHolder();
				canvas.draw(x1, y1, (int)canvas.placeholder.getRadius());			// once the mouse is released, the guiding placeholder circle is deleted, and a circle is updated with its attributes
			}                                                                       // through the draw method and is shown on the canvas








		}

		/**
		 * A helper method in case you need it. Get the Euclidean distance between (x1,y1) and (x2,y2)
		 */
		private double getDistance(int x1, int y1, int x2, int y2)
		{
			return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
		}

	}//end of class PointerHandler
}//end of DisplayCirclePane class
