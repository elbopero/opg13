import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        /*
        JDBCOps operations = new JDBCOps();
        Program program = new Program();
        // program.dummyMethod();
        File file = new File("shapes.txt");
        //read.readFile();
        //readShape.readFile();
        Circle circle = new Circle(5);

        program.shapeMenu();

        // ArrayList<Shape> circles = operations.getAllShapes();
        ArrayList<Shape> shapes = operations.getShapes();
        for (Shape shape : shapes) {
            System.out.println(shape);
        }

*/
        ReadShape rs = new ReadShape();
        rs.readFile();
        rs.writeToFile();

        JDBCOps d = new JDBCOps();
        for(Shape shape : d.getAllShapes()) {
            System.out.println("Shape from db: " + shape);
        }
    }

    public static void init(JDBCOps ops) {
        ArrayList<Shape> shapes = ops.getAllShapes();
    }


}

