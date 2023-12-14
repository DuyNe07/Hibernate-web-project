package utils;

import java.time.LocalDate;
import java.util.*;

import DAO.*;
import bean.*;

public class demo {
	public static void demoUser() {
		UserAccountDAO userAccountDAO = new UserAccountDAO();
		AddressDAO addressDAO = new AddressDAO();

		Address address = new Address("59", "Dinh Bo Linh", "Ho Chi Minh", "Binh Thanh", null, null);
//		addressDAO.deleteAddress(1);
		addressDAO.addAddressForUser(address, 3);
		/*
		 * UserAccount newUserAccount1 = new UserAccount("manager", "manager@gmail.com",
		 * "0123456789", "123", 0, null, null); userAccountDAO.addUser(newUserAccount1);
		 * UserAccount newUserAccount2 = new UserAccount("staff", "staff@gmail.com",
		 * "0123456789", "123", 1, null, null); userAccountDAO.addUser(newUserAccount2);
		 */
//		UserAccount newUserAccount = new UserAccount("manager", "manager@gmail.com", "0123456789", "123", 0, null, null);
//		UserAccount newUserAccount = new UserAccount("client", "client@gmail.com", "1234567890", "123", 2, null, null);
//		userAccountDAO.addUser(newUserAccount);
//		userAccountDAO.editUser(1, newUserAccount);
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
		
//		shoppingCartDAO.addShoppingCart(3);
//		Set<ShoppingCartItem> shoppingCartItems = shoppingCartDAO.listProductItemByUserID(3);
//		shoppingCartItems.forEach(p -> System.out.println(p.getProductItem().getSku()));
		ShoppingCart shoppingCart = shoppingCartDAO.getShoppingCart(3);
		System.out.println(shoppingCart.getShoppingCartID());

	}
	
	public static void demoShoppingCartItem() {
		ShoppingCartItemDAO shoppingCartItemDAO = new ShoppingCartItemDAO();
		ShoppingCartDAO shoppingCartDAO = new ShoppingCartDAO();
		
		shoppingCartItemDAO.addProductToShoppingCart(3, 3, 2);
		shoppingCartItemDAO.addProductToShoppingCart(2, 3, 1);
//		shoppingCartItemDAO.deleleProductToShoppingCart(4, 1);
	}

	public static void demoVariation(){
		VariationOptionDAO variationOptionDAO = new VariationOptionDAO();

		Set<VariationOption> variationOptions = variationOptionDAO.getVariationOptionByProductID(1002);
		variationOptions.forEach(p -> System.out.println(p.getValue()));
	}

	public static void demeOrder(){
		ShopOrderDAO shopOrderDAO = new ShopOrderDAO();
		OrderLineDAO orderLineDAO = new OrderLineDAO();

		int shipping_method_ID = 1;
		int address_ID = 2;
		int user_ID = 3;
		int payment_method_ID = 3;

		shopOrderDAO.addShopOrder(shipping_method_ID, address_ID, user_ID, payment_method_ID);
	}

	public static void main(String[] args) throws Exception {
		demeOrder();
	}
}
