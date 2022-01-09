/*
BARREAU Lucas ANDRE Gaetan

Fichier principal du TP. Il contient le script permettant aux joueurs de 
s'affronter dans une partie.

L'esemble des commentaires est redige sans accents pour eviter les problemes
d'encodage, veuillez excuser les fautes engendrees.

Bonne lecture.

Séance1

21/10/2020 */
package tp1;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;



public class TP1 {
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        
        int compt_dedans; //compte le nombre de fois ou un bon chiffre est rentre par l'ordi
        int compt_bon;  //compte le nombre de fois ou un chiffre est bien place par l'ordi
        int nbr_chiffre; //nbr de chiffre du code
        int nbr_partie; //nbr de manches que le joueur veut faire
        String pseudo; //pseudo de l'utilisateur
        boolean gagne; //definie si le joueur a gagne ou non la manche
        Random rand=new Random(); //generateur d'entier aleatoire
        
        
        miseEnPage();
        System.out.println("Veuillez entrer votre pseudo");
        
        //construction des deux joueurs
        pseudo=input.nextLine();
        Joueur ordi=new Joueur();
        Joueur joueur=new Joueur(pseudo);
        
        miseEnPage();
        System.out.println("Combien de manches voulez-vous jouer ?");
        nbr_partie=input.nextInt(); //induction du nombre de manche
        while(nbr_partie<=0){
            System.out.println("Rentrez un nombre entier positif autre que 0 svp.");
            nbr_partie=input.nextInt();
        }
        System.out.println("Ok.");
        miseEnPage();
        for (int i=0;i<nbr_partie;i++){ //boucle de jeu --> s'arrete quand toutes les manches ont ete jouees

            miseEnPage();
            System.out.println(joueur);
            System.out.println("");
            System.out.println("Versus :");
            System.out.println("");
            System.out.println(ordi);
            miseEnPage();               

            if (joueur.wichRole()){ //round ou le joueur devine le code
                
                System.out.println("Combien de chiffre voulez vous deviner "+pseudo+" ?");
                nbr_chiffre=input.nextInt(); //le joueur rentre le nombre de chiffre qu'il veut deviner
                while (nbr_chiffre>=10 || nbr_chiffre<=0){
                    System.out.println("Le nombre de chiffre du code ne peut contenir que 10 chiffres differents max et 1 chiffre min. Rentrez une valeur fonctionnelle, merci.");
                    nbr_chiffre=input.nextInt(); //le joueur est invite a entrer une valeur correct
                }
                
                joueur.creerCode(nbr_chiffre);//cree un code aleatoire
                
                for(int j=0;j<10;j++){ //boucle de manche --> prend fin quand le code est trouve ou que le nombre de proposition depasse 10
                    joueur.saisirProp(nbr_chiffre); //le joueur rentre sa proposition de code
                    gagne=joueur.verifPlaces();// vrai si tout est bien place faux --> donne le nombre de bien place et mal place
                    if (gagne||j==9){
                        joueur.finManche();//augmente le score de l'ordi en fonction du nombre de props du joueur
                        joueur.modifRole();//echange des roles
                        ordi.modifRole();
                        break;
                    }
                }
            }else{//round ou l'ordi devine le code
                compt_dedans=0; //on reinitialise les compteurs
                compt_bon=0;
                System.out.println("Combien de chiffre voulez vous deviner, ordinateur ?");
                nbr_chiffre=rand.nextInt(5); //le nombre de chiffre est defini aleatoirement par l'ordi on bride a 4 pour plus de jouabilite
                while (nbr_chiffre==0){                    
                    nbr_chiffre=rand.nextInt(5); //on empeche que nbr_chiffre soit nul !
                }
                System.out.println("ordi : 'Je veux deviner "+nbr_chiffre+" chiffres !'");
                ordi.saisirCode(nbr_chiffre); //le joueur entre le code que l'ordi doit deviner
                int [] test=new int[2]; //on initialise un array qui contiendra le nombre de chiffre bien place et bon
                for(int j=0;j<10;j++){ //boucle de manche --> prend fin quand le code est trouve ou que le nombre de proposition depasse 10

                    ordi.propOrdi( compt_dedans,compt_bon, test,nbr_chiffre); //l'ordi propose quelquechose
                    test=ordi.saisirPlaces(); //test prend le retour de saisirPlaces
                    if (test[0]>0){ //ce compteur augmente de 1 si au moins 1 chiffre est bien choisi
                        compt_dedans++;
                    }
                    if (compt_dedans>nbr_chiffre & test[1]>compt_bon){
                        ordi.reinitDejaVu(compt_bon);
                        compt_bon++;
                        
                    }
                    if (test[1]==nbr_chiffre || j==9){ //victoire si le nombre de chiffre bien place est egal au nombre de chiffre du code
                        ordi.finManche();
                        joueur.modifRole(); //on change les roles des joueurs
                        ordi.modifRole();    
                        break;
                    }
                }                
            }
            if (i==nbr_partie-1){ //quand toutes les manches sont revolues
                int[] score_stock=new int[2]; //on cree un array stockant le score des deux joueurs
                score_stock[1]=ordi.getScore();
                score_stock[0]=joueur.getScore();
                ordi.finDePartie(score_stock,pseudo); //on affiche le message de fin
            }
        }
    }

    public static void miseEnPage(){ //sert a rendre le code plus joli dans la console
        System.out.println();
        System.out.println("------------------------------------------------");
        System.out.println();
    }
}