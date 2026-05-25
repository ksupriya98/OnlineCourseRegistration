package filter;

import com.app.model.Student;
import util.SessionConstants;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/courses", "/enrollments", "/enroll", "/drop", "/profile"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        Student student = null;
        if (session != null) {
            student = (Student) session.getAttribute(SessionConstants.STUDENT_SESSION);
        }

        if (student == null) {
            resp.sendRedirect(req.getContextPath() + "/login?error=session");
            return;
        }

        chain.doFilter(request, response);
    }
}