package com.example.pendu.modele;

public class mot {

    private String chaineCar;

    public mot(String s){
        this.chaineCar = s;
    }

    public mot(){
        this.chaineCar = new String();
    }
    public String getChaineCar() {
        return chaineCar;
    }

    public void setChaineCar(String chaineCar) {
        this.chaineCar = chaineCar;
    }

}
