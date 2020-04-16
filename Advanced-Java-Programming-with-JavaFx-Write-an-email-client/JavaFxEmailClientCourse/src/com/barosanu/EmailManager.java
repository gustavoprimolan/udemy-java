package com.barosanu;

import com.barosanu.controller.services.FetchFoldersService;
import com.barosanu.model.EmailAccount;
import com.barosanu.model.EmailTreeItem;
import javafx.scene.control.TreeItem;

public class EmailManager {

    //FOLDER HANDLING;
//    private TreeItem<String> foldersRoot = new TreeItem<String>("");
    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<String>("");

    public EmailTreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    public void addEmailAccount(EmailAccount emailAccount) {
        EmailTreeItem<String> treeItem = new EmailTreeItem<>(emailAccount.getAddress());
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), treeItem);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(treeItem);
    }

}
