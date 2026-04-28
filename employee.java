public class employee {
    Double id;
    String name;
    Double salary;
    public employee(Double id, String name, Double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }
    public String toString(){
        return id + " " + name + " " + salary;
    }
}
