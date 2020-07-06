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
        //将设备类型和taglist加载到内存中，为创建monitor做准备
        ExcelReaderUtil.parseTagConfigSheet();
//        将设备大类和名字与设备类型信息加载到内存中，并创建profile
//        ExcelReaderUtil.parseDeviceConfigSheet();
    }
}
