/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.Buscar_Eliminar;
import Vista.Ingresar;
import Vista.Inicio;
import Vista.Modificar;
import Vista.Listar;
import Vista.Consulta;

/**
 *
 * @author Duoc UC
 */
public class Taller4TapiaEgana {

    public static void main(String[] args) {
        // TODO code application logic here
        new Coordinador(new Inicio(), new Buscar_Eliminar(), new Ingresar(), new Modificar(), new Listar(), new Consulta()).iniciar();
    }
    
}
