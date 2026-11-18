
package productapp;

public class ProductCalculations implements IProductCalculations {

    private final double price;

    public ProductCalculations(double price) {
        this.price = price;
    }

    // Calculate 15% VAT

    /**
     *
     * @return
     */
    @Override
    public double GetVat() {
        return price * 0.15;
    }

    // Calculate 5% commission
    @Override
    public double GetCommission() {
        return price * 0.05;
    }

    // Calculate total sale amount
    @Override
    public double GetTotal() {
        return price + GetVat() + GetCommission();
    }
}
