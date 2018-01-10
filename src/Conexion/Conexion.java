/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.sql.*;
/**
 * @web http://www.jc-mouse.net
 * @author Mouse
 */
public class Conexion {
 /* DATOS PARA LA CONEXION */
  /** base de datos por defecto es test*/
  private String db = "taller3";
  /** usuario */
  private String user = "root";
  /** contraseña de MySql*/
  private String password = "8162";
  /** Cadena de conexion */
  private String url = "jdbc:mysql://localhost/"+db;
  /** variable para trabajar con la conexion a la base de datos */
  private Connection conn = null;

   /** Constructor de clase */
   public Conexion(){
        this.url = "jdbc:mysql://localhost/"+this.db;
       try{
         //obtenemos el driver de para mysql
         //Class.forName("com.mysql.jdbc.Driver");
         Class.forName("com.mysql.jdbc.Driver");
         //obtenemos la conexión
         conn = DriverManager.getConnection( this.url, this.user , this.password ); 
         
      }catch(SQLException e){
         System.out.println( e.getMessage() );
      }catch(ClassNotFoundException e){
         System.out.println( e.getMessage() );
      }catch(Exception e){
         System.out.println( e.getMessage() );
      }
   }

   public Connection getConexion()
   {
    return this.conn;
   }

}