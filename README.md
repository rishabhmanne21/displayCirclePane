# displayCirclePane
In this project, JavaFX is used to build a GUI that allows the user to draw circles on a window, which presents them with options to change colors and erase/reset the entire pane if they wish. 



Main Class
- This class primarily functions as the setting for the GUI and the actual creation of it for the user to see

DisplayCirclePane Class
- This class provides the main foundational work for the GUI by adding the components
- The main pane is added and has a particular orientation to fit just below the buttons, but still take up a majority of the space
- The buttons are also made functional through the ButtonHandler class, which makes use of the event handling functionality that Java offers
- Helper methods are used to draw the circles, in their particular colors, and erase/reset circles if needed 
- The pointer handler is more technical because it requires the exact coordinates of when an user clicks and drags across the entire pane
- Hence, through the implementation of the EventHandler interface, methods such as .getSceneX() and .getSceneY() can be functional in understanding how big             of a circle the user desires

JavaFX is fundamental to this code and for any project that requires an efficient GUI.
