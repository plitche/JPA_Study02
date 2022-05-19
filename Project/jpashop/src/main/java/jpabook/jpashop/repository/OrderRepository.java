package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

/*
    return em.createQuery("select o from Order o join o.member m" +
            " where o.status = :status " +
            " and m.name like :name", Order.class)
            .setParameter("status", orderSearch.getOrderStatus())
            .setParameter("name", orderSearch.getMemberName())
            // .setFirstResult(100)
            .setMaxResults(1000) // 최대 1000건
            .getResultList();
*/

    // 1. 첫번째 무식한 방법
    public List<Order> findAll(OrderSearch orderSearch) {

        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = status";
        }

         TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                 .setMaxResults(1000);

        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }

        return query.getResultList();
    }

     public List<Order> findAllByCriteria(OrderSearch orderSearch) {
         // 2.
         CriteriaBuilder cb = em.getCriteriaBuilder();
         CriteriaQuery<Order> cq = cb.createQuery(Order.class);
         Root<Order> o = cq.from(Order.class);
         Join<Object, Object> m = o.join("member", JoinType.INNER);

         //주문 상태 검색
         List<Predicate> criteria = new ArrayList<>();
         if (orderSearch.getOrderStatus() != null) {
             Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
             criteria.add(status);
         }

         //회원 이름 검색
         if (StringUtils.hasText(orderSearch.getMemberName())) {
             Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
             criteria.add(name);
         }

         cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
         TypedQuery<Order> orderTypedQuery = em.createQuery(cq).setMaxResults(1000);
         return orderTypedQuery.getResultList();
     }


    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                "select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d", Order.class
        ).getResultList();
    }
}
