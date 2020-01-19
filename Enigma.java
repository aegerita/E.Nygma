package enigma;
// testing for githun in vscode
import java.util.*;

public class Enigma {                                    
    static List<List<Integer>> rotors;
    static List<int[]> inv_rotors;
    static List<List<Integer>> reflectors;
    static List<int[]> inv_reflectors;
    static int rotate;
    
    public Enigma(){            
        rotors = new ArrayList<>();
        inv_rotors = new ArrayList<>();
        reflectors = new ArrayList<>();
                                  // a   b   c   d   e   f   g   h   i   j   k   l   m   n   o   p   q   r   s   t   u   v   w   x   y   z
                                  // 0   1   2   3   4   5   6   7   8   9   10  11  12  13  14  15  16  17  18  19  20  21  22  23  24  25 
        rotors.add(    Arrays.asList(4,  10, 12, 5,  11, 6,  3,  16, 21, 25, 13, 19, 14, 22, 24, 7,  23, 20, 18, 15, 0,  8,  1,  17, 2,  9)); // I
        rotors.add(    Arrays.asList(0,  9,  3,  10, 18, 8,  17, 20, 23, 1,  11, 7,  22, 19, 12, 2,  16, 6,  25, 13, 15, 24, 5,  21, 14, 4)); // II
        rotors.add(    Arrays.asList(1,  3,  5,  7,  9,  11, 2,  15, 17, 19, 23, 21, 25, 13, 24, 4,  8,  22, 6,  0,  10, 12, 20, 18, 16, 14));// III
        rotors.add(    Arrays.asList(4,  18, 14, 21, 15, 25, 9,  0,  24, 16, 20, 8,  17, 7,  23, 11, 13, 5,  19, 6,  10, 3,  2,  12, 22, 1)); // IV
        rotors.add(    Arrays.asList(21, 25, 1,  17, 6,  8,  19, 24, 20, 15, 18, 3,  13, 7,  11, 23, 0,  22, 12, 9,  16, 14, 5,  4,  2,  10));// V
        rotors.forEach((rt) -> {
            inv_rotors.add(getInverse(rt));
        });
        
        reflectors.add(Arrays.asList(24, 17, 20, 7,  16, 18, 11, 3,  15, 23, 13, 6,  14, 10, 12, 8,  4,  1,  5,  25, 2,  22, 21,  9,  0, 19)); // B 
        reflectors.add(Arrays.asList(5,  21, 15, 9,  8,  0,  14, 24, 4,  3,  17, 25, 23, 22, 6,  2,  19, 10, 20, 16, 18, 1,  13,  12, 7, 11)); // C

        rotate = 0;
    }
    
    public static void main(String[] args) {
        //Scanner in = new Scanner(System.in);
        Enigma nygma = new Enigma();
        List<int[]> decoded = new ArrayList<>();
        
        String crib = "enigma"; 
        String str = "This is an Enigma Machine, nice to meet you!";
        int Rotate = 23123;
        String code = nygma.code(str,1,2,3,1,Rotate%26,Rotate/26%26,Rotate/676%26);
        System.out.println(code);
        System.out.println(nygma.findStartWithCrib(code, crib));
        
        
        for (int i = 0; i < rotors.size(); i++){
        for (int j = 0; j < rotors.size(); j++){
            //System.out.println("Processing: " + (i*20+j*4) +"%");
        if (j != i){
        for (int k = 0; k < rotors.size(); k++){
        if (k != i && k != j){
            
            System.out.println(i +" "+j+" "+k+" ");
            Rotate = 0;
            while (Rotate < 17576){
                nygma = new Enigma();
                String temp = nygma.code(code,i,j,k,0,Rotate%26,Rotate/26%26,Rotate/676%26);
                nygma = new Enigma();
                String temp2 = nygma.code(code,i,j,k,1,Rotate%26,Rotate/26%26,Rotate/676%26);
                for (int start: nygma.findStartWithCrib(code, crib)){
                    if (crib.equalsIgnoreCase(temp.substring(start, start + crib.length()))) {
                        decoded.add(new int[]{i,j,k,0,Rotate%26, Rotate/26%26, Rotate/676%26});
                    } 
                    if (crib.equalsIgnoreCase(temp2.substring(start, start + crib.length()))) {
                        decoded.add(new int[]{i,j,k,1,Rotate%26, Rotate/26%26, Rotate/676%26});
                    } 
                }
                Rotate++;
            }
            
        }}}}}
        
        if (decoded.isEmpty())
            System.out.println("Error: false crib or no result. ");
        for (int[] decode: decoded){
            nygma = new Enigma();
            System.out.println(Arrays.toString(decode) + ": " 
                    + nygma.code(code,decode[0], decode[1], decode[2],decode[3], decode[4], decode[5],decode[6]));
        }
    }
    
