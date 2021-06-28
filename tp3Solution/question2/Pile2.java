package question2;

import question1.PilePleineException;
import question1.PileVideException;

import java.util.Stack;


public class Pile2 implements PileI {
    /** par delegation : utilisation de la class Stack */
    private Stack<Object> stk;
    /** la capacite de la pile */
    private int capacite;
    private int taille;
    /**
     * Creation d'une pile.
     * 
     * @param taille
     *            la taille de la pile, la taille doit etre > 0
     */
    public Pile2(int taille) {
        if (taille <= 0)// prevoir le cas <=0
            taille = CAPACITE_PAR_DEFAUT;
        this.stk = new Stack<Object>(); 
        this.capacite=taille;
        this.taille=0;
    }

    // constructeur fourni
    public Pile2() {
        this(CAPACITE_PAR_DEFAUT);
    }

    public void empiler(Object o) throws PilePleineException {
        if (estPleine())
           throw new PilePleineException();
           stk.push(o);
           taille++;
     }

    public Object depiler() throws PileVideException {
        if (estVide())
            throw new PileVideException();
            taille--;
            return stk.pop();
           
            }

    public Object sommet() throws PileVideException {
        if (estVide())
            throw new PileVideException();
        return stk.peek();
    }

    /**
     * Effectue un test de l'etat de la pile.
     * 
     * @return vrai si la pile est vide, faux autrement
     */
    public boolean estVide() {
        return taille == 0;
    }

    /**
     * Effectue un test de l'etat de la pile.
     * 
     * @return vrai si la pile est pleine, faux autrement
     */
    public boolean estPleine() {
        return stk.size()==capacite;
    }

    /**
     * Retourne une representation en String d'une pile, contenant la
     * representation en String de chaque element.
     * 
     * @return une representation en String d'une pile
     */
    public String toString() {
        String s = "[";
        if(estVide()) return s+"]";
        
        StringBuffer sb = new StringBuffer("[");
        for (int i = stk.size() - 1; i >= 0; i--) {
            sb.append(stk.get(i));
            if (i > 0)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
    
    /* first trial that didn't work in question2-5 while comparing
      * PileI to Pile and Pile2 and Pile3
      * public boolean equals(Object o) {
     if (this == o) return true;
     if (o == null) return false;
     if (!(o instanceof Pile2)) return false;
     Pile2 pile= (Pile2)o;
     if (stk.size() != pile.stk.size()){return false;}
     if (capacite != pile.capacite){return false;}
   
     for (int i=0;i<stk.size()-1;i++){
         if ( stk.get(i)!=pile.stk.get(i) )return false;
         } 
     
    return true;
   }*/
       private void remplirPile(PileI p1, PileI p2){
        while(!p1.estVide()){
            try{
                p2.empiler(p1.depiler());
            } catch (PileVideException pve){pve.printStackTrace();}
            catch (PilePleineException ppe){ppe.printStackTrace();}
        }
    }

    public boolean equals(Object object){
        if(object == null) return false;
        
        //Assurer que l'objet en parametre est une instance d'une classe qui implemente PileI
        if(!(object instanceof PileI))
            return false;
        PileI pile = (PileI)object;
        
        //Meme instance? same as object==this
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
        Pile2 tempPile1 = new Pile2(taille);
        Pile2 tempPile2 = new Pile2(pile.taille());
        
        //Pour ne pas recopier du code, j'utilisera un boolean elementsEgaux
        boolean elementsEgaux;
        
        while (!this.estVide() && !pile.estVide()){
            
            try{
                elementsEgaux = false;
                if(this.sommet() == null){
                    if(pile.sommet() == null) 
                        elementsEgaux = true;
                }        
                else if(pile.sommet() == null){
                    if(this.sommet() == null) 
                        elementsEgaux = true;
                }  
                else if(this.sommet().equals(pile.sommet())){
                    elementsEgaux = true;
                }
                
                if(elementsEgaux){
                    tempPile1.empiler(this.depiler());
                    tempPile2.empiler(pile.depiler());
                }
                else{
                    remplirPile(tempPile1, this);
                    remplirPile(tempPile2, pile);
                    return false;
                }
            } catch(PilePleineException ppe){ppe.printStackTrace();}
            catch(PileVideException pve){pve.printStackTrace();}
        }
        
        //Retourner les elements a la pile initiale:
        remplirPile(tempPile1, this);
        remplirPile(tempPile2, pile);
        
        return true;
    }

            
    // fonction fournie
    public int hashCode() {
        return toString().hashCode();
    }
    

    /**
     * Retourne le nombre d'element d'une pile.
     * 
     * @return le nombre d'element
     */
    public int taille() {
       return stk.size();
    }

    /**
     * Retourne la capacite de cette pile.
     * 
     * @return le nombre d'element
     */
    public int capacite() {
    return this.capacite;
    }

} // Pile2.java
