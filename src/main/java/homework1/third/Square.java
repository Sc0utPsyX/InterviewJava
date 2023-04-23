package homework1.third;

public class Square extends Figure{
    private double side;

    public Square(double side, String color) {
        this.side = side;
        this.color = color;
    }

    @Override
    void paintOver() {
        System.out.println("Square painted");
        super.paintOver();
    }

    @Override
    double getSquareArea() {
        return Math.pow(side, 2);
    }
}
