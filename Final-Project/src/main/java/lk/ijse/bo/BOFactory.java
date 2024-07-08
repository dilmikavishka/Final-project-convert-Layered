package lk.ijse.bo;


import lk.ijse.bo.impl.*;


public class BOFactory {

    private static BOFactory boFactory;
    private BOFactory(){}

    public static BOFactory getBoFactory(){
        return (boFactory==null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        BATCH,BATCHCOST,BATCHMACHINE,BATCHMATERIAL,CUSTOMER,EMPLOYEE,MACHINE,MATERIAL,MATERIALDETAIL,ORDER,ORDERDETAIL,PAYMENT,PLACEORDER,QUERY,SUPPLIER
    }


    public SuperBO getBO(BOTypes type){
        switch(type){
            case BATCH:
                return new BatchBOImpl();
            case BATCHCOST:
                return new BatchCostBOImpl();
            case BATCHMACHINE:
                return new BatchMachineBOImpl();
            case BATCHMATERIAL:
                return new BatchMaterialBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case MACHINE:
                return new MachineBOImpl();
            case MATERIAL:
                return new MaterialBOImpl();
            case MATERIALDETAIL:
                return new MaterialDetailBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case ORDERDETAIL:
                return new OrderDetailBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case PLACEORDER:
                return new PlaceOrderBOImpl();
            case QUERY:
                return new QueryBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            default:
                return null;
        }
    }

}
