package online.library.repositories;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Date;

@Repository
@Transactional
public class BookDaoImpl implements BookDao{
    @PersistenceContext
    EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @Override
    public Long getCountOfReaders(Long id) {
        String queryString = "select count(ub.id) from bookzz.book b " +
                "join bookzz.user_book ub on b.id = ub.book_id\n" +
                " where b.id =:id";
        Query query = getSession().createSQLQuery(queryString)
                .setParameter("id", id);
        BigInteger count = (BigInteger) query.getSingleResult();

        return count.longValue();
    }
}
