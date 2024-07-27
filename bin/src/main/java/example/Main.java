package example;

import com.blade.Blade;
import com.blade.event.EventType;
import com.blade.mvc.WebContext;
import com.blade.mvc.http.Session;

public class Main {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        Blade.of()
            .get("/", ctx -> ctx.render("index.html"))
            .addStatics("/custom-static")
            // .showFileList(true)
            .enableCors(true)
            .before("/user/*", ctx -> log.info("[NarrowedHook] Before '/user/*', URL called: " + ctx.uri()))
            .on(EventType.SERVER_STARTED, e -> {
                String version = WebContext.blade()
                    .env("app.version")
                    .orElse("N/D");
                log.info("[Event::serverStarted] Loading 'app.version' from configuration, value: " + version);
            })
            .on(EventType.SESSION_CREATED, e -> {
                Session session = (Session) e.attribute("session");
                session.attribute("mySessionValue", "Baeldung");
            })
            .use(new MyMiddleware())
            .start(Main.class, args);
    }
}
