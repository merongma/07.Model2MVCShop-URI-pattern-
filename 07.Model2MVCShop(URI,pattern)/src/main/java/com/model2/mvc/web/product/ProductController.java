package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

//==> 회원관리 Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {

	/// Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	// setter Method 구현 않음

	public ProductController() {
		System.out.println(this.getClass());
	}

	@Value("#{commonProperties['pageUnit']}")
	// @Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;

	@Value("#{commonProperties['pageSize']}")
	// @Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;

	@RequestMapping(value = "addProduct", method = RequestMethod.GET)
	public String addProductView() throws Exception {

		System.out.println("/product/addProduct : GET");

		return "forward:/product/addProductView.jsp";
	}

	@RequestMapping(value = "addProduct", method = RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") Product product) throws Exception {

		System.out.println("/product/addProduct : POST");
		// Business Logic
		productService.addProduct(product);

		return "forward:/product/addProduct.jsp";
	}

	@RequestMapping(value = "getProduct", method = RequestMethod.GET)
	public String getProduct(@RequestParam("prodNo") int prodNo, Model model, @RequestParam("menu") String menu,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("/product/getProduct : POST");
		// Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);

		Cookie cookieBox[] = request.getCookies();
		Cookie cookie = null;

		if (cookieBox != null) {
			for (int i = 0; i < cookieBox.length; i++) {
				if (cookieBox[i].getName().equals("history")) {
					cookie = new Cookie("history", cookieBox[i].getValue() + "," + prodNo);
					break;
				}
			}
		} else {
			cookie = new Cookie("history", String.valueOf(prodNo));
		}

		if (cookie == null) {
			cookie = new Cookie("history", String.valueOf(prodNo));
		}

		cookie.setMaxAge(-1);
		cookie.setPath("/");

		System.out.println("쿠키값 확인 " + cookie.getValue());
		response.addCookie(cookie);

		System.out.println("menu값" + menu);
		if (menu.equals("manage")) {

			return "forward:/product/updateProductView.jsp";

		} else {

			return "forward:/product/getProduct.jsp";
		}

	}

	@RequestMapping(value = "updateProduct", method = RequestMethod.GET)
	public String updateProductView(@RequestParam("prodNo") int prodNo, Model model) throws Exception {

		System.out.println("/product/updateProduct : GET");
		// Business Logic
		Product product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", product);

		return "forward:/product/updateProductView.jsp";
	}

	@RequestMapping(value = "updateProduct", method = RequestMethod.POST)
	public String updateProduct(@ModelAttribute("product") Product product, Model model, HttpSession session,
			@RequestParam("prodNo") int prodNo) throws Exception {

		System.out.println("/product/updateProduct : POST");

		productService.updateProduct(product);

		Product product2 = productService.getProduct(prodNo);
		product.setRegDate(product2.getRegDate());

		model.addAttribute("product", product);

		return "forward:/product/updateProduct.jsp";

	}

	@RequestMapping(value = "listProduct")
	public String listProduct(@ModelAttribute("search") Search search, Model model, HttpServletRequest request)
			throws Exception {

		System.out.println("/user/listUser : GET / POST");

		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);

		// Business logic 수행
		Map<String, Object> map = productService.getProductList(search);

		Page resultPage = new Page(search.getCurrentPage(), ((Integer) map.get("totalCount")).intValue(), pageUnit,
				pageSize);
		System.out.println(resultPage);

		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);

		return "forward:/product/listProduct.jsp";
	}
}