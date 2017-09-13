package patrickstar.com.greendaodemo;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by admin on 2017/9/11.
 * 该代码用于生成基于GreenDao所需要的类文件
 */

public class MClass {

    public static  void main(String [] args){

        /*
        Schema mySchema = new Schema(1, "com.patrickstar.greendao");
        Entity UserInfo = mySchema.addEntity("UserInfo");
        UserInfo.addStringProperty("userName").primaryKey();
        UserInfo.addStringProperty("realName");
        UserInfo.addIntProperty("age");

        try {
            //运行该句代码将生成指定实体所对应的greendao相关操作类
            new DaoGenerator().generateAll(mySchema, "/users/admin/desktop/greenDao");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */

        Schema mySchema = new Schema(1, "com.patrickstar.onetomanygreendao");
        //学生实体
        Entity Student = mySchema.addEntity("Student");
        Student.addStringProperty("stuNO").primaryKey();//添加字段同时指定为主键
        Student.addStringProperty("stuName");
        Student.addLongProperty("classId");
        //班级实体
        Entity ClassInfo = mySchema.addEntity("ClassInfo");
        ClassInfo.addLongProperty("classId").primaryKey();
        ClassInfo.addStringProperty("className");
        ClassInfo.addStringProperty("grade");
        //指定班级与学生之间的一对多关系
        //指定classId为外键关联ClassInfo
        Student.addToOne(ClassInfo,Student.getProperties().get(2));
        //指定一个班级中存在多个学生
        ClassInfo.addToMany(Student, Student.getProperties().get(2)).setName("stuList");

        try {
            //运行该句代码将生成指定实体所对应的greendao相关操作类
            new DaoGenerator().generateAll(mySchema, "/users/admin/desktop/greenDao/ontomany");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
