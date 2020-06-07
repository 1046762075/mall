package com.firenay.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.firenay.common.utils.PageUtils;
import com.firenay.common.utils.Query;
import com.firenay.mall.product.dao.SpuImagesDao;
import com.firenay.mall.product.entity.SpuImagesEntity;
import com.firenay.mall.product.service.SpuImagesService;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuImagesService")
public class SpuImagesServiceImpl extends ServiceImpl<SpuImagesDao, SpuImagesEntity> implements SpuImagesService {

	private Logger logger = Logger.getLogger(SpuImagesServiceImpl.class);

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
		IPage<SpuImagesEntity> page = this.page(
                new Query<SpuImagesEntity>().getPage(params),
                new QueryWrapper<SpuImagesEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public void saveImages(Long id, List<String> images) {
		if(images == null || images.size() == 0){
			logger.warn("图片为空");
		}else{
			// 保存所有图片
			List<SpuImagesEntity> collect = images.stream().map(img -> {
				SpuImagesEntity imagesEntity = new SpuImagesEntity();
				imagesEntity.setSpuId(id);
				imagesEntity.setImgUrl(img);

				return imagesEntity;
			}).collect(Collectors.toList());
			this.saveBatch(collect);
		}
	}

}