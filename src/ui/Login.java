package ui;
import domain.User;

import java.util.ArrayList;
import java.util.Random;
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
//        1.判断用户是否存在或者是否被禁用
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入用户名:");
        String username=sc.next();
//        是否存在
        if(!contains(list,username)){
            System.out.println("用户名"+username+"未注册！");
            return;
        }
//        是否被禁用
        int index=findIndex(list,username);
        User u=list.get(index);
        if(!u.isStatus()){
            System.out.println("用户"+u.getUsername()+"已经被禁用！请联系zwy客服处理！");
            return;
        }
        for (int i=0;i<3;i++) {
            System.out.println("请输入密码：");
            String password = sc.next();
//         验证码
            while (true) {
                String rightCode=getCode();
                System.out.println("正确的为验证码："+rightCode);
                System.out.println("请输入验证码：");
                String code = sc.next();
                if(rightCode.equalsIgnoreCase(code)){
                    break;
                }else {
                    System.out.println("验证码输入错误！再试一次吧！");
                    System.out.println("——————————————");
                }
            }
//        验证密码是否正确
            String rightPassword=u.getPassword();
            if(rightPassword.equals(password)){
                System.out.println("密码正确！登录成功，游戏启动！！！！");
                FightingGame fg=new FightingGame();
                fg.gameStart(username);
                break;
            }else {
                System.out.println("密码错误，你还有"+(2-i)+"次机会");
                if(i==2){
                    u.setStatus(false);
                    System.out.println("账户"+u.getUsername()+"已经被锁定！请联系zwy客服~");
                    return;
                }
            }
        }

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
//    找集合中username的索引
    public int findIndex(ArrayList<User> list,String name){
        for (int i = 0; i < list.size(); i++) {
            User u=list.get(i);
            if(u.getUsername().equals(name)){
                return i;
            }
        }
        return -1;
    }
    public static String getCode(){
//        获取验证码

//        第一步：将需要的字母都放进一个容器当中
        ArrayList<Character> list=new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char)('a'+i));
            list.add((char)('A'+i));
        }
//        第二步：从容器中随机获取四次字母
        StringBuilder sb=new StringBuilder();
        Random r=new Random();
        for (int i = 0; i < 4; i++) {
            int index=r.nextInt(list.size());
            Character c = list.get(index);
            sb.append(c);
        }
//        第三步：生成一个0~9的数字
        sb.append(r.nextInt(10));
//        第四步：将里面的顺序打乱，前面四个的字母其实已经是随机的了，只需将数字与前面四位交换位置即可
        char[] arr=sb.toString().toCharArray();
        int i=r.nextInt(arr.length);
        char temp=arr[i];
        arr[i]=arr[arr.length-1];
        arr[arr.length-1]=temp;
//        第五步：把数组转变为String即可
        String code=new String(arr);
        return code;
    }
}
