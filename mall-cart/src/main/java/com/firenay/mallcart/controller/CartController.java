package com.firenay.mallcart.controller;

import com.firenay.mallcart.service.CartService;
import com.firenay.mallcart.vo.Cart;
import com.firenay.mallcart.vo.CartItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * <p>Title: CartController</p>
 * Description：
 * date：2020/6/27 22:20
 */
@Slf4j
@Controller
public class CartController {

	private final String PATH = "redirect:http://cart.glmall.com/cart.html";

	@Autowired
	private CartService cartService;

	@ResponseBody
	@GetMapping("/currentUserCartItems")
	public List<CartItem> getCurrentUserCartItems(){

		return cartService.getUserCartItems();
	}

	@ResponseBody
	@GetMapping("toTrade")
	public String toTrade() throws ExecutionException, InterruptedException {
		BigDecimal price = cartService.toTrade();
		return "结算成功,共：￥" + price.toString();
	}

	@GetMapping("/deleteItem")
	public String deleteItem(@RequestParam("skuId") Long skuId){
		cartService.deleteItem(skuId);
		return PATH;
	}

	@GetMapping("/countItem")
	public String countItem(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num){
		cartService.changeItemCount(skuId, num);
		return PATH;
	}

	@GetMapping("checkItem.html")
	public String checkItem(@RequestParam("skuId") Long skuId, @RequestParam("check") Integer check){
		cartService.checkItem(skuId, check);
		return PATH;
	}

	@GetMapping("/addToCartSuccess.html")
	public String addToCartSuccessPage(@RequestParam(value = "skuId",required = false) Object skuId, Model model){
		CartItem cartItem = null;
		// 然后在查一遍 购物车
		if(skuId == null){
			model.addAttribute("item", null);
		}else{
			try {
				cartItem = cartService.getCartItem(Long.parseLong((String)skuId));
			} catch (NumberFormatException e) {
				log.warn("恶意操作! 页面传来非法字符.");
			}
			model.addAttribute("item", cartItem);
		}
		return "success";
	}

	/**
	 * 添加商品到购物车
	 * 	RedirectAttributes: 会自动将数据添加到url后面
	 */
	@GetMapping("/addToCart")
	public String addToCart(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num, RedirectAttributes redirectAttributes) throws ExecutionException, InterruptedException {

		cartService.addToCart(skuId, num);
		redirectAttributes.addAttribute("skuId", skuId);
		// 重定向到成功页面
		return "redirect:http://cart.glmall.com/addToCartSuccess.html";
	}

	/**
	 * 浏览器有一个cookie：user-key 标识用户身份 一个月后过期
	 * 每次访问都会带上这个 user-key
	 * 如果没有临时用户 还要帮忙创建一个
	 */
	@GetMapping({"/","/cart.html"})
	public String carListPage(Model model) throws ExecutionException, InterruptedException {

		Cart cart = cartService.getCart();
		model.addAttribute("cart", cart);
		return "cartList";
	}
}
