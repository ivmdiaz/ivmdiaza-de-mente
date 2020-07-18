package co.demente.negocio.services;

public class Services {

    private static Services mInstance = null;

    public UsuarioService mUserService;
    public ConfAppService mConfAppService;


    private Services() {
        mUserService = UsuarioService.getInstance();
        mConfAppService = ConfAppService.getConfAppService();
    }

    public static Services getInstance() {
        if (mInstance == null) {
            mInstance = new Services();
        }
        return mInstance;
    }

}
