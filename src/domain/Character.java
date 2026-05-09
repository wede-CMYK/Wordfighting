package domain;

public class Character {
    public String name;
    public int HP;
    public int maxHP;
    public int attack;
    public int defense;
    public Character(){

    }
    public Character(String name,int HP,int attack,int defense){
        this.name=name;
        this.HP=HP;
        this.maxHP=HP;
        this.attack=attack;
        this.defense=defense;
    }
//    判断任务是否还存活
    public boolean isAlive(){
        return HP>0;
    }
//    加血
    public void heal(int amount){
        HP+=amount;
        if(HP>maxHP){
            HP=maxHP;
        }
    }
//    收到攻击
    public void takeDamage(int damage){
        HP-=damage;
        if(HP<0){
            HP=0;
        }
    }
//    展示人物属性
    public void show(){
        System.out.println(name+"的当前生命：["+HP+",攻击力："+attack+"，防御："+defense+"]");
    }
}
