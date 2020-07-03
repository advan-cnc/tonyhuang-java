package com.ad.listener;

import com.ad.util.ExcelReaderUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 容器启动之后开始创建monitor
 */
@Component
public class TagMapListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ExcelReaderUtil.parseTagConfigSheet();
    }
}
