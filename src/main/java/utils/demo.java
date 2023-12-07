package utils;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import DAO.*;
import bean.Product;
import bean.ProductCategory;
import bean.ProductItem;
import bean.VariationOption;

public class demo {
	public static void demoUser() {
		UserAccountDAO userAccountDAO = new UserAccountDAO();

		/*
		 * UserAccount newUserAccount1 = new UserAccount("manager", "manager@gmail.com",
		 * "0123456789", "123", 0, null, null); userAccountDAO.addUser(newUserAccount1);
		 * UserAccount newUserAccount2 = new UserAccount("staff", "staff@gmail.com",
		 * "0123456789", "123", 1, null, null); userAccountDAO.addUser(newUserAccount2);
		 */
		System.out.println(userAccountDAO.getShoppingCart(4));
	}

	public static void demoProductCategory() {
		ProductCategoryDAO productCategoryDAO = new ProductCategoryDAO();

//		ProductCategory productCategory1 = new ProductCategory("Blazer", null);
//		ProductCategory productCategory2 = new ProductCategory("Shirt", null);
		ProductCategory productCategory3 = new ProductCategory("Accessories", null);
//		ProductCategory productCategory4 = new ProductCategory("Pantalon", null);
//		ProductCategory productCategory5 = new ProductCategory("Shorts", null);
		 
		if(productCategoryDAO.addProductCategogy(productCategory3)){
			System.out.println("Đã thêm thành công 1 product category");
		}
		else {
			System.out.println("Thất bại khi thêm 1 product category");
		}
//		productCategoryDAO.addProductCategogy(productCategory2);
//		productCategoryDAO.addProductCategogy(productCategory3);
//		productCategoryDAO.addProductCategogy(productCategory4);
//		productCategoryDAO.addProductCategogy(productCategory5);

		List<ProductCategory> listProductCategories = productCategoryDAO.listProductCategories();
		listProductCategories.forEach(p -> System.out.println(p.getCategoryName()));
		
//		System.out.println(productCategoryDAO.getProductCategorybyID(2).getCategoryName());

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
		
//		Thêm product 
//		Product newProduct1 = new Product("Blazer đẹp vl", "áo đẹp lém tình yêu ơi", "", null); 
//		Product newProduct2 = new Product("Long blazer jacket", "This is the coolest shirt the shop has ever had", "", null); 
//		Product newProduct3 = new Product("Men's short-sleeved blazer jacket", "This is the coolest shirt the shop has", "", null); 
//		Product newProduct4 = new Product("Classic European blazer", "Cette chemise a l'air trop cool", "", null);
		
//		productDAO.addProduct(newProduct2, "Shirt");
//		productDAO.addProduct(newProduct3, "Shirt");
		
//		Product product = productDAO.getProductbyID(2);
//		System.out.print(product.getProductID() + " " + product.getName());
		
//		productDAO.addProduct(newProduct2, "Blazer");
//		productDAO.addProduct(newProduct3, "Blazer");
//		productDAO.addProduct(newProduct4, "Blazer");
		
//		productDAO.deleteProductByName("Basic blazer jacket");
//		s
//		// Lấy danh sách product item
//		Set<Product> oldProducts = productCategoryDAO.getProductByCategory("Shirt");
//		if (oldProducts != null) {
//			oldProducts.forEach(p -> System.out.println(p.getName()));
//		} else {
//			System.out.print("Danh sách các product rỗng");
//		}
		
//		if(productDAO.deleteProductByID(2)) {
//			System.out.println("Đã xoá thành công");
//		}
//		else {
//			System.out.println("Xoá thất bại");
//		}
		
		
//		System.out.print(productDAO.getProductbyID(1).getDescription());

//		List<Product> productList = productDAO.searchProduct("jacket");
//
//		productList.forEach(p -> System.out.println(p.getName()));

	}
	
	
	public static void addProductItem() throws Exception {
		ProductItemDAO productItemDAO = new ProductItemDAO();
		ProductDAO productDAO = new ProductDAO();
		
		Product newProduct = new Product();
		ProductItem newProductItem = new ProductItem();
		
//		newProduct.setName("Shirt 15");
//		newProduct.setDescription("Mac suong vl");
//		newProduct.setProduct_image(null);

//		productDAO.addProduct(newProduct, "Shirt");

//		newProductItem.setPrice(12000);
//		newProductItem.setQty_in_stock(100);
//		newProductItem.setProduct_image(null);
//
//		productItemDAO.addProductItem(newProductItem, 1002, "XL", "White");
//		productItemDAO.addProductItem(newProductItem, 2, "L", "White");
//		productItemDAO.addProductItem(newProductItem, 2, "XL", "White");
//		
//		productItemDAO.addProductItem(newProductItem, 3, "M", "Black");
//		productItemDAO.addProductItem(newProductItem, 2, "L", "Black");
//		productItemDAO.addProductItem(newProductItem, 2, "XL", "Black");

//		List<ProductItem> productItems = productItemDAO.getListProductItemByProductID(2);
//		for(ProductItem productItem : productItems){
//			for(VariationOption variationOption : productItem.getVariationOptions()){
//				variationOption.getVariation().getName();
//			}
//			productItem.getVariationOptions().forEach(p -> System.out.println(p.getVariation().getName() +": " + p.getValue()));
//		}
		
//		Set<String> setColor = new HashSet<String>();
//		Set<String> setSize = new HashSet<String>();
//		
//		setSize.add("S");
//		setSize.add("L");
//		setSize.add("M");
//		
//		setColor.add("Black");
//		setColor.add("Red");
		
//		productItemDAO.addListProductItem(newProductItem, newProduct, setSize, setColor, productCategoryPick);
//		ProductItem productItem = productItemDAO.getProductItemsByConditions(1002, "S", "White");
//		System.out.println(productItem.getSku());
		List<ProductItem> productItems = productItemDAO.getListProductItemByProductID(1002);
		productItems.forEach(p -> System.out.println(p.getSku()));
		System.out.println("-----------");
		List<ProductItem> productItems2 = productItemDAO.getListProductItemByProductID2(1002);
		productItems2.forEach(p -> System.out.println(p.getSku()));
	}
	
	public static void demoShoppingCart() {
		ShoppingCartDAO shoppingCartDAO = new ShoppingCartDAO();
		
		shoppingCartDAO.addShoppingCart(4);
		
	}
	
	public static void demoShoppingCartItem() {
		ShoppingCartItemDAO shoppingCartItemDAO = new ShoppingCartItemDAO();
		
		shoppingCartItemDAO.addProductToShoppingCart(10, 4, 2);
		shoppingCartItemDAO.addProductToShoppingCart(11, 4, 1);
		
	}

	public static void demoVariation(){
		VariationOptionDAO variationOptionDAO = new VariationOptionDAO();

		Set<VariationOption> variationOptions = variationOptionDAO.getVariationOptionByProductID(1002);
		variationOptions.forEach(p -> System.out.println(p.getValue()));
	}

	public static void main(String[] args) throws Exception {
		addProductItem();

	}
}
