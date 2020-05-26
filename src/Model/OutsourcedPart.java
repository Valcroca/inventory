package Model;

public class OutsourcedPart extends Part {
    private String companyName;

    public OutsourcedPart(int ID, String name, double price, int stock, int min, int max, String companyName) {
        super(ID, name, price, stock, min, max);
        this.companyName = companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public boolean isIncomplete() {
        return (this.getID() == 0 || this.getName() == "" || this.getPrice() == 0 || this.getStock() == 0 || this.getMin() == 0 || this.getMax() == 0 || this.companyName == "");
    }
}
