package esb;

public class OrderTypeRouter {

	public String route(Order order) {
		if ("international".equalsIgnoreCase(order.getOrderType())) {
			return "internationalShippingChannel";
		}
		return "domesticRoutingChannel";
	}
}
