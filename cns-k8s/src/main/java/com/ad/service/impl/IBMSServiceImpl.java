package com.ad.service.impl;

import com.ad.entity.MachineDTO;
import com.ad.service.IBMSService;
import com.ad.util.ExcelReaderUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.ad.util.ExcelReaderUtil.XLSX;

@Service
public class IBMSServiceImpl implements IBMSService {

    @Value("${apm.protocol}")
    private String apmProtocol;


    @Value("${apm.host}")
    private String apmHost;

    @Value("${apm.machine.create-path}")
    private String createPath;

    @Override
    public void initProfile() {
        System.out.println("initProfile...");
    }

    @Override
    public void createTopo() {
        System.out.println("createTopo...");
    }

    @Override
    public void createMachine(String targetMachineType,Integer parentId, String topoName) throws IOException {
        System.out.println("start createMachine ...");
        //读取文件
        Resource resource = new ClassPathResource("/ibms_config.xlsx");
        InputStream is = resource.getInputStream();
        final Workbook sheets = ExcelReaderUtil.getWorkbook(is, XLSX);
        System.out.println(sheets);
        List<MachineDTO> machineDTOList =  ExcelReaderUtil.parseExcel(sheets,targetMachineType);
        for (MachineDTO machineDTO:machineDTOList){
            //组装json
        }
    }
}
