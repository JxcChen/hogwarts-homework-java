package studentManager;

/**
 * @author JJJJ
 * @Title:
 * @date 2022/12/2612:12 下午
 * @Description: 学生类
 */
public class Student {

    private String name;
    private int number;
    private String sex;

    /**
     *
     * @param number 学号
     * @param name  姓名
     * @param sex   性别
     */
    public Student(int number,String name,String sex){
        this.number = number;
        this.name = name;
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", number=" + number +
                ", sex='" + sex + '\'' +
                '}';
    }
}
