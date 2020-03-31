package inf112.skeleton.app.ai;

import inf112.skeleton.app.Player;
import inf112.skeleton.app.cards.ICard;
import inf112.skeleton.app.object.Robot;

import java.util.ArrayList;
import java.util.Random;

public class Ai extends Player {

    public Ai(String name) {
        super(name);
    }

    public Ai(String name, Robot r) {
        super(name, r);
    }


    public void setMoves(){
        int moves = 0;
        ArrayList<Integer> list = new ArrayList<>();
        Random r = new Random();
        int randInt = r.nextInt(this.getHand().size());
        while (list.size()<5){
            if(!list.contains(randInt)){
                list.add(randInt);
                moves++;
            }
            randInt = r.nextInt(this.getHand().size());
        }

        for(Integer i : list){
            this.addCardToSheet(this.getHand().get(i));
        }
    }




}
