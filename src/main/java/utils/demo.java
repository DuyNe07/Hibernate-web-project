package utils;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import DAO.*;
import bean.*;

public class demo {
	public static void demoUser() {
		UserAccountDAO userAccountDAO = new UserAccountDAO();

		/*
		 * UserAccount newUserAccount1 = new UserAccount("manager", "manager@gmail.com",
		 * "0123456789", "123", 0, null, null); userAccountDAO.addUser(newUserAccount1);
		 * UserAccount newUserAccount2 = new UserAccount("staff", "staff@gmail.com",
		 * "0123456789", "123", 1, null, null); userAccountDAO.addUser(newUserAccount2);
		 */
		UserAccount newUserAccount = new UserAccount("client", "client2@gmail.com", "0123456789", "123", 2, null, null);
//		userAccountDAO.addUser(newUserAccount);
		userAccountDAO.editUser(1, newUserAccount);
	}

	public static void demoProductCategory() {
		ProductCategoryDAO productCategoryDAO = new ProductCategoryDAO();

	}

	public static void SKUGenerator(String productType, String color, String size) {

		Random random = new Random();
		int randomSuffix = random.nextInt(1000000);

		String sku = productType.substring(0, 2) + color.substring(0, 1) + size.substring(0, 1) + randomSuffix;

		System.out.println(sku.toUpperCase());
		// SKUGenerator("Balzer", "Blue", "L");
	}

	public static void demoProduct() throws Exception {
		ProductCategoryDAO productCategoryDAO = new ProductCategoryDAO();
		ProductDAO productDAO = new ProductDAO();
//		ProductCategory productCategory = productCategoryDAO.getProductCategorybyID(2);
//		Product newProduct = new Product();
//		newProduct.setName("Shirt 6");
//		newProduct.setProduct_image("123.jpg");
//		newProduct.setDescription("he he");
//		newProduct.setProductCategory(productCategory);
//		productDAO.editProduct(7, newProduct);
//		productDAO.addProduct(newProduct, "Shirt");

		List<Product> products = productDAO.getProductsByPage(2, 3);
		products.forEach(p -> System.out.println(p.getName()));
	}
	
	
	public static void addProductItem() throws Exception {
		ProductItemDAO productItemDAO = new ProductItemDAO();
		ProductDAO productDAO = new ProductDAO();
		
		Product newProduct = new Product();

		ProductItem productItem = productItemDAO.getProductItemsByConditions(1, "S", "Red");
		System.out.println(productItem.getSku());
	}

	
	public static void demoShoppingCart() {
		ShoppingCartDAO shoppingCartDAO = new ShoppingCartDAO();
		
//		shoppingCartDAO.addShoppingCart(1);
		Set<ShoppingCartItem> shoppingCartItems = shoppingCartDAO.listProductItemByUserID(1);
		for(ShoppingCartItem shoppingCartItem : shoppingCartItems){
			System.out.println(shoppingCartItem.getProductItem().getProduct().getName());
			shoppingCartItem.getProductItem().getVariationOptions().forEach(p -> System.out.println(p.getValue()));
		}


	}
	
	public static void demoShoppingCartItem() {
		ShoppingCartItemDAO shoppingCartItemDAO = new ShoppingCartItemDAO();
		
		shoppingCartItemDAO.addProductToShoppingCart(3, 1, 2);
//		shoppingCartItemDAO.addProductToShoppingCart(4, 1, 1);
//		shoppingCartItemDAO.deleleProductToShoppingCart(3, 1);
	}

	public static void demoVariation(){
		VariationOptionDAO variationOptionDAO = new VariationOptionDAO();

		Set<VariationOption> variationOptions = variationOptionDAO.getVariationOptionByProductID(1002);
		variationOptions.forEach(p -> System.out.println(p.getValue()));
	}

	public static void main(String[] args) throws Exception {
		demoProduct();
	}
}
