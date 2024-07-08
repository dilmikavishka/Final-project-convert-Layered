package lk.ijse.dao;

import lk.ijse.dao.impl.*;

public class DAOFactory  {

    private static DAOFactory daoFactory;
    private DAOFactory(){}

    public static DAOFactory getDaoFactory(){
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOType{
        BATCH,BATCHMACHINE,BATCHMATERIAL,CUSTOMER,EMPLOYEE,MACHINE,MATERIAL,MATERIALDETAIL,ORDER,ORDERDETAIL,PAYMENT,QUERY,SUPPLIER
    }

    public SuperDAO getDAO(DAOType daoType){
        switch(daoType){
            case BATCH:
                return new BatchDAOImpl();
            case BATCHMACHINE:
                return new BatchDAOImpl();
            case BATCHMATERIAL:
                return new BatchDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case MACHINE:
                return new MachineDAOImpl();
            case MATERIAL:
                return new MaterialDAOImpl();
            case MATERIALDETAIL:
                return new MaterialDetailDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDERDETAIL:
                return new OrderDetailDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            default:
                return null;
        }
    }

}