    public String code(String str, int rotor1, int rotor2, int rotor3, int reflector){
        String x = "";
        boolean capital;
        
        /*System.out.println(" a  b  c  d  e  f  g  h  i  j  k  l  m  n  o  p  q  r  s  t  u  v  w  x  y  z");
        System.out.println(" 0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25");
        System.out.println(rotors.get(rotor1));
        System.out.println(rotors.get(rotor2));
        System.out.println(rotors.get(rotor3));
        System.out.println(reflectors.get(reflector));
        System.out.println(Arrays.toString(inv_rotors.get(rotor3)));
        System.out.println(Arrays.toString(inv_rotors.get(rotor2)));
        System.out.println(Arrays.toString(inv_rotors.get(rotor1)));*/
        
        for (char character: str.toCharArray()) {
            if (character >= 'a' && character <= 'z') {capital = false; character -= 'a';}
            else if (character >= 'A' && character <= 'Z') {capital = true; character -= 'A';}
            else {x += character; continue;}

            int c = inv_rotors.get(rotor1)[inv_rotors.get(rotor2)[
                    inv_rotors.get(rotor3)[reflectors.get(reflector).get(
                    rotors.get(rotor3).get(rotors.get(rotor2).get(rotors.get(
                    rotor1).get(character))))]]];
            
            if (capital) x += (char)(c+'A');
            else x += (char)(c+'a');
                
            rotate++;
            rotate(rotor1);
            if (rotate % 26 == 0) rotate(rotor2);
            if (rotate % 676 == 0) rotate(rotor3);
        }
        return x;
    }
    
    public String code(String str, int rotor1, int rotor2, int rotor3, int reflector, int rotor1index, int rotor2index, int rotor3index){
        //System.out.println(rotors.get(rotor3));
        Collections.rotate(rotors.get(rotor1), 26 - rotor1index);
        Collections.rotate(rotors.get(rotor2), 26 - rotor2index);
        Collections.rotate(rotors.get(rotor3), 26 - rotor3index);
        //System.out.println(Arrays.toString(inv_rotors.get(rotor2)));
        for (int i = 0; i < 26; i++){
            inv_rotors.get(rotor1)[i] = (26 - rotor1index + inv_rotors.get(rotor1)[i]) % 26;
            inv_rotors.get(rotor2)[i] = (26 - rotor2index + inv_rotors.get(rotor2)[i]) % 26;
            inv_rotors.get(rotor3)[i] = (26 - rotor3index + inv_rotors.get(rotor3)[i]) % 26;
        }
        return code(str, rotor1, rotor2, rotor3, reflector);
    }
    
    private void rotate(int rt) {
        Collections.rotate(rotors.get(rt),25);
        for (int i = 0; i < 26; i++){
            inv_rotors.get(rt)[i] = (25 + inv_rotors.get(rt)[i]) % 26;
        }
        // System.out.println(Arrays.toString(inv_rotors.get(rt)));
    }
    
    public List<Integer> findStartWithCrib(String str, String crib){
        str = str.toLowerCase();
        crib = crib.toLowerCase();
        List<Integer> startPos = new ArrayList<>();
        boolean[] falsePos = new boolean[str.length() - crib.length() +1];
        
        for (int i = 0; i < crib.length(); i++){
            for (int j = i; j <= str.length() - crib.length() + i; j++){
                if ((crib.charAt(i) >= 'a' && crib.charAt(i) <= 'z') && crib.charAt(i) == str.charAt(j))
                    falsePos[j-i] = true;
                else if (str.charAt(j) ==' ' && crib.charAt(i) !=' ')
                    falsePos[j-i] = true;
            }
        }
        /*for (int i = 0; i <= str.length() - findStartWithCrib.length(); i++) {
        // if (str.charAt(i) < 'a' || str.charAt(i) > 'z') continue;
        startPos.add(i);
        for (int j = i; j < findStartWithCrib.length() + i; j++){
        if (str.charAt(j) == findStartWithCrib.charAt(j-i)){
        startPos.remove(startPos.size()-1);
        break;
        }
        }
        }*/
        //System.out.println(findStartWithCrib);
        
        for (int i = 0; i < falsePos.length; i++){
            if (!falsePos[i]) startPos.add(i);
        }
        return startPos;
    }
    
    public static int[] getInverse(List rt){
        int[] inv = new int[26];
        int i = 0;
        for (Object x: rt){
            inv[(int) x] = i;
            i++;
        }
        return inv;
    }
}


