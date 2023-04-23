package homework1.third;

public class Triangle extends Figure{
    //пускай прямоугольный будет :)
    private double a;
    private double b;
    private double c;


    public Triangle(double a, double b, String color) {
        this.a = a;
        this.b = b;
        this.c = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        this.color = color;
    }

    @Override
    void paintOver() {
        System.out.println("Triangle painted");
        super.paintOver();
    }

    @Override
    double getSquareArea() {
        return 0.5 * a * b;
    }
}
