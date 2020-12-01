package com.artcweb.listener;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.artcweb.baen.NailConfig;
import com.artcweb.baen.NailDetailConfig;
import com.artcweb.cache.DateMap;
import com.artcweb.service.NailConfigService;
import com.artcweb.service.NailDetailConfigService;


@Component
public class GlobalListener implements ApplicationListener<ApplicationEvent> {
	public static final Logger logger = LoggerFactory.getLogger(GlobalListener.class);
	
	@Autowired
	NailDetailConfigService nailDetailConfigService;
	@Autowired
	NailConfigService nailConfigService;

	@Override
	public void onApplicationEvent(ApplicationEvent applicationEvent) {
		if (applicationEvent instanceof ContextRefreshedEvent && ((ContextRefreshedEvent) applicationEvent).getApplicationContext().getParent() == null) {
			logger.info("初始化数据开始----------");
			
			Map<String,Object> paramMap = null;
			
			// 初始化
			List<NailDetailConfig> nailDetailConfigList = nailDetailConfigService.select(paramMap);
			DateMap.initNailDetailConfigMap(nailDetailConfigList);
			
			List<NailConfig> nailConfigList = nailConfigService.select(paramMap);
			DateMap.initNailConfigMap(nailConfigList);
		
			logger.info("初始化数据结束----------");
		}
	}
}