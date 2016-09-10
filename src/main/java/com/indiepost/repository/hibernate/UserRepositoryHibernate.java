package com.indiepost.repository.hibernate;

import com.indiepost.enums.UserEnum;
import com.indiepost.model.User;
import com.indiepost.repository.UserRepository;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by jake on 7/30/16.
 */
@Repository
public class UserRepositoryHibernate implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void save(User user) {
        getSession().save(user);
    }

    @Override
    public void update(User user) {
        getSession().update(user);
    }

    @Override
    public void delete(User user) {
        getSession().delete(user);
    }

    @Override
    public User findOne(int id) {
        return (User) getCriteria()
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public User findByUsername(String username) {
        return (User) getCriteria()
                .add(Restrictions.eq("username", username))
                .uniqueResult();
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return (User) getCriteria()
                .add(Restrictions.eq("username", username))
                .add(Restrictions.eq("pawword", password))
                .uniqueResult();
    }

    @Override
    public User findByEmail(String email) {
        return (User) getCriteria()
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }

    @Override
    public List<User> findByState(UserEnum.State state) {
        return getCriteria()
                .add(Restrictions.eq("state", state))
                .list();
    }

    @Override
    public List<User> findByGender(UserEnum.Gender gender) {
        return getCriteria()
                .add(Restrictions.eq("gender", gender))
                .list();
    }

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    private Criteria getCriteria() {
        return getSession().createCriteria(User.class);
    }
}
