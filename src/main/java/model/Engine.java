package model;

/**
 *
 */

public class Engine {
    private int id;
    private String model;
    private int power;

    public Engine() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", power=" + power +
                '}';
    }
}
