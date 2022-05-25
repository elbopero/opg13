

import java.awt.*;
import java.io.Serializable;
import java.util.Scanner;

public abstract class Shape implements Movable, Serializable {

    private Color color;
    private boolean filled;
    private String form;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    /*
                Let's have only one constructor. We require that all fields are populated through constructor.
                We now have no alternative options when creating a shape object.
             */
    public Shape(Color color, boolean filled){
        this.color=color;
        this.filled=filled;
    }


    public Shape() {

    }
    // Getter for type of shape

    // Getter for color
    public Color getColor() {
        return color;
    }

    // Setter for color
    public void setColor(Color color) {
        this.color = color;
    }

    /*
        Getter for filled.
        Normally, we name getter methods for boolean values is... , not get...
     */
    public boolean isFilled() {
        return filled;
    }

    // Setter for filled
    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    /*
        Look! We have an abstract method. We are hiding implementation details.
        So all subclasses (os Shape) must implement getArea (or be abstract and delegate
        the implementation details to a subclass).
     */
    public abstract double getArea(int i);

    /*
            We override the abstract method getArea in Shape.
            If we do not do this, this class (Rectangle) must be abstract as we are missing
            implementation details for an abstract method defined in class Shape.
        */

    public abstract double getArea();

    // Another abstract method.
    public abstract double getPerimeter();

    /*
        We override a method from the Object class.
        Remember that all classes inherit from the Object class.
        It is normal to override this method as the method
        inherited from Object provides little information.
     */
    public String toString(){
        if(this.isFilled()) {
            return String.format("A Shape with color of %s and filled.",
                    color.toString());  // Using static method "format" in class String.
        }
        return String.format("A Shape with color of %s and not filled.",
                color.toString());
    }

    public static int colorValidate() {
        Scanner scan = new Scanner(System.in);
        int input = scan.nextInt();
        do {
            if (input >= 0 && input <= 255) {
                return input;
            }
            System.out.println("Ugyldig RGB verdi tastet. Må være mellom 0 til 255.");
            input = scan.nextInt();

        }
        while (!(input >= 0 && input <= 255));

            return input;


    } }