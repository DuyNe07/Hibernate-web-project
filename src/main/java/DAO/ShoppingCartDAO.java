package DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import bean.ShoppingCart;
import bean.UserAccount;
import utils.HibernateUtil;

public class ShoppingCartDAO {
	private static final SessionFactory factory = HibernateUtil.getSessionFactory();
	
	public void addShoppingCart(int userID) {
		try (Session session = factory.openSession()) {
			session.getTransaction().begin();
			
			UserAccountDAO userAccountDAO = new UserAccountDAO();
			UserAccount userAccount = userAccountDAO.getUserAccount(userID);

			ShoppingCart newsShoppingCart = new ShoppingCart(userAccount, null);
			
			session.save(newsShoppingCart);
			session.getTransaction().commit();
		}
	}
	
}
