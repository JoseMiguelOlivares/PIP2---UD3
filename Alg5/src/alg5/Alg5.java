/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alg5;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Usuario
 */
public class Alg5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        ArrayList<ArrayList<Double>> coefs = new ArrayList();
        ArrayList<Double> resultado;
        int n;
        double d;
        long antes;
        long despues;
        
        // Se pide el número de incóginas del sistema.
        System.out.print("Introduce número de incognitas (será el mismo que el de ecuaciones): ");
        
        try
        {
            n = s.nextInt();            
        }
        catch (Exception e)
        {
            System.out.println("Hubo un error en el argumento introducido");
            return;
        }
        
        for (int i = 0; i < n; i++)
        {            
            ArrayList<Double> ec = new ArrayList();
            System.out.println("ECUACIÓN "+(i+1));
            
            for (int j = 0; j < n; j++){
                System.out.print("Introduce el parámetro número " + (j+1) + " de la ecuación: ");
                try
                {
                    d = s.nextDouble();
                }
                catch(Exception e){
                    System.out.println("Hubo un error en el argumento introducido");
                    return;
                }
                ec.add(d);
            }
            
            System.out.print("Introduce el resultado de la ecuación: ");
            try
            {
                d = s.nextDouble();
            }
            catch(Exception e){
                System.out.println("Hubo un error en el argumento introducido");
                return;
            }
            ec.add(d);
                
            coefs.add(ec);
            System.out.println("");
        }
        for(int j = 0; j < coefs.size(); j++)
            System.out.println(coefs.get(j));
        
        System.out.println();
        antes = System.nanoTime();
        resultado = GaussPivote(coefs); 
        despues = System.nanoTime();
        
        if (null == resultado)
        {
            System.out.println("No existe solución única");
            return;
        }
        System.out.println("Resultado:");
        System.out.println(resultado);        
        
        System.out.println();
        System.out.println("Solución calculada en " + (despues - antes) +" nano segundos");
    }
    
    
    public static ArrayList<Double> GaussPivote (ArrayList<ArrayList<Double>> coefs)
    {
        ArrayList<Double> resultado = new ArrayList();
        int findP;
        int i;
        int p;
        int n = coefs.size()-1;
        int nind = coefs.get(0).size()-1; // n + término independiente
        double aux;
                
        // ponemos -2 porque comenzamos en 0 y el máximo es n-1, si pusieramos
        // -1 el máximo sería n.
        for (i = 0; i < n; i++)
        {
            findP = i;
            for (p = i; p < n; p++)
            {
                if (abs(coefs.get(p).get(i)) > abs(coefs.get(findP).get(i)))
                {
                    findP = p;
                }
            }
            // Si todos los elementos son 0
            if (0 == coefs.get(findP).get(i))
                return null;
            // Si la fila que encuentra con pivote distinto de 0 no es la fila i
            // se permuta con la fila correspondiente.
            if (p != i)
            {
                for (int j = 0; j <= nind; j++)
                {
                    aux = coefs.get(i).get(j);
                    coefs.get(i).set(j, coefs.get(p).get(j));
                    coefs.get(p).set(j, aux);
                }
            }
            
            for (int j = i+1; j <= n; j++)
            {                
                aux = coefs.get(j).get(i)/coefs.get(i).get(i);
                for (int k = 0; k <= nind; k++)
                {
                    coefs.get(j).set(k, (coefs.get(j).get(k)-aux*coefs.get(i).get(k)));
                }
            }
        }
        if (0 == coefs.get(n).get(n))
            return null;
        
        resultado.add(coefs.get(n).get(nind)/coefs.get(n).get(n));
        
        for (i = n-1; i >= 0; i--)
        {
            aux = 0;
            for (int j = i+1; j <= n; j++)
            {
                aux += coefs.get(i).get(j)*resultado.get(j-(i+1));
            }
            resultado.add(0, (coefs.get(i).get(nind) - aux)/coefs.get(i).get(i));
        }
        return resultado;
    }
    
    
}
