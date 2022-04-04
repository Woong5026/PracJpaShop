package com.jpabook.pracjpashop.service;

import com.jpabook.pracjpashop.domain.Address;
import com.jpabook.pracjpashop.domain.Item.Book;
import com.jpabook.pracjpashop.domain.Item.Item;
import com.jpabook.pracjpashop.domain.Member;
import com.jpabook.pracjpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        // given
        Member member = memberSet();

        Item item = createBook("책1",10000,10);


        // when

        // then
    }

    private Member memberSet() {
        Member member = new Member();
        member.setName("member1");
        member.setAddress(new Address("서울", "강가","123-123"));
        em.persist(member);
        return member;
    }
    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }
}