package lk.ijse.bo.impl;

import lk.ijse.bo.custome.PaymentBO;
import lk.ijse.dao.custome.PaymentDAO;
import lk.ijse.dao.impl.PaymentDAOImpl;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBOImpl implements PaymentBO {

    PaymentDAO paymentDAO = new PaymentDAOImpl();
    @Override
    public List<PaymentDTO> getAllPayment() throws SQLException, ClassNotFoundException {
        List<PaymentDTO> paymentDTOS = new ArrayList<>();
        List<Payment> payments = paymentDAO.getAll();
        for (Payment payment : payments) {
            paymentDTOS.add(new PaymentDTO(payment.getPaymentId(),payment.getPaymentDate(),payment.getAmount(),payment.getType(),payment.getOId()));
        }
        return paymentDTOS;
    }

    @Override
    public boolean deletePayment(String id) throws SQLException, ClassNotFoundException {
        return paymentDAO.delete(id);
    }

    @Override
    public boolean savePayment(PaymentDTO payment) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(new Payment(payment.getPaymentId(),payment.getPaymentDate(),payment.getAmount(),payment.getType(),payment.getOId()));
    }

    @Override
    public boolean updatePayment(PaymentDTO payment) throws SQLException, ClassNotFoundException {
        return paymentDAO.update(new Payment(payment.getPaymentId(),payment.getPaymentDate(),payment.getAmount(),payment.getType(),payment.getOId()));
    }

    @Override
    public PaymentDTO searchByIdPayment(String id) throws SQLException, ClassNotFoundException {
        Payment payment = paymentDAO.searchById(id);
        return new PaymentDTO(payment.getPaymentId(),payment.getPaymentDate(),payment.getAmount(),payment.getType(),payment.getOId());
    }

    @Override
    public String generateNextIdPayment() throws SQLException, ClassNotFoundException {
        return paymentDAO.generateNextId();
    }

    @Override
    public List<String> getPaymentId() throws SQLException, ClassNotFoundException {
        return paymentDAO.getPaymentId();
    }
}
