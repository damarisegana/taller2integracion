package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

//Ver nombre de las bases.
//modificar sql
//ver las validaciones.
public class Registro extends Conexion.Conexion {

    private String[] columNames = {"Código", "Nombre", "Precio", "Categoría", "4K"};
    public TableModel ModificarSeleccionar;

    public Registro() {
        super();
    }

    public boolean ValidarPel(String codigo, String nombre, String precio, int idcategoria, String formato4k) {

        int validacioncodigo = 0;
        int codigolargo = 0;
        int validacionprecio = 0;
        int validacioncantidad = 0;
        int validacioncodigo2 = 0;
        String existeCodigo = "";

        if (codigo.equals("")) {
            JOptionPane.showMessageDialog(null, "Error: Ingrese Código.");
            return false;
        } else if (!codigo.equals("")) {
            try {

                float validacodigo = Float.parseFloat(codigo);
                validacioncodigo = 1;
            } catch (NumberFormatException f) {
                JOptionPane.showMessageDialog(null, "Error: Ingrese Codigo Númerico entre 10000 y 99999");
            }
        }

        if (validacioncodigo == 1) {
            int codigonum = Integer.parseInt(codigo);

            if (codigonum < 10000 || codigonum > 99999) {
                JOptionPane.showMessageDialog(null, "Error: Ingrese Codigo Númerico entre 10000 y 99999.");
                return false;
            } else if (codigonum >= 10000 && codigonum <= 99999) {

                try {
                    //realizamos la consulta sql y llenamos los datos en la matriz "Object[][] data"
                    PreparedStatement pstm = this.getConexion().prepareStatement("SELECT codigo FROM pelicula where codigo =" + codigo + ";");
                    ResultSet res = pstm.executeQuery();
                    int i = 0;
                    while (res.next()) {
                        existeCodigo = res.getString("codigo");
                        i++;
                    }
                    res.close();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }

            if (!existeCodigo.equals("")) {
                JOptionPane.showMessageDialog(null, "Error: Código ingresado ya existe");
                return false;
            } else if (nombre.equals("")) {
                JOptionPane.showMessageDialog(null, "Error: Ingrese Nombre.");
                return false;
            } else if (nombre.length() < 3) {
                JOptionPane.showMessageDialog(null, "Error: Ingrese Nombre Válido.");
                return false;
            } else if (precio.equals("")) {
                JOptionPane.showMessageDialog(null, "Error: Ingrese Precio.");
                return false;
            } else if (!precio.equals("")) {
                try {
                    float validaprecio = Float.parseFloat(precio);
                    validacionprecio = 1;
                } catch (NumberFormatException f) {
                    JOptionPane.showMessageDialog(null, "Error: Ingrese Precio Válido");
                }
            }
            if (validacionprecio == 1) {
                if (Integer.parseInt(precio) < 1000) {
                    JOptionPane.showMessageDialog(null, "Error: Ingrese Precio Mayor 1000.");
                    return false;
                } else if (idcategoria == 0) {
                    JOptionPane.showMessageDialog(null, "Error: Ingrese Id de Categoría");
                    return false;
                } else if (formato4k.equals("Ingrese Formato")) {
                    JOptionPane.showMessageDialog(null, "Error: Ingrese Formato de la Película");
                    return false;
                } else {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean IngresarPel(String codigo, String nombre, String precio, int idcategoria, String formato4k) {

        if (ValidarPel(codigo, nombre, precio, idcategoria, formato4k)) {
            int codigoint = Integer.parseInt(codigo);
            int precioint = Integer.parseInt(precio);
            String q = " INSERT INTO pelicula ( codigo , nombre , precio, id_categoria, formato4k) "
                    + "VALUES (" + codigoint + ",'" + nombre + "'," + precioint + "," + idcategoria + ", '" + formato4k + "');";
            //se ejecuta la consulta
            try {
                if (this.getConexion() == null) {
                    System.out.println("La conexión es nula");
                } else {
                    System.out.println("La conexión no es nula");
                    PreparedStatement pstm = this.getConexion().prepareStatement(q);
                    pstm.execute();
                    pstm.close();
                    return true;
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
            return false;
        }
        return false;
    }

    public boolean EliminarPel(String codigo) {
        boolean res = false;
        int codigoint = Integer.parseInt(codigo);

        String q = " DELETE FROM pelicula WHERE codigo=" + codigoint + ";";
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement(q);
            pstm.execute();
            pstm.close();
            res = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return res;
    }

    //mensaje de que el codigo no exista!!
    public TableModel BuscarPel(String codigo) {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int producto = 0;
        if (codigo.equals("")) {
            JOptionPane.showMessageDialog(null, "Error: Ingrese Código a Buscar");
        } else {
            try {
                PreparedStatement pstm = this.getConexion().prepareStatement("SELECT count(*) as total FROM pelicula");
                ResultSet res = pstm.executeQuery();
                res.next();
                producto = res.getInt("total");
                res.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
            Object[][] data = new String[producto][6];
            try {
                PreparedStatement pstm = this.getConexion().prepareStatement("select pelicula.codigo, pelicula.nombre, pelicula.precio, pelicula.id_categoria, pelicula.formato4k from pelicula left join categoria on pelicula.id_categoria=categoria.id where pelicula.codigo=" + codigo + ";");
                ResultSet res = pstm.executeQuery();
                int i = 0;
                while (res.next()) {
                    data[i][0] = res.getString("codigo");
                    data[i][1] = res.getString("nombre");
                    data[i][2] = res.getString("precio");
                    data[i][3] = res.getString("id_categoria");
                    data[i][4] = res.getString("formato4k");
                    i++;
                }
                res.close();
                tablemodel.setDataVector(data, columNames);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return tablemodel;
    }

    public TableModel MostrarPel() {
        DefaultTableModel tablemodel = new DefaultTableModel();
        int producto = 0;
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT count(*) as total FROM pelicula");
            ResultSet res = pstm.executeQuery();
            res.next();
            producto = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        Object[][] data = new String[producto][9];
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement("select pelicula.codigo, pelicula.nombre, pelicula.precio, categoria.descripcion, pelicula.formato4k from pelicula left join categoria on pelicula.id_categoria=categoria.id;");
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                data[i][0] = res.getString("codigo");
                data[i][1] = res.getString("nombre");
                data[i][2] = res.getString("precio");
                data[i][3] = res.getString("descripcion");
                data[i][4] = res.getString("formato4k");
                i++;
            }
            res.close();
            tablemodel.setDataVector(data, columNames);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return tablemodel;
    }

    public boolean ModificarPel(int codigo, String nombre, String precio, int idcategoria, String formato4k) {

        if (Validar3(nombre, precio, idcategoria, formato4k)) {
            String q = " UPDATE pelicula SET nombre = '" + nombre + "' , precio = " + precio + ", id_categoria=" + idcategoria + ", formato4k='" + formato4k + "' WHERE codigo =" + codigo + ";";
            try {
                PreparedStatement pstm = this.getConexion().prepareStatement(q);
                pstm.execute();
                pstm.close();
                return true;
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
            return false;
        } else {
            return false;
        }
    }

    public boolean Validar2(String codigo) {
        int numcodigo = 0;
        int validacioncodigo = 0;
        String pelicula = "";

        if (codigo.equals("")) {
            JOptionPane.showMessageDialog(null, "Error: Ingrese Código.");
            return false;
        } else if (!codigo.equals("")) {
            try {
                float validacodigo = Float.parseFloat(codigo);
                validacioncodigo = 1;
            } catch (NumberFormatException f) {
                JOptionPane.showMessageDialog(null, "Error: Ingrese Codigo Númerico entre 10000 y 99999");
            }
        }
        if (validacioncodigo == 1) {
            numcodigo = Integer.parseInt(codigo);
            if (numcodigo < 10000 || numcodigo > 99999) {
                JOptionPane.showMessageDialog(null, "Error: Ingrese Codigo Númerico entre 10000 y 99999.");
                return false;
            } else if (numcodigo >= 10000 && numcodigo <= 99999) {
                try {
                    PreparedStatement pstm = this.getConexion().prepareStatement("SELECT pelicula.codigo FROM pelicula where pelicula.codigo =" + codigo + ";");
                    ResultSet res = pstm.executeQuery();
                    int i = 0;
                    while (res.next()) {
                        pelicula = res.getString("codigo");
                        i++;
                    }
                    res.close();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
            if (!pelicula.equals("")) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Error: El Código Ingresado no Existe");
            }
        }
        return false;
    }

    public boolean Validar3(String nombre, String precio, int idcategoria, String formato4k) {
        int validacionprecio = 0;

        if (nombre.equals("")) {
            JOptionPane.showMessageDialog(null, "Error: Ingrese Nombre.");
            return false;
        } else if (nombre.length() < 3) {
            JOptionPane.showMessageDialog(null, "Error: Ingrese Nombre Válido.");
            return false;
        } else if (precio.equals("")) {
            JOptionPane.showMessageDialog(null, "Error: Ingrese Precio.");
            return false;
        } else if (!precio.equals("")) {
            try {
                float validaprecio = Float.parseFloat(precio);
                validacionprecio = 1;
            } catch (NumberFormatException f) {
                JOptionPane.showMessageDialog(null, "Error: Ingrese Precio Válido");
            }
        }
        if (validacionprecio == 1) {
            if (Integer.parseInt(precio) <= 1000) {
                JOptionPane.showMessageDialog(null, "Error: Ingrese Precio Mayor a 1000.");
                return false;
            } else if (idcategoria == 0) {
                JOptionPane.showMessageDialog(null, "Error: Ingrese Categoria");
                return false;
            } else if (formato4k.equals("Ingrese Formato")) {
                JOptionPane.showMessageDialog(null, "Error: Ingrese Formato 4k ");
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean consulta1() {

        if (IngresarPel("12001", "Titanic", "16000", 6, "N")) {
            return true;
        }
        return false;
    }

    public boolean consulta2() {

        if (IngresarPel("12003", "Chicas Pesadas", "17000", 4, "S")) {
            return true;
        }
        return false;
    }

    public TableModel consulta4() {

        DefaultTableModel tablemodel = new DefaultTableModel();
        int producto = 0;
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement("SELECT count(*) as total FROM pelicula where id_categoria = 10 ");
            ResultSet res = pstm.executeQuery();
            res.next();
            producto = res.getInt("total");
            System.out.println(producto);
            res.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        if (producto == 0) {
            JOptionPane.showMessageDialog(null, "Error: No hay Películas en la Categoría Romance.");
        } else {

            Object[][] data = new String[producto][9];
            try {
                PreparedStatement pstm = this.getConexion().prepareStatement("select pelicula.codigo, pelicula.nombre, pelicula.precio, categoria.descripcion, pelicula.formato4k from pelicula left join categoria on pelicula.id_categoria=categoria.id where categoria.id = 10 ;");
                ResultSet res = pstm.executeQuery();
                int i = 0;
                while (res.next()) {
                    data[i][0] = res.getString("codigo");
                    data[i][1] = res.getString("nombre");
                    data[i][2] = res.getString("precio");
                    data[i][3] = res.getString("descripcion");
                    data[i][4] = res.getString("formato4k");
                    i++;
                }
                res.close();
                tablemodel.setDataVector(data, columNames);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return tablemodel;

    }

    public boolean consulta5() {
        String q = " DELETE FROM pelicula WHERE precio>2000;";
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement(q);
            pstm.execute();
            pstm.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public boolean consulta6() {
        String q = " UPDATE pelicula SET nombre = CONCAT ('P',nombre) WHERE codigo > 1;";
        try {
            PreparedStatement pstm = this.getConexion().prepareStatement(q);
            pstm.execute();
            pstm.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

}
