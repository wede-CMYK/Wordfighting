package ui;

import domain.Character;
import domain.EnemyCharacter;
import domain.HeroCharacter;

import java.sql.SQLOutput;
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
//                玩家回合
                playerTurn(player,enemy);
                if(!enemy.isAlive()){
                    System.out.println("恭喜你击败了 "+enemy.name+" !");
                    wins++;
                    break;
                }
//                敌人回合
                enemyTurn(enemy,player);
                if(!player.isAlive()){
                    System.out.println("💀你被 "+enemy.name+" 击败了");
                    break;
                }
                round++;
            }
            if(player.isAlive()){
                int healHP = r.nextInt(21)+20;
                player.heal(healHP);
                System.out.println("❤战斗结束，恭喜你恢复了"+healHP+"点生命值");
                System.out.println("🏆当前胜场："+wins);
                System.out.println("————————————————————");
                wins++;
                if(wins%3==0){
                    System.out.println("⭐恭喜你！胜场积累足够，获得了属性提升！");
                    player.maxHP+=30;
                    player.attack+=5;
                    player.defense+=3;
                    System.out.println("你的属性为：");
                    player.show();
                }
                System.out.println("是否继续迎接下一个更强的对手！(y/n)");
                Scanner sc=new Scanner(System.in);
                String choose=sc.next();
                if("y".equalsIgnoreCase(choose)){
                    count++;
                }else if("n".equalsIgnoreCase(choose)){
                    break;
                }else {
                    System.out.println("没有这个选项，游戏继续~");
                    count++;
                }
            }
        }
//        最终结算
        System.out.println("——————————————————————————————————");
        System.out.println("游戏结束！！！！！！");
        System.out.println("总胜场："+wins);
        System.out.println("感谢游玩文字版格斗游戏！！！我是致未远！！");
        System.exit(0);
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
//    玩家回合选择行动
    public void playerTurn(HeroCharacter player,EnemyCharacter enemy){
        System.out.println("——————你的回合——————");
        System.out.println("1.普通攻击");
        System.out.println("2.强力一击");
        System.out.println("3.生命汲取");
        Scanner sc=new Scanner(System.in);
        String choose = sc.next();
        switch(choose){
            default:
                System.out.println("无效操作，默认使用普通攻击");
            case "1":
                int damage = calculateDamage(player.attack, enemy.defense);
                System.out.println("⚔️你对 "+enemy.name+" 使用普通攻击，造成了"+damage+"点伤害！");
                enemy.takeDamage(damage);
                break;
            case "2":
                if(player.HP>10){
                    player.takeDamage(10);
                    int damage1 = calculateDamage((int) (player.attack * 1.8), enemy.defense);
                    System.out.println("💥消耗10HP，你对 "+enemy.name+" 使用了强力一击，造成 "+damage1+" 点伤害！");
                    enemy.takeDamage(damage1);
                }else System.out.println("生命值不足十点！攻击失败");
                break;
            case "3":
                if(player.HP>10){
                    player.takeDamage(10);
                    Random r=new Random();
                    int healHP = r.nextInt(21);
                    player.heal(healHP);
                    System.out.println("💚消耗十点生命值，回复了 "+healHP+"点生命~");
                }else System.out.println("生命值不足十点！回复失败");
        }
    }
//    敌人回合
    public void enemyTurn(EnemyCharacter enmey,HeroCharacter player){
        System.out.println("——————"+enmey.name+"的回合——————");
        String action="普通攻击";
        Random r=new Random(10);
        int num = r.nextInt(10);
        if(num>5){
            action=enmey.skill;
        }
        switch (action){
            case "普通攻击":
                System.out.println(enmey.name+" 采取了 "+action);
                int damage1 = calculateDamage(enmey.attack, player.defense);
                System.out.println("💥"+enmey.name+" 对 你 使用普通攻击，造成了"+damage1+"点伤害！");
                player.takeDamage(damage1);
                break;
            case "猛击":
                System.out.println(enmey.name+" 采取了 "+action);
                int damage2 = calculateDamage((int) (enmey.attack * 1.5), player.defense);
                System.out.println("💥"+enmey.name+"对 你 使用了 "+action+"，造成 "+damage2+" 点伤害！");
                player.takeDamage(damage2);
                break;
            case "快速攻击":
                System.out.println(enmey.name+" 采取了 "+action);
                int damage3=0;
                for (int i=0;i<2;i++) {
                    int temp = calculateDamage(enmey.attack/2, player.defense);
                    damage3+=temp;
                }
                System.out.println("💥"+enmey.name+"对 你 使用了 "+action+"，造成 "+damage3+" 点伤害！");
                player.takeDamage(damage3);
                break;
            case "防御姿态":
                System.out.println(enmey.name+" 采取了 "+action);
                enmey.defending=true;
                System.out.println("🛡️"+enmey.name+" 摆出了防御姿态！");
                break;
            case "火球术":
                System.out.println(enmey.name+" 采取了 "+action);
                int damage4 = calculateDamage((int) (enmey.attack * 1.8), player.defense);
                System.out.println("🔥"+enmey.name+"对你使用了火球术，造成 "+damage4+"点伤害！");
                player.takeDamage(damage4);
                break;
        }
    }
//    用来计算双方战斗的时候，对对方造成的伤害
    public int calculateDamage(int attack,int defense){
        return attack>defense?attack-defense:1;
    }
}
