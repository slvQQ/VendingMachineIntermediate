import Controller.VendingMachineController;
import DataAccessObject.VendingMachineAuditDao;
import DataAccessObject.VendingMachineAuditDaoFileImpl;
import DataAccessObject.VendingMachineDao;
import DataAccessObject.VendingMachineDaoFileImpl;
import Service.VendingMachineServiceLayer;
import Service.VendingMachineServiceLayerImpl;
import UI.UserIO;
import UI.UserIOConsoleImpl;
import UI.VendingMachineView;

public class VendingMachineApp {

    public static void main(String[] args) {

        UserIO io = new UserIOConsoleImpl();
        VendingMachineView view = new VendingMachineView(io);
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoFileImpl();
        VendingMachineDao dao = new VendingMachineDaoFileImpl();
        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(auditDao, dao);
        VendingMachineController controller = new VendingMachineController(view, service);
        controller.run();

    }
}
