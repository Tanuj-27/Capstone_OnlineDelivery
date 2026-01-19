package com.ey.dto.response;

import com.ey.enums.OrderStatus;

public class OwnerOrderSummaryResponse {

	private Long orderId;

	private OrderStatus status;

	private Integer totalAmount;

	public Long getOrderId() {

		return orderId;

	}

	public void setOrderId(Long orderId) {

		this.orderId = orderId;

	}

	public OrderStatus getStatus() {

		return status;

	}

	public void setStatus(OrderStatus status) {

		this.status = status;

	}

	public Integer getTotalAmount() {

		return totalAmount;

	}

	public void setTotalAmount(Integer totalAmount) {

		this.totalAmount = totalAmount;

	}

}
