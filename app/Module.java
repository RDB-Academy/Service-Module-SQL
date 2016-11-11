import initializers.Initializer;
import initializers.InitializerBase;
import play.api.Configuration;
import play.api.Environment;
import play.api.inject.Binding;
import scala.collection.Seq;

/**
 * @author fabiomazzone
 */
public class Module extends play.api.inject.Module {
    @Override
    public Seq<Binding<?>> bindings(Environment environment, Configuration configuration) {
        return seq(
                bind(InitializerBase.class).to(Initializer.class).eagerly()
        );
    }
}
