package initializers;

import play.api.Configuration;
import play.api.Environment;
import play.api.inject.Binding;
import play.api.inject.Module;
import scala.collection.Seq;

/**
 * @author fabiomazzone
 */
public class InitializerModule extends Module {
    @Override
    public Seq<Binding<?>> bindings(Environment environment, Configuration configuration) {
        Seq<Binding<?>> bindingSeq;
        if (environment.mode() == play.Mode.DEV.asScala()) {
            bindingSeq = seq(
                    bind(DevelopmentInitializer.class).toSelf().eagerly()
            );
        } else if(environment.mode() == play.Mode.TEST.asScala()) {
            bindingSeq = seq(
                    bind(DevelopmentInitializer.class).toSelf().eagerly()
            );
        } else {
            bindingSeq = seq(
                    bind(DevelopmentInitializer.class).toSelf().eagerly()
            );
        }
        return bindingSeq;
    }
}
