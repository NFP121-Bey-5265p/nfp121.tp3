package question3;

import question1.PilePleineException;
import question1.PileVideException;

import java.util.Stack;

public class Pile2 <T> implements PileI <T>{
    /** par délégation : utilisation de la class Stack */
    private Stack<T> stk;
    /** la capacité de la pile */
    private int capacite;
    private int taille;
    /** Création d'une pile.
     * @param taille la "taille maximale" de la pile, doit être > 0
     */
    public Pile2(int taille){
        if (taille <= 0)// prevoir le cas <=0
            taille = CAPACITE_PAR_DEFAUT;
        this.stk = new Stack<T>(); 
        this.capacite=taille;
        this.taille=0;
    }

    public Pile2(){
         this(CAPACITE_PAR_DEFAUT);
    }

    public void empiler(T o) throws PilePleineException{
         if (estPleine())
           throw new PilePleineException();
           stk.push(o);
           taille++;
     }

    public T depiler() throws PileVideException{
        if (estVide())
            throw new PileVideException();
           taille--;
            return stk.pop();
    }

    public T sommet() throws PileVideException{
        if (estVide())
            throw new PileVideException();
        return stk.peek();
    }
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
} // Pile2