package com.zkong.sout;

public class CoolWater extends Water {

    private String hight;


    public CoolWater() {

    }

    public CoolWater(String hight) {

        this.hight=hight;
    }

    public static CoolWater getInstance(){
        return new CoolWater("1");
    }

    private  CoolWater getCoolWater(){
        return new CoolWater("2");
    }


    public void print(String hight){
        this.hight=hight;
        System.out.println( "hight="+this.hight);
    }

    public void print1(String[] hight){
        if(hight.length>0) {
            this.hight = hight[0];
        }
        System.out.println( "hight="+this.hight);
    }

    @Override
    public String toString() {
        return "CoolWater{" +
                "hight='" + hight + '\'' +
                ", a=" + a +
                ", isOpen=" + isOpen +
                '}';
    }
}
