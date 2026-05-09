package ui;

import domain.Character;
import domain.EnemyCharacter;
import domain.HeroCharacter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FightingGame {
    public void gameStart(String username){
        System.out.println("╔═════════════════════════════╗");
        System.out.println("🎮欢迎玩家  "+username+"  来到文字格斗游戏🎮");
        System.out.println("╚═════════════════════════════╝");
//        1.创建玩家角色
        HeroCharacter player=createPlayerCharacter(username);
        System.out.println("角色创建成功~");
        System.out.println("\uD83C\uDF1F 初始属性: ");
        player.show();
        System.out.println("\uD83C\uDF1F 拥有技能: "+player.showSkill());
//        多个敌人的集合
        ArrayList<EnemyCharacter> enemyList=new ArrayList<>();
        enemyList.add(new EnemyCharacter("初级战士",80,15,10,"猛击"));
        enemyList.add(new EnemyCharacter("敏捷刺客",60,20,5,"快速攻击"));
        enemyList.add(new EnemyCharacter("重装坦克",120,10,20,"防御姿态"));
        enemyList.add(new EnemyCharacter("神秘法师",70,25,8,"火球术"));
//        准备战斗
        int count=1;//记录和第几个敌人进行战斗
        int wins=0;//赢了几场
        while(player.isAlive()){
//            重置敌人属性点，敌人每场都回增加属性，用以增加游戏难度
            if(wins!=0){
                for (int i = 0; i < enemyList.size(); i++) {
                    EnemyCharacter c = enemyList.get(i);
                    c.maxHP+=10;
                    c.HP=c.maxHP;
                    c.attack+=3;
                    c.defense+=2;
                    c.defending=false;
                }
            }
            Random r=new Random();
            int index = r.nextInt(enemyList.size());
            EnemyCharacter enemy = enemyList.get(index);
            enemy.show();
            System.out.println("————————————————————————————");
            System.out.println("第"+count+"战斗开始⚔️，你的对手是"+enemy.name+"!");
//            第一回合
            int round=1;
            while(player.isAlive()){
                System.out.println("————————————————————————————");
                System.out.println("第"+round+"回合开始！");
                System.out.println(getHealthBar(player.name,player.HP,player.maxHP));
                System.out.println(getHealthBar(enemy.name,enemy.HP,enemy.maxHP));

            }

        }

    }
//    创建玩家角色
    public HeroCharacter createPlayerCharacter(String username){
        System.out.println("创建您的角色吧~");
        System.out.println("您的角色名字为："+username);
        int points=20;
        System.out.println("请分配属性点 (共20点):");
        System.out.println("1. 生命值 (每点+10 HP)");
        System.out.println("2. 攻击力 (每点+2 ATK)");
        System.out.println("3. 防御力 (每点+1 DEF)");
        Scanner sc=new Scanner(System.in);
        String[] attributes={"生命值","攻击力","防御力"};
        int[] values=new int[3];
        while (points>0) {
            for (int i = 0; i < attributes.length; i++) {
                if(points==0){
                    break;
                }
                System.out.println("分配点数到 "+attributes[i]+"(剩余点数: "+points+"):");
                int input=sc.nextInt();
                if(input<0){
                    System.out.println("输入无效！默认分配0点");
                    input=0;
                }
                if(input>points){
                    System.out.println("属性点不足！剩余属性点全部分配到"+attributes[i]);
                    input=points;
                }
                points=points-input;
                values[i]+=input;
            }
            if(points>0){
                System.out.println("您还有"+points+"点属性没有分配，您可以将剩余的"+"points"+"点属性再次分配~");
                System.out.println("————————————————————————————————————————————");
            }
        }
        HeroCharacter player=new HeroCharacter(username,100+values[0]*10,10+values[1]*2,values[2]);
//        添加玩家技能
        player.skillList.add("普通攻击");
        player.skillList.add("强力一击");
        player.skillList.add("生命恢复");
        return player;
    }
//    显示血条
    public String getHealthBar(String name,int HP,int maxHP){
//        满血状态下打印20个方块
        int barLength=20;
        int filled = (int)((HP * 1.0 / maxHP )*barLength);
        StringBuilder sb=new StringBuilder();
        sb.append(name).append(": [");
        for (int i = 0; i < barLength; i++) {
            if(i<filled){
                sb.append("█");
            }else sb.append(" ");
        }
        sb.append("]").append(HP).append("/").append(maxHP).append(" HP");
        return sb.toString();
    }
}
