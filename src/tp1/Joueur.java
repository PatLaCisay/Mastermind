/*
 BARREAU Lucas ANDRE Gaetan

Classes TP_final

14/11/2020

Ce fichier comporte l'ensemble de nos methodes s'executant dans le script principal.
Elles sont classes en fonction du joueur avec lequel elles interagissent.

Bonne lecture !


 */
package tp1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner ;
import java.util.Random;

public class Joueur {
    private String pseudo; //pseudo du joueur
    private Boolean role; //si vrai role est le challenger
    
    private List<List> props=new ArrayList<List>() ; //liste des propositions de code du joueur sous forme de liste
    private List<Integer> code=new ArrayList<Integer>() ; //code du joueur sous forme de liste d'entier
    private List<Integer> deja_dit=new ArrayList<Integer>() ; //liste de stockage des chiffres deja proposes par l'odri pour deviner le code
    private List<Integer> deja_dit2=new ArrayList<Integer>() ; //liste de stockage des chiffres deja proposes par l'odri pour deviner le code
    private List<Integer> deja_trouve=new ArrayList<Integer>() ; //deuxieme liste de stockage des chiffres presents dans le code
    private List<String> paroles = Arrays.asList("Ca marche.", "OK !", "Ahaaaah !","Tiens, tiens, tiens...","Hmmmmmm...","Je le savais...","Ah bon ?!","Mais non ?? :O","Soit."); //Array de str pour rendre l'ordi plus "vivant"
    private int score=0; //score du joueur
    private String role_string; //role du joueur sous forme de str : "cadenas" ou "hacker"
    private Scanner input=new Scanner(System.in); //scanner utile dans le script
    private Random rand=new Random(); //generateur d'entier aleatoire
    
    //CONSTRUCTEURS
    
    public Joueur(){//ordi
        //Construit le joueur Ordi
        this.role=false; //l'ordi commence comme cadenas
        this.pseudo="Ordinateur";
        this.score=0;
        this.props.clear();
        this.code.clear();
        this.paroles=paroles;
        this.deja_dit.clear();
        this.deja_dit2.clear();
        this.deja_trouve.clear();
    }
    
    public Joueur(String pseudo){//joueur l'argument est son pseudo
       //Construit le joueur Utilisateur
       
       this.role=true;
       this.pseudo=pseudo;
       this.score=0;
       this.props.clear();
       this.code.clear();
    }
    
//METHODES

    //Independantes des deux joueurs
    boolean wichRole() { //retourne le role associe a un joueur
        return this.role;
    }

    int getScore(){ //retourne le score associe a un joueur
        return this.score;
    }   

    public String toString(){ //sert a afficher qui est quoi en retournant une chaine de caractere
        if (this.role){
            role_string="Hacker";
        }else{
            role_string="Cadenas";
        }

        return this.pseudo+" est "+role_string;
    }

    int incrScore(List<List>props){ //incremente le score associe au joueur. Entree --> liste des props du joueurs Sortie --> entier
        this.score+=props.size();
        return this.score;
    }

    public static void miseEnPage(){ //procedure qui sert a ameliorer le visuel du jeu dans la console
        System.out.println();
        System.out.println("------------------------------------------------");
        System.out.println();
    }

    public void finManche(){ //procedure qui affiche le message de fin de manche
        miseEnPage();
        System.out.println("*************************************");
        System.out.println("Cadenas ouvert !!");
        System.out.println("*************************************");
        System.out.println();
        System.out.println("Le code était en effet: "+this.code+", bien joué !"); //affiche le code associe a la manche
        this.score=incrScore(this.props); //incremente le score associe au cadenas
        System.out.println("Le cadenas gagne : "+this.props.size()+" points."); //affiche le nombre de points gagnes par le cadenas
        affichProp(); //affiche les propositions du hacker
    }

    public void affichProp(){ //affiche les props du Hacker.
        miseEnPage();
        System.out.println("Les propositions du Hacker étaient :");
        System.out.println("");
        System.out.println(this.props);
        miseEnPage();
    }

    public void modifRole(){ //modifie le role du joueur
        if (role==true){
            role=false;
        }else{
            role=true;
        }
    }

    public void finDePartie(int[] score,String pseudo){ //affiche fin
        String gagnant; //initialise un str qui correspondra au nom du gagnant
        miseEnPage();
        System.out.println("Fin de partie !");
        System.out.println("");
        System.out.println("Le score de "+pseudo+" est : "+score[1]); //affiche le score de l'utilisateur
        System.out.println("");
        System.out.println("Le score de l'ordinateur est : "+score[0]); //affiche le score de l'ordi
        System.out.println("");
        if (score[1]>score[0]){ //deduis le gagnant
            gagnant=pseudo;
            System.out.println("Le gagnant est : "+gagnant); //affiche le pseudo du gagnant
        }else if(score[1]<score[0]){
            gagnant="Ordinateur";
            System.out.println("Le gagnant est : "+gagnant);
        }else{
            System.out.println("Egalité ! ");
        }
        
        System.out.println("Bien joué !");
    }

