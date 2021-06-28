package question2;

import question1.PilePleineException;
import question1.PileVideException;

import java.util.Stack;
import java.util.stream.Collectors;

public class Pile4 implements PileI, Cloneable {
    /** la liste des Maillons/Elements */
    private Maillon stk;
    /** la capacité de la pile */
    private int capacite;
    /** le nombre */
    private int nombre;

    /**
     * Classe interne "statique" contenant chaque élément de la chaine c'est une
     * proposition, vous pouvez l'ignorer !
     */
    private static class Maillon implements Cloneable {
        private Object element;
        private Maillon suivant;

        public Maillon(Object element, Maillon suivant) {
             this.element = element;
            this.suivant = suivant;
        }

        public Maillon suivant() {
            return this.suivant;
        }

        public Object element() {
            return this.element;
        }

        public Object clone() throws CloneNotSupportedException {
            Maillon m = (Maillon) super.clone();
            m.element = element;
            return m;
        }
    }

    /**
     * Création d'une pile.
     * 
     * @param taille
     *            la taille de la pile, la taille doit être > 0
     */
    public Pile4(int taille) {
         if (taille <= 0)
            taille = CAPACITE_PAR_DEFAUT;
        this.stk = null;
        this.capacite = taille;
        this.nombre=0;
    }

     public Pile4() {
        this(PileI.CAPACITE_PAR_DEFAUT);
     }

    public void empiler(Object o) throws PilePleineException {
        if (estPleine())
            throw new PilePleineException();
       Maillon maillon =new Maillon(o,stk);
       stk=maillon;
       nombre++;
    }

    public Object depiler() throws PileVideException {
        if (estVide())
            throw new PileVideException();
        Maillon res=stk;
        stk=stk.suivant();
        nombre--;
        return res.element();
    }

    public Object sommet() throws PileVideException {
        if (estVide())
            throw new PileVideException();
        return stk.element(); 
    }

    /**
     * Effectue un test de l'état de la pile.
     * 
     * @return vrai si la pile est vide, faux autrement
     */
    public boolean estVide() {
        return stk==null; 
    }

    /**
     * Effectue un test de l'état de la pile.
     * 
     * @return vrai si la pile est pleine, faux autrement
     */
    public boolean estPleine() {
        return nombre==capacite; 
    }

    /**
     * Retourne une représentation en String d'une pile, contenant la
     * représentation en String de chaque élément.
     * 
     * @return une représentation en String d'une pile
     */
    public String toString(){ 
        StringBuffer sb = new StringBuffer("[");
        Maillon x=stk;//pointeur node
        Maillon y=stk;//pointeur node
        while(x!=null){
            sb.append(x.element());
            x=x.suivant;
            y=y.suivant();
            if(y!=null){
            sb.append(", ");}
        }
        sb.append("]");
        return sb.toString();
       }
       

    /*
     * didn't work in question2.5
     * public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o instanceof Pile4) {return true;}
        Pile4 pile= (Pile4)o;
        if (this.capacite != pile.capacite){return false;}
        if (this.nombre != pile.nombre){return false;}
        
        for (int i=0;i<nombre;i++){
            if ( stk!=pile.stk )return false;
        } 
        return true;
    }*/
     public boolean equals(Object object){
        if(object == null) return false;
        
        //Assurer que l'objet en parametre est une instance d'une classe qui implemente PileI
        if(!(object instanceof PileI))
            return false;
        PileI pile = (PileI)object;
        
        //Meme instance?
        if(super.equals(object))
            return true;
        
        //Comparer les tailles et les capacites
        int capacite = this.capacite();
        int taille = this.taille();
        if(capacite != pile.capacite())
            return false;
        if(taille != pile.taille())
            return false;
            
        //Si les piles sont vides, elles sont egaux
        if(taille == 0) return true;
        
        //Comparaison element par element. J'ai considere que les piles sont egaux si leurs sequences d'elements sont egaux.
        Pile4 tempPile2 = new Pile4(pile.taille());
        Maillon maillonInitial = stk;
        
        //Pour ne pas recopier du code, j'utilisera un boolean elementsEgaux
        boolean elementsEgaux;
        
        while (stk!=null && !pile.estVide()){
            try{
                elementsEgaux = false;
                if(this.sommet() == null){
                    if(pile.sommet() == null) {
                        elementsEgaux = true;
                    }
                }
                else if(pile.sommet() == null){
                    if(this.sommet() == null) {
                        elementsEgaux = true;
                    }
                }
                else if(stk.element().equals(pile.sommet())){
                    elementsEgaux = true;
                }
                if(elementsEgaux){
                    stk = stk.suivant();
                    tempPile2.empiler(pile.depiler());
                }
                else {
                    stk = maillonInitial;
                    remplirPile(tempPile2, pile);
                    return false;
                }
            } catch(PilePleineException ppe){ppe.printStackTrace();}
            catch(PileVideException pve){pve.printStackTrace();}
        }
        
        //Retourner les elements a la pile initiale:
        stk = maillonInitial;
        remplirPile(tempPile2, pile);
        
        return true;
    }
    
    private void remplirPile(PileI p1, PileI p2){
        while(!p1.estVide()){
            try{
                p2.empiler(p1.depiler());
            } catch (PileVideException pve){pve.printStackTrace();}
            catch (PilePleineException ppe){ppe.printStackTrace();}
        }
    }

        
    public int capacite() {
        return this.capacite;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public int taille() {
        return nombre;
    }
}