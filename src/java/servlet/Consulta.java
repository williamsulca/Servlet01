/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author l11m14
 */
public class Consulta extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/vehiculo?user=root&password=mysqladmin";
            Connection connect = DriverManager.getConnection(url);
            Statement statement = connect.createStatement();
            String query = "select id_vehiculo, marca, precio,precio * 3.5 as 'Precio_Dolar',color, anioFabricacion from vehiculo order by id_vehiculo desc";         
            ResultSet resultSet = statement.executeQuery(query);
            JsonArray jarry = new JsonArray();
            JsonObject gson = new JsonObject();

            while (resultSet.next()) {
                int id_veh = resultSet.getInt("id_vehiculo");
                String marca = resultSet.getString("marca");
                double precio = resultSet.getDouble("precio");
                double Precio_Dolar = resultSet.getDouble("Precio_Dolar");
                String color = resultSet.getString("color");
                String anioFabricacion = resultSet.getString("anioFabricacion");
                    
                
                gson = new JsonObject();
                gson.addProperty("id_vehiculo", id_veh);
                gson.addProperty("marca", marca);
                gson.addProperty("precio", precio);
                gson.addProperty("Precio_Dolar", Precio_Dolar);
                gson.addProperty("color", color);
                gson.addProperty("anioFabricacion", anioFabricacion);
                jarry.add(gson);
            }

            out.print(jarry.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
