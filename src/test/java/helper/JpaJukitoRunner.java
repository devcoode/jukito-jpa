package helper;

import com.google.inject.Injector;
import com.google.inject.persist.UnitOfWork;
import org.jukito.JukitoRunner;
import org.junit.runner.Description;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

import java.lang.reflect.InvocationTargetException;

public class JpaJukitoRunner extends JukitoRunner {

    public JpaJukitoRunner(final Class<?> klass) throws InitializationError, InvocationTargetException, InstantiationException, IllegalAccessException {
        super(klass);
    }

    public JpaJukitoRunner(final Class<?> klass, final Injector injector) throws InitializationError, InvocationTargetException, InstantiationException, IllegalAccessException {
        super(klass, injector);
    }

    private UnitOfWork unitOfWork;

    @Override
    protected Object createTest() throws Exception {
        this.unitOfWork = getInjector().getInstance(UnitOfWork.class);
        this.unitOfWork.begin();
        return super.createTest();
    }

    @Override
    public void run(final RunNotifier notifier) {
        notifier.addListener(new RunListener() {

            @Override
            public void testFinished(final Description description) throws Exception {
                // this ensures every tests use distinct entity manager instances
                unitOfWork.end();
            }
        });
        super.run(notifier);
    }
}
