/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrega2;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Usuario
 */
public class Entrega2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
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
        resultado = Gauss(coefs); 
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
    
    public static ArrayList<Double> Gauss (ArrayList<ArrayList<Double>> coefs)
    {
        ArrayList<Double> resultado = new ArrayList();
        short findP;
        int i;
        int p;
        int n = coefs.size()-1;
        int nind = coefs.get(0).size()-1; // n + término independiente
        double aux;
                
        // ponemos -2 porque comenzamos en 0 y el máximo es n-1, si pusieramos
        // -1 el máximo sería n.
        for (i = 0; i < n; i++)
        {
            findP = 0;
            for (p = i; p < n; p++)
            {
                if (0.0 != coefs.get(p).get(i))
                {
                    findP = 1; 
                    break;
                }
            }
            // Si no hay elementos no hay solución única
            if (0 == findP)
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
            /*
            if (1 != abs(coefs.get(i).get(i)))
            {
                aux = coefs.get(i).get(i);
                for (int k = 0; k <= nind; k++)
                {
                    coefs.get(i).set(k, (coefs.get(i).get(k)/aux));
                }
                
                
            }
          */
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
