package com.transferconnect.controller;

import com.transferconnect.dao.*;
import com.transferconnect.model.*;
import com.transferconnect.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Path("/transfers")
public class TransferServlet {
    @GET
    @Path("/session")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSessionUserTransfers(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Utilisateur non connecté").build();
        }

        List<Transfer> userTransfers = UserService.getInstance().getAllTransfersBySessionUser(session);
        return Response.ok(userTransfers != null ? userTransfers : "[]").build();
    }

    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTransfer(@Context HttpServletRequest request, Map<String, Object> requestData) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("{\"success\": false, \"message\": \"Utilisateur non connecté\"}")
                           .build();
        }

        String senderService = (String) requestData.get("senderService");
        String receiverService = (String) requestData.get("receiverService");
        String receiverNumber = (String) requestData.get("receiverNumber");
        double amount = ((Number) requestData.get("amount")).doubleValue();
        String reason = (String) requestData.get("reason");

        User user = UserService.getInstance().getUserBySessionUsername(session);
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("{\"success\": false, \"message\": \"Utilisateur introuvable\"}")
                           .build();
        }

        AccountDAO accountDAO = new AccountDAO();
        TransferDAO transferDAO = new TransferDAO();

        Map<String, Account> accounts = accountDAO.getAccountByConstraintKey(user.getNni());
        Account senderAccount = null;

        if (accounts != null) {
            for (Map.Entry<String, Account> entry : accounts.entrySet()) {
                Account acc = entry.getValue();
                if (acc.getBank().trim().equalsIgnoreCase(senderService.trim())) {
                    senderAccount = acc;
                    break;
                }
            }
        }

        if (senderAccount == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"success\": false, \"message\": \"Compte émetteur introuvable\"}")
                           .build();
        }

        if (senderAccount.getBalance() < amount) {
            saveTransfer(transferDAO, user, senderAccount, receiverNumber, senderService, receiverService, amount, "failed");
            return Response.ok("{\"success\": false, \"message\": \"Votre solde est insuffisant\"}").build();
        }

        Map<String, Account> receiverAccounts = accountDAO.getAccountByConstraintKeytel(receiverNumber);
        Account receiverAccount = null;
        if (receiverAccounts != null) {
            for (Map.Entry<String, Account> entry : receiverAccounts.entrySet()) {
                Account acc = entry.getValue();
                if (acc.getBank().trim().equalsIgnoreCase(receiverService.trim())) {
                    receiverAccount = acc;
                    break;
                }
            }
        }
        System.out.println("Receiver accounts: " + receiverAccounts);
        System.out.println("Looking for receiverService: " + receiverService);
        if (receiverAccount == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("{\"success\": false, \"message\": \"Compte destinataire introuvable pour ce numéro et cette banque\"}")
                           .build();
        }

        senderAccount.setBalance(senderAccount.getBalance() - amount);
        receiverAccount.setBalance(receiverAccount.getBalance() + amount);
        accountDAO.updateAccount(senderAccount.getAccountId(), senderAccount);
        accountDAO.updateAccount(receiverAccount.getAccountId(), receiverAccount);

        Transfer transfer = saveTransfer(transferDAO, user, senderAccount, receiverNumber, senderService, receiverService, amount, "completed");
        return Response.ok("{\"success\": true, \"message\": \"Transfert réussi avec ID: " + transfer.getTransferId() + "\"}").build();
    }

    private Transfer saveTransfer(TransferDAO transferDAO, User user, Account senderAccount, String receiverNumber,
                                  String senderService, String receiverService, double amount, String status) {
        String transferId = generateTransferId(transferDAO);
        Transfer transfer = new Transfer(transferId, user.getNni(), senderAccount.getAccountId(), receiverNumber,
                                         senderService, receiverService, amount, new Date(), status);
        transferDAO.addTransfer(transfer);
        return transfer;
    }

    private String generateTransferId(TransferDAO transferDAO) {
        int count = transferDAO.getAllTransfers().size() + 1;
        return String.format("TR-%05d", count);
    }
}