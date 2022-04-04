package com.jpabook.pracjpashop.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDtos(){
        List<OrderQueryDto> result = findOrders();

        // o = OrderQueryDto
        result.forEach(o -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });
        return result;
    }

    public List<OrderQueryDto> findAllByDtos_optimization() {
        List<OrderQueryDto> result = findOrders();

        // OrderQueryDto의 id 추출
        List<Long> orderIds = result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());


        // 이전 findOrderQueryDtos에서는 forEach를 통해 루프를 돌았지만 이젠 한방에 가져올 것
        List<OrderItemQueryDto> orderItems = em.createQuery(
                        "select new com.jpabook.pracjpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                                " from OrderItem oi " +
                                " join oi.item i " +
                                " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        // orderItems 를 map으로 최적화 하는 과정
        // orderItemQueryDto.getOrderId()가 key가 되어 List<OrderItemQueryDto>의 값이 바뀜
        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));

        // 차이점은 기존은 루프를 돌렸지만 이 코드는 쿼리를 한번 날리고 map으로 다 가져온 후
        // 메모리에서 값을 매칭한 후 세팅하는 차이 , 메모리에 저장을 하니 쿼리가 두번만 나간다
        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }

    private List<OrderQueryDto> findOrders(){
        return em.createQuery(
                "select new com.jpabook.pracjpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate,o.status, d.address)" +
                        " from Order o " +
                        " join o.member m " +
                        " join o.delivery d " , OrderQueryDto.class
        ).getResultList();
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId){
        return em.createQuery(
                "select new com.jpabook.pracjpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi " +
                        " join oi.item i " +
                        " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }


}
