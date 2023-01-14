package studentManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author JJJJ
 * @Title:
 * @date 2023/1/142:25 下午
 * @Description:
 */
public class Client {

    public static void main(String[] args) {
        Manager manager = new Manager();
        while (true){
            System.out.print("1.根据学号查看学员信息     2.添加学员     3.查看所有学员信息     4.删除学员信息     5.退出");
            Scanner sc = new Scanner(System.in);
            int res;
            try {
                res = sc.nextInt();
            }catch (Exception e){
                System.out.println("输入有误 请重新输入");
                continue;
            }
            if(res == 1){
                System.out.println("请输入学号");
                int num;
                try {
                    num = new Scanner(System.in).nextInt();
                }catch (Exception e){
                    System.out.println("输入有误 请重新输入");
                    continue;
                }
                Student studentMessage = manager.getStudentMessage(num);
                System.out.println(studentMessage == null ? "该学生不存在": studentMessage);
            }else if(res == 2){
                System.out.println("请输入学号、姓名、性别 并以空格相连");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String s = null;
                try {
                    s = br.readLine();
                    String[] studentData = s.split(" ");
                    try {
                        System.out.println(manager.addStudent(Integer.parseInt(studentData[0]), studentData[1], studentData[2]));
                    }catch (Exception e){
                        System.out.println("输入的内容有误");
                        e.printStackTrace();
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(res == 3){
                ArrayList<Student> studentList = manager.getStudentList();
                if (studentList == null || studentList.size() == 0){
                    System.out.println("没有学生数据");
                }else{
                    studentList.forEach(System.out::println);
                }
            }else if(res == 4){
                System.out.println("请输入要删除的学生学号");
                int num;
                try {
                    num = new Scanner(System.in).nextInt();
                }catch (Exception e){
                    System.out.println("输入有误 请重新输入");
                    continue;
                }
                manager.deleteStudent(num);
            }else if(res == 5){
                break;
            }else {
                System.out.println("输入有误 请重新输入");
            }
        }

    }
}
