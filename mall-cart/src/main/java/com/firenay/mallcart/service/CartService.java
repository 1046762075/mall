package com.firenay.mallcart.service;

import com.firenay.mallcart.vo.Cart;
import com.firenay.mallcart.vo.CartItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * <p>Title: CartService</p>
 * Description：
 * date：2020/6/27 22:17
 */
public interface CartService {

	/**
	 * 将商品添加到购物车
	 */
	CartItem addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;

	/**
	 * 获取购物车中某个购物项
	 */
	CartItem getCartItem(Long skuId);

	/**
	 * 获取整个购物车
	 */
	Cart getCart() throws ExecutionException, InterruptedException;

	/**
	 * 清空购物车
	 */
	void clearCart(String cartKey);

	/**
	 * 勾选购物项
	 */
	void checkItem(Long skuId, Integer check);

	/**
	 * 改变购物车中物品的数量
	 */
	void changeItemCount(Long skuId, Integer num);

	/**
	 * 删除购物项
	 */
	void deleteItem(Long skuId);

	/**
	 * 结账
	 */
	BigDecimal toTrade() throws ExecutionException, InterruptedException;

	List<CartItem> getUserCartItems();
}
