package project;


public final class SupplierInterface extends StandardInterface {

    //TODO finish implementation

    private Supplier currentSupplier;

    /**
     * The login manager...
     */
    private LoginManager loginManager;

    public SupplierInterface(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

    @Override
    protected void mainInterface() {

    }

    @Override
    protected Account createAccount() {
        return null;
    }
}