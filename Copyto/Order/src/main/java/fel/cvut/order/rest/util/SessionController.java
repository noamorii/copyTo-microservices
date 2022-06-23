package fel.cvut.order.rest.util;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("api/v1/")
public class SessionController {

    @GetMapping(value = "/{userid}")
    public void createUserCookie(HttpServletResponse response, @PathVariable Integer userid) throws IOException {
        Cookie cookie = new Cookie("userId", userid.toString());
        cookie.setMaxAge(60*60);
        response.addCookie(cookie);
        response.sendRedirect("http://localhost:8081/api/v1/user");
    }

    @GetMapping(value = "/user")
    public Integer getCurrentUserId(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("userId")) {
                return Integer.valueOf(cookie.getValue());
            }
        }
        return 0;
    }
}
