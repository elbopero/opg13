

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Program{

    ReadShape readShape = new ReadShape();


    public void dummyMethod(){
        Circle circle = new Circle(2, Color.DARK_GRAY, false, new MovablePoint(0.0, 0.0));
        System.out.println(circle);
    }

    public void shapeMenu() throws FileNotFoundException {
        try {
            Scanner scan = new Scanner(System.in);
            readShape.readFile();

            while (true) {
                displayMenu();
                switch (Integer.parseInt(scan.nextLine())) {
                    case 1:
                        readShape.drawAllShapes();
                        break;
                    case 2:
                        readShape.sumAreaSquare();
                        // testArray();
                        break;
                    case 3:
                        readShape.createNewShapes();
                        break;
                    case 4:
                        readShape.moveFigure();

                        break;
                    case 5:
                     //   scan.close();

                        readShape.writeToFile();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Ugyldig nummer. Tast 1,2 eller 3.");
                }
               // scan.close();
            }
        } catch (Exception e) {
            displayMenu();
        }
    }

    public void displayMenu(){
        System.out.println("\n----Welcome to the Shape Menu----\n" +
                "\nPress 1 to draw all shapes.\n" +
                "Press 2 to get the sum of all areas for Square\n" +
                "Press 3 to add a shape\n" +
                "Press 4 to move a shape\n" +
                "Press 5 to exit the program.\n");
    }


}