    //Pour le tour de l'utilisateur
    public void creerCode(int nbr_chiffre){ //cree le code de l'ordi. Entree--> entier nombre n de chiffre du code
        this.props.clear(); //reintialise les propositions et le code
        this.code.clear();
        for (int i=0;i<nbr_chiffre;i++){this.code.add(0);} //cree une liste de n 0   
        for (int i=0;i<nbr_chiffre;i++){
            this.code.set(i,rand.nextInt(10)); //assigne au i-ieme terme un entier aleatoire
            for(int j=0;j<i;j++){
                if (this.code.get(i)==this.code.get(j)){ //test si cet entier est deja dans le code (INTERDIT)
                    i--; //retire 1 a i donc repete la boucle pour le terme jusqu'a ce qu'il soit valide
                    break; //arrete la seconde boucle pour permettre a la premiere de fonctionner
                }
            }
        }
    }

    public void saisirProp(int nbr_chiffre){ //sert a rentrer une prop. Entree--> nombre n de chiffre dans le code
         List<Integer> new_prop=new ArrayList<Integer>() ; //initialise une liste d'entier vide
         System.out.println("Rentrez une proposition de code.");
         for (int i=0;i<nbr_chiffre;i++){
             System.out.println("Rentrez le chiffre numéro: "+(i+1));
             new_prop.add(input.nextInt()); //le joueur rentre un entier
             while (new_prop.get(i)>9 || new_prop.get(i)<0){ //tant que le joueur rentre un mauvais chiffre, il est invite a recommencer
                System.out.println("Veuillez rentrer un entier entre 0 et 9.");
                new_prop.set(i,input.nextInt()); //l'entier errone est modifie
             }
         }
         this.props.add(new_prop); //ajoute la nouvelle proposition a la liste des autres
         System.out.println(this.props.get(this.props.size()-1)); //affiche la derniere proposition du joueur
    }

    public boolean verifPlaces(){ //procedure compte le nombre de bon chiffres
        int bien_place=0; //entier comptant le nombre de chiffre bien place
        int dedans=0; //entier comptant le nombre de chiffre dedans mais mal places
        for (int i=0;i<this.code.size();i++){
            if (this.code.get(i)==this.props.get(this.props.size()-1).get(i)){ //si le i-eme chiffre du code est egal au i-eme chiffre de la derniere proposition
                bien_place+=1; //on incremente bien_place de 1
            }
            for (int j=0;j<this.code.size();j++){
                if (this.code.get(i)==this.props.get(this.props.size()-1).get(j)) //si le i-eme chiffre du code est egal au j-eme chiffre de la derniere proposition
                    dedans+=1; //on incremente dedans de 1
            }
        }
        if (bien_place==this.code.size()){ //s'il y a autant de chiffre bien place que de chiffre dans le code 
            return true; //c'est gagne !
        }else{ //sinon on affiche les infos sur les chiffres de la prop
            System.out.println("Il y a "+dedans+" chiffre(s) bons dont "+bien_place+" chiffre(s) bien placé.");
            return false; //c'est pas encore gagne !
        }    
    }

    //Pour le tour de l'ordi

