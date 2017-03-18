package initializers;

import play.api.Configuration;
import play.api.Environment;
import play.api.Mode;
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
        if (environment.mode() == Mode.Dev()) {
            bindingSeq = seq(
                    bind(DevelopmentInitializer.class).toSelf().eagerly()
            );
        } else if(environment.mode() == Mode.Test()) {
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
