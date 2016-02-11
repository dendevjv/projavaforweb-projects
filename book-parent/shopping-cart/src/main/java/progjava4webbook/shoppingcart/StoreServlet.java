package progjava4webbook.shoppingcart;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(
        name = "storeServlet", 
        urlPatterns = "/shop")
public class StoreServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final Map<Integer, String> products = new Hashtable<>();
    
    public StoreServlet() {
        this.products.put(1, "Sandpager");
        this.products.put(2, "Nails");
        this.products.put(3, "Glue");
        this.products.put(4, "Paint");
        this.products.put(5, "Tape");
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "browse";
        }
        switch (action) {
        case "addToCart":
            addToCart(req, resp);
            break;
        case "viewCart":
            viewCart(req, resp);
            break;
        case "emptyCart":
            emptyCart(req, resp);
            break;
        case "browser":
        default:
            browser(req, resp);
            break;
        }
    }

    private void emptyCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().removeAttribute("cart");
        resp.sendRedirect("shop?action=viewCart");
    }

    private void browser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", products);
        req.getRequestDispatcher("/WEB-INF/jsp/view/browse.jsp").forward(req, resp);
    }

    private void viewCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", products);
        req.getRequestDispatcher("/WEB-INF/jsp/view/viewCart.jsp").forward(req, resp);
    }

    private void addToCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int productId;
        try {
            productId = Integer.parseInt(req.getParameter("productId"));
        } catch (Exception e) {
            resp.sendRedirect("shop");
            return;
        }
        
        HttpSession session = req.getSession();
        if (session.getAttribute("cart") == null) {
            session.setAttribute("cart", new Hashtable<Integer, Integer>());
        }
        
        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart = (Map<Integer, Integer>)session.getAttribute("cart");
        if (!cart.containsKey(productId)) {
            cart.put(productId, 0);
        }
        cart.put(productId, cart.get(productId) + 1);
        
        resp.sendRedirect("shop?action=viewCart");
    }
}
