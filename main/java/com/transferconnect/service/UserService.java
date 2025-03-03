package com.transferconnect.service;

import com.transferconnect.dao.UserDAO;
import com.transferconnect.model.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public User validateUser(String userId, String password) {
        User user = userDAO.getUserById(userId);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null; 
    }
 // تغيير كلمة المرور
    public boolean changePassword(String userId, String oldPassword, String newPassword) {
        User user = userDAO.getUserById(userId);
        if (user != null && user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            userDAO.updateUser(userId, user);
            return true; // نجاح تغيير كلمة المرور
        }
        return false; // فشل
    }

    // إعادة تعيين كلمة المرور (نسيت كلمة المرور)
    public boolean resetPassword(String userId, String newPassword) {
        User user = userDAO.getUserById(userId);
        if (user != null) {
            user.setPassword(newPassword);
            userDAO.updateUser(userId, user);
            return true;
        }
        return false;
    }

    // التحقق مما إذا كان المستخدم مسجلاً مسبقًا
    public boolean isUserExists(String userId) {
        return userDAO.getUserById(userId) != null;
    }

}
