package servlet;

import model.CarritoItem;
import model.Producto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/carrito")
public class CarritoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        Map<Integer, CarritoItem> carrito =
                (Map<Integer, CarritoItem>) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new LinkedHashMap<>();
            session.setAttribute("carrito", carrito);
        }

        String accion = req.getParameter("accion");

        if ("agregar".equals(accion)) {

            int id = Integer.parseInt(req.getParameter("idProducto"));

            Producto prod = obtenerProducto(id);

            if (prod != null) {

                carrito.merge(id,
                        new CarritoItem(prod, 1),
                        (existente, nuevo) -> {
                            existente.setCantidad(existente.getCantidad() + 1);
                            return existente;
                        });
            }

        } else if ("limpiar".equals(accion)) {
            carrito.clear();
        }

        resp.sendRedirect(req.getContextPath() + "/carrito");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        Map<Integer, CarritoItem> carrito =
                (Map<Integer, CarritoItem>) session.getAttribute("carrito");

        req.setAttribute("items",
                carrito != null ? carrito.values() : new ArrayList<>());

        req.getRequestDispatcher("/WEB-INF/views/carrito.jsp")
                .forward(req, resp);
    }

    private Producto obtenerProducto(int id) {

        List<Producto> catalogo =
                (List<Producto>) getServletContext().getAttribute("catalogo");

        if (catalogo == null) {
            throw new RuntimeException("Catálogo no inicializado en ServletContext");
        }

        return catalogo.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
}