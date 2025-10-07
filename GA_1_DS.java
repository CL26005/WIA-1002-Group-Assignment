/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ga_1_ds;

//K2 Group 7

abstract class Shape3D {
    abstract double calculateVolume();
    abstract double calculateSurfaceArea();
}

class Sphere extends Shape3D {
    private double radius;

    public Sphere(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateVolume() {
        return (4.0 / 3) * Math.PI * Math.pow(radius, 3);
    }

    @Override
    public double calculateSurfaceArea() {
        return 4 * Math.PI * Math.pow(radius, 2);
    }
}

class Cylinder extends Shape3D {
    private double radius, height;

    public Cylinder(double radius, double height) {
        this.radius = radius;
        this.height = height;
    }

    @Override
    public double calculateVolume() {
        return Math.PI * Math.pow(radius, 2) * height;
    }

    @Override
    public double calculateSurfaceArea() {
        return 2 * Math.PI * radius * (radius + height);
    }
}

class Cone extends Shape3D {
    private double radius, height;

    public Cone(double radius, double height) {
        this.radius = radius;
        this.height = height;
    }

    @Override
    public double calculateVolume() {
        return (1.0 / 3) * Math.PI * Math.pow(radius, 2) * height;
    }

    @Override
    public double calculateSurfaceArea() {
        return Math.PI * radius * (radius + Math.sqrt(Math.pow(radius, 2) + Math.pow(height, 2)));
    }
}

class ShapeCalculator<T extends Shape3D> {
    private T shape;

    public ShapeCalculator(T shape) {
        this.shape = shape;
    }

    public void printCalculations() {
        String shapeName = shape.getClass().getSimpleName();
        System.out.printf("%s Volume: %.4f%n", shapeName, shape.calculateVolume());
        System.out.printf("%s Surface Area: %.4f%n", shapeName, shape.calculateSurfaceArea());
    }
}

public class GA_1_DS {
    public static void main(String[] args) {
        Sphere sphere = new Sphere(5);
        Cylinder cylinder = new Cylinder(5, 10);
        Cone cone = new Cone(5, 10);

        ShapeCalculator<Sphere> sphereCalc = new ShapeCalculator<>(sphere);
        ShapeCalculator<Cylinder> cylinderCalc = new ShapeCalculator<>(cylinder);
        ShapeCalculator<Cone> coneCalc = new ShapeCalculator<>(cone);

        sphereCalc.printCalculations();
        cylinderCalc.printCalculations();
        coneCalc.printCalculations();
    }
}

