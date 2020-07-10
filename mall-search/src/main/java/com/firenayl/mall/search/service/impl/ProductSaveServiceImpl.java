package com.firenayl.mall.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.firenay.common.to.es.SkuEsModel;
import com.firenayl.mall.search.config.MallElasticSearchConfig;
import com.firenayl.mall.search.constant.EsConstant;
import com.firenayl.mall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Title: ProductSaveServiceImpl</p>
 * Description：
 * date：2020/6/8 21:16
 */
@Slf4j
@Service
public class ProductSaveServiceImpl implements ProductSaveService {

	@Resource
	private RestHighLevelClient client;

	/**
	 * 将数据保存到ES
	 * BulkRequest bulkRequest, RequestOptions options
	 */
	@Override
	public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {
		// 1.给ES建立一个索引 product
		BulkRequest bulkRequest = new BulkRequest();
		// 2.构造保存请求
		for (SkuEsModel esModel : skuEsModels) {
			IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
			// 设置索引id
			indexRequest.id(esModel.getSkuId().toString());
			String jsonString = JSON.toJSONString(esModel);
			indexRequest.source(jsonString, XContentType.JSON);
			bulkRequest.add(indexRequest);
		}
		BulkResponse bulk = client.bulk(bulkRequest, MallElasticSearchConfig.COMMON_OPTIONS);
		// TODO 是否拥有错误
		boolean hasFailures = bulk.hasFailures();
		if(hasFailures){
			List<String> collect = Arrays.stream(bulk.getItems()).map(item -> item.getId()).collect(Collectors.toList());
			log.error("商品上架错误：{}",collect);
		}
		return hasFailures;
	}
}
