package com.jpabook.pracjpashop.controller;

import com.jpabook.pracjpashop.domain.Item.Item;
import com.jpabook.pracjpashop.domain.Member;
import com.jpabook.pracjpashop.domain.Order;
import com.jpabook.pracjpashop.repository.OrderSearch;
import com.jpabook.pracjpashop.service.ItemService;
import com.jpabook.pracjpashop.service.MemberService;
import com.jpabook.pracjpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model){

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItem();

        model.addAttribute("members",members);
        model.addAttribute("items",items);

        return "order/orderForm";
    }
    // submit 후 넘어가는 post
    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count){
        orderService.order(memberId, itemId, count);

        return "redirect:/orders";
    }
    //
    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model){
        List<Order> orders = orderService.findOrders(orderSearch);

        model.addAttribute("orders",orders);
        return "order/orderList";
    }
    // 취소
    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId){
        orderService.cancleOrder(orderId);
        return "redirect:/orders";
    }
}
