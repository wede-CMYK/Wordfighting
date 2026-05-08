package ui;
import domain.User;

import java.util.ArrayList;
import java.util.Scanner;
public class Login {
//    登录注册的主页面
    public void start(){

        System.out.println("游戏的登录注册页面打开了！");
        ArrayList<User> list=new ArrayList<>();
        while (true) {
            System.out.println("╔════════════════════════════════╗");
            System.out.println("    🎮 欢迎来到文字格斗游戏 🎮   ");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("请选择操作：1登录 2注册 3退出");
            Scanner sc = new Scanner(System.in);
            String choose = sc.next();
            switch (choose) {
                case "1":
                    login(list);
                    break;
                case "2":
                    register(list);
                    break;
                case "3": {
                    System.out.println("用户选择了退出操作");
                    System.exit(0);
                }
                break;
                default:
                    System.out.println("输入有误，请重新输入！");
            }
        }
    }
//    登录操作
    public void login(ArrayList<User> list){
        System.out.println("用户选择了登录操作");
    }
//    注册操作
    public void register(ArrayList<User> list){
        System.out.println("用户选择了注册操作");
        User u=new User();
        Scanner sc=new Scanner(System.in);
//        注册用户名
        while (true) {
            System.out.println("请输入用户名~");
            String username = sc.next();
//            名字要满足长度3~16个字符
            if(!checkLength(3,16,username)){
                System.out.println("用户名的长度不符合要求！长度必须是3~16个字符！");
                continue;
            }
//            名字要满足可以由数字和字母，但是不能是纯数字
            if(!checkUsername(username)){
                System.out.println("用户名只能是字母加数字且不能是纯数字！");
                continue;
            }
//            名字要满足唯一性，不能是已经存在的名字
            if(contains(list,username)){
                System.out.println("用户名已经存在~");
                continue;
            }
            System.out.println("用户名创建成功~");
            u.setUsername(username);
            break;
        }
//        注册用户密码
        while (true) {
            System.out.println("请输入您的密码：");
            String password1 = sc.next();
            if(!checkLength(3,8,password1)){
                System.out.println("密码长度不符合长度，请重新输入！");
                continue;
            }
            if(!checkpass(password1)){
                System.out.println("密码格式只能是字母加数字，请重新输入！");
                continue;
            }
            System.out.println("请再次输入密码确认：");
            String password2=sc.next();
            if(!password2.equals(password1)){
                System.out.println("两次密码不一致，请重新输入！");
                continue;
            }
            System.out.println("密码设置成功！");
            u.setPassword(password2);
            break;
        }
        list.add(u);
        System.out.println("用户"+u.getUsername()+"注册成功！");
        System.out.println("请前往登录~");
    }
//    判断长度是否在文档要求之内
    public boolean checkLength(int minLen,int maxLen,String s){
        return s.length()>=minLen&&s.length()<=maxLen;
    }
//    判断用户名的内容是否在文档要求之内
    public boolean checkUsername(String name){
        int ch=0,other=0;
        for(int i=0;i<name.length();i++){
            char temp=name.charAt(i);
            if(Character.isLetter(temp)){
                ch++;
            }else if(!Character.isDigit(temp)){
                return false;
            }
        }
        return ch>0;
    }
//    判断用户名是否已经存在
    public boolean contains(ArrayList<User> list,String name){
        for(int i=0;i<list.size();i++){
            User u=list.get(i);
            if(u.getUsername().equals(name)){
                return true;
            }
        }
        return false;
    }
//    判断密码是否符合文档要求
    public boolean checkpass(String pass){
        for(int i=0;i<pass.length();i++){
            char temp=pass.charAt(i);
            if(!Character.isLetter(temp)&&!Character.isDigit(temp)){
                return false;
            }
        }
        return true;
    }
}
