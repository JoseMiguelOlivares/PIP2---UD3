/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alg3;

import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Alg3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int error = 0;
        double a;
        double b;
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
                  if (i >= 4)
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
                a = Double.parseDouble(args[0]);
                b = Double.parseDouble(args[1]);
                iteraciones = Integer.parseInt(args[2]);
                tol = Double.parseDouble(args[3]);
                // Pintamos la ecuación
                System.out.println("");
                System.out.print("Ecuación: ");
                char signo = '.';
                for(int i = coef.size()-1; i>=0; i--)
                {
                    if (coef.get(i)!= 0)
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
                System.out.println("Intervalo de f(" + a + ") a f(" + b + ")");
                System.out.println("");
                System.out.println("");
                
                
                // Tomo valores del instante antes de empezar la función y del
                // instante después de que finalice. Esta función es propia.
                antes = System.nanoTime();
                raiz = Secante(tol, iteraciones,coef, a, b);
                despues = System.nanoTime();
                if (raiz < a)
                {
                    System.out.println("No se obtuvo una solución precisa");
                }
                else
                {
                    System.out.println("Raiz: " + raiz);
                    System.out.println("Tiempo de ejecucion: " + (despues - antes));
                }              
            }
        }
        else{
            System.out.println("Debe introducir como parámetros los extremos del intervalo y los coeficientes de la ecuación");
        }
    }
    
    
    public static double Secante(double tol, int it, ArrayList<Double> ec, double a, double b)
    {
        double fa = 0;
        double fb = 0;
        double p;
        double p0;
        double q0;
        double q1;
        
        for(int i = ec.size()-1; i>=0; i--)
        {
            fa += ec.get(i)*(Math.pow(a, i));
            fb += ec.get(i)*(Math.pow(b, i));
        }
        if (fa > 0)
        {
            p0 = b;
            q0 = fa;
            q1 = fb;
            
            for (int i = 2; i < it; i++)
            {
                p = p0 - (q1/(q1-q0))*(p0-a);
                
                if (tol > abs(p-p0))
                    return p0;
                
                p0 = p;
                q1 = 0;
                for(int j = ec.size()-1;j>=0; j--)
                {
                    q1 += ec.get(j)*(Math.pow(p, j));
                }                
            }
        }
        else
        {
            p0 = a;
            q0 = fb;
            q1 = fa;
            //System.out.println("P0 = " + p0);
            for (int i = 2; i < it; i++)
            {
                p = p0 - (q1/(q0-q1))*(b-p0);
                //System.out.println("P" + i + " = " + p);
                
                if (tol > abs(p-p0))
                    return p0;
                
                p0 = p;
                q1 = 0;
                for(int j = ec.size()-1;j>=0; j--)
                {
                    q1 += ec.get(j)*(Math.pow(p, j));
                }                
            }
        }
        
        return a - 1;
    }
    
}
