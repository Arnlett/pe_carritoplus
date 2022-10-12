package com.emergentes.pe_carrito;

import java.io.IOException;
import java.util.ArrayList;
//import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ProcesaServlet", urlPatterns = {"/ProcesaServlet"})
public class ProcesaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String op = request.getParameter("op");

        if (op.equals("vaciar")) {
            // obtener acceso al objeto session
            HttpSession ses = request.getSession();
            //se elimina la session
            ses.invalidate();
            //trasnfiere el control a index.jsp
            response.sendRedirect("index.jsp");
        }
        if (op.equals("eliminar")) {
            int pos = -1;
            int buscado = -1;
            int id = Integer.parseInt(request.getParameter("id"));
            // Eliminar el producto
            HttpSession ses = request.getSession();
            ArrayList<Producto> lista = (ArrayList<Producto>) ses.getAttribute("almacen");

            for (Producto p : lista) {
                pos++;
                if (p.getId() == id) {
                    buscado = pos;
                }
            }
            lista.remove(buscado);
            response.sendRedirect("index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
       //recupera el producto enviadoo desde formulario 
        String producto = request.getParameter("producto");
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        double precio = Double.parseDouble(request.getParameter("precio"));

        Producto prod = new Producto();

        prod.setId(id);
        prod.setProducto(producto);
        prod.setCantidad(cantidad);
        prod.setPrecio(precio);

        HttpSession ses = request.getSession();

        ArrayList<Producto> lista = (ArrayList<Producto>) ses.getAttribute("almacen");

        lista.add(prod);

        response.sendRedirect("index.jsp");

    }

}
