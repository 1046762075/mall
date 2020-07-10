package com.firenay.mall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: Catelog2Vo</p>
 * Description：
 * date：2020/6/9 14:41
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Catelog2Vo implements Serializable {

	private String id;

	private String name;

	private String catalog1Id;

	private List<Catalog3Vo> catalog3List;
}
