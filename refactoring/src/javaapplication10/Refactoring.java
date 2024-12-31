/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication10;


public class Refactoring {

  
    public String function(){
        String error=checkError(operation1(), "operation1 failed");
        if(error!=null){
            return error;
        }
        error=checkError(operation2(),"operaton2 failed");
        if(error!=null){
            return error;
        }
        
          error=checkError(operation3(),"operaton3 failed");
        if(error!=null){
            return error;
        }
        
          error=checkError(operation4(),"operaton4 failed");
        if(error!=null){
            return error;
        }
        return error;
    }
    
    private String checkError(boolean pass,String fail){
        if(!pass){
            return fail;
        }
        return null;
    }
 
    private boolean operation1() {
return false;    }

    private boolean operation2() {
return false;      }

    private boolean operation3() {
return true;      }

    private boolean operation4() {
return false;      }
    
    
    
    public static void main(String[] args) {
       Refactoring jjj= new Refactoring();
       String result=jjj.function();
        System.out.println(result);
    }
}

/* Answer---> As we know refactor reduces complexity and simplifies error handling and imporve readabilitgy by using methods and functional interface.
also in a proper way of handling multiple operations function method calls each operation if an operation fails it return immediately.
*/