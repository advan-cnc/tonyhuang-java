package com.ad.util;

import ch.qos.logback.classic.Logger;
import com.ad.entity.MachineDTO;
import com.ad.entity.MachineTagMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ExcelReaderUtil {
    public static final String XLS = "xls";
    public static final String XLSX = "xlsx";
    private static Workbook sheets;

    static {
        Resource resource = new ClassPathResource("/ibms_config.xlsx");
        InputStream is = null;
        try {
            is = resource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            sheets = getWorkbook(is, XLSX);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 根据文件后缀名类型获取对应的工作簿对象
     * @param inputStream 读取文件的输入流
     * @param fileType 文件后缀名类型（xls或xlsx）
     * @return 包含文件数据的工作簿对象
     * @throws IOException
     */
    public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
        Workbook workbook = null;
        if (fileType.equalsIgnoreCase(XLS)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileType.equalsIgnoreCase(XLSX)) {
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }

    public static void parseTagConfigSheet(){
        System.out.println("parseTagConfigSheet start...");
        Sheet tagConfig =  sheets.getSheet("tag_config");
        if(tagConfig == null){
            throw new IllegalArgumentException("tag_config sheet 不存在！！！");
        }
        // 获取第一行数据
        check(tagConfig);
        int firstRowNum = tagConfig.getFirstRowNum();
        // 解析每一行的数据，构造数据对象
        int rowStart = firstRowNum + 1;
        int rowEnd = tagConfig.getPhysicalNumberOfRows();
        for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
            Row row = tagConfig.getRow(rowNum);
            if (null == row) {
                continue;
            }
            final short firstCellNum = row.getFirstCellNum();
            final short lastCellNum = row.getLastCellNum();
            String type = "";
            String tag;
            for(int i=firstCellNum;i<=lastCellNum;i++){
                final Cell cell1 = row.getCell(i);
                final String value = convertCellValueToString(cell1);
                if(i==1){
                    type = value;
                }
                if(i==2){
                    tag = value;
                    if (StringUtils.isEmpty(type)){
                        throw new IllegalArgumentException("tag_config sheet DeviceType 存在空行");
                    }

                    MachineTagMap.init(type,tag);
                    break;
                }
            }
        }
        System.out.println("parseTagConfigSheet end...");
        System.out.println("总共统计到设备类型：" + MachineTagMap.getCount());
    }


    private static void check(Sheet sheet){
        int firstRowNum = sheet.getFirstRowNum();
        Row firstRow = sheet.getRow(firstRowNum);
        if (null == firstRow) {
            System.out.println("解析Excel失败，在第一行没有读取到任何数据！");
        }
    }

    public static List<MachineDTO> parseDeviceConfigSheet(String targetMachineType){
        List<MachineDTO> rtv = new ArrayList<>();
        // 解析sheet
        Sheet deviceConfig =  sheets.getSheet("device_config");
        if(deviceConfig == null){
            throw new IllegalArgumentException("device_config sheet 不存在！！！");
        }
        // 获取第一行数据
        check(deviceConfig);

        // 解析每一行的数据，构造数据对象
        int firstRowNum = deviceConfig.getFirstRowNum();
        int rowStart = firstRowNum + 1;
        int rowEnd = deviceConfig.getPhysicalNumberOfRows();
        for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
            Row row = deviceConfig.getRow(rowNum);
            if (null == row) {
                continue;
            }
            MachineDTO machineDTO = convertRowToData(row,rowNum);
            //找到该设备的profileID
            final String type = machineDTO.getType();
            if(!targetMachineType.equalsIgnoreCase(type)){
                System.out.println(type + "为非指定的" + targetMachineType+"类型不处理");
                continue;
            }
            final Integer modelId = MachineUtil.getModelId(type);
            if(modelId == null){
                throw new IllegalArgumentException("设备类型" + type +"没有创建profile");
            }
            machineDTO.setModelId(modelId.intValue());
            rtv.add(machineDTO);
        }
        return rtv;
    }


    /**
     * 将单元格内容转换为字符串
     * @param cell
     * @return
     */
    private static String convertCellValueToString(Cell cell) {
        if(cell==null){
            return null;
        }
        String returnValue = null;
        switch (cell.getCellType()) {
            case NUMERIC:   //数字
                Double doubleValue = cell.getNumericCellValue();

                // 格式化科学计数法，取一位整数
                DecimalFormat df = new DecimalFormat("0");
                returnValue = df.format(doubleValue);
                break;
            case STRING:    //字符串
                returnValue = cell.getStringCellValue();
                break;
            case BOOLEAN:   //布尔
                Boolean booleanValue = cell.getBooleanCellValue();
                returnValue = booleanValue.toString();
                break;
            case BLANK:     // 空值
                break;
            case FORMULA:   // 公式
                returnValue = cell.getCellFormula();
                break;
            case ERROR:     // 故障
                break;
            default:
                break;
        }
        return returnValue;
    }


    /**
     * 提取每一行中需要的数据，构造成为一个结果数据对象
     *
     * 当该行中有单元格的数据为空或不合法时，忽略该行的数据
     *
     * @param row 行数据
     * @return 解析后的行数据对象，行数据错误时返回null
     */
    public static MachineDTO convertRowToData(Row row, int rowNum) {

        final short firstCellNum = row.getFirstCellNum();
        final short lastCellNum = row.getLastCellNum();
        final MachineDTO machineDTO = new MachineDTO();
        for(int i=firstCellNum;i<=lastCellNum;i++){
            final Cell cell1 = row.getCell(i);
            final String value = convertCellValueToString(cell1);
//            System.out.println("第" + rowNum +"行，第"+ i+"格子，值为"+ value);
            if(i==1){
                machineDTO.setName(value);
            }
            if(i==2){
                machineDTO.setType(value);
            }
        }

        return machineDTO;
    }
}
