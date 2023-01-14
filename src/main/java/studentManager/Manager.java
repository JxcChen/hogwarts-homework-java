package studentManager;

import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.util.ArrayList;

/**
 * @author JJJJ
 * @Title:
 * @date 2022/12/2612:14 下午
 * @Description: 学生管理类
 */
public class Manager {
    public static final String STUDENT_FILE = "src/main/resources/students.xlsx";
    private Workbook wb;
    private Sheet sheet;
    private FileInputStream fis;

    /**
     * 新增学生
     *
     * @param number 学号
     * @param name   姓名
     * @param sex    性别
     * @return 新增结果
     */
    public String addStudent(int number, String name, String sex) throws Exception {
        // 先判断传入的数据是否正确
        if (number <= 0) throw new Exception("学号输入有误");
        if (name.length() > 10) throw new Exception("姓名输入有误");
        if (!sex.equals("男") && !sex.equals("女")) throw new Exception("性别输入有误");
        this.openStudentFile();
        // 根据学号判断学生信息是否存在
        int res = this.isStudentExist(number);
        if (res != -1) {
            return "该学生已存在";
        }
        // 开始添加学生信息
        int curRow = this.sheet.getLastRowNum() + 1;
        Row row = this.sheet.createRow(curRow);
        Cell cell0 = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        cell0.setCellType(CellType.NUMERIC);
        cell0.setCellValue(number);
        Cell cell1 = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        cell1.setCellType(CellType.STRING);
        cell1.setCellValue(name);
        Cell cell2 = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        cell2.setCellType(CellType.STRING);
        cell2.setCellValue(sex);
        this.saveAndClose();
        return "新增成功";
    }

    /**
     * 根据学生编号删除学生信息
     *
     * @param number 学号
     * @return 删除结果
     */
    public String deleteStudent(int number){
        this.openStudentFile();
        int res = this.isStudentExist(number);
        if (res == -1) {
            return "该名学生不存在";
        }
        this.sheet.removeRow(this.sheet.getRow(res));
        this.saveAndClose();
        return "删除成功";
    }

    /**
     * 获取单个学生信息
     *
     * @param number 学生编号
     * @return 学生信息
     */
    public Student getStudentMessage(int number){
        this.openStudentFile();
        int row = this.isStudentExist(number);
        if (row == -1) return null;
        Row sheetRow = this.sheet.getRow(row);
        Student stu = new Student((int) sheetRow.getCell(0).getNumericCellValue(), sheetRow.getCell(1).getStringCellValue(), sheetRow.getCell(2).getStringCellValue());
        this.close();
        return stu;
    }

    /**
     * 获取学生列表
     * @return 学生列表
     */
    public ArrayList<Student> getStudentList() {
        this.openStudentFile();
        int lastRowNum = this.sheet.getLastRowNum();
        ArrayList<Student> stuList = new ArrayList<Student>();
        for (int i = 1; i <= lastRowNum; i++) {
            Row row = this.sheet.getRow(i);
            Student stu = new Student((int) row.getCell(0).getNumericCellValue(), row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue());
            stuList.add(stu);
        }
        this.close();
        return stuList;
    }

    /**
     * 打开学生文档
     */
    private void openStudentFile() {
        if (this.wb == null) {
            try {
                this.fis = new FileInputStream(STUDENT_FILE);
                this.wb = WorkbookFactory.create(this.fis);
                this.sheet = this.wb.getSheetAt(0);

            } catch (Exception e) {
                System.out.println("学生管理文件不存在");
            }
        }
    }

    /**
     * 判断学生是否已存在
     * @param number 学号
     * @return 学号对应的行 不存在则为-1
     */
    private int isStudentExist(int number) {
        for (int i = 1; i <= this.sheet.getLastRowNum(); i++) {
            Row row = this.sheet.getRow(i);
            if (row.getCell(0).getNumericCellValue() == number) {
                return i;
            }
        }
        return -1;
    }

    private void saveAndClose() {

        try {
            // 保存
            FileOutputStream fos = new FileOutputStream(STUDENT_FILE);
            this.wb.write(fos);
            fos.close();

        } catch (Exception e) {
            System.out.println("文件保存异常");
            e.printStackTrace();
        }
        this.close();
    }

    private void close() {
        // 保存
        try {
            if (this.wb != null) {
                this.wb.close();
            }
            if (this.fis != null) {
                this.fis.close();
            }
        } catch (Exception e) {
            System.out.println("文件关闭异常");
        }

    }

}
