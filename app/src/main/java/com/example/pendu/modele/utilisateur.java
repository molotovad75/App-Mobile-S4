package com.example.pendu.modele;

public class utilisateur {
    private String pseudo;
    private String email;
    private String mdp;
    private int nbMotsCorrects;

    public utilisateur(){
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getEmail() {
        return email;
    }

    public String getMdp() {
        return mdp;
    }

    public int getNbMotsCorrects() {
        return nbMotsCorrects;
    }

    public void setNbMotsCorrects(int nbMotsCorrects) {
        this.nbMotsCorrects = nbMotsCorrects;
    }
}
