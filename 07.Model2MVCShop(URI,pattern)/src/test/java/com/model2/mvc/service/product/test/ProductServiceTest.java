package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

/*
 *	FileName :  UserServiceTest.java
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class ProductServiceTest {

	// ==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	// @Test
	public void testAddProduct() throws Exception {

		Product product = new Product();
		product.setProdName("�������Ű");
		product.setProdDetail("������� ����־��");
		product.setManuDate("2019-04-23");
		product.setPrice(2000);
		product.setFileName("fileName");

		productService.addProduct(product);

		product = productService.getProduct(10010);

		// ==> console Ȯ��
		// System.out.println(user);

		// ==> API Ȯ��
		Assert.assertEquals("�������Ű", product.getProdName());
		Assert.assertEquals("������� ����־��", product.getProdDetail());
		Assert.assertEquals("2019-04-23", product.getManuDate());
		Assert.assertEquals(2000, product.getPrice());
		Assert.assertEquals("fileName", product.getFileName());
	}

	// @Test
	public void testGetProduct() throws Exception {

		Product product = productService.getProduct(10010);

		// ==> console Ȯ��
		// System.out.println(user);

		// ==> API Ȯ��
		System.out.println(product);
		System.out.println(productService.getProduct(10020));

		Assert.assertNotNull(productService.getProduct(10010));
		Assert.assertNotNull(productService.getProduct(10020));
	}

	// @Test
	public void testUpdateProduct() throws Exception {

		Product product = productService.getProduct(10013);
		Assert.assertNotNull(product);

		Assert.assertEquals("�������Ű", product.getProdName());
		Assert.assertEquals("������� ����־��", product.getProdDetail());
		Assert.assertEquals("2019-04-23", product.getManuDate());
		Assert.assertEquals(2000, product.getPrice());
		Assert.assertEquals("fileName", product.getFileName());

		product.setProdName("����Ĩ��Ű");
		product.setProdDetail("������");
		product.setPrice(2300);

		productService.updateProduct(product);

		product = productService.getProduct(10013);
		Assert.assertNotNull(product);
		System.out.println(productService.getProduct(10013));

	}

	//@Test
	public void testGetProductListAll() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		Map<String, Object> map = productService.getProductList(search);

		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		// ==> console Ȯ��
		// System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("0");
		search.setSearchKeyword("");
		map = productService.getProductList(search);

		list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		// ==> console Ȯ��
		// System.out.println(list);

		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}

	@Test
	public void testGetProductListByProdNo() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("0");
		search.setSearchKeyword("10011");
		Map<String, Object> map = productService.getProductList(search);
		

		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(1, list.size());

		// ==> console Ȯ��
		 System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setSearchCondition("0");
		search.setSearchKeyword("10004");
		//search.setSearchKeyword("" + System.currentTimeMillis());
		map=productService.getProductList(search);

		list = (List<Object>) map.get("list");
		Assert.assertEquals(1, list.size());

		// ==> console Ȯ��
		System.out.println(list);

		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}

	//@Test
	public void testGetProductListByProdName() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("1");
		search.setSearchKeyword("���׶��");
		Map<String, Object> map = productService.getProductList(search);

		List<Object> list = (List<Object>) map.get("list");
		System.out.println(list);

		Assert.assertEquals(1, list.size());


		// ==> console Ȯ��
		System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setSearchCondition("1");
		search.setSearchKeyword("�ζ���");
		//search.setSearchKeyword("" + System.currentTimeMillis());
		map=productService.getProductList(search);

		list = (List<Object>) map.get("list");
		Assert.assertEquals(1, list.size());

		// ==> console Ȯ��
		System.out.println(list);

		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}
}