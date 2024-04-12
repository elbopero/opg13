import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.io.*;
import java.nio.ByteBuffer;

public class JDBCOps {
    public JDBCOps(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public int insertShape(String shape) {
        int dbShape = 0;
        try (Connection con = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/chill", removed, removed)) {
            dbShape = getShapeByName(shape);
            Statement stmt = con.createStatement();
            if (dbShape == 0) {
                String insertSql = "Insert into shape(type)" + " values('" + shape + "')";
                stmt.executeUpdate(insertSql);
                dbShape = getShapeByName(shape);
            }
           // System.out.println(dbShape);


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            // return false;
        }
        return dbShape;

    }

    public boolean insertCircle(Shape circle) {
        try (Connection con = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/chill", "root", blank)) {

            PreparedStatement stmt = con.prepareStatement("insert into circle(x,y,filled,radius,color) VALUES(?,?,?,?,?)");

            if (circle instanceof Circle) {
                Circle rect = (Circle) circle;
                stmt.setDouble(1, rect.getCenter().getX());
                stmt.setDouble(2, rect.getCenter().getY());
                stmt.setBoolean(3, rect.isFilled());
                stmt.setDouble(4, rect.getRadius());
                stmt.setInt(5, rect.getColor().getRGB());

                stmt.execute();
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertRectangle(Shape rectangle) {
        try (Connection con = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/chill", "root", "tuttifrutti")) {

            PreparedStatement stmt = con.prepareStatement("insert into rectangle(x,y,filled,length,width,color) VALUES(?,?,?,?,?,?)");

            if (rectangle instanceof Rectangle) {
                Rectangle rect = (Rectangle) rectangle;
                stmt.setDouble(1, rect.getTopLeft().getX());
                stmt.setDouble(2, rect.getTopLeft().getY());
                stmt.setBoolean(3, rect.isFilled());
                stmt.setDouble(4, rect.getLength());
                stmt.setDouble(5, rect.getWidth());
                int color = rect.getColor().getRGB();
                stmt.setInt(6, color );
                stmt.execute();
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertSquare(Shape square) {
        try (Connection con = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/chill", "root", "tuttifrutti")) {

            PreparedStatement stmt = con.prepareStatement("insert into square(x,y,filled,side,color) VALUES(?,?,?,?,?)");

            if (square instanceof Square) {
                Square rect = (Square) square;
                stmt.setDouble(1, rect.getTopLeft().getX());
                stmt.setDouble(2, rect.getTopLeft().getY());
                stmt.setBoolean(3, rect.isFilled());
                stmt.setDouble(4, rect.getSide());
                stmt.setInt(5, rect.getColor().getRGB());

                stmt.execute();
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
        return true;
    }

   /* public boolean insertCircle(Circle circle){
       // Circle circle = (Circle) new Object();
        try(Connection con = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/chill", "root", "tuttifrutti")) {
            
                Statement stmt = con.createStatement();
                
                String insertSql = "insert into circle(radius, center, shape)" +
                        " values('" + circle.getRadius() + ", " + circle.getCenter() + ", " + circle.getId(1) + "')";
                stmt.executeUpdate(insertSql);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
        return true;
    } */

    public ArrayList<Shape> getAllShapes(){
        ArrayList<Shape> results = new ArrayList<>();

        try(Connection con = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/chill", "root", "tuttifrutti")){

            Statement stmt = con.createStatement();
            String selectSql = "select width,length,x,y,filled,color from Rectangle";
            ResultSet resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next()){
                Rectangle rect = new Rectangle();
                rect.setWidth(resultSet.getDouble(1));
                rect.setLength(resultSet.getDouble(2));
                MovablePoint l = new MovablePoint(resultSet.getDouble(3), resultSet.getDouble(4));
                MovablePoint r = new MovablePoint(resultSet.getDouble(3) + rect.getWidth(),
                        resultSet.getDouble(4) + rect.getLength());
                rect.setTopLeft(l);
                rect.setBottomRight(r);
                rect.setFilled(resultSet.getBoolean(5));
                int color = resultSet.getInt(6);
                Color c = new Color(color);
                rect.setColor(c);
                results.add(rect);
            }
            resultSet.close();


            selectSql = "select side,x,y,filled, color from Square";
            resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next()){
                Square rect = new Square();
                rect.setSide(resultSet.getDouble(1));

                MovablePoint l = new MovablePoint(resultSet.getDouble(2), resultSet.getDouble(3));
                MovablePoint r = new MovablePoint(resultSet.getDouble(2) + rect.getWidth(),
                        resultSet.getDouble(3) + rect.getLength());
                rect.setTopLeft(l);
                rect.setBottomRight(r);
                rect.setFilled(resultSet.getBoolean(4));
                int color = resultSet.getInt(5);
                Color c = new Color(color);
                rect.setColor(c);
                results.add(rect);
            }
            resultSet.close();

            selectSql = "select radius,x,y,filled , color from circle";
            resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next()){
                Circle rect = new Circle();
                rect.setRadius(resultSet.getInt(1));

                MovablePoint l = new MovablePoint(resultSet.getDouble(2), resultSet.getDouble(3));

                rect.setCenter(l);

                rect.setFilled(resultSet.getBoolean(4));
                int color = resultSet.getInt(5);
                Color c = new Color(color);
                rect.setColor(c);
                results.add(rect);
            }
            resultSet.close();


        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return results;
    }

    public Shape getShape(int id) {
        try(Connection con = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/chill", "root", "tuttifrutti")) {

            Statement stmt = con.createStatement();
            String selectShape = "select * from shape " +
                    "where id ='" + id + "'";

            ResultSet resultSet = stmt.executeQuery(selectShape);

            while (resultSet.next()) {
                Circle c1 = (Circle) new Object();
                c1.setId(resultSet.getInt("id"));
                c1.setType(resultSet.getString("type"));
                return c1;
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public int getShapeByName(String name) {
        try(Connection con = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/chill", "root", "tuttifrutti")) {

            Statement stmt = con.createStatement();
            String selectShape = "select * from shape " +
                    "where type ='" + name + "'";

            ResultSet resultSet = stmt.executeQuery(selectShape);
          //  System.out.println("resultSet " + resultSet);
          //  return (Shape) resultSet;
            while (resultSet.next()) {
               // Shape c1 = (Shape) new Object();
               // c1.setId(resultSet.getInt("id"));
               // c1.setType(resultSet.getString("type"));
                return resultSet.getInt("id");
            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return 0;
    }

    public ArrayList<Circle> getAllCircles() {
        ArrayList<Circle> results = new ArrayList<>();
        try(Connection con = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/chill", "root", "tuttifrutti")) {

            Statement stmt = con.createStatement();
            String selectSql = "select * from shape";
            ResultSet resultSet = stmt.executeQuery(selectSql);
            while (resultSet.next()) {
                int circleId = resultSet.getInt("id");
                Circle circle = new Circle(circleId);
                circle.setRadius(Integer.parseInt(resultSet.getString("radius")));
                circle.setCenter((MovablePoint) resultSet.getObject("center"));
                circle.setFilled(resultSet.getBoolean("filled"));

              //  Circle c1 = (Circle) this.getShape(resultSet.getInt("id"));
                Circle c1 = (Circle) this.getShape(resultSet.getInt("id"));
                circle.setShape(circleId);

                results.add(c1);

            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return results;
    }

    public ArrayList<Shape> getShapes() throws Exception {
        ArrayList<Shape> getshape = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chill", "root", "tuttifrutti")) {

            PreparedStatement stmt = (PreparedStatement) con.prepareStatement("SELECT * from shape");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Blob b = rs.getBlob(2);
                ObjectInputStream oin = new ObjectInputStream(b.getBinaryStream());
                Shape shape1 = (Shape) oin.readObject();
                getshape.add(shape1);
            }

        } catch (IOException sqlException) {
            sqlException.printStackTrace();
        }
        return getshape;
    }
}
