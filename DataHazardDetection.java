package ca.project;

import java.util.Scanner;

/**
 *
 * @author sanif
 */
public class CaProject {

    
   static public String instructiondecoding(String a){
     //Fetching the Instruction type
       String inst="";
       boolean check=false;
       char z;
       for (int i = 0; i < a.length() && check==false; i++) {
           z=a.charAt(i);
           if(z !=' '){
               inst=inst+z;           
           }
           else check=true;
       }
     
       return inst;

   } 
   
   public static String decoding(String a){
       // Seperating destination regsiter in seperately
      String abc="";
      char z;
      boolean check=false;
      boolean temp=false;
       for (int i = 0; i < a.length(); i++) {
           z=a.charAt(i);
           if(z==' ')check=true;
           if(check == true && z!=',' && temp==false){
           abc=abc+z;
           }
           if(z==',') temp=true;
       }
   return abc;
   }
   
    public static String decodings(String a){
      //Seperating Source registers Single only
      String abc="";
      char z;
      boolean check=false;
      boolean temp=false;
       for (int i = 0; i < a.length(); i++) {
           z=a.charAt(i);
           if(z==','){check=true;
           
           }
           
           if(check == true && z!=','){
           abc=abc+z;
          }
          
       }
        
   return abc;
   } 

    public static void main(String[] args) {
        
        System.out.println("ENTER NUMBER OF INSTRUCTION");
        Scanner s=new Scanner(System.in);
        Scanner m=new Scanner(System.in);
        int n=s.nextInt();
        
        String []instruct=new String[n];    // For storing statements
        String []fetch=new String[n];       // For storing Inst 
        String []destination=new String[n]; // For storing destinaton registers
        String aa="lw";
        String bb="sub";
        String cc="add";
        String dd="sw";
        String ee="and";
        String ff="or";
        String gg="beq";
        String hh="bneq";
        
        System.out.println("Enter Instructions \n(for registers access use it of form r1) \n(use 'add' for Addition)\n(use 'sub' for subraction) \n(and and or for AND & OR inst) \n(bneq and beq for branches)");  
        String [][]source=new String[n][2];
        for (int i = 0; i < n; i++) {
      
              
            instruct[i]=m.nextLine();    //input
          fetch[i]=instructiondecoding(instruct[i]);  //storing instruction
            destination[i]=decoding(instruct[i]);    //storing destination register
           
        }
        for (int i = 0; i < n; i++) {
            for (int   j= 0; j< 2;  j++) {
                source[i][j]=" ";
            }
        }
        for (int i = 0; i < n; i++) {
            if(fetch[i].equals(aa) || fetch[i].equals(dd)){
                source[i][0]=decodings(instruct[i]);  //storing source 1
            }
   
        }
        int count=0;
        String temp;
        String hold1="";
        String hold2="";
        char hold;
       for (int i = 0; i < n; i++) {
            if(fetch[i].equals(bb) || fetch[i].equals(ee) || fetch[i].equals(ff)|| fetch[i].equals(cc) || fetch[i].equals(gg) ||fetch[i].equals(hh)){
                          temp=instruct[i];
                          for (int j = 0; j < temp.length(); j++) {
                               hold=temp.charAt(j);
                               if(hold==',')count++;
                               if(count==1 && hold!=',' ){
                                  hold1=hold1+hold;         //Appending first source        
                               }
                               if(count==2 && hold!=','){
                               hold2=hold2+hold;            //Appending second source
                               }
                               source[i][0]=hold1;   //storing source 1 and source 2 for two source instruction
                               source[i][1]=hold2;
                               
                }
                 count=0;  
                 hold1="";
                 hold2="";
            }
            
        }
        
        // determining the completion time of instruction
         int []comp=new int[n];
        for (int i = 0; i < n; i++) {
            if(fetch[i].equals(aa)){
                comp[i]=(i+1)+4;
            }
            else if(fetch[i].equals(bb) || fetch[i].equals(cc) || fetch[i].equals(ee) || fetch[i].equals(ff) ){
                comp[i]=(i+1)+3;
            } 
            else if (fetch[i].equals(dd)){
              comp[i]=(i+1)+3;
            }
            else if(fetch[i].equals(hh) || (fetch[i].equals(gg))){
               comp[i]=(i+1)+2;
            }
        }
        
     
        String a;
        String b;
        String c;
        System.out.println("");
        //checking data hazard 
        for (int i = 1; i < source.length; i++) {
            for (int j = 0; j < i; j++) {
               
                a=source[i][0];
                b=destination[j];
                c=source[i][1];
                
                if((a.trim().equals(b.trim()) || c.trim().equals(b.trim())) && comp[j]>i){
                    System.out.println("RAW data hazard at line "+(i+1)+" on register "+destination[j]);
               }
                
            }
        }
        // checking load delay
        for (int i = 0; i < n-1; i++) {
            if(fetch[i].trim().equals(aa.trim()) ){
                
                if(fetch[i+1].trim().equals(bb.trim()) || fetch[i+1].trim().equals(cc.trim()) || fetch[i+1].trim().equals(ff.trim()) || fetch[i+1].trim().equals(ee.trim()))
                { 
                    if(source[i+1][0].trim().equals(destination[i].trim()) || source[i+1][1].trim().equals(destination[i].trim()) ){
                        System.out.println(" load delay at line "+(i+2));
                    }
                }                
            }
        }
    
       String [][]structure=new String[n][50]; // for printing clock cycle diagram
       
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 50; j++) {
                structure[i][j]=" ";
            }
        }
        int z;
        int l=0;
        int k=0;
        
        System.out.println("");
         // storing 5 stage pipeline design according to instruction
        for (int i = 0; i < fetch.length; i++) {
            
            if(fetch[i].equals(aa)){
            structure[l][k]="IF";
            k=k+1;
            structure[l][k]="ID";
            k=k+1;
            structure[l][k]="AL";
            k=k+1;
            structure[l][k]="ME";
            k=k+1;
            structure[l][k]="WB";
            l++;
            k=l;
            
            }
            else if(fetch[i].equals(bb) || fetch[i].equals(ee) || fetch[i].equals(ff)|| fetch[i].equals(cc) ){
            structure[l][k]="IF";
            k=k+1;
            structure[l][k]="ID";
            k=k+1;
            structure[l][k]="AL";
            k=k+1;
            structure[l][k]="WB";
            l++;
            k=l;
            
            }
           
            else if(fetch[i].equals(dd)){
            structure[l][k]="IF";
            k=k+1;
            structure[l][k]="ID";
            k=k+1;
            structure[l][k]="AL";
            k=k+1;
            structure[l][k]="WB";
            l++;
            k=l;
            
            }
            else if(fetch[i].equals(gg) || fetch[i].equals(hh) ){
            structure[l][k]="IF";
            k=k+1;
            structure[l][k]="ID";
            k=k+1;
            structure[l][k]="AL";
           
            l++;
            k=l;
            
            }
           
         }
       String temp1=" ";
       //printing clock cycle diagram
        for (int i = 0; i < structure.length; i++) {
            System.out.print("INSTRUCTION"+(i+1));
            for (int j = 0; j < structure[0].length; j++) {
                System.out.print(" "+structure[i][j]+" ");
            }
            System.out.println("");
        }
        // checking wb stage overlapping in structure hazard
        for (int i = 0; i < structure[0].length; i++) {
            for (int j = 0; j < structure.length-1; j++) {
                
                if(!structure[j][i].equals(temp1) && structure[j][i].equals(structure[j+1][i])){
                    System.out.println("structural hazard at line ( "+(j+1)+" - "+(j+2)+" ) due to overlapping of "+structure[j][i]+" stage");
                }
            }
               
        }
        // checking if and mem overlapping
        for (int i = 0; i < n; i++) {
            if(fetch[i].equals(aa)){
                if(i+3<n){
                if(!fetch[i+3].equals(gg) || !fetch[i+3].equals(hh)){
                    System.out.println("structural hazard at line  ("+(i+1)+" , "+(i+4)+" ) due to overlapping of IF AND MEM");
                }}
            }
        }
        
        String store1="";
        String store2="";
        boolean check1=true;
        boolean check2=true;
        // checking branch if equal control hazard
        for (int i = 0; i < n; i++) {
            if(fetch[i].equals(gg)){
                store1=destination[i];
                store2=source[i][0];
               
            for (int j = i-1; j >=0; j--) {
                if(fetch[j].equals(aa)){
                    
                if(destination[j].trim().equals(store1.trim()) && check1==true){
                store1=source[j][0];
                    
                check1=false;
                }
                if(destination[j].trim().equals(store2.trim()) && check2==true){
                 store2=source[j][0];
                    
                 check2=false;
                }
                if(check1==false && check2==false)break;
                }
            }
           
            if(store1.trim().equals(store2.trim())){
                System.out.println(" control hazard at line "+(i+1)+" branch taken as both register as equal value");
               
            }
            }
    }
         store1="";
         store2="";
         check1=true;
         check2=true;
         // branch not equal control hazard checking
        for (int i = 0; i < n; i++) {
            if(fetch[i].equals(hh)){
                store1=destination[i];
                store2=source[i][0];
                
            for (int j = i-1; j >=0; j--) {
                if(fetch[j].equals(aa)){
                    
                if(destination[j].trim().equals(store1.trim()) && check1==true){
                store1=source[j][0];
                    
                check1=false;
                }
                if(destination[j].trim().equals(store2.trim()) && check2==true){
                 store2=source[j][0];
                    
                 check2=false;
                }
                if(check1==false && check2==false)break;
                }
            }
           
            if(!store1.trim().equals(store2.trim())){
                System.out.println(" control hazard at line "+(i+1)+" branch taken as both register as unequal value");
            }
            }
    }
} 
}
