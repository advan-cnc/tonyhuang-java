package com.ad.service.impl;

import com.ad.service.IBMSService;
import com.ad.util.ExcelReaderUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

import static com.ad.util.ExcelReaderUtil.XLSX;

@Service
public class IBMSServiceImpl implements IBMSService {
    @Override
    public void initProfile() {
        System.out.println("initProfile...");
    }

    @Override
    public void createTopo() {
        System.out.println("createTopo...");
    }

    @Override
    public void createMachine() throws IOException {
        System.out.println("start createMachine ...");
        //读取文件
        Resource resource = new ClassPathResource("/ibms_config.xlsx");
        InputStream is = resource.getInputStream();
        final Workbook sheets = ExcelReaderUtil.getWorkbook(is, XLSX);
        System.out.println(sheets);
        ExcelReaderUtil.parseExcel(sheets);
    }
}
