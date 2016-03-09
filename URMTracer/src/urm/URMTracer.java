/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 *
 * @author Charie Mae
 */
public class URMTracer {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
  
    public static void main(String[] args) throws FileNotFoundException, IOException {
      File file = new File("mp1.out");
      file.createNewFile();
      FileWriter fw = new FileWriter(file);

      String[] instruction = readFile();
      String output = instruction[0]; // holds the basis for the resulting outputs (the string of numbers).
      int countLines = 1;
      
      /*this loop stops until the whole instruction is executed*/
      for (int idx = 1; idx < instruction.length; idx++){
        String returned_output = checkInstruction(instruction[idx], output);

        if (returned_output == null){
          /*the lines in this if condition resets the idx according to the jump instruction so
          that the loop continues.*/
          String condition = instruction[idx].replace(" ",","); 
          String[] forInt = condition.split(",");
          idx = Integer.parseInt(String.valueOf(forInt[3])) - 1;   
        } else {
          output = returned_output.replace(",","").replace("[","").replace("]",""); //Eliminates the commas and brackets from the returned output for consistent results
        }
        
        fw.write("["+output+"] ");
        System.out.println(output);
        countLines = countLines+ 1;
      }
      System.out.println("Lines: "+countLines);
      fw.flush();
      fw.close();
    }
    
    public static String[] readFile() throws FileNotFoundException, IOException{
      FileInputStream read_input = new FileInputStream("mp1.in");
      FileInputStream read_file = new FileInputStream("mp1.in");
      BufferedReader br = new BufferedReader(new InputStreamReader(read_input));
      BufferedReader br2 = new BufferedReader(new InputStreamReader(read_file));

      String readLine = "";
      String storeInput;
      int array_size = 0, i = 0;
      
      /*this loop count is intended for the size of the array where we will store the instructions.*/
      while ((readLine = br.readLine()) != null){ 
        array_size+= 1;   
      }
      String[] input = new String[array_size];

      // loop for storing the instruction in the array
      while ((storeInput = br2.readLine()) != null){
        input[i] = storeInput;
        System.out.println(input[i]); // prints the instruction set one by one.
        i++;   
      }
      return input;
    }

    public static String checkInstruction(String str, String basis){
      /*this function receives the instruction and the basis where the instruction is applied.
      The function will return the final output.*/
      String[] s = str.split(" ");
      String[] output = basis.split(" ");
      String ans = basis;
        
      if(s[0].charAt(0)=='S'){
        ans = successor(s,output);
      } else if(s[0].charAt(0)=='Z'){
        ans = zero(s,output);
      } else if (s[0].charAt(0)=='J'){
        ans = jump (s, output);
      } else if (s[0].charAt(0)=='C'){
        ans = copy(s, output);
      } 
      return ans;
    }
     
    public static String successor (String[] str, String[] output){
      /*Converts number character to integer.
      This will be used to find the index to be incremented.*/
      int idx = Integer.parseInt(str[1]);

      int value = Integer.parseInt(output[idx]) + 1;
      //holds the resulting value from the incrementation.
      
      output[idx] = Integer.toString(value);
      //assigns/changes the value to the specified index.

      return Arrays.toString(output);
    } 
    
    public static String zero(String[] str, String[] output){ 
      /* Converts number character to integer.
      This will be used to find the index to be set as zero.*/
      int idx = Integer.parseInt(str[1]);
      
      int value = 0;
      output[idx] = Integer.toString(value); // changes the value of index to zero.
               
      return Arrays.toString(output);
    }
    
    public static String copy(String[] str, String[] output){

      /*Converts number character to integer.
      This will be used to find the index to be copied from and to.*/
      int idx1 = Integer.parseInt(str[1]);
      int idx2 = Integer.parseInt(str[2]);

      int value = Integer.parseInt(output[idx1]);
      //holds the value from the desired index to be copied.
      
      output[idx2] = Integer.toString(value);
      // changes the value on the index assigned from the copied index.        
      
      return Arrays.toString(output);
    }
    
    public static String jump (String[] str, String[] output){
      String forReturn = null;

      /*Converts number character to integer.
      This will be used to find the value of the index to be compared.*/
      int idx1 = Integer.parseInt(str[1]);
      int idx2 = Integer.parseInt(str[2]);        

      /*if-else condition checks the value of the compared indeces.
      if values are not equal, then it proceeds to the next instruction.
      else, returns null so that the instruction to be jumped-off will be executed.
      The condition that will trigger the jump is on the main function.*/
      if(output[idx1] == null ? output[idx2] != null : !output[idx1].equals(output[idx2])){
        forReturn = Arrays.toString(output);
      } else{
        forReturn = null;
      }
      return forReturn;
    }      
}