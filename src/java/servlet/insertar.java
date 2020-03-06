/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
public class insertar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            String marca = request.getParameter("marca");
            double precio = Double.parseDouble(request.getParameter("precio"));
            String color = request.getParameter("color");
            //
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/vehiculo?user=root&password=mysqladmin";
            Connection connect = DriverManager.getConnection(url);

            String query = "SELECT MAX(id_vehiculo) + 1 AS new_id FROM vehiculo";
            Statement statement = connect.createStatement();
 //           query = "SELECT (precio)* 0.35 AS pre_dolar FROM vehiculo where id_vehiculo = ?";
           ResultSet resultSet = statement.executeQuery(query);


            
            
            
            int id_vehiculo = 0;
            while (resultSet.next()) {
                id_vehiculo = resultSet.getInt("new_id");
            }
            if (id_vehiculo == 0) {
                id_vehiculo = 1;
            }

            query = "INSERT INTO vehiculo VALUES (?,?,?,?)";
            PreparedStatement ps = connect.prepareStatement(query);

            ps.setInt(1, id_vehiculo);
            ps.setString(2, marca);
            ps.setDouble(3, precio);
            ps.setString(4, color);
            ps.executeUpdate();

            JsonObject gson = new JsonObject();
            gson.addProperty("mensaje", "Vehiculo registrado.");
            gson.addProperty("id_veh", id_vehiculo);
            out.print(gson.toString()); // Enviar rpta al JS
        } catch (Exception e) {
            System.err.println(e);
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