    public void propOrdi(int compt_dedans,int compt_bon, int[] bien_place,int nbr_chiffre){ //methode de proposition de l'ordinateur Entrees--> Liste de n 0 / entier qui correspond a l'indice du chiffre recherche / entier qui correspond au nombre de chiffre bien place dans la prop precedente
        List<Integer> new_prop=new ArrayList<Integer>() ; //initialise une liste d'entier vide
        int chiffre; //initialise un entier qui va servir a definir les termes de la prop
        int chiffre_out;
        
        
        //L'ordi va chercher tous les termes presents dans le code :
        if (compt_bon==0){
            if (bien_place[0]>0 & (int)this.deja_dit.size()>0){ //si le chiffre rentre precedement est dans le code et qu'on est pas encore a placer les chiffres dans l'ordre :
                this.deja_trouve.add((int) this.props.get(props.size()-1).get(0)); //on l'ajoute a la liste deja_trouve
            }
            if (this.deja_trouve.size()<nbr_chiffre){ //si on a pas trouve tous les chiffres du code
                chiffre=rand.nextInt(10); //genere un chiffre random entre 0 et 9
                while(this.deja_dit.contains(chiffre)){ 
                    chiffre=rand.nextInt(10); //on le change tant qu'on l'a deja dit 
                }
                this.deja_dit.add(chiffre); //on l'ajoute a deja_dit si on l'avait pas deja dit
                for (int i=0; i<nbr_chiffre;i++){ //on modifie la liste new_prop pour creer une liste de nbr_chiffre chiffre--> designer precedemment
                    new_prop.add(chiffre);
                }
            }
       }
        if ((int)this.deja_trouve.size()>=nbr_chiffre || this.deja_dit.size()==0){
        //L'ordi va placer les termes qu'il a trouve un par un :
            new_prop.clear();
            chiffre=this.deja_trouve.get(rand.nextInt(this.deja_trouve.size()));//le chiffre rentre par l'ordi est aleatoire
            while (this.deja_dit2.contains(chiffre)){// si chiffre est deja dans le code
                chiffre=this.deja_trouve.get(rand.nextInt(this.deja_trouve.size()));//on lui reatribue un nombre
            }
            this.deja_dit2.add(chiffre); //on ajoute le chiffre que l'on vient de rentrer dans une liste pour savoir si on l'a deja dit
            if (compt_bon==0){ //pour deviner si le premier chiffre est bon on veut que tous les uatres soient faux
                chiffre_out=rand.nextInt(10);
                while (this.deja_trouve.contains(chiffre_out)){chiffre_out=rand.nextInt(10);} //cree un nombre dont le chiffre n'est pas dans le code
                for (int i=0; i<nbr_chiffre; i++){new_prop.add(chiffre_out);} //cree une proposition forcemment fausse
            }else{
                new_prop=this.props.get(this.props.size()-1); //recupere la derniere proposition a avoir ete faite
            }
            new_prop.set(compt_bon,chiffre);
        }
        this.props.add(new_prop);
        miseEnPage();
        System.out.println("ordi : 'Je pense que votre code est :'");        
        System.out.println(new_prop);
    }

    public void saisirCode(int nbr_chiffre){ //utilisateur rentre le code que l'ordi devra deviner Entree--> nombre de chiffre dans le code
         this.props.clear(); //reinitialise la liste de proposition et le code
         this.code.clear();
         this.deja_trouve.clear();
         this.deja_dit.clear();
         System.out.println("Rentrez votre code secret.");
         miseEnPage();
         for (int i=0;i<nbr_chiffre;i++){this.code.add(0);} //cree une liste de n 0
         
         for (int i=0;i<nbr_chiffre;i++){
             System.out.println("Rentrez le chiffre numéro: "+(i+1));
             this.code.set(i,input.nextInt()); //l'utilisateur rentre le i-eme chiffre
             
             //Gestion des erreurs comme precedemment
             while (this.code.get(i)>9 || this.code.get(i)<0){
                 System.out.println("Rentrez un ENTIER compris entre 0 et 9, merci.");
                 this.code.set(i,input.nextInt());
             }
             if (i>0){
                for (int j=0 ; j<i; j++){
                    if (this.code.get(i)==this.code.get(j)){
                        System.out.println("Ne rentrez pas deux fois le meme chiffre.");
                        i--;
                        break;
                    }
                }
            }
         }
        miseEnPage();
        System.out.println("Le code que l'ordi doit deviner est :"+this.code); //affiche le code final que l'ordi doit trouver
    }
    
    public void reinitDejaVu(int compt_bon){ //Entree-->compteur de chiffre bien places
    //cette methode sert a faciliter la decouverte du code final
    this.deja_dit.clear(); //cela fait sauter la condition du premier if de propOrdi empechant le code de tourner en rond
    this.deja_dit2.clear();
    for (int i=0;i<this.deja_trouve.size();i++){ //on retire le terme bien place de deja_trouve pour optimiser la decouverte du code
        if (this.deja_trouve.get(i)==this.props.get(this.props.size()-1).get(compt_bon)){
            this.deja_trouve.remove(i);
            break;
        }
    }
}
    
    public int[] saisirPlaces(){ //l'ordi demande au joueur combien de chiffre il a de bon et de bien place Sortie--> Array de deux entiers contenant ces infos
        int[] resultat=new int[2]; //initialise un array de deux entier pour stocker les infos
        miseEnPage();

        System.out.println("ordi : 'Combien de chiffre j'ai bien choisi mais mal place ?'");
        resultat[0]=input.nextInt();
        System.out.println("ordi : "+"'"+this.paroles.get(rand.nextInt(this.paroles.size()))+"'"); //genere une reponse parmis la liste predefinie
        System.out.println("");
        System.out.println("ordi : 'Combien de chiffre j'ai bien place ?'");
        resultat[1]=input.nextInt();
        System.out.println("ordi : "+"'"+this.paroles.get(rand.nextInt(this.paroles.size()))+"'"); 
        miseEnPage();

        return resultat; //retourne le array contenant les infos sur les chiffres du code
    } 
}