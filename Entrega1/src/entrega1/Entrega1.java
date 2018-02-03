/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega1;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import java.util.ArrayList;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.solvers.BisectionSolver;
import org.apache.commons.math3.analysis.solvers.UnivariateSolver;

/**
 *
 * @author Usuario
 */
public class Entrega1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int error = 0;
        double a = .0;
        double b = .0;
        long antes = 0;
        long despues = 0;
        int iteraciones = 1000;
        double raiz = .0;
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
                  if (i >= 2)
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

                        if (i>2)
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
                raiz = Biseccion(iteraciones,coef, a, b);
                despues = System.nanoTime();
                
                System.out.println("Raiz en función propia: " + raiz);
                System.out.println("Tiempo de ejecucion: " + (despues - antes));
                
                // Se crea la funcion a evaluar
                UnivariateFunction f = (double d) -> {
                    double result =.0;
                    for(int i = coef.size()-1; i>=0; i--)
                    {
                        result += coef.get(i)*(pow(d, i));
                    }
                    return result;
                };
                UnivariateSolver solver = new BisectionSolver();
                antes = System.nanoTime();                
                raiz = solver.solve(iteraciones, f, a, b);
                despues = System.nanoTime();
                
                System.out.println("Raiz en función Math: " + raiz);
                System.out.println("Tiempo de ejecucion: " + (despues - antes));
            }
        }
        else{
            System.out.println("Debe introducir como parámetros los extremos del intervalo y los coeficientes de la ecuación");
        }
    }
    
    
    public static double Biseccion(int it, ArrayList<Double> ec, double a, double b)
    {
        double result = 0; 
        double r = a+(b-a)/2;
        
        // Si llego al limite de iteraciones devuelvo el resultado
        // Si ambos lados del intervalo son el mismo valor devolvemos el primero
        // E.o.c. Calculamos la función del punto medio
        if (it == 0)
            return r;
        else if (a == b)
            return a;
        else
        {
            double fa = 0;
            double fb=0;
            double fr=0;
            
            // calculamos f(r)
            for(int i = ec.size()-1; i>=0; i--)
            {
                fr += ec.get(i)*(Math.pow(r, i));
            }
            // si es la raiz devuelvo el resultado
            if (fr == 0)
                return r;
            
            // calculamos f(a)
            for(int i = ec.size()-1; i>=0; i--)
            {
                fa += ec.get(i)*(Math.pow(a, i));
            }
            // si ambos son del mismo signo el producto será positivo, se 
            // decrementa el valor de la iteracion y se pasa el nuevo intervalo
            if ((fa*fr)<0)
                result = Biseccion(--it,ec, a,r);
            else
                result = Biseccion(--it,ec,r,b);            
            
        }
        
        return result;
    }

    
}
