/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alg4;

import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Alg4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int error = 0;
        double a;
        double b;
        double p0;
        long antes;
        long despues;
        double tol;
        int iteraciones;
        double raiz;
        ArrayList<Double> coef = new ArrayList();
        
        // Si hay menos de 2 argumentos es que viene sin ecuación
        if (args.length > 2)
        {
            // Primero hago una comprobación de si lo que viene es 
            // un número.
            // Además almaceno los coeficientes de la ecuación en un array
            for (int i = args.length-1 ; i >= 0; i--){
                 try  
                {  
                  double d = Double.parseDouble(args[i]); 
                  if (i >= 3)
                  {
                      coef.add(d);
                  }
                }  
                catch(NumberFormatException e)  
                {  
                  error ++; 
                }
            }
            
            if (error == 0)
            {    
                // Obtengo los valores del intervalos inicial
                p0 = Double.parseDouble(args[0]);
                iteraciones = Integer.parseInt(args[1]);
                tol = Double.parseDouble(args[2]);
                // Pintamos la ecuación
                System.out.println("");
                System.out.print("Ecuación: ");
                char signo = '.';
                for(int i = coef.size()-1; i>=0; i--)
                {
                    if (0!= coef.get(i))
                    {
                        if (signo == '.')
                            signo = ' ';
                        else
                        {
                            if (coef.get(i) < 0)
                                signo = '-';
                            else
                                signo = '+';
                        }

                        if (i>=2)
                            System.out.print(" "+signo+" ("+abs(coef.get(i))+")x^"+i);
                        else if (i==1)
                            System.out.print(" "+signo+" ("+abs(coef.get(i))+")x");
                        else
                            System.out.print(" "+signo+" "+abs(coef.get(i)));
                    }
                }
                
                System.out.println("");
                
                
                // Tomo valores del instante antes de empezar la función y del
                // instante después de que finalice. Esta función es propia.
                antes = System.nanoTime();
                raiz = NewtonRaphson(tol, iteraciones,coef, p0);
                despues = System.nanoTime();
                
                System.out.println("Raiz: " + raiz);
                System.out.println("Tiempo de ejecucion: " + (despues - antes));
                             
            }
        }
        else{
            System.out.println("Debe introducir como parámetros los extremos del intervalo y los coeficientes de la ecuación");
        }
    }
    
    public static double NewtonRaphson(double tol, int it, ArrayList<Double> ec, double p0)
    {
        double fp0;
        double devfp0;
        double p;
        
        for (int i = 1; i <= it; i++)
        {
            fp0 = 0;
            devfp0 = 0;
            for(int j = ec.size()-1; j>=0; j--)
            {
                fp0 += ec.get(j)*(Math.pow(p0, j));
                devfp0 += j*ec.get(j)*(Math.pow(p0, j-1));
            }
            
            p = p0 - (fp0 / devfp0);
            
            if (tol > abs(p-p0))
                return p;
            
            p0 = p;
        }        
        return p0;
    }
}
