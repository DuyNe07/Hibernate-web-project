package DAO;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import javax.persistence.criteria.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import bean.Product;
import bean.ProductItem;
import bean.VariationOption;
import utils.HibernateUtil;

public class ProductItemDAO {
	private final static SessionFactory factory = HibernateUtil.getSessionFactory();

	public ProductItem getProductItem(int productItemID) {
		try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<ProductItem> query = builder.createQuery(ProductItem.class);
            Root<ProductItem> root = query.from(ProductItem.class);

            Predicate condition = builder.equal(root.get("productItemID"), productItemID);
            query.where(condition);
            root.fetch("variationOptions", JoinType.LEFT);

            return session.get(ProductItem.class, productItemID);
		}
	}
	
	public boolean addProductItem(ProductItem newProductItem, int ProductID, String size, String color) {
		try(Session session = factory.openSession()){
			try {
				session.getTransaction().begin();
				
				VariationOptionDAO variationOptionDAO = new VariationOptionDAO();
				ProductDAO productDAO = new ProductDAO();
				
				Product product = productDAO.getProduct(ProductID);
                ProductItem oldProductItem = getProductItemsByConditions(ProductID, size, color);
                if(oldProductItem != null){
                    int oldQuantity = oldProductItem.getQty_in_stock();
                    oldProductItem.setQty_in_stock(oldQuantity+newProductItem.getQty_in_stock());
                    System.out.println("Save new quantity with product item ID: " + oldProductItem.getProductItemID());
                    session.saveOrUpdate(oldProductItem);
                }
                else {
                    Random random = new Random();
                    int randomSuffix = random.nextInt(10000);
                    String sku = product.getName().substring(0, 4) + color.charAt(0) + size.charAt(0) + randomSuffix;
                    newProductItem.setSku(sku);

                    VariationOption variationOptionSize = variationOptionDAO.getVariationOption(size, session);
                    VariationOption variationOptionColor = variationOptionDAO.getVariationOption(color, session);

                    newProductItem.getVariationOptions().add(variationOptionSize);
                    newProductItem.getVariationOptions().add(variationOptionColor);
                    newProductItem.setProduct(product);

                    variationOptionSize.getProductItems().add(newProductItem);
                    variationOptionColor.getProductItems().add(newProductItem);

                    session.save(newProductItem);
                    session.saveOrUpdate(variationOptionColor);
                    session.saveOrUpdate(variationOptionSize);
                    System.out.println("New ProductItem ID: " + newProductItem.getProductItemID());
                }
				session.getTransaction().commit();
				session.close();
				return true;

			} catch (Exception e) {
	            if (session.getTransaction() != null) {
	                session.getTransaction().rollback();
	            }
	            e.printStackTrace();
			}

		}
        return false;
	}

    public ProductItem getProductItemsByConditions(int productID, String size, String color) throws Exception{
        try(Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<ProductItem> query = builder.createQuery(ProductItem.class);
            Root<ProductItem> root = query.from(ProductItem.class);

            Predicate condition = builder.equal(root.get("product").get("productID"), productID);
            query.where(condition);
            root.fetch("variationOptions", JoinType.INNER);

            List<ProductItem> productItemSet = session.createQuery(query).getResultList();

            for(ProductItem productItem : productItemSet){
                Set<VariationOption> variationOptions = productItem.getVariationOptions();

                boolean containColor = false;
                boolean contrainSize = false;

                for(VariationOption variationOption : variationOptions){
                    if(size.toLowerCase().equals(variationOption.getValue().toLowerCase())){
                        contrainSize = true;
                    }
                    else if(color.toLowerCase().equals(variationOption.getValue().toLowerCase())){
                        containColor = true;
                    }
                }
                if(containColor && contrainSize){
                    return productItem;
                }
            }
            return null;
        }
    }

    public List<ProductItem> getListProductItemByProductID(int productID) {
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<ProductItem> query = builder.createQuery(ProductItem.class);
            Root<ProductItem> root = query.from(ProductItem.class);

            query.select(root);
            Predicate condition = builder.equal(root.get("product").get("productID"), productID);
            query.where(condition);
            root.fetch("variationOptions", JoinType.LEFT).fetch("variation", JoinType.LEFT);
            query.distinct(true);

            List<ProductItem> productItemSet = session.createQuery(query).getResultList();
            return productItemSet;
        }
    }

    public List<ProductItem> getListProductItemByProductID2(int productID) {
        try(Session session = factory.openSession()){
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<ProductItem> query = builder.createQuery(ProductItem.class);
            Root<ProductItem> rootProductItem = query.from(ProductItem.class);
            Root<Product> rootProduct = query.from(Product.class);

            query = query.where(
                    builder.and(builder.equal(rootProduct.get("productID"), productID),
                    builder.equal(rootProduct.get("productID"), rootProductItem.get("product"))));

            rootProductItem.fetch("variationOptions", JoinType.LEFT).fetch("variation", JoinType.LEFT);
            query.distinct(true);
            query.select(rootProductItem);

            List<ProductItem> productItemSet = session.createQuery(query).getResultList();
            return productItemSet;
        }
    }


    public boolean editProductItem(int productItemID ,int newQuantity, float newPrice, String img_url){
        try(Session session = factory.openSession()){
            try{
                session.getTransaction().begin();

                ProductItem productItem = getProductItem(productItemID);
                productItem.setPrice(newPrice);
                productItem.setQty_in_stock(newQuantity);
                productItem.setProduct_image(img_url);

                session.saveOrUpdate(productItem);
                session.getTransaction().commit();
                System.out.println("Updated product Item with SKU" + productItem.getSku());
                session.close();
                return true;
            }catch (Exception e){
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean deleteProductItem(int productItemID){
        try(Session session = factory.openSession()){
            session.getTransaction().begin();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<ProductItem> query = builder.createQuery(ProductItem.class);
            Root<ProductItem> root = query.from(ProductItem.class);

            Predicate condition = builder.equal(root.get("productItemID"), productItemID);
            query.where(condition);
            root.fetch("orderLines", JoinType.LEFT);

            ProductItem productItem = session.createQuery(query).uniqueResult();
            if(productItem.getOrderLines().size() == 0){
                session.delete(productItem);
                System.out.println("Successfully deleted product item");
                session.getTransaction().commit();
                session.close();
                return true;
            }
            else {
                System.out.println("This product item does exist order");
                session.close();
                return false;
            }
        }
    }

}
