package homework1.third;

public class Circle extends Figure{
    private double dia;

    public Circle(double dia, String color) {
        this.dia = dia;
        this.color = color;
    }

    @Override
    void paintOver() {
        System.out.println("Circle painted");
        super.paintOver();
    }

    @Override
    double getSquareArea() {
        return 3.1415 * Math.pow(dia/2, 2);
    }
}
