package fel.cvut.user.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class for logging on authorization
 */
public class AuthorizationInterceptor implements HandlerInterceptor {
    private final Logger LOG = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    /**
     *  Writes logs before the request has been sent
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        if (request.getHeader("Authorization") == null){
            LOG.info("\n-------- AuthorizationInterceptor.preHandle ---");
            LOG.info("Request URL: " + request.getRequestURL());
            LOG.info("Authorization not sent.");
            LOG.info("Validation NOT OK.");
            return false;
        }
        else if (request.getHeader("Authorization").equals("Test"))    {
            LOG.info("\n-------- AuthorizationInterceptor.preHandle ---");
            LOG.info("Request URL: " + request.getRequestURL());
            LOG.info("Start Time: " + System.currentTimeMillis());
            LOG.info("Validation OK.");
            return true;
        } else {
            LOG.info("\n-------- AuthorizationInterceptor.preHandle ---");
            LOG.info("Request URL: " + request.getRequestURL());
            LOG.info("Validation NOT OK.");
            return false;
        }
    }

    /**
     *  Writes logs after the request has been sent
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOG.info("\n-------- AuthorizationInterceptor.preHandle ---");
        LOG.info("Request URL: " + request.getRequestURL());
    }

    /**
     * Writes logs when the request has been completed
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOG.info("\n-------- AuthorizationInterceptor.preHandle ---");

        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        LOG.info("Request URL: " + request.getRequestURL());
        LOG.info("End Time: " + endTime);

        LOG.info("Time Taken: " + (endTime - startTime));
    }
}
