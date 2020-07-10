package com.firenay.common.constant;

/**
 * <p>Title: ProductConstant</p>
 * Description：给销售属性用来区分保存与修改  根据情况看是否需要保存关联属性
 * date：2020/6/2 23:45
 */
public class ProductConstant {

	public enum AttrEnum {
		ATTR_TYPE_BASE(1, "基本属性"), ATTR_TYPE_SALE(0, "销售属性");

		private int code;

		private String msg;

		AttrEnum(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public int getCode() {
			return code;
		}

		public String getMsg() {
			return msg;
		}
	}

	public enum StatusEnum {
		SPU_NEW(0, "新建"), SPU_UP(1, "上架"), SPU_DOWN(2, "下架");

		private int code;

		private String msg;

		StatusEnum(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public int getCode() {
			return code;
		}

		public String getMsg() {
			return msg;
		}
	}
}
