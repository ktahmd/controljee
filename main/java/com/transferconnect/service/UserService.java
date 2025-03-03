package com.transferconnect.service;

import com.transferconnect.dao.UserDAO;
import com.transferconnect.model.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    private static UserService instance;

    // Singleton Pattern لضمان وجود نسخة واحدة فقط
    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    // استرجاع المستخدم عن طريق الـ ID
    public User getUserById(String userId) {
        return userDAO.getUserById(userId);
    }

    // التحقق من صحة المستخدم
    public boolean validateUser(String username, String password) {
        User user = userDAO.getUserByConstraintKey(username);
        return user != null && user.getPassword().equals(password);
    }

    // تغيير كلمة المرور
    public boolean changePassword(String userId, String oldPassword, String newPassword) {
        User user = userDAO.getUserById(userId);
        if (user != null && user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            userDAO.updateUser(userId, user);
            return true;
        }
        return false;
    }

    // إعادة تعيين كلمة المرور
    public boolean resetPassword(String userId, String newPassword) {
        User user = userDAO.getUserById(userId);
        if (user != null) {
            user.setPassword(newPassword);
            userDAO.updateUser(userId, user);
            return true;
        }
        return false;
    }

    // التحقق مما إذا كان المستخدم موجودًا
    public boolean isUserExists(String userId) {
        return userDAO.getUserById(userId) != null;
    }
}
