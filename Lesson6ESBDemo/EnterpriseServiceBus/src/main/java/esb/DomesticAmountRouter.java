package esb;

public class DomesticAmountRouter {

	public String route(Order order) {
		if (order.getAmount() > 175) {
			return "nextDayShippingChannel";
		}
		return "normalShippingChannel";
	}
}
