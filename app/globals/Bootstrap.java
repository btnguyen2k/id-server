package globals;

import play.Application;
import play.GlobalSettings;

public class Bootstrap extends GlobalSettings {

    public void onStart(Application app) {
        super.onStart(app);
        Registry.init();
    }

    @Override
    public void onStop(Application app) {
        Registry.destroy();
        super.onStop(app);
    }
}
