package by.vsu.epam.ioc;

public final class ContextFactory {
    private ContextFactory() {}

    public static Context newInstance() {
        return new ContextImpl();
    }
}
