package com.szells.gce.auth.interceptors;

/*@Component
@Slf4j
public class CorsInterceptor implements Filter {

    public CorsInterceptor() {
        log.info("SimpleCORSFilter init");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");

        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }
}*/

/*
@EqualsAndHashCode(callSuper = true)
@Component
@Data
public class CorsInterceptor extends HandlerInterceptorAdapter {

    private static final String ORIGIN = "Origin";
    private static final String AC_REQUEST_METHOD = "Access-Control-Request-Method";
    private static final String AC_REQUEST_HEADERS = "Access-Control-Request-Headers";

    private static final String AC_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
    private static final String AC_ALLOW_METHODS = "Access-Control-Allow-Methods";
    private static final String AC_ALLOW_HEADERS = "Access-Control-Allow-Headers";

    private CorsData corsData;

    private String origin;
    private String allowMethods;
    private String allowHeaders;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        this.corsData = new CorsData(request);

        if (this.corsData.isPreflighted()) {
            response.setHeader(AC_ALLOW_ORIGIN, origin);
            response.setHeader(AC_ALLOW_METHODS, allowMethods);
            response.setHeader(AC_ALLOW_HEADERS, allowHeaders);

            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (this.corsData.isSimple()) {
            response.setHeader(AC_ALLOW_ORIGIN, origin);
        }
    }

    @Data
    class CorsData {

        private String origin;
        private String requestMethods;
        private String requestHeaders;

        CorsData(HttpServletRequest request) {
            this.origin = request.getHeader(ORIGIN);
            this.requestMethods = request.getHeader(AC_REQUEST_METHOD);
            this.requestHeaders = request.getHeader(AC_REQUEST_HEADERS);
        }

        public boolean hasOrigin() {
            return origin != null && !origin.isEmpty();
        }

        public boolean hasRequestMethods() {
            return requestMethods != null && !requestMethods.isEmpty();
        }

        public boolean hasRequestHeaders() {
            return requestHeaders != null && !requestHeaders.isEmpty();
        }

        public boolean isPreflighted() {
            return hasOrigin() && hasRequestHeaders() && hasRequestMethods();
        }

        public boolean isSimple() {
            return hasOrigin() && !hasRequestHeaders();
        }
    }
}*/
