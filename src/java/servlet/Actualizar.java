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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author l11m14
 */
public class Actualizar extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        try {
            PrintWriter out = response.getWriter();
            String id_vehiculo = request.getParameter("id_veh");
            String marca = request.getParameter("marca");
            double precio = Double.parseDouble(request.getParameter("precio"));
            String color = request.getParameter("color");
            //
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost/vehiculo?user=root&password=mysqladmin";
            Connection connect = DriverManager.getConnection(url);
            String query = "UPDATE vehiculo SET marca = ?, precio = ?, color=? WHERE id_vehiculo = ?";
            PreparedStatement ps = connect.prepareStatement(query);
            ps.setString(1, marca);
            ps.setDouble(2, precio);
            ps.setString(3, color);
            ps.setString(4, id_vehiculo);
            ps.executeUpdate();

            JsonObject gson = new JsonObject();
            gson.addProperty("mensaje", "VEHICULO ACTUALIZADO");
            out.print(gson.toString());

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
