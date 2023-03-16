package com.epam.rd.autotasks;

import java.math.BigInteger;
import java.math.MathContext;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.pow;

public class Battleship8x8 {
    private final long ships;
    private long shots = 0L;

    public Battleship8x8(final long ships) {
        this.ships = ships;
    }

    public boolean shoot(String shot) {
        Pattern p = Pattern.compile("[A-H][1-8]");
        Matcher m = p.matcher(shot);
        if(!m.matches()){
            throw new IllegalArgumentException();
        }

        final int row = shot.charAt(1) - 49;
        final int column = shot.charAt(0) - 65;
        //System.out.println(row + " " + column);

        BigInteger base = new BigInteger("2");
        int indicator = 8 * (7 - row) + (7 - column);
        BigInteger result = base.pow(indicator);

        long newShoot = result.longValue();
        //System.out.println(String.format("%64s", Long.toBinaryString(shots)));

        String shotsB = String.format("%64s", Long.toBinaryString(shots)).replace(' ', '0');

        if(shotsB.charAt(63 - indicator) == '1'){
            System.out.println("You cannot shoot in the same field twice!");
        }
        else{
            shots += newShoot;
        }
        //System.out.println(indicator);
        //System.out.println(newShoot);
        //System.out.println(String.format("%64s", Long.toBinaryString(newShoot)).replace(' ', '0'));

        String shipsB = String.format("%64s", Long.toBinaryString(ships)).replace(' ', '0');
        if (shipsB.charAt(63 - indicator) == '1')
            return true;
        return false;
    }

    public String state() {
        StringBuilder result = new StringBuilder();

        String shotsB = String.format("%64s", Long.toBinaryString(shots)).replace(' ', '0');
        //System.out.println(shotsB);
        String shipsB = String.format("%64s", Long.toBinaryString(ships)).replace(' ', '0');
        //System.out.println(shipsB);

        for (int i = 0; i < shipsB.length(); i++){
            if (i % 8 == 0){
                result.append("\n");
            }
            switch (shipsB.charAt(i)){
                case '1': {
                    if(shotsB.charAt(i) == '1'){
                        result.append("☒");
                        break;
                    }
                    result.append("☐");
                    break;
                }
                case '0':{
                    if(shotsB.charAt(i) == '1'){
                        result.append("×");
                        break;
                    }
                    result.append(".");
                    break;
                }
            }
        }
        return result.toString();
    }
    public static void main(String[] args){
        /*
        11110000
        00000111
        00000000
        00110000
        00000010
        01000000
        00000000
        00000000
        */
        long map = 0b11110000_00000111_00000000_00110000_00000010_01000000_00000000_00000000L;
        Battleship8x8 battle = new Battleship8x8(map);
        List<String> shots = List.of("A1", "B2", "C3", "D4", "E5", "F6", "G7", "H8");
        shots.forEach(battle::shoot);
        System.out.println(battle.state());
    }
}
