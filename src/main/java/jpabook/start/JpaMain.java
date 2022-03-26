package jpabook.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        // 엔티티 매니저 팩토리 - 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        // 엔티티 매니저 - 생성
        EntityManager em = emf.createEntityManager();
        // 트랜잭션 - 획득
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            logic(em);
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    // 비즈니스 로직
    private static void logic(EntityManager em) {
        Member member = new Member();
        member.setId(1L);
        member.setName("test1");
        member.setAge(22);

        // 등록
        em.persist(member);

        // 한 건 조회
        Member findMember = em.find(Member.class, 1L);
        System.out.println("findMember.getId() = " + findMember.getId());
        System.out.println("findMember.getName() = " + findMember.getName());
        System.out.println("findMember.getAge() = " + findMember.getAge());

        // 수정
        findMember.setAge(11);

        // 목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        System.out.println("members.size() = " + members.size());

        // 삭제
        em.remove(findMember);

    }

}
