package example;

import com.blade.mvc.RouteContext;
import com.blade.mvc.hook.WebHook;

public class MyMiddleware implements WebHook{

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MyMiddleware.class);

    @Override
    public boolean before(RouteContext context) {
        log.info("[MyMiddleware] called before Route method and other WebHooks");
        return true;
    }
}
