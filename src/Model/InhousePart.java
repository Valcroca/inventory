package Model;

public class InhousePart extends Part {

    private int machineID;

    public InhousePart(int ID, String name, double price, int stock, int min, int max, int machineID) {
        super(ID, name, price, stock, min, max);
        this.machineID = machineID;
    }

    public int getMachineID() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    public boolean isIncomplete() {
        return (this.getID() == 0 || this.getName() == "" || this.getPrice() == 0 || this.getStock() == 0 || this.getMin() == 0 || this.getMax() == 0 || this.machineID == 0);
    }
}
