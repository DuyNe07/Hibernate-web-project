package DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import bean.ProductItem;
import bean.ShoppingCart;
import bean.ShoppingCartItem;
import utils.HibernateUtil;

public class ShoppingCartItemDAO {
	private static final SessionFactory factory = HibernateUtil.getSessionFactory();
	
	public boolean addProductToShoppingCart(int productID, int userID, int quantity) {
		try(Session session = factory.openSession()){
			try {
				session.getTransaction().begin();
				UserAccountDAO userAccountDAO = new UserAccountDAO();
				
				ShoppingCart shoppingCart = userAccountDAO.getShoppingCart(userID);
				ProductItem productItem = session.get(ProductItem.class, productID);
				ShoppingCartItem shoppingCartItem = new ShoppingCartItem(quantity, null, null);
				shoppingCartItem.setShoppingCart(shoppingCart);
				shoppingCartItem.setProductItem(productItem);
				
				session.save(shoppingCartItem);
				session.getTransaction().commit();
				return true;
				
			} catch (Exception e) {
	            session.getTransaction().rollback();
			}
		}
		return false;
	}
}
