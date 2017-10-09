package messenger;

import javax.swing.*;

public class ObjectCreator {

    public static final int VBOX = 0;
    public static final int HBOX = 1;

    public static Box getBox(int size, int orientation) {

        Box box;
        switch (orientation) {

            default:
                System.out.println("Invalid orientation given!");

            case ObjectCreator.VBOX:
                box = Box.createVerticalBox();
                box.setSize(box.getWidth(), size);
                break;

            case ObjectCreator.HBOX:
                box = Box.createHorizontalBox();
                box.setSize(size, box.getHeight());
                break;
        }
        return box;
    }

    public static Box getBox(int width, int height, int orientation) {
        Box box = orientation == ObjectCreator.VBOX ? Box.createVerticalBox() : Box.createHorizontalBox();
        box.setSize(width, height);
        return box;
    }

    public static JSeparator getSpacer(int size, int orientation) {

        JSeparator spacer = new JSeparator();
        spacer.setVisible(false);

        switch (orientation) {

            default:
                System.out.println("Invalid orientation given!");

            case ObjectCreator.VBOX:
                spacer.setSize(spacer.getWidth(), size);
                break;

            case ObjectCreator.HBOX:
                spacer.setSize(size, spacer.getHeight());
                break;
        }
        return spacer;
    }

}
