package cms.Entity;

public class Payment {
    private CreditCard card;
    private boolean paymentStatus;

    public Payment() {
    }

    public Payment(CreditCard card, boolean paymentStatus) {
        this.card = card;
        this.paymentStatus = paymentStatus;
    }

    public CreditCard getCard() {
        return card;
    }

    public void setCard(CreditCard card) {
        this.card = card;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


    @Override
    public String toString() {
        return "Payment{" +
                "card=" + card +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}
